/**
 * 
 */
package com.taoqu.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import com.taoqu.common.utils.JedisUtils;
import com.taoqu.common.utils.SerializeUtil;

/**
 * 2018年5月25日
 * RedisCache.java
 * @author xushaoqun
 * desc:借助JedisCluster实现MyBatis的二级缓存
 */
public class MyBatisClientCluster implements Cache {
	
	
	//private static Logger logger = LogManager.getLogger(MyBatisClientCluster.class);
	
	/*
	 * 这里如果要采用注入的方式，将JedisCluster注入到MyBatisClientCluster中
	 * 将无法正常实现功能，原因是MyBatis不会使用IOC创建的MyBatisClientCluster
	 * 对象实例，而是调用指定的构造函数：MyBatisClientCluster(final String id)，
	 * 为每一个Mapper实体类创建一个对象，id为mapper类的全类名(例子：com.taoqu.cache.mapper.TbItemMapper)
	 * 由于MyBatisClientCluster(final String id)不会自动调用@Autowired
	 * 因此所有的对象的jedisCluster=null，相应的getObject等方法会报错。
	 * 所以这里只能使用JedisUtils来获取JedisCluster
	 */
	//@Autowired
    //private JedisCluster jedisCluster;
	
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	
	private String id;

	public MyBatisClientCluster(final String id) {
        if(id == null) {
        	throw new IllegalArgumentException("Cache instances requires an ID");
        }
        //logger.debug("Cache instance created,id="+id);
        this.id = id;
	}

	@Override
	public void clear() {
		JedisUtils.getJedisCluster().flushDB();
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Object getObject(Object key) {
		String sKey = SerializeUtil.serizlize(key); 
		String sValue = JedisUtils.getJedisCluster().get(sKey);
		if(sValue == null) {
			return null;
		}
		Object obj = SerializeUtil.deserialize(sValue);
		return obj;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	@Override
	public int getSize() {
		return Integer.valueOf(JedisUtils.getJedisCluster().dbSize().toString()) ;
	}

	@Override
	public void putObject(Object key, Object value) {
		JedisUtils.getJedisCluster().set(SerializeUtil.serizlize(key), SerializeUtil.serizlize(value));
	}

	@Override
	public Object removeObject(Object key) {
		return JedisUtils.getJedisCluster().expire(SerializeUtil.serizlize(key), 0);
	}

}
