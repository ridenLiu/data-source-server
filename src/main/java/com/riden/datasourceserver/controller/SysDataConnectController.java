package com.riden.datasourceserver.controller;

import com.riden.datasourceserver.common.BaseApiService;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataConnect;
import com.riden.datasourceserver.service.SysDataConnectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据链路控制层
 */
@Api(tags = "1、数据链路信息管理")
@Slf4j
@RestController
@RequestMapping("/dataSource/sysDataConnect")
public class SysDataConnectController extends BaseApiService {

    @Autowired
    private SysDataConnectService connectService;

    @ApiOperation("数据链路管理-分页列表查询")
    @PostMapping(value = "/list")
    public ResponseBase list(@ApiParam("数据库连接名称/数据库IP") String parameter,
                             @ApiParam(value = "页码数", required = true) Integer pageNumber,
                             @ApiParam(value = "行数", required = true) Integer pageSize) {
        return setResultSuccess(connectService.querySysDataConnect(parameter, pageNumber, pageSize));

    }

    @ApiOperation("数据链路管理-新增数据库链路信息")
    @PostMapping(value = "/add")
    public ResponseBase add(@RequestBody SysDataConnect sysDataConnect) {
        // 先进行链路测试
        if (!connectService.testConnection(sysDataConnect)) return setResultError("保存链路失败,链路不通");

        if (connectService.add(sysDataConnect)) {
            return setResultSuccess("添加链路成功");
        } else {
            return setResultError("添加链路失败");
        }

    }


    @ApiOperation("数据链路管理-修改数据库链路信息")
    @PostMapping(value = "/edit")
    public ResponseBase edit(@RequestBody SysDataConnect sysDataConnect) {
        return connectService.edit(sysDataConnect);
    }

    @ApiOperation("数据链路管理-删除数据库链路信息")
    @PostMapping(value = "/delete")
    public ResponseBase delete(@RequestParam(value = "db_link_nos") String[] db_link_nos) {
        return connectService.delete(db_link_nos);
    }


    @ApiOperation("数据链路管理-查询所有启用的")
    @PostMapping(value = "/listAllDbState")
    public ResponseBase listAllDbState() {
        return setResultSuccess(connectService.listAllDbState());
    }


}
