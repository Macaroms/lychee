package com.lychee.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.lychee.aes.AesException;
import com.lychee.aes.WXBizMsgCrypt;
import com.lychee.configuation.QywxConfig;
import com.lychee.service.IQywxService;
import com.lychee.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class QywxService implements IQywxService {

    @Resource
    private QywxConfig qywxConfig;

    @Override
    public String verificationUrl(HttpServletRequest request) {
        log.info("=========验证URL有效性开始=========");
        String sEchoStr; //需要返回的明文
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(qywxConfig.getToken(), qywxConfig.getEncodingAESKey(), qywxConfig.getCorpid());
            String msgSignature = request.getParameter("msg_signature");
            String timeStamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            log.info("企业微信加密签名: {},时间戳: {},随机数: {},加密的字符串: {}", msgSignature, timeStamp, nonce, echostr);
            sEchoStr = wxcpt.VerifyURL(msgSignature,
                    timeStamp,
                    nonce,
                    echostr);
            log.info("给企业微信返回的明文,{}", sEchoStr);
            log.info("=========验证URL有效性结束=========");
            return sEchoStr;

        } catch (AesException e) {
            log.error("验证URL失败，错误原因请查看异常:{}", e.getCode());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void getRequestParameter(HttpServletRequest request, String body) {
        log.info("=========参数解析开始=========");
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(qywxConfig.getToken(), qywxConfig.getEncodingAESKey(), qywxConfig.getCorpid());
            String msgSignature = request.getParameter("msg_signature");
            String timeStamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            log.info("企业微信加密签名: {},时间戳: {},随机数: {}", msgSignature, timeStamp, nonce);
            String sMsg = wxcpt.DecryptMsg(msgSignature, timeStamp, nonce, body);
            Map<String, String> resultMap = new HashMap<String, String>(16);
            ConstantUtil.parseXmlToMap(sMsg, resultMap);
            log.info("decrypt密文结果为{}", JSONObject.toJSONString(resultMap));
            log.info("=========参数解析结束=========");
        } catch (AesException e) {
            log.error("密文参数解析失败，错误原因请查看异常:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
