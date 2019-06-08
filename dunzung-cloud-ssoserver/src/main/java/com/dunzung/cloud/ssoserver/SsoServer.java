package com.dunzung.cloud.ssoserver;

import com.dunzung.cloud.framework.mvc.ApplicationStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsoServer {

    public static void main(String[] args) {
        ApplicationStarter.run(SsoServer.class, args);
    }

}