package com.bigao.backend.module.activeCode;

import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.db.Platform;
import com.bigao.backend.module.activeCode.dto.ActiveCode;
import com.bigao.backend.module.activeCode.dto.ActiveCodeDto;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonUtils;
import com.bigao.backend.util.ExportExcel2007;
import com.bigao.backend.util.LogUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Workbook;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 激活码
 * Created by wait on 2016/1/27.
 */
@Controller
@RequestMapping(value = "activeCode")
public class ActiveCodeController {
    private Logger logger = LoggerFactory.getLogger(ActiveCodeController.class);
    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private ActiveCodeService activeCodeService;
    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

    @RequestMapping(value = "active")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        view.setViewName("activeCode/activeCode");
        StringBuilder builder = new StringBuilder();
        builder.append(" 平台ID【");
        for (Platform platform : dbUtil4ServerLog.getPlatformMap().values()) {
            builder.append(platform.getName()).append(":").append(platform.getValue()).append(",");
        }
        if (builder.toString().endsWith(",")) {
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("】");
        view.addObject("platformInfo", builder.toString());
        return view;
    }

    @RequestMapping(value = "activeCodeQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ActiveCodeDto actionResult(HttpSession session, HttpServletRequest request,
                                      @RequestParam(value = "platform") int platform,
                                      @RequestParam(value = "reward") short reward,
                                      @RequestParam(value = "type") byte type,
                                      @RequestParam(value = "server") short server,
                                      @RequestParam(value = "count") short count) {
        try {
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{platform, reward, type, server, count});
            if (platform != -1 && !dbUtil4ServerLog.getPlatformMap().containsKey(platform)) {
                return CommonUtils.alterMessage(ActiveCodeDto.class, ActiveCodeErrorKey.PLATFORM_NOT_EXIST);
            }
            if (type != 1 && type != 2) {
                return CommonUtils.alterMessage(ActiveCodeDto.class, ActiveCodeErrorKey.ERROR_TYPE);
            }
            if (server != -1 && dbUtil4ServerLog.getDBServer(platform, server) == null) {
                return CommonUtils.alterMessage(ActiveCodeDto.class, ActiveCodeErrorKey.SERVER_NOT_EXIST);
            }
            count = count < 0 ? 1 : count;
            List<String> result = activeCodeService.card((byte) platform, reward, type, server, count);
            return ActiveCodeDto.valueOf(result.stream().map(ActiveCode::valueOf).collect(Collectors.toList()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(ActiveCodeDto.class, CommonErrorKey.UNKNOWN);
        }
    }

    @RequestMapping(value = "exportTable")
    @ResponseBody
    public String exportTable(HttpSession session, HttpServletRequest request,
                              @RequestParam(value = "platform") int platform,
                              @RequestParam(value = "reward") short reward,
                              @RequestParam(value = "type") byte type,
                              @RequestParam(value = "server") short server,
                              @RequestParam(value = "count") short count,
                              HttpServletResponse response) {
        ActiveCodeDto codeDto = actionResult(session, request, platform, reward, type, server, count);
        if (codeDto == null || !codeDto.getMessage().equals("")) {
            return "error";
        } else {
            List<Pair<String, String>> paramMap = Lists.newArrayList();
            paramMap.add(Pair.of("平台(说明：指定平台生效,-1表示无限制)", String.valueOf(platform)));
            paramMap.add(Pair.of("奖励Id(说明：奖励id,同样也用来当做批次id,对应q_card.q_id)", String.valueOf(reward)));
            paramMap.add(Pair.of("是否复用(说明：1:相同reward的激活码同一个玩家仅仅能够使用一次 2:可多次使用不同id的激活码)", String.valueOf(type)));
            paramMap.add(Pair.of("服务器(说明：指定服务器生效,-1表示无限制)", String.valueOf(server)));
            paramMap.add(Pair.of("生成的数量", String.valueOf(count)));

            List<String> codes = new ArrayList<>();
            List<ActiveCode> codePtos = codeDto.getCode();
            codes.addAll(codePtos.stream().map(ActiveCode::getCode).collect(Collectors.toList()));
            try {
                Workbook workbook = ExportExcel2007.getWorkbook("激活码", codes, paramMap);
                if (workbook == null) {
                    return "error";
                }
                String fileName = "ActiveCode-" + dateFormat.format(new Date()) + ".xlsx";
                String headStr = "attachment; filename=\"" + fileName + "\"";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", headStr);
                workbook.write(response.getOutputStream());
                response.getOutputStream().close();
                workbook.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return "error";
            }
        }
        return "success";
    }
}
