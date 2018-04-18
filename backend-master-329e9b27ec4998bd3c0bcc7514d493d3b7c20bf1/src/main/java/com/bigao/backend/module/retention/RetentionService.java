package com.bigao.backend.module.retention;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.RoleCreateLog;
import com.bigao.backend.log.RoleLoginOrOutLog;
import com.bigao.backend.module.register.RegisterService;
import com.bigao.backend.module.retention.dto.RetentionInfo;
import com.bigao.backend.util.NumberUtil;
import com.bigao.backend.util.SubCollectionUtil;
import com.google.common.collect.Lists;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 留存
 * Created by wait on 2015/12/15.
 */
@Service
public class RetentionService {
    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;
    @Autowired
    private RegisterService registerService;

    public static final int SECOND = 2;
    public static final int THIRD = 3;
    public static final int FOURTH = 4;
    public static final int FIFTH = 5;
    public static final int SIXTH = 6;
    public static final int SEVENTH = 7;
    public static final int TENTH = 10;
    public static final int FOURTEEN = 14;
    public static final int TWENTY_ONE = 21;
    public static final int THIRTY = 30;

    public static final long ONE_DAY_MILL = TimeUnit.DAYS.toMillis(1);

    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.parse("2016-01-12T00:00:00");
        getRetentionKey(dateTime.atZone(ZoneId.systemDefault()).toEpochSecond(), 1452470400000L);
    }

    /**
     * 计算玩家进入地图时间属于开始计算时间之后第几天, 取key值来统计人数
     *
     * @param enterMapTime 玩家进入地图的时间
     * @param startTime    开始计算那一天的零点
     * @return
     */
    public static int getRetentionKey(long enterMapTime, long startTime) {
        if (enterMapTime >= startTime + ONE_DAY_MILL && enterMapTime <= startTime + ONE_DAY_MILL * 2) {// 次日
            return SECOND;
        }
        if (enterMapTime > startTime + ONE_DAY_MILL * 2 && enterMapTime <= startTime + ONE_DAY_MILL * 3) {// 3日
            return THIRD;
        }
        if (enterMapTime > startTime + ONE_DAY_MILL * 3 && enterMapTime <= startTime + ONE_DAY_MILL * 4) {// 4日
            return FOURTH;
        }
        if (enterMapTime > startTime + ONE_DAY_MILL * 4 && enterMapTime <= startTime + ONE_DAY_MILL * 5) {// 5日
            return FIFTH;
        }
        if (enterMapTime > startTime + ONE_DAY_MILL * 5 && enterMapTime <= startTime + ONE_DAY_MILL * 6) {// 6日
            return SIXTH;
        }
        if (enterMapTime > startTime + ONE_DAY_MILL * 6 && enterMapTime <= startTime + ONE_DAY_MILL * 7) {// 7日
            return SEVENTH;
        }
        if (enterMapTime >= startTime + ONE_DAY_MILL && enterMapTime <= startTime + ONE_DAY_MILL * 2) {// 10日
            return TENTH;
        }
        if (enterMapTime >= startTime + ONE_DAY_MILL && enterMapTime <= startTime + ONE_DAY_MILL * 2) {// 14日
            return FOURTEEN;
        }
        if (enterMapTime >= startTime + ONE_DAY_MILL && enterMapTime <= startTime + ONE_DAY_MILL * 2) {// 21日
            return TWENTY_ONE;
        }
        if (enterMapTime >= startTime + ONE_DAY_MILL && enterMapTime <= startTime + ONE_DAY_MILL * 2) {// 31日
            return THIRTY;
        }
        return 999;
    }

    public RetentionInfo queryRetention(int platform, int server, int day) throws SQLException {
        LocalDate serverOpenDate = dbUtil4ServerLog.getOpenDate(platform, server).plusDays(day);
        LocalDate date2 = serverOpenDate.plusDays(1).atTime(0, 0, 0).toLocalDate();// 次日凌晨
        LocalDate date7 = serverOpenDate.plusDays(6).atTime(0, 0, 0).toLocalDate();// 7日凌晨
        LocalDate date10 = serverOpenDate.plusDays(10).atTime(0, 0, 0).toLocalDate();// 10日凌晨
        LocalDate date14 = serverOpenDate.plusDays(14).atTime(0, 0, 0).toLocalDate();// 14日凌晨
        LocalDate date21 = serverOpenDate.plusDays(21).atTime(0, 0, 0).toLocalDate();// 21日凌晨
        LocalDate date30 = serverOpenDate.plusDays(30).atTime(0, 0, 0).toLocalDate();// 21日凌晨
        String timeQuerySql = "((time >=''" + date2 + "''" + // 次日至7日
                " and time <= ''" + date7.atTime(23, 59, 59) + "'')" +
                " or (time>=''" + date10 + "''" +// 10日
                " and time<=''" + date10.atTime(23, 59, 59) + "'')" +
                " or (time>=''" + date14 + "''" +// 14日
                " and time<=''" + date14.atTime(23, 59, 59) + "'')" +
                " or (time>=''" + date21 + "''" +// 21日
                " and time<=''" + date21.atTime(23, 59, 59) + "'')" +
                " or (time>=''" + date30 + "''" +// 30日
                " and time<=''" + date30.atTime(23, 59, 59) + "''))";

        RetentionInfo retentionInfo = new RetentionInfo();
        retentionInfo.setOpenDatesNum(day);

        // 取当天的注册数量
        List<RoleCreateLog> register = registerService.queryCreateRole(platform, server, serverOpenDate.toString(), serverOpenDate.toString());
        retentionInfo.setRegisterNum(register.size());
        if (register.size() == 0) {
            return retentionInfo;
        }

        List<String> roleQuery = Lists.newArrayList();
        StringBuilder roleQueryBuilder = new StringBuilder();
        // 每次查询100个
        List<List<RoleCreateLog>> sub = SubCollectionUtil.toSub(register, 1000);
        for (List<RoleCreateLog> t : sub) {
            if (t.isEmpty()) {
                continue;
            }
            roleQueryBuilder.append(" roleId in(");
            int size = t.size();
            for (int i = 0; i < size; i++) {
                roleQueryBuilder.append(t.get(i).getRoleId());
                if (i < size - 1) {
                    roleQueryBuilder.append(",");
                }
            }
            if (!roleQueryBuilder.toString().endsWith("(")) {
                roleQueryBuilder.append(")");
                roleQuery.add(roleQueryBuilder.toString());
            }
            roleQueryBuilder.delete(0, roleQueryBuilder.length());
        }
        List<RetentionCount> allRoleMapLog = Lists.newArrayList();
        for (String roleSql : roleQuery) {
            String querySql = "SELECT roleId,time" +
                    "    FROM {0}" +
                    "    WHERE " +
                    " state=''LOGIN''" +
                    "    AND " + roleSql +
                    "    AND   " + timeQuerySql;
            // 检查留存
            String endDate = serverOpenDate.plusDays(30).toString();
            List<List<RetentionCount>> data = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleLoginOrOutLog.class, serverOpenDate.toString(), endDate, new BeanListHandler<>(RetentionCount.class)));
            if (data.size() > 0) {
                data.forEach(allRoleMapLog::addAll);
            }
        }
        if (!allRoleMapLog.isEmpty()) {
            long startTime = LocalDateTime.of(serverOpenDate, LocalTime.of(0, 0, 0)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            Map<Integer, Set<Long>> roleHasCount = new HashMap<>();
            for (RetentionCount log : allRoleMapLog) {
                int retentionKey = getRetentionKey(log.getTime().getTime(), startTime);
                if (!roleHasCount.containsKey(retentionKey)) {
                    roleHasCount.put(retentionKey, new HashSet<>());
                }
                if (!roleHasCount.get(retentionKey).contains(log.getRoleId())) {
                    roleHasCount.get(retentionKey).add(log.getRoleId());
                }
            }
            retentionInfo.setSecondNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(SECOND, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setThirdNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(THIRD, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setForthNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(FOURTH, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setFifthNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(FIFTH, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setSixthNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(SIXTH, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setSevenNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(SEVENTH, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setTenNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(TENTH, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setFourteenNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(FOURTEEN, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setTwentyOneNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(TWENTY_ONE, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
            retentionInfo.setThirtyNum(NumberUtil.divideWithBefore(roleHasCount.getOrDefault(THIRTY, Collections.emptySet()).size(), retentionInfo.getRegisterNum()));
        }

        return retentionInfo;
    }
}
