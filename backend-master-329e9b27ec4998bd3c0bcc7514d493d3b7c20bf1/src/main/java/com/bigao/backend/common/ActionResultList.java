package com.bigao.backend.common;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wait on 2015/11/28.
 */
public class ActionResultList {
    private String result;
    private List<Map<String, Object>> resultList;
    private Map<String, String> fieldErrors;
    private Map<String, String> actionErrors;

    public static ActionResultList error() {
        ActionResultList actionResult = new ActionResultList();
        actionResult.result = "false";
        actionResult.resultList = Collections.emptyList();
        actionResult.fieldErrors = new HashMap<>();
        actionResult.actionErrors = new HashMap<>();
        return actionResult;
    }

    public static ActionResultList succ() {
        ActionResultList result = new ActionResultList();
        result.result = "true";
        result.resultList = Lists.newArrayList();
        result.fieldErrors = Collections.emptyMap();
        result.actionErrors = Collections.emptyMap();
        return result;
    }

    public static ActionResultList succ(List<Map<String, Object>> resultList) {
        ActionResultList actionResult = new ActionResultList();
        actionResult.result = "true";
        actionResult.resultList = resultList;
        actionResult.fieldErrors = Collections.emptyMap();
        actionResult.actionErrors = Collections.emptyMap();
        return actionResult;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getActionErrors() {
        return actionErrors;
    }

    public void setActionErrors(Map<String, String> actionErrors) {
        this.actionErrors = actionErrors;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }
}
