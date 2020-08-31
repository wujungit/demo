package com.example.demo.service.impl;

import com.example.demo.service.ActivitiService;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private TaskService taskService;// 对用户任务进行操作和查询
    @Autowired
    private RepositoryService repositoryService;// 对activiti资源进行操作，比如部署文件，附件
    @Autowired
    private RuntimeService runtimeService;// 运行时服务，可以对运行时流程进行修改，如增加变量，移除变量等
    @Autowired
    private IdentityService identityService;// 身份认证服务，对用户，用户组，用户角色进行操作
    @Autowired
    private HistoryService historyService;// 历史记录服务，对审批历史进行操作
    @Autowired
    private FormService formService;// 表单服务，操作表单数据
    @Autowired(required = false)
    private DynamicBpmnService dynamicBpmnService;// 通过该服务，可以动态修改流程
    @Autowired
    private ManagementService managementService;// 管理服务，查看当前activiti系统信息，不会在应用里用到，一般用于管理系统里

    public String start() {
        ProcessInstance instance = runtimeService.startProcessInstanceById("myProcess_1:1:4");
        return instance.getId();
    }

    public List<Task> getTask(String uid) {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(uid).list();
        return tasks;
    }

}