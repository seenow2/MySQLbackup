package com.seenow.dbbackup.service;

import com.github.pagehelper.PageInfo;
import com.seenow.dbbackup.pojo.TBackupLog;

import java.util.List;

/****
 * @Author: https://blog.csdn.net/pkxwyf
 * @Description:TBackupLog业务层接口
 *****/
public interface TBackupLogService {

    /***
     * 多条件分页查询TBackupLog表数据
     * @param tBackupLog
     * @param page
     * @param size
     * @return
     */
    PageInfo<TBackupLog> findPage(TBackupLog tBackupLog, int page, int size);

    /***
     * 分页查询TBackupLog表数据
     * @param page
     * @param size
     * @return
     */
    PageInfo<TBackupLog> findPage(int page, int size);

    /***
     * 多条件搜索TBackupLog表数据
     * @param tBackupLog
     * @return
     */
    List<TBackupLog> findList(TBackupLog tBackupLog);

    /***
     * 根据id删除TBackupLog表数据
     * @param id
     */
    void delete(Integer id);

    /***
     * 根据条件修改TBackupLog表数据
     * @param tBackupLog
     */
    void update(TBackupLog tBackupLog);

    /***
     * 新增TBackupLog表数据
     * @param tBackupLog
     */
    void add(TBackupLog tBackupLog);

    /**
     * 根据ID查询TBackupLog表数据
     * @param id
     * @return
     */
     TBackupLog findById(Integer id);

    /***
     * 查询所有TBackupLog表数据
     * @return
     */
    List<TBackupLog> findAll();

    List<TBackupLog> findByDbId(int dbId);

    void deleteByDbId(int dbId);
}
