/**
 * 
 */
package com.taoqu.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.taoqu.search.pojo.SearchResult;

/**
 * 2018年5月29日
 * SearchDao.java
 * @author xushaoqun
 * desc:搜索接口
 */
public interface SearchDao {
	
	SearchResult search(SolrQuery query) throws Exception;

}
