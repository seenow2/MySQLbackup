package com.seenow.dbbackup.service.impl;
import com.seenow.dbbackup.dao.TPolicyMapper;
import com.seenow.dbbackup.pojo.TPolicy;
import com.seenow.dbbackup.service.TPolicyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author: PKXING
 * @Description:TPolicy业务层接口实现类
 *****/
@Service
public class TPolicyServiceImpl implements TPolicyService {

    // 注入持久层对象
    @Autowired
    private TPolicyMapper tPolicyMapper;


    /**
     * 条件+分页查询TPolicy表数据
     * @param tPolicy 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<TPolicy> findPage(TPolicy tPolicy, int page, int size){
        // 分页
        PageHelper.startPage(page,size);
        // 搜索条件构建
        Example example = createExample(tPolicy);
        // 执行搜索
        return new PageInfo<TPolicy>(tPolicyMapper.selectByExample(example));
    }

    /**
     * 分页查询TPolicy表数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<TPolicy> findPage(int page, int size){
        // 静态分页
        PageHelper.startPage(page,size);
        // 分页查询
        return new PageInfo<TPolicy>(tPolicyMapper.selectAll());
    }

    /**
     * 条件查询TPolicy表数据
     * @param tPolicy
     * @return
     */
    @Override
    public List<TPolicy> findList(TPolicy tPolicy){
        // 构建查询条件
        Example example = createExample(tPolicy);
        // 根据构建的条件查询数据
        return tPolicyMapper.selectByExample(example);
    }


    /**
     * TPolicy构建查询对象
     * @param tPolicy
     * @return
     */
    public Example createExample(TPolicy tPolicy){
        Example example=new Example(TPolicy.class);
        Example.Criteria criteria = example.createCriteria();
        if(tPolicy!=null){
            //
            if(!StringUtils.isEmpty(tPolicy.getId())){
                    criteria.andEqualTo("id",tPolicy.getId());
            }
            //
            if(!StringUtils.isEmpty(tPolicy.getPolicyName())){
                    criteria.andEqualTo("policyName",tPolicy.getPolicyName());
            }
            //
            if(!StringUtils.isEmpty(tPolicy.getDbId())){
                    criteria.andEqualTo("dbSourceId",tPolicy.getDbId());
            }
            //
            /*if(!StringUtils.isEmpty(tPolicy.getDbName())){
                    criteria.andEqualTo("dbName",tPolicy.getDbName());
            }*/
            //
            if(!StringUtils.isEmpty(tPolicy.getCompleteBackup())){
                    criteria.andEqualTo("completeBackup",tPolicy.getCompleteBackup());
            }
            //
            if(!StringUtils.isEmpty(tPolicy.getTableName())){
                    criteria.andEqualTo("tableName",tPolicy.getTableName());
            }
            //
            if(!StringUtils.isEmpty(tPolicy.getSignfld())){
                    criteria.andEqualTo("signfld",tPolicy.getSignfld());
            }
            //
            if(!StringUtils.isEmpty(tPolicy.getDaysLen())){
                    criteria.andEqualTo("daysLen",tPolicy.getDaysLen());
            }
            //
            if(!StringUtils.isEmpty(tPolicy.getDelSign())){
                    criteria.andEqualTo("delSign",tPolicy.getDelSign());
            }
        }
        return example;
    }

    /**
     * 根据id删除TPolicy表数据
     * @param id
     */
    @Override
    public void delete(Integer id){
        tPolicyMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改TPolicy表数据
     * @param tPolicy
     */
    @Override
    public void update(TPolicy tPolicy){
        tPolicyMapper.updateByPrimaryKey(tPolicy);
    }

    /**
     * 增加TPolicy表数据
     * @param tPolicy
     */
    @Override
    public void add(TPolicy tPolicy){
        tPolicyMapper.insert(tPolicy);
    }

    /**
     * 根据ID查询TPolicy表数据
     * @param id
     * @return
     */
    @Override
    public TPolicy findById(Integer id){
        return  tPolicyMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询TPolicy表全部数据
     * @return
     */
    @Override
    public List<TPolicy> findAll() {
        return tPolicyMapper.selectAll();
    }

    @Override
    public List<TPolicy> findByDbId(int dbId){
        return tPolicyMapper.findByDbId(dbId);
    }

    @Override
    public void deleteByDbId(int dbId){
        tPolicyMapper.deleteByDbId(dbId);
    }

    @Override
    public int findByDbidAndTableName(int dbId,String tableName){
        return tPolicyMapper.findByDbidAndTableName(dbId, tableName);
    }

    @Override
    public int findPlcNumByDbId(int dbId){
        return tPolicyMapper.findPlcNumByDbId(dbId);
    }

}
