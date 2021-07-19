CREATE DATABASE IF NOT EXISTS dbbackup DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

/*表一：数据源表  t_db_source  */
create table IF NOT EXISTS t_db_source(
     id int(4) primary key not null auto_increment,
     hostip varchar(16) not null,
	 port smallint not null,
	 dbname varchar(30) not null,
	 dbType varchar(30) not null Default "MySQL",
	 username varchar(16) not null,
     password varchar(32) 
     )CHARACTER SET utf8 COLLATE utf8_general_ci;

/*表二：备份策略表  t_policy*/
drop table if exists t_policy
create table IF NOT EXISTS t_policy(
     id int(4) primary key not null auto_increment,
	 dbid int(4) not null,
     policyName varchar(50) not null,
     completeBackup  tinyint(1) not null DEFAULT 0,
     tableName varchar(50),
	 signfld varchar(30) ,
	 daysLen int ,
	 delSign tinyint(1) not null DEFAULT 0
     )CHARACTER SET utf8 COLLATE utf8_general_ci;


/*表三：备份日志表*/
create table IF NOT EXISTS t_backup_log(
     id int(4) primary key not null auto_increment,
     dbid int(4) not null,
	 succeed varchar(4) not null,
	 creater varchar(30) not null,
	 createTime DATETIME not null,
	 content varchar(1000) not null
     )CHARACTER SET utf8 COLLATE utf8_general_ci;
