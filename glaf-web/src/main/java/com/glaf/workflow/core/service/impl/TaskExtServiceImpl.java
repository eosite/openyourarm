package com.glaf.workflow.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.rest.exception.ActivitiConflictException;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.util.ParamUtils;
import com.glaf.workflow.core.mapper.ProcessInsMappingMapper;
import com.glaf.workflow.core.service.TaskExtService;
import com.glaf.workflow.core.util.WorkflowConstant;

@Service("taskExtService")
@Transactional(readOnly = true)
public class TaskExtServiceImpl implements TaskExtService {
	protected RepositoryService repositoryService;
	protected ProcessInsMappingMapper processInsMappingMapper;
	protected IdentityService identityService;

	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@javax.annotation.Resource
	public void setProcessInsMappingMapper(ProcessInsMappingMapper processInsMappingMapper) {
		this.processInsMappingMapper = processInsMappingMapper;
	}

	@javax.annotation.Resource
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	protected RuntimeService runtimeService;

	protected TaskService taskService;

	@javax.annotation.Resource
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@javax.annotation.Resource
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	protected HistoryService historyService;

	@javax.annotation.Resource
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	protected RestResponseFactory restResponseFactory;

	@javax.annotation.Resource
	public void setRestResponseFactory(RestResponseFactory restResponseFactory) {
		this.restResponseFactory = restResponseFactory;
	}

	@Override
	public void jumpTask(String procInstId, String destTaskKey, String userId, String rejectMessage,
			Map<String, Object> variables) {

		// TODO Auto-generated method stub
		// 获得当前任务的对应实列
		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(procInstId).list();

		// 获得当前流程的定义模型
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(taskEntityList.get(0).getProcessDefinitionId());
		// 获得当前流程定义模型的所有任务节点
		List<ActivityImpl> activitilist = processDefinition.getActivities();
		int i = 0, size = taskEntityList.size();
		for (Task task : taskEntityList) {
			TaskEntity taskEntity = (TaskEntity) task;
			// 当前任务key
			String taskDefKey = taskEntity.getTaskDefinitionKey();

			// 获得当前活动节点和驳回的目标节点
			ActivityImpl currActiviti = null;// 当前活动节点
			ActivityImpl destActiviti = null;// 驳回目标节点
			int sign = 0;
			for (ActivityImpl activityImpl : activitilist) {
				// 确定当前活动activiti节点
				if (taskDefKey.equals(activityImpl.getId())) {
					currActiviti = activityImpl;
					sign++;
				} else if (destTaskKey.equals(activityImpl.getId())) {
					destActiviti = activityImpl;
					sign++;
				}
				// System.out.println("//-->activityImpl.getId():"+activityImpl.getId());
				// if (sign == 2) {
				// break;// 如果两个节点都获得,退出跳出循环
				// }
			}
			System.out.println("//-->currActiviti activityImpl.getId():" + currActiviti.getId());
			System.out.println("//-->destActiviti activityImpl.getId():" + destActiviti.getId());
			// 保存当前活动节点的流程参数
			List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(0);
			for (PvmTransition pvmTransition : currActiviti.getOutgoingTransitions()) {
				hisPvmTransitionList.add(pvmTransition);
			}
			TransitionImpl newTransitionImpl = null;

			if (i == size - 1) {
				// 清空当前活动节点的所有流出项
				System.out.println("//before-->currActiviti.getOutgoingTransitions().clear():"
						+ currActiviti.getOutgoingTransitions().size());
				currActiviti.getOutgoingTransitions().clear();
				System.out.println("//-->currActiviti.getOutgoingTransitions().clear():"
						+ currActiviti.getOutgoingTransitions().size());
				// 为当前节点动态创建新的流出项
				newTransitionImpl = currActiviti.createOutgoingTransition();
				// 为当前活动节点新的流出目标指定流程目标
				newTransitionImpl.setDestination(destActiviti);
			}

			if (i == 0) {

			}
			// 保存驳回意见
			taskEntity.setDescription(rejectMessage);// 设置驳回意见
			taskEntity.setAssignee(userId);
			taskService.saveTask(taskEntity);
			//获取当前任务定义key
			String currTaskDef=taskEntity.getTaskDefinitionKey();
			//获取当前任务的动态人参数
			String person = (String) taskService.getVariable(task.getId(), taskDefKey + "_user");  
			if(StringUtils.isNotEmpty(person)) {
				variables.put(taskDefKey + "_user", person);
			}else {
			    variables.put(taskDefKey + "_user", userId);
			}
			taskService.complete(taskEntity.getId(), variables);
			//获取任务定义key任务 并行会签任务自动完成
			List<Task> tasks=taskService.createTaskQuery().processInstanceId(procInstId).taskDefinitionKey(currTaskDef).list();
			while(tasks!=null&&tasks.size()>0) {
			for(Task defTask:tasks) {
				taskService.complete(defTask.getId(), null);
				// 清除历史空任务实例
				historyService.deleteHistoricTaskInstance(defTask.getId());
			 }
			    tasks=taskService.createTaskQuery().processInstanceId(procInstId).taskDefinitionKey(currTaskDef).list();
			}
			// taskService.deleteTask(taskEntity.getId(), true);
			// 设定驳回标志
			// Map<String, Object> variables = new HashMap<String, Object>(0);
			variables.put(WorkflowConstant.WF_VAR_IS_REJECTED, WorkflowConstant.IS_REJECTED);
			// 执行当前任务驳回到目标任务draft
			// taskService.complete(taskEntity.getId(), variables);
			if (i == 0) {
				// 清除目标节点的新流入项
			}

			if (i == size - 1) {
				destActiviti.getIncomingTransitions().remove(newTransitionImpl);
				currActiviti.getOutgoingTransitions().clear();
				// 还原原活动节点流出项参数
				currActiviti.getOutgoingTransitions().addAll(hisPvmTransitionList);
			}

			i++;
		}

	}

