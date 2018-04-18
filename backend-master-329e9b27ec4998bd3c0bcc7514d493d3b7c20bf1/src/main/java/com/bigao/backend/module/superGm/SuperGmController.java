package com.bigao.backend.module.superGm;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.PlatformKey;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.commonGm.CommonGmService;
import com.bigao.backend.module.commonGm.dto.OneMailDto;
import com.bigao.backend.module.gm.AsyncKey;
import com.bigao.backend.module.gm.dto.GmCommandDto;
import com.bigao.backend.module.gm.dto.GmResultDto;
import com.bigao.backend.module.gm.dto.GmStateDto;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.util.*;
import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.ExecutionException;

/**
 * Created by wait on 2015/12/31.
 */
@Controller
@RequestMapping(value = "superGm")
public class SuperGmController {

    private Logger logger = LoggerFactory.getLogger(SuperGmController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private CommonGmService commonGmService;

    @RequestMapping(value = "superGmQuery")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("superGm/superGm");
        return view;
    }


    @RequestMapping(value = "actionResult", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmResultDto actionResult(@RequestParam(value = "batchSign") String batchSign,
                                    @RequestParam(value = "asyncKey") int asyncIntKey) {
        try {
            return commonGmService.actionResult(batchSign, asyncIntKey);
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            return GmResultDto.valueOf().setResult(Boolean.FALSE.toString());
        }
    }

    @RequestMapping(value = "actionState", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmStateDto actionState(@RequestParam(value = "batchSign") String batchSign,
                                  @RequestParam(value = "asyncKey") int asyncIntKey) {
        try {
            return commonGmService.actionState(batchSign, asyncIntKey);
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            return GmStateDto.err(SystemConfig.getLang(CommonErrorKey.UNKNOWN));
        }
    }

    @RequestMapping(value = "sendMail", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmCommandDto sendMail(HttpSession session,
                                 HttpServletRequest request,
                                 @RequestParam(value = "mailContent") String mailContent,
                                 @RequestParam(value = "mailReward") String mailReward,
                                 @RequestParam(value = "server") String server) {
        LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{mailContent, mailReward, server});
        return commonGmService.sendMail(session.getAttribute("username").toString(), IpUtil.remoteIp(request), AsyncKey.SUPER_GM_MAIL, "gmMailGold", mailContent, mailReward, server);
    }


    @RequestMapping(value = "sendNote", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmCommandDto sendNote(HttpSession session,
                                 HttpServletRequest request,
                                 @RequestParam(value = "startTime") String startTime,
                                 @RequestParam(value = "times") int times,
                                 @RequestParam(value = "interval") int interval,
                                 @RequestParam(value = "noteContent") String content,
                                 @RequestParam(value = "server") String server) {
        LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startTime, times, interval, content, server});
        return commonGmService.sendNote(session, IpUtil.remoteIp(request), AsyncKey.SUPER_GM_MAIL, startTime, times, interval, content, server);
    }

    @RequestMapping(value = "oneMail", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public OneMailDto sendOneMail(HttpSession session,
                                  HttpServletRequest request,
                                  @RequestParam(value = "account") String account,
                                  @RequestParam(value = "mailContent") String mailContent,
                                  @RequestParam(value = "mailReward") String mailReward,
                                  @RequestParam(value = "server") String server) {
        try {
            ServerParse parse = CommonUtils.validateServer(server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(OneMailDto.class, parse.getMessageKey());
            }
            PlatformKey platformKey = parse.getFirst();
            List<NameValuePair> v = Lists.newArrayList();
            v.add(NVPUtil.build("platform", platformKey.getPlatformId()));
            v.add(NVPUtil.build("server", platformKey.getServerId()));
            v.add(NVPUtil.build("usr", account));
            v.add(NVPUtil.build("content", mailContent));
            v.add(NVPUtil.build("reward", mailReward));
            v.add(NVPUtil.build("gold", "0"));
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{account, mailContent, mailReward, server});
            return commonGmService.sendOneMail(session.getAttribute("username").toString(), IpUtil.remoteIp(request), platformKey.getPlatformId(), platformKey.getServerId(), "gmMailToOneWithGold", v);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(OneMailDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}
