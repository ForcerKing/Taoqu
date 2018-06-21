/**
 * 
 */
package com.taoqu.service;

import java.util.List;

import com.taoqu.common.dto.DTOForEasyUITreeNode;
import com.taoqu.common.utils.TaoquResult;

/**
 * 2018年5月17日
 * ContentCategoryService.java
 * @author xushaoqun
 * desc:内容分类服务接口
 */
public interface ContentCategoryService {

	
	public List<DTOForEasyUITreeNode> getContentCategoryList(Long parentId);
	
	TaoquResult insertContentCategory(Long parentid,String name);
	
	TaoquResult deleteContentCategory(Long parentid,Long id)throws Exception;

    TaoquResult updateContentCategory(Long id,String name);
}
