package com.seenow.dbbackup;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.seenow.dbbackup.dao")
@SpringBootApplication
public class BackupApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackupApplication.class, args);
	}

}
