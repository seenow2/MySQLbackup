package com.seenow.dbbackup.controller;
import com.seenow.dbbackup.pojo.TPolicy;
import com.seenow.dbbackup.service.TPolicyService;
import com.github.pagehelper.PageInfo;
import com.seenow.dbbackup.result.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author: https://blog.csdn.net/pkxwyf
 *****/
@Api(value = "TPolicyController")
@RestController
@RequestMapping("/tPolicy")
@CrossOrigin
public class TPolicyController {

    // 注入业务层对象
    @Autowired
    private TPolicyService tPolicyService;

    /***
     * 分页+条件搜索TPolicy表数据
     * @param tPolicy
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "TPolicy条件分页查询",notes = "分页条件查询TPolicy方法详情",tags = {"TPolicyController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "TPolicy对象",value = "传入JSON数据",required = false) TPolicy tPolicy, @PathVariable  int page, @PathVariable  int size){
        //调用TPolicyService实现分页条件查询TPolicy
        PageInfo<TPolicy> pageInfo = tPolicyService.findPage(tPolicy, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 分页搜索TPolicy表数据
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "TPolicy分页查询",notes = "分页查询TPolicy方法详情",tags = {"TPolicyController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        // 调用TPolicyService实现分页查询TPolicy
        PageInfo<TPolicy> pageInfo = tPolicyService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索TPolicy表数据
     * @param tPolicy
     * @return
     */
    @ApiOperation(value = "TPolicy条件查询",notes = "条件查询TPolicy方法详情",tags = {"TPolicyController"})
    @PostMapping(value = "/search" )
    public Result<List<TPolicy>> findList(@RequestBody(required = false) @ApiParam(name = "TPolicy对象",value = "传入JSON数据",required = false) TPolicy tPolicy){
        //调用TPolicyService实现条件查询TPolicy
        List<TPolicy> list = tPolicyService.findList(tPolicy);
        return new Result<List<TPolicy>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除TPolicy表数据
     * @param id
     * @return
     */
    @ApiOperation(value = "TPolicy根据ID删除",notes = "根据ID删除TPolicy方法详情",tags = {"TPolicyController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用TPolicyService实现根据主键删除
        tPolicyService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改TPolicy表数据
     * @param tPolicy
     * @param id
     * @return
     */
    @ApiOperation(value = "TPolicy根据ID修改",notes = "根据ID修改TPolicy方法详情",tags = {"TPolicyController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "TPolicy对象",value = "传入JSON数据",required = false) TPolicy tPolicy,@PathVariable Integer id){
        //设置主键值
        tPolicy.setId(id);
        //调用TPolicyService实现修改TPolicy
        tPolicyService.update(tPolicy);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增TPolicy表数据
     * @param tPolicy
     * @return
     */
    @ApiOperation(value = "TPolicy添加",notes = "添加TPolicy方法详情",tags = {"TPolicyController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "TPolicy对象",value = "传入JSON数据",required = true) TPolicy tPolicy){
        //调用TPolicyService实现添加TPolicy
        tPolicyService.add(tPolicy);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询TPolicy表数据
     * @param id
     * @return
     */
    @ApiOperation(value = "TPolicy根据ID查询",notes = "根据ID查询TPolicy方法详情",tags = {"TPolicyController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public Result<TPolicy> findById(@PathVariable Integer id){
        //调用TPolicyService实现根据主键查询TPolicy
        TPolicy tPolicy = tPolicyService.findById(id);
        return new Result<TPolicy>(true,StatusCode.OK,"查询成功",tPolicy);
    }

    /***
     * 查询TPolicy表全部数据
     * @return
     */
    @ApiOperation(value = "查询所有TPolicy",notes = "查询TPolicy方法详情",tags = {"TPolicyController"})
    @GetMapping
    public Result<List<TPolicy>> findAll(){
        // 调用TPolicyService实现查询所有TPolicy
        List<TPolicy> list = tPolicyService.findAll();
        return new Result<List<TPolicy>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
