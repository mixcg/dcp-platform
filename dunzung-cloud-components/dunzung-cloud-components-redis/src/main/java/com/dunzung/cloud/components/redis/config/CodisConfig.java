package com.dunzung.cloud.components.redis.config;

import com.dunzung.cloud.components.redis.codis.CodisConnectionFactory;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dunzung.cloud.components.redis.codis.CodisRoundRobinJedisPool;
import io.codis.jodis.JedisResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duanzj
 * @since 2018-12-28
 */
@Configuration
public class CodisConfig extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(CodisConfig.class);

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisProperties.getMaxTotal());
        poolConfig.setMaxIdle(redisProperties.getMaxIdle());
//        poolConfig.setMaxIdle(100);
//        poolConfig.setMinIdle(50);
//        poolConfig.setMaxTotal(1000);
//        //在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
//        poolConfig.setTestOnBorrow(true);
//        //在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setMaxWaitMillis(30000);
//        //连接耗尽的时候，是否阻塞，false 会抛出异常，true 阻塞直到超时。默认为true。
//        poolConfig.setBlockWhenExhausted(false);
        return poolConfig;
    }

    @Bean
    public JedisResourcePool codisRoundRobinJedisPool() {
        JedisResourcePool codisPool = null;
        try {
            codisPool = CodisRoundRobinJedisPool.create()
                    .curatorClient(redisProperties.getNodes(), redisProperties.getTimeout())
                    //.curatorClient("10.236.0.84:2181,10.236.0.85:2181,10.236.0.86:2181", 3000)
                    .zkProxyDir(redisProperties.getZkProxyDir())
                    // .zkProxyDir("/zk/codis/db_codis-demo2/proxy")
                    .poolConfig(jedisPoolConfig())
                    //  .password(codisProperties.getZookeeper().getPassword())
                    // .database(codisProperties.getDatabase())
                    .build();
        } catch (Exception e) {
            logger.error("Failed to create jedisPool!", e);
        }
        logger.info("Create jedisPool success! parameters: " + redisProperties);
        return codisPool;
    }

    @Bean
    public RedisConnectionFactory jedisConnectionFactory(CodisRoundRobinJedisPool codisRoundRobinJedisPool) {
        return new CodisConnectionFactory(codisRoundRobinJedisPool);
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        Map<String, Long> expireKeys = new HashMap<>();
        // 都在默认1小时
        expireKeys.put("pcm:pagecache:news", 60 * 60L);
        // 分类&公告
        expireKeys.put("pcm:pagecache:category", 5 * 60L);
        // 偏好设置
        expireKeys.put("site:pagecache:preference", 5 * 60L);
        // 个人标签
        expireKeys.put("site:pagecache:label", 5 * 60L);
        // 个人标签
        //expireKeys.put("flowquery:pagecache:userarticle", 3 * 60L);
        logger.info("初始化业务缓存KEY成功。");
        rcm.setExpires(expireKeys);
        return rcm;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {

        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

}
