package com.huixin.framework.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static String IP_UNKNOW = "unknown";
    private static String[] POSSIBLE_HEADERS = new String[] { "x-forwarded-for", "Proxy-Client-IP",
            "WL-Proxy-Client-IP" };

    /**
     * Get the remote IP address of the client
     */
    public static String getRemoteAddr(ServletRequest req) {
        if (req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) req;
            String ip = null;
            for (int i = 0; i < POSSIBLE_HEADERS.length && isInvalidIP(ip); i++) {
                ip = request.getHeader(POSSIBLE_HEADERS[i]);
            }
            if (isInvalidIP(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        } else {
            return req.getRemoteAddr();
        }
    }

    private static boolean isInvalidIP(String ip) {
        return ip == null || ip.trim().length() == 0 || IP_UNKNOW.equalsIgnoreCase(ip);
    }

}
