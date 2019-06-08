package com.dunzung.cloud.components.redis.config;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by duanzj on 2018/9/10.
 */
@Configuration
public class RedisConfig {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JedisResourcePool jedisPool;

    /**
     * 获取Jedis实例
     *
     * @return 返回Jedis实例
     */
    public Jedis getJedisInstance() {
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            logger.error("getResource error", e);
            return null;
        }
    }

    /**
     * 释放资源
     *
     * @param jedis
     * @Date 2018年5月12日 下午7:30:10
     * @Author yz.teng
     */
    public void close(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            }catch (Exception e) {
               logger.error("",e);
            }
        }
    }

}