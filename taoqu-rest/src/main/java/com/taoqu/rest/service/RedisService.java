/**
 * 
 */
package com.taoqu.rest.service;

import com.taoqu.common.utils.TaoquResult;

/**
 * 2018年5月24日
 * RedisService.java
 * @author xushaoqun
 * desc:缓存同步服务，供后台系统调用
 */
public interface RedisService {

	TaoquResult syncRedis(String hKey,String key);
}
