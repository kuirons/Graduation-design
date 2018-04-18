package com.bigao.backend.common;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by wait on 2015/11/28.
 */
public class PlatformServer {
    private int platform;
    private List<Integer> server = Lists.newArrayList();

    public static PlatformServer valueOf(int platform) {
        PlatformServer platformServer = new PlatformServer();
        platformServer.platform = platform;
        return platformServer;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public List<Integer> getServer() {
        return server;
    }

    public void setServer(List<Integer> server) {
        this.server = server;
    }
}
