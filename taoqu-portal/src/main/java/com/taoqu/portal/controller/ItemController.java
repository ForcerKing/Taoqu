/**
 * 
 */
package com.taoqu.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.portal.pojo.ItemInfo;
import com.taoqu.portal.service.ItemDescService;
import com.taoqu.portal.service.ItemParamItemService;
import com.taoqu.portal.service.ItemService;

/**
 * 2018年5月30日
 * ItemController.java
 * @author xushaoqun
 * desc:商品信息控制器
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemParamItemService itemParamItemService;

	/*
	 * 商品基本信息
	 */
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable Long itemId, Model model) {
		ItemInfo item = itemService.getItemById(itemId);
		model.addAttribute("item", item);
		return "item";
	}
	
	/*
	 * 商品描述
	 */
	@RequestMapping(value="/item/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId) {
		String string = itemDescService.getItemDescById(itemId);
		return string;
	}
	
	/*
	 * 商品规格参数明细
	 */
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId) {
		String string = itemParamItemService.getItemParam(itemId);
		return string;
	}


}

