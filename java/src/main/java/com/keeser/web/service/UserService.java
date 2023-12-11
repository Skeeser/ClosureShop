package com.keeser.web.service;

import com.keeser.web.common.ResultCode;
import com.keeser.web.common.ResultMetaJson;
import com.keeser.web.entity.User;
import com.keeser.web.dao.UserDAO;
import com.keeser.web.utils.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;

// 用于封装User DAO的类, 负责业务逻辑, 功能相关代码在这写

@Service
public class UserService {
    // 在 Service 类中使用 @Autowired 注解可以告诉 Spring 自动注入对应的 Repository 实例，
    // 而不需要手动创建或者在配置文件中进行显式配置。
    @Autowired
    UserDAO userDAO;
    @Autowired
    JwtTokenUtil tokenUtil;

    public boolean isExist(String username) {
        User user = getByName(username);
        return null!=user;
    }

    public User getByName(String username) {
        return userDAO.findByMgName(username);
    }

    public User get(String username, String password){
        return userDAO.getByMgNameAndMgPwd(username, password);
    }

    public User add(User user) {
        return userDAO.save(user);
    }

    // 验证登录
    public JSONObject checkLogin(String username, String password){
        User user = this.get(username, password);
        if (null == user) {
            String message = "账号密码错误";
            return new ResultMetaJson(ResultCode.STATUS_BAD_REQUEST, message).getMetaJson();
        }
        JSONObject ret_json = new ResultMetaJson(ResultCode.STATUS_OK, "登录成功").getMetaJson();
        // 登录成功, 获取token
        String token = tokenUtil.generateToken(username);
        JSONObject dataJson = new JSONObject();
        dataJson.put("id", user.getId());
        dataJson.put("rid", user.getRoleId());
        dataJson.put("username", user.getUsername());
        dataJson.put("mobile", user.getMobile());
        dataJson.put("email", user.getEmail());
        dataJson.put("token", token);
        // ret_json.put("data", dataJson);
        return ret_json;

    }
}
