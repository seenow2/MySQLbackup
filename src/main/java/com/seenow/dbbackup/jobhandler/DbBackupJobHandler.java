package com.seenow.dbbackup.jobhandler;

import com.seenow.dbbackup.pojo.TBackupLog;
import com.seenow.dbbackup.pojo.TDbSource;
import com.seenow.dbbackup.pojo.TPolicy;
import com.seenow.dbbackup.service.TBackupLogService;
import com.seenow.dbbackup.service.TDbSourceService;
import com.seenow.dbbackup.service.TPolicyService;
import com.seenow.dbbackup.util.DateUtils;
import com.seenow.dbbackup.util.DbUtil;
import com.seenow.dbbackup.util.StringUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class DbBackupJobHandler {

    @Autowired
    TPolicyService tPolicyService;
    @Autowired
    TDbSourceService tDbSourceService;
    @Autowired
    TBackupLogService tBackupLogService;

    private static Logger logger = LoggerFactory.getLogger(DbBackupJobHandler.class);

    @XxlJob("dbBackupJobHandler")
    public void dbBackupJobHandler() throws Exception {

        String param = XxlJobHelper.getJobParam();
        int dbid = Integer.parseInt(param);
        TDbSource tDbSource = tDbSourceService.findById(dbid);
        List<TPolicy> tpolicies = tPolicyService.findByDbId(dbid);
        String fileName = new String();
        String conditionSql1 = new String();
        String cmdSql = new String();
        Boolean succesed = true;
        List<String> delSql = new ArrayList<>();
        String runMsg = new String();
        String logContent= new String();

        XxlJobHelper.log("XXL-JOB, 开始备份数据库."+ tDbSource.getDbName());

        if (tpolicies.size() == 1 && tpolicies.get(0).getCompleteBackup().equals("1")) {//全备
            fileName = tDbSource.getDbName();
            cmdSql = DbUtil.getCmdSql(tDbSource, "", fileName);
            XxlJobHelper.log("全备导出 ：" + cmdSql);
            succesed = DbUtil.exeSql(cmdSql) ? true : false;
            runMsg = succesed ? "OK!,备份完成。" : "Sorry,备份失败！";
            logContent = logContent + fileName + ".sql";
        }

        if (tpolicies.get(0).getCompleteBackup().equals("0")) {  //条件导出
            for (TPolicy tPolicy : tpolicies) {
                //记录下需要被忽略的表
                conditionSql1 = conditionSql1
                        + " --ignore-table="
                        + tDbSource.getDbName()
                        + "."
                        + tPolicy.getTableName();
                if (tPolicy.getDelSign().equals("1")) {  //需要清除才清除
                    delSql.add("delete from "
                            + tDbSource.getDbName()
                            + "." + tPolicy.getTableName()
                            + " where "
                            + tPolicy.getSignfld()
                            + "<DATE_SUB(CURDATE(), INTERVAL "
                            + tPolicy.getDaysLen()
                            + " DAY)");
                }
                // 开始条件备份
                String conditionSql2 = " --tables "
                        + tPolicy.getTableName()
                        + " --where=\""
                        + tPolicy.getSignfld()
                        + "<DATE_SUB(CURDATE(), INTERVAL "
                        + tPolicy.getDaysLen()
                        + " DAY)\" ";
                fileName = tPolicy.getTableName() + "-" + DateUtils.parseCurrentDate("HHmmss");;
                logContent =  logContent + "、" + fileName + ".sql" ;
                cmdSql = DbUtil.getCmdSql(tDbSource, conditionSql2, fileName);
                XxlJobHelper.log("条件导出 ：" + cmdSql);

                if (!DbUtil.exeSql(cmdSql)) {
                    succesed = false;
                    break;
                }
            }
        }

        if (succesed && !conditionSql1.equals("")) {  //当要导出的表都成功导出了
            fileName = "others" + "-" + DateUtils.parseCurrentDate("HHmmss");
            logContent = logContent + "、" + fileName + ".sql" ;
            cmdSql = DbUtil.getCmdSql(tDbSource, conditionSql1, fileName);//其余的表全部导出
            XxlJobHelper.log("默认全表导出所有未指定策略的表 ：" + cmdSql);
            succesed = DbUtil.exeSql(cmdSql) ? true : false;
        }

        runMsg = succesed ? "OK!,备份完成。" : "Sorry,备份失败！";

        if (succesed && delSql.size() > 0) {     //导出成功了且有需要清除的表
            runMsg = "OK!,备份完成,清除数据完成。";
            for (String s : delSql) {
                XxlJobHelper.log("执行清除 ：" + s);
                if (!DbUtil.exeSql(DbUtil.getDelSql(tDbSource, s))) {
                    runMsg = "Sorry,备份成功，但清理数据失败！";
                    break;
                }
            }
        }

        TBackupLog tBackupLog = new TBackupLog();
        tBackupLog.setDbid(tDbSource.getId());
        tBackupLog.setCreater("xxl-job客户端");
        tBackupLog.setCreateTime(new Date());
        tBackupLog.setContent(runMsg + "数据库名："
                + tDbSource.getHostip() + ":"
                + tDbSource.getDbName() + ", 导出文件名："
                + StringUtils.removePreChar(logContent)
        );
        if(succesed){
            log.info(runMsg);
            tBackupLog.setSucceed("成功");
        }else{
            log.warn(runMsg);
            tBackupLog.setSucceed("失败");
        }
        tBackupLogService.add(tBackupLog);

        XxlJobHelper.log(runMsg);



    }

    public void init(){
        logger.info("init");
    }
    public void destroy(){
        logger.info("destory");
    }

}
