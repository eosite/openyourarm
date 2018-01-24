package com.glaf.pageworkflow.web.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
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
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.form.core.domain.FormPageEventProcDef;
import com.glaf.form.core.service.FormPageEventProcDefService;
import com.glaf.pageworkflow.core.service.PageWorkFlowRepositoryService;

@Controller
@Path("/rs/page/workflow")
public class PageWorkFlowRestful {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected RuntimeService runtimeService;

	protected TaskService taskService;

	protected RepositoryService repositoryService;

	protected PageWorkFlowRepositoryService pageWorkFlowRepositoryService;
	
	protected FormPageEventProcDefService formPageEventProcDefService;

	@POST
	@Path("/startPageWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] startPageWorkFlow(@Context HttpServletRequest request)
			throws IOException {
		Date beginDt = new Date();
		//System.out.println("开始执行时间" + beginDt.getTime());
		// 获取页面ID
		String pageId = request.getParameter("pageId");
		// 获取流程定义名称
		String processDefinitionId = request
				.getParameter("processDefinitionId");
		// 根据规则定义初始化流程上下文（如通过页面参数给流程变量赋值）
		Map<String, Object> variables = RequestUtils.getParameterMap(request);
		System.out
				.println("variables::::::::::::::::::::::::::::::::::::::::::::::"
						+ variables.get("conValue"));
		// 根据页面ID获取工作流引擎实例
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(
				processDefinitionId, variables);
		logger.debug("pi=================" + pi.getProcessInstanceId());
		// 查询单个实例
		Execution execution = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(pi.getProcessInstanceId()).singleResult();
		// 获取当前任务的ID
		logger.debug("task=================" + execution.getActivityId());
		// 根据任务ID获取规则（控件ID、输入、输出参数）
		/*
		 * //获取下一个Task PvmActivity
		 * nextTask=nextTaskDefinition(pi.getProcessInstanceId
		 * (),pi.getProcessDefinitionId()); // 设置运行时服务变量
		 * runtimeService.setVariable(execution.getId(), "type",
		 * nextTask.getId()); // 向特定的流程实例发送触发器执行的信号
		 * runtimeService.signal(execution.getId());
		 */
		variables = getRuleVariablesByTaskId(execution.getActivityId(),
				variables);
		JSONObject jsonObj = JsonUtils.toJSONObject(variables);
		jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		runtimeService.setVariable(execution.getId(), "type",
				execution.getActivityId());
		// 向特定的流程实例发送触发器执行的信号
		runtimeService.signal(execution.getId());
		// 获取下一个Task
		PvmActivity nextTask = nextTaskDefinition(pi.getProcessInstanceId(),
				pi.getProcessDefinitionId());
		if (nextTask == null) {
			jsonObj.put("endFlag", 1);
			//Date endDt = new Date();
			//System.out.println("执行结束时间" + endDt.getTime());
			//System.out.println("共耗时" + (endDt.getTime() - beginDt.getTime())+ "毫秒");
		}
		jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		return jsonObj.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/runPageWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] runPageWorkFlow(@Context HttpServletRequest request)
			throws IOException {
		// 获取页面ID
		String pageId = request.getParameter("pageId");
		// 获取流程实例ID
		String processInstanceId = request.getParameter("processInstanceId");
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 获取当前流程变量
		Map<String, Object> variables = pi.getProcessVariables();
		// 查询单个实例
		Execution execution = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(pi.getProcessInstanceId()).singleResult();
		runtimeService.setVariable(execution.getId(), "type",
				execution.getActivityId());
		// 向特定的流程实例发送触发器执行的信号
		runtimeService.signal(execution.getId());
		// taskService.complete(execution.getId(), variables);
		variables = getRuleVariablesByTaskId(execution.getActivityId(),
				variables);
		JSONObject jsonObj = JsonUtils.toJSONObject(variables);
		// 获取下一个Task
		PvmActivity nextTask = nextTaskDefinition(pi.getProcessInstanceId(),
				pi.getProcessDefinitionId());
		if (nextTask == null) {
			jsonObj.put("endFlag", 1);
		}
		jsonObj.put("processInstanceId", pi.getProcessInstanceId());
		return jsonObj.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取数据集（项目中需将此方法独立到dataset rest工具类）
	 * 
	 * @param datasetId
	 * @param params
	 * @return
	 * @throws IOException
	 */
	@POST
	@Path("/getDatasetById")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getDatasetById(String datasetId, Map<String, Object> params)
			throws IOException {
		JSONObject jsonObj = new JSONObject();
		return jsonObj.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 考虑流程定义时使用扩展表保存任务对应的规则
	 * 
	 * @param taskId
	 * @param variables
	 *            工作流变量
	 * @return
	 */
	public Map<String, Object> getRuleVariablesByTaskId(String taskId,
			Map<String, Object> variables) {
		// 通过流程TaskId获取对应的规则集，通过规则引擎执行规则并将规则执行的结果更新到工作数据区
		// 实现流程与规则间的数据传递
		if (taskId.equals("ReceiveTask1")) {
			// 执行规则 可以会改变流程variables值
			variables.put("controlId", "province");
		} else if (taskId.equals("ReceiveTask2")) {
			variables.put("controlId", "city");
		} else if (taskId.equals("openTask1")) {
			variables.put("url", "http://www.baidu.com");
			variables.put("type", "open");
			variables.put("param", "");
		} else if (taskId.equals("openTask2")) {
			variables.put("url", "http://www.sina.com.cn");
			variables.put("type", "open");
			variables.put("param", "");
		} else if (taskId.equals("redirectTask3")) {
			variables.put("url", "http://www.qq.com");
			variables.put("type", "redirect");
			variables.put("param", "");
		}
		// 将规则执行结果放入流程上下文中
		return variables;
	}

	/**
	 * 根据实例编号查找下一个任务节点
	 * 
	 * @param String
	 *            procInstId ：实例编号
	 * @return
	 */
	public PvmActivity nextTaskDefinition(String procInstId,
			String processDefinitionId) {
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
		// 当前实例的执行到哪个节点
		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createProcessInstanceQuery().processInstanceId(procInstId)
				.singleResult();
		if (execution == null) {
			return null;
		}
		String activitiId = execution.getActivityId();

		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				//System.out.println("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : outTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					//System.out.println("下一步任务任务：" + ac.getId()+ ac.getProperty("name"));

					return ac;
				}
				break;
			}
		}

		return null;
	}

