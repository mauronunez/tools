package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestMyProcess {

	private String filename = "/home/mnunez/workspace/modelling/inicial/Garantias/bin/src/main/resources/diagrams/Garantias.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("myProcess.bpmn20.xml",
				new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
		
		TaskService taskService = activitiRule.getTaskService();
		System.out.println("count " +taskService.createTaskQuery().count());
		
		List<Task> list = taskService.createTaskQuery().list();
		for(Task task:list){
			System.out.println("task available: " + task.getName());
			System.out.println("task id: " + task.getId());
			System.out.println("task description: " + task.getDescription());
			System.out.println("task definition key: " + task.getTaskDefinitionKey());
			System.out.println("task owner: " + task.getOwner());
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("input", "2");
			taskService.complete(task.getId(),vars);
		}
		System.out.println("count " +taskService.createTaskQuery().count());
		
		List<Task> next= taskService.createTaskQuery().list();
		for(Task task:next){
			System.out.println("task available: " + task.getName());
			System.out.println("task id: " + task.getId());
			System.out.println("task description: " + task.getDescription());
			System.out.println("task definition key: " + task.getTaskDefinitionKey());
			System.out.println("task owner: " + task.getOwner());
			
			taskService.complete(task.getId());
		}
		
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		System.out.println("is ended?: " + processInstance.isEnded());
		HistoryService historyService = activitiRule.getHistoryService();
	    HistoricProcessInstance historicProcessInstance =
	      historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
	    System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
	  
		
	}
}