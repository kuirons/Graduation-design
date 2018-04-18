package com.bigao.backend.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wait on 2016/1/12.
 */
public class IpUtil {
    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public static String remoteIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static boolean isLocal(HttpServletRequest request) {
        String ip = remoteIp(request);
        return ip.equals("127.0.0.1") || ip.startsWith("192.168");
    }
}
