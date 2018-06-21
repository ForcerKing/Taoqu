/**
 * 
 */
package com.taoqu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taoqu.common.dto.DTOForEasyUITreeNode;
import com.taoqu.mapper.TbItemCatMapper;
import com.taoqu.pojo.TbItemCat;
import com.taoqu.pojo.TbItemCatExample;
import com.taoqu.pojo.TbItemCatExample.Criteria;
import com.taoqu.service.ItemCatService;

/**
 * 2018年4月25日
 * ItemCatServiceImpl.java
 * @author xushaoqun
 * desc:商品类目展示服务
 */

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	/*
	 * 通过父节点Id来获取所有子节点信息，用于商品类目展现
	 */
	@Override
	public List<TbItemCat> getItemCatList(Long parentId) {
		// TODO Auto-generated method stub
		
		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		Criteria criteria = tbItemCatExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> list = tbItemCatMapper.selectByExample(tbItemCatExample);
		return list;
	}
    /*
     * 获取选择类目的所有信息，并封装成List<DTOForEasyUITreeNode>返回给Controller
     */
	@Override
	public List<DTOForEasyUITreeNode> getCatList(Long parentId) {
		// TODO Auto-generated method stub
		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		Criteria criteria = tbItemCatExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<DTOForEasyUITreeNode> list = tbItemCatMapper.selectDTOByExample(tbItemCatExample);
		return list;
	}
	
}
