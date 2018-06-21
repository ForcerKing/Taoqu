/**
 * 
 */
package com.taoqu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoqu.common.dto.DTOForEasyUIDataGrid;
import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.mapper.TbContentCategoryMapper;
import com.taoqu.mapper.TbContentMapper;
import com.taoqu.pojo.TbContent;
import com.taoqu.pojo.TbContentCategory;
import com.taoqu.pojo.TbContentCategoryExample;
import com.taoqu.pojo.TbContentExample;
import com.taoqu.pojo.TbContentExample.Criteria;
import com.taoqu.service.ContentService;
import com.taoqu.service.DeleteImageService;

/**
 * 2018年5月18日
 * ContentServiceImpl.java
 * @author xushaoqun
 * desc:内容管理服务
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Autowired
	private DeleteImageService deleteImageService;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_REDIS_SYNC}")
	private String REST_REDIS_SYNC;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	/*
	 * 返回内容列表，结果以DTO形式返回
	 */
	@Override
	public DTOForEasyUIDataGrid getContentList(int pagesize, int rows, Long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//设置分页
		PageHelper.startPage(pagesize, rows);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		//设置DTO
		DTOForEasyUIDataGrid dtoForEasyUIDataGrid = new DTOForEasyUIDataGrid();
		dtoForEasyUIDataGrid.setRows(list);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		dtoForEasyUIDataGrid.setTotal(pageInfo.getTotal());
		return dtoForEasyUIDataGrid;
	}

	/*
	 * 增加一条内容记录
	 */
	@Override
	public TaoquResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_REDIS_SYNC+INDEX_CONTENT_REDIS_KEY+"/"+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaoquResult.ok();
	}
	
	/*
	 * 将一个保存了多个url的字符串截取，获得一个字符串数组
	 */
	public String[] getImages(String imageStr) {
		if(imageStr != null) {
			String[] images = imageStr.split(",");
			return images;
		}
		return null;
	}

	/*
	 * 删除若干记录
	 */
	@Transactional
	@Override
	public TaoquResult deleteContents(Long[] ids)  throws Exception {
		if(ids == null || ids.length == 0) return TaoquResult.build(500, "请选择要删除的条目");
		int count = 1;//缓存同步计数器，只执行一次
		for(Long id:ids) {
			TbContent content = contentMapper.selectByPrimaryKey(id);
			
            //删除图片
			String[] images = getImages(content.getPic());
			for(String image:images) {
				deleteImageService.deleteImage(image);			
			}
			String[] images2 = getImages(content.getPic2());
			for(String image:images) {
				deleteImageService.deleteImage(image);			
			}
            //删除记录
			contentMapper.deleteByPrimaryKey(id);
			//清空redis缓存，且只执行一次
			if(count == 1) {
				try {
					HttpClientUtil.doGet(REST_BASE_URL+REST_REDIS_SYNC+INDEX_CONTENT_REDIS_KEY+"/"+content.getCategoryId());
				} catch (Exception e) {
					e.printStackTrace();
					count--;
				}
			}
			count++;
		}
		return TaoquResult.ok();
	}
	
	/*
	 * 根据ContentCategoryId来删除内容，遵循以下逻辑
	 * 1.当前节点是父节点，继续寻找它的子节点，直到当前节点是叶节点
	 * 2.如果当前节点是叶节点，删除当前节点所对应的content。同时删除相应的图片
	 */
	@Transactional
	@Override
	public TaoquResult deleteContentsByCategoryId(Long id)  throws Exception {
		TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		if(tbContentCategory.getIsParent()) {
			//设置查询条件
			TbContentCategoryExample contentCategoryExample = new TbContentCategoryExample();
			TbContentCategoryExample.Criteria contentCriteria = contentCategoryExample.createCriteria();
			contentCriteria.andParentIdEqualTo(id);
			//获取当前节点的所有子节点
			List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(contentCategoryExample);
	        for(TbContentCategory contentCategory:list) {
	        	//重复这个过程
	        	deleteContentsByCategoryId(contentCategory.getId());
	        }

		}else {
			//找到叶节点，设置查询条件
			TbContentExample contentExample = new TbContentExample();
			TbContentExample.Criteria contentCriteria = contentExample.createCriteria();
			contentCriteria.andCategoryIdEqualTo(id);
			//获取该叶节点对应的所有content
			List<TbContent> contentList = contentMapper.selectByExample(contentExample);
			if(contentList != null && contentList.size() > 0) {
				for(TbContent content:contentList) {
					//删除图片
					String[] images = getImages(content.getPic());
					for(String image:images) {
						deleteImageService.deleteImage(image);			
					}
					String[] images2 = getImages(content.getPic2());
					for(String image:images) {
						deleteImageService.deleteImage(image);			
					}				
					//删除记录
					contentMapper.deleteByPrimaryKey(content.getId());
				}
			}
		}
		//TODO:这里要清空一次缓存，即远程调用sync方法，注意要保证只调用一次
		return TaoquResult.ok();
	}

	/*
	 * 更新一条记录
	 */
	@Override
	public TaoquResult updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(content);
		//清空redis缓存，即远程调用sync方法
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_REDIS_SYNC+INDEX_CONTENT_REDIS_KEY+"/"+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaoquResult.ok();
	}

}
