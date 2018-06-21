/**
 * 
 */
package com.taoqu.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.rest.pojo.CatResult;
import com.taoqu.rest.service.ItemCatService;

/**
 * 2018年5月15日
 * ItemCatController.java
 * @author xushaoqun
 * desc:商品分类列表控制器
 */
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	
	/*
	 * produces规定返回的json格式的编码方式，不然会出现乱码
	 */
	/*@RequestMapping(value="/itemcat/list",produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		//把pojo转换成字符串
		String json = JsonUtils.objectToJson(catResult);
		//拼装返回值
		String result = callback + "(" + json + ");";
		return result;
		
	}*/
	
	
	/*
	 * Spring4.1支持如下方法
	 */
	@RequestMapping(value="/itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
}
