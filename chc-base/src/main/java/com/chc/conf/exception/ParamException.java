package com.chc.conf.exception;

/**
 * Description: 自定义参数异常
 *
 * @author cuihaochong
 * @date 2020/3/3
 */
public class ParamException extends RuntimeException {
    public ParamException() {
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
