
package com.taoqu.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.portal.pojo.CartItem;
import com.taoqu.portal.service.CartService;

/**
 * 2018年6月7日
 * CartController.java
 * @author xushaoqun
 * desc:购物车控制器
 */
@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	/*
	 * 重定向到cartSuccess页面，这样url就不会是localhost:8082/cart/add/itemid.html
	 * 而是停留在localhost:8082/cart/success.html
	 */
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId,
			@RequestParam(defaultValue="1") Integer num,
			HttpServletRequest request,
			HttpServletResponse response) {
				
		TaoquResult result = cartService.addCartItem(itemId, num, request, response);
		
		return "redirect:/cart/success.html";
		
	}
	
	@RequestMapping("/success")
	public String showSuccess() {
		return "cartSuccess";
	}
	
	/*
	 * 修改商品数量，浏览器不会对响应内容做任何处理，只会取response中的Cookie中的值
	 * 
	 * 这里加@ResponseBody的理由是：
	 * 1.如果不加，return后的字符串必然会被视图处理器解析成返回url的一部分，从而返回给前端一个jsp页面。
	 * 而访问这个方法的请求是个AJAX请求，并不要求跳转页面，因此需要加@ResponseBody注解。
	 * 2.但是这个方法的返回值还不能是TaoquResult，因为我们已经在web中定义了拦截的url格式的后缀名
	 * 是.html，而SpringMVC规定：以后缀名为.html的url所请求的响应数据的格式必须也是.html的
	 * ，如果返回值是TaoquResult,@ResponseBody会调用MappingJackson2HttpMessageConverter
	 * 把TaoquResult转换成json发给前端，这样就会报406 not accept错误。
	 * 
	 * 因此我们只需要返回一个无关紧要的字符串就可以了。所以，这个方法的返回值的类型被确定为String。
	 * @ResponseBody会使用StringHttpMessageConverter对返回值进行处理，这个处理过程不会
	 * 将字符串转化为Json数据。
	 */
	@RequestMapping(value="/update/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String updateCartItem(@PathVariable Long itemId,
			@RequestParam(defaultValue="1") Integer num,
			HttpServletRequest request,
			HttpServletResponse response) {
	    TaoquResult result = cartService.updateCartItem(itemId, num, request, response);
		return "这里写什么都无所谓，因为只是作为响应体返回给页面，而不经过视图处理器,而客户端拿了这个值又不做任何处理";
	}
	
	/*
	 * 返回购物车列表
	 */
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList",list);
		return "cart";
	}
	/*
	 * 删除商品
	 */
	@RequestMapping(value="/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
			HttpServletRequest request,
			HttpServletResponse response) {
	    TaoquResult result = cartService.deleteCartItem(itemId, request, response);
	    return "redirect:/cart/cart.html";
	}
	
	
}
