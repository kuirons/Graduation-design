package com.bigao.backend.module.activeCode;

import com.bigao.backend.util.CommonErrorKey;

/**
 * Created by wait on 2016/1/27.
 */
public interface ActiveCodeErrorKey extends CommonErrorKey {
    /** 平台不存在 */
    String PLATFORM_NOT_EXIST = "activeCode.noPlatform";

    /** 类型只能为1或者2 */
    String ERROR_TYPE = "activeCode.errType";

    /** 服务器不存在 */
    String SERVER_NOT_EXIST = "activeCode.noServer";
}
