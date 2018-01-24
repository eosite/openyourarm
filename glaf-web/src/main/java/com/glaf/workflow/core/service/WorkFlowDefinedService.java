package com.glaf.workflow.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TableModel;
import com.glaf.form.core.domain.FormTaskmain;
import com.glaf.form.core.domain.FormWorkflowPlan;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Transactional(readOnly = true)
public interface WorkFlowDefinedService {
	/**
	 * 获取流程元素定义模板
	 * 
	 * @param elemType
	 * @return
	 */
	public Template getElemsetTemplate(String elemType);

	/**
	 * 获取流程元素定义模板JSON
	 * 
	 * @param elemType
	 * @return
	 */
	public String getElemsetTemplateJson(String elemType);

	/**
	 * 根据用户输入返回流程元素定义JSON
	 * 
	 * @param elemType
	 *            元素类型（如：用户任务、连接线）
	 * @param fieldMap
	 *            表单元素序列号后值对象 通过request.getParameterMap获取
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	public String getElemsetJsonByTemplate(String elemType, Map<String, String[]> fieldMap)
			throws TemplateException, IOException;

	/**
	 * 保存流程元素定义
	 * 
	 * @param creator
	 * @param elemType
	 * @param resourceId
	 * @param fieldMap
	 * @return
	 */
	public JSONObject saveElemSet(String creator, String modelId, String elemType, String resourceId,
			Map<String, String[]> fieldMap);

	/**
	 * 获取流程图路径
	 * 
	 * @param rootPath
	 * @param modelData
	 * @return
	 */
	public String getProcessDiagramByModelId(String rootPath, Model modelData);

	/**
	 * 根据部署ID获取流程定义
	 * 
	 * @param deploymentId
	 *            部署ID
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId);

	/**
	 * 根据流程定义KEY获取流程定义
	 * 
	 * @param key
	 * 
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByKey(String key);

	/**
	 * 根据部署ID获取流程图路径
	 * 
	 * @param rootPath
	 * @param deployId
	 * @return
	 */
	public String getProcessDiagramByDeployId(String rootPath, String deployId);

	/**
	 * 根据流程定义key pageId 业务表 id 获取流程实例
	 * 
	 * @param processDefinitionKey
	 * @param pageId
	 * @param id
	 * @return
	 */
	public ProcessInstance getProcessInstanceByDKeyAndPKeyAndBKey(String processDefinitionKey, String pageId,
			String id);

	/**
	 * 根据流程定义key pageId 业务表 id 获取流程实例
	 * 
	 * @param processDefinitionKey
	 * @param pageId
	 * @param id
	 * @return
	 */
	public ProcessInstance getProcessInstanceByDefKeyKeyAndBussinessKey(String processDefinitionKey,
			String bussinessKey);

	/**
	 * 获取任务执行人员
	 * 
	 * @param delegateTask
	 * @return
	 */
	public Set<String> getUserTaskAssigns(ActivityExecution execution);

	/**
	 * 根据实例编号查找下一个任务节点
	 * 
	 * @param procInstId
	 * @param processDefinitionId
	 * @return
	 */
	PvmActivity getNextTaskDefinition(String procInstId, String processDefinitionId);

	/**
	 * 根据实例编号查找上一个任务节点
	 * 
	 * @param procInstId
	 * @param processDefinitionId
	 * @return
	 */
	PvmActivity getPrevTaskDefinition(String procInstId, String processDefinitionId);

	/**
	 * 启动流程
	 * 
	 * @param formWorkflowPlans
	 * @return
	 */
	ProcessInstance startProcess(List<FormWorkflowPlan> formWorkflowPlans, Map<String, Object> params, String actorId);

	
	public void runSubProcess(Map<String, Object> params//
			, String actorId, String processId, String defId);
	
	/**
	 * 保存表单流程信息
	 * 
	 * @param formWorkflowPlans
	 * @param formTaskmain
	 * @param params
	 * @param actorId
	 */
	void saveFormTask(List<FormWorkflowPlan> formWorkflowPlans, FormTaskmain formTaskmain, Map<String, Object> params,
			String actorId);

	/**
	 * 获取页面主键id的形参 如 :col33439043904
	 * 
	 * @param pageId
	 * @param idKey
	 * @return
	 */
	String getIdVariable(String pageId, String idKey);

	/**
	 * 根据页面id 动态生成一条实例数据
	 */
	TableModel createNewInstanceByPageId(String pageId, String actorId);

	/**
	 * 获取当前实例的发起人
	 * 
	 * @param processId
	 * @return
	 */
	String getSubmitterByProcessId(String processId);
}
