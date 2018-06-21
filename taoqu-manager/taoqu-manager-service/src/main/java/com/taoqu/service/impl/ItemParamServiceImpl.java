/**
 * 
 */
package com.taoqu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoqu.common.dto.DTOForEasyUIDataGrid;
import com.taoqu.common.dto.DTOForItemParamAndItemCatName;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.mapper.TbItemParamMapper;
import com.taoqu.pojo.TbItem;
import com.taoqu.pojo.TbItemParam;
import com.taoqu.pojo.TbItemParamExample;
import com.taoqu.pojo.TbItemParamExample.Criteria;
import com.taoqu.service.ItemParamService;

/**
 * 2018年5月7日
 * ItemParamServiceImpl.java
 * @author xushaoqun
 * desc:商品规格参数服务的实现类
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
	
	@Autowired
	private TbItemParamMapper tbItemParamMapper;

	@Override
	public DTOForEasyUIDataGrid getItemParamAndItemCatNameList(int page, int rows) {
		// TODO Auto-generated method stub
		TbItemParamExample tbItemParamExample = new TbItemParamExample();
		PageHelper.startPage(page, rows);
		List<DTOForItemParamAndItemCatName> list = tbItemParamMapper.selectItemParamsAndItemCatName(tbItemParamExample);

		//创建返回值对象
		DTOForEasyUIDataGrid dtoForEasyUIDataGrid = new DTOForEasyUIDataGrid();
		dtoForEasyUIDataGrid.setRows(list);
		//取记录总条数
		PageInfo<DTOForItemParamAndItemCatName> pageInfo = new PageInfo<>(list);
		dtoForEasyUIDataGrid.setTotal(pageInfo.getTotal());
		return dtoForEasyUIDataGrid;
	}

	/*
	 * 根据itemCatId取得商品规格参数模板
	 */
	@Override
	public TaoquResult getItemParamByCid(long cid) {
		// TODO Auto-generated method stub
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		//List<TbItemParam> list = tbItemParamMapper.selectByExample(example);注意，这个方法和
		//BLOBs相比，并没有将text类型的field取出来，因此paramData为空，所以要使用下面这个方法
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);

		if(list != null && list.size() > 0) {
			return TaoquResult.ok(list.get(0));
		}
		return TaoquResult.ok();
	}
	
	
    /*
     * 增添一条规格参数
     */
	@Override
	public TaoquResult insertItemParam(TbItemParam itemParam) {
		// TODO Auto-generated method stub
		
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		tbItemParamMapper.insert(itemParam);
		return TaoquResult.ok();
	}

	/*
	 * 删除规格参数
	 */
	@Override
	public TaoquResult deleteItemParam(Long[] params) {
		// TODO Auto-generated method stub
		int FinalResult = 0;
		if(params != null && params.length > 0) {
			for(int i = 0;i < params.length ;i++) {
				//删除成功，singleDelResult的值为1，否则为0
				int singleDelResult = tbItemParamMapper.deleteByPrimaryKey(params[i]);
				FinalResult += singleDelResult;
			}
		}
		TaoquResult failure = TaoquResult.build(500, "删除失败！");
		return FinalResult == params.length?TaoquResult.ok():failure;
	}
	
	

}
