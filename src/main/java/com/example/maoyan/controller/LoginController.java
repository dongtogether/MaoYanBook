package com.example.maoyan.controller;

import com.example.maoyan.config.CorsConfig;
import com.example.maoyan.entity.UserInfo;
import com.example.maoyan.result.Result;
import com.example.maoyan.result.ResultFactory;
import com.example.maoyan.service.UserService;
import com.example.maoyan.utils.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import com.example.maoyan.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class LoginController {
    @Autowired
    UserService userService;

    /**
     * 用户登录
     * @param requestUser
     * @return
     */
    //@RequestMapping(value = "api/login",method = RequestMethod.POST)
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User user = userService.selectUser(username, requestUser.getPassword());
        if (null == user) {
            String message = "用户名或密码错误";
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(user);
        }
    }

    /**
     * 用户登出
     * @return
     */
    @GetMapping("api/logout")
    public Result logout() {
        return ResultFactory.buildSuccessResult("退出成功");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("api/register")
    public Result register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);

        boolean exist = userService.isExist(username);
        if (exist) {
            String message = "用户名已被使用";
            return ResultFactory.buildFailResult(message);
        }
        String id = OrderCodeFactory.generateUserID();
        user.setUserId(id);
        userService.registerUser(user);

        /*用户详情表添加*/
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        String nowDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
        userInfo.setRegisterDate(nowDay);
        userService.registerUserInfo(userInfo);

        return ResultFactory.buildSuccessResult("注册成功");
    }

}


