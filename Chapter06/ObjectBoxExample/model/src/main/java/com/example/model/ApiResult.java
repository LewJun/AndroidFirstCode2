package com.example.model;

/**
 * Created by LewJun on 2018/02/01.
 */
public class ApiResult<T> {
    public int code;
    public String errmsg;
    public T data;

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", errmsg='" + errmsg + '\'' +
                ", data=" + data +
                '}';
    }
}
