package com.bigao.backend.module.login;

import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.module.log.LogService;
import com.bigao.backend.module.sys.UserService;
import com.bigao.backend.module.sys.auth.AuthenticationResult;
import com.bigao.backend.module.sys.model.Log;
import com.bigao.backend.security.MyUserDetailsService;
import com.bigao.backend.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * 登录
 * Created by wait on 2015/11/26.
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ServerListService serverListService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private LogService logService;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "xfresh")
    @ResponseBody
    public String refresh() {
        try {
            dbUtil4ServerLog.refreshServer();
            userService.clearCache();
            return "success";
        } catch (SQLException e) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    @RequestMapping(value = "/")
    public ModelAndView loginAction() throws Exception {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "login")
    public ModelAndView loginAction(@RequestParam(value = "type", required = false) String type) throws Exception {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "loginSubmit", method = RequestMethod.GET)
    public ModelAndView getLoginSubmit() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "loginSubmit", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest httpRequest,
                              @RequestParam(value = "username") String userName,
                              @RequestParam(value = "password") String password) {
        String ip = IpUtil.remoteIp(httpRequest);
        ModelAndView view = new ModelAndView();
        try {
            AuthenticationResult authenticationResult = loginService.authenticate(userName, password);

            if (authenticationResult != null && authenticationResult.getCode() == 1) {
                UserDetails userDetails = userDetailsService.loadInfo(authenticationResult);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(httpRequest));
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
                Log log = new Log(userName, "登录系统", ip, System.currentTimeMillis());
                logService.addLog(log);

                HttpSession session = httpRequest.getSession(true);
                session.setAttribute("permissionMap", authenticationResult.getUrlPermissionMap());
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
                session.setAttribute("username", userName);
                session.setAttribute("ip", ip);
                serverListService.addGlobalAttribute(view);

                view.setViewName("index");
            }
        } catch (Exception e) {
            logger.error("登陆提交错误:", e);
        }
        if (view.getViewName() == null || view.getViewName().equals("")) {
            view.setViewName("login");
            view.addObject("userError", "登录失败");
        }
        return view;
    }
}
