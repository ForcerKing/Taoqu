/**
 * 
 */
package com.taoqu.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taoqu.portal.service.ContentService;

/**
 * 2018年5月14日
 * IndexController.java
 * @author xushaoqun
 * desc:首页显示
 */
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		String adJson = contentService.getContentList();
		//前端jsp需要根据一个json数据来初始化大广告位
		model.addAttribute("ad1",adJson);
		return "index";
	}
}
