/**
 * 
 */
package com.taoqu.rest.service;

import java.util.List;

import com.taoqu.pojo.TbContent;

/**
 * 2018年5月21日
 * ContentService.java
 * @author xushaoqun
 * desc:内容服务for前台
 */
public interface ContentService {
	public List<TbContent> getContentList(long categoryId);
}
