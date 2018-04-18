package com.bigao.backend.module.sys.model;

public class OperationLog {
    private Integer gameId;
    private String gameName;
    private Integer platformId;
    private String platformAlias;
    private Integer serverId;
    private String serverName;
    private String cmd;
    private String version;
    private Long sendTime;
    private String sendDate;
    private String sendUser;
    private Long callBackTime;
    private Integer result;
    private String batchSign;
    private String sign;
    private String resultString;
    private Integer state;

    public OperationLog() {
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getPlatformAlias() {
        return platformAlias;
    }

    public void setPlatformAlias(String platformAlias) {
        this.platformAlias = platformAlias;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public Long getCallBackTime() {
        return callBackTime;
    }

    public void setCallBackTime(Long callBackTime) {
        this.callBackTime = callBackTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getBatchSign() {
        return batchSign;
    }

    public void setBatchSign(String batchSign) {
        this.batchSign = batchSign;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
