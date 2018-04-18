package com.bigao.backend.util;

import com.bigao.backend.common.anno.LogType;
import com.bigao.backend.common.anno.TableDesc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import static java.time.temporal.ChronoField.*;

public class DateUtil {
    public static final DateTimeFormatter INIT_ISO_LOCAL_TIME;

    static {
        // 形如16:25:47
        INIT_ISO_LOCAL_TIME = new DateTimeFormatterBuilder()
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)// 去掉后面的纳秒
                .toFormatter();
    }


    public static final DateTimeFormatter INIT_ISO_LOCAL_DATE_TIME;

    static {
        // 形如2016-01-21 16:25:47
        INIT_ISO_LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(" ")// 把T改为空格
                .append(INIT_ISO_LOCAL_TIME)
                .toFormatter();
    }

    /**
     * 把yyyy-MM-dd格式的字符串日期转为LocalDate
     *
     * @param localDateString
     * @return
     */
    public static LocalDate toLocalDateByDate(String localDateString) {
        return LocalDate.parse(localDateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 把yyyy-MM-dd格式的字符串日期转为毫秒
     *
     * @param localDateString
     * @return
     */
    public static long toMill(String localDateString) {
        LocalDate localDate = LocalDate.parse(localDateString, DateTimeFormatter.ISO_LOCAL_DATE);
        return localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    public static String timeStampToString(Timestamp timestamp) {
        return timestamp.toLocalDateTime().format(INIT_ISO_LOCAL_DATE_TIME);
    }

    /**
     * @param timeString yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime toLocalDateTimeByTime(String timeString) {
        return LocalDateTime.parse(timeString, INIT_ISO_LOCAL_DATE_TIME);
    }

    /**
     * @param timeString yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDate toLocalDateByTime(String timeString) {
        return LocalDate.parse(timeString, INIT_ISO_LOCAL_DATE_TIME);
    }

    /**
     * @param timeString yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime parseToDateTime(String timeString) {
        return LocalDateTime.parse(timeString, INIT_ISO_LOCAL_DATE_TIME);
    }

    public static long toMill(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static boolean isBefore(String dbTableName, String date, Class beanClass) {
        if (beanClass.getAnnotation(TableDesc.class) == null) {
            throw new RuntimeException(beanClass.getName() + "需要有@TableName注解");
        }
        TableDesc tableDesc = (TableDesc) beanClass.getAnnotation(TableDesc.class);
        LogType logType = tableDesc.logType();
        String dbTableDateStr = dbTableName.substring(tableDesc.value().length());
        if (logType.equals(LogType.YEAR)) {
            if (Integer.parseInt(dbTableDateStr) <= Integer.parseInt(date.substring(0, 4))) {
                return true;
            }
            return false;
        } else if (logType.equals(LogType.MONTH)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM");
            try {
                Date dbTableDate = simpleDateFormat.parse(dbTableDateStr);
                LocalDate dbLocalDate = dbTableDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate queryDate = toLocalDateByDate(date);
                if (dbLocalDate.getYear() < queryDate.getYear()) {
                    return true;
                }
                if (dbLocalDate.getMonthValue() < queryDate.getMonthValue()) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }
        // 以天为单位的暂时不做
        return true;
    }

    public static boolean isAfter(String dbTableName, String date, Class beanClass) {
        if (beanClass.getAnnotation(TableDesc.class) == null) {
            throw new RuntimeException(beanClass.getName() + "需要有@TableName注解");
        }
        TableDesc tableDesc = (TableDesc) beanClass.getAnnotation(TableDesc.class);
        LogType logType = tableDesc.logType();
        String dbTableDateStr = dbTableName.substring(tableDesc.value().length());
        if (logType.equals(LogType.YEAR)) {
            if (Integer.parseInt(dbTableDateStr) >= Integer.parseInt(date.substring(0, 4))) {
                return true;
            }
            return false;
        } else if (logType.equals(LogType.MONTH)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM");
            try {
                Date dbTableDate = simpleDateFormat.parse(dbTableDateStr);
                LocalDate dbLocalDate = dbTableDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate queryDate = toLocalDateByDate(date);
                if (dbLocalDate.getYear() > queryDate.getYear()) {
                    return true;
                }
                if (dbLocalDate.getMonthValue() > queryDate.getMonthValue()) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }
        // 以天为单位的暂时不做
        return true;
    }

    public static boolean isBetween(String dbTableName, String startDate, String endDate, Class beanClass) {
        if (beanClass.getAnnotation(TableDesc.class) == null) {
            throw new RuntimeException(beanClass.getName() + "需要有@TableName注解");
        }
        TableDesc tableDesc = (TableDesc) beanClass.getAnnotation(TableDesc.class);
        LogType logType = tableDesc.logType();
        String dbTableDateStr = dbTableName.substring(tableDesc.value().length());
        if (logType.equals(LogType.YEAR)) {
            int dbYear = Integer.parseInt(dbTableDateStr);
            if (dbYear >= Integer.parseInt(startDate.substring(0, 4)) && dbYear <= Integer.parseInt(endDate.substring(0, 4))) {
                return true;
            }
        } else if (logType.equals(LogType.MONTH)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM");
            try {
                LocalDate dbLocalDate = simpleDateFormat.parse(dbTableDateStr).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate startLocalDate = toLocalDateByDate(startDate);
                LocalDate endLocalDate = toLocalDateByDate(endDate);
                // 先比年不在这区间的筛选掉
                if (dbLocalDate.getYear() < startLocalDate.getYear() || dbLocalDate.getYear() > endLocalDate.getYear()) {
                    return false;
                }

                if (endLocalDate.getYear() > dbLocalDate.getYear()) {
                    // 如果截止日期的年比数据库的大, 那么属于判断是否至少比开始日期后
                    return dbLocalDate.getYear() >= startLocalDate.getYear() && dbLocalDate.getMonthValue() >= startLocalDate.getMonthValue();
                } else {
                    // 截止日期跟数据库日期年份一致, 则月份要小于截止日期月份
                    if (endLocalDate.getMonthValue() < dbLocalDate.getMonthValue()) {
                        return false;
                    }
                    // 如果开始日期跟数据库日期年份一致
                    if (startLocalDate.getYear() < dbLocalDate.getYear()) { // 年份
                        return true;
                    } else if (startLocalDate.getYear() == dbLocalDate.getYear()) {
                        if (dbLocalDate.getMonthValue() >= startLocalDate.getMonthValue()) {
                            return true;
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 以天为单位的暂时不做
        return false;
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://121.201.59.100:22306/game_log_9998?autoReconnect=true","game","game");
            //Connection connection=DriverManager.getConnection("jdbc:mysql://123.56.129.155:3306/xiyou?autoReconnect=true","game","game");

            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
