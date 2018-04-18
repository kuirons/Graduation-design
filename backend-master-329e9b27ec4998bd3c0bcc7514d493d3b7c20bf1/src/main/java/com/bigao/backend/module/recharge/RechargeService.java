package com.bigao.backend.module.recharge;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.db.DBServer;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.OldUserLog;
import com.bigao.backend.log.RoleExchangeLog;
import com.bigao.backend.log.RoleLoginOrOutLog;
import com.bigao.backend.log.RoleMonetaryLog;
import com.bigao.backend.module.recharge.dto.RechargeInfo;
import com.bigao.backend.module.register.RegisterService;
import com.bigao.backend.module.register.dto.RegisterInfo;
import com.bigao.backend.util.DateUtil;
import com.bigao.backend.util.NumberUtil;
import com.bigao.backend.util.SqlUtil;
import com.bigao.backend.util.SubCollectionUtil;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 充值查询
 * Created by wait on 2016/1/2.
 */
@Service
public class RechargeService {
    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;
    @Autowired
    private RegisterService registerService;

    public RechargeInfo allRecharge(int platform, int server, String startDate, String endDate) throws SQLException {
        String querySql = "SELECT a.rmb,a.roleId,a.time, b.sumRmb from (select rmb,roleId,time from {0} where " + SqlUtil.formatDate(startDate, endDate) + " ) as a left join (select sum(rmb) as sumRmb from {0} ) as b on 1=1;";
        List<List<RoleExchangeLog>> exchangeLogList = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleExchangeLog.class, startDate, endDate, new BeanListHandler<>(RoleExchangeLog.class)));
        RechargeInfo rechargeInfo = RechargeInfo.empty(platform, server);
        DBServer dbServer = dbUtil4ServerLog.getDBServer(platform, server);
        if (dbServer == null) {
            return new RechargeInfo();
        }
        rechargeInfo.setOpenDate(dbServer.getServer().getOpen_date());
        rechargeInfo.setOpenDays((int) ChronoUnit.DAYS.between(DateUtil.parseToDateTime(dbServer.getServer().getOpen_date()), LocalDateTime.now()));

        if (exchangeLogList != null && exchangeLogList.size() > 0) {
            List<RoleExchangeLog> allLog = new ArrayList<>(dbUtil4ServerLog.toOneList(exchangeLogList));
            Collections.sort(allLog, (o1, o2) -> o1.getTime().compareTo(o2.getTime()));
            // 用户重复付费次数
            Map<Long, Integer> repeat = new HashMap<>();
            // 用户次日重复付费次数
            Set<Long> otherRepeat = new HashSet<>();
            int lastYear = 0;
            int size = allLog.size();
            for (int i = 0; i < size; i++) {
                RoleExchangeLog log = allLog.get(i);
                if (lastYear == 0) {
                    lastYear = log.getTime().toLocalDateTime().getYear();
                }
                // 这里使用sum的时候, 可能会出现跨年的情况
                if (rechargeInfo.getSumRmb() == 0 || lastYear != log.getTime().toLocalDateTime().getYear()) {
                    rechargeInfo.setSumRmb(log.getSumRmb() + rechargeInfo.getSumRmb());
                }
                rechargeInfo.setRmb(rechargeInfo.getRmb() + log.getRmb());

                repeat.merge(log.getRoleId(), 1, Integer::sum);
                if (!otherRepeat.contains(log.getRoleId())) {
                    for (int j = (i + 1); j < size; j++) {
                        RoleExchangeLog otherLog = allLog.get(j);
                        if (otherLog.getRoleId() == log.getRoleId()) {
                            otherRepeat.add(otherLog.getRoleId());
                            break;
                        }
                    }
                }
            }
            rechargeInfo.setRechargeNum(repeat.size());// 这里暂时当做充值人数处理
            rechargeInfo.setRepeatRechargeNum((int) repeat.values().stream().filter(i -> i > 1).count());
            rechargeInfo.setOtherRechargeNum(otherRepeat.size());
        }
        RegisterInfo registerInfo = registerService.queryRegisterAndCreateRole(platform, server, startDate, endDate);
        if (registerInfo != null) {
            rechargeInfo.setRegisterNum(registerInfo.getRegisterNum());
            rechargeInfo.setRoleNum(registerInfo.getCreateRoleNum());
            rechargeInfo.setRoleRate(registerInfo.getCreateRate());
        }
        rechargeInfo.setRechargeRate(NumberUtil.divide(rechargeInfo.getRechargeNum(), rechargeInfo.getRegisterNum()));
        rechargeInfo.setArppu(NumberUtil.divideWithoutPercent(rechargeInfo.getSumRmb(), rechargeInfo.getRegisterNum()));
        rechargeInfo.setArpu(NumberUtil.divideWithoutPercent(rechargeInfo.getSumRmb(), rechargeInfo.getRegisterNum()));

        querySql = "select distinct(roleId) from {0} where state=''LOGIN'' and " + SqlUtil.formatDate(startDate, endDate);
        List<List<Long>> allData = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleLoginOrOutLog.class, startDate, endDate, new ColumnListHandler<Long>(1)));
        Set<Long> roleActive = new HashSet<>();
        if (allData != null) {
            for (List<Long> e : allData) {
                roleActive.addAll(e.stream().collect(Collectors.toList()));
            }
            rechargeInfo.setActiveNum(roleActive.size());
        }
        if (!roleActive.isEmpty() && dbUtil4ServerLog.getLogByServer(dbServer) != null) {
            List<List<Long>> subData = SubCollectionUtil.toSub(roleActive, 500);
            StringBuilder builder = new StringBuilder();
            for (List<Long> t : subData) {
                builder.append("select count(*) from {0} where `id` in (").append(SubCollectionUtil.toSqlIn(t)).append(")");

                List<Long> count = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, builder.toString(), OldUserLog.class, new ScalarHandler(1)));
                if (!count.isEmpty()) {
                    rechargeInfo.setOldActiveNum(rechargeInfo.getOldActiveNum() + count.stream().mapToInt(Long::intValue).sum());
                }
                builder.delete(0, builder.length());
            }
        }
        querySql = "select sum(`change`) from {0} where monetary=''GOLD'' and `change` < 0 and " + SqlUtil.formatDate(startDate, endDate);
        List<BigDecimal> allChange = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleMonetaryLog.class, startDate, endDate, new ScalarHandler<>(1)));
        if (allChange != null && !allChange.isEmpty()) {
            rechargeInfo.setCostGold(Math.abs(allChange.stream().mapToInt(BigDecimal::intValue).sum()));
        }
        return rechargeInfo;
    }
}
