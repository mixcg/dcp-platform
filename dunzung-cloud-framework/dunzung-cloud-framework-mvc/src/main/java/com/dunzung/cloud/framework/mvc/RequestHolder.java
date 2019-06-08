package com.dunzung.cloud.framework.mvc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by duanzj on 2018/9/12.
 */
public final class RequestHolder {
    /**
     * 获取当前线程的Request对象
     */
    public static ThreadLocal<ServletRequest> requestLocal = new ThreadLocal<>();

    /**
     * 获取当前线程的Response对象
     */
    public static ThreadLocal<ServletResponse> responseLocal = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) requestLocal.get();
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) responseLocal.get();
    }

}

