/**
 * 
 */
package com.taoqu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.dto.DTOForEasyUIDataGrid;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbContent;
import com.taoqu.service.ContentService;

/**
 * 2018年5月18日
 * ContentController.java
 * @author xushaoqun
 * desc:内容管理控制器
 */

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	/*
	 * 其实这里的参数列表里也可以不写@RequestParam，但是如果要这样做，那么参数列表中的参数名称必须要与
	 * js中的参数名一致，即必须要写成page和categoryId,而不能是pagesize和categoryid
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public DTOForEasyUIDataGrid getContentList(@RequestParam(value="page") int pagesize,int rows,@RequestParam(value="categoryId") Long categoryid) {
		DTOForEasyUIDataGrid result = contentService.getContentList(pagesize, rows, categoryid);
	    return result;
	}
	//增添一条内容记录
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public TaoquResult createContent(TbContent content) {
		TaoquResult result = contentService.insertContent(content);
		return result;
	}
	
	//删除一个或多个内容记录
	@RequestMapping("/delete")
	@ResponseBody
	public TaoquResult deleteContents(Long[] ids) throws Exception {
		TaoquResult result = contentService.deleteContents(ids);
		return result;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public TaoquResult updateContent(TbContent content) throws Exception{
		TaoquResult result = contentService.updateContent(content);
		return result;
	}

}
