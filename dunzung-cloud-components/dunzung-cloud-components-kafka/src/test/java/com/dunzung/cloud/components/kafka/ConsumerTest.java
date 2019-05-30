package com.dunzung.cloud.components.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Arrays;

/**
 * Created by duanzj on 2018/10/25.
 */
public class ConsumerTest {

    public static void main(String[] args) {
        ConsumerTest consumerTest = new ConsumerTest();
        consumerTest.testConsumerRecords();
    }

    public void testConsumerRecords() {
        Consumer consumer = getConsumer();
        consumer.subscribe(Arrays.asList(KafkaConst.Topics.PM_TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(3000);
            System.out.println("Records.size:" + records.count());
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Consumer getConsumer() {
        ConsumerCfg consumerCfg = new ConsumerCfg();
        return consumerCfg.getConsumer();
    }
}
