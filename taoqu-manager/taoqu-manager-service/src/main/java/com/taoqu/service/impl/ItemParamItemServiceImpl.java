/**
 * 
 */
package com.taoqu.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.mapper.TbItemParamItemMapper;
import com.taoqu.pojo.TbItemParamItem;
import com.taoqu.pojo.TbItemParamItemExample;
import com.taoqu.pojo.TbItemParamItemExample.Criteria;
import com.taoqu.service.ItemParamItemService;

/**
 * 2018年5月9日
 * ItemParamItemServiceImpl.java
 * @author xushaoqun
 * desc:商品规格明细服务实现
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	/*
	 * 增添一条商品规格明细
	 */
	@Override
	public TaoquResult insertItemParamItem(Long itemId, String itemParams) {
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setId(itemId);
		tbItemParamItem.setCreated(new Date());
		tbItemParamItem.setUpdated(new Date());
		tbItemParamItem.setParamData(itemParams);
		int result = tbItemParamItemMapper.insert(tbItemParamItem);
		TaoquResult failure = TaoquResult.build(500, "添加失败！");
		return result == 1?TaoquResult.ok():failure;
	}

	@Override
	public String getItemParamItemByItemId(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null || list.size() == 0) {
			return "";
		}
		//取规格参数信息
		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		//生成html
		// 把规格参数json数据转换成java对象
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for(Map m1:jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for(Map m2:list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
				sb.append("            <td>"+m2.get("v")+"</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

	@Override
	public TaoquResult getItemParamItemByItemId2(Long itemId) {

		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);

		if(list != null && list.size() > 0) {
			return TaoquResult.ok(list.get(0));
		}
		
		return null;
	}

}
