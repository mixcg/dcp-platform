package com.unicom.portal.components.kafka.service;

import com.unicom.portal.components.kafka.dto.MsxBoxDTO;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Created by Wooola on 2018/10/10.
 */
public interface KafkaProducerService {

    void send(String topicName, MsxBoxDTO msgBody);

    void send(String topicName, String msgBody);

    KafkaTemplate<String, String> getKafkaTemplate();

}
