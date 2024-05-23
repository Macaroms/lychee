package com.lychee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lychee.pojo.User;

/**
 * @Author: jiangwei97@aliyun.com
 * @Date: 2024/5/11
 */
public interface IUserService extends IService<User> {

//    int deleteByIds(Long[] ids);
//
//    int addUser(User user);

    User findByUsername(String username);

    User findUserById(String userId);

}
