package com.bigao.backend.module.consume.dto;

import com.bigao.backend.module.common.PlatformServerInfo;

/**
 * Created by wait on 2015/12/13.
 */
public class ConsumeNumInfo extends PlatformServerInfo {
    private int consumeNum;

    public ConsumeNumInfo(int platform, int server, int consumeNum) {
        this.platform = platform;
        this.server = server;
        this.consumeNum = consumeNum;
    }

    public int getPlatform() {
        return platform;
    }

    public int getConsumeNum() {
        return consumeNum;
    }

    public void setConsumeNum(int consumeNum) {
        this.consumeNum = consumeNum;
    }
}
