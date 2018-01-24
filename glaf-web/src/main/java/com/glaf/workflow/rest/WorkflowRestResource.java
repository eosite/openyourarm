package com.glaf.workflow.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.explorer.util.XmlUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.workflow.core.service.ActReElementDefService;
import com.glaf.workflow.core.service.TaskExtService;
import com.glaf.workflow.core.service.WorkFlowDefinedService;
import com.google.protobuf.ServiceException;

@Controller
@Path("/rs/workflow")
public class WorkflowRestResource {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected RepositoryService repositoryService;
	protected ActReElementDefService actReElementDefService;
	protected IdentityService identityService;
	protected TaskExtService taskExtService;
	@javax.annotation.Resource
	public void setActReElementDefService(ActReElementDefService actReElementDefService) {
		this.actReElementDefService = actReElementDefService;
	}

	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@javax.annotation.Resource
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	protected WorkFlowDefinedService workFlowDefinedService;

	@javax.annotation.Resource
	public void setWorkFlowDefinedService(WorkFlowDefinedService workFlowDefinedService) {
		this.workFlowDefinedService = workFlowDefinedService;
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
	@javax.annotation.Resource
	public void setTaskExtService(TaskExtService taskExtService) {
		this.taskExtService = taskExtService;
	}

	/**
	 * 新建模型
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/createWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] createWorkFlow(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		try {
			String name = RequestUtils.getString(request, "modelName", "");
			String key = "PROCESS_" + new Date().getTime();
			String description = RequestUtils.getString(request, "modelDesc", "");
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.set("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();
			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(StringUtils.defaultString(key));
			modelData.setCategory("workflow");
			String categoryId=RequestUtils.getString(request, "categoryId", "");
			if(!categoryId.equals("0")&&!categoryId.equals("-1"))
			{
			  //用户talendId保存分类categoryId
		      modelData.setTenantId(categoryId);
			}
			repositoryService.saveModel(modelData);
			// 创建流程定义初始化结构
			JSONObject modelEditorSourceJSON=createModelEditorSource(modelData.getId(),name,key);
			repositoryService.addModelEditorSource(modelData.getId(), modelEditorSourceJSON.toJSONString().getBytes("utf-8"));
			jsonObj.put("modelId", modelData.getId());
			jsonObj.put("result", 1);
			jsonObj.put("modelKey", key);

			// response.sendRedirect(request.getContextPath() +
			// "/service/editor?id=" + modelData.getId());
		} catch (Exception e) {
			logger.error("创建模型失败：", e);
			jsonObj.put("result", -1);
		}
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	public JSONObject createModelEditorSource(String modelId, String modelName, String modelKey) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resourceId", modelId);
		JSONObject propJson = new JSONObject();
		propJson.put("process_id", modelKey);
		propJson.put("process_namespace", "http://www.activiti.org/processdef");
		propJson.put("executionlisteners", "");
		propJson.put("eventlisteners", "");
		propJson.put("documentation", "");
		propJson.put("name", modelName);
		propJson.put("process_version", "");
		propJson.put("process_author", "");
		jsonObject.put("properties", propJson);
		JSONObject stencil = new JSONObject();
		stencil.put("id", "BPMNDiagram");
		jsonObject.put("stencil", stencil);
		jsonObject.put("childShapes", new JSONArray());

		JSONObject boundsJson = new JSONObject();
		JSONObject upperLeftJson = new JSONObject();
		upperLeftJson.put("x", 0);
		upperLeftJson.put("y", 0);
		boundsJson.put("upperLeft", upperLeftJson);
		JSONObject lowerRightJson = new JSONObject();
		lowerRightJson.put("x", 1200);
		lowerRightJson.put("y", 1050);
		boundsJson.put("lowerRight", lowerRightJson);
		jsonObject.put("bounds", boundsJson);

		JSONObject stencilsetJson = new JSONObject();
		stencilsetJson.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		stencilsetJson.put("url", "stencilsets/bpmn2.0/bpmn2.0.json");
		jsonObject.put("stencilset", stencilsetJson);
		jsonObject.put("ssextensions", new JSONArray());
		return jsonObject;
	}

	/**
	 * 新建模型
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/convertWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] convertWorkFlow(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		try {
			String processDefId = RequestUtils.getString(request, "processDefId", "");
			if (!StringUtils.isEmpty(processDefId)) {
				// 根据ID获取ProcessDefinition
				ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(processDefId).singleResult();
				String name = processDefinition.getName();
				String key = processDefinition.getKey();
				try {
					InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
							processDefinition.getResourceName());
					XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
					InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
					XMLStreamReader xtr = xif.createXMLStreamReader(in);
					BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

					if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {

						logger.error("转换模型失败：请确认文件中包含有效的BPMN定义");
						jsonObj.put("result", 0);
					} else {

						if (bpmnModel.getLocationMap().isEmpty()) {

							logger.error("转换模型失败：请确认文件中包含BPMN DI信息");
							jsonObj.put("result", 0);
						} else {
							BpmnJsonConverter converter = new BpmnJsonConverter();
							ObjectNode modelNode = converter.convertToJson(bpmnModel);
							Model modelData = repositoryService.newModel();
							ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
							modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
							modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
							modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
									processDefinition.getDescription());
							modelData.setMetaInfo(modelObjectNode.toString());
							modelData.setName(processDefinition.getName());
							modelData.setCategory("workflow");
							repositoryService.saveModel(modelData);
							repositoryService.addModelEditorSource(modelData.getId(),
									modelNode.toString().getBytes("utf-8"));
							jsonObj.put("result", 1);
							jsonObj.put("modelId", modelData.getId());
						}
					}

				} catch (Exception e) {
					logger.error("转换模型失败：", e);
					jsonObj.put("result", -1);
				}
			}
		} catch (Exception e) {
			logger.error("转换模型失败：", e);
			jsonObj.put("result", -1);
		}

		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	/**
	 * 删除模型
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/deleteWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteWorkFlow(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		String modelId = RequestUtils.getString(request, "modelId", "");
		if (!StringUtils.isEmpty(modelId)) {
			repositoryService.deleteModel(modelId);
		}
		jsonObj.put("result", 1);
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	/**
	 * 复制流程定义扩展配置
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/copyWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] copyWorkFlow(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		String modelId = RequestUtils.getString(request, "modelId", "");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (!StringUtils.isEmpty(modelId)) {
			if (actReElementDefService.copyWorkFlow(modelId, loginContext.getActorId())) {
				jsonObj.put("result", 1);
			} else {
				jsonObj.put("result", 0);
			}
		}

		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	/**
	 * 发布流程
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/deployWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deployWorkFlow(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		String modelId = RequestUtils.getString(request, "modelId", "");
		if (!StringUtils.isEmpty(modelId)) {
			try {
				Model modelData = repositoryService.getModel(modelId);
				// 检测当前model是否已经发布过
				if (StringUtils.isEmpty(modelData.getDeploymentId())) {

					ObjectNode modelNode = (ObjectNode) new ObjectMapper()
							.readTree(repositoryService.getModelEditorSource(modelData.getId()));
					BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
					byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
					String processName = modelData.getKey() + ".bpmn20.xml";
					Deployment deployment = this.repositoryService.createDeployment().name(modelData.getKey())
							.addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
					// 更新扩展规则定义表ACT_RE_ELEMENTDEF PROCEDEF_ID_
					ProcessDefinition processDef = this.repositoryService.createProcessDefinitionQuery()
							.deploymentId(deployment.getId()).singleResult();
					actReElementDefService.updateProcedefIdByModelId(modelId, processDef.getId(), deployment.getId());
					jsonObj.put("message", "部署成功，部署ID=" + deployment.getId());
					jsonObj.put("result", 1);
				} else {
					jsonObj.put("message", "模型之前已部署过ID=" + modelData.getDeploymentId());
					jsonObj.put("result", 1);
				}
			} catch (Exception e) {
				logger.error("根据模型部署流程失败：modelId={}", modelId, e);
				jsonObj.put("result", -1);
			}
		}

		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	/**
	 * 导出流程模型
	 * 
	 * @param request
	 * @param response
	 */
	@GET
	@POST
	@Path("/exportWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void exportWorkFlow(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		String modelId = RequestUtils.getString(request, "modelId", "");
		try {
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
	//		IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
	//		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
	//		response.setContentType("application/OCTET-STREAM;charset=UTF-8");
	//		response.flushBuffer();
			
			ResponseUtils.download(request, response, in, filename);
			
			
		} catch (Exception e) {
			logger.error("导出model的xml文件失败：modelId={}", modelId, e);
		}
	}

	/**
	 * 导出流程定义
	 * 
	 * @param request
	 * @param response
	 */
	@GET
	@POST
	@Path("/exportWorkFlowByProcDefId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void exportWorkFlowByProcessDefinitionId(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String processDefinitionId = RequestUtils.getString(request, "processDefinitionId", "");
		try {
			BpmnModel bpmnModel = null;
			ProcessDefinition processDefinition = null;
			if (processDefinitionId.trim().length() > 0) {
				processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
				if (processDefinition != null)
					bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
				else
					logger.error("未找到流程定义：processDefinitionId={}", processDefinitionId);
			} else {
				logger.error("流程定义唯一标识processDefinitionId为空");
			}
			if (bpmnModel != null) {
				BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
				byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
				ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
				String filename = processDefinition.getKey() + "_" + processDefinition.getVersion() + ".bpmn20.xml";
			//	response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			//	response.setContentType("application/OCTET-STREAM;charset=UTF-8");
			//	IOUtils.copy(in, response.getOutputStream());
			//	response.flushBuffer();
				
				ResponseUtils.download(request, response, in, filename);
			} else {
				logger.error("未找到流程定义模型：processDefinitionId={}", processDefinitionId);
			}
		} catch (Exception e) {
			logger.error("导出model的xml文件失败：processDefinitionId={}", processDefinitionId, e);
		}
	}

	/**
	 * 导入流程定义
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/importWorkFlow")
	@ResponseBody
	@Produces({ MediaType.TEXT_HTML })
	@javax.ws.rs.Consumes({ MediaType.MULTIPART_FORM_DATA })
	public byte[] importWorkFlow(@Context HttpServletRequest request, @RequestParam("uploadfile") MultipartFile file)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		ByteArrayOutputStream outputStream = null;
		Model modelData = null;
		String fileName = file.getName();
		try {
			try {
				if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn")) {
					XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
					XMLStreamReader xtr = xif.createXMLStreamReader(file.getInputStream());
					BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

					if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {
						logger.error("转换模型失败：请确认文件中包含有效的BPMN定义");
						jsonObj.put("result", 0);
					} else {

						if (bpmnModel.getLocationMap().isEmpty()) {
							logger.error("转换模型失败：请确认文件中包含BPMN DI信息");
							jsonObj.put("result", 0);
						} else {

							String processName = null;
							if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
								processName = bpmnModel.getMainProcess().getName();
							} else {
								processName = bpmnModel.getMainProcess().getId();
							}

							modelData = repositoryService.newModel();
							ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
							modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
							modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
							modelData.setMetaInfo(modelObjectNode.toString());
							modelData.setName(processName);
							modelData.setCategory("workflow");
							String categoryId=RequestUtils.getString(request, "categoryId", "");
							if(!categoryId.equals("0")&&!categoryId.equals("-1"))
							{
							  //用户talendId保存分类categoryId
						      modelData.setTenantId(categoryId);
							}
							repositoryService.saveModel(modelData);

							BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
							ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);

							repositoryService.addModelEditorSource(modelData.getId(),
									editorNode.toString().getBytes("utf-8"));

							jsonObj.put("modelId", modelData.getId());

						}
					}
				} else {
					jsonObj.put("result", 0);
				}
			} catch (Exception e) {
				jsonObj.put("result", -1);
			}
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					jsonObj.put("result", -1);
				}
			}
		}

		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	@GET
	@Path("/startWorkflowProcess")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] startWorkflowProcess(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		String processDefinitionKey = RequestUtils.getString(request, "processDefinitionKey", "");
		JSONObject jsonObj = new JSONObject();
		if (!StringUtils.isEmpty(processDefinitionKey)) {
			Map<String, Object> variables = RequestUtils.getParameterMap(request);
			String userId = request.getParameter("userId");
			identityService.setAuthenticatedUserId(userId);
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
			jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		}
		return jsonObj.toJSONString().getBytes("UTF-8");
	}
	@GET
	@Path("/startWorkflowProcessSkipEmptyTask")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] startWorkflowProcessSkipEmptyTask(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		String processDefinitionKey = RequestUtils.getString(request, "processDefinitionKey", "");
		JSONObject jsonObj = new JSONObject();
		if (!StringUtils.isEmpty(processDefinitionKey)) {
			Map<String, Object> variables = RequestUtils.getParameterMap(request);
			String userId = request.getParameter("userId");
			identityService.setAuthenticatedUserId(userId);
			ProcessInstance pi = taskExtService.startProcessInstanceByKeySkipEmptyTask(processDefinitionKey, variables);
			jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		}
		return jsonObj.toJSONString().getBytes("UTF-8");
	}
	@GET
	@Path("/runWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] runWorkFlow(@Context HttpServletRequest request) throws IOException {
		// 获取流程实例ID
		String processInstanceId = request.getParameter("processInstanceId");
		String userId = request.getParameter("userId");
		JSONObject jsonObj = new JSONObject();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		Task userTask = getUserTaskByProcessInstanceId(processInstanceId, userId);
		String taskDefKey=userTask.getTaskDefinitionKey();
		Map<String, Object> variables = RequestUtils.getParameterMap(request);
		if (userTask != null) {
			//写入当前流程节点变量
			variables.put("currentTask", userTask.getTaskDefinitionKey());
			taskService.claim(userTask.getId(), userId);
			variables.put("approve", true);
			taskService.complete(userTask.getId(), variables);
			//获取当前任务
			List<Task> tasks=taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(taskDefKey).list();
			//检测上一个任务与当前任务是否为同一个任务 为不同任务清空流程变量
			//获取当前所有任务定义
			Vector<String> taskDefkeys=new Vector<String>();
			for(Task task:tasks) {
				taskDefkeys.add(task.getTaskDefinitionKey()+"_user");
			}
			//清空当前待办任务外的流程变量
			taskExtService.emptyProcessInstVaribale(processInstanceId,taskDefkeys);
		}else{
			List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			taskService.claim(taskList.get(0).getId(), null);
			variables.put("approve", true);
			taskService.complete(taskList.get(0).getId(), variables);
		}
		jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		return jsonObj.toJSONString().getBytes("UTF-8");
	}
	@GET
	@Path("/runWorkFlowSkipEmptyTask")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] runWorkFlowEmptyTask(@Context HttpServletRequest request) throws IOException {
		// 获取流程实例ID
		String processInstanceId = request.getParameter("processInstanceId");
		String userId = request.getParameter("userId");
		
		JSONObject jsonObj = new JSONObject();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		Map<String, Object> variables = RequestUtils.getParameterMap(request);
		taskExtService.completeTaskSkipEmptyTask(processInstanceId, userId, variables);
		jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		return jsonObj.toJSONString().getBytes("UTF-8");
	}
	@GET
	@Path("/jumpWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] jumpWorkFlow(@Context HttpServletRequest request) throws IOException {
		// 获取流程实例ID
		String processInstanceId = request.getParameter("processInstanceId");
		String userId = request.getParameter("userId");
		String taskId=request.getParameter("taskId");
		JSONObject jsonObj = new JSONObject();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		Map<String, Object> variables = RequestUtils.getParameterMap(request);
		variables.put("审核结果", true);
		try {
			taskExtService.jumpTask(processInstanceId, taskId, userId, "", variables);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		return jsonObj.toJSONString().getBytes("UTF-8");
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
	@GET
	@Path("/rejecttoPreTask")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] rejecttoPreTask(@Context HttpServletRequest request) throws IOException {
		// 获取流程实例ID
		String processInstanceId = request.getParameter("processInstanceId");
		String userId = request.getParameter("userId");
		String rejectMessage= request.getParameter("rejectMessage");
		JSONObject jsonObj = new JSONObject();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		taskExtService.rejectPreTask(processInstanceId,userId, rejectMessage);
		return jsonObj.toJSONString().getBytes("UTF-8");
	}
	@GET
	@Path("/rejecttoTask")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] rejecttoTask(@Context HttpServletRequest request) throws IOException {
		// 获取流程实例ID
		String processInstanceId = request.getParameter("processInstanceId");
		String userId = request.getParameter("userId");
		String taskId = request.getParameter("taskId");
		String rejectMessage= request.getParameter("rejectMessage");
		JSONObject jsonObj = new JSONObject();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		try {
			taskExtService.rejectTask(processInstanceId,taskId,userId, rejectMessage);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj.toJSONString().getBytes("UTF-8");
	}
}
