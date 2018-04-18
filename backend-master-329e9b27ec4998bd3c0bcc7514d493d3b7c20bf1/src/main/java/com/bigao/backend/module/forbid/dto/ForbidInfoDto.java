package com.bigao.backend.module.forbid.dto;

import com.bigao.backend.common.CommonDto;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by wait on 2016/1/18.
 */
public class ForbidInfoDto extends CommonDto {
    private List<String> forbidIp = Lists.newArrayList();
    private List<String> forbidAccount = Lists.newArrayList();

    public static ForbidInfoDto err(String content) {
        ForbidInfoDto dto = new ForbidInfoDto();
        dto.message = content;
        return dto;
    }

    public List<String> getForbidIp() {
        return forbidIp;
    }

    public void setForbidIp(List<String> forbidIp) {
        this.forbidIp = forbidIp;
    }

    public List<String> getForbidAccount() {
        return forbidAccount;
    }

    public void setForbidAccount(List<String> forbidAccount) {
        this.forbidAccount = forbidAccount;
    }
}
