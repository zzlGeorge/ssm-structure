package com.vct.tool.test;

import com.vct.tool.util.DESUtil;

/**
 * @author vincent
 * @version 1.0.0
 * @ClassName CommonTest.java
 * @Description TODO
 * @createTime 2019-08-07 19:09:00
 */
public class CommonTest {

    public static void main(String[] args) {
        System.out.println(DESUtil.getEncryptString("root"));
        System.out.println(DESUtil.getEncryptString("518178"));
    }

}
