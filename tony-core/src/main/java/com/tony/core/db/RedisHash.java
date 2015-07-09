package com.tony.core.db;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class RedisHash {
	
		@Autowired
		private RedisFactory redisFactory;
		
		public int hset(String key, String field, String value) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hset(key, field, value).intValue();
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}
		
		public int hlen(String key) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hlen(key).intValue();
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}
		
		/**
		 * 设置多个值
		 * @param key
		 * @param hash
		 */
		public void hmset(String key, Map<String, String> hash) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				jedis.hmset(key, hash);
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}
		
		public int hset(String key, String field, byte[] value) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hset(key.getBytes(), field.getBytes(), value).intValue();
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}

		
		public String hget(String key, String field) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hget(key, field);
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}

		public byte[] hgetBytes(String key, String field) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hget(key.getBytes(), field.getBytes());
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}
		
		public Map<byte[], byte[]> hgetAll(byte[] key) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hgetAll(key);
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}
		

		public Map<String, String> hgetAll(String key) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hgetAll(key);
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}
		

		
		public int hdel(String key, String field) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hdel(key, field).intValue();
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}

		public int hdelBytes(String key, String field) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hdel(key.getBytes(), field.getBytes()).intValue();
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}

		public boolean hexists(String key, String field) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hexists(key, field);
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}

		public boolean hexistsB(String key, String field) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				return jedis.hexists(key.getBytes(), field.getBytes());
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}
		
		public void hincrBy(String key, String field, int value) {
			Jedis jedis = null;
			try {
				jedis = redisFactory.getJedis();
				jedis.hincrBy(key, field, value);
			} finally {
				redisFactory.returnJedis(jedis);
			}
		}
}
