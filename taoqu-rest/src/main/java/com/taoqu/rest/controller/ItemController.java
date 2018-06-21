/**
 * 
 */
package com.taoqu.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.rest.service.ItemDescService;
import com.taoqu.rest.service.ItemParamItemService;
import com.taoqu.rest.service.ItemService;

/**
 * 2018年5月30日
 * ItemController.java
 * @author xushaoqun
 * desc:商品基本信息控制器
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	/*
	 * 获取商品基本信息
	 */
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaoquResult getItem(@PathVariable Long itemId) {
		TaoquResult result = itemService.getItem(itemId);
		return result;
	}
	/*
	 * 获取商品描述
	 */
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaoquResult getItemDesc(@PathVariable Long itemId) {
		TaoquResult result = itemDescService.getItemDesc(itemId);
		return result;
	}
	
	/*
	 * 获取商品规格参数明细
	 */
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaoquResult getItemParamItem(@PathVariable Long itemId) {
		TaoquResult result =itemParamItemService.getItemParamItem(itemId);
		return result;
	}
}
