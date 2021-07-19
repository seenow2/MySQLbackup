package com.seenow.dbbackup.service;
import com.seenow.dbbackup.pojo.TDbSource;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author: https://blog.csdn.net/pkxwyf
 * @Description:TDbSource业务层接口
 *****/
public interface TDbSourceService {

    /***
     * 多条件分页查询TDbSource表数据
     * @param tDbSource
     * @param page
     * @param size
     * @return
     */
    PageInfo<TDbSource> findPage(TDbSource tDbSource, int page, int size);

    /***
     * 分页查询TDbSource表数据
     * @param page
     * @param size
     * @return
     */
    PageInfo<TDbSource> findPage(int page, int size);

    /***
     * 多条件搜索TDbSource表数据
     * @param tDbSource
     * @return
     */
    List<TDbSource> findList(TDbSource tDbSource);

    /***
     * 根据id删除TDbSource表数据
     * @param id
     */
    void delete(Integer id);

    /***
     * 根据条件修改TDbSource表数据
     * @param tDbSource
     */
    void update(TDbSource tDbSource);

    /***
     * 新增TDbSource表数据
     * @param tDbSource
     */
    void add(TDbSource tDbSource);

    /**
     * 根据ID查询TDbSource表数据
     * @param id
     * @return
     */
     TDbSource findById(Integer id);

    /***
     * 查询所有TDbSource表数据
     * @return
     */
    List<TDbSource> findAll();

    /**
     * @description:  根据机器ip与数据库名称查找记录
     * @param: [hostip, dbname]
     * @return: Integer
     * @author: seenow
     * @date: 2021-06-26 18:16:44
     */
    Integer findByHostAndDbname(String hostip,String dbname);
}
