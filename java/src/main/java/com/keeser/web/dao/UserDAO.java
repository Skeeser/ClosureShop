package com.keeser.web.dao;

import com.keeser.web.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 本接口负责用于与数据库直接交互, 定义增删改查等操作

// Data Access Object（数据访问对象，DAO）即用来操作数据库的对象
// 通过继承 JpaRepository 的方式构建 DAO。
public interface UserDAO extends JpaRepository<User, Integer>{
    User findByUsername(String username);

    User getByUsernameAndPassword(String username,String password);
}
