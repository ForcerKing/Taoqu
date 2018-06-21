/**
 * 
 */
package com.taoqu.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.HttpClientUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbUser;
import com.taoqu.portal.service.UserService;

/**
 * 2018年6月5日
 * UserServiceImpl.java
 * @author xushaoqun
 * desc:用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;	
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;
	/*
	 * 根据token获取用户信息
	 */
	@Override
	public TbUser getUserByToken(String token) {
		try {
			String json = HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN+token);
			//把json转化为TaoquResult
			TaoquResult result = TaoquResult.formatToPojo(json, TbUser.class);
			if(result.getStatus() == 200) {
				TbUser user = (TbUser)result.getData();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
