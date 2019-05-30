package com.unicom.portal.components.elasticsearch.config;

import lombok.Data;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * Created by duanzj on 2018/11/12.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "common.elasticsearch")
public class EsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsConfig.class);

    /**
     * 连接池
     */
    private String poolSize;

    /**
     * elk集群地址
     */
    private String clusterHosts;

    /**
     * 集群名称
     */
    private String clusterName;

    @Bean
    public TransportClient getTransportClient() {
        LOGGER.info("ES初始化开始...");

        TransportClient tclient = null;
        try {
            Settings esSetting = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", true)//增加嗅探机制，找到ES集群
                    .put("thread_pool.search.size", Integer.parseInt(poolSize))//增加线程池个数，暂时设为5
                    .build();
            String[] nodes = clusterHosts.split(",");
            tclient = new PreBuiltTransportClient(esSetting);
            for (String node : nodes) {
                if (node.length() > 0) {
                    String[] hostPort = node.split(":");
                    tclient.addTransportAddress(new TransportAddress(InetAddress.getByName(hostPort[0]), Integer.parseInt(hostPort[1])));
                }
            }
        } catch (Exception e) {
            LOGGER.error("elasticsearch TransportClient create error!!!", e);
        }

        LOGGER.info("ES初始化完成...");
        return tclient;
    }
}

