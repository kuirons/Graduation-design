package com.bigao.backend.common;

/**
 * Created by wait on 2015/11/29.
 */
public class ActionStringResult {
    private String result;

    public static ActionStringResult succ() {
        return valueOf(Boolean.TRUE.toString());
    }

    public static ActionStringResult err() {
        return valueOf(Boolean.FALSE.toString());
    }

    public static ActionStringResult valueOf(String result) {
        ActionStringResult actionStringResult = new ActionStringResult();
        actionStringResult.result = result;
        return actionStringResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
