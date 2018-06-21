/**
 * 
 */
package com.taoqu.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taoqu.mapper.TbOrderItemMapper;
import com.taoqu.mapper.TbOrderMapper;
import com.taoqu.mapper.TbOrderShippingMapper;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.order.dao.JedisClient;
import com.taoqu.order.service.OrderService;
import com.taoqu.pojo.TbOrder;
import com.taoqu.pojo.TbOrderItem;
import com.taoqu.pojo.TbOrderShipping;

/**
 * 2018年6月13日
 * orderServiceImpl.java
 * @author xushaoqun
 * desc:订单服务
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;

	@Transactional
	@Override
	public TaoquResult createOrder(TbOrder order, 
			List<TbOrderItem> itemList, 
			TbOrderShipping orderShipping) throws Exception{
		//向订单表中插入记录
		//获得订单号
		String globalOrderId = jedisClient.get(ORDER_GEN_KEY);
		if (StringUtils.isBlank(globalOrderId)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}
		
		long orderId = jedisClient.incr(ORDER_GEN_KEY);
		
		//补全pojo属性
		order.setOrderId(orderId+"");
		//状态：1、未付款 2、已付款 3、未发货 4、已发货 5、交易成功 6、交易关闭
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		//0:未评价 1：已评价
		order.setBuyerRate(0);
		//向订单表插入数据
		orderMapper.insert(order);
		//插入订单明细
		for (TbOrderItem tbOrderItem : itemList) {
			//补全订单明细
			//取订单明细Id
			long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			tbOrderItem.setId(orderDetailId + "");
			tbOrderItem.setOrderId(orderId + "");  
			//向订单明细插入记录
			orderItemMapper.insert(tbOrderItem);
			
		}
		
		//插入物流表
		//补全物流表属性
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		return TaoquResult.ok(orderId);
	}

}
