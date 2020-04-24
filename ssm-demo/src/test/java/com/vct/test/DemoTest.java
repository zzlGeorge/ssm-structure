package com.vct.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.vct.goods.api.service.IGoodsService;
import com.vct.goods.bo.GoodsBO;
import com.vct.user.bo.UserBO;
import com.vct.user.service.api.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * description: DemoTest <br>
 * date: 2020/4/23 15:02 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
@ContextConfiguration(locations={
        "classpath:user-dubbo-config.xml",
}) //加载配置文件
public class DemoTest extends BaseJunit4Test{

    @Autowired
    private IUserService userService;

    @Reference
    private IGoodsService goodsService;

    @Test
    public void testUser(){
        UserBO userById = userService.getUserById(1L);
        System.out.println();
        GoodsBO goods = goodsService.getGoodsById(1L);
        System.out.println();
    }

}
