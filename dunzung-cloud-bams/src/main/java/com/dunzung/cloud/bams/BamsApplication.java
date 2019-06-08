package com.dunzung.cloud.bams;

import com.dunzung.cloud.framework.mvc.ApplicationStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BamsApplication {

    public static void main(String[] args) {
        ApplicationStarter.run(BamsApplication.class, args);
    }

}
