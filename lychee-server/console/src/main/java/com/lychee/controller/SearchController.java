package com.lychee.controller;

import com.lychee.annotation.AuthorizationIgnore;
import com.lychee.model.Result;
import com.lychee.service.ISearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/5/29 1:54
 */
@Api(tags = "查询")
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private ISearchService searchService;

    @AuthorizationIgnore
    @ApiOperation(value = "IP信息查询", notes = "IP信息查询")
    @GetMapping("/ipSearch")
    public Result<?> ipSearch(@RequestParam String ip) {
        return searchService.ipData(ip);
    }

    @AuthorizationIgnore
    @ApiOperation(value = "垃圾分类查询", notes = "垃圾分类查询")
    @GetMapping("/rubbishClassify")
    public Result<?> rubbishClassify(@RequestParam String name) {
        return searchService.rubbishClassify(name);
    }

}
