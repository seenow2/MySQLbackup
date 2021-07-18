package com.seenow.dbbackup.feign;

import com.seenow.dbbackup.result.*;
import com.seenow.dbbackup.pojo.TPolicy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import java.util.List;

/****
 * @Author: https://blog.csdn.net/pkxwyf
 *****/
@FeignClient(name="seenow")
@RequestMapping("/tPolicy")
public interface TPolicyFeign {

    /***
     * 分页+条件搜索TPolicy表数据
     * @param tPolicy
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) TPolicy tPolicy, @PathVariable int page, @PathVariable  int size);

    /***
     * 分页搜索TPolicy表数据
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索TPolicy表数据
     * @param tPolicy
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<TPolicy>> findList(@RequestBody(required = false) TPolicy tPolicy);

    /***
     * 根据ID删除TPolicy表数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Integer id);

    /***
     * 修改TPolicy表数据
     * @param tPolicy
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody TPolicy tPolicy,@PathVariable Integer id);

    /***
     * 新增TPolicy表数据
     * @param tPolicy
     * @return
     */
    @PostMapping
    Result add(@RequestBody TPolicy tPolicy);

    /***
     * 根据ID查询TPolicy表数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<TPolicy> findById(@PathVariable Integer id);

    /***
     * 查询TPolicy表全部数据
     * @return
     */
    @GetMapping
    Result<List<TPolicy>> findAll();
}
