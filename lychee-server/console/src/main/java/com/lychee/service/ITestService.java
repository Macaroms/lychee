package com.lychee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lychee.pojo.TestEntity;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/3/10 23:43
 */
public interface ITestService extends IService<TestEntity> {

    boolean creat(TestEntity testEntity);

}
