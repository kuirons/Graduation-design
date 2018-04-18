package com.bigao.backend.common;

import java.util.Collections;
import java.util.Map;

/**
 * Created by wait on 2015/11/29.
 */
public class ActionResultOne {
    private String result;
    private Map<String, Object> resultList;

    public static ActionResultOne err() {
        ActionResultOne actionResultOne = new ActionResultOne();
        actionResultOne.result = Boolean.FALSE.toString();
        actionResultOne.resultList = Collections.emptyMap();
        return actionResultOne;
    }

    public static ActionResultOne succ(Map<String, Object> resultList) {
        ActionResultOne actionResultOne = new ActionResultOne();
        actionResultOne.result = Boolean.TRUE.toString();
        actionResultOne.resultList = resultList;
        return actionResultOne;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, Object> getResultList() {
        return resultList;
    }

    public void setResultList(Map<String, Object> resultList) {
        this.resultList = resultList;
    }
}
