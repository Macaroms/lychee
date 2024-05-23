package com.lychee.controller;

import com.lychee.annotation.AuthorizationIgnore;
import com.lychee.model.Result;
import com.lychee.model.param.TranslateParam;
import com.lychee.model.result.PoetryResult;
import com.lychee.model.result.TranslateResult;
import com.lychee.service.IRemoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @AuthorizationIgnore
    @ApiOperation(value = "随机诗词", notes = "随机诗词")
    @GetMapping("/poetry")
    public Result<PoetryResult> poetry() {
        return Result.ok(remoteService.poetry());
    }

    @AuthorizationIgnore
    @ApiOperation(value = "翻译", notes = "翻译")
    @PostMapping("/translate")
    public Result<TranslateResult> translate(@RequestBody TranslateParam param) {
        TranslateResult translate = remoteService.translate(param);
        if(translate == null){
            return Result.fail(null);
        } else {
            return Result.ok(translate);
        }
    }

}
