package com.glaf.pageworkflow.core.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@Transactional(readOnly = true)
public interface PageWorkFlowRepositoryService {
	/**
	 * 通过页面ID获取相应的流程定义
	 * 
	 * @param pageId
	 * @return
	 */
	public List<ProcessDefinition> getProcessDefinitionsByPageId(String pageId);

	/**
	 * 通过部署ID获取流程定义
	 * 
	 * @param deploymentId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId);

	/**
	 * 通过定义ID获取流程定义
	 * 
	 * @param deploymentId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByProcessDefId(String processDefId);

	/**
	 * 根据流程名称获取流程定义
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	public List<ProcessDefinition> getProcessDefinitionByProcessDefinitionKey(String processDefinitionKey);

	/**
	 * 获取分类下的流程定义
	 * 
	 * @param category
	 * @return
	 */
	public List<ProcessDefinition> getProcessDefinitionByCategory(String category);

	/**
	 * 获取页面流程定义
	 * 
	 * @param processDefinitions
	 * @return
	 */
	public List<JSONObject> getProcessDefinitionJson(List<ProcessDefinition> processDefinitions, String contextPath);

	/**
	 * 获取页面建模定义
	 * 
	 * @param pageId
	 * @return
	 */
	public List<Model> getModelsByPageId(String pageId);

	/**
	 * 获取页面建模定义
	 * 
	 * @param models
	 * @return
	 */
	public List<JSONObject> getModelJson(List<Model> models, Map<String, JSONObject> processDefinitionsMap);

	/**
	 * 获取页面流程模型与流程定义
	 * 
	 * @param pageId
	 * @return
	 */
	public List<JSONObject> getProcessModelByPageId(String pageId, String contextPath);

	/**
	 * 获取页面对应的控件集合
	 * 
	 * @param pageId
	 * @return
	 */
	public List<JSONObject> getPageCompentsByPageId(String pageId, String contextPath);

	/**
	 * 获取流程定义图
	 * 
	 * @param rootPath
	 *            根路径
	 * @param modelId
	 *            模型ID
	 * @return
	 */
	public String getProcessDiagramByModelId(String rootPath, Model modelData);

	/**
	 * 获取流程定义图
	 * 
	 * @param rootPath
	 * @param modelData
	 * @param deployId
	 * @return
	 */
	public String getProcessDiagramByDeployId(String rootPath, String deployId);

}