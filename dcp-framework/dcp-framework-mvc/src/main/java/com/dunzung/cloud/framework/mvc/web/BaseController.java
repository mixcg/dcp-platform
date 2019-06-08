package com.dunzung.cloud.framework.mvc.web;

import com.dunzung.cloud.framework.mvc.RequestHolder;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器支持类
 *
 * @author unicom qq+: 117721385
 * @version 2013-3-23
 */
public abstract class BaseController {

    public Gson gson = new Gson();

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 线程安全初始化request，respose对象
     *
     * @param request
     * @param response
     * @date 2015年11月24日
     * @author 漂泊者及其影子
     */
    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) {
        RequestHolder.requestLocal.set(request);
        RequestHolder.responseLocal.set(response);
    }

    /**
     * 获取客户端ip
     *
     * @return
     */
    public static String getClientIp() {
        HttpServletRequest request = RequestHolder.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
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
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }




}
