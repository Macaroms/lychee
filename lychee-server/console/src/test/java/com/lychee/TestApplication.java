package com.lychee;

import com.lychee.util.PropertiesUtil;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/11/17 22:53
 */

@SpringBootApplication
public class TestApplication {

    @Resource
    PropertiesUtil propertiesUtil;

    @Test
    public void test() {
        String str = "server.servlet.context-path=/api\n" +
                "server.port=8081\n" +
                "\n" +
                "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver";
        Properties properties = propertiesUtil.loadYaml(str);
        String s = propertiesUtil.prop2Target(properties, "prop");
        System.out.println(s);

    }

}
