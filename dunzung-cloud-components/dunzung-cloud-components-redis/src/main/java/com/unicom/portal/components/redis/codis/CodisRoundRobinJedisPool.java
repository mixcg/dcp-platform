package com.unicom.portal.components.redis.codis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Closeables;
import io.codis.jodis.BoundedExponentialBackoffRetryUntilElapsed;
import io.codis.jodis.CodisProxyInfo;
import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.curator.framework.imps.CuratorFrameworkState.LATENT;
import static org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode.BUILD_INITIAL_CACHE;
import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.*;

/**
 * @author duanzj
 * @since 2018-12-28
 */
public class CodisRoundRobinJedisPool implements JedisResourcePool {
    private static final Logger logger = LoggerFactory.getLogger(CodisRoundRobinJedisPool.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String CODIS_PROXY_STATE_ONLINE = "online";

    private static final int CURATOR_RETRY_BASE_SLEEP_MS = 100;

    private static final int CURATOR_RETRY_MAX_SLEEP_MS = 30 * 1000;

    private static final ImmutableSet<PathChildrenCacheEvent.Type> RESET_TYPES = Sets.immutableEnumSet(CHILD_ADDED, CHILD_UPDATED, CHILD_REMOVED);

    private final CuratorFramework curatorClient;

    private final boolean closeCurator;

    private final PathChildrenCache watcher;

    private static final class PooledObject {
        public final String addr;

        public final JedisPool pool;

        public PooledObject(String addr, JedisPool pool) {
            this.addr = addr;
            this.pool = pool;
        }
    }

    private volatile ImmutableList<PooledObject> pools = ImmutableList.of();

    private final AtomicInteger nextIdx = new AtomicInteger(-1);

    private final JedisPoolConfig poolConfig;

    private final int connectionTimeoutMs;

    private final int soTimeoutMs;

    private final String password;

    private final int database;

    private final String clientName;

    private CodisRoundRobinJedisPool(CuratorFramework curatorClient, boolean closeCurator,
                                     String zkProxyDir, JedisPoolConfig poolConfig,
                                     int connectionTimeoutMs, int soTimeoutMs,
                                     String password, int database, String clientName) {

        this.poolConfig = poolConfig;
        this.connectionTimeoutMs = connectionTimeoutMs;
        this.soTimeoutMs = soTimeoutMs;
        this.password = password;
        this.database = database;
        this.clientName = clientName;
        this.curatorClient = curatorClient;
        this.closeCurator = closeCurator;
        watcher = new PathChildrenCache(curatorClient, zkProxyDir, true);
        watcher.getListenable().addListener(new PathChildrenCacheListener() {

            private void logEvent(PathChildrenCacheEvent event) {
                StringBuilder msg = new StringBuilder("Receive child event: ");
                msg.append("type=").append(event.getType());
                ChildData data = event.getData();
                if (data != null) {
                    msg.append(", path=").append(data.getPath());
                    msg.append(", stat=").append(data.getStat());
                    if (data.getData() != null) {
                        msg.append(", bytes length=").append(data.getData().length);
                    } else {
                        msg.append(", no bytes");
                    }
                } else {
                    msg.append(", no data");
                }
                logger.info(msg.toString());
            }

            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
                    throws Exception {
                logEvent(event);
                if (RESET_TYPES.contains(event.getType())) {
                    resetPools();
                }
            }
        });
        try {
            watcher.start(BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            close();
            throw new JedisException(e);
        }
        resetPools();
    }

    private void resetPools() {

        ImmutableList<PooledObject> pools = this.pools;
        Map<String, PooledObject> addr2Pool = Maps.newHashMapWithExpectedSize(pools.size());
        for (PooledObject pool : pools) {
            addr2Pool.put(pool.addr, pool);
        }
        ImmutableList.Builder<PooledObject> builder = ImmutableList.builder();
        for (ChildData childData : watcher.getCurrentData()) {
            try {
                CodisProxyInfo proxyInfo = MAPPER.readValue(childData.getData(),
                        CodisProxyInfo.class);
                if (!CODIS_PROXY_STATE_ONLINE.equals(proxyInfo.getState())) {
                    continue;
                }
                String addr = proxyInfo.getAddr();
                PooledObject pool = addr2Pool.remove(addr);
                if (pool == null) {
                    String[] hostAndPort = addr.split(":");
                    String host = hostAndPort[0];
                    int port = Integer.parseInt(hostAndPort[1]);
                    pool = new PooledObject(addr,
                            new JedisPool(poolConfig, host, port, connectionTimeoutMs, soTimeoutMs,
                                    password, database, clientName, false, null, null, null));
                    logger.info("Add new proxy: " + addr);
                }
                builder.add(pool);
            } catch (Exception e) {
                logger.warn("parse " + childData.getPath() + " failed", e);
            }
        }
        this.pools = builder.build();
        for (PooledObject pool : addr2Pool.values()) {
            logger.info("Remove proxy: " + pool.addr);
            pool.pool.close();
        }
    }

    /* 
     * TODO
     * @return
     * @see io.codis.jodis.JedisResourcePool#getResource()
     */
    @Override
    public Jedis getResource() {

        ImmutableList<PooledObject> pools = this.pools;
        if (pools.isEmpty()) {
            throw new JedisException("Proxy list empty");
        }
        for (; ; ) {
            int current = nextIdx.get();
            int next = current >= pools.size() - 1 ? 0 : current + 1;
            if (nextIdx.compareAndSet(current, next)) {
                return pools.get(next).pool.getResource();
            }
        }
    }

    /**
     * 下一个可用的连接池
     *
     * @return
     */
    public JedisPool nextPool() {

        ImmutableList<PooledObject> pools = this.pools;
        if (pools.isEmpty()) {
            throw new JedisException("Proxy list empty");
        }
        for (; ; ) {
            int current = nextIdx.get();
            int next = current >= pools.size() - 1 ? 0 : current + 1;
            if (nextIdx.compareAndSet(current, next)) {
                return pools.get(next).pool;
            }
        }
    }

    /* 
     * TODO
     * @throws IOException
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() {

        try {
            Closeables.close(watcher, true);
        } catch (IOException e) {
            throw new AssertionError("IOException should not have been thrown", e);
        }
        if (closeCurator) {
            curatorClient.close();
        }
        List<PooledObject> pools = this.pools;
        this.pools = ImmutableList.of();
        for (PooledObject pool : pools) {
            pool.pool.close();
        }
    }

    /**
     * Create a {@link RoundRobinJedisPool} using the fluent style api.
     *
     * @return
     */
    public static Builder create() {
        return new Builder();
    }

    public static final class Builder {

        private CuratorFramework curatorClient;

        private boolean closeCurator;

        private String zkProxyDir;

        private String zkAddr;

        private int zkSessionTimeoutMs;

        private JedisPoolConfig poolConfig;

        private int connectionTimeoutMs = Protocol.DEFAULT_TIMEOUT;

        private int soTimeoutMs = Protocol.DEFAULT_TIMEOUT;

        private String password;

        private int database = Protocol.DEFAULT_DATABASE;

        private String clientName;

        private Builder() {
        }

        /**
         * Set curator client.
         *
         * @param curatorClient the client to be used
         * @param closeCurator  whether to close curator client while closing pool
         */
        public Builder curatorClient(CuratorFramework curatorClient, boolean closeCurator) {
            this.curatorClient = curatorClient;
            this.closeCurator = closeCurator;
            return this;
        }

        /**
         * Set codis proxy path on zk.
         *
         * @param zkProxyDir the codis proxy dir on ZooKeeper. e.g.,
         *                   "/zk/codis/db_xxx/proxy"
         */
        public Builder zkProxyDir(String zkProxyDir) {
            this.zkProxyDir = zkProxyDir;
            return this;
        }

        /**
         * Set curator client.
         * <p>
         * We will create curator client based on these parameters and close it
         * while closing pool.
         *
         * @param zkAddr             ZooKeeper connect string. e.g., "zk1:2181"
         * @param zkSessionTimeoutMs ZooKeeper session timeout in milliseconds
         */
        public Builder curatorClient(String zkAddr, int zkSessionTimeoutMs) {
            this.zkAddr = zkAddr;
            this.zkSessionTimeoutMs = zkSessionTimeoutMs;
            return this;
        }

        /**
         * Set jedis pool config.
         */
        public Builder poolConfig(JedisPoolConfig poolConfig) {
            this.poolConfig = poolConfig;
            return this;
        }

        /**
         * Set jedis pool timeout in milliseconds.
         * <p>
         * We will set connectionTimeoutMs and soTimeoutMs both.
         *
         * @param timeoutMs timeout is milliseconds
         */
        public Builder timeoutMs(int timeoutMs) {
            this.connectionTimeoutMs = this.soTimeoutMs = timeoutMs;
            return this;
        }

        /**
         * Set jedis pool connection timeout in milliseconds.
         *
         * @param connectionTimeoutMs timeout is milliseconds
         */
        public Builder connectionTimeoutMs(int connectionTimeoutMs) {
            this.connectionTimeoutMs = connectionTimeoutMs;
            return this;
        }

        /**
         * Set jedis pool connection soTimeout in milliseconds.
         *
         * @param soTimeoutMs timeout is milliseconds
         */
        public Builder soTimeoutMs(int soTimeoutMs) {
            this.soTimeoutMs = soTimeoutMs;
            return this;
        }

        /**
         * Set password.
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Set redis database.
         */
        public Builder database(int database) {
            this.database = database;
            return this;
        }

        /**
         * Set redis client name.
         */
        public Builder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        private void validate() {
            Preconditions.checkNotNull(zkProxyDir, "zkProxyDir can not be null");
            if (curatorClient == null) {
                Preconditions.checkNotNull(zkAddr, "zk client can not be null");
                curatorClient = CuratorFrameworkFactory.builder().connectString(zkAddr)
                        .sessionTimeoutMs(zkSessionTimeoutMs)
                        .retryPolicy(new BoundedExponentialBackoffRetryUntilElapsed(
                                CURATOR_RETRY_BASE_SLEEP_MS, CURATOR_RETRY_MAX_SLEEP_MS, -1L))
                        .build();
                curatorClient.start();
                closeCurator = true;
            } else {
                // we need to get the initial data so client must be started
                if (curatorClient.getState() == LATENT) {
                    curatorClient.start();
                }
            }
            if (poolConfig == null) {
                poolConfig = new JedisPoolConfig();
            }
        }

        /**
         * Create the {@link CodisRoundRobinJedisPool}.
         */
        public CodisRoundRobinJedisPool build() {
            validate();
            return new CodisRoundRobinJedisPool(curatorClient, closeCurator, zkProxyDir, poolConfig,
                    connectionTimeoutMs, soTimeoutMs, password, database, clientName);
        }
    }
}
