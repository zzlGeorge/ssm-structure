package com.vct.user.web;

import com.vct.user.bo.UserBO;
import com.vct.user.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String helloWorld() {
        return "Hello world! This is user module!";
    }

    @ResponseBody
    @RequestMapping(value = "/getUser", method = {RequestMethod.GET})
    public Object getUser(@RequestParam Map input) {
        Object userId = input.get("userId");
        Long userIdl = Long.parseLong(userId.toString());
        UserBO userBO = userService.getUserById(userIdl);
        return userBO;
    }
}
