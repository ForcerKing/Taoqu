/**
 * 
 */
package com.taoqu.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taoqu.cache.mapper.TbUserMapper;
import com.taoqu.common.utils.CookieUtils;
import com.taoqu.common.utils.JsonUtils;
import com.taoqu.common.utils.TaoquResult;
import com.taoqu.pojo.TbUser;
import com.taoqu.pojo.TbUserExample;
import com.taoqu.pojo.TbUserExample.Criteria;
import com.taoqu.sso.dao.JedisClient;
import com.taoqu.sso.pojo.CheckType;
import com.taoqu.sso.service.UserService;

/**
 * 2018年6月1日
 * UserServiceImpl.java
 * @author xushaoqun
 * desc:用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${USERNAME_PASSWORD_ERROR}")
	private String USERNAME_PASSWORD_ERROR;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private int SSO_SESSION_EXPIRE;
	@Value("${SESSION_TIME_OUT}")
	private String SESSION_TIME_OUT;
	
	/*
	 *数据校验 
	 */
	@Override
	public TaoquResult checkData(String content, Integer type) {
		//创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//对数据进行校验，1、2、3分别代表username、phone和email
		//用户名校验
		if(type== CheckType.USERNAME.getType()) {
			criteria.andUsernameEqualTo(content);
		}else if(type == CheckType.PHONE.getType()) {
			criteria.andPhoneEqualTo(content);
		}else {
			criteria.andEmailEqualTo(content);
		}
		//执行查询,注意TbUserMapper中不能开启二级缓存，否则会出错。
		List<TbUser> list = userMapper.selectByExample(example);
		if(list == null || list.size() == 0) {
			return TaoquResult.ok(true);//数据库中不存在这条记录，返回true
		}
		return TaoquResult.ok(false);
	}

	/*
	 * 用户注册
	 */
	@Override
	public TaoquResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		//md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaoquResult.ok();
	}

	/*
	 * 用户登录
	 */
	@Override
	public TaoquResult userLogin(String username, String password,
			HttpServletRequest request,HttpServletResponse response) {

		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		//如果没有此用户名
		if (null == list || list.size() == 0) {
			return TaoquResult.build(400, USERNAME_PASSWORD_ERROR);
		}
		TbUser user = list.get(0);
		//比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaoquResult.build(400, USERNAME_PASSWORD_ERROR);
		}
		//生成token
		String token = UUID.randomUUID().toString();
		//处于安全考虑，保存用户之前，把用户对象中的密码清空。
		user.setPassword(null);
		//把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		//设置session的过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		//添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效
		CookieUtils.setCookie(request, response, "TAOQU_TOKEN", token);
		//返回token
		return TaoquResult.ok(token);

	}

	/*
	 * 根据tokern获取用户信息
	 */
	@Override
	public TaoquResult getUserByToken(String token) {
		//根据token从redis中查询用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		//判断是否为空
		if (StringUtils.isBlank(json)) {
			//提示该会话已过期
			return TaoquResult.build(400, SESSION_TIME_OUT);
		}
		
		//重置过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		//返回用户信息
		return TaoquResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

	/*
	 * 用户退出
	 */
	@Override
	public TaoquResult userLogout(String token) {
		if(token !=null) {
			//删除用户信息
			jedisClient.del(REDIS_USER_SESSION_KEY+":"+token);
		}
		return TaoquResult.ok();
	}

}
