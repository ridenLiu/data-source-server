package com.riden.datasourceserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.riden.datasourceserver.common.BaseApiService;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataParm;
import com.riden.datasourceserver.entity.SysDataSource;
import com.riden.datasourceserver.mapper.SysDataColumnMapper;
import com.riden.datasourceserver.mapper.SysDataParamMapper;
import com.riden.datasourceserver.mapper.SysDataSourceMapper;
import com.riden.datasourceserver.service.SysDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class SysDataSourceServiceImpl extends BaseApiService implements SysDataSourceService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataSourceMapper sysDataSourceMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataColumnMapper sysDataColumnMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataParamMapper sysDataParamMapper;


    @Override
    public JSONObject querySysDataSource(String parameter, String db_link_no, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<SysDataSource> list = sysDataSourceMapper.querySysDataSoure(parameter.toUpperCase(),
                db_link_no == null || "".equals(db_link_no) ? null : db_link_no);
        PageInfo<SysDataSource> pageInfoList = new PageInfo<>(list);
        JSONObject res = new JSONObject();
        Long total = pageInfoList.getTotal();
        JSONArray rows = JSONArray.parseArray(JSON.toJSONString(list));
        res.put("total", total);
        res.put("rows", rows);
        return res;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(SysDataSource sysDataSource) {
        int rows = sysDataSourceMapper.add(sysDataSource);
        boolean flag = false;
        if (rows == 1) {
            flag = true;
        } else {
            //手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysDataSource sysDataSource) {
        int rows = sysDataSourceMapper.edit(sysDataSource);
        boolean flag = false;
        if (rows == 1) {
            flag = true;
        } else {
            //手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    @Override
    public ResponseBase delete(String[] ds_ids) {
        //删除数据源对应的参数
        int parmNum = sysDataParamMapper.deleteByDsIds(ds_ids);
        //删除数据源对应的字段
        int columnNum = sysDataColumnMapper.deleteByDsIds(ds_ids);
        //删除数据源
        int dataSourceNum = sysDataSourceMapper.deleteByDsIds(ds_ids);
        return setResultSuccess("删除成功,删除数据源:" + dataSourceNum + "行," +
                "字段信息:" + columnNum + "行,参数信息:" + parmNum + "行");
    }

    @Override
    public ResponseBase paramCopyJson(String ds_id) {
        if (ds_id == null || "".equals(ds_id)) {
            return setResultError("数据源ID不能为空");
        }

        SysDataSource sysDataSoure = sysDataSourceMapper.queryById(ds_id);
        String ds_typ = sysDataSoure.getDs_type();//数据源类型：SQL/存储过程
        String ds = sysDataSoure.getDs().trim().toUpperCase();//SQL语句
        boolean array_flag = false;//是否需要将参数封装JSON数组
        if ("SQL".equals(ds_typ)) {
            if (ds.startsWith("INSERT") || ds.startsWith("UPDATE") || ds.startsWith("DELETE")) {
                //如果数据源是新增 更新 删除操作,可能会存在批量操作,所以参数封装为JSON数组
                array_flag = true;
            }
        }


        List<SysDataParm> parmList = sysDataParamMapper.querySysDataParam("", ds_id);
        JSONObject obj = new JSONObject(true);
        obj.put("ds_id", ds_id);

        if (array_flag) {
            JSONArray array = new JSONArray();
            if (parmList != null && parmList.size() > 0) {
                JSONObject objParm = new JSONObject(true);
                for (SysDataParm parm : parmList) {
                    objParm.put(parm.getParm_code(), "");
                }
                array.add(objParm);
            }
            obj.put("parameter", array);
        } else {
            if (parmList != null && parmList.size() > 0) {
                for (SysDataParm parm : parmList) {
                    obj.put(parm.getParm_code(), "");
                }
            }
        }
        return setResult(true, 200, "处理成功", obj.toJSONString());
    }
}
