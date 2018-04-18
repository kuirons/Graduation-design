package com.bigao.backend.module.zone;

import com.bigao.backend.module.SuperTest;
import com.bigao.backend.module.zone.dto.CopyInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wait on 2015/12/28.
 */
public class ZoneServiceTest extends SuperTest {
    @Autowired
    private ZoneService zoneService;

    @Test
    public void testQueryLost() throws Exception {
        List<CopyInfo> copyInfoList = zoneService.queryLost(1, 9999, "2016-01-01", "2016-01-13");
        copyInfoList.forEach(System.out::println);
    }
}