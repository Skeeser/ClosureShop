package com.keeser.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// 此类用来定义用户属性, 同时使用jpa建立对数据库的映射
// JPA 默认情况下会将字段名与数据库列名进行匹配，但在需要特殊命名的情况下
// （如数据库中的列名与 Java 中的字段名不一致），使用 @Column 注解可以手动指定映射关系。

@Entity  // 表示这是一个实体类
@Table(name = "sp_manager")  // 指定表名
// jpa生成的无需json化的属性, 忽略掉
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mg_id")
    int mgId;

    String mgName;
    String mgPwd;
    String mgTime;
    int roleId;
    String mgMobile;
    String mgEmail;
    int mgState;

    public User(){

    }

    public User(String mgName, String mgPwd, String mgMobile, String mgEmail){
        initUser();

        this.mgName = mgName;
        this.mgPwd = mgPwd;
        this.mgMobile = mgMobile;
        this.mgEmail = mgEmail;
    }

    public void initUser(){
        // 默认在线
        mgState = 1;
        // 默认为普通用户
        roleId = 31;
        // 计算当前时间
        mgTime =  LocalDateTime.now().toString();
    }

    public int getId() {
        return mgId;
    }

    public void setId(int id) {
        this.mgId = id;
    }

    public String getUsername() {
        return mgName;
    }

    public void setUsername(String username) {
        this.mgName = username;
    }

    public String getPassword() {
        return mgPwd;
    }

    public void setPassword(String password) {
        this.mgPwd = password;
    }

    public String getEmail(){
        return mgEmail;
    }

    public void setEmail(String email){
        this.mgEmail = email;
    }

    public String getMobile(){
        return mgMobile;
    }

    public void setMobile(String mobile){
        this.mgMobile = mobile;
    }

    public String getMgTime() {
        return mgTime;
    }

    public void setMgTime(String mgTime) {
        this.mgTime = mgTime;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMgState() {
        return mgState;
    }

    public void setMgState(int mgState) {
        this.mgState = mgState;
    }
}
