/**
 * 
 */
package com.taoqu.portal.service;

import com.taoqu.portal.pojo.SearchResult;

/**
 * 2018年5月29日
 * SearchService.java
 * @author xushaoqun
 * desc:搜索服务接口
 */
public interface SearchService {
	
	SearchResult search(String queryString,int page);

}
