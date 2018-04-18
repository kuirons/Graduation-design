package com.bigao.backend.module.activeCode.dto;

/**
 * Created by wait on 2016/1/27.
 */
public class ActiveCode {
    private String code;

    public static ActiveCode valueOf(String code) {
        ActiveCode activeCode = new ActiveCode();
        activeCode.code = code;
        return activeCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
