package com.bigao.backend.module.atest;

import com.bigao.backend.util.DateUtil;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by wait on 2016/1/21.
 */
public class TestDateUtil {
    public static void main(String[] args) {
        System.err.println(DateUtil.toLocalDateByDate("2016-01-21"));
        System.err.println(DateUtil.timeStampToString(Timestamp.from(Instant.now())));
        System.err.println(DateUtil.toLocalDateTimeByTime("2016-01-21 16:20:01"));
        System.err.println(DateUtil.toLocalDateByTime("2016-01-21 16:20:01"));
    }
}
