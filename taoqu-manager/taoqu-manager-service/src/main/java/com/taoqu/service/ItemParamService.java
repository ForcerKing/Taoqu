/**
 * 
 */
package com.taoqu.service;

import java.util.List;

import com.taoqu.common.dto.DTOForEasyUIDataGrid;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbItemParam;

/**
 * 2018年5月7日
 * ItemParamService.java
 * @author xushaoqun
 * desc:商品规格参数
 */
public interface ItemParamService {
	
	//返回所有商品规格参数
	DTOForEasyUIDataGrid getItemParamAndItemCatNameList(int page,int rows);

	TaoquResult getItemParamByCid(long cid);
	
	TaoquResult insertItemParam(TbItemParam itemParam);
	
	TaoquResult deleteItemParam(Long[] params);
}