	/**
	 * 获取页面定义流程
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@POST
	@Path("/getPageWorkFlows")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getPageWorkFlows(@Context HttpServletRequest request)
			throws IOException {
		//获取页面ID
		String pageId = request.getParameter("pageId");
		String contextPath=request.getContextPath();
		//获取页面包含控件
		List<JSONObject> pageCompJsons=pageWorkFlowRepositoryService.getPageCompentsByPageId(pageId,contextPath);
		//List<JSONObject> modelJsons = pageWorkFlowRepositoryService
				//.getProcessModelByPageId(pageId,contextPath);
		//pageCompJsons.addAll(modelJsons);
		return JSON.toJSONString(pageCompJsons).getBytes("UTF-8");
	}

	/**
	 * 新建模型
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/createPageWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] createPageWorkFlow(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		try {
			String pageId = RequestUtils.getString(request, "pageId", "");
			String name = RequestUtils.getString(request, "modelName", "");
			String key = RequestUtils.getString(request, "modelKey", "");
			String description = RequestUtils.getString(request, "modelDesc",
					"");
			String compId= RequestUtils.getString(request, "compId", "");
			String event= RequestUtils.getString(request, "event", "");
			if (StringUtils.isEmpty(pageId) ||StringUtils.isEmpty(compId)||StringUtils.isEmpty(event)|| StringUtils.isEmpty(name)
					|| StringUtils.isEmpty(key)) {
				jsonObj.put("result", 0);
			} else {
				ObjectMapper objectMapper = new ObjectMapper();
				ObjectNode editorNode = objectMapper.createObjectNode();
				editorNode.put("id", "canvas");
				editorNode.put("resourceId", "canvas");
				ObjectNode stencilSetNode = objectMapper.createObjectNode();
				stencilSetNode.put("namespace",
						"http://b3mn.org/stencilset/bpmn2.0#");
				editorNode.set("stencilset", stencilSetNode);
				Model modelData = repositoryService.newModel();
				ObjectNode modelObjectNode = objectMapper.createObjectNode();
				modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
				modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
				description = StringUtils.defaultString(description);
				modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
						description);
				modelData.setMetaInfo(modelObjectNode.toString());
				modelData.setName(name);
				modelData.setKey(StringUtils.defaultString(key));
				repositoryService.saveModel(modelData);
				repositoryService.addModelEditorSource(modelData.getId(),
						editorNode.toString().getBytes("utf-8"));
				//增加流程与构件事件关联
				FormPageEventProcDef formPageEventProcDef=new FormPageEventProcDef();
				formPageEventProcDef.setPageId(pageId);
				formPageEventProcDef.setCompId(compId);
				formPageEventProcDef.setEvent_(event);
				formPageEventProcDef.setProcModelId(modelData.getId());
				formPageEventProcDefService.save(formPageEventProcDef);
				jsonObj.put("modelId", modelData.getId());
				jsonObj.put("result", 1);
                
				// response.sendRedirect(request.getContextPath() +
				// "/service/editor?id=" + modelData.getId());
			}
		} catch (Exception e) {
			logger.error("创建模型失败：", e);
			jsonObj.put("result", -1);
		}
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	/**
	 * 新建模型
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/convertPageWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] convertPageWorkFlow(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		try {
			String processDefId = RequestUtils.getString(request,
					"processDefId", "");
			if (!StringUtils.isEmpty(processDefId)) {
				// 根据ID获取ProcessDefinition
				ProcessDefinition processDefinition = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionId(processDefId).singleResult();
				String pageId = RequestUtils.getString(request, "pageId", "");
				String name = processDefinition.getName();
				String key = processDefinition.getKey();
				if (StringUtils.isEmpty(pageId) || StringUtils.isEmpty(name)
						|| StringUtils.isEmpty(key)) {
					jsonObj.put("result", 0);
				} else {
					try {
						InputStream bpmnStream = repositoryService
								.getResourceAsStream(
										processDefinition.getDeploymentId(),
										processDefinition.getResourceName());
						XMLInputFactory xif = XmlUtil
								.createSafeXmlInputFactory();
						InputStreamReader in = new InputStreamReader(
								bpmnStream, "UTF-8");
						XMLStreamReader xtr = xif.createXMLStreamReader(in);
						BpmnModel bpmnModel = new BpmnXMLConverter()
								.convertToBpmnModel(xtr);

						if (bpmnModel.getMainProcess() == null
								|| bpmnModel.getMainProcess().getId() == null) {

							logger.error("转换模型失败：请确认文件中包含有效的BPMN定义");
							jsonObj.put("result", 0);
						} else {

							if (bpmnModel.getLocationMap().isEmpty()) {

								logger.error("转换模型失败：请确认文件中包含BPMN DI信息");
								jsonObj.put("result", 0);
							} else {
								BpmnJsonConverter converter = new BpmnJsonConverter();
								ObjectNode modelNode = converter
										.convertToJson(bpmnModel);
								Model modelData = repositoryService.newModel();
								ObjectNode modelObjectNode = new ObjectMapper()
										.createObjectNode();
								modelObjectNode.put(
										ModelDataJsonConstants.MODEL_NAME,
										processDefinition.getName());
								modelObjectNode.put(
										ModelDataJsonConstants.MODEL_REVISION,
										1);
								modelObjectNode
										.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
												processDefinition
														.getDescription());
								modelData.setMetaInfo(modelObjectNode
										.toString());
								modelData.setName(processDefinition.getName());
								modelData.setCategory("page");
								repositoryService.saveModel(modelData);
								repositoryService.addModelEditorSource(
										modelData.getId(), modelNode.toString()
												.getBytes("utf-8"));
								jsonObj.put("result", 1);
								jsonObj.put("modelId", modelData.getId());
							}
						}

					} catch (Exception e) {
						logger.error("转换模型失败：", e);
						jsonObj.put("result", -1);
					}
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
	@Path("/deletePageWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deletePageWorkFlow(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		String modelId = RequestUtils.getString(request, "modelId", "");
		String eventProcId= RequestUtils.getString(request, "eventProcId", "");
		if (!StringUtils.isEmpty(modelId)) {
			repositoryService.deleteModel(modelId);
			if(eventProcId.trim().length()>0)
			formPageEventProcDefService.deleteById(eventProcId);
		}
		jsonObj.put("result", 1);
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}
	@POST
	@Path("/updateDeployStatus")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] updateDeployStatus(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		String eventProcId= RequestUtils.getString(request, "eventProcId", "");
		if(eventProcId.trim().length()>0)
		{
	       formPageEventProcDefService.updateDeployStatus(eventProcId);
	       jsonObj.put("result", 1);
		}
		else
		{
			jsonObj.put("result", 0);
		}
		
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}
	@POST
	@Path("/deployPageWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deployPageWorkFlow(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		String modelId = RequestUtils.getString(request, "modelId", "");
		String EVENTPRO_ID= RequestUtils.getString(request, "eventProcId", "");
		if (!StringUtils.isEmpty(modelId)) {
			try {
				Model modelData = repositoryService.getModel(modelId);
				ObjectNode modelNode = (ObjectNode) new ObjectMapper()
						.readTree(repositoryService
								.getModelEditorSource(modelData.getId()));
				BpmnModel model = new BpmnJsonConverter()
						.convertToBpmnModel(modelNode);
				byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
				String processName = modelData.getKey() + ".bpmn20.xml";
				Deployment deployment = this.repositoryService
						.createDeployment().name(modelData.getKey())
						.addString(processName, new String(bpmnBytes,"UTF-8")).deploy();
				//更新FORM_PAGE_EVENT_PROCDEF表PROCDEF_KEY_、PROCDEF_ID_、PROCDEPLOY_ID_
				ProcessDefinition processDefinition=pageWorkFlowRepositoryService.getProcessDefinitionByDeploymentId(deployment.getId());
				String PROCDEF_KEY_=processDefinition.getKey();
				String PROCDEF_ID_=processDefinition.getId();
				String PROCDEPLOY_ID_=deployment.getId();
				formPageEventProcDefService.updateFormPageEventProcDefById(PROCDEF_KEY_,PROCDEF_ID_,PROCDEPLOY_ID_,EVENTPRO_ID);
				jsonObj.put("message", "部署成功，部署ID=" + deployment.getId());
				jsonObj.put("result", 1);
			} catch (Exception e) {
				logger.error("根据模型部署流程失败：modelId={}", modelId, e);
				jsonObj.put("result", -1);
			}
		}

		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/exportPageWorkFlow")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void exportPageWorkFlow(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String modelId = RequestUtils.getString(request, "modelId", "");
		try {
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService
					.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId()
					+ ".bpmn20.xml";
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);
			response.setContentType("application/OCTET-STREAM;charset=UTF-8");
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("导出model的xml文件失败：modelId={}", modelId, e);
		}
	}

	@POST
	@Path("/importPageWorkFlow")
	@ResponseBody
	@Produces({ MediaType.TEXT_HTML})
	@javax.ws.rs.Consumes({ MediaType.MULTIPART_FORM_DATA})
	public byte[] importPageWorkFlow(@Context HttpServletRequest request,@RequestParam("uploadfile") MultipartFile file)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		ByteArrayOutputStream outputStream = null;
		Model modelData = null;
		String fileName = file.getName();
		String pageId = RequestUtils.getString(request, "pageId", "");
		String compId= RequestUtils.getString(request, "compId", "");
		String event= RequestUtils.getString(request, "event", "");
		String eventProcId= RequestUtils.getString(request, "eventProcId", "");
		try {
			try {
				if (fileName.endsWith(".bpmn20.xml")
						|| fileName.endsWith(".bpmn")) {
					XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
					XMLStreamReader xtr = xif.createXMLStreamReader(file.getInputStream());
					BpmnModel bpmnModel = new BpmnXMLConverter()
							.convertToBpmnModel(xtr);

					if (bpmnModel.getMainProcess() == null
							|| bpmnModel.getMainProcess().getId() == null) {
						logger.error("转换模型失败：请确认文件中包含有效的BPMN定义");
						jsonObj.put("result", 0);
					} else {

						if (bpmnModel.getLocationMap().isEmpty()) {
							logger.error("转换模型失败：请确认文件中包含BPMN DI信息");
							jsonObj.put("result", 0);
						} else {

							String processName = null;
							if (StringUtils.isNotEmpty(bpmnModel
									.getMainProcess().getName())) {
								processName = bpmnModel.getMainProcess()
										.getName();
							} else {
								processName = bpmnModel.getMainProcess()
										.getId();
							}

							modelData = repositoryService.newModel();
							ObjectNode modelObjectNode = new ObjectMapper()
									.createObjectNode();
							modelObjectNode.put(
									ModelDataJsonConstants.MODEL_NAME,
									processName);
							modelObjectNode.put(
									ModelDataJsonConstants.MODEL_REVISION, 1);
							modelData.setMetaInfo(modelObjectNode.toString());
							modelData.setName(processName);
							modelData.setCategory("page");
							repositoryService.saveModel(modelData);

							BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
							ObjectNode editorNode = jsonConverter
									.convertToJson(bpmnModel);

							repositoryService.addModelEditorSource(modelData
									.getId(),
									editorNode.toString().getBytes("utf-8"));
							//增加流程与构件事件关联
							FormPageEventProcDef formPageEventProcDef=new FormPageEventProcDef();
							formPageEventProcDef.setPageId(pageId);
							formPageEventProcDef.setCompId(compId);
							formPageEventProcDef.setEvent_(event);
							formPageEventProcDef.setProcModelId(modelData.getId());
							if(eventProcId.trim().length()>0)
							{
								formPageEventProcDef.setId(eventProcId);
							}
							 formPageEventProcDefService.save(formPageEventProcDef);
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

	@javax.annotation.Resource
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@javax.annotation.Resource
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@javax.annotation.Resource
	public void setPageWorkFlowRepositoryService(
			PageWorkFlowRepositoryService pageWorkFlowRepositoryService) {
		this.pageWorkFlowRepositoryService = pageWorkFlowRepositoryService;
	}
	@javax.annotation.Resource
	public void setFormPageEventProcDefService(
			FormPageEventProcDefService formPageEventProcDefService) {
		this.formPageEventProcDefService = formPageEventProcDefService;
	}

}
