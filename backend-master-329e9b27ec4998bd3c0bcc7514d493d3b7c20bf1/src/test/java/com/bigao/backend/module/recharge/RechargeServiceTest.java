package com.bigao.backend.module.recharge;

import com.bigao.backend.module.SuperTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wait on 2016/1/13.
 */
public class RechargeServiceTest extends SuperTest{

    @Autowired
    private RechargeService rechargeService;

    @Test
    public void testAllRecharge() throws Exception {
        System.err.println(rechargeService.allRecharge(0, 1, "2016-01-01", "2016-01-13"));
    }
}