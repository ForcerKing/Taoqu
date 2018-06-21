/**
 * 
 */
package com.taoqu.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 2018年5月23日
 * JedisTest.java
 * @author xushaoqun
 * desc:jedis测试类
 */
public class JedisTest {

	@Test
	public void testJedisSingle() {
		//创建一个jedis对象
		Jedis jedis = new Jedis("192.168.195.178",6379);
		//调用jedis对象的方法，方法名称和redis命令一致。
		jedis.set("key1", "jedis test");
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭jedis
		jedis.close();
	}
	
	/*
	 * 使用连接池
	 */
	@Test
	public void testJedisPool() {
		//创建jedis连接池
		JedisPool pool = new JedisPool("192.168.195.178",6379);
		//从连接池中获取连接池对象
		Jedis jedis = pool.getResource();
		String string = jedis.get("Key1");
		System.out.println(string);
		jedis.close();
		pool.close();	
	}
	
	/*
	 * 集群访问形式
	 */
	@Test
	public void testJedisCluster() {
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.195.178", 7001));
		nodes.add(new HostAndPort("192.168.195.178", 7002));
		nodes.add(new HostAndPort("192.168.195.178", 7003));
		nodes.add(new HostAndPort("192.168.195.178", 7004));
		nodes.add(new HostAndPort("192.168.195.178", 7005));
		nodes.add(new HostAndPort("192.168.195.178", 7006));
		
		JedisCluster cluster = new JedisCluster(nodes);
		
		cluster.set("key1", "1000");
		String string = cluster.get("key1");
		System.out.println(string);
		
		cluster.close();
	}
	/*
	 * 整合spring
	 */
	@Test
	public void testSpringJedisCluster() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
	}


}
