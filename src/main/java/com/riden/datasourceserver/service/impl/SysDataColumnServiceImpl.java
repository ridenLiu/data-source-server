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
import com.riden.datasourceserver.entity.SysDataColumn;
import com.riden.datasourceserver.entity.SysDataConnect;
import com.riden.datasourceserver.entity.SysDataParm;
import com.riden.datasourceserver.entity.SysDataSource;
import com.riden.datasourceserver.mapper.SysDataColumnMapper;
import com.riden.datasourceserver.mapper.SysDataConnectMapper;
import com.riden.datasourceserver.mapper.SysDataParamMapper;
import com.riden.datasourceserver.mapper.SysDataSourceMapper;
import com.riden.datasourceserver.service.SysDataColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class SysDataColumnServiceImpl extends BaseApiService implements SysDataColumnService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataColumnMapper columnMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataSourceMapper dataSourceMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataConnectMapper connectMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataParamMapper paramMapper;
    @Autowired
    private DataSourceFactory dataSourceFactory;


    /**
     * 分页查询数据源字段信息
     *
     * @param parameter 字段标题/字段名
     * @param ds_id     数据源ID
     * @param pageNum   页码数
     * @param pageSize  行数
     * @return
     */
    @Override
    public JSONObject listPage(String parameter, String ds_id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysDataColumn> list = columnMapper.querySysDataColumn(parameter.toUpperCase(),
                ds_id == null || "".equals(ds_id) ? null : ds_id);
        PageInfo<SysDataColumn> pageInfoList = new PageInfo<>(list);
        Long total = pageInfoList.getTotal();
        JSONObject res = new JSONObject();
        res.put("total", total);
        res.put("rows", JSONArray.parseArray(JSON.toJSONString(list)));
        return res;
    }

    /**
     * 查询数据源所有字段信息
     *
     * @param parameter 字段标题/字段名
     * @param ds_id     数据源ID
     * @return
     */
    @Override
    public JSONObject list(String parameter, String ds_id) {
        List<SysDataColumn> list = columnMapper.querySysDataColumn(parameter.toUpperCase(),
                ds_id == null || "".equals(ds_id) ? null : ds_id);
        JSONObject res = new JSONObject();
        res.put("rows", JSONArray.parseArray(JSON.toJSONString(list)));
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase add(SysDataColumn sysDataColumn) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        sysDataColumn.setColumn_id(uuid);
        int num = columnMapper.add(sysDataColumn);
        if (num == 1) {
            return setResultSuccess("新增成功");
        } else {
            //手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return setResultError("新增失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase edit(SysDataColumn sysDataColumn) {
        int num = columnMapper.edit(sysDataColumn);
        if (num == 1) {
            return setResultSuccess("修改成功");
        } else {
            //手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return setResultError("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase delete(String[] column_ids) {
        int num = columnMapper.delete(column_ids);
        if (num >= 1) {
            return setResultSuccess("删除成功," + num + "行被删除");
        } else {
            //手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return setResultError("删除失败");
        }
    }

    /**
     * 将数据源中对应的字段信息从数据库中导出来
     *
     * @param ds_id 数据源ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase importForDb(String ds_id) {
        // 查出数据源信息
        SysDataSource dataSource = dataSourceMapper.queryById(ds_id);
        if (dataSource == null) {
            return setResultError("数据源ID:[" + ds_id + "],不存在");
        }
        // 根据数据源ID,删除数据源下所有字段
        columnMapper.deleteByDsId(ds_id);
        //更具数据源链路ID,查询出对应的数据源链路
        SysDataConnect connect = connectMapper.querySysDataConnectById(dataSource.getDb_link_no());
        // 根据数据源ID,查询出数据源对应的参数信息
        List<SysDataParm> paramList = paramMapper.querySysDataParam("", ds_id);


        return null;
    }

    @Override
    public ResponseBase editColumnNumByColumnId(JSONArray array) {
        return null;
    }


    /**
     * 获取参数阻断
     *
     * @param dataSource
     * @param connectData
     * @param paramList
     * @param needNewConnection 是否新建一个连接,true: 重新创建新链接,false: 使用数据源中的链接
     */
    public void getColumnDataForDb(SysDataSource dataSource, SysDataConnect connectData, List<SysDataParm> paramList, Boolean needNewConnection) {
        Connection conn = null;
        if (needNewConnection == null || !needNewConnection) {
            try {
                conn = dataSourceFactory.getById(connectData.getDb_link_no()).getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // 获取连接
            conn = JDBCUtils.getConnection(connectData);
        }
        Objects.requireNonNull(conn,"获取数据链路时发生异常");
        String datasourceType = dataSource.getDs_type();// 数据源类型 sql/储存过程
        String sqlStr = dataSource.getDs();// 数据源sql语句或者储存过程
        List<SysDataColumn> columnList = new ArrayList<>();// 需要返回的字段集合
        if ("SQL".equals(datasourceType)) {
            if (sqlStr == null || "".equals(sqlStr) || !sqlStr.trim().toUpperCase().startsWith("SELECT")) {
                throw new RuntimeException("该数据源不支持从数据库导入,请检查SQL");
            }
        }






    }
}
