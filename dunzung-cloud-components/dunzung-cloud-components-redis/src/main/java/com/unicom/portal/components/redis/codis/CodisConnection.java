package com.unicom.portal.components.redis.codis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 *
 * @author duanzj
 * @since 2018-12-28
 */
public class CodisConnection extends JedisConnection {

    public CodisConnection(Jedis jedis, Pool<Jedis> pool) {
        this(jedis, pool, 0);
    }

    public CodisConnection(Jedis jedis, Pool<Jedis> pool, int dbIndex) {
        super(jedis, pool, dbIndex);
    }

    @Override
    public void close() throws DataAccessException {
        super.close();
    }
}
