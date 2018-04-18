package com.bigao.backend.module.gm;

import com.bigao.backend.util.CommonErrorKey;

/**
 * Created by wait on 2015/12/26.
 */
public interface GmErrorKey extends CommonErrorKey {

    /** gm指令只能为jar或者reload */
    String ERR_COMMAND = "gm.command.err";

    /** 现在有其它人在执行, 请稍候 */
    String ACTION_ING = "gm.command.actioning";
}
