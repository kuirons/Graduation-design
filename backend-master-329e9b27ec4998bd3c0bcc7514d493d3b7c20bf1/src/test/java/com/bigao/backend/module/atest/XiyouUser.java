package com.bigao.backend.module.atest;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wait on 2016/4/6.
 */
public class XiyouUser {
    private String userName;
    private String pass;
    private Set<Integer> auth = new HashSet<>();

    public static XiyouUser valueOf(String userName, String pass) {
        XiyouUser user = new XiyouUser();
        user.userName = userName;
        user.pass = pass;
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    public Set<Integer> getAuth() {
        return auth;
    }
}
