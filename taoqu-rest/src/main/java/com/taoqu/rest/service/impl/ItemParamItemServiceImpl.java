/**
 * 
 */
package com.taoqu.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.cache.mapper.TbItemParamItemMapper;
import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbItemParamItem;
import com.taoqu.pojo.TbItemParamItemExample;
import com.taoqu.pojo.TbItemParamItemExample.Criteria;
import com.taoqu.rest.dao.JedisClient;
import com.taoqu.rest.service.ItemParamItemService;

/**
 * 2018年5月30日
 * ItemParamItemServiceImpl.java
 * @author xushaoqun
 * desc:商品规格参数实现类
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired 
	private TbItemParamItemMapper itemParamItemMapper;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	@Autowired
	private JedisClient jedisClient;


	@Override
	public TaoquResult getItemParamItem(long itemId) {
		//添加缓存
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return TaoquResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询规格参数
		//设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size()>0) {
			TbItemParamItem paramItem = list.get(0);
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaoquResult.ok(paramItem);
		}
		return TaoquResult.build(400, "无此商品规格");

	}

}
