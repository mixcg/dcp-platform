package com.dunzung.cloud.components.redis;

import com.dunzung.cloud.components.redis.config.RedisConfig;
import com.unicom.portal.framework.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import java.util.*;

/**
 * Created by duanzj on 2018/11/19.
 */
@Component
public class RedisClient {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisConfig redisConfig;

    public long setnx(String key, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return 0;
    }

    public String getSet(String key, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.getSet(key, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return "";
    }

    /**
     * 存储简单的字符串或者是Object 因为jedis没有分装直接存储Object的方法，所以在存储对象需斟酌下
     * 存储对象的字段是不是非常多而且是不是每个字段都用到，如果是的话那建议直接存储对象，
     * 否则建议用集合的方式存储，因为redis可以针对集合进行日常的操作很方便而且还可以节省空间
     */
    public void setObject(String key, Object value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(keyToBytes(key), valueToBytes(value));
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public void set(String key, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public void setex(String key, int seconds, String value) {
        Jedis jedis = getJedis();
        try {
            if (value == null) {
                jedis.del(key);
            }
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public void setexObject(String key, int seconds, Object value) {
        Jedis jedis = getJedis();
        try {
            if (value == null) {
                jedis.del(keyToBytes(key));
            }
            jedis.setex(keyToBytes(key), seconds, valueToBytes(value));
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public <T> T getObject(String key) {
        T value = null;
        Jedis jedis = getJedis();
        try {
            value = (T) valueFromBytes(jedis.get(keyToBytes(key)));
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * Get String
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String value = null;
        Jedis jedis = getJedis();
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    public void delObject(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(keyToBytes(key));
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public void del(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public void hdel(String key, String... field) {
        Jedis jedis = getJedis();
        try {
            jedis.hdel(key, field);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public void hset(String key, String field, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public Long hlen(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.hlen(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return 0L;
    }

    /**
     * 批量添加记录
     * hmset 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * 此命令会覆盖哈希表中已存在的域。
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
     *
     * @param key
     * @param map
     * @return
     */
    public void hmsetAll(String key, Map<String, String> map) {
        Jedis jedis = getJedis();
        try {
            jedis.hmset(key, map);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    /**
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        Jedis jedis = getJedis();
        String val = "";
        try {
            val = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return val;
    }

    /**
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * @param key
     * @return
     */
    public List<String> hvals(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.hvals(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return null;
    }

    public void expireObject(String key, int timeOut) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(keyToBytes(key), timeOut);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public void expire(String key, int timeOut) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, timeOut);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public Long decr(String key) {
        Long value = null;
        Jedis jedis = getJedis();
        try {
            value = jedis.decr(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            redisConfig.close(jedis);
        }
        return value;
    }

    public boolean exist(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error("", e);
            return false;
        } finally {
            close(jedis);
        }
    }


    public Long incr(String key) {
        Long value = null;
        Jedis jedis = getJedis();
        try {
            value = jedis.incr(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    public void lpush(String key, String... value) {
        Jedis jedis = getJedis();
        try {
            jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public void rpush(String key, String... value) {
        Jedis jedis = getJedis();
        try {
            jedis.rpush(key, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public Long llen(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.llen(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return 0L;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = getJedis();
        try {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return null;
    }

    public void sadd(String key, String... value) {
        Jedis jedis = getJedis();
        try {
            jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public Long scard(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return 0L;
    }

    public Boolean sismember(String key, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return null;
    }

    public void srem(String key, String... value) {
        Jedis jedis = getJedis();
        try {
            jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public Set<String> smembers(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.smembers(key);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return Collections.EMPTY_SET;
    }


    public void zadd(String key, double score,String member) {
        Jedis jedis = getJedis();
        try {
            jedis.zadd(key, score, member);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
    }

    public Long zcount(String key, double min,double max) {
        Jedis jedis = getJedis();
        try {
            return jedis.zcount(key, min, max);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return 0L;
    }

    public Double zscore(String key, String member) {
        Jedis jedis = getJedis();
        try {
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(jedis);
        }
        return 0d;
    }


    private static <T> T valueFromBytes(byte[] bytes) {
        if (bytes != null) {
            return Serialize.deserialize(bytes);
        }
        return null;
    }

    private byte[] keyToBytes(String key) {
        return SafeEncoder.encode(key);
    }

    private static byte[] valueToBytes(Object object) {
        return Serialize.serialize(object);
    }

    private Jedis getJedis() {
        return redisConfig.getJedisInstance();
    }

    private void close(Jedis jedis) {
        redisConfig.close(jedis);
    }

}
