package com.vct.ssm.service;

/**
 * description: DemoService <br>
 * date: 2019/9/2 11:56 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public interface DemoService {

    Object operate();

    void testDeadLock();

    void testUsingLock();
}