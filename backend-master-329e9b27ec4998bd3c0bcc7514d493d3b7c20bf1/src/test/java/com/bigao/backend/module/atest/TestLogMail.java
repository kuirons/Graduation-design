package com.bigao.backend.module.atest;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wait on 2016/3/25.
 */
public class TestLogMail {
    private static Logger logger = LoggerFactory.getLogger(TestLogMail.class);

    public static void main(String[] args) throws InterruptedException {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        logger.error("kd11111dddd11111k");

        Thread.sleep(10 * 1000L);
    }
}
