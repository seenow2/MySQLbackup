package com.seenow.dbbackup.dao;
import com.seenow.dbbackup.pojo.TBackupLog;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author: PKXING
 * @Description:TBackupLog的Dao
 *****/
public interface TBackupLogMapper extends Mapper<TBackupLog> {

    @Select("select * from  t_backup_log"
            + " where dbid="
            + "#{dbId}" )
    List<TBackupLog> findByDbId(int dbId);

    //deleteByDbId
    @Select("delete from  t_backup_log"
            + " where dbid="
            + "#{dbId}" )
    void deleteByDbId(int dbId);

}
