/**
 * 
 */
package com.taoqu.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.cache.mapper.TbItemMapper;
import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbItem;
import com.taoqu.rest.dao.JedisClient;
import com.taoqu.rest.service.ItemService;

/**
 * 2018年5月30日
 * ItemServiceImpl.java
 * @author xushaoqun
 * desc:商品信息实现类
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	@Autowired
	private JedisClient jedisClient;

	@Override
	public TaoquResult getItem(Long itemId) {
		try {
			//从缓存中获取商品信息，商品id对应的信息
			//Key的格式设置为：REDIS_ITEM_KEY:商品id:base
			String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
			//判断是否有值
			if(!StringUtils.isBlank(json)) {
				//把Json转换成Pojo
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return TaoquResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//缓存未命中，从数据库中查
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		
		try {
			//把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base", JsonUtils.objectToJson(item));
			//设置Key的有效期
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaoquResult.ok(item);
	}

}
