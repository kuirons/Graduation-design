package com.bigao.backend.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yuan-hai
 * @date 2013-10-10
 */
public class MyAccessDeniedHandlerImpl implements AccessDeniedHandler {

    private String accessDeniedUrl;

    public String getAccessDeniedUrl() {
        return accessDeniedUrl;
    }

    public void setAccessDeniedUrl(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        if (isAjax) {
            String contentType = "application/html";
            response.setContentType(contentType);
            response.sendError(601);
            return;
        } else {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login?type=noright");
        }

    }
}
