package com.seenow.dbbackup.dao;
import com.seenow.dbbackup.pojo.TPolicy;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author: PKXING
 * @Description:TPolicy的Dao
 *****/
public interface TPolicyMapper extends Mapper<TPolicy> {

    //查找同一数据源上的所有备份策略
    @Select("select * from t_policy "
            + " where dbid="
            + "#{dbId}" )
    List<TPolicy> findByDbId(int dbId);

    //查找同一数据源上的所有备份策略数目
    @Select("select count(*) from t_policy "
            + " where dbid="
            + "#{dbId}" )
    int findPlcNumByDbId(int dbId);

    //根据dbId删除所有备份策略
    @Select("delete from  t_policy"
            + " where dbid="
            + "#{dbId}" )
    void deleteByDbId(int dbId);

    //根据数据源与表名查找策略条数
    @Select("Select count(*) from t_policy "
            + " where dbid="
            + "#{dbId}"
            + " and tableName="
            + "#{tableName}")
    int findByDbidAndTableName(int dbId,String tableName);

}
