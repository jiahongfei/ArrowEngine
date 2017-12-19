package com.andrjhf.arrow.http;

/**
 * 网络返回json的模板类
 * 要求返回json格式为 : {"code":1,"msg":"成功",  "data":{}}
 * Created by jiahongfei on 2017/11/9.
 */

public class ApiResponse<T> {

    public static final int CODE_OK = 1;
    public static final int CODE_ERR = 2;

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
