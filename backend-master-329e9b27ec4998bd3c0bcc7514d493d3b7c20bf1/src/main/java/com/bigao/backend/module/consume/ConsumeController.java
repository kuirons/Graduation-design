package com.bigao.backend.module.consume;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.consume.dto.ConsumeNumDto;
import com.bigao.backend.module.consume.dto.ConsumeNumInfo;
import com.bigao.backend.module.login.ServerListService;
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
 * 消耗
 * Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping(value = "consumeQuery")
public class ConsumeController {
    private Logger logger = LoggerFactory.getLogger(ConsumeController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private ConsumeService consumeService;

    @RequestMapping(value = "consumeQuery")
    public ModelAndView root(HttpSession session) {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("consume/consumeQuery");
        return view;
    }

    @RequestMapping(value = "consumeInfo", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ConsumeNumDto consumeInfo(HttpSession session,
                                     HttpServletRequest request,
                                     @RequestParam(value = "start") String startDate,
                                     @RequestParam(value = "end") String endDate,
                                     @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return CommonUtils.alterMessage(ConsumeNumDto.class, CommonErrorKey.EMPTY_DATE);
        }
        try {
            ServerParse parse = CommonUtils.validateDateAndServer(startDate, endDate, server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(ConsumeNumDto.class, parse.getMessageKey());
            }
            List<ConsumeNumInfo> consumeNumInfo = Lists.newArrayList();
            for (Map.Entry<Integer, List<Integer>> e : parse.getServer().entrySet()) {
                for (int svrId : e.getValue()) {
                    int sumGold = consumeService.getConsumeGold(e.getKey(), svrId, startDate, endDate);
                    consumeNumInfo.add(new ConsumeNumInfo(e.getKey(), svrId, Math.abs(sumGold)));
                }
            }
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, endDate, server});
            return new ConsumeNumDto(consumeNumInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(ConsumeNumDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}
