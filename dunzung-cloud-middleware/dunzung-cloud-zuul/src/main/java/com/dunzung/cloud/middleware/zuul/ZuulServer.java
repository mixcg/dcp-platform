package com.dunzung.cloud.middleware.zuul;

import com.dunzung.cloud.framework.mvc.ApplicationStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableOAuth2Sso
@EnableZuulProxy
@SpringBootApplication
public class ZuulServer {

    public static void main(String[] args) {
        ApplicationStarter.run(ZuulServer.class, args);
    }

}
