package com.vct.user.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.vct.goods.api.service.IGoodsService;
import com.vct.goods.bo.GoodsBO;
import com.vct.user.bo.UserBO;
import com.vct.user.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Reference
    private IGoodsService goodsService;

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String helloWorld() {
        return "Hello world! This is user module!";
    }

    @RequestMapping(value = "/getUser", method = {RequestMethod.GET})
    public Object getUser(@RequestParam Map input) {
        Object userId = input.get("userId");
        Long userIdl = Long.parseLong(userId.toString());
        UserBO userBO = userService.getUserById(userIdl);
        return userBO;
    }

    @RequestMapping(value = "/consumeGoods", method = {RequestMethod.GET})
    public Object consumeGoods(@RequestParam Map input) {
        Object goodsId = input.get("goodsId");
        Long goodsId1 = Long.parseLong(goodsId.toString());
        GoodsBO goodsBO = goodsService.getGoodsById(goodsId1);
        return goodsBO;
    }
}
