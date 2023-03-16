package com.lychee.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.lychee.model.result.PoetryResult;
import com.lychee.service.IRemoteService;
import com.lychee.util.HttpClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/4/10 13:05
 */
@Service
public class RemoteService implements IRemoteService {

    @Resource
    private HttpClient httpClient;

    @Override
    public PoetryResult poetry() {
        String url = "https://v1.jinrishici.com/all.json";
        try {
            String result = httpClient.sendGet(url, new HashMap<>(), new HashMap<>());
            return JSONObject.parseObject(result, PoetryResult.class);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
