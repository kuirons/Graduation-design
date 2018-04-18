package com.bigao.backend.module.task;

import com.bigao.backend.module.SuperTest;
import com.bigao.backend.module.task.dto.TaskInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wait on 2015/12/14.
 */
public class TaskServiceTest extends SuperTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testQueryRegisterUser() throws Exception {
       List<TaskInfo> taskInfo = taskService.queryTask(0, 1, "2016-01-01", "2016-01-18");
        taskInfo.forEach(System.err::println);
    }
}