package com.tony.core.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * list数据结构的操作
 * @author jahe.lai
 *
 */
@Component
public class RedisList {


	@Autowired
	private RedisFactory redisFactory;
	
	
	/**
	 * 将value加到key指定的列表尾 tail
	 * @param key
	 * @param value
	 * @return 成功的数量
	 */
	public long rpush(String key, String... value) {
		Jedis jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.rpush(key, value);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}

	public long rpushBytes(String key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.rpush(key.getBytes(), value);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	

	/**
	 * 返回索引位置的列表元素值
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key, long index) {
		Jedis jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.lindex(key, index);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	

	public byte[] lindexBytes(String key, long index) {
		Jedis jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.lindex(key.getBytes(), index);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/** 将value加到key指定的列表头 head **/
	public long lpush(String key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.lpush(key.getBytes(), value);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	public int lpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.lpush(key, value).intValue();
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/** 返回指定key的list的长度 */
	public int llen(String key) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.llen(key).intValue();
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/**
	 * 移除key的列表中与value相等的项
	 * @param key
	 * @param count count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
	 * 				count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
	 * 				count = 0 : 移除表中所有与 value 相等的值。
	 * @param value
	 * @return
	 */
	public int lrem(String key, int count, String value) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.lrem(key, count, value).intValue();
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	public List<String> lrange(String key, long start , long end) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.lrange(key, start, end);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	public List<byte[]> lrangeBytes(String key, long start , long end) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.lrange(key.getBytes(), start, end);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
}
