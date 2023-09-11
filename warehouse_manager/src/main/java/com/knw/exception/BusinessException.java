package com.knw.exception;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-07 23:21
 */
public class BusinessException extends RuntimeException {

    //只是创建异常对象
    public BusinessException() {
        super();
    }
    //创建异常对象，指定异常信息

    public BusinessException(String message) {
        super(message);
    }
}
