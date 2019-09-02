package com.vct.mq;

/**
 * description: Listener <br>
 * date: 2019/8/26 11:50 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public class Listener {

    //具体执行业务的方法
    public void listen(String msg) {
        System.out.println("\n消费者开始处理消息： " + msg + "\n");
    }

}
