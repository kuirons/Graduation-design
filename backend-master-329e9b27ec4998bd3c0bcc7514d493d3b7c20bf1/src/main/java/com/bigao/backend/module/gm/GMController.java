package com.bigao.backend.module.gm;

import com.bigao.backend.common.ActionStringResult;
import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.mapper.GmCommandMapper;
import com.bigao.backend.module.gm.dto.GmCommandDto;
import com.bigao.backend.module.gm.dto.GmLogDto;
import com.bigao.backend.module.gm.dto.GmResultDto;
import com.bigao.backend.module.gm.dto.GmStateDto;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.sys.model.GmCommand;
import com.bigao.backend.util.CommonUtils;
import com.bigao.backend.util.DateUtil;
import com.bigao.backend.util.SystemConfig;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * GM
 * Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping(value = "gmCommand")
public class GMController {

    private Logger logger = LoggerFactory.getLogger(GMController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private GmService gmService;
    @Autowired
    private GmCommandMapper gmCommandMapper;

    @RequestMapping(value = "gmCommand")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("GMCommand/gmCommand");
        return view;
    }

    @RequestMapping(value = "actionResult", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmResultDto actionResult(@RequestParam(value = "batchSign") String batchSign) {
        try {
            if (StringUtils.isBlank(gmService.getSign(AsyncKey.PROGRAM)) || !StringUtils.equals(batchSign, gmService.getSign(AsyncKey.PROGRAM))) {
                return GmResultDto.valueOf().setResult(Boolean.FALSE.toString());
            }
            return GmResultDto.build(gmService.getGmResult(AsyncKey.PROGRAM));
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            return GmResultDto.valueOf().setResult(Boolean.FALSE.toString());
        }
    }

    @RequestMapping(value = "actionState", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmStateDto actionState(@RequestParam(value = "batchSign") String batchSign) {
        try {
            if ((StringUtils.isBlank(gmService.getSign(AsyncKey.PROGRAM)) || !batchSign.equals(gmService.getSign(AsyncKey.PROGRAM)))) {
                return GmStateDto.err("100%");
            }
            return GmStateDto.succ(gmService.getPercent(AsyncKey.PROGRAM));
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            return GmStateDto.err("100%");
        }
    }


    @RequestMapping(value = "actionGMCommand", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmCommandDto command(HttpSession session,
                                HttpServletRequest request,
                                @RequestParam(value = "command") int command,
                                @RequestParam(value = "gmParam") String gmParam,
                                @RequestParam(value = "server") String server) {
        AsyncKey asyncKey = AsyncKey.PROGRAM;
        if (command < 1 || command > 3) {
            return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.ERR_COMMAND), asyncKey);
        }

        try {
            if (StringUtils.isNotBlank(gmService.getSign(asyncKey)) && !gmService.isFinish(asyncKey)) {
                return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.ACTION_ING), asyncKey);
            }
            ServerParse parse = CommonUtils.validateServer(server);
            if (parse.isFail()) {
                return GmCommandDto.err(SystemConfig.getLang(parse.getMessageKey()), asyncKey);
            }
            if (command == 3 && parse.getServerSize() > 1) {
                return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.ONE_SERVER_LIMIT), asyncKey);
            }
            Map<Integer, List<Integer>> allServer = parse.getServer();

            String commandString = command == 1 ? "reload" : (command == 2 ? "jar" : "gm");
            gmService.handle(session, request, commandString, allServer, asyncKey, command, gmParam);
            gmService.setSign(asyncKey, CommonUtils.randString());

            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{command, gmParam, server});

            return GmCommandDto.succ(gmService.getSign(asyncKey), asyncKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return GmCommandDto.err(SystemConfig.getLang(GmErrorKey.UNKNOWN), asyncKey);
        }
    }

    @RequestMapping(value = "innerReload", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActionStringResult innerReload(HttpSession session,
                                          HttpServletRequest request) {
        try {
            return ActionStringResult.valueOf(gmService.reloadInner(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ActionStringResult.valueOf(e.getMessage());
        }
    }


    @RequestMapping(value = "actionHistory", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmLogDto actionHistory(HttpSession session,
                                  HttpServletRequest request,
                                  @RequestParam(value = "start") String start,
                                  @RequestParam(value = "end") String end) {
        try {
            LocalDate startDate = DateUtil.toLocalDateByDate(start);
            LocalDate endDate = DateUtil.toLocalDateByDate(end);
            long beginTime = startDate.atTime(0, 0, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long endTime = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            List<GmCommand> gmLogInfo = gmCommandMapper.selectByTime(beginTime, endTime);
            GmLogDto dto = new GmLogDto();
            if (!gmLogInfo.isEmpty()) {
                gmLogInfo.stream().forEach(g -> dto.getGmInfo().add(g.toInfo()));
            }

            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{start, end});
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(GmLogDto.class, GmErrorKey.UNKNOWN);
        }
    }
}
