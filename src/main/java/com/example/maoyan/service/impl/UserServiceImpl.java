package com.example.maoyan.service.impl;


import com.example.maoyan.entity.User;
import com.example.maoyan.entity.UserInfo;
import com.example.maoyan.mapper.UserInfoMapper;
import com.example.maoyan.mapper.UserMapper;
import com.example.maoyan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    public User selectUser(String username,String password) {
        return userMapper.selectUser(username, password);
    }

    public User findUserById(String UserId){
        return userMapper.findUserById(UserId);
    }

    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    public boolean isExist(String username) {
        User user = findByUserName(username);
        return null!=user;
    }

    public void registerUser(User user){
         userMapper.insertUser(user);
    }

    public void updateUser(User user){
         userMapper.updateUser(user);
    }

    /*
    用户详情表
     */
    /*查找*/
    public UserInfo findUserInfoById(String userId) {
        return userInfoMapper.findUserInfoById(userId);
    }

    public UserInfo findUserInfoByUserName(String username) {
        return userInfoMapper.findUserInfoByUserName(username);
    }

    /*注册插入*/
    public void registerUserInfo(UserInfo userInfo){
        userInfoMapper.insertUserInfo(userInfo);
    }

    /*修改更新*/
    public void updateUserInfo(UserInfo userInfo) {
        userInfoMapper.updateUserInfo(userInfo);
    }
}

