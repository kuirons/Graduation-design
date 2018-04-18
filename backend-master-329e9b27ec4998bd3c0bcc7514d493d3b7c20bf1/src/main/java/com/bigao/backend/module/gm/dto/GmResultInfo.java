package com.bigao.backend.module.gm.dto;

/**
 * Created by wait on 2015/12/26.
 */
public class GmResultInfo {
    private String serverName;
    private String result;

    private transient boolean isFail ;
    private transient String command;

    public GmResultInfo valueOf(String serverName, String result) {
        GmResultInfo info = new GmResultInfo();
        info.serverName = serverName;
        info.result = result;
        return info;
    }

    public String getServerName() {
        return serverName;
    }

    public GmResultInfo setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getResult() {
        return result;
    }

    public GmResultInfo setResult(String result) {
        this.result = result;
        return this;
    }

    public boolean isFail() {
        return isFail;
    }

    public GmResultInfo setFail(boolean fail) {
        isFail = fail;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public GmResultInfo setCommand(String command) {
        this.command = command;
        return this;
    }
}
