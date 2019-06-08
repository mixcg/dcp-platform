//package com.dunzung.cloud.components.kafka.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.dunzung.cloud.components.kafka.KafkaConst;
//import com.unicom.portal.components.kafka.dto.MsxBoxDTO;
//import com.dunzung.cloud.components.kafka.service.KafkaProducerService;
//import com.unicom.portal.framework.BizConst;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//
///**
// * Created by duanzj on 2018/10/10.
// */
//public class KafkaProducerServiceImpl implements KafkaProducerService {
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Override
//    public void send(String topicName, MsxBoxDTO msgBody) {
//        if (BizConst.MWID.equals(msgBody.getWid())) {
//            send(topicName, JSON.toJSONString(msgBody));
//        }
//    }
//
//    @Override
//    public void send(String topicName, String msgBody) {
//        /**
//         * 屏蔽积分功能
//         */
//        if (KafkaConst.Topics.PI_TOPIC.equals(topicName)) {
//            return;
//        }
//        kafkaTemplate.send(topicName, msgBody);
//    }
//
//    public KafkaTemplate<String, String> getKafkaTemplate() {
//        return kafkaTemplate;
//    }
//}
