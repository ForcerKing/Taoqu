/**
 * 
 */
package com.taoqu.rest.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 2018年5月15日
 * CatResult.java
 * @author xushaoqun
 * desc:json数据格式中的data
 */
public class CatResult  {

	private List<?> data;

	/**
	 * @return the data
	 */
	public List<?> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<?> data) {
		this.data = data;
	}



}
