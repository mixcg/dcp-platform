package com.unicom.portal.components.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by duanzj on 2018/10/23.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "global.redis")
public class RedisProperties {

    private String nodes;

    private Integer timeout;

    private String zkProxyDir;

    private Integer maxTotal = 80;

    private Integer maxIdle = 10;

}
