package com.dunzung.cloud.framework.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableHystrix
@EnableFeignClients(basePackages = {"com.unicom.portal.**.feign.**"})
@Import({EurekaClientConfig.class, SwaggerConfig.class,})
public class PortalConfig {

}