package com.ltc.miaosha.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltc.miaosha.dao.OrderDao;
import com.ltc.miaosha.domain.MiaoshaOrder;
import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.domain.OrderInfo;
import com.ltc.miaosha.vo.GoodsVo;

import javax.annotation.Resource;

@Service
public class OrderService {
	
	@Resource
	OrderDao orderDao;
	
	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
	}


	public OrderInfo getOrderInfoByOrderId(long orderId){
		return orderDao.getOrderById(orderId);
	}

	@Transactional
	public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId("空");
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		long orderId = orderDao.insert(orderInfo);
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goods.getId());
		miaoshaOrder.setOrderId(orderInfo.getId());
		miaoshaOrder.setUserId(user.getId());
		orderDao.insertMiaoshaOrder(miaoshaOrder);
		return orderInfo;
	}

	public void deleteOrders() {
		orderDao.deleteOrders();
		orderDao.deleteMiaoshaOrders();
	}

	public void updateOrder(long orderId, String address){
		orderDao.updateOrder(orderId,address);
	}

	public List<OrderInfo> getAll(){
		return orderDao.getAllOrder();
	}

	@Transactional
	public OrderInfo createStockOrder(MiaoshaUser user, GoodsVo goods,Integer buyCount) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId("空");
		orderInfo.setGoodsCount(buyCount);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getGoodsPrice()*buyCount);
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		long orderId = orderDao.insert(orderInfo);
		return orderInfo;
	}
	
}
