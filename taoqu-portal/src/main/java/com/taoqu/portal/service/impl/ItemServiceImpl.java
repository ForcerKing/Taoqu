/**
 * 
 */
package com.taoqu.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.portal.pojo.ItemInfo;
import com.taoqu.portal.service.ItemService;

/**
 * 2018年5月30日
 * ItemServiceImpl.java
 * @author xushaoqun
 * desc:
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	@Override
	public ItemInfo getItemById(Long itemId) {
        //调用rest的服务查询商品基本信息
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
			if(!StringUtils.isBlank(json)) {
				TaoquResult taoquResult = TaoquResult.formatToPojo(json, ItemInfo.class);
				if(taoquResult.getStatus() == 200) {
					ItemInfo item = (ItemInfo) taoquResult.getData();
					return item;
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
