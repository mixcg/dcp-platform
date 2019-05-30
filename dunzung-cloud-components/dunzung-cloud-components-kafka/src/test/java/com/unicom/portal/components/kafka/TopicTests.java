//package com.unicom.portal.components.kafka;
//
//import kafka.admin.AdminUtils;
//import kafka.admin.RackAwareMode;
//import kafka.utils.ZkUtils;
//import org.apache.kafka.common.security.JaasUtils;
//
//import java.util.Properties;
//
///**
// * Created by duanzj on 2019/3/12.
// */
//public class TopicTests {
//
//    public static void main(String[] args) {
//       ZkUtils zkUtils = ZkUtils.apply("10.236.9.103:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
////
//      //  AdminUtils.deleteTopic(zkUtils,"sms-topic");
//
////
////// 创建一个单分区单副本名为t1的topic
//        AdminUtils.createTopic(zkUtils, "sms-topic", 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
//        zkUtils.close();
//    }
//
//}
