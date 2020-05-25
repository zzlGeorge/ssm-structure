package com.vct.user.web;

import com.vct.seckill.init.SecKillProductConfig;
import com.vct.seckill.service.SecKillChargeService;
import com.vct.user.dto.CodeMsg;
import com.vct.user.dto.Result;
import com.vct.user.dto.request.ChargeOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * description: SeckillOrderController <br>
 * date: 2020/5/22 17:33 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/api")
public class SeckillOrderController {


    @Autowired
    private SecKillChargeService secKillChargeService;

    @Autowired
    private SecKillProductConfig secKillProductConfig;

    /**
     * 平台下单接口
     * @param chargeOrderRequest
     * @return
     */
    @RequestMapping(value = "/charge.do", method = {RequestMethod.POST})
    public Result chargeOrder(@ModelAttribute ChargeOrderRequest chargeOrderRequest) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionId = attributes.getSessionId();
        // 下单前置参数校验
        if (!secKillChargeService.checkParamsBeforeSecKillCharge(chargeOrderRequest, sessionId)) {
            return Result.error(CodeMsg.PARAM_INVALID);
        }
        // 前置商品校验
        String prodId = chargeOrderRequest.getProdId();
        if (!secKillChargeService.checkProdConfigBeforeKillCharge(prodId, sessionId)) {
            return Result.error(CodeMsg.PRODUCT_NOT_EXIST);
        }
        // 前置预减库存
        if (!secKillProductConfig.preReduceProdStock(prodId)) {
            return Result.error(CodeMsg.PRODUCT_STOCK_NOT_ENOUGH);
        }
        // 秒杀订单入队
        return secKillChargeService.secKillOrderEnqueue(chargeOrderRequest, sessionId);
    }

}
