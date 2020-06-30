package com.riden.datasourceserver.common;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 返回实体类,统一响应服务接口信息
 */
@Data
@Slf4j
@ApiModel
public class ResponseBase {
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;
    @ApiModelProperty(value = "返回code")
    private Integer returnCode;
    @ApiModelProperty(value = "返回消息")
    private String returnMessage;
    @ApiModelProperty(value = "返回数据")
    private Object returnData;

    public ResponseBase() {
    }

    public ResponseBase(boolean success, Integer returnCode, String returnMessage, Object returnData) {
        this.success = success;
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.returnData = returnData;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("success", success);
        obj.put("returnCode", returnCode);
        obj.put("returnMessage", returnMessage);
        obj.put("returnData", returnData);
        return obj.toString();
    }


}
