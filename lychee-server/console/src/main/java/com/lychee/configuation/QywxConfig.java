package com.lychee.configuation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class QywxConfig {

    /**
     * 企业微信ID
     */
    @Value("${qywx.corpid}")
    private String corpid;


    /**
     * 开发者设置的token
     */
    @Value("${qywx.token}")
    private String token;

    /**
     * 开发者设置的EncodingAESKey
     */
    @Value("${qywx.encodingAESKey}")
    private String encodingAESKey;
}
