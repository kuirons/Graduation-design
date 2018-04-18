package com.bigao.backend.common;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author yuan-hai
 * @date 2013-12-26
 */
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*HttpServletRequest req = (HttpServletRequest) request;
        //判断ajax请求时session超时
        if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
            // 这里因为inner移过来了, 然后restart接口属于ajax, 也不需要权限验证
            if (!req.getRequestURI().equals("/postRestart.php") && null == req.getSession().getAttribute("SPRING_SECURITY_CONTEXT")) {
                HttpServletResponse rep = (HttpServletResponse) response;
                rep.sendError(602);
                return;
            }
        }*/
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
