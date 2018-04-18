package com.bigao.backend.module.log;

import com.bigao.backend.mapper.LogMapper;
import com.bigao.backend.module.sys.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author yuan-hai
 * @date 2013-10-13
 */
@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;
    @Autowired
    private TaskExecutor taskExecutor;

    public void addLog(Log log) throws Exception {
        taskExecutor.execute(() -> logMapper.insertLog(log));
    }
}
