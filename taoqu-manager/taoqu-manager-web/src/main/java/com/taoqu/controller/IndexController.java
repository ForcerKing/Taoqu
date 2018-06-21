/**
 * 
 */
package com.taoqu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2018年4月23日
 * IndexController.java
 * @author xushaoqun
 * desc:实现首页跳转
 */
@Controller
public class IndexController {

	/**
	 * 返回首页
	 * 2018年4月23日
	 * String
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	/**
	 * 在index页面点击左侧按钮，显示出来的页面，如item-add.jsp等等
	 * 2018年4月23日
	 * String
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showOtherPages(@PathVariable String page) {
		return page;
	}
}
