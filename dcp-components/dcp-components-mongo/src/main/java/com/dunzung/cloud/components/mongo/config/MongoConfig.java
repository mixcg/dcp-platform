package com.dunzung.cloud.components.mongo.config;

import com.dunzung.cloud.components.mongo.service.MongoService;
import com.dunzung.cloud.components.mongo.service.impl.MongoServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by duanzj on 2018/9/10.
 * prototype:singleton or multi
 */
@Configuration
@ConditionalOnProperty(prefix = "mongo", name = "prototype", havingValue = "singleton")
public class MongoConfig {

    @Bean
    public MongoService mongoService() {
        return new MongoServiceImpl();
    }

}