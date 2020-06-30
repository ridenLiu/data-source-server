package com.riden.datasourceserver.service;

import com.alibaba.fastjson.JSONObject;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataConnect;

import java.util.List;

/**
 * 数据链路管理服务层
 */
public interface SysDataConnectService {

    JSONObject querySysDataConnect(String parameter, Integer pageNum, Integer pageSize);

    boolean add(SysDataConnect sysDataConnect);

    ResponseBase edit(SysDataConnect sysDataConnect);

    ResponseBase delete(String[] db_link_nos);

    boolean testConnection(SysDataConnect sysDataConnect);

    List<SysDataConnect> listAllDbState();


}
