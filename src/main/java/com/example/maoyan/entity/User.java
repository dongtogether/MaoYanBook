package com.example.maoyan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class User {

    String userId;
    String username;
    String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "User [id=" + userId + ", username=" + username + ", password=" + password + ", ]";

    }
}

