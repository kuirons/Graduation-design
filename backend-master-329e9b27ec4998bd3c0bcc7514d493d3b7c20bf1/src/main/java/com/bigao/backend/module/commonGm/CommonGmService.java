package com.bigao.backend.module.commonGm;

import com.alibaba.fastjson.JSON;
import com.bigao.backend.common.CommonResult;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.db.DBServer;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.mapper.GmCommandMapper;
import com.bigao.backend.module.commonGm.dto.OneMailDto;
import com.bigao.backend.module.gm.AsyncKey;
import com.bigao.backend.module.gm.GmErrorKey;
import com.bigao.backend.module.gm.GmParam;
import com.bigao.backend.module.gm.GmService;
import com.bigao.backend.module.gm.dto.GmCommandDto;
import com.bigao.backend.module.gm.dto.GmResultDto;
import com.bigao.backend.module.gm.dto.GmStateDto;
import com.bigao.backend.module.sys.model.GmCommand;
import com.bigao.backend.util.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by wait on 2015/12/31.
 */
@Service
public class CommonGmService {

    private Logger logger = LoggerFactory.getLogger(CommonGmService.class);

    @Autowired
    private GmService gmService;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;
    @Autowired
    private GmCommandMapper gmCommandMapper;

    public GmCommandDto sendNote(HttpSession session, String ip, AsyncKey asyncKey, String startTime, int times, int interval, String content, String server) {
        try {
            if (StringUtils.isNotBlank(gmService.getSign(asyncKey)) && !gmService.isFinish(asyncKey)) {
                return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.ACTION_ING), asyncKey);
            }
            ServerParse parse = CommonUtils.validateServer(server);
            if (parse.isFail()) {
                return GmCommandDto.err(SystemConfig.getLang(parse.getMessageKey()), asyncKey);
            }
            long startMills = 0;
            if (!startTime.equals("0")) {
                LocalDateTime nowDateTime = DateUtil.toLocalDateTimeByTime(startTime);
                if (nowDateTime == null) {
                    return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.UNKNOWN), asyncKey);
                }
                startMills = DateUtil.toMill(nowDateTime);
            }
            List<NameValuePair> nameValuePair = Lists.newArrayList();
            nameValuePair.add(NVPUtil.build("noteType", "1"));
            nameValuePair.add(NVPUtil.build("startTime", String.valueOf(startMills)));
            nameValuePair.add(NVPUtil.build("times", String.valueOf(times)));
            nameValuePair.add(NVPUtil.build("interval", String.valueOf(interval)));
            nameValuePair.add(NVPUtil.build("content", content));
            final List<NameValuePair> finalNameValuePair = nameValuePair;
            Map<Integer, List<Integer>> allServer = parse.getServer();
            for (Map.Entry<Integer, List<Integer>> e : allServer.entrySet()) {
                for (Integer serverId : e.getValue()) {
                    gmService.addServer(asyncKey, e.getKey(), serverId);
                    taskExecutor.execute(() -> gmService.actionGmCommand(asyncKey, ip, e.getKey(), serverId, "gmNote", session.getAttribute("username"), finalNameValuePair));
                }
            }
            gmService.setSign(asyncKey, CommonUtils.randString());
            return GmCommandDto.succ(gmService.getSign(asyncKey), asyncKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.UNKNOWN), asyncKey);
        }
    }

    public GmCommandDto sendMail(String actor, String ip, AsyncKey asyncKey, String command, String mailContent, String mailReward, String server) {
        try {
            if (StringUtils.isNotBlank(gmService.getSign(asyncKey)) && !gmService.isFinish(asyncKey)) {
                return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.ACTION_ING), asyncKey);
            }
            ServerParse parse = CommonUtils.validateServer(server);
            if (parse.isFail()) {
                return GmCommandDto.err(SystemConfig.getLang(parse.getMessageKey()), asyncKey);
            }
            List<NameValuePair> nameValuePair = Lists.newArrayList();
            nameValuePair.add(NVPUtil.build("content", mailContent));
            nameValuePair.add(NVPUtil.build("reward", mailReward));
            if (asyncKey == AsyncKey.SUPER_GM_MAIL) {
                nameValuePair.add(NVPUtil.build("gold", "0"));
            }
            final List<NameValuePair> finalNameValuePair = nameValuePair;
            Map<Integer, List<Integer>> allServer = parse.getServer();
            for (Map.Entry<Integer, List<Integer>> e : allServer.entrySet()) {
                for (Integer serverId : e.getValue()) {
                    gmService.addServer(asyncKey, e.getKey(), serverId);
                    taskExecutor.execute(() -> gmService.actionGmCommand(asyncKey, ip, e.getKey(), serverId, command, actor, finalNameValuePair));
                }
            }
            gmService.setSign(asyncKey, CommonUtils.randString());
            return GmCommandDto.succ(gmService.getSign(asyncKey), asyncKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.UNKNOWN), asyncKey);
        }
    }

    public GmStateDto actionState(String batchSign, int asyncIntKey) throws ExecutionException {
        AsyncKey asyncKey = AsyncKey.valueOf(asyncIntKey);
        if (asyncKey == null) {
            return GmStateDto.err("100%");
        }

        if (!gmService.isFinish(asyncKey) && (StringUtils.isBlank(gmService.getSign(asyncKey)) || !batchSign.equals(gmService.getSign(asyncKey)))) {
            return GmStateDto.err("100%");
        }
        if (gmService.isFinish(asyncKey)) {
            return GmStateDto.succ("100%");
        }
        return GmStateDto.succ(gmService.getPercent(asyncKey));
    }

    public GmResultDto actionResult(String batchSign, int asyncIntKey) throws ExecutionException {
        AsyncKey asyncKey = AsyncKey.valueOf(asyncIntKey);
        if (asyncKey == null) {
            return GmResultDto.valueOf().setResult(Boolean.FALSE.toString());
        }
        if (StringUtils.isBlank(gmService.getSign(asyncKey)) || !StringUtils.equals(batchSign, gmService.getSign(asyncKey))) {
            return GmResultDto.valueOf().setResult(Boolean.FALSE.toString());
        }
        return GmResultDto.build(gmService.getGmResult(asyncKey));
    }

    public OneMailDto sendOneMail(String actor, String ip, int platform, int server, String command, List<NameValuePair> valuePair) throws Exception {
        DBServer dbServer = dbUtil4ServerLog.getDBServer(platform, server);
        String resultString = HttpClientUtil.get(dbServer.getBackendUrl(command), valuePair);
        logger.info(resultString);
        CommonResult result = JSON.parseObject(resultString, CommonResult.class);
        taskExecutor.execute(() -> {
            try {
                GmCommand gmCommand = new GmCommand();
                gmCommand.setIp(ip);
                gmCommand.setPlatformId(platform);
                gmCommand.setServerId(server);
                gmCommand.setCommand(command);
                gmCommand.setSendTime(System.currentTimeMillis());
                gmCommand.setSendUser(actor);
                gmCommand.setResult(resultString);
                gmCommand.setAsyncKey(-1);
                GmParam gmParam = GmParam.valueOf(actor, ip, valuePair);
                gmCommand.setParam(gmParam.toString());
                gmCommandMapper.insertLog(gmCommand);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        if (result.getResult() == CommonResult.SUCC) {
            return OneMailDto.succ(result.getContent());
        }
        return OneMailDto.err(result.getContent());
    }
}