package com.bigao.backend.module.zone.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/1.
 */
public class CopyDto extends CommonDto {
    /** 注册用户数 */
    private int registerNum;
    /*** 创建角色数 */
    private int createRoleNum;
    /** 副本数据集合 */
    private List<CopyInfo> copyInfo;

    public int getRegisterNum() {
        return registerNum;
    }

    public CopyDto setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
        return this;
    }

    public int getCreateRoleNum() {
        return createRoleNum;
    }

    public CopyDto setCreateRoleNum(int createRoleNum) {
        this.createRoleNum = createRoleNum;
        return this;
    }

    public List<CopyInfo> getCopyInfo() {
        return copyInfo;
    }

    public CopyDto setCopyInfo(List<CopyInfo> copyInfo) {
        this.copyInfo = copyInfo;
        return this;
    }
}
