package com.bigao.backend.module.gm;

import com.bigao.backend.common.PlatformKey;
import com.bigao.backend.module.gm.dto.GmResultInfo;
import com.bigao.backend.util.NumberUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by wait on 2015/12/31.
 */
public class AsyncResult {
    private volatile String sign = "";
    /** 最终执行结果 */
    private ConcurrentMap<PlatformKey, GmResultInfo> gmResult = new ConcurrentHashMap<>();
    /** 执行中的结果 */
    private ConcurrentMap<PlatformKey, Boolean> actionMap = new ConcurrentHashMap<>();
    /** 是否已经完成 */
    private volatile boolean isFinish = true;

    public String getPercent() {
        int allSize = actionMap.size();
        int count = (int) actionMap.values().stream().filter(c -> c).count();
        return NumberUtil.divideToIntPercent(count, allSize);
    }

    public void clearServer() {
        actionMap.clear();
        isFinish = false;
    }

    public void add(PlatformKey key, GmResultInfo info, Object username) {
        actionMap.put(key, true);
        gmResult.put(key, info);

        if (actionMap.values().stream().allMatch(c -> c)) {
            actionMap.clear();
            isFinish = true;
        }
    }

    public void addServer(int platform, int server) {
        actionMap.put(PlatformKey.valueOf(platform, server), false);
    }

    // getter and setter.....
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public ConcurrentMap<PlatformKey, GmResultInfo> getGmResult() {
        return gmResult;
    }

    public void setGmResult(ConcurrentMap<PlatformKey, GmResultInfo> gmResult) {
        this.gmResult = gmResult;
    }

    public ConcurrentMap<PlatformKey, Boolean> getActionMap() {
        return actionMap;
    }

    public void setActionMap(ConcurrentMap<PlatformKey, Boolean> actionMap) {
        this.actionMap = actionMap;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
