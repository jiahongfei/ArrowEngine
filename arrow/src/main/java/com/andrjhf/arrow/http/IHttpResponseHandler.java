package com.andrjhf.arrow.http;

/**
 * 处理返回的接口
 * Created by jiahongfei on 2017/11/9.
 */

public interface IHttpResponseHandler<T extends ApiResponse> {

   /**
    *
    * @param t
    * @return true处理成功可以继续执行；false处理失败消费掉
    */
   boolean httpResponseHandler(T t);

}
