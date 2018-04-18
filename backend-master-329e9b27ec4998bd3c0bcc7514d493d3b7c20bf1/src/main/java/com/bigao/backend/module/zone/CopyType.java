package com.bigao.backend.module.zone;

/**
 * Created by shell on 2015/7/2.
 */
public enum CopyType {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 单人副本
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @CopyDesc("剧情副本")
    STORY(1), // 剧情副本
    @CopyDesc("守护唐僧")
    GUARDIAN(2), // 守护唐僧
    @CopyDesc("六道轮回")
    BATTLE_HERO(4),// 六道轮回
    @CopyDesc("真假美猴王")
    REAL_MONKEY(5), // 真假美猴王
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 组队副本
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @CopyDesc("八卦")
    BAGUA(51), // 八卦
    ;

    private int value;

    CopyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CopyType valueOf(int value) {
        for (CopyType dailyCopyType : CopyType.values()) {
            if (dailyCopyType.value == value) {
                return dailyCopyType;
            }
        }
        return null;
    }

    public static CopyType valueOfName(String name) {
        for (CopyType dailyCopyType : CopyType.values()) {
            if (dailyCopyType.name().equals(name)) {
                return dailyCopyType;
            }
        }
        return null;
    }

    // 不能跟CopyType重复
    public enum ActiveType {
        WORSHIP(1000), // 花果山
        CAMP_BATTLE(1001), // 善恶难辨
        ;

        private int value;

        ActiveType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static CopyType valueOf(int value) {
            for (CopyType dailyCopyType : CopyType.values()) {
                if (dailyCopyType.value == value) {
                    return dailyCopyType;
                }
            }
            return null;
        }

        public static CopyType valueOfName(String name) {
            for (CopyType dailyCopyType : CopyType.values()) {
                if (dailyCopyType.name().equals(name)) {
                    return dailyCopyType;
                }
            }
            return null;
        }
    }
}
