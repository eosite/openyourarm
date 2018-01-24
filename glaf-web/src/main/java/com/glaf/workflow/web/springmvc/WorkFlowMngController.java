package com.glaf.workflow.web.springmvc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.explorer.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.RequestUtils;
import com.glaf.workflow.core.service.WorkFlowDefinedService;

@Controller("/workflow/management")
@RequestMapping("/workflow/management")
public class WorkFlowMngController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected RepositoryService repositoryService;
	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	protected WorkFlowDefinedService workFlowDefinedService;
	@javax.annotation.Resource
	public void setWorkFlowDefinedService(
			WorkFlowDefinedService workFlowDefinedService) {
		this.workFlowDefinedService = workFlowDefinedService;
	}
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
        String url="/workflow/model_list";
		return new ModelAndView(url, modelMap);
	}
	/**
	 * 流程定义列表页面
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/view/process-definitions")
	public ModelAndView processDefinitions(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
        String url="/workflow/process_def_list";
		return new ModelAndView(url, modelMap);
	}
	/**
	 * 流程定义选择页面
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/view/process-definition-choose")
	public ModelAndView processDefinitionChoose(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
        String url="/workflow/process_def_choose";
		return new ModelAndView(url, modelMap);
	}
	/**
	 * 选择流程定义变量
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/view/variable-choose")
	public ModelAndView processVariableChoose(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
        String url="/workflow/variable_choose";
		return new ModelAndView(url, modelMap);
	} 
	@RequestMapping("/create")
	public ModelAndView create(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
        String url="/workflow/model_add";
		return new ModelAndView(url, modelMap);
	}
	@RequestMapping("/viewDiagram")
	public ModelAndView viewDiagram(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String procModelId = RequestUtils.getString(request, "procModelId", "");
		String procDeployId = RequestUtils.getString(request, "procDeployId",
				"");
		Model modelData = null;
		if (procDeployId.trim().length() > 0) {
			ProcessDefinition processDefinition = workFlowDefinedService
					.getProcessDefinitionByDeploymentId(procDeployId);
			modelMap.put("procName", processDefinition.getName());
			modelMap.put("procVersion", processDefinition.getVersion());
			String rootPath = request.getServletContext().getRealPath("/");
			String filePath = workFlowDefinedService
					.getProcessDiagramByDeployId(rootPath, procDeployId);
			modelMap.put("filePath", filePath);
		} else if (procModelId.trim().length() > 0) {
			modelData = repositoryService.getModel(procModelId);
			modelMap.put("procName", modelData.getName());
			modelMap.put("procVersion", modelData.getVersion());
			String rootPath = request.getServletContext().getRealPath("/");
			String filePath = workFlowDefinedService
					.getProcessDiagramByModelId(rootPath, modelData);
			modelMap.put("filePath", filePath);
		} else {
			modelMap.put("procName", "流程未定义");
		}
		modelMap.put("procModelId", procModelId);
		return new ModelAndView("/workflow/workflow_view", modelMap);
	}
	@RequestMapping("/viewProcessDef")
	public ModelAndView viewProcessDef(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
		return new ModelAndView("/workflow/workflow_def_view", modelMap);
	}
	/**
	 * 导入流程模型
	 * @param request
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/importWorkFlow")
	@ResponseBody
	public byte[] importWorkFlow(@Context HttpServletRequest request,@RequestParam("uploadfile") MultipartFile file)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		ByteArrayOutputStream outputStream = null;
		Model modelData = null;
		String fileName = file.getOriginalFilename();
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
							modelData.setKey(bpmnModel.getMainProcess().getId());
							modelData.setCategory("workflow");
							repositoryService.saveModel(modelData);

							BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
							ObjectNode editorNode = jsonConverter
									.convertToJson(bpmnModel);

							repositoryService.addModelEditorSource(modelData
									.getId(),
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
	
}
