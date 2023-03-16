package com.lychee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lychee.mapper.TestMapper;
import com.lychee.pojo.TestEntity;
import com.lychee.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/3/10 23:44
 */
@Service
public class TestService extends ServiceImpl<TestMapper, TestEntity> implements ITestService {

    private TestMapper testMapper;

    @Autowired
    public void setTestMapper(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Override
    public boolean creat(TestEntity testEntity) {
        int row = testMapper.insert(testEntity);
        return row > 0;
    }
}
