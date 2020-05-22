package com.vct.common.test;

import java.util.HashMap;
import java.util.Map;

/**
 * description: CommonTest <br>
 * date: 2020/3/23 16:52 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public class CommonTest {
    public static void main(String[] args) {
        try {
            try {
                System.out.println(111);
                int i = 1/0;
                System.out.println(222);
            } finally {
                System.out.println("hello 11111");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
