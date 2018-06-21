/**
 * 
 */
package com.taoqu.common.dto;

import java.util.Date;

/**
 * 2018年5月7日
 * DTOForItemParamAndItemCatName.java
 * @author xushaoqun
 * desc:用于传输一个ItemParam+一个itemCatName
 */
public class DTOForItemParamAndItemCatName {

	private Long id;

	private Long itemCatId;

	private String itemCatName;

	private Date created;

	private Date updated;

	private String paramData;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the itemCatId
	 */
	public Long getItemCatId() {
		return itemCatId;
	}

	/**
	 * @param itemCatId the itemCatId to set
	 */
	public void setItemCatId(Long itemCatId) {
		this.itemCatId = itemCatId;
	}

	/**
	 * @return the itemCatName
	 */
	public String getItemCatName() {
		return itemCatName;
	}

	/**
	 * @param itemCatName the itemCatName to set
	 */
	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the paramData
	 */
	public String getParamData() {
		return paramData;
	}

	/**
	 * @param paramData the paramData to set
	 */
	public void setParamData(String paramData) {
		this.paramData = paramData;
	}



}
