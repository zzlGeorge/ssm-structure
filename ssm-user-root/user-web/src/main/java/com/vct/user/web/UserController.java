package com.vct.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @ResponseBody
    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String helloWorld() {
        return "Hello world! This is user module!";
    }

}
