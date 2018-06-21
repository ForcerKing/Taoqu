package com.taoqu.service;

import java.util.List;

import com.taoqu.common.dto.DTOForEasyUITreeNode;
import com.taoqu.pojo.TbItemCat;

public interface ItemCatService {
	
	public List<TbItemCat> getItemCatList(Long parentId);
	public List<DTOForEasyUITreeNode> getCatList(Long parentId);
}
