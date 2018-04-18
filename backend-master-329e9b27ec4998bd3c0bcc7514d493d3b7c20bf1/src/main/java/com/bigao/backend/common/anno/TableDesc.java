package com.bigao.backend.common.anno;

import java.lang.annotation.*;

/**
 * 表格名字注解, 除了时间后面前面的表名, 如数据库真实表名为"accountcreatelog2015", 则在AccountCreateLog类里, 应该为"accountcreatelog"
 * Created by wait on 2015/12/13.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableDesc {
    String value();
    LogType logType() default LogType.MONTH;
}
