package com.bigao.backend.module.online;

import com.bigao.backend.module.SuperTest;
import com.bigao.backend.module.online.dto.OnlineRealTimeInfo;
import com.bigao.backend.module.online.dto.OnlineTimeInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wait on 2015/12/28.
 */
public class OnlineServiceTest extends SuperTest {

    @Autowired
    private OnlineService onlineService;

    @Test
    public void testOnlineTimeQuery() throws Exception {
        List<OnlineTimeInfo> r = onlineService.onlineTimeQuery("2015-12-25 00:00:00", "2015-12-28 12:00:00", 0, 1003);
        r.forEach(System.out::println);
    }

    @Test
    public void testRealTimeCount() throws Exception {
        List<OnlineRealTimeInfo> all = onlineService.realTimeCount(0, 1003, 10, "2015-12-25 00:00:00", "2015-12-28 12:00:00");
        all.forEach(System.out::println);
    }
}