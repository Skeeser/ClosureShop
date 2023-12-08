package com.keeser.web.service;

import com.keeser.web.entity.User;
import com.keeser.web.dao.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 用于封装User DAO的类, 负责业务逻辑, 功能相关代码在这写

@Service
public class UserService {
    // 在 Service 类中使用 @Autowired 注解可以告诉 Spring 自动注入对应的 Repository 实例，
    // 而不需要手动创建或者在配置文件中进行显式配置。
    @Autowired
    UserDAO userDAO;

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
}
