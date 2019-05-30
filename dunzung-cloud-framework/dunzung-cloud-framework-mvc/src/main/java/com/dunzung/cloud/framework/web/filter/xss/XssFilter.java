package com.dunzung.cloud.framework.web.filter.xss;

import com.dunzung.cloud.framework.utils.XssUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by duanzj on 2018/12/16.
 */
@WebFilter
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String portUrl =req.getServerPort()+ req.getRequestURI();
        if (!XssUtils.hasExceptional(portUrl)) {
            XssRequestWrapper xssRequestWrapper = new XssRequestWrapper(req);
            chain.doFilter(xssRequestWrapper, response);
            return;
        }
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
    }
}
