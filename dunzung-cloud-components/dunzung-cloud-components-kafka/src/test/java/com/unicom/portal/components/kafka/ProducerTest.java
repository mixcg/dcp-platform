package com.unicom.portal.components.kafka;

import com.alibaba.fastjson.JSON;
import com.unicom.portal.components.kafka.dto.MsxBoxDTO;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Created by Wooola on 2018/10/25.
 */
public class ProducerTest {

    public static void main(String[] args) throws InterruptedException {
        ProducerTest producerTest = new ProducerTest();
        while (true) {
            producerTest.send();
        }
    }

    public void send() throws InterruptedException {
        ProducerCfg producerCfg = new ProducerCfg();
        Producer<String, String> producer = producerCfg.getProducer();

        MsxBoxDTO msxBoxDTO = new MsxBoxDTO();
        msxBoxDTO.setBizId(10003L);
        msxBoxDTO.setCreateTm(new Date());
        msxBoxDTO.setDataSources(0);
        msxBoxDTO.setMsgSubType(1);
        msxBoxDTO.setContent("评论点赞");
        msxBoxDTO.setReceiveDate(new Date());
        msxBoxDTO.setUserId("188");
        msxBoxDTO.setSendPlatform("");
        msxBoxDTO.setMsgUrl("/comment/v1/10003");
        producer.send(new ProducerRecord<>(KafkaConst.Topics.PM_TOPIC, "", JSON.toJSONString(msxBoxDTO)));

        producer.close();
        System.out.println(">>>> 发送成功！");

        Thread.sleep(20000L);
    }

}
