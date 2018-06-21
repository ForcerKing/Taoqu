/**
 * 
 */
package com.taoqu.common.dto;

/**
 * 2018年4月28日
 * DTOForEasyUITreeNode.java
 * @author xushaoqun
 * desc：当点击商品类目时，前端需要获取一个json格式的结果集，格式如下
 * {
 *   id:商品分类id
 *   text:商品描述
 *   state:是否是叶节点，如果是，那么为open,不是则说明为父节点，默认打开状态为closed
 * }
 * 这个DTO用来保存从Mapper中获取的数据，利用springmvc中的ResponseBody,供前端解析成json.
 * 
 */
public class DTOForEasyUITreeNode {
    
	private String id;
	private String text;
	private String state;
	/**
	 * @return the id
	 */
	
	
	
	public String getId() {
		return id;
	}
	/**
	 * 
	 */
	public DTOForEasyUITreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param text
	 * @param state
	 * desc:Mapper.xml中的resultMap会根据这个有参构造器，将结果集映射成DTO对象
	 * 而在这个有参构造器里，会将实参进行处理
	 */
	public DTOForEasyUITreeNode(String id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = ((state.equals(String.valueOf('1')))?"closed":"open");
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = ((state.equals(String.valueOf('1')))?"closed":"open");
	}
	
	
}
