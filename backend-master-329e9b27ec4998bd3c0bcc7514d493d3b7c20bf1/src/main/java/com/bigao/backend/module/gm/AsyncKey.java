package com.bigao.backend.module.gm;

/**
 * Created by wait on 2015/12/31.
 */
public enum AsyncKey {
    /** 程序猿gm */
    PROGRAM(1),

    /** 运营邮件 */
    GM_MAIL(2),

    /** 运营公告 */
    GM_NOTE(3),

    /** 发放元宝的超级GM */
    SUPER_GM_MAIL(4),

    OPERATE(5), // 运维操作
    ;

    private int key;

    public static AsyncKey valueOf(int key) {
        for (AsyncKey asyncKey : AsyncKey.values()) {
            if (asyncKey.getKey() == key) {
                return asyncKey;
            }
        }
        return null;
    }

    AsyncKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
