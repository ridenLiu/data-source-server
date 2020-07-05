package com.riden.datasourceserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.riden.datasourceserver.common.BaseApiService;
import com.riden.datasourceserver.common.ResponseBase;
import com.riden.datasourceserver.entity.SysDataParm;
import com.riden.datasourceserver.mapper.SysDataParamMapper;
import com.riden.datasourceserver.service.SysDataParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SysDataParamServiceImpl extends BaseApiService implements SysDataParamService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataParamMapper sysDataParamMapper;


    @Override
    public JSONObject listPage(String parameter, String ds_id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysDataParm> list = sysDataParamMapper.querySysDataParam(parameter.toUpperCase(),
                ds_id == null || "".equals(ds_id) ? null : ds_id);
        PageInfo<SysDataParm> pageInfoList = new PageInfo<>(list);
        Long total = pageInfoList.getTotal();
        JSONObject obj = new JSONObject();
        obj.put("total", pageInfoList.getTotal());
        obj.put("rows", JSONArray.parseArray(JSON.toJSONString(list)));

        return obj;
    }

    @Override
    public JSONObject list(String parameter, String ds_id) {
        List<SysDataParm> list = sysDataParamMapper.querySysDataParam(parameter.toUpperCase(),
                ds_id == null || "".equals(ds_id) ? null : ds_id);
        JSONObject obj = new JSONObject();
        obj.put("rows", JSONArray.parseArray(JSON.toJSONString(list)));
        return obj;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseBase add(SysDataParm sysDataParam) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        sysDataParam.setParm_id(uuid);
        int num = sysDataParamMapper.add(sysDataParam);
        if (num == 1) {
            return setResultSuccess("新增成功");
        } else {
            //手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return setResultError("新增失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseBase edit(SysDataParm sysDataParm) {
        int num = sysDataParamMapper.edit(sysDataParm);
        if (num == 1) {
            return setResultSuccess("修改成功");
        } else {
            //手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return setResultError("修改失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseBase delete(String[] parm_ids) {
        int num = sysDataParamMapper.delete(parm_ids);
        if (num >= 1) {
            return setResultSuccess("删除成功," + num + "行被删除");
        } else {
            //手动进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return setResultError("删除失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseBase editParamNumByParamId(JSONArray array) {
        return null;
    }
}
