package com.keeser.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MenusController {
    @Autowired
    MenusService menusService;

    @CrossOrigin  // 处理跨域资源共享（CORS）
    @GetMapping(value = "api/menus")  // 方法是用来处理 HTTP POST 请求的
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject getMenus() {
        return menusService.getMenus();
    }
}
