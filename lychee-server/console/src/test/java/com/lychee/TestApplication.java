package com.lychee;

import com.google.common.collect.ImmutableMap;
import com.lychee.util.HttpClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/11/17 22:53
 */

@SpringBootApplication
public class TestApplication {

    @Autowired
    HttpClient httpClient;

    @Test
    public void test() throws IOException, URISyntaxException {
        String url = "https://api.vore.top/api/IPdata?ip=117.136.12.79";
        ImmutableMap<String, String> ip = ImmutableMap.of("ip", "117.136.12.79");
        String s = httpClient.sendGet(url, new HashMap<>(), ip);
        System.out.println(s);


    }

}
