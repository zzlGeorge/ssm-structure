package com.vct.common.redis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * description: RedisClient <br>
 * date: 2019/8/12 18:21 <br>
 * author: lszhangzhangl <br>
 * version: 1.0 <br>
 */
public class RedisClient {

    private ThreadLocal<Jedis> jedis = new ThreadLocal<Jedis>();//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池

    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池

    private final String host = "xx.xx.xx.xx";
    private final String pswd = "xxxxxxxx";
    private final int port = 7001;

    public RedisClient() {
        initialPool();
        initialShardedPool();
        shardedJedis = shardedJedisPool.getResource();
    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000L);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, host, port, 2000, pswd);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000L);
        config.setTestOnBorrow(false);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo(host, port, "master"));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }


    public Jedis getJedisConn() {
        Jedis resource = jedisPool.getResource();
        jedis.set(resource);
        return resource;
    }

    public void closeJedisConn() {
        Jedis jedis = this.jedis.get();
        if (jedis != null) {
            jedis.close();
        }
    }
}
