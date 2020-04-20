package com.vct.goods.api.service;

import com.vct.goods.bo.GoodsBO;

/**
 * description: IGoodsService <br>
 * date: 2020/4/15 11:18 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public interface IGoodsService {
    GoodsBO getGoodsById(long goodsId);
}
