package com.keeser.web.User;

// 此类用来定义用户属性

public class User {
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
