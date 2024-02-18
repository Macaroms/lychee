package com.lychee.controller;

import com.alibaba.fastjson2.JSONObject;
import com.lychee.aes.AesException;
import com.lychee.aes.WXBizMsgCrypt;
import com.lychee.service.IQywxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "企业微信控制层")
@RestController
@RequestMapping("/qywx")
@Slf4j
public class QywxController {

    @Resource
    private IQywxService qywxService;

    /**
     * 企业微信回调
     * 3.1 支持Http Get请求验证URL有效性
     * 3.2 支持Http Post请求接收业务数据
     *
     * @return
     */
    @ApiOperation(value = "回调", notes = "回调")
    @RequestMapping(value = "/getCallBack", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCallBack(HttpServletRequest request, @RequestBody(required = false) String body) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String jsonString = JSONObject.toJSONString(parameterMap);
        log.info("企业微信回调参数：{},  解析参数：{}", jsonString, body);

        if (body == null) {
            return qywxService.verificationUrl(request);
        }
        Map<String, String> resultMap = qywxService.getRequestParameter(request, body);
        System.err.println(resultMap);
        return "success";
    }


}
