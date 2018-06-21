/**
 * 
 */
package com.taoqu.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.JsonUtils;
import com.taoqu.cache.mapper.TbContentMapper;
import com.taoqu.pojo.TbContent;
import com.taoqu.pojo.TbContentExample;
import com.taoqu.pojo.TbContentExample.Criteria;
import com.taoqu.rest.dao.JedisClient;
import com.taoqu.rest.service.ContentService;

/**
 * 2018年5月21日
 * ContentServiceImpl.java
 * @author xushaoqun
 * desc:前台：内容服务实现类
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	/*
	 * For 前台功能：根据contentCid,即内容分类查询列表
	 */
	@Override
	public List<TbContent> getContentList(long contentCid){
		//从缓存中取内容
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid + "");
            if(!StringUtils.isBlank(result)) {
            	List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
                return resultList;
            }		
		}catch(Exception e){
			e.printStackTrace();
		}
		//根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//向缓存中添加内容
		try {
			//把list转换成字符串
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCid + "", cacheString);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
		
	}

}
