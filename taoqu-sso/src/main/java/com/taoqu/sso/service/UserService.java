/**
 * 
 */
package com.taoqu.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbUser;

/**
 * 2018年6月1日
 * UserService.java
 * @author xushaoqun
 * desc:用户服务接口
 */
public interface UserService {
	//数据校验方法
	public TaoquResult checkData(String content,Integer type);

	//用户注册
	public TaoquResult createUser(TbUser user); 
	
	//用户登录
	public TaoquResult userLogin(String username, String password,
			HttpServletRequest request,HttpServletResponse response);
	
	//根据token获取用户信息
	public TaoquResult getUserByToken(String token);
	
	//用户退出
	public TaoquResult userLogout(String token);
}
