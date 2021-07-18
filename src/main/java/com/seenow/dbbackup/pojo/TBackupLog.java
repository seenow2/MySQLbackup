package com.seenow.dbbackup.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author: https://blog.csdn.net/pkxwyf
 * @Description:TBackupLog构建
 *****/
@Data
@ApiModel(description = "TBackupLog",value = "TBackupLog")
@Table(name="t_backup_log")
public class TBackupLog implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "dbid")
	private Integer dbid;//

	@ApiModelProperty(value = "",required = false)
	@Column(name = "succeed")
	private String succeed;//


	@ApiModelProperty(value = "",required = false)
    @Column(name = "creater")
	private String creater;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "createTime")
	private Date createTime;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "content")
	private String content;//

}
