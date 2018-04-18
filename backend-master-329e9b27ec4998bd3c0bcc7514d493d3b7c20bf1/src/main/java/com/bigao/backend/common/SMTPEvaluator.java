package com.bigao.backend.common;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluator;
import ch.qos.logback.core.spi.ContextAwareBase;

/**
 * Created by wait on 2016/4/7.
 */
public class SMTPEvaluator extends ContextAwareBase implements EventEvaluator<LoggingEvent> {

    String name;
    boolean started;

    public boolean evaluate(LoggingEvent event) throws NullPointerException, EvaluationException {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            return false;
        }
        return event.getLevel().levelInt >= Level.ERROR_INT && event.getThrowableProxy() != null;// error级别且带异常的日志
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStarted() {
        return started;
    }

    public void start() {
        started = true;
    }

    public void stop() {
        started = false;
    }
}

