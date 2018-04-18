package com.bigao.backend.module.task;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.common.config.GameConfigService;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.RoleClientNodeLog;
import com.bigao.backend.log.RoleTaskAcceptLog;
import com.bigao.backend.log.RoleTaskFinishLog;
import com.bigao.backend.module.task.config.Q_chapter_taskBean;
import com.bigao.backend.module.task.dto.TaskInfo;
import com.bigao.backend.module.task.vo.TaskCount;
import com.bigao.backend.util.HttpUtil;
import com.bigao.backend.util.NumberUtil;
import com.bigao.backend.util.SqlUtil;
import com.google.common.collect.Lists;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by wait on 2015/12/14.
 */
@Service
public class TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskService.class);

    private TreeMap<Integer, Q_chapter_taskBean> taskConfig = new TreeMap<>();

    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;
    @Autowired
    private GameConfigService gameConfigService;

    @PostConstruct
    public void initConfig() {
//        try {
//            List<Q_chapter_taskBean> taskBeen = (List<Q_chapter_taskBean>) gameConfigService.read(HttpUtil.get50InputStream("q_chapter_task.txt"), Q_chapter_taskBean.class);
//            for (Q_chapter_taskBean tBean : taskBeen) {
//                taskConfig.put(tBean.getQ_id(), tBean);
//            }
//        } catch (IOException | IllegalAccessException | InstantiationException e) {
//            logger.error(e.getMessage(), e);
//        }
    }

    public List<TaskInfo> queryTask(int platform, int server, String startDate, String endDate) throws SQLException {
        String querySql = "select taskId,count(taskId) as num from {0} where " + SqlUtil.formatDate(startDate, endDate) + " group by taskId";
        List<List<TaskCount>> acceptData = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleTaskAcceptLog.class, startDate, endDate, new BeanListHandler<>(TaskCount.class)));
        List<List<TaskCount>> doneData = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleTaskFinishLog.class, startDate, endDate, new BeanListHandler<>(TaskCount.class)));
        Map<Integer, Integer> acceptNum = new HashMap<>();
        Map<Integer, Integer> finishNum = new HashMap<>();
        if (!acceptData.isEmpty()) {
            for (List<TaskCount> taskCounts : acceptData) {
                for (TaskCount t : taskCounts) {
                    acceptNum.merge(t.getTaskId(), t.getNum(), Integer::sum);
                }
            }
        }
        if (!doneData.isEmpty()) {
            for (List<TaskCount> taskCounts : doneData) {
                for (TaskCount t : taskCounts) {
                    finishNum.merge(t.getTaskId(), t.getNum(), Integer::sum);
                }
            }
        }
        List<TaskInfo> result = Lists.newArrayList();
        // 加入新手剧情任务统计数据
        querySql = "select node as taskId,count(taskId) as num from {0} where " + SqlUtil.formatDate(startDate, endDate) + " group by node";
        List<List<TaskCount>> newerFinish = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleClientNodeLog.class, startDate, endDate, new BeanListHandler<>(TaskCount.class)));
        List<TaskInfo> newerTaskResult = Lists.newArrayList();
        if (!newerFinish.isEmpty()) {
            for (List<TaskCount> taskCounts : newerFinish) {
                for (TaskCount t : taskCounts) {
                    TaskInfo info = new TaskInfo();
                    info.setTaskId(t.getTaskId());
                    info.setAcceptNum(0);
                    info.setFinishNum(t.getNum());
                    info.setTaskName("新手剧情");
                    info.setFinishRate(StringUtils.EMPTY);
                    newerTaskResult.add(info);
                }
            }
        }
        for (Map.Entry<Integer, Integer> e : acceptNum.entrySet()) {
            if (e.getKey() == 0) {
                continue;
            }
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskId(e.getKey());
            taskInfo.setAcceptNum(e.getValue());
            if (taskConfig.containsKey(e.getKey())) {
                taskInfo.setTaskName(taskConfig.get(e.getKey()).getQ_name());
            } else {
                taskInfo.setTaskName("unknown");
            }
            taskInfo.setFinishNum(finishNum.getOrDefault(e.getKey(), 0));
            taskInfo.setFinishRate(NumberUtil.divide(taskInfo.getFinishNum(), taskInfo.getAcceptNum()));
            result.add(taskInfo);
        }
        if (result.size() > 1) {
            Collections.sort(result, (o1, o2) -> o1.getTaskId() - o2.getTaskId());
        }
        if (newerTaskResult.size() > 1) {
            Collections.sort(newerTaskResult, (o1, o2) -> o1.getTaskId() - o2.getTaskId());
            result.addAll(0, newerTaskResult);
        }
        return result;
    }
}
