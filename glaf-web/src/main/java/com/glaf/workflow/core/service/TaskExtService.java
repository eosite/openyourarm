package com.glaf.workflow.core.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;

import com.google.protobuf.ServiceException;

public interface TaskExtService {
	/**
	 * 自由流 回退到流程节点
	 * 
	 * @param procInstId
	 *            流程实例编号
	 * @param destTaskKey
	 *            目标节点key
	 * @param userId
	 *            回退人
	 * @param rejectMessage
	 *            回退原因
	 * @throws ServiceException
	 */
	public void rejectTask(String procInstId, String destTaskKey, String userId, String rejectMessage)
			throws ServiceException;

	/**
	 * 回退到上一个节点（撤销）
	 * 
	 * @param procInstId
	 *            流程实例编号
	 * @param userId
	 *            操作人
	 * @param rejectMessage
	 *            撤销原因
	 */
	public void rejectPreTask(String procInstId, String userId, String rejectMessage);

	/**
	 * 激活流程实例
	 * 
	 * @param processInstanceId
	 *            流程实例编号
	 * @return
	 */
	public ProcessInstanceResponse activateProcessInstance(String processInstanceId);

	/**
	 * 暂挂流程实例
	 * 
	 * @param processInstanceId
	 *            流程实例编号
	 * @return
	 */
	public ProcessInstanceResponse suspendProcessInstance(String processInstanceId);

	/**
	 * 终止流程实例
	 * 
	 * @param processInstanceId
	 *            流程实例编号
	 * @param deleteReason
	 *            终止原因
	 */
	public void deleteProcessInstance(String processInstanceId, String deleteReason);

	/**
	 * 获取所有可退回节点
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List<ActivityImpl> getPermissRejectTasks(String processInstanceId);

	/**
	 * 获取某个节点所有可退回节点
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @param taskId
	 *            任务实例ID
	 * @return
	 */
	public List<ActivityImpl> getPermissRejectTasksByTaskId(String processInstanceId, String taskId);

	/**
	 * 流程跳转指定节点
	 * 
	 * @param processId
	 * @param destTaskKey
	 * @param actorId
	 * @param string
	 * @param params
	 * @throws ServiceException
	 */
	public void jumpTask(String processId, String destTaskKey, String actorId, String string,
			Map<String, Object> params) throws ServiceException;
	/**
	 * 跳转到指定节点
	 * @param _processId
	 * @param targetTaskDefinitionKey
	 */
	public void jump(String _processId, String targetTaskDefinitionKey) throws Exception ;
	/**
	 * 根据执行器ID获得任务ID
	 * @param execoutionId
	 * @return
	 */
	public String getActIdByExecoutionId(String execoutionId);
	/**
	 * 获取最近一个执行器对应的任务ID
	 * @param processInstId
	 * @return
	 */
	public String getActIdByProcessInstId(String processInstId);
	/**
	 * 删除流程变量
	 * @param processInstId
	 * @param varName
	 */
	public void removeVariable(String processInstId, String varName);
	
	public void emptyAllProcessInstVaribale(String processInstId);
	
	public void emptyProcessInstVaribale(String processInstId,Vector<String> taskDefKeys);
	/**
	 * 获取任务最近一次的执行者
	 * @param processInstId
	 * @param taskKey
	 * @return
	 */
	public List<String> getTaskLastAssignee(String processInstId,String taskKey);
	/**
	 * 获取用户待办任务
	 * @param processInstanceId
	 * @param userId
	 * @return
	 */
	public Task getUserTaskByProcessInstanceId(String processInstanceId, String userId);
	/**
	 * 启动流程跳过空任务（未分配执行者的任务）
	 * @param processDefinitionKey
	 * @param variables
	 * @return
	 */
	ProcessInstance startProcessInstanceByKeySkipEmptyTask(String processDefinitionKey,Map<String,Object> variables);
	/**
	 * 执行任务并跳过空任务（未分配执行者的任务）
	 * @param taskId
	 * @param variables
	 */
	void completeTaskSkipEmptyTask(String processInstanceId,String userId, Map<String, Object> variables);

	ProcessInstance startProcessInstanceByKeySkipEmptyTask(String actorId, String processDefinitionKey,
			String businessKey, Map<String, Object> variables);
	/**
	 * 获取流程实例正在运行中的子流程实例
	 * @param processInstId
	 * @return
	 */
	Vector<String> getRuntimeSubProcessInstBySuperProcessInst(String processInstId);
	/**
	 * 获取流程实例的所有子流程实例
	 * @param processInstId
	 * @return
	 */
	Vector<String> getSubProcessInstBySuperProcessInst(String processInstId);
	/**
	 * 根据父流程key获取子流程key集合
	 * @param superProcessDef 流程定义id
	 * @return
	 */
	List<String> getSubProcessDefBySuperProcessDef(String superProcessDef);
}
