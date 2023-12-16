package com.keeser.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.keeser.web.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrdersController {
    @Autowired
    OrdersService ordersService;


    @CrossOrigin  // 处理跨域资源共享（CORS）
    @GetMapping(value = "/api/orders")
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject getOrdersList(@RequestParam String query, @RequestParam int pagenum, @RequestParam int pagesize) {

        return ordersService.getOrdersList(query, pagenum, pagesize);
    }

    @CrossOrigin  // 处理跨域资源共享（CORS）
    @PostMapping(value = "/api/orders/goods")
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject addOrders(@RequestBody JSONObject addOdersJson) {
        return ordersService.addOrders(addOdersJson);
    }

}
