/**
 * 
 */
package com.taoqu.common.dto;

import java.util.List;

/**
 * 2018年4月23日
 * DTOForEasyUIDataGrid.java
 * @author xushaoqun
 * desc:以EasyUI的DataGrid的[total:[rows:]]格式，保存查询结果
 */
public class DTOForEasyUIDataGrid {
   
	private long total;
	private List<?> rows;
	
	
	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	/**
	 * @return the rows
	 */
	public List<?> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
