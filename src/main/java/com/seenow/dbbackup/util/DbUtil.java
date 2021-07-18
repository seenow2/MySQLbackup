package com.seenow.dbbackup.util;


import com.seenow.dbbackup.pojo.TDbSource;
import com.seenow.dbbackup.pojo.TPolicy;
import com.seenow.dbbackup.service.TPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
//import com.shucha.deveiface.biz.model.DbSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DbUtil
 * @Description TODO
 * @@Create 2021-06-24 23:53
 * @Created by seenow
 */
public class DbUtil<dbType> {

    @Autowired
    TPolicyService tPolicyService;

    /**
     * @description: 测试数据源连接是否有效
     * @param: [driveClass, url, username, password]
     * @return: boolean
     * @author: seenow
     * @date: 2021-06-24 23:56:49
     */
   public static boolean testDatasource(TDbSource tDbSource){

       String jdbcUrl=new String();
       String driveClass = new String();

       switch (tDbSource.getDbType()) {
           case "MySQL":
               // mysql高版本数据库
               driveClass = "com.mysql.cj.jdbc.Driver";
               jdbcUrl = "jdbc:mysql://" + tDbSource.getPort() + "/" + tDbSource.getDbName()
                       + "&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
               break;
           case "mysql5.x":
               // mysql高版本数据库
               driveClass = "com.mysql.jdbc.Driver";
               jdbcUrl = "jdbc:mysql://"
                       + tDbSource.getHostip() + ":"
                       + tDbSource.getPort() + "/" + tDbSource.getDbName()
                       + "?useSSL=false&useUnicode=true&characterEncoding=utf8";
               break;
           case "Oracle":
               // oracle数据库
               driveClass = "oracle.jdbc.OracleDriver";
               //jdbcUrl = "jdbc:oracle:thin:@" + dbIpPort + ":" + dbName;
               break;
           default:
               // PostgreSQL数据库
               driveClass = "org.postgresql.Driver";
               //jdbcUrl = "jdbc:postgresql://" + dbIpPort + "/" + dbName;
       }

       try {
           System.out.println(jdbcUrl);
           Class.forName(driveClass);
           DriverManager.getConnection(jdbcUrl, tDbSource.getUsername(), tDbSource.getPassword());
           return true;
       } catch (Exception e) {
           System.out.println(e.getMessage());
           return false;
       }
    }


