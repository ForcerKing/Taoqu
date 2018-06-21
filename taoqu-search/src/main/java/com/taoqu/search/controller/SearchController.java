/**
 * 
 */
package com.taoqu.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.ExceptionUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.search.pojo.SearchResult;
import com.taoqu.search.service.SearchService;

/**
 * 2018年5月29日
 * SearchController.java
 * @author xushaoqun
 * desc:
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/query", method=RequestMethod.GET)
	@ResponseBody
	public TaoquResult search(@RequestParam("q")String queryString, 
			@RequestParam(defaultValue="1")Integer page, 
			@RequestParam(defaultValue="60")Integer rows) {
		//查询条件不能为空
		if (StringUtils.isBlank(queryString)) {
			return TaoquResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
			searchResult = searchService.search(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return TaoquResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaoquResult.ok(searchResult);
		
	}
	
}

