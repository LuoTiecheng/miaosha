package com.ltc.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltc.miaosha.dao.GoodsDao;
import com.ltc.miaosha.domain.MiaoshaGoods;
import com.ltc.miaosha.vo.GoodsVo;

import javax.annotation.Resource;

@Service
public class GoodsService {
	
	@Resource
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public void reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceStock(g);
	}
	
	
	
}
