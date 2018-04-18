package com.bigao.backend.db;


import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Created by wait on 2015/11/27.
 */
public class DBServer {
    /** 服务器 */
    private Server server;
    /** 数据库ip */
    private String dbIp;
    /** 数据库端口 */
    private int dbPort;
    /** 数据库名字 */
    private String dbName;
    /** 服务器对应的日志数据库连接池 */
    private BasicDataSource dataSource;
    /** 是否属于合服 */
    private boolean isMerge;

    public boolean isMerge() {
        return isMerge;
    }

    public void setMerge(boolean merge) {
        isMerge = merge;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getDbIp() {
        return dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    public int getDbPort() {
        return dbPort;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getBackendUrl(String command) {
        return "http://" + server.getIp() + ":" + server.getBackend_port() + "/" + command;
    }
}
