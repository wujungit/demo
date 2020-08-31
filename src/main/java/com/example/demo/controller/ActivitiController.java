package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.ActivitiService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivitiController {

    private final Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private ActivitiService activitiService;

    @GetMapping("/start")
    public String start() {
        String start = activitiService.start();
        logger.info(start);
        return start;
    }

    @GetMapping("/getTask")
    public List<Task> getTask(@RequestParam(value = "uid", defaultValue = "1") String uid) {
        List<Task> tasks = activitiService.getTask(uid);
        logger.debug(JSON.toJSONString(tasks));
        return tasks;
    }


}
