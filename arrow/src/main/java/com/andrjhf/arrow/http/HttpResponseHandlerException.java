package com.andrjhf.arrow.http;

/**
 * 服务器返回错误，包装成异常进行处理
 * Created by jiahongfei on 2017/11/9.
 */

public class HttpResponseHandlerException extends Exception {

    private int code;

    public HttpResponseHandlerException(int code) {
        super();
        this.code = code;
    }

    public HttpResponseHandlerException(int code, String message) {
        super(message);
        this.code = code;
    }


    public HttpResponseHandlerException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    public HttpResponseHandlerException(int code, Throwable cause) {
        super(cause);
        this.code = code;

    }


    protected HttpResponseHandlerException(int code, String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;

    }

    public int getCode() {
        return code;
    }
}
