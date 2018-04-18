package com.bigao.backend.module.retention;

import com.bigao.backend.module.SuperTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wait on 2015/12/15.
 */
public class RetentionServiceTest extends SuperTest {

    @Autowired
    private RetentionService retentionService;

    @Test
    public void testQueryRetention() throws Exception {
        System.err.println(retentionService.queryRetention(1, 9999, 0));
    }
}