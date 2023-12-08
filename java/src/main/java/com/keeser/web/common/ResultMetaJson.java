package com.keeser.web.common;

import com.keeser.web.common.ResultCode;

import com.alibaba.fastjson.JSONObject;


public class ResultMetaJson {

    private final String code_;
    private final String msg_;

    // 默认无效
    public ResultMetaJson() {
        this.code_ = ResultCode.STATUS_JSON_UNVAIL;
        this.msg_ = "无效的json";
    }

    // 设置返回码和提示信息
    public ResultMetaJson(String code, String msg) {
        this.code_ = code;
        this.msg_ = msg;
    }

    // 返回json对象
    public JSONObject getMetaJson(){
        JSONObject meta_json = new JSONObject();
        meta_json.put("meta", new JSONObject());
        JSONObject value_json =  meta_json.getJSONObject("meta");
        value_json.put("msg", this.msg_);
        value_json.put("status", this.code_);

        return meta_json;
    }

}
