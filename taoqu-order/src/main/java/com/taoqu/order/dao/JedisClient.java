/**
 * 
 */
package com.taoqu.order.dao;

/* 
 * 2018年5月23日
 * JedisClient.java
 * @author xushaoqun
 * desc:封装Redis的各种方法，适配单机版和集群版都可以.
 * 该接口实现了类似于Mybatis二级缓存的功能，这里也可用通过实现Mybatis的
 * Cache接口来完成这一功能，两种方案各有利弊，对比如下：
 * 1.如果采用实现MyBatis的Cache接口来实现二级缓存，那么只能实现Cache接口中
 * 自带的putObject方法和getObject方法。这两个方法强制性的要求Object必须支持
 * 序列化和反序列化。因此，当我们只是需要向redis中读/写简单的数据结构，如Integer或者
 * String类型的时候，也依然要被强制地进行序列化和反序列化。此外，绝大多数的实体类也要
 * 实现Serializable接口。而下面的这个JedisClient接口定义了多种set/get方法，程序员
 * 可以根据自己要向redis读写的数据结构类型，灵活地选取效率最高的方法来完成相应的读写操作。
 * 2.如果采用实现MyBatis的Cache接口，那么意味着我们的项目只能依赖MyBatis框架来
 * 操作redis，如果要换ORM框架，与redis相关的功能都要重构。
 * 3.我们可以自由地决定JedisClient的调用位置。很多情况下，我们可以选择在创建
 * 查询条件之前就去访问redis并获得结果。如果采用实现MyBatis的Cache接口的方式，那么
 * 我们只有等MyBatis解析完sql，才能知道我们要的数据是不是在redis中。在这种情况下，
 * 一旦我们要查询的结果位于redis中，那么我们之前创建的查询条件对象(xxExample)就相当于
 * 白白创建了。
 * 
 * 缺点：
 * 我们需要显式地调用jedisClient，而通过MyBatis实现了cache接口后，则不需要显式调用
 * redis的读写，大幅度减少了缓存调用代码对业务代码的侵入，代码风格更加优雅和简洁。
 */
public interface JedisClient {
	String get(String key);
	String set(String key,String value);
	String hget(String hKey,String key);
	long hset(String hKey,String key,String value);
	long incr(String key);//自增方法
	long expire(String key,int second);//设置key的生命周期
	long ttl(String key);//判断一个key还有多少生命周期
	Object getObject(String hKey,String key);
	long setObject(String hKey,String key,Object obj);
	long del(String key);//删除key
	long hdel(String hKey,String key);
}
