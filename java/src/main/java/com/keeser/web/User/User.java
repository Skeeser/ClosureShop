package com.keeser.web.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

// 此类用来定义用户属性, 同时使用jpa建立对数据库的映射

@Entity  // 表示这是一个实体类
@Table(name = "sp_manager")  // 指定表名
// jpa生成的无需json化的属性, 忽略掉
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mg_id")

    int id;
    String username;
    String password;
    String email;
    String mobile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
}
