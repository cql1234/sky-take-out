package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.exception.DuplicateUsernameException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.piccolo.util.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获用户名重复异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> DuplicateUsernameHandler(DuplicateUsernameException e){
        log.error(e.getMessage());
        return Result.error(e.getMessage());
    }

}
