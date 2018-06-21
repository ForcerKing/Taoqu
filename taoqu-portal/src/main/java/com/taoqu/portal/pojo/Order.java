/**
 * 
 */
package com.taoqu.portal.pojo;

import java.util.List;

import com.taoqu.pojo.TbOrder;
import com.taoqu.pojo.TbOrderItem;
import com.taoqu.pojo.TbOrderShipping;

/**
 * 2018年6月13日
 * Order.java
 * @author xushaoqun
 * desc:DTO,继承了TbOrder，组合了TbOrderShipping和List<TbOrderItem>
 */
public class Order  extends TbOrder{
	
	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

}
