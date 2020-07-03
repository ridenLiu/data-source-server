package com.riden.datasourceserver.service;


import com.alibaba.fastjson.JSONObject;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataSource;

public interface SysDataSourceService {

    /**
     * 分页查询数据源信息
     *
     * @param parameter 数据源ID/数据源名称/数据源
     * @param db_link_no 数据库链路ID
     * @param pageNumber 页码数
     * @param pageSize 行数
     * @return
     */
    JSONObject querySysDataSource(String parameter, String db_link_no, Integer pageNumber, Integer pageSize);

    boolean add(SysDataSource sysDataSource);

    boolean edit(SysDataSource sysDataSource);

    ResponseBase delete(String[] ds_ids);

    /**
     * 数据源配置完后, 客户端进行调用需要传递数据源相关信息, 此功能是主要方便一键复制参数信息
     *
     * @param ds_id 数据源ID
     */
    ResponseBase paramCopyJson(String ds_id);

}
