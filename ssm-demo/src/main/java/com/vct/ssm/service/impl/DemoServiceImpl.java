package com.vct.ssm.service.impl;

import com.vct.common.redis.RedisUtil;
import com.vct.ssm.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * description: DemoServiceImpl <br>
 * date: 2019/9/2 11:57 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private RedisUtil redisUtil;

    private static final Object firstMonitor = new Object();
    private static final Object secondMonitor = new Object();

    @Override
    public Object operate(Map<String,String> input) {
        Map<String,Object> result = new HashMap<>();
        result.put("success",true);
        result.put("msg","操作成功！");
        if("1".equals(input.get("flag"))) {
            redisUtil.set(input.get("key"), input.get("val"));
        }else if("2".equals(input.get("flag"))) {
            String val = redisUtil.get(input.get("key"));
            result.put("data",val);
        }
        return result;
    }

    @Override
    public void testDeadLock() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    synchronized (firstMonitor) {
                        synchronized (secondMonitor) {
                            System.out.println("Thread1");
                        }
                    }
                }
            }

        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    synchronized (secondMonitor) {
                        synchronized (firstMonitor) {
                            System.out.println("Thread2");
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void testUsingLock() {
        synchronized (firstMonitor){
            System.out.println("^_^^_^^_^^_^");
        }
    }
}
