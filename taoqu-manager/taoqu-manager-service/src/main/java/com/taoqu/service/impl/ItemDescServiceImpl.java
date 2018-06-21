/**
 * 
 */
package com.taoqu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.mapper.TbItemDescMapper;
import com.taoqu.pojo.TbItemDesc;
import com.taoqu.pojo.TbItemDescExample;
import com.taoqu.service.ItemDescService;

/**
 * 2018年5月9日
 * ItemDescServiceImpl.java
 * @author xushaoqun
 * desc:商品描述实现
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	/*
	 * 根据商品id返回这个商品的描述
	 */
	@Override
	public TaoquResult getItemDescByItemId(Long ItemId) {
				
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(ItemId);
		if(tbItemDesc != null) {
			return TaoquResult.ok(tbItemDesc);
		}	
		return null;
	}

}
