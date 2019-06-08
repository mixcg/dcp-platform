package com.dunzung.cloud.uas;

import com.dunzung.cloud.framework.mvc.ApplicationStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UasServer {

    public static void main(String[] args) {
        ApplicationStarter.run(UasServer.class, args);
    }

}
