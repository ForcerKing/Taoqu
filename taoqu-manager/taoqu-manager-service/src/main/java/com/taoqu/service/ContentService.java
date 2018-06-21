/**
 * 
 */
package com.taoqu.service;

import com.taoqu.common.dto.DTOForEasyUIDataGrid;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbContent;

/**
 * 2018年5月18日
 * ContentService.java
 * @author xushaoqun
 * desc:内容管理接口
 */
public interface ContentService {
	
	//根据内容分类，返回内容列表
	public DTOForEasyUIDataGrid getContentList(int pagesize,int rows,Long categoryId);

	//增添一条内容
	public TaoquResult insertContent(TbContent content);
	
	//删除若干条内容记录
	public TaoquResult deleteContents(Long[] ids)throws Exception;
	
	//编辑一条内容
	public TaoquResult updateContent(TbContent content);
	
	//根据内容分类id删除内容信息
	public TaoquResult deleteContentsByCategoryId(Long id)  throws Exception;
	
}
