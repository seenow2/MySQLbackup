package com.seenow.dbbackup.controller;
import com.seenow.dbbackup.pojo.TDbSource;
import com.seenow.dbbackup.service.TDbSourceService;
import com.github.pagehelper.PageInfo;
import com.seenow.dbbackup.result.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author: https://blog.csdn.net/pkxwyf
 *****/
@Api(value = "TDbSourceController")
@RestController
@RequestMapping("/tDbSource")
@CrossOrigin
public class TDbSourceController {

    // 注入业务层对象
    @Autowired
    private TDbSourceService tDbSourceService;

    /***
     * 分页+条件搜索TDbSource表数据
     * @param tDbSource
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "TDbSource条件分页查询",notes = "分页条件查询TDbSource方法详情",tags = {"TDbSourceController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "TDbSource对象",value = "传入JSON数据",required = false) TDbSource tDbSource, @PathVariable  int page, @PathVariable  int size){
        //调用TDbSourceService实现分页条件查询TDbSource
        PageInfo<TDbSource> pageInfo = tDbSourceService.findPage(tDbSource, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 分页搜索TDbSource表数据
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "TDbSource分页查询",notes = "分页查询TDbSource方法详情",tags = {"TDbSourceController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        // 调用TDbSourceService实现分页查询TDbSource
        PageInfo<TDbSource> pageInfo = tDbSourceService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索TDbSource表数据
     * @param tDbSource
     * @return
     */
    @ApiOperation(value = "TDbSource条件查询",notes = "条件查询TDbSource方法详情",tags = {"TDbSourceController"})
    @PostMapping(value = "/search" )
    public Result<List<TDbSource>> findList(@RequestBody(required = false) @ApiParam(name = "TDbSource对象",value = "传入JSON数据",required = false) TDbSource tDbSource){
        //调用TDbSourceService实现条件查询TDbSource
        List<TDbSource> list = tDbSourceService.findList(tDbSource);
        return new Result<List<TDbSource>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除TDbSource表数据
     * @param id
     * @return
     */
    @ApiOperation(value = "TDbSource根据ID删除",notes = "根据ID删除TDbSource方法详情",tags = {"TDbSourceController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用TDbSourceService实现根据主键删除
        tDbSourceService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改TDbSource表数据
     * @param tDbSource
     * @param id
     * @return
     */
    @ApiOperation(value = "TDbSource根据ID修改",notes = "根据ID修改TDbSource方法详情",tags = {"TDbSourceController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "TDbSource对象",value = "传入JSON数据",required = false) TDbSource tDbSource,@PathVariable Integer id){
        //设置主键值
        tDbSource.setId(id);
        //调用TDbSourceService实现修改TDbSource
        tDbSourceService.update(tDbSource);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增TDbSource表数据
     * @param tDbSource
     * @return
     */
    @ApiOperation(value = "TDbSource添加",notes = "添加TDbSource方法详情",tags = {"TDbSourceController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "TDbSource对象",value = "传入JSON数据",required = true) TDbSource tDbSource){
        //调用TDbSourceService实现添加TDbSource
        tDbSourceService.add(tDbSource);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询TDbSource表数据
     * @param id
     * @return
     */
    @ApiOperation(value = "TDbSource根据ID查询",notes = "根据ID查询TDbSource方法详情",tags = {"TDbSourceController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public Result<TDbSource> findById(@PathVariable Integer id){
        //调用TDbSourceService实现根据主键查询TDbSource
        TDbSource tDbSource = tDbSourceService.findById(id);
        return new Result<TDbSource>(true,StatusCode.OK,"查询成功",tDbSource);
    }

    /***
     * 查询TDbSource表全部数据
     * @return
     */
    @ApiOperation(value = "查询所有TDbSource",notes = "查询TDbSource方法详情",tags = {"TDbSourceController"})
    @GetMapping
    public Result<List<TDbSource>> findAll(){
        // 调用TDbSourceService实现查询所有TDbSource
        List<TDbSource> list = tDbSourceService.findAll();
        return new Result<List<TDbSource>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
