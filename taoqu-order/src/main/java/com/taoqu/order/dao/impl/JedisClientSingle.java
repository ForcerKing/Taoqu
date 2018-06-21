/**
 * 
 */
package com.taoqu.order.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taoqu.common.utils.SerializeUtil;
import com.taoqu.order.dao.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 2018年5月23日
 * JedisClientSingle.java
 * @author xushaoqun
 * desc:单机版实现类
 */
public class JedisClientSingle implements JedisClient{
	
	@Autowired
	private JedisPool jedisPool; 
	
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public Object getObject(String hKey, String key) {
		String string = this.hget(hKey, key);
		if(string == null) {
			return null;
		}
		Object obj = SerializeUtil.deserialize(string);
		return obj;
	}

	@Override
	public long setObject(String hKey, String key, Object obj) {
		String string = SerializeUtil.serizlize(obj);
		long result = this.hset(hKey, key, string);
		return result;
	}

	@Override
	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hKey, String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(hKey, key);
		jedis.close();
		return result;
	}

}

