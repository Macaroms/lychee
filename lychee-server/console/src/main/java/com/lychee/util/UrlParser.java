package com.lychee.util;

import com.google.common.collect.ImmutableMap;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/3/12 19:47
 */
public class UrlParser {
    /**
     * add a param into uri, if the param is present, will replace it with value param.
     *
     * @param name  param name
     * @param value param value
     */
    public static String setQueryParam(String url, String name, String value) throws URISyntaxException {
        return setQueryParams(url, ImmutableMap.of(name, value));
    }

    /**
     * similar with setQueryParam method
     */
    public static String setQueryParams(String url, Map<String, String> params) throws URISyntaxException {
        URI uri = new URI(url);
        if (params == null || params.isEmpty()) {
            return uri.toString();
        }
        List<NameValuePair> pairs = URLEncodedUtils.parse(uri, Charset.defaultCharset());
        for (NameValuePair p : pairs) {
            if (params.containsKey(p.getName())) {
                // NameValuePair can not be modified, so remove it, and add a new one later.
                pairs.remove(p);
                break;
            }
        }
        params.forEach((k, v) -> pairs.add(new BasicNameValuePair(k, v)));
        String parameters = URLEncodedUtils.format(pairs, Charset.defaultCharset());
        return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), parameters, uri.getFragment()).toString();
    }

    public static Map<String, String> getQueryParams(String targetUrl) {
        URI uri;
        try {
            uri = new URI(targetUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<NameValuePair> params = URLEncodedUtils.parse(uri, Charset.defaultCharset());
        Map<String, String> map = new HashMap<>();
        for (NameValuePair param : params) {
            map.put(param.getName(), param.getValue());
        }
        return map;
    }

    public static String getParam(String url, String name) {
        String value = "";
        try {
            List<NameValuePair> args = URLEncodedUtils.parse(new URI(url), Charset.defaultCharset());
            for (NameValuePair temp : args) {
                String key = temp.getName();
                if (key.equals(name)) {
                    value = temp.getValue();
                    break;
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Map<String, String> getParam(String url) {
        Map<String, String> paramMap = new HashMap<>();
        try {
            List<NameValuePair> extraList = URLEncodedUtils.parse(new URI(url), Charset.defaultCharset());
            for (NameValuePair item : extraList) {
                String name = item.getName();
                int i = 0;
                while (paramMap.containsKey(name)) {
                    name = item.getName() + ++i;
                }
                paramMap.put(name, item.getValue());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return paramMap;
    }

    public static String formatParamsWithoutUrlEncode(Collection<NameValuePair> pairs) {
        StringBuilder sb = new StringBuilder();
        for (NameValuePair pair : pairs) {
            sb.append(pair.getName()).append("=").append(pair.getValue()).append("&");
        }
        // remove last '&'
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String urlWithOutPageParam(String url, String pageParam){
        Map<String, String> queryParams = getQueryParams(url);
        queryParams.remove(pageParam);
        try {
            return setQueryParams(url.split("\\?")[0], queryParams);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
