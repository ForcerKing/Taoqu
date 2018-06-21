/**
 * 
 */
package com.taoqu.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.portal.pojo.SearchResult;
import com.taoqu.portal.service.SearchService;

/**
 * 2018年5月29日
 * SearchServiceImpl.java
 * @author xushaoqun
 * desc:搜索服务实现
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String queryString, int page) {
		//调用taoqu-search服务
		//查询参数
		Map<String,String> param = new HashMap<String, String>();
		param.put("q", queryString);
		param.put("page", page + "");
		try {
			//调用服务
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
			//把字符串转换成java对象
			TaoquResult taoquResult = TaoquResult.formatToPojo(json, SearchResult.class);
			if(taoquResult.getStatus() == 200) {
				SearchResult result = (SearchResult)taoquResult.getData();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
