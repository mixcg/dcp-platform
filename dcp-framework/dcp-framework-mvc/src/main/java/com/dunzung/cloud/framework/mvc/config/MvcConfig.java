package com.dunzung.cloud.framework.mvc.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableHystrix
@EnableFeignClients(basePackages = {"com.dunzung.cloud.**.feign.**"})
@Import({EurekaClientConfig.class, SwaggerConfig.class,})
public class MvcConfig {

}
