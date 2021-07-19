package com.seenow.dbbackup.feign;

import com.seenow.dbbackup.result.*;
import com.seenow.dbbackup.pojo.TDbSource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import java.util.List;

/****
 * @Author: https://blog.csdn.net/pkxwyf
 *****/
@FeignClient(name="seenow")
@RequestMapping("/tDbSource")
public interface TDbSourceFeign {

    /***
     * 分页+条件搜索TDbSource表数据
     * @param tDbSource
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) TDbSource tDbSource, @PathVariable int page, @PathVariable  int size);

    /***
     * 分页搜索TDbSource表数据
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索TDbSource表数据
     * @param tDbSource
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<TDbSource>> findList(@RequestBody(required = false) TDbSource tDbSource);

    /***
     * 根据ID删除TDbSource表数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Integer id);

    /***
     * 修改TDbSource表数据
     * @param tDbSource
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody TDbSource tDbSource,@PathVariable Integer id);

    /***
     * 新增TDbSource表数据
     * @param tDbSource
     * @return
     */
    @PostMapping
    Result add(@RequestBody TDbSource tDbSource);

    /***
     * 根据ID查询TDbSource表数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<TDbSource> findById(@PathVariable Integer id);

    /***
     * 查询TDbSource表全部数据
     * @return
     */
    @GetMapping
    Result<List<TDbSource>> findAll();
}
