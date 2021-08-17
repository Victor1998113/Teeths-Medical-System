package com.oral.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author panjianhui
 * @Date
 */
public class Join {
    public static String createOrder(String prefix){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String parse = simpleDateFormat.format(new Date());
        String randomString=String.format("%06d",new Random().nextInt(9999));
        String orderNo = prefix+parse+randomString;
        return orderNo;

    }
}
