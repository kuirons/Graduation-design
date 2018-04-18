package com.bigao.backend.module.online;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.db.Server;
import com.bigao.backend.log.RoleInMapCountLog;
import com.bigao.backend.log.RoleLoginOrOutLog;
import com.bigao.backend.module.level.LevelService;
import com.bigao.backend.module.online.dto.OnlineRealTimeInfo;
import com.bigao.backend.module.online.dto.OnlineTimeInfo;
import com.bigao.backend.module.player.LogRole;
import com.bigao.backend.module.player.PlayerService;
import com.bigao.backend.util.DateUtil;
import com.bigao.backend.util.SqlUtil;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author yuan-hai
 * @date 2013-12-19
 */
@Service
public class OnlineService {

    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;
    @Autowired
    private LevelService levelService;
    @Autowired
    private PlayerService playerService;


    public List<OnlineRealTimeInfo> realTimeCount(int platform, int server, int step, String startTime, String endTime) throws SQLException {
        if (step == 0) {
            step = 5;
        }
        String querySql = "select count,time from {0} where " + SqlUtil.formatTime(startTime, endTime) + " GROUP BY ROUND(UNIX_TIMESTAMP(time) / " + (step * 60) + ") limit 5000;";
        LocalDate nowDate = LocalDate.now();
        List<List<RoleInMapCountLog>> data = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleInMapCountLog.class, nowDate.toString(), nowDate.toString(), new BeanListHandler<>(RoleInMapCountLog.class)));
        List<RoleInMapCountLog> all = dbUtil4ServerLog.toOneList(data);
        return all.stream().map(c -> OnlineRealTimeInfo.valueOf(DateUtil.timeStampToString(c.getTime()), c.getCount())).collect(Collectors.toList());
    }


    /**
     * 历史在线
     */
    public List<Map<String, Object>> historyData(List<Server> serverList, String start, String end) throws Exception {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        // 求和
        Map<String, Integer> timeMap = new HashMap<String, Integer>();
        for (Map<String, Object> map : resultList) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (!key.equals("serverName") && !key.equals("platform")) {
                    Integer total = timeMap.get(key);
                    Integer now = (Integer) map.get(key);
                    if (total == null) {
                        timeMap.put(key, now);
                    } else {
                        timeMap.put(key, now + total);
                    }
                }
            }
        }
        // 组织数据
        resultList = Lists.newArrayList();
        Iterator<String> it = timeMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", key);
            map.put("count", timeMap.get(key));
            if (!timeMap.get(key).equals("0")) {
                resultList.add(map);
            }
        }
        //伪造数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("time", "2015-08-28");
        map.put("count", 11000);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("time", "2015-11-19");
        map2.put("count", 40000);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("time", "2015-09-19");
        map3.put("count", 35000);

        resultList.add(map);
        resultList.add(map2);
        resultList.add(map3);

        // 排序
        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String first = (String) o1.get("time");
                String secend = (String) o2.get("time");
                return first.compareTo(secend);
            }
        });

        return resultList;
    }

    public List<OnlineTimeInfo> onlineTimeQuery(String startTime, String endTime, int platform, int server) throws SQLException {
        LocalDate startDateTime = DateUtil.toLocalDateByTime(startTime);
        LocalDate endDateTime = DateUtil.toLocalDateByTime(endTime);

        String querySql = "select state,roleId,time from {0} where " + SqlUtil.formatTime(startTime, endTime);

        List<List<RoleLoginOrOutLog>> allData = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleLoginOrOutLog.class, startDateTime.toString(), endDateTime.toString(), new BeanListHandler<>(RoleLoginOrOutLog.class)));
        List<RoleLoginOrOutLog> allLog = dbUtil4ServerLog.toOneList(allData);
        if (allLog.isEmpty()) {
            return Collections.emptyList();
        }
        ImmutableMap.Builder<Long, OnlineTimeInfo> builder = ImmutableBiMap.builder();
        Collections.sort(allLog, (o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        Map<Long, List<RoleLoginOrOutLog>> roleLog = new TreeMap<>();
        for (RoleLoginOrOutLog log : allLog) {
            if (!roleLog.containsKey(log.getRoleId())) {
                roleLog.put(log.getRoleId(), Lists.newArrayList());
            }
            roleLog.get(log.getRoleId()).add(log);
        }
        List<List<Long>> roleId = Lists.newArrayList();
        int count = 0;
        int index = 1;
        for (Map.Entry<Long, List<RoleLoginOrOutLog>> e : roleLog.entrySet()) {
            count++;
            if (count > 500) {
                count = 0;
                index++;
            }
            if (roleId.size() < index) {
                roleId.add(new ArrayList<>(512));
            }
            roleId.get(index - 1).add(e.getKey());
            OnlineTimeInfo info = new OnlineTimeInfo();
            info.setRoleId(e.getKey());
            info.setStartTime(DateUtil.timeStampToString(e.getValue().get(0).getTime()));

            int time = 0;
            long loginStartTime = 0;
            for (RoleLoginOrOutLog log : e.getValue()) {
                if (log.getState().equals(RoleLoginOrOutLog.LOGIN)) {
                    loginStartTime = log.getTime().getTime();
                } else if (log.getState().equals(RoleLoginOrOutLog.LOGOUT)) {
                    if (loginStartTime == 0) {
                        continue;
                    }
                    // 玩家创建角色, 但有可能会不进入地图, 对于没有登出的玩家过滤掉在线时长
                    time += (log.getTime().getTime() - loginStartTime) / 1000;
                    loginStartTime = 0;
                }
            }
            if (loginStartTime > 0) {
                time += (System.currentTimeMillis() - loginStartTime) / 1000;
            }
            info.setOnlineTime(time);
            builder.put(e.getKey(), info);
        }
        ImmutableMap<Long, OnlineTimeInfo> result = builder.build();
        for (List<Long> idSet : roleId) {
            Map<Long, Integer> roleLevelVo = levelService.getRoleLevel(platform, server, idSet);
            roleLevelVo.entrySet().stream().filter(e -> result.containsKey(e.getKey())).forEach(e -> result.get(e.getKey()).setLevel(e.getValue()));

            List<LogRole> logRoles = playerService.queryLogRole(platform, server,idSet);
            logRoles.stream().filter(logRole -> result.containsKey(logRole.getRoleId())).forEach(logRole -> result.get(logRole.getRoleId()).setAccount(logRole.getAccount()).setRoleName(logRole.getRoleName()));
        }
        List<OnlineTimeInfo> finalResult = new ArrayList<>(result.values().asList());
        finalResult.stream().filter(c -> c.getLevel() == 0).forEach(c -> c.setOnlineTime(0));
        Collections.sort(finalResult, (o1, o2) -> o2.getOnlineTime() - o1.getOnlineTime());
        return finalResult;
    }

    public List<RoleLoginOrOutLog> getLastLogin(int platform, int server, String startDate, String endDate, List<Long> roleId) throws SQLException {
        String querySql = "select roleId,max(time) as time from {0} where state=''"
                + RoleLoginOrOutLog.LOGIN
                + "'' and "
                + SqlUtil.formatDate(startDate, endDate)
                + " and roleId in ("
                + SqlUtil.listToSqlString(roleId) + ") group by roleId";
        List<List<RoleLoginOrOutLog>> allData = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleLoginOrOutLog.class, startDate, endDate, new BeanListHandler<>(RoleLoginOrOutLog.class)));
        return dbUtil4ServerLog.toOneList(allData);
    }

}
