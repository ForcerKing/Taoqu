/**
 * 
 */
package com.taoqu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.service.ItemParamItemService;

/**
 * 2018年5月9日
 * ItemParamItemController.java
 * @author xushaoqun
 * desc:商品规格明细控制器,展示商品规格参数
 * 
 */
@Controller
public class ItemParamItemController {
  
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/showItem/{itemId}")
	public String showItemParamItem(@PathVariable Long itemId,Model model) {
		String string = itemParamItemService.getItemParamItemByItemId(itemId);
		model.addAttribute("itemParam",string);
		return "item";	
	}
	/*
	 * 获取商品规格明细
	 *  http://localhost:8080/rest/item/param/item/query/990723 
	 */
	@RequestMapping("/rest/item/param/item/query/{id}")
	@ResponseBody
	public TaoquResult getItemParamItemByItemId(@PathVariable Long id) {
		TaoquResult result = itemParamItemService.getItemParamItemByItemId2(id);
		return result;
	}
}
