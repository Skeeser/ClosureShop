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

    int id_;
    String username_;
    String password_;
    String email_;
    String mobile_;

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        this.id_ = id;
    }

    public String getUsername() {
        return username_;
    }

    public void setUsername(String username) {
        this.username_ = username;
    }

    public String getPassword() {
        return password_;
    }

    public void setPassword(String password) {
        this.password_ = password;
    }

    public String getEmail(){
        return email_;
    }

    public void setEmail(String email){
        this.email_ = email;
    }

    public String getMobile(){
        return mobile_;
    }

    public void setMobile(String mobile){
        this.mobile_ = mobile;
    }
}
