package com.bigao.backend.module.retention;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.module.common.PlatformServerInfo;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.retention.dto.RetentionDto;
import com.bigao.backend.module.retention.dto.RetentionInfo;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonUtils;
import com.google.common.collect.Lists;
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
 * 留存
 * Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping(value = "retentionQuery")
public class RetentionController {

    private Logger logger = LoggerFactory.getLogger(RetentionController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private RetentionService retentionService;
    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

    @RequestMapping(value = "retentionQuery")
    public ModelAndView root(HttpSession session) {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("retention/retentionQuery");
        return view;
    }

    @RequestMapping(value = "retention", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public RetentionDto retention(HttpSession session,
                                  HttpServletRequest request,
                                  @RequestParam(value = "server") String server) {
        try {
            ServerParse parse = CommonUtils.validateServer(server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(RetentionDto.class, parse.getMessageKey());
            }
            if (parse.getServerSize() > 1) {
                return CommonUtils.alterMessage(RetentionDto.class, CommonErrorKey.ONE_SERVER_LIMIT);
            }

            List<RetentionInfo> activeInfo = Lists.newArrayList();
            Map.Entry<Integer, List<Integer>> e = parse.getServer().entrySet().iterator().next();
            int platform = e.getKey();
            int intServer = e.getValue().get(0);
            for (int i = 0; i < 3; i++) {
                RetentionInfo info = retentionService.queryRetention(platform, intServer, i);
                if (info != null) {
                    activeInfo.add(info);
                }
            }
            RetentionDto dto = new RetentionDto().setSaveInfo(activeInfo);
            dto.setPlatformServer(PlatformServerInfo.desc(platform, intServer));
            dto.setOpenServerDate(dbUtil4ServerLog.getOpenTimeString(platform, intServer));

            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{server});
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(RetentionDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}
