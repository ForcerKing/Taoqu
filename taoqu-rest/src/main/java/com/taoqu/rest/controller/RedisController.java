/**
 * 
 */
package com.taoqu.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.rest.service.RedisService;

/**
 * 2018年5月24日
 * RedisController.java
 * @author xushaoqun
 * desc:redis同步控制器
 */
@Controller
@RequestMapping("/redis")
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/sync/{hKey}/{key}")
	@ResponseBody
	public TaoquResult cacheSync(@PathVariable String hKey,@PathVariable String key) {
		TaoquResult result = redisService.syncRedis(hKey, key);
		return result;
	}
	
	
}
