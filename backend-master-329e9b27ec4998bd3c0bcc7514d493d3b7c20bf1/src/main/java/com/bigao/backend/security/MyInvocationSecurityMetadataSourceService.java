package com.bigao.backend.security;

import com.google.common.collect.Lists;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yuan-hai
 * @date 2013-10-10
 */
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    /**
     * 返回访问改地址所需的权限
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
        HttpSession session = ((FilterInvocation) object).getHttpRequest().getSession();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }

        String nameSpace = url.substring(0, url.lastIndexOf("/"));
        List<ConfigAttribute> result = Lists.newArrayList();
        try {
            Map<String, List<String>> permissionMap = (Map<String, List<String>>) session.getAttribute("permissionMap");
            if (permissionMap != null) {
                List<String> perList = permissionMap.get(nameSpace);
                if (perList != null) {
                    for (String string : perList) {
                        ConfigAttribute conf = new SecurityConfig(string);
                        result.add(conf);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

} 
