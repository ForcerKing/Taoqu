/**
 * 
 */
package com.taoqu.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taoqu.common.utils.CookieUtils;
import com.taoqu.pojo.TbUser;
import com.taoqu.portal.service.impl.UserServiceImpl;

/**
 * 2018年6月5日
 * LoginInterceptor.java
 * @author xushaoqun
 * desc:用户登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;
	/*
	 * 正常情况下，该部分的正确需求是 ：
	 * 在用户要结账的时候，通过这个拦截器，判断该用户是否已登录，如果没有，需要跳转到登录页面
	 * 但是现在暂时没有结账页面，我们先拦截商品详情页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//在Handler执行之前处理
		//判断用户是否登录
		//从Cookie中取token
		String token = CookieUtils.getCookieValue(request, "TAOQU_TOKEN");
		//根据token获取用于信息，调用sso系统的接口
		TbUser user = userService.getUserByToken(token);
		//取不到用户信息
		if( null == user) {
			//跳转到登录页面，把用户请求的url作为参数传输给登录页面
			response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN 
					+ "?redirect=" + request.getRequestURL());
			//返回false,即不再执行request.getRequestURL()所对应的Controller的方法。
			return false;
		}
		//取到用户信息，放行
		//将用户信息放入request中
		request.setAttribute("user", user);
		//返回值决定handler是否执行，true:执行，false:不执行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//handler执行之后，返回ModelAndView之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//handler执行并返回ModelAndView之后
		//响应用户之后

	}

}
