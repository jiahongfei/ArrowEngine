package com.andrjhf.arrow.http;

/**
 * 处理异常的接口
 * Created by jiahongfei on 2017/11/8.
 */

public interface IHttpErrorHandler {

    void httpErrorHandler(Throwable throwable);

}
