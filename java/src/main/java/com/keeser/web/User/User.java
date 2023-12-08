package com.keeser.web.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

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
    int mg_id;

    String mg_name;
    String mg_pwd;
    int mg_time;
    int role_id;
    String mg_mobile;
    String email;
    int mg_state;

    public int getId() {
        return mg_id;
    }

    public void setId(int id) {
        this.mg_id = id;
    }

    public String getUsername() {
        return mg_name;
    }

    public void setUsername(String username) {
        this.mg_name = username;
    }

    public String getPassword() {
        return mg_pwd;
    }

    public void setPassword(String password) {
        this.mg_pwd = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getMobile(){
        return mg_mobile;
    }

    public void setMobile(String mobile){
        this.mg_mobile = mobile;
    }

    public int getMgTime() {
        return mg_time;
    }

    public void setMgTime(int mgTime) {
        this.mg_time = mgTime;
    }

    public int getRoleId() {
        return role_id;
    }

    public void setRoleId(int roleId) {
        this.role_id = roleId;
    }

    public int getMgState() {
        return mg_state;
    }

    public void setMgState(int mgState) {
        this.mg_state = mgState;
    }
}
