package com.lychee.controller;

import com.lychee.model.Result;
import com.lychee.model.param.CodeSrcParam;
import com.lychee.model.param.ParseTextParam;
import com.lychee.model.result.HistoryResult;
import com.lychee.model.result.IpDataResult;
import com.lychee.model.result.WeatherResult;
import com.lychee.service.ITextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/4/10 12:59
 */
@Api(tags = "文本编辑器")
@RestController
@RequestMapping("/text")
public class TextController {

    private ITextService textService;

    @Autowired
    public void setTextService(ITextService textService) {
        this.textService = textService;
    }

    @ApiOperation(value = "下一(几)次运行的时间", notes = "下一(几)次运行的时间")
    @GetMapping("/getNextExecTime")
    public Result<List<String>> getNextExecTime(@RequestParam String cronExpression, @RequestParam Integer numTimes) {
        return Result.ok(textService.getNextExecTime(cronExpression, numTimes));
    }

    @ApiOperation(value = "base64编码", notes = "base64编码")
    @PostMapping("/base64Encode")
    public Result<String> base64Encode(@RequestBody CodeSrcParam param) {
        return Result.ok(textService.base64Encode(param.getSrc()));
    }

    @ApiOperation(value = "base64解码", notes = "base64解码")
    @PostMapping("/base64Decode")
    public Result<String> base64Decode(@RequestBody CodeSrcParam param) {
        return Result.ok(textService.base64Decode(param.getSrc()));
    }

    @ApiOperation(value = "md5加密(32位)", notes = "md5加密(32位)")
    @PostMapping("/md5Encode")
    public Result<String> md5Encode(@RequestBody CodeSrcParam param) {
        return Result.ok(textService.md5Encode(param.getSrc()));
    }

    @ApiOperation(value = "提取字符串内容", notes = "提取字符串内容")
    @PostMapping("/pickTextByPath")
    public Result<String> pickTextByPath(@RequestBody ParseTextParam param) {
        return Result.ok(textService.pickTextByPath(param));
    }

    @ApiOperation(value = "IP信息查询", notes = "IP信息查询")
    @GetMapping("/ipData")
    public Result<IpDataResult> ipData(@RequestParam String ip) {
        IpDataResult ipDataResult = textService.ipData(ip);
        if (ipDataResult == null) {
            return Result.fail(null);
        } else {
            return Result.ok(ipDataResult);
        }
    }

    @ApiOperation(value = "获取天气", notes = "获取天气")
    @GetMapping("/weather")
    public Result<WeatherResult> weather(HttpServletRequest request) {
        WeatherResult weatherResult = textService.weather(request);
        if (weatherResult == null) {
            return Result.fail(null);
        } else {
            return Result.ok(weatherResult);
        }
    }

    @ApiOperation(value = "那年今日", notes = "那年今日")
    @GetMapping("/history")
    public Result<List<HistoryResult>> history() {
        return Result.ok(textService.history());
    }

}