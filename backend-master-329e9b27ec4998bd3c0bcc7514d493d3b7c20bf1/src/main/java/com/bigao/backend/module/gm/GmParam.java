package com.bigao.backend.module.gm;

import org.apache.http.NameValuePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wait on 2016/1/4.
 */
public class GmParam {
    private String actor;
    private String ip;
    private Map<String, String> param = new HashMap<>();

    public static GmParam valueOf(String actor, String ip, List<NameValuePair> valuePair) {
        GmParam param = new GmParam();
        param.actor = actor;
        param.ip = ip;
        for (NameValuePair v : valuePair) {
            param.put(v.getName(), v.getValue());
        }
        return param;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void put(String key, String value) {
        param.put(key, value);
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "GmParam{" +
                "actor='" + actor + '\'' +
                ", ip='" + ip + '\'' +
                ", param=" + param +
                '}';
    }
}
