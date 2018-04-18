package com.bigao.backend.module.register;

import com.bigao.backend.module.register.dto.RegisterInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wait on 2015/12/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Test
    public void testQueryRegisterUser() throws Exception {
        RegisterInfo info = registerService.queryRegisterAndCreateRole(0, 1001, "2015-11-01", "2015-12-14");
        System.err.println(info);
    }
}