package com.seenow.dbbackup.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seenow.dbbackup.pojo.TBackupLog;
import com.seenow.dbbackup.pojo.TDbSource;
import com.seenow.dbbackup.pojo.TPolicy;
import com.seenow.dbbackup.service.TBackupLogService;
import com.seenow.dbbackup.service.TDbSourceService;
import com.seenow.dbbackup.service.TPolicyService;
import com.seenow.dbbackup.util.DateUtils;
import com.seenow.dbbackup.util.DbUtil;
import com.seenow.dbbackup.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Classname PageController
 * @Description TODO
 * @@Create 2021-06-24 17:57
 * @Created by seenow
 */
@Slf4j
@Controller
public class PageController {
    @Autowired
    TDbSource tDbSource;
    @Autowired
    TDbSourceService tDbSourceService;
    @Autowired
    TPolicyService tPolicyService;

    @Autowired
    TBackupLogService tBackupLogService;

    /**
     * @description:  首页，数据源列表
     * @param: [model]
     * @return: String
     * @author: seenow
     * @date: 2021-06-26 14:05:55
     */
    @RequestMapping(path = "/index" , method = RequestMethod.GET)
    public String getHtml(Model model){
        model.addAttribute("tDbSources" ,tDbSourceService.findAll());
        return "dblist";
    }


    /**
     * @description:  备份日志列表页
     * @param: [model, pageNum, dbid]
     * @return: String
     * @author: seenow
     * @date: 2021-07-13 16:40:10
     */
    @GetMapping(path = "/bakLogList" )
    public String bakLogList(Model model,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam("dbid") int dbid){
        PageHelper.startPage(pageNum,10);// pageNum:当前页码数，第一次进来时默认为1（首页）
        List<TBackupLog> tBackupLogs = tBackupLogService.findByDbId(dbid);//list:页面要展示的数据的集合
        PageInfo<TBackupLog> pageInfo = new PageInfo<TBackupLog>(tBackupLogs);//pageInfo:将分页数据和显示的数据封装到PageInfo当中
        model.addAttribute("pageInfo",pageInfo);//将封装好的数据返回到前台页面
        model.addAttribute("dbid" ,dbid);
        model.addAttribute("tBackupLogs" ,tBackupLogs);
        return "bakloglist";
    }


    /**
     * @description:  备份策略列表
     * @param: [dbid, hostip, dbname]
     * @return: ModelAndView
     * @author: seenow
     * @date: 2021-06-29 16:25:53
     */
    @GetMapping("/poliList")
    public ModelAndView policesList(@RequestParam("dbid") int dbid,@RequestParam("hostip") String hostip,@RequestParam("dbname") String dbname){
        ModelAndView modelAndView = new ModelAndView("policylist");
        modelAndView.addObject("tpolicies",tPolicyService.findByDbId(dbid));
        modelAndView.addObject("dbid",dbid);
        modelAndView.addObject("hostip",hostip);
        modelAndView.addObject("dbname",dbname);
        return modelAndView;
    }


    /**
     * @description:  删除数据源
     * @param: [dbid]
     * @return: String
     * @author: seenow
     * @date: 2021-06-26 14:17:16
     */
    @RequestMapping(path = "/deldb")
    public String deldb(@RequestParam(name = "dbid") Integer dbId){
        tDbSourceService.delete(dbId);
        //删除这个数据源上所有备份策略
        tPolicyService.deleteByDbId(dbId);
        //还要删除所有相关的备份日志
        tBackupLogService.deleteByDbId(dbId);
        return "redirect:/index";
    }

    @RequestMapping(path = "/delbaklog")
    public String delbaklog(@RequestParam(name = "logid") Integer logId,@RequestParam(name = "dbid") Integer dbid){
        tBackupLogService.delete(logId);
        return "redirect:/bakLogList?dbid="+dbid;
    }


    /**
     * @description:  添加或修改数据源
     * @param: [dbid] //添加时id置为null
     * @return: ModelAndView
     * @author: seenow
     * @date: 2021-06-26 18:00:06
     */
    @RequestMapping(value="/editdb")
    public ModelAndView dbSourceEdit(@RequestParam(defaultValue = "0") int dbid) {
        ModelAndView modelAndView = new ModelAndView("dbconfig");
        if(dbid>0){
            modelAndView.addObject("tDbSource",tDbSourceService.findById(dbid));
        }else{
            TDbSource tDbSource = new TDbSource();
            tDbSource.setHostip("127.0.0.1");
            tDbSource.setPort(3306);
            tDbSource.setDbType("mysql5.x");
            tDbSource.setUsername("root");
            modelAndView.addObject("tDbSource",tDbSource);
            modelAndView.addObject("vaild","true");
        }
        return modelAndView;
    }

