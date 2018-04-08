package com.yexuejc.vertx.web.base.util;

import io.vertx.core.http.HttpServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * ip 工具类
 *
 * @ClassName: NetworkUtil
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 10:39
 */
public class NetworkUtil {
    private static Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @return String 返回对应Inet4 对应的网口ip
     */
    public static String getIpAddress() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request {@link HttpServerRequest} 获取请求客户端ip、或代理ip
     * @return 返回对应的网口ip
     * @throws IOException
     */
    public final static String getIpAddress(HttpServerRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (logger.isInfoEnabled()) {
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.remoteAddress().host();
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
