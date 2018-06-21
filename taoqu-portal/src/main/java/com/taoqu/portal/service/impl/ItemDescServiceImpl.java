/**
 * 
 */
package com.taoqu.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbItemDesc;
import com.taoqu.portal.service.ItemDescService;

/**
 * 2018年5月31日
 * ItemDescServiceImpl.java
 * @author xushaoqun
 * desc:商品描述实现类
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	
	@Override
	public String getItemDescById(Long itemId) {
		try {
			//查询商品描述
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			//转换成java对象
			TaoquResult taoquResult = TaoquResult.formatToPojo(json, TbItemDesc.class);
			if (taoquResult.getStatus() == 200) {
				TbItemDesc itemDesc = (TbItemDesc) taoquResult.getData();
				//取商品描述信息
				String result = itemDesc.getItemDesc();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
