/**
 * 
 */
package com.taoqu.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.portal.pojo.CartItem;

/**
 * 2018年6月7日
 * CartService.java
 * @author xushaoqun
 * desc:购物车服务接口
 */
public interface CartService {

	//得取Cookie中的值，所以得用到Servlet对象
	public TaoquResult addCartItem(long itemId,int num,HttpServletRequest request,HttpServletResponse response);

	//从Cookie中获取购物车列表
	public List<CartItem> getCartItemList(HttpServletRequest request,
			HttpServletResponse response);
	
	//直接修改指定商品的数量
	public TaoquResult updateCartItem(long itemId,int num,HttpServletRequest request,HttpServletResponse response);

	//删除购物车商品
	public TaoquResult deleteCartItem(long itemId,HttpServletRequest request,HttpServletResponse response);

}