    /**
     * @description:  添加或修改备份策略
     * @param: [pid, hostip, dbname]
     * @return: ModelAndView
     * @author: seenow
     * @date: 2021-06-29 11:29:58
     */
    @RequestMapping(value="/policyEdit")
    public ModelAndView policyEdit(@RequestParam(defaultValue = "0") int pid,String hostip,String dbname,int dbid ) {
        ModelAndView modelAndView = new ModelAndView("policyconfig");
        if(pid>0){
            modelAndView.addObject("tPolicy",tPolicyService.findById(pid));
        }else{
            TPolicy tPolicy = new TPolicy();
            tPolicy.setCompleteBackup("1");  //默认全备
            tPolicy.setDelSign("0");
            tPolicy.setDbId(dbid);
            modelAndView.addObject("tPolicy",tPolicy);
        }
        modelAndView.addObject("hostip",hostip);
        modelAndView.addObject("dbname",dbname);
        return modelAndView;
    }

    /**
     * @description:  添加数据源
     * @param: []
     * @return: ModelAndView
     * @author: seenow
     * @date: 2021-06-25 22:00:59
     */
    @GetMapping("/addDbsource")
    public ModelAndView index(){
        tDbSource.setHostip("127.0.0.1");
        tDbSource.setPort(3306);
        tDbSource.setDbType("MySQL5.1");
        ModelAndView modelAndView = new ModelAndView("dbconfig");
        modelAndView.addObject("tDbSource",tDbSource);
        return modelAndView;
    }

    /**
     * @description:  保存数据源，并跳转到数据源列表页
     * @param: [tDbSource]
     * @return: String
     * @author: seenow
     * @date: 2021-06-25 22:00:16
     */
    @PostMapping(value="/dbSourceEdit", params="action=save")
    public ModelAndView save(@ModelAttribute("tDbSource") TDbSource tDbSource) {
        //判断是新建还是更新
        if(tDbSource.getId()!=null && tDbSource.getId() > 0){  //更新
            tDbSourceService.update(tDbSource);
        }else{  //新添加
            //添加的保存之前应该先检查一下数据源是否重复！
            if(tDbSourceService.findByHostAndDbname(tDbSource.getHostip(),tDbSource.getDbName())>0){
                ModelAndView modelAndView = new ModelAndView("dbconfig");
                modelAndView.addObject("tDbSource",tDbSource);
                modelAndView.addObject("connMsg","Sorry!该数据源已存在！");
                return modelAndView;
            }else{
                tDbSourceService.add(tDbSource);
            }
        }
        //return "redirect:/dblist";
        List<TDbSource> tDbSources = tDbSourceService.findAll();
        ModelAndView modelAndView = new ModelAndView("dblist");
        modelAndView.addObject("tDbSources",tDbSources);
        return modelAndView;
    }

    /**
     * @description:  测试数据源是否能正常连接
     * @param: [tDbSource]
     * @return: ModelAndView
     * @author: seenow
     * @date: 2021-06-25 21:24:56
     */
    @PostMapping(value="/dbSourceEdit", params="action=testconn")
    public ModelAndView testconn(@ModelAttribute("tDbSource") TDbSource tDbSource) {
        ModelAndView modelAndView = new ModelAndView("dbconfig");
        modelAndView.addObject("tDbSource",tDbSource);
        if (DbUtil.testDatasource(tDbSource)){
            modelAndView.addObject("vaild","false");
            modelAndView.addObject("connMsg","OK!连接成功!");
        }else{
            modelAndView.addObject("vaild","true");
            modelAndView.addObject("connMsg","Sorry!连接失败！");
        }
        return modelAndView;
    }

