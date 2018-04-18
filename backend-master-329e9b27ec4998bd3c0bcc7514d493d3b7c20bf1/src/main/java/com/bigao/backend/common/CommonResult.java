package com.bigao.backend.common;

/**
 * Created by wait on 2015/12/26.
 */
public class CommonResult {
    public static final int SUCC = 1;
    public static final int FAIL = -1;

    private int result;
    private String content;

    public static CommonResult newInstance() {
        return new CommonResult();
    }

    public int getResult() {
        return result;
    }

    public CommonResult setResult(int result) {
        this.result = result;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommonResult setContent(String content) {
        this.content = content;
        return this;
    }
}
