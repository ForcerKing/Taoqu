/**
 * 
 */
package com.taoqu.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.ExceptionUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.order.pojo.Order;
import com.taoqu.order.service.OrderService;

/**
 * 2018年6月13日
 * OrderController.java
 * @author xushaoqun
 * desc:订单控制器
 * 
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	/*
	 * 接收一个json字符串，需要使用requestBody注解。原理和ResponseBody一样
	 * 借助MessageConverter将json字符串转换成Order对象
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TaoquResult createOrder(@RequestBody Order order) {
		try {
			TaoquResult result = orderService.createOrder(order, order.getOrderItems(), 
					order.getOrderShipping());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaoquResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
