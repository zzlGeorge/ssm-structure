package com.vct.ssm.service;

import java.util.Map;

/**
 * description: DemoService <br>
 * date: 2019/9/2 11:56 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public interface DemoService {

    Object operate(Map<String,String> input);

    void testDeadLock();

    void testUsingLock();
}
