package com.seenow.dbbackup.feign;

import com.github.pagehelper.PageInfo;
import com.seenow.dbbackup.pojo.TBackupLog;
import com.seenow.dbbackup.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: https://blog.csdn.net/pkxwyf
 *****/
@FeignClient(name="seenow")
@RequestMapping("/tBackupLog")
public interface TBackupLogFeign {

    /***
     * 分页+条件搜索TBackupLog表数据
     * @param tBackupLog
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) TBackupLog tBackupLog, @PathVariable int page, @PathVariable int size);

    /***
     * 分页搜索TBackupLog表数据
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size);

    /***
     * 多条件搜索TBackupLog表数据
     * @param tBackupLog
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<TBackupLog>> findList(@RequestBody(required = false) TBackupLog tBackupLog);

    /***
     * 根据ID删除TBackupLog表数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Integer id);

    /***
     * 修改TBackupLog表数据
     * @param tBackupLog
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody TBackupLog tBackupLog, @PathVariable Integer id);

    /***
     * 新增TBackupLog表数据
     * @param tBackupLog
     * @return
     */
    @PostMapping
    Result add(@RequestBody TBackupLog tBackupLog);

    /***
     * 根据ID查询TBackupLog表数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<TBackupLog> findById(@PathVariable Integer id);

    /***
     * 查询TBackupLog表全部数据
     * @return
     */
    @GetMapping
    Result<List<TBackupLog>> findAll();
}
