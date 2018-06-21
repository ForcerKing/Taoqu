/**
 * 
 */
package com.taoqu.portal.pojo;

/**
 * 2018年5月29日
 * Item.java
 * @author xushaoqun
 * desc:solr所需要的数据库字段映射模型
 */
public class Item {

	private String id;
	private String title;
	private String sell_point;
	private long price;
	private String image;
	private String category_name;
	private String item_des;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the sell_point
	 */
	public String getSell_point() {
		return sell_point;
	}
	/**
	 * @param sell_point the sell_point to set
	 */
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}
	/**
	 * @return the price
	 */
	public long getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(long price) {
		this.price = price;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the category_name
	 */
	public String getCategory_name() {
		return category_name;
	}
	/**
	 * @param category_name the category_name to set
	 */
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	/**
	 * @return the item_des
	 */
	public String getItem_des() {
		return item_des;
	}
	/**
	 * @param item_des the item_des to set
	 */
	public void setItem_des(String item_des) {
		this.item_des = item_des;
	}
	
	public String[] getImages() {
		if(image != null) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
	
}
