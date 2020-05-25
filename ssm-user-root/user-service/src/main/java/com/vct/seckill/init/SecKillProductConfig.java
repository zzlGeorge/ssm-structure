package com.vct.seckill.init;

import com.alibaba.fastjson.JSON;
import com.vct.common.util.StringUtils;
import com.vct.seckill.dao.dataobject.SecKillProductDobj;
import com.vct.seckill.service.SecKillProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 14:43
 * @className SecKillProductConfig
 * @desc 秒杀商品初始化加载，正式生产环境是有一个商品服务的，这里简化处理了
 */
@Component
public class SecKillProductConfig implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillProductConfig.class);

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SecKillProductService secKillProductService;

    @Override
    public void afterPropertiesSet() throws Exception {

        List<SecKillProductDobj> killProductList = secKillProductService.querySecKillProductList();
        if (killProductList == null) {
            throw new RuntimeException("请确保数据库中存在秒杀商品配置!");
        }
        killProductList.forEach((secKillProductDobj -> {
            String prodId = secKillProductDobj.getProdId();
            redisTemplate.opsForValue().set(prodId, secKillProductDobj, 86400, TimeUnit.SECONDS);
        }));
        LOGGER.info("[SecKillProductConfig]初始化秒杀配置完成,商品信息=[{}]", JSON.toJSONString(killProductList));
    }

    /**
     * 预减库存
     * @param prodId
     * @return
     */
    public boolean preReduceProdStock(String prodId) {
        if(StringUtils.isBlank(prodId)){
            throw new RuntimeException("请确保prodId非空!");
        }
        synchronized (this) {
            SecKillProductDobj productDobj = (SecKillProductDobj) redisTemplate.opsForValue().get(prodId);
            int prodStock = productDobj.getProdStock();
            if (prodStock <= 0) {
                LOGGER.info("prodId={},prodStock={},当前秒杀商品库存已不足!", prodId, prodStock);
                return false;
            }

            int afterPreReduce = prodStock - 1;
            if (afterPreReduce < 0) {
                // 预减库存后小于0,说明库存预减失败,库存已不足
                LOGGER.info("prodId={} 预减库存失败,当前库存已不足!", prodId);
                return false;
            }
            // 预减库存成功,回写库存
            LOGGER.info("prodId={} 预减库存成功,当前扣除后剩余库存={}!", prodId, afterPreReduce);
            productDobj.setProdStock(afterPreReduce);
            redisTemplate.opsForValue().set(prodId, productDobj, 86400, TimeUnit.SECONDS);
            return true;
        }
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public SecKillProductConfig setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        return this;
    }
}
