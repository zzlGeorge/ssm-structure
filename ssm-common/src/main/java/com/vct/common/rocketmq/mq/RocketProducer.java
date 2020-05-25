package com.vct.common.rocketmq.mq;

import com.vct.common.rocketmq.message.constant.MessageProtocolConst;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/15 10:43
 * @className SecKillChargeOrderProducer
 * @desc 秒杀订单生产者初始化
 */
@Component
public class RocketProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketProducer.class);

//    @Autowired
//    private MQNamesrvConfig namesrvConfig;

//    @Value("${rocketmq.acl.accesskey}")
//    String aclAccessKey;
//
//    @Value("${rocketmq.acl.accessSecret}")
//    String aclAccessSecret;

    @Value("${mq.namesrvaddr}")
    private String nameSrvAddr;


    private DefaultMQProducer defaultMQProducer;

    @PostConstruct
    public void init() {
//        defaultMQProducer =
//                new DefaultMQProducer
//                        (MessageProtocolConst.SECKILL_CHARGE_ORDER_TOPIC.getProducerGroup(),
//                                new AclClientRPCHook(new SessionCredentials(aclAccessKey, aclAccessSecret)));
        defaultMQProducer = new DefaultMQProducer(MessageProtocolConst.SECKILL_CHARGE_ORDER_TOPIC.getProducerGroup());
        defaultMQProducer.setNamesrvAddr(nameSrvAddr);
        // 发送失败重试次数
        defaultMQProducer.setRetryTimesWhenSendFailed(3);
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            LOGGER.error("[秒杀订单生产者]--SecKillChargeOrderProducer加载异常!e={}", e);
            throw new RuntimeException("[秒杀订单生产者]--SecKillChargeOrderProducer加载异常!", e);
        }
        LOGGER.info("[秒杀订单生产者]--SecKillChargeOrderProducer加载完成!");
    }

    public DefaultMQProducer getProducer() {
        return defaultMQProducer;
    }
}
