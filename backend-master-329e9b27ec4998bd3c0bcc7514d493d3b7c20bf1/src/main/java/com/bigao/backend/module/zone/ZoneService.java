package com.bigao.backend.module.zone;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.RoleCopyDoneLog;
import com.bigao.backend.log.RoleCopyEnterLog;
import com.bigao.backend.log.RoleCopyOfflineLog;
import com.bigao.backend.log.RoleLoginOrOutLog;
import com.bigao.backend.module.zone.dto.CopyInfo;
import com.bigao.backend.module.zone.vo.CopyCount;
import com.bigao.backend.module.zone.vo.CopyOfflineTime;
import com.bigao.backend.module.zone.vo.CopyVo;
import com.bigao.backend.module.zone.vo.RoleLoginTime;
import com.bigao.backend.util.NumberUtil;
import com.bigao.backend.util.SqlUtil;
import com.bigao.backend.util.SubCollectionUtil;
import com.google.common.collect.Lists;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wait on 2015/12/28.
 */
@Service
public class ZoneService {
    private Logger logger = LoggerFactory.getLogger(ZoneService.class);

    private Map<String, CopyTypeInfo> zoneName = new HashMap<>();

    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

    @PostConstruct
    public void initZoneName() {
        try {
            for (CopyType copyType : CopyType.values()) {
                CopyDesc copyDesc = copyType.getClass().getField(copyType.name()).getAnnotation(CopyDesc.class);
                if (copyDesc != null) {
                    zoneName.put(copyType.name(), CopyTypeInfo.valueOf(copyType.getValue(), copyDesc.value()));
                }
            }
            logger.info("副本:[{}]", zoneName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public List<CopyInfo> queryLost(int platform, int server, String startDate, String endDate) throws SQLException {
        // 取时间段内每个玩家的最大掉线时间
        String querySql = "select copyType,roleId,MAX(time) as time from {0} where " + SqlUtil.formatDate(startDate, endDate) + " group by roleId";

        List<List<RoleCopyOfflineLog>> allEnterLog = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleCopyOfflineLog.class, startDate, endDate, new BeanListHandler<>(RoleCopyOfflineLog.class)));

        Map<Long, CopyOfflineTime> copyOfflineTimeMap = new HashMap<>();
        List<RoleCopyOfflineLog> toOneEnter = dbUtil4ServerLog.toOneList(allEnterLog);
        Collections.sort(toOneEnter, (o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        // 因为会涉及到跨表, 所以有可能会存在同个玩家的情况
        for (RoleCopyOfflineLog log : toOneEnter) {
            if (!copyOfflineTimeMap.containsKey(log.getRoleId())) {
                copyOfflineTimeMap.put(log.getRoleId(), CopyOfflineTime.valueOf(log.getTime().getTime(), log.getCopyType()));
            } else {
                CopyOfflineTime copyOfflineTime = copyOfflineTimeMap.get(log.getRoleId());
                if (copyOfflineTime.getOfflineTime() < log.getTime().getTime()) {
                    copyOfflineTime.setOfflineTime(log.getTime().getTime());
                    copyOfflineTime.setCopyType(log.getCopyType());
                }
            }
        }
        // 取上面这部分玩家最后登录游戏的时间
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime startTime = nowTime.minusDays(30);// 最近一个月之内都没有登录, 就算流失了
        List<List<Long>> subRole = SubCollectionUtil.toSub(copyOfflineTimeMap.keySet(), 100);

        Map<Long, RoleLoginTime> lastLoginMap = new HashMap<>();
        for (List<Long> one : subRole) {
            String query = "select t1.roleId,t1.state,t1.time from {0} t1, (select max(time) as time,roleId from {0} where " + SqlUtil.formatTime(startTime, nowTime) + " and roleId in(" + SubCollectionUtil.toSqlIn(one) + ") group by roleId) t2 where t1.roleId=t2.roleId and t1.time = t2.time";
            List<List<RoleLoginTime>> lastLoginTime = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, query, RoleLoginOrOutLog.class, startTime.toLocalDate().toString(), nowTime.toLocalDate().toString(), new BeanListHandler<>(RoleLoginTime.class)));
            for (List<RoleLoginTime> e : lastLoginTime) {
                for (RoleLoginTime c : e) {
                    if (lastLoginMap.containsKey(c.getRoleId())) {
                        RoleLoginTime loginTime = lastLoginMap.get(c.getRoleId());
                        if (c.getTime().after(loginTime.getTime())) {
                            loginTime.setTime(c.getTime());
                            loginTime.setState(c.getState());
                        }
                    } else {
                        lastLoginMap.put(c.getRoleId(), RoleLoginTime.valueOf(c.getTime(), c.getState()));
                    }
                }
            }
        }

        querySql = "select count(roleId) as count,copyType from {0} where " + SqlUtil.formatDate(startDate, endDate) + " group by copyType";
        List<List<CopyCount>> enterCount = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleCopyEnterLog.class, startDate, endDate, new BeanListHandler<>(CopyCount.class)));
        Map<String, CopyVo> enterMap = new HashMap<>();
        for (List<CopyCount> e : enterCount) {
            for (CopyCount c : e) {
                if (enterMap.containsKey(c.getCopyType())) {
                    CopyVo vo = enterMap.get(c.getCopyType());
                    vo.setEnterCount(vo.getEnterCount() + c.getCount());
                } else {
                    enterMap.put(c.getCopyType(), CopyVo.valueOf(c.getCount()));
                }
            }
        }

        querySql = "select count(roleId) as count,copyType from {0} where " + SqlUtil.formatDate(startDate, endDate) + " group by copyType";
        List<List<CopyCount>> doneCount = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleCopyDoneLog.class, startDate, endDate, new BeanListHandler<>(CopyCount.class)));
        for (List<CopyCount> e : doneCount) {
            for (CopyCount c : e) {
                if (!enterMap.containsKey(c.getCopyType())) {
                    continue;
                }
                CopyVo vo = enterMap.get(c.getCopyType());
                vo.setDoneCount(vo.getDoneCount() + c.getCount());
            }
        }


        List<CopyInfo> builder = Lists.newArrayList();
        for (Map.Entry<String, CopyVo> e : enterMap.entrySet()) {
            CopyTypeInfo typeInfo = zoneName.get(e.getKey());
            if (typeInfo == null) {
                continue;
            }
            CopyInfo info = new CopyInfo();
            info.setCopyId(typeInfo.getValue());
            info.setCopyName(typeInfo.getName());
            CopyVo vo = e.getValue();
            info.setLostRate(NumberUtil.divide(vo.getDoneCount(), vo.getEnterCount()) + "[" + vo.getDoneCount() + "/" + vo.getEnterCount() + "]");
            for (Map.Entry<Long, CopyOfflineTime> t : copyOfflineTimeMap.entrySet()) {
                CopyOfflineTime copyOfflineTime = t.getValue();
                if (!copyOfflineTime.getCopyType().equals(e.getKey())) {
                    continue;
                }
                // 过去一个月里完全没有登录过
                if (!lastLoginMap.containsKey(t.getKey())) {
                    info.setLostNum(info.getLostNum() + 1);
                    continue;
                }
                RoleLoginTime loginTime = lastLoginMap.get(t.getKey());
                if (loginTime.getState().equals("LOGOUT") && copyOfflineTime.getOfflineTime() > loginTime.getTime().getTime()) {
                    info.setLostNum(info.getLostNum() + 1);
                }
            }
            builder.add(info);
        }
        Collections.sort(builder, (o1, o2) -> o1.getCopyId() - o2.getCopyId());
        return builder;
    }
}
