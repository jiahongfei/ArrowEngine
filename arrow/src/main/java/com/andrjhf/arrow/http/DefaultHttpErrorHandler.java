package com.andrjhf.arrow.http;

import android.app.Application;
import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 默认的错误处理
 * Created by jiahongfei on 2017/11/8.
 */

public class DefaultHttpErrorHandler implements IHttpErrorHandler {

    private static final String TAG = "DefaultHttpErrorHandler";

    private Application application;

    public DefaultHttpErrorHandler(Application application) {
        this.application = application;
    }

    @Override
    public void httpErrorHandler(Throwable t) {

        String msg = "未知错误";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof java.net.SocketException) {
            msg = "网络无法访问";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertHttpException(httpException);
        } else if (t instanceof JsonParseException
                || t instanceof ParseException
                || t instanceof JSONException
                || t instanceof JsonIOException) {
            msg = "数据解析错误";
        } else if (t instanceof HttpResponseHandlerException) {
            msg = converHttpResponseHandlerException((HttpResponseHandlerException) t);
        }

        Log.e(TAG, "msg : " + msg);
        Toast.makeText(application,msg,Toast.LENGTH_SHORT).show();
    }

    private String converHttpResponseHandlerException(HttpResponseHandlerException exception) {

        return exception.getMessage();
    }

    private String convertHttpException(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}