/**
 * 
 */
package com.taoqu.sso.pojo;

/**
 * 2018年6月1日
 * CheckType.java
 * @author xushaoqun
 * desc:校验类型枚举
 */
public enum CheckType {
	
	USERNAME(1),
	PHONE(2),
	EMAIL(3);
	
	private final Integer type;
	
	CheckType(Integer type){
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	
}
