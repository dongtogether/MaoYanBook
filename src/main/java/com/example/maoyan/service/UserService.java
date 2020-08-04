package com.example.maoyan.service;

import com.example.maoyan.entity.User;
import com.example.maoyan.entity.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User selectUser(String username, String password);

    public User findUserById(String  userId);

    public User findByUserName(String username);

    public boolean isExist(String username);

    public void registerUser(User user);

    public void updateUser(User user);

    //用户详情表
    public UserInfo findUserInfoById(String userId);

    public UserInfo findUserInfoByUserName(String username);

    public void registerUserInfo(UserInfo userInfo);

    public void updateUserInfo(UserInfo userInfo);
}
