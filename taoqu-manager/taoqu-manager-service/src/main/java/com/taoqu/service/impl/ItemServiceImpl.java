package com.taoqu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoqu.common.dto.DTOForEasyUIDataGrid;
import com.taoqu.common.utils.IDUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.mapper.TbItemDescMapper;
import com.taoqu.mapper.TbItemMapper;
import com.taoqu.mapper.TbItemParamItemMapper;
import com.taoqu.pojo.TbItem;
import com.taoqu.pojo.TbItemDesc;
import com.taoqu.pojo.TbItemExample;
import com.taoqu.pojo.TbItemExample.Criteria;
import com.taoqu.pojo.TbItemParamItem;
import com.taoqu.pojo.TbItemParamItemExample;
import com.taoqu.service.DeleteImageService;
import com.taoqu.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Autowired
	private DeleteImageService deleteImageService;
	
	@Override
	public TbItem getItemById(long itemId) {
		// TODO Auto-generated method stub
		//TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		
		TbItemExample example= new TbItemExample();
		//MyBatis模仿Hibernate的QBC风格，实现了自创查询条件来满足复杂查询的机制
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		if( list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

    /**
     * 商品列表查询
     */
	@Override
	public DTOForEasyUIDataGrid getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		//设置分页
		PageHelper.startPage(page, rows);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//创建返回值对象
		DTOForEasyUIDataGrid dtoForEasyUIDataGrid = new DTOForEasyUIDataGrid();
		dtoForEasyUIDataGrid.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		dtoForEasyUIDataGrid.setTotal(pageInfo.getTotal());
		return dtoForEasyUIDataGrid;
	}

	/*
	 * 添加一个商品信息
	 */
	private int insertItem(TbItem item,Long itemId) {
		item.setId(itemId);
		item.setStatus((byte)1);//商品状态:1 正常
		item.setCreated(new Date());
		item.setUpdated(new Date());
		return tbItemMapper.insert(item);
	}
	
	/*
	 * 创建一个sku的商品明细、描述、规格明细，添加到三个表中，只有当三个表都插入成功才算成功
	 * 因此这里要引入事务
	 */
	@Transactional
	@Override
	public TaoquResult createItem(TbItem item,String desc,String itemParams) throws Exception {
		int result = 0;
		TaoquResult failure = null;
		try {
			Long itemId = IDUtils.genItemId();
			result = insertItem(item,itemId) + insertItemDesc(itemId, desc) + insertItemParamItem(itemId,itemParams);
		} catch (Exception e) {
            throw new Exception("添加表发生异常！");
		}
		if(result == 3) {//三个表都成功插入，才算成功插入
			return TaoquResult.ok();
		}else {
            throw new Exception("添加表发生异常！");
		}
	}
	
	/*
	 * 添加商品描述信息
	 */
	private int insertItemDesc(Long itemId,String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		return tbItemDescMapper.insert(itemDesc);
	}
	
	/*
	 * 添加商品规格明细
	 */
	private int insertItemParamItem(Long itemId, String itemParams) {
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setItemId(itemId);
		tbItemParamItem.setCreated(new Date());
		tbItemParamItem.setUpdated(new Date());
		tbItemParamItem.setParamData(itemParams);
		int result = tbItemParamItemMapper.insert(tbItemParamItem);
		return result;
	}

    /*
     * 修改一个sku的商品明细、描述、规格明细，要修改三个表,因为全修改了才算修改成功
     * 因此这里要引入Spring中的事务
     */
	@Transactional
	@Override
	public TaoquResult updateItem(TbItem item, String desc, String itemParams, Long itemParamId) throws Exception {
		// TODO Auto-generated method stub
        int result = 0;
		try {
			result = updateItem(item) + updateItemDesc(item.getId(),desc) + updateItemParamItem(itemParamId,itemParams);
		} catch (Exception e) {
			throw new Exception("表更新异常！");
		}
		
		if(result == 3) {//三个更新都成功了
			return TaoquResult.ok();
		}else {
			throw new Exception("表更新异常！");
		}
	}
	/*
	 * 添加商品信息明细
	 */
	private int updateItem(TbItem item) {
		item.setUpdated(new Date());
		int result = tbItemMapper.updateByPrimaryKeySelective(item);
		return result;
	}
	/*
	 * 更新商品描述
	 */
	@SuppressWarnings("unused")
	private int updateItemDesc(Long itemId,String desc) {
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		if(itemDesc ==null) {
			return 0;
		}
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		return tbItemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}
	
	/*
	 * 更新商品规格明细
	 */
    @SuppressWarnings("unused")
	private int updateItemParamItem(Long itemParamId,String itemParams) {
    	TbItemParamItem tbItemParamItem = tbItemParamItemMapper.selectByPrimaryKey(itemParamId);
        if(tbItemParamItem == null) {
        	return 0;
        }
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(new Date());
        return tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
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
     * 删除一个sku的商品信息明细、描述和规格参数明细，只有三个表全删除了才算成功
     * 因此要引入Spring中的事务,这里有几个细节需要注意:
     * 1.方法定义了抛出的是throws Exception即非运行时异常。而Spring事务在默认情况下
     * 是只针对unchecked，即运行时异常(RuntimeException和error)才执行回滚操作的。
     * 如果我们要实现“当程序抛出Exception，方法回滚”，那么就必须要在applicationContext-trans.xml
     * 中在切点方法后配置一个选项：rollback-for="Exception"
     * 2.rollback-for选项也可以在本方法中的@Transactional注解中设置，语法为:
     * @Transactional(rollbackFor="Exception.class")
     * 不过，经测试，我发现如果仅在注解中设置这个选项，而applicationContext-trans.xml中没有配置这个选项
     * 那么这个选项还是不生效的，所以必须要在applicationContext.trans.xml中设置！！！
     * 3.如果设置了rollback-for="Exception"，那么在方法中使用try/catch包裹一个程序块的时候，catch()中的
     * 内容就不能是Exception e了。因为只要当把Exception throw出去，Spring事务管理器才会捕获到该异常，从而回滚
     * 如果被catch了，那么这个异常就不会抛给Spring的事务管理器，那么Spring事务管理器还是不会执行回滚操作
     * 
     */
    @Transactional
	@Override
	public TaoquResult deleteItem(Long[] ids) throws Exception{
		// TODO Auto-generated method stub
		int result;
		if(ids == null || ids.length == 0) {
			return TaoquResult.build(202, "删除列表为空！");
		}
		try {
			result = 0;
			for(Long id:ids) {
				TbItem item = tbItemMapper.selectByPrimaryKey(id);
				String imageURL = item.getImage();
				
				result += tbItemMapper.deleteByPrimaryKey(id);
				result += tbItemDescMapper.deleteByPrimaryKey(id);
				TbItemParamItemExample example = new TbItemParamItemExample();
				TbItemParamItemExample.Criteria criteria = example.createCriteria();
				criteria.andItemIdEqualTo(id);
				result += tbItemParamItemMapper.deleteByExample(example);
				//删除服务器上对应的图片
				String[] images = getImages(imageURL);
				for(String image:images) {
					deleteImageService.deleteImage(image);			
				}
			}
		} catch (Exception e) {
			throw new Exception("表删除出现错误！");		
		}
		if(result == ids.length * 3) {
			return TaoquResult.ok();
		}else {
			throw new Exception("表删除出现错误！");
		}
	}
}
