package com.lychee.interceptor;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lychee.annotation.AuthorizationIgnore;
import com.lychee.common.CommonConstant;
import com.lychee.model.Result;
import com.lychee.pojo.User;
import com.lychee.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author: jiangwei97@aliyun.com
 * @Date: 2024/5/11
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Resource
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("Authorization");
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(AuthorizationIgnore.class)) {
            AuthorizationIgnore passToken = method.getAnnotation(AuthorizationIgnore.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        // 执行认证
        if (token == null) {
            return permissionDenied(httpServletResponse, "no not have token");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            return permissionDenied(httpServletResponse, "permission denied, please login");
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            return permissionDenied(httpServletResponse, "can not find user");
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(CommonConstant.TOKEN_SECRET)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return permissionDenied(httpServletResponse, "permission denied, please login");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean permissionDenied(HttpServletResponse httpServletResponse, String msg) throws IOException {
        Result<String> commonResponse = new Result<>(HttpStatus.UNAUTHORIZED.value(), msg, false);
        httpServletResponse.getWriter().write(JSON.toJSONString(commonResponse));
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        log.error(msg);
        return false;
    }
}
