package com.bigao.backend.module.gm;

import com.alibaba.fastjson.JSON;
import com.bigao.backend.common.CommonResult;
import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.PlatformKey;
import com.bigao.backend.db.DBServer;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.db.Server;
import com.bigao.backend.mapper.GmCommandMapper;
import com.bigao.backend.module.common.PlatformServerInfo;
import com.bigao.backend.module.gm.dto.GmResultInfo;
import com.bigao.backend.module.sys.model.GmCommand;
import com.bigao.backend.util.HttpClientUtil;
import com.bigao.backend.util.IpUtil;
import com.bigao.backend.util.NVPUtil;
import com.bigao.backend.util.SystemConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 合服也只会有一个服务器
 * Created by wait on 2015/12/26.
 */
@Service
public class GmService {

    private Logger logger = LoggerFactory.getLogger(GmService.class);

    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;
    @Autowired
    GmCommandMapper gmCommandMapper;
    @Autowired
    private TaskExecutor taskExecutor;

    private ConcurrentMap<AsyncKey, AsyncResult> asyncResultMap = new ConcurrentHashMap<>();
    private Cache<AsyncKey, AsyncResult> resultCache = CacheBuilder.newBuilder()
            .expireAfterWrite(20, TimeUnit.SECONDS)
            .removalListener(new RemovalListener<AsyncKey, AsyncResult>() {
                @Override
                public void onRemoval(RemovalNotification<AsyncKey, AsyncResult> notification) {
                    logger.info("remove, [{},{}]", notification.getKey(), notification.getValue());
                }
            })
            .build();

    public String getPercent(AsyncKey asyncKey) {
        AsyncResult result = resultCache.getIfPresent(asyncKey);
        if (result != null) {
            return "100%";
        }
        if (asyncResultMap.containsKey(asyncKey)) {
            return asyncResultMap.get(asyncKey).getPercent();
        }
        return "100%";
    }

    private void add(AsyncKey asyncKey, PlatformKey key, GmResultInfo info, GmParam gmParam) {
        if (asyncResultMap.containsKey(asyncKey)) {
            AsyncResult result = asyncResultMap.get(asyncKey);
            result.add(key, info, gmParam.getActor());
            if (result.isFinish()) {
                resultCache.put(asyncKey, result);
                asyncResultMap.remove(asyncKey);
            }
        }
        taskExecutor.execute(() -> {
            try {
                GmCommand gmCommand = new GmCommand();
                gmCommand.setIp(gmParam.getIp());
                gmCommand.setPlatformId(key.getPlatformId());
                gmCommand.setServerId(key.getServerId());
                gmCommand.setCommand(info.getCommand());
                gmCommand.setSendTime(System.currentTimeMillis());
                gmCommand.setSendUser(gmParam.getActor());
                gmCommand.setResult(info.getResult());
                gmCommand.setParam(gmParam.toString());
                gmCommand.setAsyncKey(asyncKey.getKey());
                gmCommandMapper.insertLog(gmCommand);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    public void handle(HttpSession session, HttpServletRequest request, String commandString, Map<Integer, List<Integer>> allServer, AsyncKey asyncKey, int command, String gmParam) {
        for (Map.Entry<Integer, List<Integer>> e : allServer.entrySet()) {
            for (Integer serverId : e.getValue()) {
                addServer(asyncKey, e.getKey(), serverId);
                List<NameValuePair> nameValuePair = NVPUtil.EMPTY_PAIR;
                if (command == 3) {
                    nameValuePair = Lists.newArrayList();
                    nameValuePair.add(NVPUtil.build("platform", e.getKey()));
                    nameValuePair.add(NVPUtil.build("server", serverId));
                    nameValuePair.add(NVPUtil.build("usr", gmParam));
                }
                final List<NameValuePair> finalNameValuePair = nameValuePair;
                String ip = IpUtil.remoteIp(request);
                taskExecutor.execute(() -> actionGmCommand(asyncKey, ip, e.getKey(), serverId, commandString, session.getAttribute("username"), finalNameValuePair));
            }
        }
    }

    public void actionGmCommand(AsyncKey asyncKey, String ip, int platform, int server, String command, Object username, List<NameValuePair> nameValuePair) {
        if (!asyncResultMap.containsKey(asyncKey)) {
            asyncResultMap.put(asyncKey, new AsyncResult());
        }
        GmParam gmParam = GmParam.valueOf(username.toString(), ip, nameValuePair);
        GmResultInfo info = new GmResultInfo();
        info.setCommand(command);
        info.setServerName(PlatformServerInfo.desc(platform, server));
        PlatformKey key = PlatformKey.valueOf(platform, server);
        try {
            DBServer dbServer = dbUtil4ServerLog.getDBServer(platform, server);
            if (dbServer == null) {
                info.setResult(SystemConfig.getLang(GmErrorKey.SEVER_NOT_EXIST));
                info.setFail(true);
                add(asyncKey, key, info, gmParam);
                return;
            }
            Server phyServer = dbServer.getServer();
            String resultString = HttpClientUtil.get("http://" + phyServer.getIp() + ":" + phyServer.getBackend_port() + "/" + command, nameValuePair);
            logger.info(resultString);
            CommonResult result = JSON.parseObject(resultString, CommonResult.class);
            info.setResult(result.getContent());
            if (result.getResult() == -1) {
                info.setFail(true);
            }
            add(asyncKey, key, info, gmParam);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            info.setResult(e.getMessage());
            info.setFail(true);
            add(asyncKey, key, info, gmParam);
        }
    }

    public void addServer(AsyncKey asyncKey, int platform, int server) {
        if (asyncResultMap.containsKey(asyncKey)) {
            asyncResultMap.get(asyncKey).addServer(platform, server);
        }
    }

    public void setSign(AsyncKey asyncKey, String sign) {
        if (!asyncResultMap.containsKey(asyncKey)) {
            asyncResultMap.put(asyncKey, new AsyncResult());
        }
        asyncResultMap.get(asyncKey).setSign(sign);
    }

    public String getSign(AsyncKey asyncKey) throws ExecutionException {
        AsyncResult asyncResult = resultCache.getIfPresent(asyncKey);
        if (asyncResult != null) {
            return asyncResult.getSign();
        }
        if (this.asyncResultMap.containsKey(asyncKey)) {
            return this.asyncResultMap.get(asyncKey).getSign();
        }
        return null;
    }

    public Map<PlatformKey, GmResultInfo> getGmResult(AsyncKey asyncKey) throws ExecutionException {
        AsyncResult asyncResult = resultCache.getIfPresent(asyncKey);
        if (asyncResult != null) {
            resultCache.invalidate(asyncKey);
            return asyncResult.getGmResult();
        }
        return Collections.emptyMap();
    }

    public boolean isFinish(AsyncKey asyncKey) {
        if (resultCache.getIfPresent(asyncKey) != null) {
            return true;
        }
        if (asyncResultMap.containsKey(asyncKey)) {
            return asyncResultMap.get(asyncKey).isFinish();
        }
        return false;
    }

    public String reloadInner(HttpSession session,
                              HttpServletRequest request,
                              Class clz, String methodName) throws Exception {
        LogUtil.toLog(session, request, clz, methodName);
        return HttpClientUtil.get(SystemConfig.getProperty("inner.reload.host"), null);
    }
}
