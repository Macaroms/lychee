package com.lychee.util;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/6/4 16:36
 */
public class IPUtils {

    private static final Set<String> WHITE_IP_SET = Sets.newHashSet("1.2.3.4");

    public static boolean isWhiteIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        return ip.startsWith("192.168.0") || ip.equals("127.0.0.1") || ip.equals(localIP()) || WHITE_IP_SET.contains(ip);
    }

    public static boolean isWhiteIp(HttpServletRequest request) {
        String ip = originIp(request);
        return isWhiteIp(ip);
    }

    public static String originIp(HttpServletRequest request) {
        if(request == null){
            return null;
        }
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        // 穿透Nginx
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (!("unknown".equalsIgnoreCase(s))) {
                    ip = s;
                    break;
                }
            }
        }
        return ip;
    }

    public static String localIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }


}

