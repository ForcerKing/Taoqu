/**
 * 
 */
package com.taoqu.rest.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 2018年5月15日
 * CatNode.java
 * @author xushaoqun
 * desc:商品分类返回列表的pojo
 */
public class CatNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("n") //表示name在转换成json后，对应的key的名称是n
	private String name;
	@JsonProperty("u")
	private String url;
	@JsonProperty("i")
	private List<?> item;//list的类型可能是CatNode，可能是String，所以这里是？
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the item
	 */
	public List<?> getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(List<?> item) {
		this.item = item;
	}


}
