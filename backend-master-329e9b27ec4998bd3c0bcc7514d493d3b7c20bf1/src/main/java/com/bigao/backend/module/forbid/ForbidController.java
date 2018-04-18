package com.bigao.backend.module.forbid;

import com.alibaba.fastjson.JSON;
import com.bigao.backend.common.CommonResult;
import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.PlatformKey;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.db.DBServer;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.module.forbid.dto.ForbidInfoDto;
import com.bigao.backend.module.forbid.dto.ForbidResultDto;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.util.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 封号
 * Created by wait on 2016/1/18.
 */
@Controller
@RequestMapping(value = "forbid")
public class ForbidController {

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

    @RequestMapping(value = "forbid")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("forbid/forbid");
        return view;
    }

    @RequestMapping(value = "info", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ForbidInfoDto info(HttpSession session,
                              HttpServletRequest request,
                              @RequestParam(value = "server") String server) {
        try {
            ServerParse serverParse = CommonUtils.validateOneServer(server);
            if (serverParse.isFail()) {
                return CommonUtils.alterMessage(ForbidInfoDto.class, serverParse.getMessageKey());
            }
            DBServer dbServer = dbUtil4ServerLog.getDBServer(serverParse.getFirst());
            if (dbServer == null) {
                return CommonUtils.alterMessage(ForbidInfoDto.class, CommonErrorKey.SEVER_NOT_EXIST);
            }
            String url = dbServer.getBackendUrl("allForbid");
            String resultString = HttpClientUtil.get(url, NVPUtil.EMPTY_PAIR);
            CommonResult commonResult = JSON.parseObject(resultString, CommonResult.class);
            if (commonResult.getResult() == CommonResult.FAIL) {
                return ForbidInfoDto.err(commonResult.getContent());
            }
            List<ForbidInfoBean> forbidInfoBeen = JSON.parseArray(commonResult.getContent(), ForbidInfoBean.class);
            ForbidInfoDto dto = new ForbidInfoDto();
            for (ForbidInfoBean b : forbidInfoBeen) {
                if (b.getForbidType() == ForbidInfoBean.BY_IP) {
                    dto.getForbidIp().add(b.getContent());
                } else if (b.getForbidType() == ForbidInfoBean.BY_ACCOUNT) {
                    dto.getForbidAccount().add(b.getContent());
                }
            }
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{server});

            return dto;
        } catch (Exception e) {
            return CommonUtils.alterMessage(ForbidInfoDto.class, CommonErrorKey.UNKNOWN);
        }
    }

    @RequestMapping(value = "addForbid", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ForbidResultDto addForbid(
            @RequestParam(value = "forbidContent") String forbidContent,
            @RequestParam(value = "forbidType") byte forbidType,
            @RequestParam(value = "server") String server,
            @RequestParam(value = "add") int add) {
        try {
            if (add < 1 || add > 3) {
                return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(CommonErrorKey.UNKNOWN));
            }
            ServerParse serverParse = CommonUtils.validateOneServer(server);
            if (serverParse.isFail()) {
                return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(serverParse.getMessageKey()));
            }
            DBServer dbServer = dbUtil4ServerLog.getDBServer(serverParse.getFirst());
            if (dbServer == null) {
                return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(CommonErrorKey.SEVER_NOT_EXIST));
            }
            String url;
            List<NameValuePair> valuePair = Lists.newArrayList(NVPUtil.build("forbidType", forbidType));
            if (add == 1) {
                if (StringUtils.isBlank(forbidContent)) {
                    return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(CommonErrorKey.ACCOUNT_EMPTY));
                }
                url = dbServer.getBackendUrl("addForbid");
                valuePair.add(NVPUtil.build("content", forbidContent));
            } else if (add == 2) {
                if (StringUtils.isBlank(forbidContent)) {
                    return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(CommonErrorKey.ACCOUNT_EMPTY));
                }
                url = dbServer.getBackendUrl("delForbid");
                valuePair.add(NVPUtil.build("content", forbidContent));
            } else {
                url = dbServer.getBackendUrl("delForbidByType");
            }
            String resultString = HttpClientUtil.get(url, valuePair);
            CommonResult commonResult = JSON.parseObject(resultString, CommonResult.class);
            if (commonResult.getResult() == CommonResult.FAIL) {
                return ForbidResultDto.valueOf(CommonResult.FAIL, commonResult.getContent());
            }
            return ForbidResultDto.valueOf(CommonResult.SUCC, commonResult.getContent());
        } catch (Exception e) {
            return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(CommonErrorKey.UNKNOWN));
        }
    }

    @RequestMapping(value = "kickOff", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ForbidResultDto kickOff(@RequestParam(value = "kickOffAccount") String kickOffAccount,
                                   @RequestParam(value = "server") String server) {
        try {
            ServerParse serverParse = CommonUtils.validateOneServer(server);
            if (serverParse.isFail()) {
                return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(serverParse.getMessageKey()));
            }
            PlatformKey platformKey = serverParse.getFirst();
            DBServer dbServer = dbUtil4ServerLog.getDBServer(serverParse.getFirst());
            if (dbServer == null) {
                return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(CommonErrorKey.SEVER_NOT_EXIST));
            }
            if (StringUtils.isBlank(kickOffAccount)) {
                return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(CommonErrorKey.ACCOUNT_EMPTY));
            }
            String url = dbServer.getBackendUrl("kickOff");
            List<NameValuePair> valuePair = Lists.newArrayList(NVPUtil.build("platform", platformKey.getPlatformId()),
                    NVPUtil.build("server", platformKey.getServerId()),
                    NVPUtil.build("account", kickOffAccount));
            String resultString = HttpClientUtil.get(url, valuePair);
            CommonResult commonResult = JSON.parseObject(resultString, CommonResult.class);
            if (commonResult.getResult() == CommonResult.FAIL) {
                return ForbidResultDto.valueOf(CommonResult.FAIL, commonResult.getContent());
            }
            return ForbidResultDto.valueOf(CommonResult.SUCC, commonResult.getContent());
        } catch (Exception e) {
            return ForbidResultDto.valueOf(CommonResult.FAIL, SystemConfig.getLang(CommonErrorKey.UNKNOWN));
        }
    }
}
