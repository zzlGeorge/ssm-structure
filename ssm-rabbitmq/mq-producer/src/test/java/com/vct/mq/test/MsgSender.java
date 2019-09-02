package com.vct.mq.test;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * description: MsgSender 测试消息生产方<br>
 * date: 2019/8/26 11:37 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public class MsgSender {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring-rabbitmq-p.xml");
        //RabbitMQ模板
        RabbitTemplate template = ctx.getBean(RabbitTemplate.class);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//24小时制
        //发送消息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("type", "1");
        msg.put("date", date);
        template.convertAndSend("type", JSON.toJSONString(msg));
        Thread.sleep(1000);// 休眠1秒
        ctx.destroy(); //容器销毁
    }

}
