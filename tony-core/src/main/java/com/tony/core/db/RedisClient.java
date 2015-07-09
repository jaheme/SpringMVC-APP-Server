package com.tony.core.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * 供外部应用调用的redis操作接口
 * @author jahe
 *
 */
@Component
public class RedisClient {

	@Autowired
	private RedisFactory redisFactory;
	
	public String set(String key, String value) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.set(key, value);
		} finally {
			redisFactory.returnJedis(jedis);
		}		
	}
	
	public void set(String key, byte[] value) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			jedis.set(key.getBytes(), value);
		} finally {
			redisFactory.returnJedis(jedis);
		}		
	}

	public void del(String key) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			jedis.del(key);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}


	/** 设置超时缓存 */
	public void setex(String key, byte[] value , int seconds) {
		this.setex(key.getBytes(), value, seconds);
	}
	
	private void setex(byte[] key, byte[] value , int seconds) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			jedis.setex(key, seconds, value);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}

	/** 取缓存内key对应的值 */
	public byte[] getBytes(String key) {
		return this.get(key.getBytes());
	}
	
	private byte[] get(byte[] key) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.get(key);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}

	public void delBytes(String key) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			jedis.del(key.getBytes());
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	
	/** 设置超时缓存 */
	public void setex(String key, String value , int seconds) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			jedis.setex(key, seconds, value);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/** 取缓存内key对应的值 */
	public String get(String key) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.get(key);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
}
