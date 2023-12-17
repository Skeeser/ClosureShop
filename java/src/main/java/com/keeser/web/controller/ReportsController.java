package com.keeser.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportsController {
    @Autowired
    ReportsService reportsService;

    @CrossOrigin  // 处理跨域资源共享（CORS）
    @GetMapping(value = "/api/reports")
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject getGoodsList() {
        // reportsService.randomHotNumber();
        return reportsService.getHotNumberReport();
    }
}
