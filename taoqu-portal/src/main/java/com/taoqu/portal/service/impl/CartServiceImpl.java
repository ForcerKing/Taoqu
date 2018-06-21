/**
 * 
 */
package com.taoqu.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.CookieUtils;
import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbItem;
import com.taoqu.portal.pojo.CartItem;
import com.taoqu.portal.service.CartService;

/**
 * 2018年6月7日
 * CartServiceImpl.java
 * @author xushaoqun
 * desc:购物车服务实现类
 */
@Service
public class CartServiceImpl implements CartService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;


	/*
	 * 向购物车中添加商品
	 */
	@Override
	public TaoquResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		//取商品信息
		CartItem cartItem = null;
		//取购物车商品列表
		List<CartItem> itemList= getCartItemList(request);
		//判断购物车是否存在此商品
		for(CartItem cItem : itemList) {
			//如果存在此商品，增加商品数量
			if(cItem.getId() == itemId) {
				cItem.setNum(cItem.getNum() + num);
				cartItem = cItem;
				break;
			}
		}
		
		//如果不存在这个商品，添加一个
		if(cartItem == null) {
			cartItem = new CartItem();
			//根据商品Id查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
			//把json转换为java对象
			TaoquResult taoquResult = TaoquResult.formatToPojo(json, TbItem.class);
			if (taoquResult.getStatus() == 200) {
				TbItem item = (TbItem)taoquResult.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage());
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());	
			}
			
			//添加到购物车列表
			itemList.add(cartItem);
		
		}
	    //把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TAOQU_CART", JsonUtils.objectToJson(itemList),true);	
		return TaoquResult.ok();
	}
	
	/*
	 * 从Cookie中取商品列表
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request){
		//从Cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "TAOQU_CART",true);
		
		if(cartJson == null) {
			return new ArrayList();
		}
		
		//把json转换成商品列表
		try {
			List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ArrayList();
	}

	/*
	 * 供Controller调用：获取购物车列表
	 */
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItemList = this.getCartItemList(request);
		return cartItemList;
	}

	/*
	 * 修改商品数量
	 */
	@Override
	public TaoquResult updateCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		
		if(num < 0) {
			return null;
		}
		//获取购物车列表
		List<CartItem> list = this.getCartItemList(request);
		if(list != null) {
			for(CartItem cItem:list) {
				if(cItem.getId() == itemId) {
					cItem.setNum(num);//将新数量设置为新的数量
					break;
				}
			}
		}
		
		//写回Cookie
		CookieUtils.setCookie(request, response, "TAOQU_CART", JsonUtils.objectToJson(list),true);	
		return TaoquResult.ok();
	}

	/*
	 * 删除购物车商品
	 */
	@Override
	public TaoquResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {

		//从Cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		//从列表中找到此商品
		for(CartItem cItem:itemList) {
			if(cItem.getId() == itemId) {
				itemList.remove(cItem);
				break;
			}
		}
		//把购物车列表重新写入Cookie
		CookieUtils.setCookie(request, response, "TAOQU_CART", JsonUtils.objectToJson(itemList),true);
		return TaoquResult.ok();
	}

}
;