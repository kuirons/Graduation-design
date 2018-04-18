package com.bigao.backend.common;


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wait on 2015/11/30.
 */
public class ServerParse {
    private String messageKey;
    /** <platform, List<servers>> */
    private Map<Integer, List<Integer>> server;

    public static ServerParse succ(Map<Integer, List<Integer>> server) {
        ServerParse parse = new ServerParse();
        parse.server = server;
        return parse;
    }

    public static ServerParse err(String messageKey) {
        ServerParse parse = new ServerParse();
        parse.messageKey = messageKey;
        return parse;
    }

    /**
     * @return true:失败
     */
    public boolean isFail() {
        return !StringUtils.isBlank(messageKey);
    }

    // logic method...
    public int getServerSize() {
        if (server == null) {
            return 0;
        }
        int size = 0;
        for (Map.Entry<Integer, List<Integer>> e : server.entrySet()) {
            size += e.getValue().size();
        }
        return size;
    }

    public PlatformKey getFirst() {
        if (server == null || server.isEmpty()) {
            return null;
        }
        Map.Entry<Integer, List<Integer>> e = server.entrySet().iterator().next();
        return PlatformKey.valueOf(e.getKey(), e.getValue().get(0));
    }

    // getter and setter
    public ActionMessageResult toActionMessageResult() {
        return ActionMessageResult.err(messageKey);
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public Map<Integer, List<Integer>> getServer() {
        return server;
    }

    public void setServer(Map<Integer, List<Integer>> server) {
        this.server = server;
    }

}
