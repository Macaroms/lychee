package com.lychee.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import com.lychee.mapper.TestMapper;
import com.lychee.model.param.*;
import com.lychee.model.result.HistoryResult;
import com.lychee.model.result.IpDataResult;
import com.lychee.model.result.PickTextResult;
import com.lychee.model.result.WeatherResult;
import com.lychee.pojo.TestEntity;
import com.lychee.service.ITextService;
import com.lychee.util.HttpClient;
import com.lychee.util.PropertiesUtil;
import com.lychee.util.ScriptExecUtil;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

    @Resource
    private PropertiesUtil propertiesUtil;

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
        return Base64.getEncoder().encodeToString(src.getBytes(StandardCharsets.UTF_8));
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
        String url = "https://query.asilu.com/today/list";
        try {
            String result = HttpRequest.get(url)
                    .timeout(25000)
                    .execute()
                    .body();
            JSONObject resultObject = JSONObject.parseObject(result);
            if (resultObject != null && "200".equals(resultObject.get("code").toString())) {
                return JSONObject.parseObject(
                        resultObject.get("data").toString(),
                        new TypeReference<List<HistoryResult>>() {
                        }
                );
            } else {
                return Lists.newArrayList();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String propsConvert(PropsConvertParam param) {
        Properties properties;
        if ("yaml".equals(param.getSource())) {
            properties = propertiesUtil.loadYaml(param.getText());
        } else if ("prop".equals(param.getSource())) {
            properties = propertiesUtil.loadProperties(param.getText());
        } else {
            return null;
        }
        return propertiesUtil.prop2Target(properties, param.getTarget());
    }

    @Override
    public String urlCoder(UrlCoderParam param) {
        String result;
        Integer type = param.getType();
        try {
            switch (type) {
                case 0:
                    result = URLEncoder.encode(param.getSrc(), param.getEnc());
                    break;
                case 1:
                    result = URLDecoder.decode(param.getSrc(), param.getEnc());
                    break;
                default:
                    result = param.getSrc();
            }
        } catch (UnsupportedEncodingException e) {
            result = param.getSrc();
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("传闻中的陈芊芊", "GBK"));
        System.out.println(URLEncoder.encode("传闻中的陈芊芊", "UTF-8"));
        System.out.println(URLEncoder.encode("传闻中的陈芊芊", "UTF-16"));
        System.out.println(URLEncoder.encode("传闻中的陈芊芊", "UTF-16BE"));
        System.out.println(URLEncoder.encode("传闻中的陈芊芊", "UTF-16LE"));


        String a = "%20%4F%FB%95%2D%4E%84%76%48%96%8A%82%8A%82";
        System.out.println(URLDecoder.decode(a, "UTF-16LE"));
    }

    @Override
    public String unicode(CoderParam param) {
        String result;
        Integer type = param.getType();
        try {
            switch (type) {
                case 0:
                    result = strToUnicode(param.getSrc(), true);
                    break;
                case 1:
                    result = unicodeToStr(param.getSrc());
                    break;
                default:
                    result = param.getSrc();
            }
        } catch (Exception e) {
            result = param.getSrc();
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String base64Coder(CoderParam param) {
        String result;
        switch (param.getType()) {
            case 0:
                result = Base64.getEncoder().encodeToString(param.getSrc().getBytes(StandardCharsets.UTF_8));
                break;
            case 1:
                byte[] decode = Base64.getDecoder().decode(param.getSrc().getBytes(StandardCharsets.UTF_8));
                result =  new String(decode, StandardCharsets.UTF_8);
                break;
            default:
                result = param.getSrc();
        }
        return result;
    }

    @Resource
    private ScriptExecUtil scriptExecUtil;

    @Override
    public String execJsScript(ExecScriptParam param) {
        List<ExecScriptParam.Arg> args = param.getArgs();
        Object[] array = getParamsByType(args);
        return scriptExecUtil.scriptEngineJs(param.getMethod(), param.getCode(), array);
    }

    private Object[] getParamsByType(List<ExecScriptParam.Arg> args){
        return args.stream().map(o -> {
            String type = o.getType();
            if ("text".equals(type)) {
                return o.getValue();
            } else if ("number".equals(type)) {
                return Integer.parseInt(o.getValue());
            } else {
                return o.getValue();
            }
        }).toArray(Object[]::new);
    }

    private String userIp(HttpServletRequest request) {
        for (String header : IP_HEADERS) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
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

    private String strToUnicode(String string, boolean halfWith) {
        if (string == null || string.isEmpty()) {
            // 传入字符串为空返回原内容
            return string;
        }
        StringBuilder value = new StringBuilder(string.length() << 3);
        String prefix = "\\u";
        String zeroFix = " 0 ";
        String unicode;
        char c;
        for (int i = 0, j; i < string.length(); i++) {
            c = string.charAt(i);
            if (!halfWith && c > 31 && c < 127) {
                // 不转换半角字符
                value.append(c);
                continue;
            }
            value.append(prefix);
            // 高 8 位
            j = c >>> 8;
            unicode = Integer.toHexString(j);
            if (unicode.length() == 1) {
                value.append(zeroFix);
            }
            value.append(unicode);
            // 低 8 位
            j = c & 0xFF;
            unicode = Integer.toHexString(j);
            if (unicode.length() == 1) {
                value.append(zeroFix);
            }
            value.append(unicode);
        }

        return value.toString();
    }

    private String unicodeToStr(String string) {
        String prefix = "\\u";
        if (string == null || !string.contains(prefix)) {
            // 传入字符串为空或不包含 Unicode 编码返回原内容
            return string;
        }
        StringBuilder value = new StringBuilder(string.length() >> 2);
        String[] strings = string.split("\\\\u");
        String hex, mix;
        char hexChar;
        int ascii, n;
        if (strings[0].length() > 0) {
            // 处理开头的普通字符串
            value.append(strings[0]);
        }
        try {
            for (int i = 1; i < strings.length; i++) {
                hex = strings[i];
                if (hex.length() > 3) {
                    mix = "";
                    if (hex.length() > 4) {
                        // 处理 Unicode 编码符号后面的普通字符串
                        mix = hex.substring(4, hex.length());
                    }
                    hex = hex.substring(0, 4);

                    try {
                        Integer.parseInt(hex, 16);
                    } catch (Exception e) {
                        // 不能将当前 16 进制字符串正常转换为 10 进制数字，拼接原内容后跳出
                        value.append(prefix).append(strings[i]);
                        continue;
                    }
                    ascii = 0;
                    for (int j = 0; j < hex.length(); j++) {
                        hexChar = hex.charAt(j);
                        // 将 Unicode 编码中的 16 进制数字逐个转为 10 进制
                        n = Integer.parseInt(String.valueOf(hexChar), 16);
                        // 转换为 ASCII 码
                        ascii += n * ((int) Math.pow(16, (hex.length() - j - 1)));
                    }
                    // 拼接解码内容
                    value.append((char) ascii).append(mix);
                } else {
                    // 不转换特殊长度的 Unicode 编码
                    value.append(prefix).append(hex);
                }
            }
        } catch (Exception e) {
            // Unicode 编码格式有误，解码失败
            return null;
        }
        return value.toString();
    }

}
