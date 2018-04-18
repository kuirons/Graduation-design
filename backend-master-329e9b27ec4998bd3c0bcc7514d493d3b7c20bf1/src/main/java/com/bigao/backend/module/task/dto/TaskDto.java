package com.bigao.backend.module.task.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/14.
 */
public class TaskDto extends CommonDto {
    /** 注册用户数 */
    private int registerNum;
    /** 创建角色数 */
    private int createRoleNum;
    /** 任务接取情况 */
    private List<TaskInfo> taskInfo;

    public List<TaskInfo> getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(List<TaskInfo> taskInfo) {
        this.taskInfo = taskInfo;
    }

    public int getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }

    public int getCreateRoleNum() {
        return createRoleNum;
    }

    public void setCreateRoleNum(int createRoleNum) {
        this.createRoleNum = createRoleNum;
    }
}
