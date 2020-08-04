package com.example.maoyan.controller;

import com.example.maoyan.entity.User;
import com.example.maoyan.entity.UserInfo;
import com.example.maoyan.result.Result;
import com.example.maoyan.result.ResultFactory;
import com.example.maoyan.service.UserService;
import com.example.maoyan.utils.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 个人详情资料
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/api/user/userInfo/{username}")
    @ResponseBody
    public Result userInfoDetails(@PathVariable("username") String userId) throws Exception {
        UserInfo userInfo = userService.findUserInfoById(userId);
        return ResultFactory.buildSuccessResult(userInfo);
    }

    /**
     * 个人信息修改
     * @param userInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/api/user/editUserInfo")
    public Result editUserInfo(@RequestBody UserInfo userInfo) throws Exception {
        //id存在则是修改，没有则新增
        if(userInfo.getUserId() != null && userInfo.getUserId() != ""){
            User modifyUser = userService.findUserById(userInfo.getUserId());
            if(modifyUser != null){
                modifyUser.setUsername(userInfo.getUsername());
                modifyUser.setPassword(userInfo.getPassword());
                userService.updateUser(modifyUser);
            }
            userService.updateUserInfo(userInfo);
        } else {
            userService.registerUserInfo(userInfo);
        }
        return ResultFactory.buildSuccessResult("更改成功");
    }

    /**
     * 密码重置
     * @param userInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/api/user/reset-password")
    public Result resetPassword(@RequestBody UserInfo userInfo) throws Exception {
        User modifyUser = userService.findByUserName(userInfo.getUsername());
        UserInfo modifyUserInfo = userService.findUserInfoByUserName(userInfo.getUsername());
        if(modifyUserInfo != null && modifyUser != null){
            modifyUser.setPassword("123456");
            userService.updateUser(modifyUser);

            modifyUserInfo.setPassword("123456");
            userService.updateUserInfo(modifyUserInfo);
            return ResultFactory.buildSuccessResult("密码重置成功");
        } else {
            return ResultFactory.buildFailResult("不存在该用户");
        }

    }

}
