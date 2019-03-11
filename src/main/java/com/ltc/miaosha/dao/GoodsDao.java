package com.ltc.miaosha.dao;

import java.util.List;

import com.ltc.miaosha.domain.Goods;
import org.apache.ibatis.annotations.*;

import com.ltc.miaosha.domain.MiaoshaGoods;
import com.ltc.miaosha.vo.GoodsVo;

@Mapper
public interface GoodsDao {

	@Select("select g.id as id ,g.goods_name as goodsName, g.goods_title as goodsTitle,g.goods_img as goodsImg,g.goods_detail as goodsDetail, g.goods_price as goodsPrice ,g.goods_stock as goodsStock,mg.stock_count as stockCount, mg.start_date as startDate , mg.end_date as endDate,mg.miaosha_price as miaoshaPrice from miaosha_goods mg left join goods g on mg.goods_id = g.id")
	public List<GoodsVo> listGoodsVo();


	@Select("select g.id as id ,g.goods_name as goodsName, g.goods_title as goodsTitle,g.goods_img as goodsImg,g.goods_detail as goodsDetail, g.goods_price as goodsPrice ,g.goods_stock as goodsStock,mg.stock_count as stockCount, mg.start_date as startDate , mg.end_date as endDate,mg.miaosha_price as miaoshaPrice from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
	public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

	@Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
	public int reduceMiaoshaStock(MiaoshaGoods g);

	@Update("update goods set goods_stock = goods_stock - #{buyCount} where id = #{goodsId}")
	public int reduceStock(@Param("goodsId") Long goodsId,@Param("buyCount") Integer buyCount);

}
