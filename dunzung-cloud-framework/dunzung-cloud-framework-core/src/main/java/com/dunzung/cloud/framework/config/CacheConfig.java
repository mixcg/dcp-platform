package com.dunzung.cloud.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@EnableCaching
public class CacheConfig {
    protected static Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @PostConstruct
    public void init(){
        logger.info("系统缓存初始化完成...");
    }

}
