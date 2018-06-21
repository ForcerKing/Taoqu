package com.taoqu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.dto.DTOForEasyUIDataGrid;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbItem;
import com.taoqu.service.ItemService;
/*
 * 商品管理
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	/**
	 * 
	 * 2018年4月23日
	 * desc:返回商品列表
	 * @param page
	 * @param rows
	 * @return DTOForEasyUIDataGrid 自动转化为json提交给前端
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public DTOForEasyUIDataGrid getItemList(Integer page,Integer rows) {
		DTOForEasyUIDataGrid dTOForEasyUIDataGrid = itemService.getItemList(page, rows);
	    return dTOForEasyUIDataGrid;
	}

	/*
	 * 增添一件sku的商品信息、描述和规格参数明细
	 * springMVC会自动地把form中各组件中的name和Tbitem中的属性做匹配
	 * 其他的desc和itemParams这个两个参数必须要和form表单中name属性的值是一致的
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	private TaoquResult createItem(TbItem item,@RequestParam String desc, String itemParams) throws Exception {
		TaoquResult result = itemService.createItem(item,desc,itemParams);
		return result;		
	}
	
	/*
	 * 修改商品信息、描述、规格明细
	 */
	@RequestMapping(value="/rest/item/update",method=RequestMethod.POST)
	@ResponseBody
	private TaoquResult updateItem(TbItem item, String desc, String itemParams, Long itemParamId)throws Exception {
		TaoquResult result = itemService.updateItem(item, desc, itemParams, itemParamId);
		return result;		
	}
	
	
	@RequestMapping("/rest/page/item-edit")
	private String getItemById(Long id) {
		return "item-edit";
	}
	
	/*
	 * 删除一个sku的商品明细、描述和规格参数明细
	 */
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaoquResult deleteItem(@RequestParam Long[] ids) throws Exception {//@RequestParam注解可以省略，但是后面的参数名称必须要和url后的参数名称一致
		TaoquResult result = itemService.deleteItem(ids);
		return result;
	}
}
