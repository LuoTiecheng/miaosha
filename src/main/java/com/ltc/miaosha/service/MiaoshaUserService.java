package com.ltc.miaosha.service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ltc.miaosha.vo.RegisterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltc.miaosha.dao.MiaoshaUserDao;
import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.exception.GlobalException;
import com.ltc.miaosha.redis.MiaoshaUserKey;
import com.ltc.miaosha.redis.RedisService;
import com.ltc.miaosha.result.CodeMsg;
import com.ltc.miaosha.util.MD5Util;
import com.ltc.miaosha.util.UUIDUtil;
import com.ltc.miaosha.vo.LoginVo;

import java.util.Random;

@Service
public class MiaoshaUserService {
	
	
	public static final String COOKI_NAME_TOKEN = "token";
	
	@Resource
	MiaoshaUserDao miaoshaUserDao;
	
	@Autowired
	RedisService redisService;
	
	public MiaoshaUser getById(long id) {
		return miaoshaUserDao.getById(id);
	}
	

	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}
	

	public String login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		return token;
	}


	public boolean register(RegisterVo registerVo){
		 String mobile = registerVo.getMobile();
		 String formPass = registerVo.getPassword();
		 String nickname = registerVo.getNickname();

		 MiaoshaUser user = getById(Long.parseLong(mobile));
		 if(user != null){
		 	throw new GlobalException(CodeMsg.USER_EXIST);
		 }
		 Random random = new Random();
		 int number = random.nextInt(10000000)+10000000;
		 String salt = ""+number;
		 String dbPass = MD5Util.formPassToDBPass(formPass,salt);
		 miaoshaUserDao.insert(Long.parseLong(mobile),dbPass,nickname,salt);
         return true;
	}
	
	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
