package com.ltc.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ltc.miaosha.domain.MiaoshaOrder;
import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.domain.OrderInfo;
import com.ltc.miaosha.redis.RedisService;
import com.ltc.miaosha.result.CodeMsg;
import com.ltc.miaosha.service.GoodsService;
import com.ltc.miaosha.service.MiaoshaService;
import com.ltc.miaosha.service.MiaoshaUserService;
import com.ltc.miaosha.service.OrderService;
import com.ltc.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;
	
    @RequestMapping("/do_miaosha")
    public String list(Model model,MiaoshaUser user,
    		@RequestParam("goodsId")long goodsId) {
    	model.addAttribute("user", user);
    	if(user == null) {
    		return "login";
    	}
    	//判断库存
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
    		return "miaosha_fail";
    	}
    	//判断是否已经秒杀到了
    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
    		return "miaosha_fail";
    	}
    	//减库存 下订单 写入秒杀订单
    	OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
    	model.addAttribute("orderInfo", orderInfo);
    	model.addAttribute("goods", goods);
        return "order_detail";
    }

	@RequestMapping("/normal_buy")
	public String normalBuy(Model model,MiaoshaUser user,
					   @RequestParam("goodsId")long goodsId,@RequestParam("buyCount")Integer buyCount) {
		model.addAttribute("user", user);
		if(user == null) {
			return "login";
		}
		//判断库存
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getGoodsStock();
		if(stock <= buyCount) {
			model.addAttribute("errmsg", CodeMsg.STOCK_OVER.getMsg());
			return "miaosha_fail";
		}
		//减库存 下订单 写入秒杀订单
		OrderInfo orderInfo = miaoshaService.normalBuy(user, goods,buyCount);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goods);
		return "order_detail";
	}
}
