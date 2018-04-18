package com.bigao.backend.module.operate;

import com.bigao.backend.common.ActionStringResult;
import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.gm.AsyncKey;
import com.bigao.backend.module.gm.GMController;
import com.bigao.backend.module.gm.GmErrorKey;
import com.bigao.backend.module.gm.GmService;
import com.bigao.backend.module.gm.dto.GmCommandDto;
import com.bigao.backend.module.gm.dto.GmResultDto;
import com.bigao.backend.module.gm.dto.GmStateDto;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.util.CommonUtils;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 运维
 * Created by wait on 2016/4/6.
 */
@Controller
@RequestMapping(value = "operate")
public class OperateController {
    private Logger logger = LoggerFactory.getLogger(GMController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private GmService gmService;

    @RequestMapping(value = "operate")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("operate/operate");
        return view;
    }

    @RequestMapping(value = "actionResult", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmResultDto actionResult(@RequestParam(value = "batchSign") String batchSign) {
        try {
            if (StringUtils.isBlank(gmService.getSign(AsyncKey.OPERATE)) || !StringUtils.equals(batchSign, gmService.getSign(AsyncKey.OPERATE))) {
                return GmResultDto.valueOf().setResult(Boolean.FALSE.toString());
            }
            return GmResultDto.build(gmService.getGmResult(AsyncKey.OPERATE));
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            return GmResultDto.valueOf().setResult(Boolean.FALSE.toString());
        }
    }

    @RequestMapping(value = "actionState", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmStateDto actionState(@RequestParam(value = "batchSign") String batchSign) {
        try {
            if ((StringUtils.isBlank(gmService.getSign(AsyncKey.OPERATE)) || !batchSign.equals(gmService.getSign(AsyncKey.OPERATE)))) {
                return GmStateDto.err("100%");
            }
            return GmStateDto.succ(gmService.getPercent(AsyncKey.OPERATE));
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            return GmStateDto.err("100%");
        }
    }


    @RequestMapping(value = "operateGMCommand", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public GmCommandDto command(HttpSession session,
                                HttpServletRequest request,
                                @RequestParam(value = "command") int command,
                                @RequestParam(value = "server") String server) {
        AsyncKey asyncKey = AsyncKey.OPERATE;
        if (command < 1 || command > 2) {
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

            String commandString = command == 1 ? "reload" : "jar";
            gmService.handle(session, request, commandString, allServer, asyncKey, command, null);

            gmService.setSign(asyncKey, CommonUtils.randString());

            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{command, server});

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

}
