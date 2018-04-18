package com.bigao.backend.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wait on 2015/11/29.
 */
public class ActionResultError {
    private String result;
    private Map<String, String> fieldErrors;
    private Map<String, String> actionErrors;

    public static ActionResultError err() {
        ActionResultError userResult = new ActionResultError();
        userResult.result = Boolean.FALSE.toString();
        userResult.fieldErrors = new HashMap<>();
        userResult.actionErrors = new HashMap<>();
        return userResult;
    }

    public static ActionResultError succ() {
        ActionResultError userResult = new ActionResultError();
        userResult.result = Boolean.TRUE.toString();
        userResult.fieldErrors = Collections.emptyMap();
        userResult.actionErrors = Collections.emptyMap();
        return userResult;
    }

    public ActionResultError addFiledError(String key, String value) {
        fieldErrors.put(key, value);
        return this;
    }

    public ActionResultError addActionError(String key, String value) {
        actionErrors.put(key, value);
        return this;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
