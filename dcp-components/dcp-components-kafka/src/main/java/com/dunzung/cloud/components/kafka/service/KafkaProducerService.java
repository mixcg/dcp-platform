package com.dunzung.cloud.components.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;

/**
 * Created by Wooola on 2018/10/10.
 */
public interface KafkaProducerService {

    void send(String topicName, String msgBody);

    KafkaTemplate<String, String> getKafkaTemplate();

}
