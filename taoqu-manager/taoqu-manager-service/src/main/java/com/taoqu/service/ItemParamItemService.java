/**
 * 
 */
package com.taoqu.service;

import com.taoqu.common.utils.TaoquResult;

/**
 * 2018年5月9日
 * ItemParamItemService.java
 * @author xushaoqun
 * desc:商品规格明细服务
 */
public interface ItemParamItemService {
	
	public TaoquResult insertItemParamItem(Long itemId,String itemParams);

	
	public String getItemParamItemByItemId(Long itemId);
	
	public TaoquResult getItemParamItemByItemId2(Long itemId);
}
