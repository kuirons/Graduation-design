package com.bigao.backend.module.player.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/11/30.
 */
public class RoleLevelDto extends CommonDto {
    private List<RoleLevelInfo> resultList;
    private long roleId;
    private String userName;
    private String roleName;

    public static RoleLevelDto valueOf(List<RoleLevelInfo> resultList, long roleId, String userName, String roleName) {
        RoleLevelDto player = new RoleLevelDto();
        player.resultList = resultList;
        player.roleId = roleId;
        player.userName = userName;
        player.roleName = roleName;
        return player;
    }

    public List<RoleLevelInfo> getResultList() {
        return resultList;
    }

    public RoleLevelDto setResultList(List<RoleLevelInfo> resultList) {
        this.resultList = resultList;
        return this;
    }

    public long getRoleId() {
        return roleId;
    }

    public RoleLevelDto setRoleId(long roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RoleLevelDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public RoleLevelDto setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }
}
