package com.monco.common.bean;


import java.util.Random;

/**
 * @Auther: monco
 * @Date: 2019/3/19 23:06
 * @Description: 随机码
 */
public class RandomUtils {

    /**
     * 生成随机码
     * @param length
     * @return
     */
    public static String createData(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }
        String data = sb.toString();
        System.out.println(length + " random data: " + data);
        return data;
    }

}
