/**
 * 
 */
package com.taoqu.sso.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.SerializeUtil;
import com.taoqu.sso.dao.JedisClient;

import redis.clients.jedis.JedisCluster;

/**
 * 2018年5月23日
 * JedisClientSingle.java
 * @author xushaoqun
 * desc:集群版实现类
 */
public class JedisClientCluster implements JedisClient {

	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String hget(String hkey, String key) {
		return jedisCluster.hget(hkey, key);
	}

	@Override
	public long hset(String hkey, String key, String value) {
		return jedisCluster.hset(hkey, key, value);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long expire(String key, int second) {
		return jedisCluster.expire(key, second);
	}

	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
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
		return jedisCluster.del(key);
	}

	@Override
	public long hdel(String hKey, String key) {
		return jedisCluster.hdel(hKey, key);
	}


}
