package com.riden.datasourceserver.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataParm;

/**
 * 数据源参数接口
 */
public interface SysDataParamService {
    /**
     *分页查询数据源参数信息
     *
     * @param parameter 参数代码/参数名称
     * @param ds_id 数据源ID
     * @param pageNum 页码数
     * @param pageSize 行数
     * @return
     */
    JSONObject listPage(String parameter, String ds_id, Integer pageNum, Integer pageSize);

    /**
     *查询数据源下所有参数信息
     * @param parameter 参数代码/参数名称
     * @param ds_id 数据源ID
     * @return
     */
    JSONObject list(String parameter, String ds_id);

    /**
     *
     * @param sysDataParam 数据源参数对象
     * @return
     */
    ResponseBase add(SysDataParm sysDataParam);

    /**
     *
     * @param sysDataParam 数据源参数对象
     * @return
     */
    ResponseBase edit(SysDataParm sysDataParam);

    /**
     *
     * @param param_ids 数据源参数ID 数组
     * @return
     */
    ResponseBase delete(String[] param_ids);

    /**
     *
     * @param array
     * @return
     */
    ResponseBase editParamNumByParamId(JSONArray array);

}
