package com.ltc.miaosha.service;

import java.util.List;

import com.ltc.miaosha.domain.Goods;
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

	public boolean reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		int ret = goodsDao.reduceStock(g);
		return ret > 0;
	}

	public void resetStock(List<GoodsVo> goodsList) {
		for(GoodsVo goods : goodsList ) {
			MiaoshaGoods g = new MiaoshaGoods();
			g.setGoodsId(goods.getId());
			g.setStockCount(goods.getStockCount());
			goodsDao.resetStock(g);
		}
	}




	public void reduceBuyCount(GoodsVo goods,Integer buyCount){
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceNormalStock(g.getGoodsId(),buyCount);
	}
	
	
	
}
