package com.example.demo.service;

import org.activiti.engine.task.Task;

import java.util.List;

public interface ActivitiService {

    String start();

    List<Task> getTask(String uid);

}
