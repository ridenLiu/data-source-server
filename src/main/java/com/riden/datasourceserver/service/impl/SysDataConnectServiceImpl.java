package com.riden.datasourceserver.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.riden.datasourceserver.common.BaseApiService;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.common.jdbc.DataSourceFactory;
import com.riden.datasourceserver.common.jdbc.JDBCUtils;
import com.riden.datasourceserver.entity.SysDataConnect;
import com.riden.datasourceserver.mapper.SysDataColumnMapper;
import com.riden.datasourceserver.mapper.SysDataConnectMapper;
import com.riden.datasourceserver.mapper.SysDataParamMapper;
import com.riden.datasourceserver.mapper.SysDataSourceMapper;
import com.riden.datasourceserver.service.SysDataConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SysDataConnectServiceImpl extends BaseApiService implements SysDataConnectService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataConnectMapper connectMapper;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataColumnMapper columnMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataSourceMapper sysDataSourceMapper;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataParamMapper paramMapper;
    @Autowired
    private DataSourceFactory dataSourceFactory;


    @Override
    public JSONObject querySysDataConnect(String parameter, Integer pageNum, Integer pageSize) {
        //利用PageHelper分页查询 注意：这个一定要放查询语句的前一行,否则无法进行分页,因为它对紧随其后第一个sql语句有效
        PageHelper.startPage(pageNum, pageSize);
        List<SysDataConnect> list = connectMapper.querySysDataConnect(parameter.toUpperCase());
        PageInfo<SysDataConnect> pageInfoUserList = new PageInfo<>(list);
        Long total = pageInfoUserList.getTotal();
        JSONObject obj = new JSONObject();
        obj.put("total", total);
        obj.put("rows", JSONArray.parseArray(JSON.toJSONString(list)));
        return obj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // rollbackFor：触发回滚的异常，默认是RuntimeException和Error
    public boolean add(SysDataConnect sysDataConnect) {
        // 设置主键值
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        sysDataConnect.setDb_link_no(uuid);
        int rows = connectMapper.add(sysDataConnect);
        boolean flag = false;
        if (rows == 1) {
            dataSourceFactory.addDataSource(sysDataConnect);
            flag = true;
            // 添加该数据源
        } else {
            // 手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase edit(SysDataConnect sysDataConnect) {
        String connId = sysDataConnect.getDb_link_no();
        // 查询出原数据
        SysDataConnect oldData = connectMapper.querySysDataConnectById(connId);
        // 测试新链路是否通畅
        boolean isConnect = testConnection(sysDataConnect);
        if (!isConnect) {
            return setResultError("链路不通");
        }
        // 更新数据
        int rows = connectMapper.edit(sysDataConnect);
        if (rows == 1) {
            // 删除原有的数据源
            dataSourceFactory.removeById(connId);
            // 添加编辑后的数据源
            dataSourceFactory.addDataSource(sysDataConnect);
            return setResultSuccess("修改成功");
        } else {
            // 手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return setResultError("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase delete(String[] db_link_nos) {
        int connectNum = 0;
        int dataSourceNum = 0;
        int parmNum = 0;
        int columnNum = 0;

        //查询出数据库链路下所有数据源信息
        String[] dsIds = sysDataSourceMapper.getDsIdsByDblinkNo(db_link_nos);
        if (dsIds == null || dsIds.length == 0) {
            // 直接删除数据链路
            connectNum = connectMapper.delete(db_link_nos);
            return setResultSuccess("删除成功,删除数据链路:" + connectNum + "行,数据源:" + dataSourceNum + "行," +
                    "字段信息:" + columnNum + "行,参数信息:" + parmNum + "行");
        }
        //删除数据链路下所有数据源对应的参数
        parmNum = paramMapper.deleteByDsIds(dsIds);
        //删除数据链路下所有数据源对应的字段
        columnNum = columnMapper.deleteByDsIds(dsIds);
        //删除数据链路对应的数据源
        dataSourceNum = sysDataSourceMapper.deleteByDsIds(dsIds);
        //删除链路
        connectNum = connectMapper.delete(db_link_nos);
        // 删除数据源
        for (String id : db_link_nos) {
            dataSourceFactory.removeById(id);
        }
        return setResultSuccess("删除成功,删除数据链路:" + connectNum + "行,数据源:" + dataSourceNum + "行," +
                "字段信息:" + columnNum + "行,参数信息:" + parmNum + "行");
    }

    @Override
    public boolean testConnection(SysDataConnect sysDataConnect) {
        Connection testConn = JDBCUtils.getConnection(sysDataConnect);
        if (testConn == null) {
            return false;
        } else {
            try {
                testConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override
    public List<SysDataConnect> listAllDbState() {
        return connectMapper.listAllDbState();
    }
}
