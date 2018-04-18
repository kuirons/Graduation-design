package com.bigao.backend.util;

/**
 * Created by wait on 2015/12/15.
 */
public interface CommonErrorKey {
    /** 日期为空 */
    String EMPTY_DATE = "player.emptyDate";
    /** 参数错误 */
    String ERROR_PARAM = "common.errorParam";
    /** 未知错误 */
    String UNKNOWN = "common.unknown";
    /** 角色不存在 */
    String ROLE_NOT_EXIST = "common.noRole";
    /** 角色名字/ID不能为空 */
    String EMPTY_ROLE_PARAM = "common.emptyRoleParam";
    /** 服务器不存在 */
    String SEVER_NOT_EXIST = "common.serverNotExist";
    /** 只能选一个服务器 */
    String ONE_SERVER_LIMIT = "player.oneServer";
    /** 后面的日期需要大于前面的日期 */
    String DATE_BEFORE = "common.dateBefore";
    /** 选择的服务器不能为空 */
    String EMPTY_SERVER = "common.emptyServer";
    /** 玩家账号不能为空 */
    String ACCOUNT_EMPTY = "forbid.emptyAccount";
}
