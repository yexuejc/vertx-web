package com.yexuejc.vertx.web.base.result;

import io.vertx.core.json.Json;

/**
 * 相应消息类型
 *
 * @ClassName: ResultOb
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:23
 */
public class ResultOb<T> {
    private int code = 200;//状态
    private String msg = "SUCCESS";//消息
    private T data;

    public T getData() {
        return data;
    }

    public ResultOb setData(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResultOb setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultOb setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return Json.encode(this);
    }

    public static ResultOb build() {
        return new ResultOb();
    }
}
