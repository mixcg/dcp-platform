package com.dunzung.cloud.security;

import com.dunzung.cloud.framework.ApplicationStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        ApplicationStarter.run(SecurityApplication.class, args);
    }

}
