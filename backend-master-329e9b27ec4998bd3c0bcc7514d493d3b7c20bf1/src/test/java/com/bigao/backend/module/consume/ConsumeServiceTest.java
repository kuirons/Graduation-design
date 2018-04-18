package com.bigao.backend.module.consume;

import com.bigao.backend.module.SuperTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wait on 2016/1/2.
 */
public class ConsumeServiceTest extends SuperTest {

    @Autowired
    private ConsumeService consumeService;

    @Test
    public void testGetConsumeGold() throws Exception {
        System.err.println(consumeService.getConsumeGold(0, 1001, "2015-12-20", "2016-01-02"));
    }
}