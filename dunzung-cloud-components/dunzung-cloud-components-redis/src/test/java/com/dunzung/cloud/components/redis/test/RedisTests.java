package com.dunzung.cloud.components.redis.test;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by duanzj on 2018/9/10.
 */
public class RedisTests {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private JedisResourcePool jedisPool;

    /**
     * 初始化锁
     */
    private static ReentrantLock INSTANCE_INIT_LOCL = new ReentrantLock(false);

    public JedisResourcePool initRedisPool() {
        if (jedisPool == null) {
            try {
                if (INSTANCE_INIT_LOCL.tryLock(2, TimeUnit.SECONDS)) {
                    try {
                        if (jedisPool == null) {
                            logger.info("Redis连接池初始化开始...");
                            jedisPool = RoundRobinJedisPool.create().curatorClient("10.236.1.1:2181,10.236.1.2:2181,10.236.1.3:2181", 30000).zkProxyDir("/zk/codis/db_zhmh-codis/proxy").build();
                            //jedisPool = RoundRobinJedisPool.create().curatorClient("10.236.9.154:2181,10.236.9.155:2181,10.236.9.156:2181", 30000).zkProxyDir("/zk/codis/db_codis-demo/proxy").build();
                            if (jedisPool == null) {
                                throw new NullPointerException(">>>>>>>>>>> Redis连接池为空...");
                            }
                            logger.info("Redis连接池初始化成功...");
                        }
                    } finally {
                        INSTANCE_INIT_LOCL.unlock();
                    }
                }
            } catch (InterruptedException e) {
                logger.error("Redis连接池初始化失败:", e);
            }
        }
        if (jedisPool == null) {
            throw new NullPointerException("Redis连接池初始化失败...");
        }
        return jedisPool;
    }

    /**
     * 获取Jedis实例
     *
     * @return 返回Jedis实例
     */
    public Jedis getJedisInstance() {
        try {
            if (jedisPool == null) {
                initRedisPool();
            }
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
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    public static void main1(String[] args) {
        String prov="ct;hq;bj;tj;he;sx;nm;ln;jl;hl;sh;js;zj;ah;fj;jx;sd;ha;hb;hn;gd;gx;hi;wl;lk;ds;cq;sc;gz;yn;xz;xw;ry;zk;cw;hc;ex;sn;gs;qh;nx;xj;gu;hs;ag;jc;sj;km;lx;zw;sk;kd;xy;kg;sg;ho;us;eu;jp;zf;jd;jh;;lw;ck;ys;nt;au;za;ct";
       /* public static final String PORTAL_ONLINES_USER = "portal:onlines:user";

       /* public static final String PORTAL_ONLINES_FIRST = "portal:onlines:first";*/
        RedisTests t = new RedisTests();
        t.initRedisPool();
        Jedis j = t.getJedisInstance();
        Set<String> smembers = j.smembers("portal:onlines:user");
        System.out.println(Arrays.toString(smembers.toArray()));
        String[] split = prov.split(";");
        for (String s : split) {
            Long scard = j.scard("portal:onlines:province:" + s);
            if(scard>0)
            System.out.println("portal:onlines:province:" + s+"=================="+scard);
        }

    }

    public static void main(String[] args) {
        RedisTests t = new RedisTests();
        t.initRedisPool();
        Jedis j = t.getJedisInstance();

        String grpKey = "portal:group:001000";
       // System.out.println(j.hget(grpKey,"431439"));
        Map<String, String> m = j.hgetAll(grpKey);
        System.out.println(m.size());
////
////        System.out.println(sj);
        String userArtKey = "portal:user:art:sunsz:431439";
//        String uj = j.get(userArtKey);
//        System.out.println(uj);
//        String[] a = new String[]{"/mypanel/v1/", "/selfpanel/v1/mysetup/", "/fullsearch/v1", "/news"};
//        j.sadd("portal:filter", a);
//
//        Set<String> smembers = j.smembers("portal:filter");
//        smembers.forEach(s -> {
//            System.out.println(s);
//        });
//        String key = "portal:art:rectop:cache";
//        String field = "10001";
//
//        String actionKey = "portal:art:action:cache";
//        Map<String, String> m = j.hgetAll(actionKey);
//        String rectopKey = "portal:art:rectop:cache";
//        j.hset(rectopKey, "1074497765376999424", m.get("1074497765376999424"));
//        m = j.hgetAll(rectopKey);
//        System.out.println("rectopKey:" + m.get("1074497765376999424"));
//        j.hdel(rectopKey,"10001");

//        String allArtKey = "portal:articles";
//        j.hdel(allArtKey,"1086617021237964800");
//        Map<String, String> artAllMap = j.hgetAll(allArtKey);
//        System.out.println(artAllMap.size());
    }

}