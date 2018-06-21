package com.taoqu.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2018年6月5日
 * PageController.java
 * @author xushaoqun
 * desc:页面跳转控制器
 */
@Controller
@RequestMapping("/page")
public class PageController {

	@RequestMapping("/register")
	public String showRegister() {
		return "register";
	}
	
	/*
	 * 添加回调url，很多情况下，用户并没有直接登录，而是先浏览了其他页面，然后切换到登录页面。
	 * 比如，用户在购物车中塞满了商品，然后点击结账后，这时就会弹出登录窗口，
	 * 用户在登录窗口点击登录后，需要再回到购物车窗口，这时就需要给jsp返回一个model，
	 * model中记录了需要回调的url
	 */
	@RequestMapping("/login")
	public String showLogin(String redirect,Model model) {
		model.addAttribute("redirect",redirect);
		return "login";
	}
}
