package com.lychee.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.lychee.model.Result;
import com.lychee.model.result.IpSearchResult;
import com.lychee.model.result.RubbishClassifyResult;
import com.lychee.service.ISearchService;
import com.lychee.util.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/5/29 01:35
 */
@Service
public class SearchService implements ISearchService {

    @Resource
    private HttpClient httpClient;

    @Value("${txapi.token}")
    private String txApiToken;

    @Override
    public Result<?> ipData(String ip) {
        String url = "http://ip-api.com/json/%s?lang=zh-CN";
        try {
            String result = httpClient.sendGet(String.format(url, ip), new HashMap<>(), new HashMap<>());
            IpSearchResult ipSearchResult = JSONObject.parseObject(result, IpSearchResult.class);
            if ("success".equals(ipSearchResult.getStatus())) {
                return Result.ok(ipSearchResult);
            } else {
                return Result.fail(500, "查询失败");
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Result<?> rubbishClassify(String name) {
        String url = "http://api.txapi.cn/v1/c/rubbish_classify_query";
        try {
            ImmutableMap<String, String> param = ImmutableMap.of("token", txApiToken, "name", name);
            String result = httpClient.sendGet(url, new HashMap<>(), param);
            RubbishClassifyResult rubbishClassifyResult = JSONObject.parseObject(result, RubbishClassifyResult.class);
            if ("OK".equals(rubbishClassifyResult.getMsg())) {
                return Result.ok(rubbishClassifyResult);
            } else {
                return Result.fail(500, "未查询到信息");
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
