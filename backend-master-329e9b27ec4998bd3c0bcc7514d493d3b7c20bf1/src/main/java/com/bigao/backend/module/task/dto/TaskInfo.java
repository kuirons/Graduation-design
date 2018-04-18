package com.bigao.backend.module.task.dto;

/**
 * Created by wait on 2015/12/14.
 */
public class TaskInfo {
    /** 任务ID */
    private int taskId;
    /** 任务名字 */
    private String taskName;
    /** 任务接取人数 */
    private int acceptNum;
    /** 任务完成人数 */
    private int finishNum;
    /** 任务完成率 */
    private String finishRate;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getAcceptNum() {
        return acceptNum;
    }

    public void setAcceptNum(int acceptNum) {
        this.acceptNum = acceptNum;
    }

    public int getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(int finishNum) {
        this.finishNum = finishNum;
    }

    public String getFinishRate() {
        return finishRate;
    }

    public void setFinishRate(String finishRate) {
        this.finishRate = finishRate;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", acceptNum=" + acceptNum +
                ", finishNum=" + finishNum +
                ", finishRate='" + finishRate + '\'' +
                '}';
    }
}
