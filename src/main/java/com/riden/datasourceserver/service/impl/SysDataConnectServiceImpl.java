package com.riden.datasourceserver.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.riden.datasourceserver.common.BaseApiService;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataConnect;
import com.riden.datasourceserver.mapper.SysDataColumnMapper;
import com.riden.datasourceserver.mapper.SysDataConnectMapper;
import com.riden.datasourceserver.mapper.SysDataParmMapper;
import com.riden.datasourceserver.mapper.SysDataSoureMapper;
import com.riden.datasourceserver.service.SysDataConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysDataConnectServiceImpl extends BaseApiService implements SysDataConnectService {

    @Autowired
    private SysDataConnectMapper sysDataConnectMapper;

    @Autowired
    private SysDataColumnMapper sysDataColumnMapper;

    @Autowired
    private SysDataSoureMapper sysDataSoureMapper;

    @Autowired
    private SysDataParmMapper sysDataParmMapper;

    @Override
    public JSONObject querySysDataConnect(String parameter, Integer pageNum, Integer pageSize) {

        return null;
    }

    @Override
    public boolean add(SysDataConnect sysDataConnect) {
        return false;
    }

    @Override
    public ResponseBase edit(SysDataConnect sysDataConnect) {
        return null;
    }

    @Override
    public ResponseBase delete(String[] db_link_nos) {
        return null;
    }

    @Override
    public boolean testConnection(SysDataConnect sysDataConnect) {
        return false;
    }

    @Override
    public List<SysDataConnect> listAllDbState() {
        return null;
    }
}