	/*
	 * public void jumpTask(String procInstId, String destTaskKey, String userId,
	 * String rejectMessage, Map<String, Object> variables) { TaskServiceImpl
	 * taskServiceImpl=(TaskServiceImpl)taskService; // 获得当前任务的对应实列 List<Task>
	 * taskEntityList =
	 * taskService.createTaskQuery().processInstanceId(procInstId).list(); //
	 * 获得当前流程的定义模型 ProcessDefinitionEntity processDefinition =
	 * (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
	 * .getDeployedProcessDefinition(taskEntityList.get(0).getProcessDefinitionId())
	 * ; // 获得当前流程定义模型的所有任务节点 List<ActivityImpl> activitilist =
	 * processDefinition.getActivities(); ActivityImpl currentActivity=null;
	 * ActivityImpl desActivity=null; for (ActivityImpl activityImpl : activitilist)
	 * { if (destTaskKey.equals(activityImpl.getId())) { desActivity = activityImpl;
	 * }else if
	 * (taskEntityList.get(0).getTaskDefinitionKey().equals(activityImpl.getId())) {
	 * currentActivity = activityImpl; }
	 * 
	 * } taskServiceImpl.getCommandExecutor().execute((new
	 * JumpTaskCmd(taskEntityList.get(0).getExecutionId(),currentActivity,
	 * desActivity,variables))); }
	 */
	/**
	 * 跳转至指定活动节点
	 * 
	 * @param targetTaskDefinitionKey
	 * @throws Exception
	 */
	public void jump(String _processId, String targetTaskDefinitionKey) throws Exception {
		TaskEntity currentTask = (TaskEntity) taskService.createTaskQuery().processInstanceId(_processId)
				.singleResult();
		jump(currentTask, targetTaskDefinitionKey);
	}

