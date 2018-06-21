/**
 * 
 */
package com.taoqu.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.search.service.ItemService;

/**
 * 2018年5月29日
 * ItemController.java
 * @author xushaoqun
 * desc:Item导入控制器
 */
@Controller
@RequestMapping("/manager")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/*
	 * 导入商品数据到索引库
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public TaoquResult importAllItems() {
		TaoquResult result = itemService.importAllItems();
		return result;
	}
}
