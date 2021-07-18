package com.seenow.dbbackup.controller;

import com.github.pagehelper.PageInfo;
import com.seenow.dbbackup.pojo.TBackupLog;
import com.seenow.dbbackup.result.Result;
import com.seenow.dbbackup.result.StatusCode;
import com.seenow.dbbackup.service.TBackupLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: https://blog.csdn.net/pkxwyf
 *****/
@Api(value = "TBackupLogController")
@RestController
@RequestMapping("/tBackupLog")
@CrossOrigin
public class TBackupLogController {

    // 注入业务层对象
    @Autowired
    private TBackupLogService tBackupLogService;

    /***
     * 分页+条件搜索TBackupLog表数据
     * @param tBackupLog
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "TBackupLog条件分页查询",notes = "分页条件查询TBackupLog方法详情",tags = {"TBackupLogController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "TBackupLog对象",value = "传入JSON数据",required = false) TBackupLog tBackupLog, @PathVariable int page, @PathVariable int size){
        //调用TBackupLogService实现分页条件查询TBackupLog
        PageInfo<TBackupLog> pageInfo = tBackupLogService.findPage(tBackupLog, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 分页搜索TBackupLog表数据
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "TBackupLog分页查询",notes = "分页查询TBackupLog方法详情",tags = {"TBackupLogController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size){
        // 调用TBackupLogService实现分页查询TBackupLog
        PageInfo<TBackupLog> pageInfo = tBackupLogService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索TBackupLog表数据
     * @param tBackupLog
     * @return
     */
    @ApiOperation(value = "TBackupLog条件查询",notes = "条件查询TBackupLog方法详情",tags = {"TBackupLogController"})
    @PostMapping(value = "/search" )
    public Result<List<TBackupLog>> findList(@RequestBody(required = false) @ApiParam(name = "TBackupLog对象",value = "传入JSON数据",required = false) TBackupLog tBackupLog){
        //调用TBackupLogService实现条件查询TBackupLog
        List<TBackupLog> list = tBackupLogService.findList(tBackupLog);
        return new Result<List<TBackupLog>>(true, StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除TBackupLog表数据
     * @param id
     * @return
     */
    @ApiOperation(value = "TBackupLog根据ID删除",notes = "根据ID删除TBackupLog方法详情",tags = {"TBackupLogController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用TBackupLogService实现根据主键删除
        tBackupLogService.delete(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /***
     * 修改TBackupLog表数据
     * @param tBackupLog
     * @param id
     * @return
     */
    @ApiOperation(value = "TBackupLog根据ID修改",notes = "根据ID修改TBackupLog方法详情",tags = {"TBackupLogController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "TBackupLog对象",value = "传入JSON数据",required = false) TBackupLog tBackupLog, @PathVariable Integer id){
        //设置主键值
        tBackupLog.setId(id);
        //调用TBackupLogService实现修改TBackupLog
        tBackupLogService.update(tBackupLog);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /***
     * 新增TBackupLog表数据
     * @param tBackupLog
     * @return
     */
    @ApiOperation(value = "TBackupLog添加",notes = "添加TBackupLog方法详情",tags = {"TBackupLogController"})
    @PostMapping
    public Result add(@RequestBody @ApiParam(name = "TBackupLog对象",value = "传入JSON数据",required = true) TBackupLog tBackupLog){
        //调用TBackupLogService实现添加TBackupLog
        tBackupLogService.add(tBackupLog);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询TBackupLog表数据
     * @param id
     * @return
     */
    @ApiOperation(value = "TBackupLog根据ID查询",notes = "根据ID查询TBackupLog方法详情",tags = {"TBackupLogController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public Result<TBackupLog> findById(@PathVariable Integer id){
        //调用TBackupLogService实现根据主键查询TBackupLog
        TBackupLog tBackupLog = tBackupLogService.findById(id);
        return new Result<TBackupLog>(true, StatusCode.OK,"查询成功",tBackupLog);
    }

    /***
     * 查询TBackupLog表全部数据
     * @return
     */
    @ApiOperation(value = "查询所有TBackupLog",notes = "查询TBackupLog方法详情",tags = {"TBackupLogController"})
    @GetMapping
    public Result<List<TBackupLog>> findAll(){
        // 调用TBackupLogService实现查询所有TBackupLog
        List<TBackupLog> list = tBackupLogService.findAll();
        return new Result<List<TBackupLog>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
