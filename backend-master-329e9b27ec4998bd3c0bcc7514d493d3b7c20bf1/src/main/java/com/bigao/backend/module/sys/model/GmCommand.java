package com.bigao.backend.module.sys.model;

import com.bigao.backend.module.common.PlatformServerInfo;
import com.bigao.backend.module.gm.dto.GmLogInfo;
import com.bigao.backend.util.DateUtil;

import java.sql.Timestamp;

/**
 * Created by wait on 2015/12/26.
 */
public class GmCommand {
    private int id;
    private String ip;
    private int platformId;
    private int serverId;
    private int asyncKey;
    private String command;
    private long sendTime;
    private String sendUser;
    private String result;
    private String param;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getAsyncKey() {
        return asyncKey;
    }

    public void setAsyncKey(int asyncKey) {
        this.asyncKey = asyncKey;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "GmCommand{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", platformId=" + platformId +
                ", serverId=" + serverId +
                ", asyncKey=" + asyncKey +
                ", command='" + command + '\'' +
                ", sendTime=" + sendTime +
                ", sendUser='" + sendUser + '\'' +
                ", result='" + result + '\'' +
                ", param='" + param + '\'' +
                '}';
    }

    public GmLogInfo toInfo() {
        GmLogInfo info = new GmLogInfo();
        info.setPlatformServer(PlatformServerInfo.desc(platformId, serverId));
        info.setSendUser(sendUser);
        info.setSendDate(DateUtil.timeStampToString(new Timestamp(sendTime)));
        info.setCommand(command);
        info.setResult(result);
        return info;
    }
}
