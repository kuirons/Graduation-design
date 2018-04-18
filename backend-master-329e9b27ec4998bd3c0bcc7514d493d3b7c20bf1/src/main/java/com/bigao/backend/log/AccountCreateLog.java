package com.bigao.backend.log;

import com.bigao.backend.common.anno.LogType;
import com.bigao.backend.common.anno.TableDesc;

/**
 * 玩家创建账号的日志
 * Created by wait on 2015/11/30.
 */
@TableDesc(value = "accountcreatelog", logType = LogType.YEAR)
public class AccountCreateLog {

    private int platform;
    private int server;
    private long id;
    private String account;
    private long time;

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AccountCreateLog{" +
                "platform=" + platform +
                ", server=" + server +
                ", id=" + id +
                ", account='" + account + '\'' +
                ", time=" + time +
                '}';
    }
}
