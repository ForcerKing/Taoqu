/**
 * 
 */
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
import com.taoqu.pojo.TbItemParam;
import com.taoqu.service.ItemParamService;

/**
 * 2018年5月7日
 * ItemParamController.java
 * @author xushaoqun
 * desc:商品规格参数控制器
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParmService;
	/*
	 * 点击首页左侧“规格参数”，获取tb_item_param表中所有数据，以及join一个tb_item_cat
	 * 表中的itemCatName列，返回给前端显示，借助PageHelper，一次只选取前30行
	 */
	@RequestMapping("/list")
	@ResponseBody
	public DTOForEasyUIDataGrid getItemParamAndItemCatNameList(int page,int rows) {
		
		DTOForEasyUIDataGrid dTOForEasyUIDataGrid = itemParmService.getItemParamAndItemCatNameList(page, rows);
		return dTOForEasyUIDataGrid;	
	}
	/*
	 * 用户在规格参数标签页中点击新增，弹出“选择类目”按钮，点击“选择类目”后，会弹出一个商品分类
	 * 列表，用户选择列表中的某类商品，点击后，会把这个商品的cid发送至下面这个方法。而下面这个方法
	 * 会根据cid查找tb_item_param表，如果找到，说明要添加的商品分类所对应的规格参数已存在，要提醒
	 * 用户“该商品分类规格已存在”，否则，则允许用户继续添加
	 */
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public TaoquResult getItemParamByCid(@PathVariable Long cid) {
		TaoquResult result = itemParmService.getItemParamByCid(cid);
		return result;
		
	}
	
	/*
	 * 添加一条新的规格参数
	 */
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaoquResult insertItemParam(@PathVariable Long cid,@RequestParam String paramData) {
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid) ;
		tbItemParam.setParamData(paramData);
		TaoquResult result = itemParmService.insertItemParam(tbItemParam);
		return result;		
	}
	
	/*
	 * 删除一种商品的规格参数
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaoquResult deleteItemParam(@RequestParam Long[] ids) {//@RequestParam注解可以省略，但是后面的参数名称必须要和url后的参数名称一致
		TaoquResult result = itemParmService.deleteItemParam(ids);
		return result;
	}

}
