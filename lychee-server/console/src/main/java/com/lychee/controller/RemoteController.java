package com.lychee.controller;

import com.lychee.model.Result;
import com.lychee.model.result.PoetryResult;
import com.lychee.service.IRemoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/4/10 12:59
 */
@Api(tags = "远程API")
@RestController
@RequestMapping("/remote")
public class RemoteController {

    @Resource
    private IRemoteService remoteService;

    @ApiOperation(value = "随机诗词", notes = "随机诗词")
    @GetMapping("/poetry")
    public Result<PoetryResult> poetry() {
        return Result.ok(remoteService.poetry());
    }

}
