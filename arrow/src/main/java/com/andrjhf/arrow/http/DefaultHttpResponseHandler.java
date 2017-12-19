package com.andrjhf.arrow.http;

/**
 * 默认的返回处理
 * Created by jiahongfei on 2017/11/9.
 */

public class DefaultHttpResponseHandler<T extends ApiResponse> implements IHttpResponseHandler<T> {

    private static final String TAG = "HttpResponseHandler";

    @Override
    public boolean httpResponseHandler(T t){
        if(t.getCode() == ApiResponse.CODE_OK){
            return true;
        }else {
            return false;
        }
    }
}
