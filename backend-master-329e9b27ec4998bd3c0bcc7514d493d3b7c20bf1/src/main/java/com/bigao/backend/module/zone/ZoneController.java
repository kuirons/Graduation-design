package com.bigao.backend.module.zone;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.PlatformKey;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.register.RegisterService;
import com.bigao.backend.module.register.dto.RegisterInfo;
import com.bigao.backend.module.zone.dto.CopyDto;
import com.bigao.backend.module.zone.dto.CopyInfo;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonUtils;
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
import java.util.List;

/**
 * 副本
 * Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping(value = "zoneManage")
public class ZoneController {

    private Logger logger = LoggerFactory.getLogger(ZoneController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "zoneManage")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("zone/zoneQuery");
        return view;
    }

    @RequestMapping(value = "zoneActionQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CopyDto zoneActionQuery(HttpSession session,
                                   HttpServletRequest request,
                                   @RequestParam(value = "start") String startDate,
                                   @RequestParam(value = "server") String server) {
        try {
            ServerParse parse = CommonUtils.validateServer(server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(CopyDto.class, parse.getMessageKey());
            }
            if (parse.getServerSize() > 1) {
                return CommonUtils.alterMessage(CopyDto.class, ZoneErrorKey.ONE_SERVER_LIMIT);
            }

            PlatformKey key = parse.getFirst();
            List<CopyInfo> copyInfo = zoneService.queryLost(key.getPlatformId(), key.getServerId(), startDate, LocalDate.now().toString());
            RegisterInfo registerInfo = registerService.queryRegisterAndCreateRole(key.getPlatformId(), key.getServerId(), startDate, LocalDate.now().toString());
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, server});
            return new CopyDto().setRegisterNum(registerInfo.getRegisterNum()).setCreateRoleNum(registerInfo.getCreateRoleNum()).setCopyInfo(copyInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(CopyDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}
