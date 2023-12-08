package com.keeser.web.common;

// 此类用于定义响应码

public class ResultCode {
    //响应码
    private int code;

    public ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
