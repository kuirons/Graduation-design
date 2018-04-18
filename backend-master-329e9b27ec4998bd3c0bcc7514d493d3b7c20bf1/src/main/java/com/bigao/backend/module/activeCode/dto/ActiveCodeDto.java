package com.bigao.backend.module.activeCode.dto;

import com.bigao.backend.common.CommonDto;

import java.util.List;

/**
 * Created by wait on 2016/1/27.
 */
public class ActiveCodeDto extends CommonDto{

    private List<ActiveCode> code;

    public static ActiveCodeDto valueOf(List<ActiveCode> code) {
        ActiveCodeDto codeDto = new ActiveCodeDto();
        codeDto.code = code;
        return codeDto;
    }

    public List<ActiveCode> getCode() {
        return code;
    }

    public void setCode(List<ActiveCode> code) {
        this.code = code;
    }
}
