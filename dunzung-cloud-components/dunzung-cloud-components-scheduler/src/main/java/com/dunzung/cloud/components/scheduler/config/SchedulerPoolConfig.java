package com.dunzung.cloud.components.scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Wooola on 2018/10/11.
 */
@Configuration
@ConfigurationProperties(prefix = "common.scheduler")
public class SchedulerPoolConfig {
    /**
     *  线程池维护线程的最少数量
     */
    private int corePoolSize;
    /**
     * 线程池维护线程的最大数量
     */
    private int maxPoolSize;
    /**
     * 缓存队列
     */
    private int queueCapacity;
    /**
     * 允许的空闲时间
     */
    private int keepAliveSeconds;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
