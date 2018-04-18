package com.bigao.backend.db;

import java.io.Serializable;


public class Server implements Serializable {

    private static final long serialVersionUID = 6146162648542197624L;

    public static final int CROSS_LOGIC_SERVER_ID = 600;
    public static final int CROSS_SERVER_ID = 333;
    public static final int CROSS_TEMPLATE_ID = -1;

    public static final int LEADER_LOGIC_SERVER_ID = 601;
    public static final int LEADER_SERVER_ID = 335;

    public static String LEADER_DIR = "/usr/local/games/leader";
    public static String CROSS_DIR = "/usr/local/games/share";

    private int id;
    private transient String type;
    private int game_port;
    private int flash_port;
    private int backend_port;
    private String db_log_url;
    private String db_game_url;
    private String open_date;
    private String hefu_date;
    private String ip;
    private String path;
    private int template;
    private int serverId;
    private int platformId;
    private String platformName;
    private String serverName = "game1"; //TODO 测试

    public static Server valueOf(int serverId, int platformId) {
        Server server = new Server();
        server.id = serverId;
        server.platformId = platformId;
        return server;
    }

    public static Server crossServer() {
        Server s = Server.valueOf(CROSS_SERVER_ID, 8500, "SHARE", 8501, 8502, "localhost", 1,
                CROSS_DIR, "jdbc:mysql://localhost:3306/game_log_share?autoReconnect=true&amp;characterEncoding=UTF-8",
                "jdbc:mysql://localhost:3306/game_data_share?autoReconnect=true&amp;characterEncoding=UTF-8", "2014-12-23 12:00:00", "2099-12-23 12:00:00");
        s.template = CROSS_TEMPLATE_ID;
        return s;
    }

    public static Server valueOf(int id, int game_port, String type, int flash_port, int backend_port, String ip, int template, String path, String db_log_url, String db_game_url, String open_date, String hefu_date) {
        Server s = new Server();
        s.id = id;
        s.game_port = game_port;
        s.type = type;
        s.flash_port = flash_port;
        s.backend_port = backend_port;
        s.ip = ip;
        s.template = template;
        s.path = path;
        s.db_log_url = db_log_url;
        s.db_game_url = db_game_url;
        s.open_date = open_date;
        s.hefu_date = hefu_date;
        return s;
    }

    public int getGame_port() {
        return game_port;
    }

    public void setGame_port(int game_port) {
        this.game_port = game_port;
    }

    public int getFlash_port() {
        return flash_port;
    }

    public void setFlash_port(int flash_port) {
        this.flash_port = flash_port;
    }

    public int getBackend_port() {
        return backend_port;
    }

    public void setBackend_port(int backend_port) {
        this.backend_port = backend_port;
    }

    public String getDb_log_url() {
        return db_log_url;
    }

    public void setDb_log_url(String db_log_url) {
        this.db_log_url = db_log_url;
    }

    public String getDb_game_url() {
        return db_game_url;
    }

    public void setDb_game_url(String db_game_url) {
        this.db_game_url = db_game_url;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getHefu_date() {
        return hefu_date;
    }

    public void setHefu_date(String hefu_date) {
        this.hefu_date = hefu_date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platform) {
        this.platformName = platform;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Server{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", game_port=").append(game_port);
        sb.append(", flash_port=").append(flash_port);
        sb.append(", backend_port=").append(backend_port);
        sb.append(", db_log_url=").append(db_log_url);
        sb.append(", db_game_url=").append(db_game_url);
        sb.append(", open_date=").append(open_date);
        sb.append(", hefu_date=").append(hefu_date);
        sb.append(", ip=").append(ip);
        sb.append(", path=").append(path);
        sb.append(", template=").append(template);
        sb.append(", serverId=").append(serverId);
        sb.append(", platformId=").append(platformId);
        sb.append(", platformName=").append(platformName);
        sb.append(", serverName=").append(serverName);
        sb.append('}');
        return sb.toString();
    }
}
