package com.lychee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lychee.mapper.RoleMapper;
import com.lychee.pojo.Role;
import com.lychee.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
