package com.bigao.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Time {
    private static final Logger logger = LoggerFactory.getLogger(Time.class);

    public static String changeTimeToLong(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time += " 00:00:00";
        try {
            Date date = format.parse(time);
            return String.valueOf(date.getTime()/1000);
        } catch (ParseException e) {
            logger.error("时间{}转化失败",time,e);
            return null;
        }
    }

    public static String changeTimeToString(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(Long.valueOf(time+"000")));
    }

    public static void main(String[] args) {
        System.out.println(Time.changeTimeToLong("2017-09-10 10:11:20"));
    }
}
