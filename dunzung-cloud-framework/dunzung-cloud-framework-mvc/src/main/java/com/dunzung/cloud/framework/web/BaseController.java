package com.dunzung.cloud.framework.web;

import com.alibaba.fastjson.JSONObject;
import com.dunzung.cloud.framework.RequestHolder;
import com.dunzung.cloud.framework.web.entity.Brower;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 控制器支持类
 *
 * @author unicom qq+: 117721385
 * @version 2013-3-23
 */
public abstract class BaseController {

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

    public String getUserId() {
        return RequestHolder.getUserId();
    }

    /**
     * 获取岗位ID
     *
     * @return
     */
    public static String getSiteId() {
        return RequestHolder.getSiteId();
    }

    /**
     * 获取部门ID
     *
     * @return
     */
    public static String getDeptId() {
        return RequestHolder.getDeptId();
    }

    /**
     * 获取部门PATH
     *
     * @return
     */
    public static String getPath() {
        return RequestHolder.getPath();
    }

    /**
     * 获取部门PATH
     *
     * @return
     */
    public static String getGlobalSign() {
        return RequestHolder.getGlobalSign();
    }

    /**
     * 站点ID
     *
     * @return
     */
    public static String getWid() {
        return RequestHolder.getWid();
    }

    public static String getUserTrueName() {
        return RequestHolder.getUserTrueName();
    }

    /**
     * 获取客户浏览器信息
     *
     * @return
     */
    public static Brower getBrower() {
        HttpServletRequest request = RequestHolder.getRequest();
        String agent = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        Brower brower = ieVersion(agent);
        String version = "";
        try {
            if (brower == null) {
                brower = new Brower();
                String browerName = userAgent.getBrowser().toString().replaceAll("\\d+", "");
                Version browserVersion = userAgent.getBrowserVersion();
                if (browserVersion != null) {
                    version = browserVersion.getVersion();
                }
                brower.setName(browerName);
                brower.setVersion(version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brower;
    }

    /**
     * 获取客户浏览器信息
     *
     * @return
     */
    public static String getUserAgent() {
        HttpServletRequest request = RequestHolder.getRequest();
        return request.getHeader("User-Agent");
    }

    /**
     * 获取用户操作系统
     *
     * @return
     */
    public static String getOS() {
        HttpServletRequest request = RequestHolder.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        if (operatingSystem == null) {
            return null;
        }
        return operatingSystem.getName();
    }


    /**
     * 获取客户端ip
     *
     * @return
     */
    public static String getClientIp() {
        HttpServletRequest request = RequestHolder.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        // System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            // System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            // System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            //  System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            //System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            // System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            // System.out.println("getRemoteAddr ip: " + ip);
        }
        //System.out.println("获取客户端ip: " + ip);
        return ip;
    }


    /**
     * 通过ip获取用户登录地点
     *
     * @param ip
     * @return
     */
    public static String getAdd(String ip) {
        //淘宝IP地址库：http://ip.taobao.com/instructions.php
        String add = null;
        try {
            //URL U = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=114.111.166.72");
            URL U = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
            URLConnection connection = U.openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
            JSONObject jsonObject = JSONObject.parseObject(result);
            Map<String, Object> map = (Map) jsonObject;
            String code = String.valueOf(map.get("code"));//0：成功，1：失败。
            if ("1".equals(code)) {//失败
                String data = String.valueOf(map.get("data"));//错误信息
            } else if ("0".equals(code)) {//成功
                Map<String, Object> data = (Map<String, Object>) map.get("data");

                String country = String.valueOf(data.get("country"));//国家
                String area = String.valueOf(data.get("area"));//
                String city = String.valueOf(data.get("city"));//省（自治区或直辖市）
                String region = String.valueOf(data.get("region"));//市（县）
                add = country + "-" + city + "-" + region;
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }

    /**
     * 根据内核判断浏览器版本（领导要求ie11使用兼容模式还是显示ie11）
     *
     * @param userAgent
     * @return
     */
    private static Brower ieVersion(String userAgent) {
        Brower brower = new Brower();
        boolean isIE = userAgent.contains("compatible") && userAgent.contains("MSIE"); //判断是否IE<11浏览器
        boolean isEdge = userAgent.contains("Edge") && !isIE; //判断是否IE的Edge浏览器
        boolean isIE11 = userAgent.contains("Trident") && userAgent.contains("rv:11.0");
        if (isIE11) {
            brower.setName("IE");
            brower.setVersion("11");
            return brower;
        } else if (isEdge) {
            brower.setName("Edge");
            brower.setVersion("Edge");
            return brower;
        } else {
            if (userAgent.contains("Trident/7.0")) {
                brower.setName("IE");
                brower.setVersion("11");
                return brower;
            } else if (userAgent.contains("Trident/6.0")) {
                brower.setName("IE");
                brower.setVersion("10");
                return brower;
            } else if (userAgent.contains("Trident/5.0")) {
                brower.setName("IE");
                brower.setVersion("9");
                return brower;
            } else if (userAgent.contains("Trident/4.0")) {
                brower.setName("IE");
                brower.setVersion("8");
                return brower;
            }
        }
        return null;
    }

}
