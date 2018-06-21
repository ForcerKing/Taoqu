/**
 * 
 */
package com.taoqu.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbItemParamItem;
import com.taoqu.portal.service.ItemParamItemService;

/**
 * 2018年5月31日
 * ItemParamItemServiceImpl.java
 * @author xushaoqun
 * desc:商品规格参数明细实现类
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;

	@Override
	public String getItemParam(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			//把json转换成java对象
			TaoquResult taoquResult = TaoquResult.formatToPojo(json, TbItemParamItem.class);
			if (taoquResult.getStatus() == 200) {
				TbItemParamItem itemParamItem = (TbItemParamItem) taoquResult.getData();
				String paramData = itemParamItem.getParamData();
				//生成html
				// 把规格参数json数据转换成java对象
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
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
				//返回html片段
				return sb.toString();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}



}
