/**
 * 
 */
package com.taoqu.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.ExceptionUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.rest.dao.JedisClient;
import com.taoqu.rest.service.RedisService;

/**
 * 2018年5月24日
 * RedisServiceImpl.java
 * @author xushaoqun
 * desc:RedisService实现类
 */
@Service
public class RedisServiceImpl implements RedisService {
	
	@Autowired
	private JedisClient jedisClient;

	/*
	 * 输入hkey和key，删除redis中对应的缓存内容
	 */
	@Override
	public TaoquResult syncRedis(String hKey,String key) {
		try {
			if(hKey != null && key != null) {
				jedisClient.hdel(hKey, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TaoquResult.build(500, ExceptionUtil.getStackTrace(e));
		}	
		return TaoquResult.ok();
	}

}
