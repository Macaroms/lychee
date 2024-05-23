package com.lychee.service.impl;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lychee.common.CommonConstant;
import com.lychee.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: jiangwei97@aliyun.com
 * @Date: 2024/5/11
 */
@Service
public class TokenService {

    public String getToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getId())
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(Algorithm.HMAC256(CommonConstant.TOKEN_SECRET));

//        token = JWT.create().withAudience(user.getId()) // 将userid当作载荷
//                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //两小时后token过期
//                .sign(Algorithm.HMAC256("comXie"));


        return token;
    }
}
