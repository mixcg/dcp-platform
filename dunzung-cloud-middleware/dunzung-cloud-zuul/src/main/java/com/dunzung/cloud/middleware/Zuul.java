package com.dunzung.cloud.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;


@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(value="com.dunzung.cloud")
public class Zuul {

    public static void main(String[] args) {
        SpringApplication.run(Zuul.class, args);
    }

}
