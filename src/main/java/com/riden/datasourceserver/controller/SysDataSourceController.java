package com.riden.datasourceserver.controller;

import com.riden.datasourceserver.common.BaseApiService;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataSource;
import com.riden.datasourceserver.service.SysDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "2、数据源信息管理")
@RequestMapping("/dataSource/sysDataSource")
public class SysDataSourceController extends BaseApiService {


    @Autowired
    private SysDataSourceService sourceService;


    /**
     * 分页查询数据源信息
     *
     * @param parameter  数据源ID/数据源名称/数据源
     * @param db_link_no 数据链路ID
     * @param pageNumber 页码数
     * @param pageSize   行数
     */
    @ApiOperation(value = "数据源信息管理-分页列表查询")
    @PostMapping(value = "/list")
    public ResponseBase list(@RequestParam(name = "parameter") @ApiParam("数据源ID/数据源名称/数据源") String parameter,
                             @RequestParam(name = "db_link_no") @ApiParam("数据链路ID") String db_link_no,
                             @RequestParam(name = "pageNumber") @ApiParam(value = "页码数", required = true) Integer pageNumber,
                             @RequestParam(name = "pageSize") @ApiParam(value = "行数", required = true) Integer pageSize) {
        return setResultSuccess(sourceService.querySysDataSource(parameter, db_link_no, pageNumber, pageSize));
    }

    /**
     * 添加数据源信息
     *
     * @param sysDataSource 数据源对象
     */
    @ApiOperation(value = "数据源信息管理-添加数据源")
    @PostMapping(value = "/add")
    public ResponseBase add(@RequestBody SysDataSource sysDataSource) {
        if (sourceService.add(sysDataSource)) {
            return setResultSuccess("新增成功");
        } else {
            return setResultError("新增失败");
        }
    }

    /**
     * 修改数据源信息
     *
     * @param sysDataSource 数据源对象
     */
    @ApiOperation(value = "数据源管理-修改数据源信息")
    @PostMapping(value = "/edit")
    public ResponseBase edit(@RequestBody SysDataSource sysDataSource) {
        if (sourceService.edit(sysDataSource)) {
            return setResultSuccess("修改成功");
        } else {
            return setResultError("修改失败");
        }
    }

    /**
     * 删除数据源信息
     *
     * @param ds_ids ds_ids 数据源ID 数组
     */
    @ApiOperation(value = "数据源管理-删除数据源信息")
    @PostMapping(value = "/delete")
    public ResponseBase delete(@RequestParam(value = "ds_ids[]") String[] ds_ids) {
        return sourceService.delete(ds_ids);
    }

    /**
     * 数据源配置完后, 客户端进行调用需要传递数据源相关信息, 此功能是主要方便一键复制参数信息
     *
     * @param ds_id 数据源ID
     */
    @ApiOperation(value = "数据源管理-一键复制数据源参数信息")
    @PostMapping(value = "/paramCopyJson")
    public ResponseBase paramCopyJson(@RequestParam(name = "ds_id") String ds_id) {
        return sourceService.paramCopyJson(ds_id);
    }

}
