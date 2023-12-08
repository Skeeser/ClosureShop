package com.keeser.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.entity.User;
import com.keeser.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class UserManageController {
    @Autowired
    UserService userService;

    @CrossOrigin  // 处理跨域资源共享（CORS）
    @PostMapping(value = "api/users")  // 方法是用来处理 HTTP POST 请求的
    @ResponseBody
    public JSONObject register(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        // 初始化对象
        requestUser.initUser();
        User user = null;
        try {
            // 通过user service获取user对象
            user = userService.add(requestUser);
        }catch (Exception e){
            String message = "注册失败";
            return new ResultMetaJson(ResultCode.STATUS_UNPROCESSABLE_ENTITY, message).getMetaJson();
        }

        if (null == user) {
            String message = "注册失败";
            return new ResultMetaJson(ResultCode.STATUS_UNPROCESSABLE_ENTITY, message).getMetaJson();
        } else {
            return new ResultMetaJson(ResultCode.STATUS_CREATED, "注册成功").getMetaJson();
        }
    }

}
