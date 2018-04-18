package com.bigao.backend.module.player;

import com.bigao.backend.log.AccountCreateLog;
import com.bigao.backend.log.RoleCreateLog;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by wait on 2015/11/30.
 */
public class LogRole {
    private AccountCreateLog accountCreateLog;
    private RoleCreateLog roleCreateLog;
    private int platform;
    private int server;

    public static LogRole valueOf(AccountCreateLog accountCreateLog, RoleCreateLog roleCreateLog) {
        LogRole dbRole = new LogRole();
        dbRole.accountCreateLog = accountCreateLog;
        dbRole.roleCreateLog = roleCreateLog;
        return dbRole;
    }

    public AccountCreateLog getAccountCreateLog() {
        return accountCreateLog;
    }

    public void setAccountCreateLog(AccountCreateLog accountCreateLog) {
        this.accountCreateLog = accountCreateLog;
    }

    public RoleCreateLog getRoleCreateLog() {
        return roleCreateLog;
    }

    public void setRoleCreateLog(RoleCreateLog roleCreateLog) {
        this.roleCreateLog = roleCreateLog;
    }

    public long getRoleId() {
        if (accountCreateLog != null) {
            return accountCreateLog.getId();
        }
        return -1;
    }

    public String getRoleName() {
        if (roleCreateLog != null) {
            return roleCreateLog.getRoleName();
        }
        return StringUtils.EMPTY;
    }

    public String getAccount() {
        if (accountCreateLog != null) {
            return accountCreateLog.getAccount();
        }
        return StringUtils.EMPTY;
    }

    public int getPlatform() {
        return platform;
    }

    public LogRole setPlatform(int platform) {
        this.platform = platform;
        return this;
    }

    public int getServer() {
        return server;
    }

    public LogRole setServer(int server) {
        this.server = server;
        return this;
    }

    @Override
    public String toString() {
        return "LogRole{" +
                "accountCreateLog=" + accountCreateLog +
                ", roleCreateLog=" + roleCreateLog +
                '}';
    }
}
