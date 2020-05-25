package com.vct.seckill.dao.mapper;

import com.vct.seckill.dao.dataobject.SecKillProductDO;
import com.vct.seckill.dao.dataobject.SecKillProductDobj;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 14:45
 * @className SecKillProductUserMapper
 * @desc 秒杀商品Mapper接口
 */
public interface SecKillProductUserMapper {

    /**
     * 获取秒杀商品列表
     * @return
     */
    List<SecKillProductDobj> querySecKillProductList();

    /**
     * 根据产品id查询产品
     * @param prodId
     * @return
     */
    SecKillProductDobj queryProdById(String prodId);

    /**
     * 更新商品信息
     * @param updateProdData
     * @return
     */
    int updateProdInfo(SecKillProductDO updateProdData);
}
