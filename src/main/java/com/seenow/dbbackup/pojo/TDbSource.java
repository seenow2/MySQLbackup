package com.seenow.dbbackup.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author: https://blog.csdn.net/pkxwyf
 * @Description:TDbSource构建
 *****/
@Component
@Data
@ApiModel(description = "TDbSource",value = "TDbSource")
@Table(name="t_db_source")
public class TDbSource implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "hostip")
	private String hostip;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "port")
	private Integer port;//

	@ApiModelProperty(value = "",required = false)
	@Column(name = "dbName")
	private String dbName;//

	@ApiModelProperty(value = "",required = false)
	@Column(name = "dbType")
	private String dbType;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "username")
	private String username;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "password")
	private String password;//

	public TDbSource(){}

	public String toString(){
		return "hostip: " + hostip
				+  ", Port: " +  port.toString()
				+ ", DbName: " + dbName
		        + ", DbType: " + dbType
				+ ", Username: " + username
				+ ", password: " + password;
	}


}
