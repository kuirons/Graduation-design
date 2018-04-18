package com.bigao.backend.module.register;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.register.dto.RegisterDto;
import com.bigao.backend.module.register.dto.RegisterInfo;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonUtils;
import com.google.common.collect.Lists;
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

/**
 * 注册统计
 * Created by wait on 2015/12/1.
 */
@Controller
@RequestMapping(value = "regQuery")
public class RegisterController {

    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "regQuery")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView("register/registerQuery");
        serverListService.addGlobalAttribute(view);
        return view;
    }

    @RequestMapping(value = "regUsersQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public RegisterDto regUsersQuery(HttpSession session,
                                     HttpServletRequest request,
                                     @RequestParam(value = "start") String startDate,
                                     @RequestParam(value = "end") String endDate,
                                     @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return CommonUtils.alterMessage(RegisterDto.class, CommonErrorKey.EMPTY_DATE);
        }
        try {
            ServerParse parse = CommonUtils.validateDateAndServer(startDate, endDate, server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(RegisterDto.class, parse.getMessageKey());
            }
            List<RegisterInfo> activeInfo = Lists.newArrayList();
            for (Map.Entry<Integer, List<Integer>> e : parse.getServer().entrySet()) {
                for (int iServer : e.getValue()) {
                    RegisterInfo info = registerService.queryRegisterAndCreateRole(e.getKey(), iServer, startDate, endDate);
                    if (info != null) {
                        activeInfo.add(info);
                    }
                }
            }
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, endDate, server});

            return new RegisterDto().setRegisterInfo(activeInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(RegisterDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}
