package com.bigao.backend.module.online;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.PlatformKey;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.online.dto.OnlineRealTimeDto;
import com.bigao.backend.module.online.dto.OnlineRealTimeInfo;
import com.bigao.backend.module.online.dto.OnlineTimeDto;
import com.bigao.backend.module.online.dto.OnlineTimeInfo;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonParam;
import com.bigao.backend.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
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

/**
 * 在线
 * Created by wait on 2015/11/27.
 */
@Controller
@RequestMapping(value = "onlineQuery")
public class OnlineController {
    private Logger logger = LoggerFactory.getLogger(OnlineController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private OnlineService onlineService;

    @RequestMapping(value = "onlineQuery")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("online/onlineQuery");
        return view;
    }

    @RequestMapping(value = "realTimeQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public OnlineRealTimeDto realTimeQuery(HttpSession session,
                                           HttpServletRequest request,
                                           @RequestParam(value = "step") int step,
                                           @RequestParam(value = "startTimeInit") String startTimeInit,
                                           @RequestParam(value = "endTime") String endTime,
                                           @RequestParam(value = CommonParam.SERVER_KEY) String server) {
        try {
            ServerParse parse = CommonUtils.validateServer(server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(OnlineRealTimeDto.class, parse.getMessageKey());
            }
            PlatformKey key = parse.getFirst();
            List<OnlineRealTimeInfo> realTimeInfo = onlineService.realTimeCount(key.getPlatformId(), key.getServerId(), step, startTimeInit, endTime);
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{step, startTimeInit, endTime, server});
            return new OnlineRealTimeDto().setRealTimeInfo(realTimeInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(OnlineRealTimeDto.class, OnlineErrorKey.UNKNOWN);
        }
    }

    @RequestMapping(value = "onlineTimeQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public OnlineTimeDto onlineTimeQuery(HttpSession session,
                                         HttpServletRequest request,
                                         @RequestParam(value = "startTime") String startTime,
                                         @RequestParam(value = "endTime") String endTime,
                                         @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return CommonUtils.alterMessage(OnlineTimeDto.class, CommonErrorKey.EMPTY_DATE);
        }
        try {
            ServerParse parse = CommonUtils.validateServer(server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(OnlineTimeDto.class, parse.getMessageKey());
            }
            PlatformKey platformKey = parse.getFirst();
            List<OnlineTimeInfo> onlineTimeInfo = onlineService.onlineTimeQuery(startTime, endTime, platformKey.getPlatformId(), platformKey.getServerId());
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startTime, endTime, server});
            return new OnlineTimeDto().setOnlineTimeInfo(onlineTimeInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(OnlineTimeDto.class, OnlineErrorKey.UNKNOWN);
        }
    }

}

