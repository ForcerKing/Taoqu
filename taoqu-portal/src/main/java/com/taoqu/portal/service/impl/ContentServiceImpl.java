/**
 * 
 */
package com.taoqu.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbContent;
import com.taoqu.portal.service.ContentService;

/**
 * 2018年5月22日
 * ContentServiceImpl.java
 * @author xushaoqun
 * desc:调用服务层服务，查询内容列表
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;

	@Override
	public String getContentList() {
		// 调用服务层的服务
		String result = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
		// 把字符串转换成TaoquResult
		try {
			TaoquResult taoquResult = TaoquResult.formatToList(result, TbContent.class);
			//取内容列表
			List<TbContent> list = (List<TbContent>) taoquResult.getData();
			List<Map> resultList = new ArrayList<>();
 			//创建一个jsp页码要求的pojo列表
			for (TbContent tbContent : list) {
				Map map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 550);
				map.put("heightB", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

	/**
	 * @return the rEST_BASE_URL
	 */
	public String getREST_BASE_URL() {
		return REST_BASE_URL;
	}

	/**
	 * @param rEST_BASE_URL the rEST_BASE_URL to set
	 */
	public void setREST_BASE_URL(String rEST_BASE_URL) {
		REST_BASE_URL = rEST_BASE_URL;
	}

	/**
	 * @return the rEST_INDEX_AD_URL
	 */
	public String getREST_INDEX_AD_URL() {
		return REST_INDEX_AD_URL;
	}

	/**
	 * @param rEST_INDEX_AD_URL the rEST_INDEX_AD_URL to set
	 */
	public void setREST_INDEX_AD_URL(String rEST_INDEX_AD_URL) {
		REST_INDEX_AD_URL = rEST_INDEX_AD_URL;
	}

	
}