    /**
     * @description:  返回一个数据库的有效-连接
     * @param: [tDbSource]
     * @return: Connection
     * @author: seenow
     * @date: 2021-06-28 16:02:35
     */
    public static Connection getConn(TDbSource tDbSource){

        String jdbcUrl=new String();
        String driveClass = new String();

        switch (tDbSource.getDbType()) {
            case "MySQL":
                // mysql高版本数据库
                driveClass = "com.mysql.cj.jdbc.Driver";
                jdbcUrl = "jdbc:mysql://" + tDbSource.getPort() + "/" + tDbSource.getDbName()
                        + "&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
                break;
            case "mysql5.x":
                // mysql高版本数据库
                driveClass = "com.mysql.jdbc.Driver";
                jdbcUrl = "jdbc:mysql://"
                        + tDbSource.getHostip() + ":"
                        + tDbSource.getPort() + "/" + tDbSource.getDbName()
                        + "?useSSL=false&useUnicode=true&characterEncoding=utf8";
                break;
            case "Oracle":
                // oracle数据库
                driveClass = "oracle.jdbc.OracleDriver";
                //jdbcUrl = "jdbc:oracle:thin:@" + dbIpPort + ":" + dbName;
                break;
            default:
                // PostgreSQL数据库
                driveClass = "org.postgresql.Driver";
                //jdbcUrl = "jdbc:postgresql://" + dbIpPort + "/" + dbName;
        }

        try {
            System.out.println(jdbcUrl);
            Class.forName(driveClass);
            return DriverManager.getConnection(jdbcUrl, tDbSource.getUsername(), tDbSource.getPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * @description:  获取一个数据库的所有表名
     * @param: [tDbSource]
     * @return: void
     * @author: seenow
     * @date: 2021-06-28 16:26:53
     */
    public static List<String> getTables(TDbSource tDbSource) {
        Connection conn = getConn(tDbSource);
        //要处理一下返回null的情况 。。。。
        List<String>  tableList= new ArrayList<>();
        try{
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(conn.getCatalog(), tDbSource.getUsername(), null, new String[]{"TABLE"});

            while(rs.next()) {
                tableList.add(rs.getString("TABLE_NAME"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return tableList;
    }

    public static List<String> getFields(TDbSource tDbSource,String tblName) {
        Connection conn = getConn(tDbSource);
        //要处理一下返回null的情况 。。。。
        List<String>  fieldList= new ArrayList<>();
        try{
            DatabaseMetaData metaData = conn.getMetaData();
           // ResultSet rs = metaData.getTables(conn.getCatalog(), tDbSource.getUsername(), null, new String[]{"TABLE"});
            ResultSet rs = metaData.getColumns(conn.getCatalog(), null, tblName, "%");
            while(rs.next()) {//只显示日期时间型字段
                if(rs.getString("TYPE_NAME").equals("DATE") || rs.getString("TYPE_NAME").equals("DATETIME")){
                    fieldList.add(rs.getString("COLUMN_NAME"));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return fieldList;
    }

    public static String getCmdSql(TDbSource tDbSource,String conditionSql,String fileName){

        String savePath = "/databak/"
                + new SimpleDateFormat("yyyyMMdd").format(new java.util.Date())
                + "/" + tDbSource.getHostip()
                + "/" + tDbSource.getDbName();
        File file = new java.io.File(savePath);
        if(!file.exists()){
            file.mkdirs();
        }else{
           /* if(!file.isDirectory()){
                file.delete();
                file.mkdirs();
            }*/
        }
        String saveFile = savePath + "/" + fileName +".sql";

        String cmdSql = getDbToolPath(tDbSource.getDbType());

        cmdSql = cmdSql + "mysqldump --default-character-set=utf8 --single-transaction "
                + " -h " + tDbSource.getHostip()
                + " -P " + tDbSource.getPort()
                + " -t " + tDbSource.getDbName()
                + " -u" + tDbSource.getUsername()
                + " -p" + tDbSource.getPassword()
                + " " + conditionSql
                + ">"+ saveFile;
        return cmdSql;
    }

    //清除表数据命令字符串
    public static String getDelSql(TDbSource tDbSource,String delString){

        String cmdSql = getDbToolPath(tDbSource.getDbType()) + "mysql  "
                + " -h " + tDbSource.getHostip()
                + " -P " + tDbSource.getPort()
                + " -u" + tDbSource.getUsername()
                + " -p" + tDbSource.getPassword()
                + " -e\"" + delString
                + "\"";
        return cmdSql;
    }

    public static Boolean exeSql(String cmdSql)  {
        Boolean succesed = false;
        try {
            // 运行导出SQL命令
            String os = System.getProperty("os.name"); // 系统名称
            if (os.toLowerCase().startsWith("win")) {

                /*String line = "";
                StringBuilder sb = new StringBuilder();*/
                System.out.println(cmdSql);

                Process process = Runtime.getRuntime().exec(new String[] { "cmd", "/c", cmdSql }); // windows

                /*try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                Runtime.getRuntime().exec(new String[] { "cmd", "/c", cmdSql }).getInputStream()));)
                {
                    while ((line = bufferedReader.readLine()) != null)
                        sb.append(line + "\r\n");
                } catch (IOException e) {
                    System.out.println(e.getMessage());  }
                System.out.println(sb.toString());*/

                succesed = process.waitFor()==0 ? true : false;
            } else if (os.toLowerCase().startsWith("linux")) {
                Process process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", cmdSql }); // linux
                //process.waitFor();
                succesed = process.waitFor()==0 ? true : false;
            }
        } catch (java.io.IOException e) {
            succesed = false ;
            e.printStackTrace();
            throw e;
        } catch (java.lang.InterruptedException e) {
            succesed = false ;
            e.printStackTrace();
            throw e;
        }finally{
            return succesed;
        }
    }

    /**
     * @description:  获取mysqldump命令的目录
     * @param: [dbType]
     * @return: String
     * @author: seenow
     * @date: 2021-07-04 22:27:51
     */
    public static String getDbToolPath(String dbType){
        File dictory = new File(".");
        try {
            String cmdPath = dictory.getCanonicalPath(); //程序主目录绝对路径
            String os = System.getProperty("os.name"); // 系统名称
            if (os.toLowerCase().startsWith("win")){
                cmdPath = cmdPath + "\\dbtools\\windows\\"+ dbType + "\\";
            }else if (os.toLowerCase().startsWith("linux")) {
                cmdPath = cmdPath + "/dbtools/linux/"+ dbType + "/";
            }
            return cmdPath;
        }catch (IOException e){
            e.getStackTrace();
            return "";
        }
    }

    /**
     * @description:  保存策略之前先检查一下有效性
     * @param: [tPolicy, tPolicies]
     * @return: String
     * @author: seenow
     * @date: 2021-07-06 14:55:49
     */
    public static String savePolicyCheck(TPolicy tPolicy,List<TPolicy> tPolicies){
        String errMsg = "ok";

        if(tPolicies.size()>0){
            if(tPolicy.getCompleteBackup().equals("1")){
                errMsg = "错误！当策略为全备时同一数据源上只应有一条备份策略。";
            }else{
                for(TPolicy t : tPolicies ){
                    if(t.getTableName().equals(tPolicy.getTableName())){
                        errMsg = "错误！对同一数据源的同一表设置了多于一条的备份策略。";
                        break;
                    }
                }
            }
        }

        if(tPolicy.getCompleteBackup().equals("0")  && (tPolicy.getTableName()==null || tPolicy.getTableName().equals(""))){
            errMsg = "错误！当策略为部分导出时应指定表名。";
        }

        if(tPolicy.getCompleteBackup().equals("0")  && (tPolicy.getSignfld()==null || tPolicy.getSignfld().equals(""))){
            errMsg = "错误！当策略为部分导出时应指定字段名。";
        }

        return errMsg;
    }



}
