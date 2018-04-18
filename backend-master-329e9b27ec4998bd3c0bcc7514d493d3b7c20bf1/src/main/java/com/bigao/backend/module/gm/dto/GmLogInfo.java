package com.bigao.backend.module.gm.dto;

/**
 * Created by wait on 2015/12/26.
 */
public class GmLogInfo {
    private String platformServer;
    private String sendUser;
    private String sendDate;
    private String command;
    private String result;

    public String getPlatformServer() {
        return platformServer;
    }

    public GmLogInfo setPlatformServer(String platformServer) {
        this.platformServer = platformServer;
        return this;
    }

    public String getSendUser() {
        return sendUser;
    }

    public GmLogInfo setSendUser(String sendUser) {
        this.sendUser = sendUser;
        return this;
    }

    public String getSendDate() {
        return sendDate;
    }

    public GmLogInfo setSendDate(String sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public GmLogInfo setCommand(String command) {
        this.command = command;
        return this;
    }

    public String getResult() {
        return result;
    }

    public GmLogInfo setResult(String result) {
        this.result = result;
        return this;
    }
}
