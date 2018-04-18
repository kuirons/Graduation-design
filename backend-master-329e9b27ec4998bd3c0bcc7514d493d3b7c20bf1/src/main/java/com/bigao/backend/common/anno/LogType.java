package com.bigao.backend.common.anno;

/**
 * Created by wait on 2015/12/13.
 */
public enum LogType {
    ONLY_ONE, // 不分表
    DAY, // 每日一张表的日志
    MONTH, // 每月一张表的日志
    YEAR // 每年一张表的日志
}
