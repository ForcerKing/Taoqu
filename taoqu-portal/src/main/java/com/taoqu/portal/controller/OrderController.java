/**
 * 
 */
package com.taoqu.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taoqu.common.utils.ExceptionUtil;
import com.taoqu.pojo.TbUser;
import com.taoqu.portal.pojo.CartItem;
import com.taoqu.portal.pojo.Order;
import com.taoqu.portal.service.CartService;
import com.taoqu.portal.service.OrderService;

/**
 * 2018年6月13日
 * OrderController.java
 * @author xushaoqun
 * desc:订单控制器
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderSerivce;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		
		//取购物车商品列表
		List<CartItem> list = cartService.getCartItemList(request, response);
		//传递给页面
		model.addAttribute("cartList",list);
		return "order-cart";
	}

	
	@RequestMapping("/create")
	public String createOrder(Order order,Model model,HttpServletRequest request) {
		try {
			//从request中取用户信息
			TbUser user =(TbUser) request.getAttribute("user");
			//在order中补全用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			//调用服务
			String orderId = orderSerivce.createOrder(order);
			model.addAttribute("orderId",orderId);
			model.addAttribute("payment",order.getPayment());
			model.addAttribute("date",new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message",ExceptionUtil.getStackTrace(e));
			return "error/exception";
		}
	}
}
