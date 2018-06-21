/**
 * 
 */
package com.taoqu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.dto.DTOForEasyUITreeNode;
import com.taoqu.pojo.TbItemCat;
import com.taoqu.service.ItemCatService;

/**
 * 2018年4月27日
 * ItemCatController.java
 * @author xushaoqun
 * desc:商品类目
 */

@Controller
@RequestMapping(value="/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	/*
	 * 从数据库中将所有的商品类目提取出来，以json格式返回给前端
	 * 前端的商品类目，支持的数据结构如下：
	 * 数据结构中必须包含：
     * Id：节点id
     * Text：节点名称	
     * State：如果不是叶子节点就是close，叶子节点就是open。Close的节点点击后会在此发送请求查询子项目。
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list")
	@ResponseBody
	public List catagoryList(@RequestParam(value="id",defaultValue="0") Long parentId) throws Exception{
		
//		List<Map<String,String>> catList = new ArrayList<Map<String,String>>();
//		
//		List<TbItemCat> list = itemCatService.getItemCatList(parentId);
//		
//		for(TbItemCat tbItemCat:list) {
//			Map<String,String> node = new HashMap<String,String>();
//			node.put("id", tbItemCat.getId().toString());
//			node.put("text", tbItemCat.getName());
//			//如果是父节点的话就设置成关闭状态，如果是子节点就是open状态；
//			node.put("state", tbItemCat.getIsParent()?"closed":"open");
//			catList.add(node);
//		}
		List<DTOForEasyUITreeNode> catList = itemCatService.getCatList(parentId);
		return catList;
	}

}
