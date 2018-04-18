package com.bigao.backend.module.zone.vo;

/**
 * Created by wait on 2015/12/28.
 */
public class CopyVo {
    /** 进入人数 */
    private int enterCount;
    /** 退出人数 */
    private int doneCount;

    public static CopyVo valueOf(int enterCount) {
        CopyVo vo = new CopyVo();
        vo.enterCount = enterCount;
        return vo;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }

    public int getEnterCount() {
        return enterCount;
    }

    public void setEnterCount(int enterCount) {
        this.enterCount = enterCount;
    }
}
