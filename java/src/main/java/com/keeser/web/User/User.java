package com.keeser.web.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

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
    int mgTime;
    int roleId;
    String mgMobile;
    String email;
    int mgState;

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
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getMobile(){
        return mgMobile;
    }

    public void setMobile(String mobile){
        this.mgMobile = mobile;
    }

    public int getMgTime() {
        return mgTime;
    }

    public void setMgTime(int mgTime) {
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
