/**
 * 
 */
package com.taoqu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.dto.DTOForEasyUITreeNode;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.service.ContentCategoryService;
import com.taoqu.service.ContentService;

/**
 * 2018年5月17日
 * ContentCategoryController.java
 * @author xushaoqun
 * desc:内容分类控制器
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<DTOForEasyUITreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0") Long parentId){
		List<DTOForEasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}
	
	/*
	 * 添加一条内容分类
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TaoquResult createContentCategory(@RequestParam(value="parentId") Long parentId,@RequestParam(value="name") String name) {
		TaoquResult result = contentCategoryService.insertContentCategory(parentId, name);
	    return result;
	}
	
	/*
	 * 删除一条内容分类
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST) //路径名一定要小心，和jsp中的路径保持一致
	@ResponseBody
	public TaoquResult deleteContentCategory(@RequestParam(value="parentId",required=false) Long parentId,@RequestParam(value="id") Long id) throws Exception {
		//先删除内容，在删除内容分类，顺序不能颠倒！
		TaoquResult result1 = contentService.deleteContentsByCategoryId(id);
		TaoquResult result2 = contentCategoryService.deleteContentCategory(parentId, id);
		
		if(result1.equals("ok") && result2.equals("ok")) {
			return TaoquResult.ok();
		}
		return null;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST) //路径名一定要小心，和jsp中的路径保持一致
	@ResponseBody
	public TaoquResult updateContentCategory(@RequestParam(value="id") Long id,@RequestParam(value="name") String name)  {
		TaoquResult result = contentCategoryService.updateContentCategory(id, name);
	    return result;
	}

}