    /**
     * @description:  保存备份策略
     * @param: [tPolicy]
     * @return: ModelAndView
     * @author: seenow
     * @date: 2021-06-29 11:45:43
     */
    @PostMapping("/savePolicy")
    public ModelAndView savePolicy(@ModelAttribute("tPolicy") TPolicy tPolicy,@RequestParam("dbid") int dbid,@RequestParam("hostip") String hostip,@RequestParam("dbname") String dbname) {
        Boolean isOk = true;
        String errMsg = "";
        List<TPolicy> tPolicies=tPolicyService.findByDbId(dbid);
        //判断是新建还是更新
        if (tPolicy.getId() != null && tPolicy.getId() > 0) {  //更新
            //先判断下能不能更新
            tPolicies.removeIf(tPolicy1 -> tPolicy1.getId() == tPolicy.getId());
            errMsg = DbUtil.savePolicyCheck(tPolicy,tPolicies);
            if(errMsg.equals("ok")){
                tPolicyService.update(tPolicy);
            }else{
                isOk = false;
            }
        } else { //新添加
            errMsg = DbUtil.savePolicyCheck(tPolicy,tPolicies);
            if(errMsg.equals("ok")){
                tPolicyService.add(tPolicy);
            }else{//不能添加。。。。。。。
                isOk = false;
            }
        }

        if(isOk){
            ModelAndView modelAndView = new ModelAndView("policylist");
            modelAndView.addObject("tpolicies",tPolicyService.findByDbId(dbid));
            modelAndView.addObject("dbid",dbid);
            modelAndView.addObject("hostip",hostip);
            modelAndView.addObject("dbname",dbname);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("policyconfig");
            modelAndView.addObject("tPolicy",tPolicy);
            modelAndView.addObject("hostip",hostip);
            modelAndView.addObject("dbname",dbname);
            modelAndView.addObject("saveMsg",errMsg);
            return modelAndView;
        }
    }


    /**
     * @description:  根据dbid获取指定数据库下所有表名并填充到表单
     * @param: [model, dbid]
     * @return: String
     * @author: seenow
     * @date: 2021-06-29 11:08:43
     */
    @RequestMapping("/getTables")
    public String getTables(Model model,@RequestParam("dbid")int dbid,@RequestParam("tblName")String tblName){
        //根据dbid遍历指定数据库的所有表
        TDbSource tDbSource = tDbSourceService.findById(dbid);
        List<String> tablelist = DbUtil.getTables(tDbSource);
        model.addAttribute("tablelist",tablelist);
        System.out.println(tablelist.toString());
        model.addAttribute("tableName",tblName);
        System.out.println(tblName);
        return "policyconfig::tableName";     //devicelist是你的html文件
        //tableName是你要局部刷新的容器的名字
    }

    /**
     * @description:  获取指定数据表的所有字段
     * @param: [model, dbid, tblName]
     * @return: String
     * @author: seenow
     * @date: 2021-06-29 20:38:28
     */
    @RequestMapping("/getFields")
    public String getFields(Model model,@RequestParam("dbid")int dbid,@RequestParam("tblName")String tblName,@RequestParam("signfld")String signfld){
        //根据
        TDbSource tDbSource = tDbSourceService.findById(dbid);
        List<String> fieldList = DbUtil.getFields(tDbSource,tblName);
        model.addAttribute("fieldList",fieldList);
        model.addAttribute("signName",signfld);
        return "policyconfig::signfld";
    }

    /**
     * @description:  删除一条策略
     * @param: [policyId, dbid, hostip, dbname]
     * @return: ModelAndView
     * @author: seenow
     * @date: 2021-06-29 20:39:05
     */
    @RequestMapping("/delPolicy")
    public ModelAndView delPolicy(@RequestParam(name = "policyId") Integer policyId,@RequestParam("dbid") int dbid,@RequestParam("hostip") String hostip,@RequestParam("dbname") String dbname){
        tPolicyService.delete(policyId);
        return policesList(dbid,hostip,dbname);
    }

