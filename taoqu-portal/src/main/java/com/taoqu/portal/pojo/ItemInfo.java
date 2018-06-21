/**
 * 
 */
package com.taoqu.portal.pojo;

import com.taoqu.pojo.TbItem;

/**
 * 2018年5月31日
 * ItemInfo.java
 * @author xushaoqun
 * desc:添加了getImages方法
 */
public class ItemInfo extends TbItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String[] getImages() {
		String image = getImage();
		if(image != null) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}
