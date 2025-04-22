package org.qiaice.handler;

import lombok.extern.slf4j.Slf4j;
import org.qiaice.Result;
import org.qiaice.exception.BaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<Object> exceptionHandler(Exception e) {
        if (e instanceof BaseException) {
            return Result.no(e.getMessage());
        }
        log.error("程序内部出现严重错误, 请尽早修复! ", e);
        return Result.no("服务器发生致命错误, 正在努力修复中...");
    }
}
