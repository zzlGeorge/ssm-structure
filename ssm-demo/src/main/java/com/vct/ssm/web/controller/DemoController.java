package com.vct.ssm.web.controller;

import com.vct.ssm.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ssm/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @ResponseBody
    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String helloWorld() {
        return "Hello world!";
    }

    @ResponseBody
    @RequestMapping(value = "/testRedis", method = {RequestMethod.GET})
    public Object testRedis() {
        demoService.operate();
        return "ok";
    }

}
