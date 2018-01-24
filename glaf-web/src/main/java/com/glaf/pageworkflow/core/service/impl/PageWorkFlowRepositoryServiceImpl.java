package com.glaf.pageworkflow.core.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.FileUtils;
import com.glaf.form.core.domain.FormPageEventProcDef;
import com.glaf.form.core.service.FormPageEventProcDefService;
import com.glaf.isdp.util.JSONConvertUtil;
import com.glaf.pageworkflow.core.domain.ComponentProperty;
import com.glaf.pageworkflow.core.domain.PageComponent;
import com.glaf.pageworkflow.core.mapper.PageComponentMapper;
import com.glaf.pageworkflow.core.service.PageWorkFlowRepositoryService;


@Service("pageWorkFlowRepositoryService")
@Transactional(readOnly = true)
public class PageWorkFlowRepositoryServiceImpl implements
		PageWorkFlowRepositoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected FormPageEventProcDefService formPageEventProcDefService;
	protected RepositoryService repositoryService;
	protected PageComponentMapper pageComponentMapper;

	@Resource
	public void setFormPageEventProcDefService(
			FormPageEventProcDefService formPageEventProcDefService) {
		this.formPageEventProcDefService = formPageEventProcDefService;
	}

	@Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Resource
	public void setPageComponentMapper(PageComponentMapper pageComponentMapper) {
		this.pageComponentMapper = pageComponentMapper;
	}

	/**
	 * 获取页面流程定义
	 * 
	 * @param pageId
	 * @return
	 */
	@Override
	public List<ProcessDefinition> getProcessDefinitionsByPageId(String pageId) {
		// 根据页面ID获取流程定义ID
		Set<String> processDefIds = new HashSet<String>();
		// 接口待实现
		List<ProcessDefinition> processDefinitions = repositoryService
				.createProcessDefinitionQuery().latestVersion()
				.orderByProcessDefinitionCategory().asc()
				.orderByProcessDefinitionKey().asc().list();
		return processDefinitions;
	}

	/**
	 * 获取页面建模定义
	 * 
	 * @param pageId
	 * @return
	 */
	@Override
	public List<Model> getModelsByPageId(String pageId) {
		// 根据页面ID获取流程建模ID
		Set<String> processDefIds = new HashSet<String>();
		// 接口待实现
		List<Model> models = repositoryService.createModelQuery().list();
		return models;
	}

	/**
	 * 获取页面模型定义
	 */
	@Override
	public List<JSONObject> getModelJson(List<Model> models,
			Map<String, JSONObject> processDefinitionsMap) {
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		JSONObject jsonObject = null;
		String deploymentId = null;
		for (Model model : models) {
			deploymentId = model.getDeploymentId();
			if (!StringUtils.isEmpty(deploymentId)
					&& processDefinitionsMap != null
					&& processDefinitionsMap.containsKey(deploymentId)) {
				jsonObject = processDefinitionsMap.get(deploymentId);
				jsonObject.put("modelId", model.getId());
				processDefinitionsMap.put(deploymentId, jsonObject);
			} else {
				try {
					jsonObject = JSONConvertUtil.toJSONObject(model);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonObject.put("modelId", model.getId());
				jsonObject.put("pId", 0);
				jsonObject.put("code", model.getKey());
				jsonObject.put("name",
						model.getName() + "_V" + model.getVersion());
				jsonObject.put("t", model.getMetaInfo() == null ? ""
						: JSON.parseObject(model.getMetaInfo())
								.getString("description"));
				jsonObject.put("value", model.getKey());
				jsonObject.put("icon", "/glaf/images/lightbulb.png");
				if (StringUtils.isEmpty(model.getDeploymentId())) {
					jsonObject.put("icon", "/glaf/images/lightbulb_off.png");
				}
				jsonObjects.add(jsonObject);
			}
		}
		// 合并Model与ProcessDefinition
		List<JSONObject> allJSONObjects = new ArrayList<JSONObject>();
		allJSONObjects.addAll(jsonObjects);
		if (processDefinitionsMap != null)
			allJSONObjects.addAll(processDefinitionsMap.values());
		return allJSONObjects;
	}

	/**
	 * List 转 Map
	 * 
	 * @param processDefinitions
	 * @return
	 */
	public Map<String, JSONObject> getProcessDefinitionsMapByList(
			List<JSONObject> processDefinitions) {
		Map<String, JSONObject> processDefinitionsMap = new HashMap<String, JSONObject>();
		for (JSONObject jsonObject : processDefinitions) {
			processDefinitionsMap.put(jsonObject.getString("deploymentId"),
					jsonObject);
		}
		return processDefinitionsMap;
	}

	@Override
	public ProcessDefinition getProcessDefinitionByProcessDefId(
			String processDefId) {
		// TODO Auto-generated method stub
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefId).singleResult();
		return processDefinition;
	}

	@Override
	public List<ProcessDefinition> getProcessDefinitionByProcessDefinitionKey(
			String processDefinitionKey) {
		// TODO Auto-generated method stub
		List<ProcessDefinition> processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).list();
		return processDefinition;
	}

	@Override
	public List<ProcessDefinition> getProcessDefinitionByCategory(
			String category) {
		List<ProcessDefinition> processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionCategory(category).list();
		return processDefinition;
	}

	@Override
	public ProcessDefinition getProcessDefinitionByDeploymentId(
			String deploymentId) {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().deploymentId(deploymentId)
				.singleResult();
		return processDefinition;
	}

	/**
	 * 获取流程定义树形结构显示JSON
	 * 
	 * @param processDefinitions
	 * @return
	 */
	@Override
	public List<JSONObject> getProcessDefinitionJson(
			List<ProcessDefinition> processDefinitions, String contextPath) {
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		JSONObject jsonObject = null;
		for (ProcessDefinition processDefinition : processDefinitions) {
			try {
				jsonObject = JSONConvertUtil.toJSONObject(processDefinition);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonObject.put("processDefId", processDefinition.getId());
			jsonObject.put("pId", 0);
			jsonObject.put("code", processDefinition.getKey());
			jsonObject.put("name", processDefinition.getName() + "_V"
					+ processDefinition.getVersion());
			jsonObject.put("t", processDefinition.getDescription() == null ? ""
					: processDefinition.getDescription());
			jsonObject.put("value", processDefinition.getKey());
			jsonObject.put("icon", contextPath + "/images/lightbulb.png");
			if (StringUtils.isEmpty(processDefinition.getDeploymentId())) {
				jsonObject.put("icon", contextPath
						+ "/images/lightbulb_off.png");
			}

			jsonObjects.add(jsonObject);
		}
		return jsonObjects;
	}

	@Override
	public List<JSONObject> getProcessModelByPageId(String pageId,
			String contextPath) {
		List<ProcessDefinition> processDefinitions = getProcessDefinitionsByPageId(pageId);
		Map<String, JSONObject> processDefinitionsMap = null;
		if (processDefinitions != null && processDefinitions.size() > 0) {
			List<JSONObject> processDefinitionJsons = getProcessDefinitionJson(
					processDefinitions, contextPath);
			processDefinitionsMap = getProcessDefinitionsMapByList(processDefinitionJsons);
		}
		List<Model> models = getModelsByPageId(pageId);
		List<JSONObject> modelJsons = getModelJson(models,
				processDefinitionsMap);
		return modelJsons;
	}

	@Override
	public List<JSONObject> getPageCompentsByPageId(String pageId,
			String contextPath) {
		// TODO Auto-generated method stub
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		List<PageComponent> pageComponents = pageComponentMapper
				.getPageComponentsByPageId(pageId);
		// 获取页面控件事件
		List<ComponentProperty> pageComponentPropertyList = getComponentEventPropertiesByPageId(pageId);
		Map<String, List<ComponentProperty>> componentEventPropertyMap = getComponentEventPropertyMap(pageComponentPropertyList);
		// 获取页面控件事件流程
		Map<String, FormPageEventProcDef> procDefMap = formPageEventProcDefService
				.getFormPageEventProcDefMapByPageId(pageId);
		JSONObject jsonObject = null;
		Set<Long> compIds = new HashSet<Long>();
		String compType = null;
		String key = null;
		FormPageEventProcDef formPageEventProcDef = null;
		List<ComponentProperty> componentPropertyList = null;
		for (PageComponent pageComponent : pageComponents) {

			try {
				jsonObject = JSONConvertUtil.toJSONObject(pageComponent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 添加控件库定义
			if (!compIds.contains(pageComponent.getCompId())) {
				JSONObject compJsonObject = new JSONObject();
				compJsonObject.put("pId", 0);
				compJsonObject.put("id", pageComponent.getCompId());
				compJsonObject.put("name", pageComponent.getCompName());
				compJsonObject.put("t", pageComponent.getCompName());
				compJsonObject.put("icon",
						contextPath + pageComponent.getIcon());
				compJsonObject.put("open", true);
				compIds.add(pageComponent.getCompId());
				jsonObjects.add(compJsonObject);

			}
			// 添加页面控件实例
			jsonObject.put("pId", pageComponent.getCompId());
			jsonObject.put("id", pageComponent.getPageCompId());
			jsonObject.put("t", pageComponent.getCompName() + ":"
					+ pageComponent.getPageCompName());
			jsonObject.put("name", pageComponent.getPageCompName());
			jsonObject.put("icon", contextPath + pageComponent.getIcon());
			jsonObject.put("nodeType", "component");
			jsonObject.put("open", true);
			jsonObjects.add(jsonObject);
			// 添加控件下的事件
			compType = pageComponent.getCompType();

			if (componentEventPropertyMap.containsKey(compType)) {
				componentPropertyList = componentEventPropertyMap.get(compType);
				for (ComponentProperty componentProperty : componentPropertyList) {
					key = pageId + "_" + pageComponent.getPageCompId() + "_"
							+ componentProperty.getName();
					// 创建事件节点
					jsonObject = new JSONObject();
					jsonObject.put("pId", pageComponent.getPageCompId());
					jsonObject.put("compName", pageComponent.getPageCompName());
					jsonObject.put("id", pageComponent.getPageCompId() + "_"
							+ componentProperty.getId());
					jsonObject.put("t", componentProperty.getTitle());
					jsonObject.put("name", componentProperty.getTitle());
					jsonObject.put("icon", contextPath
							+ "/images/lightning.png");
					jsonObject.put("nodeType", "event");
					jsonObject.put("event", componentProperty.getName());
					// 获取事件对应的流程定义
					if (procDefMap.containsKey(key)) {
						formPageEventProcDef = procDefMap.get(key);
						jsonObject.put("eventProcId",
								formPageEventProcDef.getId());
						jsonObject.put("procDefkey",
								formPageEventProcDef.getProcDefKey());
						jsonObject.put("procDefId",
								formPageEventProcDef.getProcDefId());
						jsonObject.put("procDeployId",
								formPageEventProcDef.getProcDeployId());
						jsonObject.put("procDeployStatus", formPageEventProcDef.getProcDeployStatus());
						jsonObject.put("procModelId",
								formPageEventProcDef.getProcModelId());
						jsonObject.put("icon", contextPath
								+ "/images/lightbulb.png");
						if (formPageEventProcDef.getProcDeployStatus()==null||!formPageEventProcDef.getProcDeployStatus().equals("1")) {
							jsonObject.put("icon", contextPath
									+ "/images/lightbulb_off.png");
						}
					}
					jsonObjects.add(jsonObject);
				}
			}
			componentPropertyList = null;
			formPageEventProcDef = null;
		}
		// 根据控件ID集合获取页面控件
		return jsonObjects;
	}

	public List<ComponentProperty> getComponentEventPropertiesByPageId(
			String pageId) {

		return pageComponentMapper.getComponentEventPropertiesByPageId(pageId);
	}

	/**
	 * List 转 Map Key componentType
	 * 
	 * @param formComponentPropertyList
	 * @return
	 */
	public Map<String, List<ComponentProperty>> getComponentEventPropertyMap(
			List<ComponentProperty> formComponentPropertyList) {
		Map<String, List<ComponentProperty>> componentPropertyMap = new HashMap<String, List<ComponentProperty>>();
		List<ComponentProperty> componentPropertyList = null;
		for (ComponentProperty componentProperty : formComponentPropertyList) {
			if (!componentPropertyMap.containsKey(componentProperty
					.getCompType())) {
				componentPropertyList = new ArrayList<ComponentProperty>();
			} else {
				componentPropertyList = componentPropertyMap
						.get(componentProperty.getCompType());
			}
			componentPropertyList.add(componentProperty);
			componentPropertyMap.put(componentProperty.getCompType(),
					componentPropertyList);
		}
		return componentPropertyMap;
	}

	@Override
	public String getProcessDiagramByModelId(String rootPath, Model modelData) {
		byte[] editorSourceExtra = repositoryService
				.getModelEditorSourceExtra(modelData.getId());
		InputStream inStream = null;
		String fileParentPath = null;
		String filePath = null;
		String relPath = null;
		File saveFileDoc = null;
		if (editorSourceExtra != null) {
			try {

				String fileName = modelData.getId() + "_" + modelData.getKey()
						+ ".png";
				fileParentPath = rootPath + "/workflow/diagram/";
				saveFileDoc = new File(fileParentPath);
				if (!saveFileDoc.exists()) {
					saveFileDoc.mkdirs();
				}
				filePath = fileParentPath + fileName;
				if (!new File(filePath).exists()) {
					inStream = new ByteArrayInputStream(editorSourceExtra);
					FileUtils.save(filePath, inStream);
				}
				relPath = "/workflow/diagram/" + fileName;
			} catch (Exception e) {
				logger.error("getProcessDiagramByModelId:" + e.getMessage());
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					inStream = null;
				}
			}
		}
		return relPath;
	}

	/**
	 * 生成流程图
	 * 
	 * @param rootPath
	 * @param proDefId
	 * @return
	 */
	@Override
	public String getProcessDiagramByDeployId(String rootPath,
			String deployId) {

		String fileParentPath = null;
		String filePath = null;
		String relPath = null;
		File saveFileDoc = null;
		InputStream inStream = null;
		try {

			List<String> names = repositoryService
					.getDeploymentResourceNames(deployId);
			String imageName = null;
			for (String name : names) {
				if (name.indexOf(".png") >= 0) {
					imageName = name;
				}
			}
			if (imageName != null) {
				// 通过部署ID和文件名称得到文件的输入流
				inStream = repositoryService.getResourceAsStream(deployId,
						imageName);
				fileParentPath = rootPath + "/workflow/diagram/";
				saveFileDoc = new File(fileParentPath);
				if (!saveFileDoc.exists()) {
					saveFileDoc.mkdirs();
				}
				String fileName = deployId
						+ ".png";
				filePath = fileParentPath + fileName;
				if (!new File(filePath).exists()) {
					FileUtils.save(filePath, inStream);
				}
				relPath = "/workflow/diagram/" + fileName;
			}
		} catch (Exception e) {
			logger.error("getProcessDiagramByModelId:" + e.getMessage());
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				inStream = null;
			}
		}
		return relPath;
	}
}
