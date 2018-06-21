/**
 * 
 */
package com.taoqu.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.portal.pojo.Order;
import com.taoqu.portal.service.OrderService;

/**
 * 2018年6月13日
 * OrderServiceImpl.java
 * @author xushaoqun
 * desc:订单服务
 */
@Service
public class OrderServiceImpl implements OrderService {

	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	


	@Override
	public String createOrder(Order order) {
		
		//调用taoqu-order的服务提交订单
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//把json转换成taoquResult
		TaoquResult taoquResult = TaoquResult.format(json);
		if(taoquResult.getStatus() == 200) {
			Object orderId = taoquResult.getData();
			return orderId.toString();
		}
		return null;
	}

}
