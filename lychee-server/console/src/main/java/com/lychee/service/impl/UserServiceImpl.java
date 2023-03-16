package com.lychee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lychee.mapper.UserMapper;
import com.lychee.pojo.User;
import com.lychee.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang
 * @since 2021-11-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
