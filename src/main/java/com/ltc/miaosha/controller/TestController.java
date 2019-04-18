package com.ltc.miaosha.controller;

import com.ltc.miaosha.dao.GoodsDao;
import com.ltc.miaosha.dao.OrderDao;
import com.ltc.miaosha.dao.UserDao;
import com.ltc.miaosha.domain.Goods;
import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.domain.OrderInfo;
import com.ltc.miaosha.domain.User;
import com.ltc.miaosha.service.MiaoshaUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: luotiecheng
 */
@Controller
@Slf4j
public class TestController {
    @Resource
    GoodsDao goodsDao;

    @Resource
    UserDao userDao;
    @Resource
    OrderDao orderDao;

    @Autowired
    MiaoshaUserService userService;


    @RequestMapping("/test")
    public String testDB(){

        return "a";
    }


    @RequestMapping("/success")
    public String success(){

        return "miaosha_success";
    }

    @RequestMapping("/dbTest")
    public String aaa(){
        List<OrderInfo> orderInfoList = new ArrayList<>();
        orderInfoList=orderDao.getAllOrder();
        System.out.println(orderInfoList.size());
        System.out.println(orderInfoList.get(3).getDeliveryAddrId());
        return "miaosha_success";
    }

    @RequestMapping("/admin")
    public String getAll(Model model, MiaoshaUser user) {
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
