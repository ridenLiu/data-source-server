package com.riden.datasourceserver.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataColumn;

/**
 * 数据源字段管理接口
 */
public interface SysDataColumnService {

    JSONObject listPage(String parameter, String ds_id, Integer pageNum, Integer pageSize);

    JSONObject list(String parameter, String ds_id);

    ResponseBase add(SysDataColumn sysDataColumn);

    ResponseBase edit(SysDataColumn sysDataColumn);

    ResponseBase delete(String[] column_ids);

    ResponseBase importForDb(String ds_id);

    ResponseBase editColumnNumByColumnId(JSONArray array);
}
