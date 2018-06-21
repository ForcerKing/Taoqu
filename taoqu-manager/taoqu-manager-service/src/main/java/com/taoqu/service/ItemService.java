package com.taoqu.service;

import com.taoqu.common.dto.DTOForEasyUIDataGrid;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(long itemId);
	DTOForEasyUIDataGrid getItemList(int page,int rows); 
	//添加商品明细、描述、规格明细
	TaoquResult createItem(TbItem item,String desc,String itemParams) throws Exception;
    
	//修改商品明细、描述，规格明细
    TaoquResult updateItem(TbItem item,String desc,String itemParams,Long itemParamId) throws Exception;
    //删除商品明细、描述、规格明细
    TaoquResult deleteItem(Long[] ids) throws Exception;
}
