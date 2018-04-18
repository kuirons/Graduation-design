package com.bigao.backend.module.player;

import com.bigao.backend.util.CommonErrorKey;

/**
 * Created by wait on 2016/1/4.
 */
public interface PlayerErrorKey extends CommonErrorKey {
    /** 玩家名字不能为空 */
    String EMPTY_PLAYER_NAME = "player.emptyName";
    /** 角色Id不能为空 */
    String EMPTY_ROLE_ID="player.emptyRoleId";
}
