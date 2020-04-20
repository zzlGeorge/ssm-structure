package com.vct.goods.service.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.vct.goods.api.service.IGoodsService;
import com.vct.goods.bo.GoodsBO;
import com.vct.goods.dao.GoodsDao;
import com.vct.goods.domain.GoodsDO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description: GoodsServiceImpl <br>
 * date: 2020/4/15 11:17 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */

@Service
public class GoodsServiceImpl implements IGoodsService {

    private static final Logger logger = Logger.getLogger(GoodsServiceImpl.class);

    @Autowired
    private GoodsDao goodsDao;

    public GoodsBO getGoodsById(long goodsId) {
        GoodsDO goodsDO = goodsDao.get(goodsId);
        GoodsBO goodsBO = null;
        if(goodsDO!=null){
            goodsBO = new GoodsBO();
            goodsBO.setCreateTime(goodsDO.getCreateTime());
            goodsBO.setGoodsName(goodsDO.getGoodsName());
            goodsBO.setGoodsPrice(goodsDO.getGoodsPrice());
            goodsBO.setGoodsWeight(goodsDO.getGoodsWeight());
            goodsBO.setId(goodsDO.getId());
            goodsBO.setModifyTime(goodsDO.getModifyTime());
        }
        return goodsBO;
    }
}
