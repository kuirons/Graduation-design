package com.bigao.backend.module.player;

import com.bigao.backend.log.AccountCreateLog;
import com.bigao.backend.log.RoleCreateLog;
import com.bigao.backend.log.RoleLevelUpLog;
import com.bigao.backend.module.player.dto.PlayerLoginInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by wait on 2015/11/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class PlayerServiceTest {


    @Autowired
    private PlayerService playerService;

    @Test
    public void testGetRoleById() throws Exception {
        System.err.println(playerService.getRole(0, 1003, 1, "370360098049"));
    }

    @Test
    public void testGetRoleByAccount() throws Exception {
        System.err.println(playerService.getRole(0, 1003, 2, "yrx19891"));
    }

    @Test
    public void testGetRoleByName() throws Exception {
        System.err.println(playerService.getRole(0, 1003, 3, "柯玲"));
    }


    @Test
    public void testQueryRoleLevel() throws Exception {
        AccountCreateLog createLog = new AccountCreateLog();
        createLog.setId(370818398210L);
        RoleCreateLog roleCreateLog = new RoleCreateLog();
        LogRole logRole = LogRole.valueOf(createLog, roleCreateLog);
        logRole.setPlatform(0);
        logRole.setServer(1003);
        List<RoleLevelUpLog> upLogs = playerService.queryRoleLevel("2015-11-07", "2015-12-13", logRole);
        upLogs.forEach(System.err::println);
    }

    @Test
    public void testQueryPlayerLoginLog() throws Exception {
        LogRole logRole = playerService.getRole(0, 1, 1, "371777273869");
        if (logRole == null) {
            return;
        }
        List<PlayerLoginInfo> loginInfo = playerService.queryPlayerLoginLog("2016-01-08 20:09:38", "2016-01-22 20:09:38", logRole);
        loginInfo.forEach(System.err::println);
    }
}