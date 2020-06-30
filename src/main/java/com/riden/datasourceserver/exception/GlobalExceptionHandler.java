package com.riden.datasourceserver.exception;

import com.riden.datasourceserver.common.BaseApiService;
import com.riden.datasourceserver.common.ResponseBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.riden.datasourceserver")
public class GlobalExceptionHandler extends BaseApiService {

    @ExceptionHandler(Exception.class)
    public ResponseBase handleException(Exception e) {
        log.error(e.toString());
        return setResultError(e.getMessage());
    }

}
