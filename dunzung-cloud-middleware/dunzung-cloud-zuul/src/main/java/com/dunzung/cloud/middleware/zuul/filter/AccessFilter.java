package com.dunzung.cloud.middleware.zuul.filter;

import com.dunzung.cloud.framework.oauth.OauthConst;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFilter extends ZuulFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);

    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.getResponse().setContentType("text/html;charset=utf-8");
        if (hasAccessToken()) {
            String responseValue = "{\"message\":\"授权Token未发现。\",\"data\":null,\"code\":\"20005\"}";
            ctx.setResponseBody(responseValue);
            ctx.setResponseStatusCode(401);
            ctx.setSendZuulResponse(false);
        } else {
            ctx.setResponseStatusCode(200);
            ctx.setSendZuulResponse(true);
        }
        return null;
    }

    private boolean hasAccessToken() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String authHeader = request.getHeader(OauthConst.Token.AUTH_HEADER);
        LOGGER.debug("authHeader is {}", authHeader);
        return StringUtils.isEmpty(authHeader);
    }

}
