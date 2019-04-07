package com.ltc.miaosha.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ltc.miaosha.vo.RegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ltc.miaosha.redis.RedisService;
import com.ltc.miaosha.result.Result;
import com.ltc.miaosha.service.MiaoshaUserService;
import com.ltc.miaosha.vo.LoginVo;

@Controller
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/login/to_login")
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping("/login/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
    	log.info(loginVo.toString());
    	//登录
    	String token = userService.login(response, loginVo);
    	return Result.success(token);
    }

    @RequestMapping("/register/to_register")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/register/do_register")
    @ResponseBody
    public Result<Boolean> doRegister( @Valid RegisterVo registerVo) {
        log.info(registerVo.toString());
        //登录
        userService.register(registerVo);
        return Result.success(true);
    }

}
