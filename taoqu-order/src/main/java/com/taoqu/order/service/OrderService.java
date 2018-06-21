/**
 * 
 */
package com.taoqu.order.service;

import java.util.List;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbOrder;
import com.taoqu.pojo.TbOrderItem;
import com.taoqu.pojo.TbOrderShipping;

/**
 * 2018年6月13日
 * orderService.java
 * @author xushaoqun
 * desc:订单服务接口
 */
public interface OrderService {
	
	public TaoquResult createOrder (TbOrder order,
			List<TbOrderItem> itemList,
			TbOrderShipping orderShipping)throws Exception;

}
