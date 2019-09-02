package com.vct.ssm.service.impl;

import com.vct.common.redis.RedisUtil;
import com.vct.ssm.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Object operate() {
        redisUtil.set("hello","hello world!");
        return null;
    }
}
