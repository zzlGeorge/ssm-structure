package com.vct.goods.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description: GoodsController <br>
 * date: 2020/4/13 11:10 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

    @ResponseBody
    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String helloWorld() {
        return "Hello world! This is goods module!";
    }

}
