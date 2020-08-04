package com.example.maoyan.mapper;

import com.example.maoyan.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface UserMapper {

    public User findUserById(String userId);

    public User selectUser(String username,String password);

    public User findByUserName(String username);

    public void insertUser(User user);

    public void updateUser(User user);

}

