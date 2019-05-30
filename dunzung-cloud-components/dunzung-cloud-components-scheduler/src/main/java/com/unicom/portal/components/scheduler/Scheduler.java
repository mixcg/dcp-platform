package com.unicom.portal.components.scheduler;

import com.unicom.portal.components.scheduler.config.SchedulerPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by duanzj on 2018/10/11
 */
@EnableAsync
@EnableScheduling
@Configuration
public class Scheduler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SchedulerPoolConfig config;

    @Bean
    @Qualifier("taskExecutor")
    public AsyncTaskExecutor taskExecutor() {
        logger.info("初始化线程池开始...");
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("Portal-Executor-");
        taskExecutor.setCorePoolSize(config.getCorePoolSize());
        taskExecutor.setMaxPoolSize(config.getMaxPoolSize());
        taskExecutor.setQueueCapacity(config.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        logger.info("初始化线程池完成...");
        return taskExecutor;
    }

}