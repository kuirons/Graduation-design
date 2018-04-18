package com.bigao.backend.common;

import com.bigao.backend.util.SystemConfig;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by wait on 2015/11/30.
 */
public class ActionMessageResult {
    /** 提示信息的key值 */
    private String message = "";
    /** 返回结果集 */
    private List<Object> resultList;

    public static ActionMessageResult err(String messageKey) {
        ActionMessageResult actionIntResult = new ActionMessageResult();
        actionIntResult.message = SystemConfig.getLang(messageKey);
        return actionIntResult;
    }

    public static ActionMessageResult succ() {
        ActionMessageResult actionIntResult = new ActionMessageResult();
        actionIntResult.resultList = Lists.newArrayList();
        return actionIntResult;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getResultList() {
        return resultList;
    }

    public void setResultList(List<Object> resultList) {
        this.resultList = resultList;
    }
}
