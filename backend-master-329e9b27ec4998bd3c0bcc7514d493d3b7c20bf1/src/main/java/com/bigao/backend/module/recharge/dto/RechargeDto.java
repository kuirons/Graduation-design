package com.bigao.backend.module.recharge.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2015/12/13.
 */
public class RechargeDto extends CommonDto {
    private List<RechargeInfo> rechargeInfo;

    public RechargeDto() {
    }

    public RechargeDto(List<RechargeInfo> rechargeInfo) {
        this.rechargeInfo = rechargeInfo;
    }

    public List<RechargeInfo> getRechargeInfo() {
        return rechargeInfo;
    }

    public void setRechargeInfo(List<RechargeInfo> rechargeInfo) {
        this.rechargeInfo = rechargeInfo;
    }
}
