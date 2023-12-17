package com.keeser.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

// 负责购买相关

@Controller
public class BuyController {

    @Autowired
    BuyService buyService;


    @CrossOrigin  // 处理跨域资源共享（CORS）
    @PostMapping(value = "/api/buy")
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject payForOrder(@RequestBody JSONObject payJson) {
        return buyService.payForOrder(payJson);
    }
}
