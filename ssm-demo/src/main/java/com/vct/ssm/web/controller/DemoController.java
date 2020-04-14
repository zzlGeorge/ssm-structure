package com.vct.ssm.web.controller;

import com.vct.ssm.service.DemoService;
import org.apache.commons.httpclient.HttpURL;
import org.apache.webdav.lib.WebdavResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

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
    public Object testRedis(@RequestParam Map<String,String> input) {
        Object operate = null;
        try {
            operate = demoService.operate(input);
        } catch (Exception e) {
            System.out.println(e);
        }
        return operate;
    }

    @ResponseBody
    @RequestMapping(value = "/testWebdav")
    public Object testWebdav() throws IOException {
        String path="http://192.168.142.133:8002/authwebdav/";
//      HttpURL httpURL = new HttpURL("admin","N#web_67dav%2","172.26.0.151",18081,"/authwebdav/");
        HttpURL httpURL = new HttpURL("vincent", "123456", "192.168.142.133", 8002, "/authwebdav/");
        WebdavResource webdavResource = new WebdavResource(path);
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = "/testDeadLock")
    public Object testDeadLock(){
        demoService.testDeadLock();
        return "testDeadLock ok";
    }

    @ResponseBody
    @RequestMapping(value = "/testUsingLock")
    public Object testUsingLock(){
        demoService.testUsingLock();
        return "testUsingLock ok";
    }

}
