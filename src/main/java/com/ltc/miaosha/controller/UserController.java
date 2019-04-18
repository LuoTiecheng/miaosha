package com.ltc.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.redis.RedisService;
import com.ltc.miaosha.result.Result;
import com.ltc.miaosha.service.MiaoshaUserService;
import org.thymeleaf.util.DateUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model,MiaoshaUser user) {
        return Result.success(user);
    }

    @RequestMapping("/getAll")
    public String getAll(Model model,MiaoshaUser user) {
        model.addAttribute("user", user);
        if(user == null) {
            return "login";
        }
        List<MiaoshaUser> userList = new ArrayList<>();
        userList = userService.getAll();
        Collections.reverse(userList);
        model.addAttribute("userList",userList);
        return "b";
    }

}
