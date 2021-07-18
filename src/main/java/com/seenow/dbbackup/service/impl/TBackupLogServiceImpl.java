package com.seenow.dbbackup.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seenow.dbbackup.dao.TBackupLogMapper;
import com.seenow.dbbackup.pojo.TBackupLog;
import com.seenow.dbbackup.service.TBackupLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author: PKXING
 * @Description:TBackupLog业务层接口实现类
 *****/
@Service
public class TBackupLogServiceImpl implements TBackupLogService {

    // 注入持久层对象
    @Autowired
    private TBackupLogMapper tBackupLogMapper;

    /**
     * 条件+分页查询TBackupLog表数据
     * @param tBackupLog 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<TBackupLog> findPage(TBackupLog tBackupLog, int page, int size){
        // 分页
        PageHelper.startPage(page,size);
        // 搜索条件构建
        Example example = createExample(tBackupLog);
        // 执行搜索
        return new PageInfo<TBackupLog>(tBackupLogMapper.selectByExample(example));
    }

    /**
     * 分页查询TBackupLog表数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<TBackupLog> findPage(int page, int size){
        // 静态分页
        PageHelper.startPage(page,size);
        // 分页查询
        return new PageInfo<TBackupLog>(tBackupLogMapper.selectAll());
    }

    /**
     * 条件查询TBackupLog表数据
     * @param tBackupLog
     * @return
     */
    @Override
    public List<TBackupLog> findList(TBackupLog tBackupLog){
        // 构建查询条件
        Example example = createExample(tBackupLog);
        // 根据构建的条件查询数据
        return tBackupLogMapper.selectByExample(example);
    }


    /**
     * TBackupLog构建查询对象
     * @param tBackupLog
     * @return
     */
    public Example createExample(TBackupLog tBackupLog){
        Example example=new Example(TBackupLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(tBackupLog!=null){
            //
            if(!StringUtils.isEmpty(tBackupLog.getId())){
                    criteria.andEqualTo("id",tBackupLog.getId());
            }
            //
            if(!StringUtils.isEmpty(tBackupLog.getDbid())){
                    criteria.andEqualTo("dbid",tBackupLog.getDbid());
            }
            //
            if(!StringUtils.isEmpty(tBackupLog.getCreater())){
                    criteria.andEqualTo("creater",tBackupLog.getCreater());
            }
            //
            if(!StringUtils.isEmpty(tBackupLog.getCreateTime())){
                    criteria.andEqualTo("createTime",tBackupLog.getCreateTime());
            }
            //
            if(!StringUtils.isEmpty(tBackupLog.getContent())){
                    criteria.andEqualTo("content",tBackupLog.getContent());
            }
        }
        return example;
    }

    /**
     * 根据id删除TBackupLog表数据
     * @param id
     */
    @Override
    public void delete(Integer id){
        tBackupLogMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改TBackupLog表数据
     * @param tBackupLog
     */
    @Override
    public void update(TBackupLog tBackupLog){
        tBackupLogMapper.updateByPrimaryKey(tBackupLog);
    }

    /**
     * 增加TBackupLog表数据
     * @param tBackupLog
     */
    @Override
    public void add(TBackupLog tBackupLog){
        tBackupLogMapper.insert(tBackupLog);
    }

    /**
     * 根据ID查询TBackupLog表数据
     * @param id
     * @return
     */
    @Override
    public TBackupLog findById(Integer id){
        return  tBackupLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询TBackupLog表全部数据
     * @return
     */
    @Override
    public List<TBackupLog> findAll() {
        return tBackupLogMapper.selectAll();
    }

    @Override
    public List<TBackupLog> findByDbId(int dbId){ return tBackupLogMapper.findByDbId(dbId); }

    @Override
    public void deleteByDbId(int dbId){
        tBackupLogMapper.deleteByDbId(dbId);
    }
}
