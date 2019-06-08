package com.dunzung.cloud.uas;

import com.dunzung.cloud.framework.mvc.ApplicationStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@EnableOAuth2Sso
@SpringBootApplication
public class UasServer {

    public static void main(String[] args) {
        ApplicationStarter.run(UasServer.class, args);
    }

}
