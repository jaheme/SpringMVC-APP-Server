package com.tony.core.db;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisFactory {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static JedisPool jedisPool;
    
    @Value("${redis.host}")
    private String redisHost;
    
    @Value("${redis.port}")
    private int redisPort=6379;

    @PostConstruct
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(100);
        config.setMinEvictableIdleTimeMillis(-1);
        jedisPool = new JedisPool(config, redisHost, redisPort, 10000);
        Jedis jedis = jedisPool.getResource();
        returnJedis(jedis);
        logger.warn("redis inited. host:{}  port:{}", redisHost, redisPort);
    }


    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void returnJedis(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
    }

}
