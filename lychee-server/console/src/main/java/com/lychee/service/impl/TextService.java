package com.lychee.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import com.lychee.mapper.TestMapper;
import com.lychee.model.param.ParseTextParam;
import com.lychee.model.result.HistoryResult;
import com.lychee.model.result.IpDataResult;
import com.lychee.model.result.PickTextResult;
import com.lychee.model.result.WeatherResult;
import com.lychee.pojo.TestEntity;
import com.lychee.service.ITextService;
import com.lychee.util.HttpClient;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/4/10 13:05
 */
@Service
public class TextService extends ServiceImpl<TestMapper, TestEntity> implements ITextService {

    static final String[] IP_HEADERS = {
            "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR", "X-Real-IP"
    };

    @Resource
    private HttpClient httpClient;

    @Override
    public List<String> getNextExecTime(String cronExpression, Integer numTimes) {
        List<String> list = new ArrayList<>();
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(cronExpression);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, numTimes);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Date date : dates) {
            list.add(dateFormat.format(date));
        }
        return list;
    }

    @Override
    public String base64Encode(String src) {
        String result = Base64.getEncoder().encodeToString(src.getBytes(StandardCharsets.UTF_8));
        return result;
    }

    @Override
    public String base64Decode(String src) {
        byte[] decode = Base64.getDecoder().decode(src.getBytes(StandardCharsets.UTF_8));
        return new String(decode, StandardCharsets.UTF_8);
    }

    @Override
    public String md5Encode(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public PickTextResult pickTextByPath(ParseTextParam param) {
        try {
            if ("json".equalsIgnoreCase(param.getType())) {
                return jsonPathVal(param.getSrc(), param.getPath());
            } else if ("xml".equalsIgnoreCase(param.getType())) {
                return xmlPathVal(param.getSrc(), param.getPath());
            } else {
                throw new RuntimeException("unknown type");
            }
        } catch (Exception e) {
            return new PickTextResult(0, JSONObject.toJSONString(e.getMessage()));
        }
    }

    @Override
    public IpDataResult ipData(String ip) {
        String url = "https://api.vore.top/api/IPdata";
        try {
            ImmutableMap<String, String> param = ImmutableMap.of("ip", ip);
            String result = httpClient.sendGet(url, new HashMap<>(), param);
            JSONObject resultObject = JSONObject.parseObject(result);
            JSONObject adcode = resultObject.getJSONObject("adcode");
            if ("200".equals(resultObject.get("code").toString())) {
                return new IpDataResult(
                        resultObject.getJSONObject("ipinfo").get("type").toString(),
                        resultObject.getJSONObject("ipinfo").get("text").toString(),
                        adcode.get("r") != null ? adcode.get("r").toString() : "",
                        adcode.get("o") != null ? adcode.get("o").toString() : "",
                        adcode.get("a") != null ? adcode.get("a").toString() : ""
                );
            } else {
                return null;
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public WeatherResult weather(HttpServletRequest request) {
        String ip = userIp(request);
        String url = "https://api.vore.top/api/Weather";
        try {
            ImmutableMap<String, String> param = ImmutableMap.of("ip", ip);
            String result = httpClient.sendGet(url, new HashMap<>(), param);
            JSONObject resultObject = JSONObject.parseObject(result);
            if ("200".equals(resultObject.get("code").toString())) {
                JSONObject weatherInfo = resultObject.getJSONObject("data").getJSONObject("tianqi");
                return new WeatherResult(
                        ip,
                        weatherInfo.get("province").toString(),
                        weatherInfo.get("city").toString(),
                        weatherInfo.get("adcode").toString(),
                        weatherInfo.get("weather").toString(),
                        weatherInfo.get("temperature").toString(),
                        weatherInfo.get("humidity").toString(),
                        weatherInfo.get("winddirection").toString(),
                        weatherInfo.get("windpower").toString(),
                        weatherInfo.get("reporttime").toString()
                );
            } else {
                return null;
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<HistoryResult> history() {
        String url = "https://api.vore.top/api/history";
        try {
            String result = httpClient.sendGet(url, new HashMap<>(), new HashMap<>());
            JSONObject resultObject = JSONObject.parseObject(result);
            if ("200".equals(resultObject.get("code").toString())) {
                return JSONObject.parseObject(
                        resultObject.get("data").toString(),
                        new TypeReference<List<HistoryResult>>() {
                        }
                );
            } else {
                return Lists.newArrayList();
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String userIp(HttpServletRequest request) {
        for (String header : IP_HEADERS) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    private PickTextResult jsonPathVal(String src, String jsonPath) {
        Object read = JsonPath.read(src, jsonPath);
        int length;
        if (read instanceof Collection) {
            length = ((List<?>) read).size();
        } else {
            length = 0;
        }
        return new PickTextResult(length, JSONObject.toJSONString(read));
    }

    private PickTextResult xmlPathVal(String src, String xmlPath) {
        JXDocument jxDocument = JXDocument.create(src);
        List<Object> nodes = jxDocument.sel(xmlPath.replace("html", ""));
        List<String> collect = nodes.stream().map(Object::toString).collect(Collectors.toList());
        return new PickTextResult(nodes.size(), JSONObject.toJSONString(collect));
    }
}
