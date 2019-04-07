package com.ltc.miaosha.controller;

import com.ltc.miaosha.domain.Goods;
import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.domain.OrderInfo;
import com.ltc.miaosha.result.CodeMsg;
import com.ltc.miaosha.service.GoodsService;
import com.ltc.miaosha.service.OrderService;
import com.ltc.miaosha.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
@Controller
public class OrderController {

    @Resource
    GoodsService goodsService;

    @Resource
    OrderService orderService;


    @RequestMapping(value = "/order/detail",method= RequestMethod.GET)
    public String normalBuy(Model model, MiaoshaUser user,
                            @RequestParam("orderId")long orderId) {
        model.addAttribute("user", user);
        if(user == null) {
            return "login";
        }

        OrderInfo orderInfo = orderService.getOrderInfoByOrderId(orderId);
        Goods goods= goodsService.getGoodsVoByGoodsId(orderInfo.getGoodsId());
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}
