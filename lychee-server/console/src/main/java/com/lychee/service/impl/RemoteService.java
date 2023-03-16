package com.lychee.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.lychee.model.param.TranslateParam;
import com.lychee.model.result.PoetryResult;
import com.lychee.model.result.TranslateResult;
import com.lychee.service.IRemoteService;
import com.lychee.service.ITextService;
import com.lychee.util.HttpClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${baidu.appid}")
    private String appid;

    @Value("${baidu.key}")
    private String key;

    @Resource
    private HttpClient httpClient;

    @Resource
    private ITextService textService;

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

    @Override
    public TranslateResult translate(TranslateParam param) {
        String url = "https://fanyi-api.baidu.com/api/trans/vip/translate";
        String salt = "lychee";
        ImmutableMap<String, String> queryParam = ImmutableMap.of(
                "appid", appid,
                "from", StringUtils.isEmpty(param.getFrom()) ? "auto" : param.getFrom(),
                "to", param.getTo(),
                "salt", salt,
                "sign", getBaiduSign(param.getQuery(), salt),
                "q", param.getQuery()
        );
        try {
            String result = httpClient.sendGet(url, new HashMap<>(), queryParam);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject.get("error_code") == null){
                return new TranslateResult(
                        jsonObject.get("from").toString(),
                        jsonObject.get("to").toString(),
                        jsonObject.getJSONArray("trans_result").getJSONObject(0).get("src").toString(),
                        jsonObject.getJSONArray("trans_result").getJSONObject(0).get("dst").toString()
                );
            } else {
                return null;
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getBaiduSign(String query, String salt){
        String src = appid + query + salt + key;
        return textService.md5Encode(src);
    }

}
