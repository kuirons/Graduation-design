package com.bigao.backend.module.activeCode;

import com.bigao.backend.util.CommonUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by wait on 2016/1/27.
 */
@Service
public class ActiveCodeService {
    /**
     * 产生激活码(同一个奖励id只能够生成1次)
     *
     * @param platform 指定平台生效,-1表示无限制
     * @param reward   奖励id,同样也用来当做批次id,对应q_card.q_id
     * @param type     1:对于相同reward的激活码，同一个玩家仅仅能够使用一次 2:可多次使用不同id的激活码
     * @param server   指定服务器生效,-1表示无限制
     * @param count    生成的数量
     * @return
     * @throws UnsupportedEncodingException
     */
    public List<String> card(byte platform, short reward, byte type, short server, short count) throws UnsupportedEncodingException {
        List<String> cards = Lists.newArrayList();
        for (short i = 0; i < count; ++i) {
            if (server == -1) {
                ActivationCode code = new ActivationCode();
                code.setPlatform(platform);
                code.setReward(reward);
                code.setCover(type);
                code.setId(i);
                cards.add(code.encrypt());
            } else {
                ActivationCode.ActivationCodeWithServer code = new ActivationCode.ActivationCodeWithServer();
                code.setPlatform(platform);
                code.setReward(reward);
                code.setCover(type);
                code.setId(i);
                code.setServer(server);
                code.setsCover((byte) CommonUtils.random(1, Byte.MAX_VALUE));
                cards.add(code.encrypt());
            }
        }
        return cards;
    }
}
