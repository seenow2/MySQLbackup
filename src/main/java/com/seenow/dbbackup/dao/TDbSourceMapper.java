package com.seenow.dbbackup.dao;
import com.seenow.dbbackup.pojo.TDbSource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author: PKXING
 * @Description:TDbSource的Dao
 *****/
public interface TDbSourceMapper extends Mapper<TDbSource> {

    @Select("select count(*) from t_db_source where "
            + " hostip = #{hostip} and "
            + " dbname = #{dbname}")
    Integer findByHostAndDbname(@Param("hostip") String hostip,@Param("dbname")  String dbname);


}