	/**
	 * 
	 * @param currentTaskEntity
	 *            当前任务节点
	 * @param targetTaskDefinitionKey
	 *            目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	private void jump(final TaskEntity currentTaskEntity, String targetTaskDefinitionKey) throws Exception {
		final ActivityImpl destActiviti = getActivityImplByTaskKey(currentTaskEntity.getProcessInstanceId(),
				targetTaskDefinitionKey);// 目标节点

		final ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery()
				.executionId(currentTaskEntity.getExecutionId()).singleResult();
		// 包装一个Command对象
		((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(new Command<java.lang.Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				// 创建新任务
				execution.setActivity(destActiviti);
				execution.executeActivity(destActiviti);
				// 删除当前的任务
				// 不能删除当前正在执行的任务，所以要先清除掉关联
				currentTaskEntity.setExecutionId(null);
				taskService.saveTask(currentTaskEntity);
				taskService.deleteTask(currentTaskEntity.getId(), true);
				return null;
			}
		});
	}

	/**
	 * 获取流程节点活动
	 * 
	 * @param currentTaskEntity
	 * @param targetTaskDefinitionKey
	 * @return
	 */
	public ActivityImpl getActivityImplByTaskKey(String processInst, String targetTaskDefinitionKey) {
		int sign = 0;
		ActivityImpl destActiviti = null;// 目标节点
		// 获得当前任务的对应实列
		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(processInst).list();
		// 获得当前流程的定义模型
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(taskEntityList.get(0).getProcessDefinitionId());
		// 获得当前流程定义模型的所有任务节点
		List<ActivityImpl> activitilist = processDefinition.getActivities();
		for (ActivityImpl activityImpl : activitilist) {
			if (targetTaskDefinitionKey.equals(activityImpl.getId())) {
				destActiviti = activityImpl;
				sign++;
			}
			// System.out.println("//-->activityImpl.getId():"+activityImpl.getId());
			if (sign == 2) {
				break;// 如果两个节点都获得,退出跳出循环
			}
		}
		return destActiviti;
	}

