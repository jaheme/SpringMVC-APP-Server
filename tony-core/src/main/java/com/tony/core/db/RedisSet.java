package com.tony.core.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;


/**
 * redis的set结构 &　sortset结构操作
 * @author jahe.lai
 *
 */
@Component
public class RedisSet {
	
	@Autowired
	private RedisFactory redisFactory;
	
	/**
	 * 将score加入到sorset的排行中
	 * @param key
	 * @param score
	 * @param member
	 * @return 成功添加返回1, member存在返回0
	 */
	public int zadd(String key, long score, String member) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.zadd(key, score, member).intValue();
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/**
	 * 取出score倒序排的前几位, 默认redis是升序排列,score值小的排第一位
	 * @param key
	 * @param start
	 * @param end
	 * @return member列表
	 */
	public Set<String> zrevRange (String key, long start, long end) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			Set<String> mems = jedis.zrevrange(key, start, end);
			return mems;
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	

	/**
	 * 取得member对应用于排行的socre值
	 * @param key
	 * @param member
	 * @return 
	 */
	public Double zscore (String key, String member) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.zscore(key, member);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}

	/**
	 * 从sortset结构里删除指定member对应的item
	 * @param key
	 * @param members
	 * @return 返回成功的数量
	 */
	public long zrem(String key, String... members) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.zrem(key, members);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/** 返回sortset的长度 */
	public int zcard(String key) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.zcard(key).intValue();
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}

	/** 取出score倒序排的前几位, 并将score值一并返回 */
	public Map<String, Long> zrevRangeWithScore(String key, long start, long end) {
		Jedis  jedis = null;
		int len = 2;
		if(start > end) {
			len = 16;
		}
		try {
			jedis = redisFactory.getJedis();
			Set<Tuple> set = jedis.zrevrangeWithScores(key, start, end);
			Map<String, Long> map = new HashMap<String, Long>(len);
			for(Tuple t : set) {
				map.put( t.getElement(), ((Double)t.getScore()).longValue() );
			}
			return map;
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	public Map<String, Long> zrangeWithScore(String key, long start, long end) {
		Jedis  jedis = null;
		int len = 2;
		if(start > end) {
			len = 16;
		}
		try {
			jedis = redisFactory.getJedis();
			Set<Tuple> set = jedis.zrangeWithScores(key, start, end);
			Map<String, Long> map = new HashMap<String, Long>(len);
			for(Tuple t : set) {
				map.put( t.getElement(), ((Double)t.getScore()).longValue() );
			}
			return map;
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/** 返回member组合 */
	public Set<String> zrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = redisFactory.getJedis();
			Set<String> set = jedis.zrange(key, start, end);
			return set;
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 让member对应的score数值递增score指定的量
	 * @param key
	 * @param score 负数:递减 正常:递增
	 * @param member
	 * @return
	 */
	public int zincr(String key, long score, String member) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			Double d = jedis.zincrby(key, score, member);
			return d.intValue();
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/////////////////////////////////////////　ｓｅｔ /////////////////////

	public int sadd(String key, String member) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.sadd(key, member).intValue();
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}

	/** 从set中删除指定的member元素 */
	public int srem(String key, String member) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.srem(key, member).intValue();
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}

	/**  set 的长度 */
	public Long scard(String key) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			return jedis.scard(key);
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}
	
	/**
	 * 返回set的列表
	 */
	public SscanResult sscan(String key, String cursor) {
		Jedis  jedis = null;
		SscanResult r = new SscanResult();
		try {
			jedis = redisFactory.getJedis();
			ScanResult<String> sr = jedis.sscan(key, cursor);
			r.cursor = sr.getStringCursor();
			r.stringList = sr.getResult();
		} finally {
			redisFactory.returnJedis(jedis);
		}
		return r;
	}
	

	/** 不存在时,加入列表 */
	public void addNotExists(String key, String member) {
		Jedis  jedis = null;
		try {
			jedis = redisFactory.getJedis();
			boolean b = jedis.sismember(key, member);
			if( !b ) {
				jedis.sadd(key, member);		
			}
		} finally {
			redisFactory.returnJedis(jedis);
		}
	}

	
	
}
