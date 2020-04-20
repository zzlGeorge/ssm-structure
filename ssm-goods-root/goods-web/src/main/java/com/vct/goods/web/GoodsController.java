package com.vct.goods.web;

import com.vct.common.util.StringUtils;
import com.vct.goods.api.service.IGoodsService;
import com.vct.goods.bo.GoodsBO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * description: GoodsController <br>
 * date: 2020/4/13 11:10 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    private static final Logger logger = Logger.getLogger(GoodsController.class);

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String helloWorld() {
        return "Hello world! This is goods module!";
    }

    @RequestMapping(value = "/getGoods", method = {RequestMethod.GET})
    public Object getGoods(@RequestParam Map map) {
        String goodsId = StringUtils.null2String(map.get("goodsId"));
        GoodsBO goods = goodsService.getGoodsById(StringUtils.isNotBlank(goodsId) ? Long.valueOf(goodsId) : 0L);
        return goods;
    }

}
