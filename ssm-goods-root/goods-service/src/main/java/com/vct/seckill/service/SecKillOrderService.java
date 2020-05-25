package com.vct.seckill.service;

import com.vct.seckill.dao.dataobject.OrderInfoDO;
import com.vct.seckill.dao.dataobject.OrderInfoDobj;
import com.vct.seckill.dto.Result;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/15 11:10
 * @className SecKillOrderService
 * @desc 秒杀订单本地service接口
 */
public interface SecKillOrderService {

    /**
     * 根据订单号查询订单明细
     * @param orderId
     * @return
     */
    OrderInfoDobj queryOrderInfoById(String orderId);

    /**
     * 秒杀订单入库
     * @param orderInfoDO
     * @return
     */
    boolean chargeSecKillOrder(OrderInfoDO orderInfoDO);

    /**
     * 订单状态到处理中
     * @param orderInfoDO
     */
    void updateOrderStatusDealing(OrderInfoDO orderInfoDO);

    /**
     * 内部订单查询
     * @return
     */
    Result queryOrder(OrderInfoDO orderInfoDO);
}
