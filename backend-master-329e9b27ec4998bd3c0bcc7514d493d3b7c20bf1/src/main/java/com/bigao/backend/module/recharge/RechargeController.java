package com.bigao.backend.module.recharge;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.recharge.dto.RechargeDto;
import com.bigao.backend.module.recharge.dto.RechargeInfo;
import com.bigao.backend.module.register.RegisterController;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 消耗
 * Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping(value = "rechargeQuery")
public class RechargeController {

    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private TaskExecutor taskExecutor;

    @RequestMapping(value = "rechargeQuery")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("recharge/rechargeQuery");
        return view;
    }

    @RequestMapping(value = "rechargeSumQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public RechargeDto rechargeSumQuery(HttpSession session,
                                        HttpServletRequest request,
                                        @RequestParam(value = "start") String startDate,
                                        @RequestParam(value = "end") String endDate,
                                        @RequestParam(value = "server") String server) {
        try {
            ServerParse parse = CommonUtils.validateDateAndServer(startDate, endDate, server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(RechargeDto.class, parse.getMessageKey());
            }
            List<RechargeInfo> rechargeInfoList = Lists.newArrayList();
            RechargeDto dto = new RechargeDto(rechargeInfoList);
            RechargeInfo sumInfo = new RechargeInfo();
            sumInfo.setFirstColumnName("累计");
            // 累计忽略开启日期
            sumInfo.setOpenDate("");
            CountDownLatch latch = new CountDownLatch(parse.getServerSize());
            for (Map.Entry<Integer, List<Integer>> e : parse.getServer().entrySet()) {
                for (int s : e.getValue()) {
                    taskExecutor.execute(() -> {
                        try {
                            RechargeInfo info = rechargeService.allRecharge(e.getKey(), s, startDate, endDate);
                            rechargeInfoList.add(info);
                            // 累加
                            sumInfo.setOpenDays(sumInfo.getOpenDays() + info.getOpenDays());
                            sumInfo.setRmb(sumInfo.getRmb() + info.getRmb());
                            sumInfo.setSumRmb(sumInfo.getSumRmb() + info.getSumRmb());
                            sumInfo.setRechargeNum(sumInfo.getRechargeNum() + info.getRechargeNum());
                            sumInfo.setRechargeRate("");// 累计忽略
                            sumInfo.setRepeatRechargeNum(sumInfo.getRepeatRechargeNum() + info.getRepeatRechargeNum());
                            sumInfo.setOtherRechargeNum(sumInfo.getOtherRechargeNum() + info.getOtherRechargeNum());
                            sumInfo.setRegisterNum(sumInfo.getRegisterNum() + info.getRegisterNum());
                            sumInfo.setRoleNum(sumInfo.getRoleNum() + info.getRoleNum());
                            sumInfo.setRoleRate("");// 累计忽略
                            sumInfo.setActiveNum(sumInfo.getActiveNum() + info.getActiveNum());
                            sumInfo.setOldActiveNum(sumInfo.getOldActiveNum() + info.getOldActiveNum());

                            if (info.getArppu() != null) {
                                if (sumInfo.getArppu() != null) {
                                    sumInfo.setArppu(String.valueOf(Float.parseFloat(sumInfo.getArppu()) + Float.parseFloat(info.getArppu())));
                                } else {
                                    sumInfo.setArppu(info.getArppu());
                                }
                            }
                            if (info.getArpu() != null) {
                                if (sumInfo.getArpu() != null) {
                                    sumInfo.setArpu(String.valueOf(Float.parseFloat(sumInfo.getArpu()) + Float.parseFloat(info.getArpu())));
                                } else {
                                    sumInfo.setArpu(info.getArpu());
                                }
                            }
                            sumInfo.setCostGold(sumInfo.getCostGold() + info.getCostGold());
                        } catch (SQLException e1) {
                            logger.error(e1.getMessage(), e);
                        } finally {
                            latch.countDown();
                        }
                    });
                }
            }
            latch.await(10, TimeUnit.MINUTES);
            if (!rechargeInfoList.isEmpty()) {
                rechargeInfoList.add(sumInfo);
            }

            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, endDate, server});


            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(RechargeDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}
