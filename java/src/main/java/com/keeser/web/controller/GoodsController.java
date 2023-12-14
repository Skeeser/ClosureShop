package com.keeser.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.keeser.web.entity.User;
import com.keeser.web.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @CrossOrigin  // 处理跨域资源共享（CORS）
    @GetMapping(value = "/api/goods")
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject getGoodsList(@RequestParam String query, @RequestParam int pagenum, @RequestParam int pagesize) {

        return goodsService.getGoodsList(query, pagenum, pagesize);
    }

    @CrossOrigin  // 处理跨域资源共享（CORS）
    @GetMapping(value = "/api/goods/{id}")
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject getGoodsList(@PathVariable(name = "id") int id) {
        return goodsService.getGoodsById(id);
    }

    @CrossOrigin  // 处理跨域资源共享（CORS）
    @PutMapping(value = "/api/goods/{id}")
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject editGoodsById(@PathVariable(name = "id") int id, @RequestBody JSONObject editGoods) {
        return goodsService.editGoodsById(id, editGoods);
    }

    @CrossOrigin  // 处理跨域资源共享（CORS）
    @PostMapping(value = "/api/goods")
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject addGoodsById(@RequestBody JSONObject addGoods) {
        return goodsService.addGoods(addGoods);
    }

}
