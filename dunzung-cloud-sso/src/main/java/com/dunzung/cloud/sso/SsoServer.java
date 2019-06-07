package com.dunzung.cloud.sso;

import com.dunzung.cloud.framework.ApplicationStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsoServer {

    public static void main(String[] args) {
        ApplicationStarter.run(SsoServer.class, args);
    }

}
