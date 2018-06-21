/**
 * 
 */
package com.taoqu.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taoqu.common.utils.ExceptionUtil;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbUser;
import com.taoqu.sso.pojo.CheckType;
import com.taoqu.sso.service.UserService;

/**
 * 2018年6月1日
 * UserController.java
 * @author xushaoqun
 * desc:用户服务控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;


	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback){

		TaoquResult result = null;

		if(type != CheckType.USERNAME.getType() && type!=CheckType.PHONE.getType()&&type != CheckType.EMAIL.getType()) {
			result = TaoquResult.build(400, "校验类型错误");
		}
		
		//校验出错
		if(result != null) {
			if(null != callback) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			    mappingJacksonValue.setJsonpFunction(callback);
			    return mappingJacksonValue;
			    //返回一个json格式的字符串：callback({"status":200,"msg":"ok","data":false})
			}else {
				return result;
			}
		}
		//调用服务
		try {
			result = userService.checkData(param, type);
		} catch (Exception e) {
			TaoquResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		if(null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		    mappingJacksonValue.setJsonpFunction(callback);
		    return mappingJacksonValue;
		}else {
			return result;
		}
	}
	
	/*
	 * 用户注册
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public TaoquResult createUser(TbUser user) {
		try {
			TaoquResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaoquResult.build(500, ExceptionUtil.getStackTrace(e));
		}	
	}
	
	/*
	 * 用户登录
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public TaoquResult userLogin(String username,String password,
			HttpServletRequest request,HttpServletResponse response) {
		try {
			TaoquResult result = userService.userLogin(username, password,request,response);
			return result;
		} catch (Exception e) {
			return TaoquResult.build(500, ExceptionUtil.getStackTrace(e));
		}	
	}	
	
	/*
	 * 根据token返回用户信息，使用taoquResult包装
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		TaoquResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaoquResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		//判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		}else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		    mappingJacksonValue.setJsonpFunction(callback);
		    return mappingJacksonValue;	
		}
	}
	
	@RequestMapping("/logout/{token}")
	public String userLogout(@PathVariable String token) {
		try {
			TaoquResult result = userService.userLogout(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
	}
	
}