	@Override
	public void rejectTask(String procInstId, String destTaskKey, String userId, String rejectMessage) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(WorkflowConstant.SUBTYPE, WorkflowConstant.REJECT);
		params.put("approve", false);
		this.jumpTask(procInstId, destTaskKey, userId, rejectMessage, params);
	}

	@Override
	public void rejectPreTask(String procInstId, String userId, String rejectMessage) {
		// 获得当前任务的对应实列
		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(procInstId).list();
		Task taskEntity = taskEntityList.get(0);
		// 获得当前流程的定义模型
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(taskEntity.getProcessDefinitionId());
		// 获得当前流程定义模型的所有任务节点
		List<ActivityImpl> activitiList = processDefinition.getActivities();
		// 当前实例的执行到哪个节点
		String excId = taskEntity.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
				.singleResult();
		if (execution == null) {
			return;
		}
		String activitiId = execution.getActivityId();

		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				// System.out.println("当前任务：" +
				// activityImpl.getProperty("name")); // 输出某个节点的某种属性
				List<PvmTransition> inTransitions = activityImpl.getIncomingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : inTransitions) {
					PvmActivity ac = tr.getSource(); // 获取线路的开始节点
					rejectTask(procInstId, ac.getId(), userId, rejectMessage);
					return;
				}
				break;
			}
		}

	}

	@Override
	public ProcessInstanceResponse activateProcessInstance(String processInstanceId) {
		ProcessInstance processInstance = getProcessInstanceFromRequest(processInstanceId);
		return activateProcessInstance(processInstance);
	}

	protected ProcessInstanceResponse activateProcessInstance(ProcessInstance processInstance) {
		if (!processInstance.isSuspended()) {
			throw new ActivitiConflictException("流程实例 id '" + processInstance.getId() + "' 已经激活");
		}
		runtimeService.activateProcessInstanceById(processInstance.getId());
		ProcessInstanceResponse response = restResponseFactory.createProcessInstanceResponse(processInstance);
		// No need to re-fetch the instance, just alter the suspended state of
		// the result-object
		response.setSuspended(false);
		return response;
	}

	@Override
	public ProcessInstanceResponse suspendProcessInstance(String processInstanceId) {
		ProcessInstance processInstance = getProcessInstanceFromRequest(processInstanceId);
		return suspendProcessInstance(processInstance);
	}

	protected ProcessInstanceResponse suspendProcessInstance(ProcessInstance processInstance) {
		if (processInstance.isSuspended()) {
			throw new ActivitiConflictException("流程实例id '" + processInstance.getId() + "' 已经挂起.");
		}
		runtimeService.suspendProcessInstanceById(processInstance.getId());
		ProcessInstanceResponse response = restResponseFactory.createProcessInstanceResponse(processInstance);
		// No need to re-fetch the instance, just alter the suspended state of
		// the result-object
		response.setSuspended(true);
		return response;
	}

	protected ProcessInstance getProcessInstanceFromRequest(String processInstanceId) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance == null) {
			throw new ActivitiObjectNotFoundException("未找到流程实例 id '" + processInstanceId + "'.");
		}
		return processInstance;
	}

	@Override
	public void deleteProcessInstance(String processInstanceId, String deleteReason) {
		ProcessInstance processInstance = getProcessInstanceFromRequest(processInstanceId);
		runtimeService.deleteProcessInstance(processInstance.getId(), deleteReason);
	}

	@Override
	public List<ActivityImpl> getPermissRejectTasks(String processInstanceId) {
		Vector<ActivityImpl> activities = new Vector<ActivityImpl>();
		ProcessInstance processInstance = getProcessInstanceFromRequest(processInstanceId);
		// 获得当前流程的定义模型
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		// 获得当前任务的对应实列
		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		// 获得当前流程定义模型的所有任务节点
		List<ActivityImpl> activitiList = processDefinition.getActivities();
		// 当前任务名称
		String activitiId = null;
		for (Task task : taskEntityList) {
			activitiId = task.getTaskDefinitionKey();
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (!activitiId.equals(id)) {
					activities.add(activityImpl);
				} else {
					break;
				}
			}
		}

		return activities;
	}

	@Override
	public List<ActivityImpl> getPermissRejectTasksByTaskId(String processInstanceId, String taskId) {
		Vector<ActivityImpl> activities = new Vector<ActivityImpl>();
		ProcessInstance processInstance = getProcessInstanceFromRequest(processInstanceId);
		// 获得当前流程的定义模型
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		// 获得当前任务的对应实列
		Task taskEntity = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 获得当前流程定义模型的所有任务节点
		List<ActivityImpl> activitiList = processDefinition.getActivities();
		// 当前任务名称
		String activitiId = null;
		activitiId = taskEntity.getTaskDefinitionKey();
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (!activitiId.equals(id)) {
				activities.add(activityImpl);
			} else {
				break;
			}
		}
		return activities;
	}

	/**
	 * 获取当前节点定义ID（execoutionId 执行器ID）
	 */
	public String getActIdByExecoutionId(String execoutionId) {
		return processInsMappingMapper.getActIdByExecoutionId(execoutionId);
	}

	/**
	 * 获取ID（processInstId 流程实例ID）
	 */
	public String getActIdByProcessInstId(String processInstId) {
		return processInsMappingMapper.getActIdByProcessInstId(processInstId);
	}

	public void removeVariable(String processInstId, String varName) {
		processInsMappingMapper.removeVariable(processInstId, varName);
	}

	/**
	 * 清空流程动态用户变量值
	 * 
	 * @param processInstId
	 */
	public void emptyProcessInstVaribale(String processInstId,Vector<String> taskDefKeys) {
		processInsMappingMapper.emptyProcessInstVaribale(processInstId,taskDefKeys);
	}
	
	public void emptyAllProcessInstVaribale(String processInstId) {
		processInsMappingMapper.emptyAllProcessInstVaribale(processInstId);
	}

	public List<String> getTaskLastAssignee(String processInstId, String taskKey) {
		return processInsMappingMapper.getTaskLastAssignee(processInstId, taskKey);
	}

	/**
	 * 获取某个流程实例用户待办任务
	 * 
	 * @param processInstanceId
	 * @param userId
	 * @return
	 */
	public Task getUserTaskByProcessInstanceId(String processInstanceId, String userId) {
		Task userTask = null;
		// 获取用户待办任务
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(userId)
				.list();
		if (taskList != null && taskList.size() == 1)
			userTask = taskList.get(0);
		if (userTask == null) {
			List<Task> userTasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			// 获取任务的候选人
			List<IdentityLink> identityLinks = null;
			for (Task task : userTasks) {
				identityLinks = taskService.getIdentityLinksForTask(task.getId());
				for (IdentityLink identityLink : identityLinks) {
					if (identityLink.getUserId().equals(userId)) {
						userTask = task;
						return userTask;
					}
				}
			}
		}
		return userTask;
	}

	@Override
	public ProcessInstance startProcessInstanceByKeySkipEmptyTask(String processDefinitionKey,
			Map<String, Object> variables) {
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
		// 自动执行当前空任务
		runEmptyTask(pi.getProcessInstanceId());
		return pi;
	}

	@Override
	public ProcessInstance startProcessInstanceByKeySkipEmptyTask(String actorId, String processDefinitionKey,
			String businessKey, Map<String, Object> variables) {
		identityService.setAuthenticatedUserId(actorId);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
		// 自动执行当前空任务
		runEmptyTask(pi.getProcessInstanceId());
		return pi;
	}

	public void runEmptyTask(String processInstanceId) {
		// 获取未分配用户的任务
		// List<Task> taskList =
		// taskService.createTaskQuery().processInstanceId(processInstanceId).taskUnassigned().list();
		List<String> taskList = processInsMappingMapper.getUnassignedAndUnCandidateTasks(processInstanceId);
		while (taskList != null && taskList.size() > 0) {
			for (String taskId : taskList) {
				taskService.claim(taskId, null);
				taskService.complete(taskId, null);
				// 清除历史空任务实例
				historyService.deleteHistoricTaskInstance(taskId);
			}
			taskList = processInsMappingMapper.getUnassignedAndUnCandidateTasks(processInstanceId);
		}
	}

	@Override
	public void completeTaskSkipEmptyTask(String processInstanceId, String userId, Map<String, Object> variables) {
		// TODO Auto-generated method stub
		Task userTask = getUserTaskByProcessInstanceId(processInstanceId, userId);
		if (userTask != null) {
			String taskDefKey=userTask.getTaskDefinitionKey();
			// 写入当前流程节点变量
			variables.put("currentTask", userTask.getTaskDefinitionKey());
			taskService.claim(userTask.getId(), userId);
			variables.put("审核结果", true);
			if (variables.containsKey("approve")) {
				variables.put("approve", ParamUtils.getBoolean(variables, "approve"));
			} else {
				variables.put("approve", true);
			}
			//删除本任务节点的流程变量（顺序会签任务动态产生 上一个任务完成才产生下一个任务 因此依赖流程变量获取待办人 而此时的流程变量被重置为当前任务的执行者，因此取消覆盖）
			variables.remove(taskDefKey+"_user");
			taskService.complete(userTask.getId(), variables);
			//获取当前任务
			List<Task> tasks=taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			//获取当前所有任务定义
			Vector<String> taskDefkeys=new Vector<String>();
			for(Task task:tasks) {
				taskDefkeys.add(task.getTaskDefinitionKey()+"_user");
			}
			//清空当前待办任务外的流程变量
			emptyProcessInstVaribale(processInstanceId,taskDefkeys);
		}
		// 自动执行当前空任务
		runEmptyTask(processInstanceId);
	}
	/**
	 * 获取流程实例正在运行中的子流程实例
	 * @param processInstId
	 * @return
	 */
	public Vector<String> getRuntimeSubProcessInstBySuperProcessInst(String processInstId) {
		List<HistoricProcessInstance> subProcessInsts=historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(processInstId).unfinished().list();
		Vector<String> subProcessInstsVc=null;
		if(subProcessInsts!=null&&subProcessInsts.size()>0) {
			subProcessInstsVc=new Vector<String>();
		}else {
			return subProcessInstsVc;
		}
		for(HistoricProcessInstance processInstance:subProcessInsts) {
			subProcessInstsVc.add(processInstance.getId());
		}
		return subProcessInstsVc;
	}
	/**
	 * 获取流程实例的所有子流程实例
	 * @param processInstId
	 * @return
	 */
	public Vector<String> getSubProcessInstBySuperProcessInst(String processInstId) {
		List<HistoricProcessInstance> subProcessInsts=historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(processInstId).list();
		Vector<String> subProcessInstsVc=null;
		if(subProcessInsts!=null&&subProcessInsts.size()>0) {
			subProcessInstsVc=new Vector<String>();
		}else {
			return subProcessInstsVc;
		}
		for(HistoricProcessInstance processInstance:subProcessInsts) {
			subProcessInstsVc.add(processInstance.getId());
		}
		return subProcessInstsVc;
	}

	@Override
	public List<String> getSubProcessDefBySuperProcessDef(String superProcessDef) {
		return processInsMappingMapper.getSubProcessDefBySuperProcessDef(superProcessDef);
	}
}
