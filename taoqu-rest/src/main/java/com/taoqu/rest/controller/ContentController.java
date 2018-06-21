/**
 * 
 */
package com.taoqu.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.ExceptionUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbContent;
import com.taoqu.rest.service.ContentService;

/**
 * 2018年5月21日
 * ContentController.java
 * @author xushaoqun
 * desc:内容控制器
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public TaoquResult getContentList(@PathVariable Long contentCategoryId) {
		
		try {
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return TaoquResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return TaoquResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
