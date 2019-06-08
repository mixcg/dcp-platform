package com.dunzung.cloud.uam;

import com.dunzung.cloud.framework.mvc.ApplicationStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UamApp {

    public static void main(String[] args) {
        ApplicationStarter.run(UamApp.class, args);
    }

}
