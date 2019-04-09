package com.ltc.miaosha.service;

import com.ltc.miaosha.domain.MiaoshaOrder;
import com.ltc.miaosha.redis.MiaoshaKey;
import com.ltc.miaosha.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.domain.OrderInfo;
import com.ltc.miaosha.vo.GoodsVo;

import java.util.List;

@Service
public class MiaoshaService {


	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	RedisService redisService;

	@Transactional
	public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
		//减库存 下订单 写入秒杀订单
		boolean success = goodsService.reduceStock(goods);
		if(success) {
			//order_info maiosha_order
			return orderService.createOrder(user, goods);
		}else {
			setGoodsOver(goods.getId());
			return null;
		}
	}

	@Transactional
	public OrderInfo ordinaryMiaosha(MiaoshaUser user, GoodsVo goods) {
		//减库存 下订单 写入秒杀订单
		goodsService.ordinaryReduceStock(goods);
		//order_info maiosha_order
		return orderService.createOrder(user, goods);
	}


	public long getMiaoshaResult(Long userId, long goodsId) {
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
		if(order != null) {//秒杀成功
			return order.getOrderId();
		}else {
			boolean isOver = getGoodsOver(goodsId);
			if(isOver) {
				return -1;
			}else {
				return 0;
			}
		}
	}

	private void setGoodsOver(Long goodsId) {
		redisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
	}

	private boolean getGoodsOver(long goodsId) {
		return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
	}

	public void reset(List<GoodsVo> goodsList) {
		goodsService.resetStock(goodsList);
		orderService.deleteOrders();
	}




	@Transactional
	public OrderInfo normalBuy(MiaoshaUser user, GoodsVo goods ,Integer buyCount) {
		//减库存 下订单 写入普通订单
		goodsService.reduceBuyCount(goods,buyCount);
		//order_info maiosha_order
		return orderService.createStockOrder(user, goods, buyCount);
	}
	
}
