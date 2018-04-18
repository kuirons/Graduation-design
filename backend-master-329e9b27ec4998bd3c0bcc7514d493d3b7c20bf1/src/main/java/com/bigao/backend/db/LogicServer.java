package com.bigao.backend.db;

/**
 * 物理服
 * Created by wait on 2015/12/29.
 */
public class LogicServer {
    private String platform;
    private int logic_server;
    private int server;
    private transient String name;

    public static LogicServer valueOf(String name, String platform, int logic_server, int serverId) {
        LogicServer server = new LogicServer();
        server.name = name;
        server.platform = platform;
        server.logic_server = logic_server;
        server.server = serverId;
        return server;
    }

    public boolean canLogin() {
        return logic_server != Server.CROSS_LOGIC_SERVER_ID && logic_server != Server.LEADER_LOGIC_SERVER_ID;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getLogic_server() {
        return logic_server;
    }

    public void setLogic_server(int logic_server) {
        this.logic_server = logic_server;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogicServer{");
        sb.append("platform=").append(platform);
        sb.append(", logic_server=").append(logic_server);
        sb.append(", server=").append(server);
        sb.append(", name=").append(name);
        sb.append('}');
        return sb.toString();
    }
}
