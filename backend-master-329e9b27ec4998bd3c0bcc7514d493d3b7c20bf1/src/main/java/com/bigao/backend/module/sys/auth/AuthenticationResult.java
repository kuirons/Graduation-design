package com.bigao.backend.module.sys.auth;

import java.util.List;
import java.util.Map;

public class AuthenticationResult {

    //-99:非法请求，-1:用户名或密码错误，0：没有权限，1：成功
    private Integer code;

    private String msg;

    private List<String> permissionList;

    private Map<String, List<String>> urlPermissionMap;

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    public Map<String, List<String>> getUrlPermissionMap() {
        return urlPermissionMap;
    }

    public void setUrlPermissionMap(Map<String, List<String>> urlPermissionMap) {
        this.urlPermissionMap = urlPermissionMap;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
