package com.example.maoyan.mapper;

import com.example.maoyan.entity.User;
import com.example.maoyan.entity.UserInfo;


public interface UserInfoMapper {

    public UserInfo findUserInfoById(String userId);

    public UserInfo selectUserInfo(String username);

    public UserInfo findUserInfoByUserName(String username);

    public void insertUserInfo(UserInfo userInfo);

    public void updateUserInfo(UserInfo userInfo);

}

