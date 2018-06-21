/**
 * 
 */
package com.taoqu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taoqu.common.dto.DTOForEasyUITreeNode;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.mapper.TbContentCategoryMapper;
import com.taoqu.mapper.TbContentMapper;
import com.taoqu.pojo.TbContent;
import com.taoqu.pojo.TbContentCategory;
import com.taoqu.pojo.TbContentCategoryExample;
import com.taoqu.pojo.TbContentCategoryExample.Criteria;
import com.taoqu.pojo.TbContentExample;
import com.taoqu.service.ContentCategoryService;
import com.taoqu.service.DeleteImageService;

/**
 * 2018年5月17日
 * ContentCategoryServiceImpl.java
 * @author xushaoqun
 * desc:内容分类服务实现
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private DeleteImageService deleteImageService;
	/*
	 * 返回内容分类列表
	 */
	@Override
	public List<DTOForEasyUITreeNode> getContentCategoryList(Long parentId) {
		// TODO Auto-generated method stub
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<DTOForEasyUITreeNode> list = tbContentCategoryMapper.selectDTOByExample(example);
		return list;
	}

	/*
	 * 添加一个节点
	 */
	@Override
	public TaoquResult insertContentCategory(Long parentid, String name) {
		
		TbContentCategory tbContentCategory = new TbContentCategory(); 
		tbContentCategory.setParentId(parentid);
		tbContentCategory.setName(name);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setStatus(1);
		tbContentCategory.setIsParent(false);
		
		tbContentCategoryMapper.insert(tbContentCategory);
		//查看父节点的isParent是否为true，如果不是则改成true
		TbContentCategory parentContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentid);
		
		if(!parentContentCategory.getIsParent()) {
			parentContentCategory.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(parentContentCategory);
		}
		return TaoquResult.ok(tbContentCategory);
	}
	

	
	
	/*
	 * 判断该内容分类是否为父节点。如果是父节点，那么需要将该内容分类下的子节点也一并删除。
     */
	@Transactional
	private void deleteContentCategoryById(Long id) throws Exception {
		//删除该商品分类节点
		tbContentCategoryMapper.deleteByPrimaryKey(id);
		TbContentCategoryExample example = new TbContentCategoryExample(); 
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		//获取该节点下的所有商品分类子节点
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		if(list != null && list.size() > 0) {
			for(TbContentCategory contentCategory:list) {
				//删除每个商品分类子节点
				deleteContentCategoryById(contentCategory.getId());	
			}
		}
		
	}
	/*
	 * 删除一个ContentCategory,由于可能需要递归删除子节点,并引入事务
	 */
	@Transactional
	@Override
	public TaoquResult deleteContentCategory(Long parentid, Long id) throws Exception{
		//前端原型设计错了。在请求list的时候，就没有把parentId返回给前端，而在delete的url
		//中，却莫名其妙地将parentid传过来，导致parentid为空，不过为了保持兼容性，这里依然保留
		//parentId这个参数，只是多加一个判断，如果为空则自己就查询一次。
		if(parentid == null) {
			TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
			parentid=contentCategory.getParentId();
		}
		//判断该内容分类是否为父节点。如果是父节点，那么需要将该内容分类下的子节点也一并删除
		deleteContentCategoryById(id);
		
		//判断parentid对应的记录下是否有子节点，如果没有子节点，需要把parentid对应的记录的isparent设置为false
		TbContentCategoryExample example = new TbContentCategoryExample(); 
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		
		//没有子节点了,将parentid对应的节点的isParent设置为false
		if(list == null || list.size() == 0) {
			TbContentCategory parentContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentid);
		    if(parentContentCategory != null) {
		    	if(parentContentCategory.getIsParent()) {
		    		parentContentCategory.setIsParent(false);
		    		tbContentCategoryMapper.updateByPrimaryKey(parentContentCategory);
		    	}
		    }
		}
		

		return TaoquResult.ok();
	}

	/*
	 * 修改一个内容分类的名称
	 */
	@Override
	public TaoquResult updateContentCategory(Long id, String name) {
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaoquResult.ok();
	}

}
