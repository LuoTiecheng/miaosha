package com.ltc.miaosha.controller;

import com.ltc.miaosha.dao.GoodsDao;
import com.ltc.miaosha.dao.UserDao;
import com.ltc.miaosha.domain.Goods;
import com.ltc.miaosha.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: luotiecheng
 */
@RestController
public class TestController {
    @Resource
    GoodsDao goodsDao;

    @Resource
    UserDao userDao;

//
//    @RequestMapping("/test")
//    public String testDB(){
//        List<Goods> goods = goodsDao.test(1);
//        User user = userDao.getById(1);
//        System.out.print(goods.get(0).toString());
//        return "success";
//    }
}
