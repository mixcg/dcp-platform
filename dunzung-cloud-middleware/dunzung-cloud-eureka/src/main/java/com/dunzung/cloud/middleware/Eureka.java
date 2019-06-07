package com.dunzung.cloud.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Eureka {

	public static void main(String[] args) {
		SpringApplication.run(Eureka.class, args);
	}
}
