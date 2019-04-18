package com.ltc.miaosha.controller;

import com.ltc.miaosha.domain.MiaoshaUser;
import com.ltc.miaosha.redis.RedisService;
import com.ltc.miaosha.service.GoodsService;
import com.ltc.miaosha.service.MiaoshaUserService;
import com.ltc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: luotiecheng
 */
@Controller
@RequestMapping("/normal")
public class NormalProController {
    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;
    private final String string = "/to_list";

    @RequestMapping(string)
    public String list(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "normal_product";
    }

    @RequestMapping("/normal_buy/{goodsId}")
    public String detail(Model model,MiaoshaUser user,
                         @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);


        return "normal_detail";
    }

    @RequestMapping("/search_buy/{goodsName}")
    public String searchDetail(Model model,MiaoshaUser user,
                         @PathVariable("goodsName")String goodsName) {
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsName(goodsName);
        model.addAttribute("goods", goods);


        return "normal_detail";
    }
}
