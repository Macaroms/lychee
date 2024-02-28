package com.lychee.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.lychee.aes.AesException;
import com.lychee.aes.WXBizMsgCrypt;
import com.lychee.configuation.QywxConfig;
import com.lychee.service.IQywxService;
import lombok.extern.slf4j.Slf4j;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            log.info("decrypt密文结果为{}", XML.toJSONObject(sMsg));
            log.info("=========参数解析结束=========");
            callbackListener(sMsg);
        } catch (AesException e) {
            log.error("密文参数解析失败，错误原因请查看异常:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Value("${qywx.approvalProjectTemplateIds}")
    private static String APPROVAL_PROJECT_TEMPLATE_IDS;

    private static void callbackListener(String xml) {
        org.json.JSONObject data = XML.toJSONObject(xml).getJSONObject("xml");
        // 判断是否是审批流程状态变更的回调
        if ("sys_approval_change".equals(data.getString("Event"))) {
            org.json.JSONObject approvalInfo = data.getJSONObject("ApprovalInfo");
            // 判断是否是立项相关的审批模板，需要存到立项表里
            // if (APPROVAL_PROJECT_TEMPLATE_IDS.contains(approvalInfo.getString("TemplateId"))) {
            if (true) {
                if (approvalInfo.getInt("SpStatus") == 2) { // 审批通过的
                    // 审批通过 查表单详情
                    log.info("审批模板：{}， 提交时间：{}， 审批通过", approvalInfo.getString("SpName"), approvalInfo.getBigInteger("ApplyTime"));
                    log.info("审批编号：{}， 模板编号：{}", approvalInfo.getBigInteger("SpNo"), approvalInfo.getString("TemplateId"));
                    getApprovalDetail(approvalInfo.getBigInteger("SpNo").toString());
                }
            }
        }
    }

    @Value("${qywx.approvalDetailUrl}")
    private static String APPROVAL_DETAIL_URL;

    private static void getApprovalDetail(String spNo) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/oa/getapprovaldetail?access_token=%s";
        String token = "oas8tw2QL64XNkJMdwaZIKRsZ3skIkgc6zzT8Hefh7Ws9nJG1IQ4jwKjhax_Ob5z4CfvjrSNicZlOLiBu_Zx98q5fgOPuzDfYahJxMdYSMNgY4RrZRI27BoQVL75fDRfHJ32BjtDSeYmRAF8wvEvXk07ozTj3T2d3OWEVadoHJbpAzQpNdOnaJCiyUYzLPN0tuEv-Ml0Aez6dEWuTjZD8g";
        HttpRequest httpRequest = new HttpRequest(String.format(url, token));
        httpRequest.setMethod(Method.POST);
        ImmutableMap<String, String> immutableMap = ImmutableMap.of("sp_no", spNo);
        httpRequest.body(JSON.toJSONString(immutableMap));// 需要转成json字符串
        HttpResponse execute = httpRequest.execute();
//        boolean ok = execute.isOk(); // 是否请求成功 判断依据为：状态码范围在200~299内
//        System.out.println(ok);
        String body = execute.body();
        System.out.println(body);
        JSONObject data = JSONObject.parse(body);
        if (data.getIntValue("errcode") != 0) {
            log.error("查询审批详情出错：{}", body);
            return;
        }
        JSONArray applyData = data.getJSONObject("info").getJSONObject("apply_data").getJSONArray("contents");
        Map<String, String> columns = getColumnsMap(applyData);
        System.out.println(JSONObject.toJSONString(columns));
        ApprovalProject approvalProject = new ApprovalProject();
        approvalProject.setProjectName(columns.get("项目名称"));
        approvalProject.setProjectType(columns.get("项目类型"));
        approvalProject.setProjectDescription(columns.get("项目描述"));
        approvalProject.setProjectManagerName(columns.get("项目经理"));
        approvalProject.setProjectMembers(columns.get("项目人员"));
        approvalProject.setCostName(columns.get("研发部"));
        approvalProject.setChargeable(columns.get("CHARGEABLE"));
        approvalProject.setProductive(columns.get("PRODUCTIVE"));
        approvalProject.setUsableHours(columns.get("是否可用于填报工时"));
        approvalProject.setUsableReim(columns.get("是否可用于填报报销"));
        approvalProject.setFile(columns.get("研发立项报告"));
        approvalProject.setSpName(data.getJSONObject("info").getString("sp_name"));
        // 明细
        JSONArray approvalNodeList = JSONArray.parse(columns.get("明细"));
        List<ApprovalNode> approvalNodes = Lists.newArrayList();
        for (int i = 0; i < approvalNodeList.size(); i++) {
            JSONObject item = approvalNodeList.getJSONObject(i);
            approvalNodes.add(new ApprovalNode(
                    approvalProject.getId(),
                    item.getString("节点编号"),
                    item.getString("节点名称"),
                    item.getString("节点日期"),
                    item.getString("付款百分比")
            ));
        }
        System.out.println(approvalNodes);
    }

    private static Map<String, String> getColumnsMap(JSONArray array) {
        Map<String, String> columns = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject applyDataItem = array.getJSONObject(i);
            String title = applyDataItem.getJSONArray("title").getJSONObject(0).getString("text");
            String control = applyDataItem.getString("control");
            columns.put(title, getApplyDataValue(applyDataItem, control));
        }
        return columns;
    }

    private static String getApplyDataValue(JSONObject applyDataItem, String control) {
        if ("Text".equals(control) || "Textarea".equals(control)) {
            return applyDataItem.getJSONObject("value").getString("text");
        } else if ("Number".equals(control)) {
            return applyDataItem.getJSONObject("value").getString("new_number");
        } else if ("Selector".equals(control)) {
            JSONObject selector = applyDataItem.getJSONObject("value").getJSONObject("selector");
            JSONArray array = selector.getJSONArray("options");
            List<String> collect = Lists.newArrayList();
            for (int i = 0; i < array.size(); i++) {
                collect.add(array.getJSONObject(i).getJSONArray("value").getJSONObject(0).getString("text"));
            }
            return String.join(",", collect);
        } else if ("File".equals(control)) {
            JSONArray array = applyDataItem.getJSONObject("value").getJSONArray("files");
            return array.isEmpty() ? null : array.getJSONObject(0).getString("file_id");
        } else if ("Date".equals(control)) {
            JSONObject res = applyDataItem.getJSONObject("value").getJSONObject("date");
            if ("day".equals(res.getString("type"))) {
                return res.getString("s_timestamp") + "000";
            } else if ("hour".equals(res.getString("type"))) {
                return res.getString("s_timestamp");
            }
            return null;
        } else if ("Contact".equals(control)) {
            JSONArray array = applyDataItem.getJSONObject("value").getJSONArray("members");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.size(); i++) {
                sb.append(array.getJSONObject(i).getString("name"));
                if (i != array.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        } else if ("Table".equals(control)) {
            JSONArray list = new JSONArray();
            JSONArray array = applyDataItem.getJSONObject("value").getJSONArray("children");
            for (int i = 0; i < array.size(); i++) {
                JSONArray cols = array.getJSONObject(i).getJSONArray("list");
                Map<String, String> columns = getColumnsMap(cols);
                list.add(columns);
            }
            return JSONObject.toJSONString(list);
        } else {
            log.error("unknown control: {}", control);
            return null;
        }
    }

    public static void main(String[] args) {
        String sMsg = "<xml><ToUserName><![CDATA[ww2728fd178710bdbe]]></ToUserName><FromUserName><![CDATA[sys]]></FromUserName><CreateTime>1708581785</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[sys_approval_change]]></Event><AgentID>1000002</AgentID><ApprovalInfo><SpNo>202402220001</SpNo><SpName><![CDATA[立项申请测试模板]]></SpName><SpStatus>2</SpStatus><TemplateId><![CDATA[3WLtyqgf6R9RgH84RzTakYp8b4kf5gAKTqNEfQAH]]></TemplateId><ApplyTime>1708581648</ApplyTime><Applyer><UserId><![CDATA[JiangWei]]></UserId><Party><![CDATA[2]]></Party></Applyer><SpRecord><SpStatus>2</SpStatus><ApproverAttr>1</ApproverAttr><Details><Approver><UserId><![CDATA[JiangWei]]></UserId></Approver><Speech><![CDATA[]]></Speech><SpStatus>2</SpStatus><SpTime>1708581783</SpTime></Details></SpRecord><SpRecord><SpStatus>2</SpStatus><ApproverAttr>1</ApproverAttr><Details><Approver><UserId><![CDATA[JiangWei]]></UserId></Approver><Speech><![CDATA[]]></Speech><SpStatus>2</SpStatus><SpTime>1708581784</SpTime></Details></SpRecord><StatuChangeEvent>2</StatuChangeEvent></ApprovalInfo></xml>";
        callbackListener(sMsg);
    }

}
