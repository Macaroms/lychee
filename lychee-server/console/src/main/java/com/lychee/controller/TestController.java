package com.lychee.controller;

import com.lychee.pojo.TestEntity;
import com.lychee.service.ITestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/3/10 23:50
 */
@Api(tags = "测试控制层")
@RestController
@RequestMapping("/test")
public class TestController {

    private ITestService testService;

    @Autowired
    public void setTestService(ITestService testService) {
        this.testService = testService;
    }

    @ApiOperation(value = "新增", notes = "insert")
    @PostMapping("/create")
    public String creat(@RequestBody TestEntity testEntity) {
        boolean creat = testService.creat(testEntity);
        return creat ? "success" : "failed";
    }

}
