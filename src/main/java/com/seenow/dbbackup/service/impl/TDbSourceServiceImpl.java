package com.seenow.dbbackup.service.impl;
import com.seenow.dbbackup.dao.TDbSourceMapper;
import com.seenow.dbbackup.pojo.TDbSource;
import com.seenow.dbbackup.service.TDbSourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author: PKXING
 * @Description:TDbSource业务层接口实现类
 *****/
@Service
public class TDbSourceServiceImpl implements TDbSourceService {

    // 注入持久层对象
    @Autowired
    private TDbSourceMapper tDbSourceMapper;


    /**
     * 条件+分页查询TDbSource表数据
     * @param tDbSource 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<TDbSource> findPage(TDbSource tDbSource, int page, int size){
        // 分页
        PageHelper.startPage(page,size);
        // 搜索条件构建
        Example example = createExample(tDbSource);
        // 执行搜索
        return new PageInfo<TDbSource>(tDbSourceMapper.selectByExample(example));
    }

    /**
     * 分页查询TDbSource表数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<TDbSource> findPage(int page, int size){
        // 静态分页
        PageHelper.startPage(page,size);
        // 分页查询
        return new PageInfo<TDbSource>(tDbSourceMapper.selectAll());
    }

    /**
     * 条件查询TDbSource表数据
     * @param tDbSource
     * @return
     */
    @Override
    public List<TDbSource> findList(TDbSource tDbSource){
        // 构建查询条件
        Example example = createExample(tDbSource);
        // 根据构建的条件查询数据
        return tDbSourceMapper.selectByExample(example);
    }


    /**
     * TDbSource构建查询对象
     * @param tDbSource
     * @return
     */
    public Example createExample(TDbSource tDbSource){
        Example example=new Example(TDbSource.class);
        Example.Criteria criteria = example.createCriteria();
        if(tDbSource!=null){
            //
            if(!StringUtils.isEmpty(tDbSource.getId())){
                    criteria.andEqualTo("id",tDbSource.getId());
            }
            //
            if(!StringUtils.isEmpty(tDbSource.getHostip())){
                    criteria.andEqualTo("hostip",tDbSource.getHostip());
            }
            //
            if(!StringUtils.isEmpty(tDbSource.getPort())){
                    criteria.andEqualTo("port",tDbSource.getPort());
            }
            //
            if(!StringUtils.isEmpty(tDbSource.getUsername())){
                    criteria.andLike("username","%"+tDbSource.getUsername()+"%");
            }
            //
            if(!StringUtils.isEmpty(tDbSource.getPassword())){
                    criteria.andEqualTo("password",tDbSource.getPassword());
            }
        }
        return example;
    }

    /**
     * 根据id删除TDbSource表数据
     * @param id
     */
    @Override
    public void delete(Integer id){
        tDbSourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改TDbSource表数据
     * @param tDbSource
     */
    @Override
    public void update(TDbSource tDbSource){
        tDbSourceMapper.updateByPrimaryKey(tDbSource);
    }

    /**
     * 增加TDbSource表数据
     * @param tDbSource
     */
    @Override
    public void add(TDbSource tDbSource){
        tDbSourceMapper.insert(tDbSource);
    }

    /**
     * 根据ID查询TDbSource表数据
     * @param id
     * @return
     */
    @Override
    public TDbSource findById(Integer id){
        return  tDbSourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询TDbSource表全部数据
     * @return
     */
    @Override
    public List<TDbSource> findAll() {
        return tDbSourceMapper.selectAll();
    }


    @Override
    public Integer findByHostAndDbname(String hostip,String dbname){
        return tDbSourceMapper.findByHostAndDbname(hostip,dbname);
    }

}
