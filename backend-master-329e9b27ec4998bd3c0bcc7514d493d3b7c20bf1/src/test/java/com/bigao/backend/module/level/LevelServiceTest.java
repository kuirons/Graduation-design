package com.bigao.backend.module.level;

import com.bigao.backend.module.SuperTest;
import com.bigao.backend.module.level.dto.LevelInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wait on 2015/12/14.
 */
public class LevelServiceTest extends SuperTest {

    @Autowired
    private LevelService levelService;

    @Test
    public void testQueryRegisterUser() throws Exception {
        for (LevelInfo info : levelService.queryRegisterUser(0, 1001, "2015-12-01", "2015-12-14")) {
            System.err.println(info);
        }
    }
}