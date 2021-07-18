package com.seenow.dbbackup.service;
import com.seenow.dbbackup.pojo.TPolicy;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author: https://blog.csdn.net/pkxwyf
 * @Description:TPolicy业务层接口
 *****/
public interface TPolicyService {

    /***
     * 多条件分页查询TPolicy表数据
     * @param tPolicy
     * @param page
     * @param size
     * @return
     */
    PageInfo<TPolicy> findPage(TPolicy tPolicy, int page, int size);

    /***
     * 分页查询TPolicy表数据
     * @param page
     * @param size
     * @return
     */
    PageInfo<TPolicy> findPage(int page, int size);

    /***
     * 多条件搜索TPolicy表数据
     * @param tPolicy
     * @return
     */
    List<TPolicy> findList(TPolicy tPolicy);

    /***
     * 根据id删除TPolicy表数据
     * @param id
     */
    void delete(Integer id);

    /***
     * 根据条件修改TPolicy表数据
     * @param tPolicy
     */
    void update(TPolicy tPolicy);

    /***
     * 新增TPolicy表数据
     * @param tPolicy
     */
    void add(TPolicy tPolicy);

    /**
     * 根据ID查询TPolicy表数据
     * @param id
     * @return
     */
     TPolicy findById(Integer id);

    /***
     * 查询所有TPolicy表数据
     * @return
     */
    List<TPolicy> findAll();

    /**
     * @description:  根据dbid找出同一数据库下所有的策略
     * @param: [dbId]
     * @return: TPolicy
     * @author: seenow
     * @date: 2021-06-26 22:48:07
     */
    List<TPolicy> findByDbId(int dbId);

    void deleteByDbId(int dbId);

    int findPlcNumByDbId(int dbId);

    int findByDbidAndTableName(int dbId,String tableName);

 }
