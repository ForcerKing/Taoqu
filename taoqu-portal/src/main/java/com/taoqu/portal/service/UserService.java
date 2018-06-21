/**
 * 
 */
package com.taoqu.portal.service;

import com.taoqu.pojo.TbUser;

/**
 * 2018年6月5日
 * UserService.java
 * @author xushaoqun
 * desc:用户登录接口
 */
public interface UserService {

	public TbUser getUserByToken(String token);
}
