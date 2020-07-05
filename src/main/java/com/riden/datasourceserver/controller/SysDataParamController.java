package com.riden.datasourceserver.controller;

import com.riden.datasourceserver.common.BaseApiService;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataParm;
import com.riden.datasourceserver.service.SysDataParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "3、数据源参数信息管理")
@RestController
@RequestMapping("/dataSource/sysDataParam")
public class SysDataParamController extends BaseApiService {

    @Autowired
    SysDataParamService paramService;


    @ApiOperation(value = "数据源参数信息管理-分页列表查询", notes = "数据源参数信息管理-分页列表查询")
    @PostMapping(value = "/listPage")
    public ResponseBase listPage(@RequestParam(name = "parameter") @ApiParam(value = "参数代码/参数名称") String parameter,
                                 @RequestParam(name = "ds_id") @ApiParam(value = "数据源ID", required = true) String ds_id,
                                 @RequestParam(name = "pageNumber") @ApiParam(value = "页码数", required = true) Integer pageNumber,
                                 @RequestParam(name = "pageSize") @ApiParam(value = "行数", required = true) Integer pageSize) {
        return setResultSuccess(paramService.listPage(parameter, ds_id, pageNumber, pageSize));
    }

    @ApiOperation(value = "数据源参数信息管理-查询数据源下所有参数信息", notes = "数据源参数信息管理-查询数据源下所有参数信息")
    @PostMapping(value = "/list")
    public ResponseBase list(@RequestParam(name = "parameter") @ApiParam(value = "参数代码/参数名称") String parameter,
                             @RequestParam(name = "ds_id") @ApiParam(value = "数据源ID", required = true) String ds_id) {
        return setResultSuccess(paramService.list(parameter, ds_id));
    }

    @ApiOperation(value = "数据源参数管理-新增数据源参数信息", notes = "数据源参数管理-新增数据源参数信息")
    @PostMapping(value = "/add")
    public ResponseBase add(@RequestBody SysDataParm sysDataParm) {
        return paramService.add(sysDataParm);
    }

    @ApiOperation(value = "数据源参数管理-修改数据源参数信息", notes = "数据源参数管理-修改数据源参数信息")
    @PostMapping(value = "/edit")
    public ResponseBase edit(@RequestBody SysDataParm sysDataParm) {
        return paramService.edit(sysDataParm);
    }

    @ApiOperation(value = "数据源参数管理-删除数据源参数信息", notes = "数据源参数管理-删除数据源参数信息")
    @PostMapping(value = "/delete")
    public ResponseBase delete(@RequestParam(value = "parm_ids[]") String[] parm_ids) {
        return paramService.delete(parm_ids);
    }


}
