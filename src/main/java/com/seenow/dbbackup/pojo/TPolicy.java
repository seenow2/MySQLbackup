package com.seenow.dbbackup.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author: https://blog.csdn.net/pkxwyf
 * @Description:TPolicy构建
 *****/
@Data
@ApiModel(description = "TPolicy",value = "TPolicy")
@Table(name="t_policy")
public class TPolicy implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "policyName")
	private String policyName;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "dbid")
	private Integer dbId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "completeBackup")
	private String completeBackup;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "tableName")
	private String tableName;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "signfld")
	private String signfld;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "daysLen")
	private Integer daysLen;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "delSign")
	private String delSign;//

}
