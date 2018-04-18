package com.bigao.backend.module.common;

/**
 * 当需要平台和服务器属性时, 可以继承此类, 可以少写几行代码, 此外, 最重要的是, 这个类有一个getPlatformServer, 可以把platform和server合并显示
 * Created by wait on 2015/12/14.
 */
public class PlatformServerInfo {
    protected int platform;
    protected int server;

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

    /**
     * 合并platform和server, 由于客户端的数据展示表格比较窄, 所以这里多余get,set用法用于把platform和server放在一列显示,
     * 这样也比较清晰
     *
     * @return
     */
    public String getPlatformServer() {
        return desc(platform, server);
    }

    public void setPlatformServer(String platformServer) {

    }

    public static String desc(int platform, int server) {
        return "[" + platform + ", " + server + "]";
    }
}
