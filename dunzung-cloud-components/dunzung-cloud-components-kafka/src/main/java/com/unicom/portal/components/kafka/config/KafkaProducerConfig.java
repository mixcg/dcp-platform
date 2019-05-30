package com.unicom.portal.components.kafka.config;

import com.unicom.portal.components.kafka.service.KafkaProducerService;
import com.unicom.portal.components.kafka.service.impl.KafkaProducerServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by duanzj on 2018/10/10.
 */
@Configuration
//@ConditionalOnProperty(name = "spring.kafka.producer.enable", havingValue = "true")
public class KafkaProducerConfig {

    @Bean
    public KafkaProducerService kafkaProducerService() {
        return new KafkaProducerServiceImpl();
    }

}
