package com.lychee.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/4/3 22:49
 */
@Component
public class PropertiesUtil {

    /**
     * 加载获取yaml配置文件
     */
    public Properties loadYaml(String yamlStr) {
        Properties properties = new Properties();
        try {
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ByteArrayResource(yamlStr.getBytes()));
            properties = yaml.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 加载获取properties配置文件
     */
    public Properties loadProperties(String propertiesStr) {
        ByteArrayInputStream fis = null;
        Properties properties = new Properties();
        try {
            fis = new ByteArrayInputStream(propertiesStr.getBytes());
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    /**
     * 把 properties 转化为 yaml/prop 格式
     */
    public String prop2Target(Properties properties, String target) {
        try {
            if (properties == null) {
                return null;
            }
            StringBuffer ymlString;
            if ("yaml".equals(target)) {
                ymlString = PropertiesToMapUtil.prop2YmlString(properties);
            } else if ("prop".equals(target)) {
                ymlString = PropertiesToMapUtil.prop2PropString(properties);
            } else {
                return null;
            }
            return ymlString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把字符串写到指定的文件
     */
    public void writeStr2File(StringBuffer msg, String filePath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            //将字符串写到指定文件
            fos.write(msg.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
