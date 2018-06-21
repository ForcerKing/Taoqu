/**
 * 
 */
package com.taoqu.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

import com.taoqu.common.utils.JedisUtils;
import com.taoqu.common.utils.SerializeUtil;

import redis.clients.jedis.Jedis;

/**
 * 2018年5月25日
 * MyBatisClientSingle.java
 * @author xushaoqun
 * desc:借助jedisPool实现MyBatis二级缓存(redis单机版)
 */
public class MyBatisClientSingle implements Cache {
	
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	
	private String id;
	
	public MyBatisClientSingle(final String id) {
        if(id == null) {
        	throw new IllegalArgumentException("Cache instances requires an ID");
        }
        this.id = id;
	}
	@Override
	public void clear() {
		Jedis jedis = JedisUtils.getJedis();
		jedis.flushDB();
		jedis.close();
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Object getObject(Object key) {
		Jedis jedis = JedisUtils.getJedis();
		String sKey = SerializeUtil.serizlize(key); 
		String sValue = jedis.get(sKey);
		if(sValue == null) {
			return null;
		}
		Object obj = SerializeUtil.deserialize(sValue);
		jedis.close();
		return obj;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	@Override
	public int getSize() {
		Jedis jedis = JedisUtils.getJedis();
		Integer size = Integer.valueOf(jedis.dbSize().toString());
		jedis.close();
		return size;
	}

	@Override
	public void putObject(Object key, Object value) {
		Jedis jedis =JedisUtils.getJedis();
		jedis.set(SerializeUtil.serizlize(key), SerializeUtil.serizlize(value));
		jedis.close();
	}

	@Override
	public Object removeObject(Object key) {
		Jedis jedis = JedisUtils.getJedis();
		Long obj = jedis.expire(SerializeUtil.serizlize(key), 0);
		return obj;
	}

}