    //手动执行备份任务
    @RequestMapping("/exeSql")
    public ModelAndView exeSql(@RequestParam("dbid")int dbid) throws java.io.IOException, java.lang.InterruptedException{
        TDbSource tDbSource = tDbSourceService.findById(dbid);
        List<TPolicy>  tpolicies = tPolicyService.findByDbId(dbid);
        String fileName = new String();
        String conditionSql1 = new String();
        String cmdSql = new String();
        Boolean succesed = true;
        List<String> delSql = new ArrayList<>();
        String runMsg=new String();
        String logContent= new String();

        if (tpolicies.size()==1 && tpolicies.get(0).getCompleteBackup().equals("1")) {//全备
            fileName = tDbSource.getDbName() + "-" + DateUtils.parseCurrentDate("HHmmss");
            cmdSql = DbUtil.getCmdSql(tDbSource,"",fileName);
            log.info("开始完全备份" + tDbSource.getHostip() + "上的" + tDbSource.getDbName() + "数据库");
            log.info("执行语句：" + cmdSql);
            succesed = DbUtil.exeSql(cmdSql) ? true : false;
            runMsg = succesed ? "OK!,备份完成。" : "Sorry,备份失败！";
            log.info(runMsg);
            logContent = logContent + fileName + ".sql";
        }

        if(tpolicies.get(0).getCompleteBackup().equals("0")){  //条件导出
            log.info( "开始按条件导出" + tDbSource.getHostip() + "上的" + tDbSource.getDbName() + "数据库");
            for (TPolicy tPolicy : tpolicies) {
                //记录下需要被忽略的表
                conditionSql1 = conditionSql1
                        + " --ignore-table="
                        + tDbSource.getDbName()
                        + "."
                        + tPolicy.getTableName();
                if(tPolicy.getDelSign().equals("1")) {  //需要清除才清除
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
                cmdSql = DbUtil.getCmdSql(tDbSource,conditionSql2,fileName);

                log.info("执行语句：" + cmdSql);
                if(!DbUtil.exeSql(cmdSql)){
                    succesed = false;
                    log.warn("导出语句： " + cmdSql + "执行失败!");
                    break;
                }
            }
        }

        if(succesed && !conditionSql1.equals("")){  //当要导出的表都成功导出了
            log.info("按条件导出全部执行完毕!下面开始全表导出剩下的表......");
            fileName = "others" + "-" + DateUtils.parseCurrentDate("HHmmss");
            logContent = logContent + "、" + fileName + ".sql" ;
            cmdSql = DbUtil.getCmdSql(tDbSource,conditionSql1,fileName);//其余的表全部导出
            log.info("执行语句：" + cmdSql);
            succesed = DbUtil.exeSql(cmdSql) ? true : false;
        }

        runMsg = succesed ? "OK!,备份完成。" : "Sorry,备份剩余表失败！";
        log.info(runMsg);

        if(succesed && delSql.size()>0){     //导出成功了且有需要清除的表
            runMsg = "OK!,清除数据完成。";
            log.info( "下面开始清理过期数据......");
            for(String s : delSql){
                log.info("执行语句：" + s);
                if (!DbUtil.exeSql(DbUtil.getDelSql(tDbSource,s))){
                    runMsg = "Sorry,备份成功，但清理数据失败！失败语句： " + s ;
                    break;
                }
            }
        }

        //保存日志
        TBackupLog tBackupLog = new TBackupLog();
        tBackupLog.setDbid(tDbSource.getId());
        tBackupLog.setCreater("管理员");
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

        ModelAndView modelAndView = new ModelAndView("policylist");
        modelAndView.addObject("tpolicies",tPolicyService.findByDbId(dbid));
        modelAndView.addObject("dbid",dbid);
        modelAndView.addObject("hostip",tDbSource.getHostip());
        modelAndView.addObject("dbname",tDbSource.getDbName());
        modelAndView.addObject("runMsg",runMsg);
        return modelAndView;
    }

    /**
     * @description:  xxl-job调用接口
     * @param: [dbid]
     * @return: void
     * @author: seenow
     * @date: 2021-07-05 23:02:30
     */
    public void xxlJobApi(@RequestParam("dbid")int dbid) throws java.io.IOException, java.lang.InterruptedException {
        TDbSource tDbSource = tDbSourceService.findById(dbid);
        List<TPolicy> tpolicies = tPolicyService.findByDbId(dbid);
        String fileName = new String();
        String conditionSql1 = new String();
        String cmdSql = new String();
        Boolean succesed = true;
        List<String> delSql = new ArrayList<>();
        String runMsg = new String();

        if (tpolicies.size() == 1 && tpolicies.get(0).getCompleteBackup().equals("1")) {//全备
            fileName = tDbSource.getDbName();
            cmdSql = DbUtil.getCmdSql(tDbSource, "", fileName);
            succesed = DbUtil.exeSql(cmdSql) ? true : false;
            runMsg = succesed ? "OK!,备份完成。" : "Sorry,备份失败！";
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
                fileName = tPolicy.getTableName();
                cmdSql = DbUtil.getCmdSql(tDbSource, conditionSql2, fileName);

                if (!DbUtil.exeSql(cmdSql)) {
                    succesed = false;
                    break;
                }
            }
        }

        if (succesed && !conditionSql1.equals("")) {  //当要导出的表都成功导出了
            fileName = "others";
            cmdSql = DbUtil.getCmdSql(tDbSource, conditionSql1, fileName);//其余的表全部导出
            succesed = DbUtil.exeSql(cmdSql) ? true : false;
        }

        runMsg = succesed ? "OK!,备份完成。" : "Sorry,备份失败！";

        if (succesed && delSql.size() > 0) {     //导出成功了且有需要清除的表
            runMsg = "OK!,备份完成,清除数据完成。";
            for (String s : delSql) {
                if (!DbUtil.exeSql(DbUtil.getDelSql(tDbSource, s))) {
                    runMsg = "Sorry,备份成功，但清理数据失败！";
                    break;
                }
            }
        }
    }


}
