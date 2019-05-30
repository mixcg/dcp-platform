package com.dunzung.cloud.components.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

/**
 * Created by duanzj on 2018/10/25.
 */
public class ProducerCfg {

    public Producer<String, String> getProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "http://10.236.0.76:9092");
        props.put("acks","-1" ); //ack方式，all，会等所有的commit最慢的方式
        props.put("retries", 0); //失败是否重试，设置会有可能产生重复数据
        props.put("batch.size", 16384); //对于每个partition的batch buffer大小
        props.put("linger.ms", 1);  //等多久，如果buffer没满，比如设为1，即消息发送会多1ms的延迟，如果buffer没满
        props.put("buffer.memory", 33554432); //整个producer可以用于buffer的内存大小
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        return producer;
    }


}
