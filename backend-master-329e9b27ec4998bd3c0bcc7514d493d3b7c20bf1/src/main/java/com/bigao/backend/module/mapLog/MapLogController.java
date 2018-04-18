package com.bigao.backend.module.mapLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.mapLog.dto.MapLogDto;
import com.bigao.backend.module.mapLog.dto.MapLogDto1;
import com.bigao.backend.module.mapLog.dto.MapLogInfo;
import com.bigao.backend.module.register.dto.RegisterInfo;
import com.bigao.backend.module.task.TaskController;
import com.bigao.backend.module.task.dto.TaskDto;
import com.bigao.backend.module.task.dto.TaskInfo;
import com.bigao.backend.util.CommonErrorKey;
import com.bigao.backend.util.CommonUtils;
import com.bigao.backend.util.LogUtil;

@Controller
@RequestMapping("mapLogQuery")
public class MapLogController {
	private Logger logger = LoggerFactory.getLogger(MapLogController.class);
	
	@Autowired
    private ServerListService serverListService;
	@Autowired
	private MapLogService mapLogService;
	
	@RequestMapping(value = "mapLogQuery")
    public ModelAndView root() {
		ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("mapLog/mapLogQuery");
        return view;
    }
	
	@RequestMapping(value = "mapLog1Query", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public MapLogDto1 mapLogQuery(HttpSession session,
                                     HttpServletRequest request,
                                     @RequestParam(value = "start") String startDate,
                                     @RequestParam(value = "end") String endDate,
                                     @RequestParam(value = "server") String server,
                                     @RequestParam(value = "way") int way) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return CommonUtils.alterMessage(MapLogDto1.class, CommonErrorKey.EMPTY_DATE);
        }
        try {
            ServerParse parse = CommonUtils.validateDateAndOneServer(startDate, endDate, server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(MapLogDto1.class, parse.getMessageKey());
            }
            Map.Entry<Integer, List<Integer>> s = parse.getServer().entrySet().iterator().next();
            MapLogDto mapLogInfo = mapLogService.queryTask(s.getKey(), s.getValue().get(0), startDate, endDate,way); 
            List<MapLogDto> logDtos=new ArrayList<>();
            logDtos.add(mapLogInfo);
            MapLogDto1 dto1=new MapLogDto1();
            dto1.setLists(logDtos);
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, endDate, server});
            return dto1;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(MapLogDto1.class, CommonErrorKey.UNKNOWN);
        }
		
	}
}
