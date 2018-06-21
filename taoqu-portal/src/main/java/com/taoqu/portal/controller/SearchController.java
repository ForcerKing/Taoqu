/**
 * 
 */
package com.taoqu.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taoqu.portal.pojo.SearchResult;
import com.taoqu.portal.service.SearchService;

/**
 * 2018年5月29日
 * SearchController.java
 * @author xushaoqun
 * desc:搜索服务控制器
 */
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q") String queryString,@RequestParam(defaultValue="1")Integer page,Model model) {
		if(queryString != null) {
			try {
				queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		
		SearchResult searchResult = searchService.search(queryString, page);	
		//向页面传递参数
		model.addAttribute("query",queryString);
		model.addAttribute("totalPages",searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page",page);	
		return "search";
		
	}

}
