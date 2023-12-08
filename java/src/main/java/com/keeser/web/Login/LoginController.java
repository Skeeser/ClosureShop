package com.keeser.web.Login;

import com.keeser.web.User.User;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.common.ResultCode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import java.util.Objects;
import com.alibaba.fastjson.JSONObject;


@Controller  // 其作用是将用户提交来的请求通过URL匹配，分发给不同的接收器（具体的Controller），该接收器会对其进行相应处理，然后返回处理结果。
public class LoginController {
    @CrossOrigin  // 处理跨域资源共享（CORS）
    @PostMapping(value = "api/login")  // 方法是用来处理 HTTP POST 请求的
    @ResponseBody  // 表示方法的返回值将直接作为 HTTP 响应的内容返回，而不是寻找视图解析器来解析为视图
    public JSONObject login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        if (!Objects.equals("admin", username) || !Objects.equals("123456", requestUser.getPassword())) {
            String message = "账号密码错误";
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, message).getMetaJson();
        } else {
            return new ResultMetaJson(ResultCode.STATUS_OK, "登录成功").getMetaJson();
        }
    }
}
