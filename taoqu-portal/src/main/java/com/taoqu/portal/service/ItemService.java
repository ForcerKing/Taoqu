/**
 * 
 */
package com.taoqu.portal.service;

import com.taoqu.portal.pojo.ItemInfo;

/**
 * 2018年5月30日
 * ItemService.java
 * @author xushaoqun
 * desc:商品信息服务接口
 */
public interface ItemService {

	public ItemInfo getItemById(Long itemId);
}
