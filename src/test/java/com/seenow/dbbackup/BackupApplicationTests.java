package com.seenow.dbbackup;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.seenow.dbbackup.pojo.TDbSource;
import com.seenow.dbbackup.pojo.TPolicy;
import com.seenow.dbbackup.service.TDbSourceService;
import com.seenow.dbbackup.service.TPolicyService;
import com.seenow.dbbackup.util.DateUtils;
import com.seenow.dbbackup.util.DbUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BackupApplicationTests {

	@Autowired
	TDbSource tDbSource;
	@Autowired
	TDbSourceService tDbSourceService;
	@Autowired
	TPolicyService tPolicyService;

	@Test
	void contextLoads() {
	}


	/*@Test
	void test1(){
		*//*tDbSource.setHostip("127.0.0.1");
		tDbSource.setPort(3306);
		tDbSource.setDbName("dbbackup");
		tDbSource.setDbType("MySQL5.1");
		tDbSource.setUsername("root");
		tDbSource.setPassword("root");*//*
		//tDbSourceService.add(tDbSource);
		//System.out.println(tDbSource.getId());
		//DbUtil.testDatasource(tDbSource);
		//DbUtil.getTables(tDbSource);
		tDbSource = tDbSourceService.findById(7);
		DbUtil.getFields(tDbSource,"person");
	}

	@Test
	void test2(){
		tDbSourceService.delete(1);
		//System.out.println(tDbSourceService.findList().toString());
	}

	@Test
	void test3(){
		List<TDbSource> t =  tDbSourceService.findAll();

		for(TDbSource t1:t){
			System.out.println(t.toString());
		}
	}

	@Test
	void test4(){
		tDbSource.setHostip("127.0.0.1");
		tDbSource.setPort(3306);
		tDbSource.setDbName("dbbackup");
		tDbSource.setDbType("MySQL5.1");
		tDbSource.setUsername("root");
		tDbSource.setPassword("root");
		if (DbUtil.testDatasource(tDbSource)){
			System.out.println("ok");
		}





	}

	@Test
	void test5(){

		Integer a = tDbSourceService.findByHostAndDbname("127.0.0.1","test");
		System.out.println(a);
		a = tDbSourceService.findByHostAndDbname("127.0.0.1","test1");
		System.out.println(a);
	}

	@Test
	void  test6(){
		//TPolicy(id=null, policyName=eee, dbId=7, completeBackup=1, tableName=null, signfld=, daysLen=null, delSign=0)
		TPolicy tPolicy=new TPolicy();
		tPolicy.setPolicyName("ddd");
		tPolicy.setDbId(7);
		tPolicy.setCompleteBackup("1");
		tPolicy.setSignfld("");
		tPolicy.setDelSign("0");
		tPolicyService.add(tPolicy);

	}

	@Test
	void test7(){
		*//*List<String> ss=new ArrayList<String>();
		ss.add("apple");
		ss.add("balana");
		System.out.println(ss.contains("apple"));
*//*
		List<TPolicy> t = tPolicyService.findByDbId(2);
		TPolicy tPolicy= new TPolicy();
		tPolicy.setTableName("t_policy");
		System.out.println(t.toString());
		System.out.println(t.contains(tPolicy));


	}

	@Test
	void test8() throws IOException {
		//String aa="D:\\springboot-dbbackup-master\\dbtools\\windows\\mysql5.x\\mysqldump --default-character-set=utf8 --single-transaction  -h 127.0.0.1 -P 3306 -t test -uroot -proot  --tables person --where=\"birthday<DATE_SUB(CURDATE(), INTERVAL 60 DAY)\" >/databak/20210704/test/person.sql";
        String aa = "D:\\springboot-dbbackup-master\\dbtools\\windows\\mysql5.x\\mysql   -h 127.0.0.1 -P 3306 -uroot -proot -e'delete from test.person where birthday<DATE_SUB(CURDATE(), INTERVAL 60 DAY)'";

		System.out.println("Result: " + DbUtil.exeSql(aa));
	}

	@Test
	void test9(){
		System.out.println(DateUtils.parseCurrentDate("HHmmss"));

	}*/

}
