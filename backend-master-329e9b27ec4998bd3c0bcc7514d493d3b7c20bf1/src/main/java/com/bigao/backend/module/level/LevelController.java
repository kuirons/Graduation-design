package com.bigao.backend.module.level;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.level.dto.LevelDto;
import com.bigao.backend.module.level.dto.LevelInfo;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.util.CommonErrorKey;
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
import java.util.Map;

/**
 * 等级分布
 * Created by wait on 2015/12/13.
 */
@Controller
@RequestMapping(value = "levelQuery")
public class LevelController {

    private Logger logger = LoggerFactory.getLogger(LevelController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private LevelService levelService;

    @RequestMapping(value = "levelQuery")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("level/levelQuery");
        return view;
    }

    @RequestMapping(value = "levelActionQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public LevelDto regUsersQuery(HttpSession session,
                                  HttpServletRequest request,
                                  @RequestParam(value = "start") String startDate,
                                  @RequestParam(value = "end") String endDate,
                                  @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return CommonUtils.alterMessage(LevelDto.class, CommonErrorKey.EMPTY_DATE);
        }
        try {
            ServerParse parse = CommonUtils.validateDateAndOneServer(startDate, endDate, server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(LevelDto.class, parse.getMessageKey());
            }
            Map.Entry<Integer, List<Integer>> s = parse.getServer().entrySet().iterator().next();
            List<LevelInfo> activeInfo = levelService.queryRegisterUser(s.getKey(), s.getValue().get(0), startDate, endDate);
            
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, endDate, server});

            return new LevelDto(activeInfo, activeInfo.stream().mapToInt(c->c.getNum()).sum());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(LevelDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}
