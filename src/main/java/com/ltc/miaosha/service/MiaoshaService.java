package com.ltc.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.domain.OrderInfo;
import com.ltc.miaosha.vo.GoodsVo;

@Service
public class MiaoshaService {
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;

	@Transactional
	public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
		//减库存 下订单 写入秒杀订单
		goodsService.reduceStock(goods);
		//order_info maiosha_order
		return orderService.createOrder(user, goods);
	}

	@Transactional
	public OrderInfo normalBuy(MiaoshaUser user, GoodsVo goods ,Integer buyCount) {
		//减库存 下订单 写入普通订单
		goodsService.reduceBuyCount(goods,buyCount);
		//order_info maiosha_order
		return orderService.createStockOrder(user, goods, buyCount);
	}
	
}
