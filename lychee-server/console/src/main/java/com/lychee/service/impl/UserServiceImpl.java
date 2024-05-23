package com.lychee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lychee.mapper.UserMapper;
import com.lychee.pojo.User;
import com.lychee.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jiangwei97@aliyun.com
 * @Date: 2024/5/11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 判断用户名
     */
    @Override
    public User findByUsername(String username){
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username));
    }

    @Override
    public User findUserById(String userId) {
        return userMapper.selectById(userId);
    }

}
