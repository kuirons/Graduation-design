package com.bigao.backend.module.task;

import com.bigao.backend.util.LogUtil;
import com.bigao.backend.common.ServerParse;
import com.bigao.backend.module.login.ServerListService;
import com.bigao.backend.module.register.RegisterService;
import com.bigao.backend.module.register.dto.RegisterInfo;
import com.bigao.backend.module.task.dto.TaskDto;
import com.bigao.backend.module.task.dto.TaskInfo;
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
 * 任务
 * Created by wait on 2015/11/29.
 */
@Controller
@RequestMapping("taskQuery")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "taskQuery")
    public ModelAndView root() {
        ModelAndView view = new ModelAndView();
        serverListService.addGlobalAttribute(view);
        view.setViewName("task/taskQuery");
        return view;
    }

    @RequestMapping(value = "taskFinishedQuery", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public TaskDto taskFinishedQuery(HttpSession session,
                                     HttpServletRequest request,
                                     @RequestParam(value = "start") String startDate,
                                     @RequestParam(value = "end") String endDate,
                                     @RequestParam(value = "server") String server) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return CommonUtils.alterMessage(TaskDto.class, CommonErrorKey.EMPTY_DATE);
        }
        try {
            ServerParse parse = CommonUtils.validateDateAndOneServer(startDate, endDate, server);
            if (parse.isFail()) {
                return CommonUtils.alterMessage(TaskDto.class, parse.getMessageKey());
            }
            Map.Entry<Integer, List<Integer>> s = parse.getServer().entrySet().iterator().next();
            List<TaskInfo> taskInfo = taskService.queryTask(s.getKey(), s.getValue().get(0), startDate, endDate);
            TaskDto dto = new TaskDto();
            dto.setTaskInfo(taskInfo);
            RegisterInfo registerInfo = registerService.queryRegisterAndCreateRole(s.getKey(), s.getValue().get(0), startDate, endDate);
            dto.setRegisterNum(registerInfo.getRegisterNum());
            dto.setCreateRoleNum(registerInfo.getCreateRoleNum());
            LogUtil.toOpLog(session, request, getClass(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startDate, endDate, server});
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonUtils.alterMessage(TaskDto.class, CommonErrorKey.UNKNOWN);
        }
    }
}
