/**
 * 
 */
package com.taoqu.common.utils;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 2018年5月25日
 * JedisUtils.java
 * @author xushaoqun
 * desc:Jedis工具类(集群版)
 */
public class JedisUtils {

	private static  JedisCluster jedisCluster = null;
	private static  JedisPool jedisPool = null;
	private static  JedisPoolConfig jedisPoolConfig= null;
	private final static ResourceBundle resource = ResourceBundle.getBundle("resource");

	
	private static JedisPoolConfig initJedisPoolConfig() {
		if(jedisPoolConfig == null) {
			jedisPoolConfig = new JedisPoolConfig();
	        
	        jedisPoolConfig.setMaxIdle(Integer.valueOf(resource.getString("maxIdle")));
	        jedisPoolConfig.setNumTestsPerEvictionRun(Integer.valueOf(resource.getString("numTestsPerEvictionRun")));
	        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Integer.valueOf(resource.getString("timeBetweenEvictionRunsMillis")));
	        jedisPoolConfig.setMinEvictableIdleTimeMillis(Integer.valueOf(resource.getString("minEvictableIdleTimeMillis")));
	        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(Integer.valueOf(resource.getString("softMinEvictableIdleTimeMillis")));
	        jedisPoolConfig.setMaxWaitMillis(Integer.valueOf(resource.getString("maxWaitMillis")));
	        jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(resource.getString("testOnBorrow")));
	        jedisPoolConfig.setBlockWhenExhausted(Boolean.parseBoolean(resource.getString("blockWhenExhausted")));
	        jedisPoolConfig.setTestWhileIdle(Boolean.parseBoolean(resource.getString("testWhileIdle")));
		}
        return jedisPoolConfig;
	}
	
	private static JedisCluster initJedisCluster() {
        Set<HostAndPort> set = new HashSet<HostAndPort>();
        set.add(new HostAndPort(resource.getString("redis01").split(":")[0],Integer.valueOf(resource.getString("redis01").split(":")[1])));
        set.add(new HostAndPort(resource.getString("redis02").split(":")[0],Integer.valueOf(resource.getString("redis02").split(":")[1])));
        set.add(new HostAndPort(resource.getString("redis03").split(":")[0],Integer.valueOf(resource.getString("redis03").split(":")[1])));
        set.add(new HostAndPort(resource.getString("redis04").split(":")[0],Integer.valueOf(resource.getString("redis04").split(":")[1])));
        set.add(new HostAndPort(resource.getString("redis05").split(":")[0],Integer.valueOf(resource.getString("redis05").split(":")[1])));
        set.add(new HostAndPort(resource.getString("redis06").split(":")[0],Integer.valueOf(resource.getString("redis06").split(":")[1])));
        jedisCluster = new JedisCluster(set,initJedisPoolConfig());
        return jedisCluster;
	} 
	
	public static JedisCluster getJedisCluster() {
		if(jedisCluster != null) {
			return jedisCluster;
		}else {
			return initJedisCluster();
		}		
	}
	
	private static JedisPool initJedisPool() {
		String host = resource.getString("redis").split(":")[0];
		int port =Integer.valueOf(resource.getString("redis").split(":")[1]) ;
		jedisPool = new JedisPool(initJedisPoolConfig(),host,port);
        return jedisPool;
	}
	
	public static Jedis getJedis() {
		if( jedisPool != null) {
			return jedisPool.getResource();
		}else {
			return initJedisPool().getResource();
		}
	}
}
