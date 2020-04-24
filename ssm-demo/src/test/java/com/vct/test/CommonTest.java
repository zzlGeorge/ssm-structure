package com.vct.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * description: CommonTest <br>
 * date: 2020/4/23 15:30 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public class CommonTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-config.xml","classpath:spring/spring-service.xml");
        context.start();
    }

}
