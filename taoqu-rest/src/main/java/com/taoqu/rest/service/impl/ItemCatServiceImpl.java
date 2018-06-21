/**
 * 
 */
package com.taoqu.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.cache.mapper.TbItemCatMapper;
import com.taoqu.pojo.TbItemCat;
import com.taoqu.pojo.TbItemCatExample;
import com.taoqu.pojo.TbItemCatExample.Criteria;
import com.taoqu.rest.dao.JedisClient;
import com.taoqu.rest.pojo.CatNode;
import com.taoqu.rest.pojo.CatResult;
import com.taoqu.rest.service.ItemCatService;

/**
 * 2018年5月15日
 * ItemCatServiceImpl.java
 * @author xushaoqun
 * desc:商品分类列表服务的实现
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CAT_REDIS_HKEY}")
	private String INDEX_CAT_REDIS_HKEY;
	
	@Override
	public CatResult getItemCatList() {
		// TODO Auto-generated method stub
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));
		return catResult;
	}
	
	/*
	 * 查询分类列表的方法.由于无法确定json中的嵌套有几层。这里用递归比较好
	 */
	private List<?> getCatList(long parentId){
		
		//先从缓存中取
		List resultList = (List) jedisClient.getObject(INDEX_CAT_REDIS_HKEY, parentId + "");
        if(resultList != null && resultList.size() > 0) {
            return resultList;
        }
		
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		List data = new ArrayList<>();
		
		
		int count = 0;
		//向data中添加节点
		for(TbItemCat tbItemCat:list) {
			//判断是否为父节点，如果是，那么向i中添加List<CatNode>,否则添加String
			if(tbItemCat.getIsParent()) {
				if(count < 14) {//版面左侧列表最多只能容纳14CatNode，最多只展示14个
					CatNode catNode = new CatNode();
					if(parentId == 0) { //如果是第一层节点，才会往n标签里面添加"<a href=..."，否则不添加
						catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
					}else {
						catNode.setName(tbItemCat.getName());
					}
				    catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				    catNode.setItem(getCatList(tbItemCat.getId()));	    
				    data.add(catNode);			    
				    count++;
				}
			}else {
				data.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
			}
		}
		//将data通过序列化转化为字符串，存放在redis中
		jedisClient.setObject(INDEX_CAT_REDIS_HKEY, parentId + "", data);
		return data;
	}

}
