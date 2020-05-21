package com.vct.test;

import com.vct.common.rocketmq.MyProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * description: DemoTest <br>
 * date: 2020/4/23 15:02 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
@ContextConfiguration(locations={
//        "classpath:user-dubbo-config.xml",
        "classpath:spring/spring-rocketmq-producer.xml",
}) //加载配置文件
public class DemoTest extends BaseJunit4Test{

    @Autowired
    private MyProducer myProducer;

    @Test
    public void testRocketMq(){
        Message msg = new Message("Liuzz", "liuzzTag", "hello world".getBytes());
        SendResult sendResult = null;
        try {
            sendResult = myProducer.getDefaultMQProducer().send(msg);
        } catch (InterruptedException | RemotingException | MQBrokerException | MQClientException e) {
            e.printStackTrace();
        }
        // 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            // TODO
        }
    }

}
