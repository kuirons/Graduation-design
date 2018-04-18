package com.bigao.backend.util;

import com.bigao.backend.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 操作日志
 * Created by wait on 2016/4/6.
 */
public class LogUtil {

    private static Logger operateLogger = LoggerFactory.getLogger("operate");
    private static Logger innerLogger = LoggerFactory.getLogger("inner");

    public static void toOpLog(HttpSession session, HttpServletRequest request, Class clz, String methodName, Object[] values) {
        toOpLog(session, request, clz, methodName, values, operateLogger);
    }

    public static void toInnerLog(HttpSession session, HttpServletRequest request, Class clz, String methodName, Object[] values) {
        toOpLog(session, request, clz, methodName, values, innerLogger);
    }

    public static void toOpLog(HttpSession session, HttpServletRequest request, Class clz, String methodName, Object[] values, Logger logger) {
        try {
            List<String> keyList = new ArrayList<>();
            Method method = null;
            for (Method iMethod : clz.getMethods()) {
                if (iMethod.getName().equals(methodName)) {
                    method = iMethod;
                    break;
                }
            }
            if (method == null) {
                return;
            }
            for (Parameter parameter : method.getParameters()) {
                Annotation annotation = parameter.getAnnotation(RequestParam.class);
                if (annotation == null) {
                    continue;
                }
                RequestParam requestParam = (RequestParam) annotation;
                keyList.add(requestParam.value());
            }
            toLog(session, request, clz, methodName, keyList, values, logger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 没有参数, 只记录接口被操作过
     */
    public static void toLog(HttpSession session, HttpServletRequest request, Class clz, String methodName) {
        toLog(session, request, clz, methodName, Collections.emptyList(), new Object[]{}, operateLogger);
    }

    public static void toLog(HttpSession session, HttpServletRequest request, Class clz, String methodName, List<String> keys, Object[] values, Logger logger) {
        StringBuilder builder = new StringBuilder();
        builder.append("className:[").append(clz == null ? "unknown" : clz.getName()).append("],");
        builder.append("methodName:[").append(methodName).append("],");
        builder.append("ip:[").append(IpUtil.remoteIp(request)).append("]");
        builder.append(",user:[").append(session == null ? StringUtils.EMPTY : session.getAttribute("username")).append("] ");
        for (String key : keys) {
            builder.append(key).append("[{}]").append(", ");
        }
        logger.info(builder.toString(), values);
    }
}
