package com.lychee.controller;


import com.alibaba.fastjson2.JSONObject;
import com.lychee.annotation.AuthorizationIgnore;
import com.lychee.common.CodeMsg;
import com.lychee.model.Result;
import com.lychee.pojo.User;
import com.lychee.service.IUserService;
import com.lychee.service.impl.TokenService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: jiangwei97@aliyun.com
 * @Date: 2024/5/11
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private TokenService tokenService;

    /**
     * 查询用户信息
     */
    @GetMapping("/list")
    public Result<Object> list(){
        return Result.ok(userService.list());
    }


    /**
     * 登录验证
     */
    @AuthorizationIgnore
    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public Result<String> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        //获取用户账号密码
        User user = userService.findByUsername(username);
        //判断账号或密码是否正确
        if (user == null) {
            return Result.fail(401, "用户不存在！");
        } else
        if (!user.getPassword().equals(password)) {
            return Result.fail(401, "账号或者密码错误，请重试！");
        } else {
            String token = tokenService.getToken(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return Result.ok(token);
        }
    }
}
