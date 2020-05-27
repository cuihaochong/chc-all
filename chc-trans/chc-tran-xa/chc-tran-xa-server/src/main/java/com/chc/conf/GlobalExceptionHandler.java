package com.chc.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 统一异常处理
 *
 * @author cuihaochong
 * @date 2019/11/18
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 普通异常捕获
     *
     * @param e 异常
     * @return 通用返回
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> exceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return Result.error("异常：" + e.getMessage());
    }

}
