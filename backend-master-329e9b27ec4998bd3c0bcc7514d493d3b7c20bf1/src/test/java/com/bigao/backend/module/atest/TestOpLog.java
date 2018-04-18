package com.bigao.backend.module.atest;

import com.bigao.backend.util.LogUtil;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by wait on 2016/4/6.
 */
public class TestOpLog {

    public void sendNote(HttpSession session,
                         HttpServletRequest request,
                         @RequestParam(value = "startTime") String startTime,
                         @RequestParam(value = "times") int times,
                         @RequestParam(value = "interval") int interval,
                         @RequestParam(value = "noteContent") String content,
                         @RequestParam(value = "server") String server) throws NoSuchMethodException {
        LogUtil.toOpLog(session, request, getClass(),Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[]{startTime, times, interval, content, server});

    }

    public static void main(String[] args) throws NoSuchMethodException, InterruptedException {
        new TestOpLog().sendNote(null, null, "123456", 1, 2, "content", "server");
        LoggerFactory.getLogger(TestOpLog.class).info("test");
        Thread.sleep(3000L);
    }
}
