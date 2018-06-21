/**
 * 
 */
package com.taoqu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.service.ItemDescService;

/**
 * 2018年5月9日
 * ItemDescController.java
 * @author xushaoqun
 * desc:
 */
@Controller
@RequestMapping("/rest/item")
public class ItemDescController {
	
	@Autowired
	private ItemDescService itemDescService;
	
	/*
	 * 获取商品描述
	 */
	@RequestMapping("/query/item/desc/{id}")
	@ResponseBody
	public TaoquResult getItemDescByItemId(@PathVariable Long id) {
		TaoquResult result = itemDescService.getItemDescByItemId(id);
	    return result;
	}

}
