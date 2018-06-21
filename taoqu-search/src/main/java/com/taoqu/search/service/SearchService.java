/**
 * 
 */
package com.taoqu.search.service;

import com.taoqu.search.pojo.SearchResult;

/**
 * 2018年5月29日
 * SearchService.java
 * @author xushaoqun
 * desc:
 */
public interface SearchService {

	public SearchResult search(String queryString, int page, int rows) throws Exception;
}
