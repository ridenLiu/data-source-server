package com.riden.datasourceserver.common;

public class BaseApiService {

    // 返回code和消息自定义,没有返回数据
    public ResponseBase setResultError(Integer code, String msg) {
        return setResult(false, code, msg, "");
    }

    // 返回错误，可以传msg
    public ResponseBase setResultError(String msg) {
        return setResult(false, 500, msg, "");
    }

    // 返回成功，可以传data值
    public ResponseBase setResultSuccess(Object data) {
        return setResult(true, 200, "处理成功", data);
    }

    // 返回成功，沒有data值
    public ResponseBase setResultSuccess() {
        return setResult(true, 200, "处理成功", "");
    }

    // 返回成功，自定义返回消息,沒有data值
    public ResponseBase setResultSuccess(String msg) {
        return setResult(true, 200, msg, "");
    }

    // 通用封装
    public ResponseBase setResult(boolean success, Integer code, String msg, Object data) {
        return new ResponseBase(success, code, msg, data);
    }
}
