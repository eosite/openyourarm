package com.glaf.workflow.rest;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.activiti.engine.RepositoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.workflow.core.domain.ActReElementDef;
import com.glaf.workflow.core.service.ActReElementDefService;
import com.glaf.workflow.core.service.WorkFLowJsonConvertService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author dane Rademakers
 */
@RestController
public class WorkflowElemsetRestResource {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected ActReElementDefService actReElementDefService;
	protected RepositoryService repositoryService;

	@Resource
	public void setActReElementDefService(
			ActReElementDefService actReElementDefService) {
		this.actReElementDefService = actReElementDefService;
	}

	protected WorkFLowJsonConvertService workFLowJsonConvertService;

	@Resource
	public void setWorkFLowJsonConvertService(
			WorkFLowJsonConvertService workFLowJsonConvertService) {
		this.workFLowJsonConvertService = workFLowJsonConvertService;
	}

	@Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	/**
	 * 获取流程定义属性结构以及属性值
	 * 
	 * @param request
	 * @param elemType
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/workflow/{elemType}/elemset")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getElemset(@Context HttpServletRequest request,
			@PathVariable String elemType) throws UnsupportedEncodingException {
		String resourceName = "";
		String elemset = "";
		String resourceId = request.getParameter("resourceId");
		String modelId = request.getParameter("modelId");
		ActReElementDef actReElementDef = null;
		if (!StringUtils.isEmpty(resourceId) && !StringUtils.isEmpty(modelId)) {
			// 从数据库获取配置
			actReElementDef = actReElementDefService
					.getActReElementDefByModelIdResourceId(modelId, resourceId,
							null);
			if (actReElementDef != null) {
				return actReElementDef.getBytes().getBytes("UTF-8");
			}
		}
		// 如果没有配置信息则获取默认配置
		if (elemType != null) {
			elemType = getJsonElemType(elemType);
			resourceName = "workflow_" + elemType + "_set" + ".json";
		}
		// InputStream elemsetStream = this.getClass().getClassLoader()
		// .getResourceAsStream(resourceName);
		try {
			// if(elemsetStream!=null)
			// elemset = IOUtils.toString(elemsetStream,"UTF-8");
			Map<String, Object> root = new HashMap<>();
			root.put("assigns", null);
			Configuration configuration = new Configuration(
					Configuration.VERSION_2_3_23);
			configuration.setDefaultEncoding("utf-8");
			configuration.setClassForTemplateLoading(this.getClass(), "/");
			Template temp = configuration.getTemplate(resourceName);
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			temp.process(root, stringWriter);
			elemset = stringWriter.toString();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error("Error while loading workflow elemset"
					+ e.getMessage());
			e.printStackTrace();
		}
		return elemset.getBytes("UTF-8");
	}
	/**
	 * 获取流程定义属性结构以及属性值
	 * 
	 * @param request
	 * @param elemType
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/workflow/{elemType}/elemsetview")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getElemsetView(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		String resourceName = "";
		String elemset = "";
		String resourceId = request.getParameter("resourceId");
		String processDefinitionId = request.getParameter("processDefinitionId");
		ActReElementDef actReElementDef = null;
		if (!StringUtils.isEmpty(resourceId) && !StringUtils.isEmpty(processDefinitionId)) {
			// 从数据库获取配置
			actReElementDef = actReElementDefService
					.getActReElementDefByProcessDefinitionIdResourceId(processDefinitionId, resourceId);
			if (actReElementDef != null) {
				return actReElementDef.getBytes().getBytes("UTF-8");
			}
		}
		return null;
	}
	/**
	 * 获取流程变量定义
	 * 
	 * @param request
	 * @param elemType
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/workflow/variable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getVariableJSON(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		String resourceId = request.getParameter("resourceId");
		String modelId = request.getParameter("modelId");
		List<JSONObject> variableJSON = null;
		if (!StringUtils.isEmpty(resourceId) && !StringUtils.isEmpty(modelId)) {
			// 从数据库获取配置
			variableJSON = actReElementDefService.getVariableJSON(modelId,
					resourceId, null);

			if (variableJSON != null) {
				return JSON.toJSONString(variableJSON).getBytes("UTF-8");
			}
		}
		return null;
	}
	/**
	 * 获取流程变量定义
	 * 
	 * @param request
	 * @param elemType
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/workflow/variable/key")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getVariableJSONByProcessKey(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		String processKey = request.getParameter("processKey");
		List<JSONObject> variableJSON = null;
		if (!StringUtils.isEmpty(processKey) && !StringUtils.isEmpty(processKey)) {
			variableJSON = actReElementDefService.getVariableJSONByProcessKey(processKey);
				return JSON.toJSONString(variableJSON).getBytes("UTF-8");
			}
		return null;
	}

	public String getJsonElemType(String elemType) {
		if (elemType.equals("UserTask")) {
			elemType = "userTask";
		} else if (elemType.equals("SequenceFlow")) {
			elemType = "flow";
		}else if (elemType.equals("startEvent")) {
			elemType = "StartNoneEvent";
		}else if (elemType.equals("endEvent")) {
			elemType = "EndNoneEvent";
		}
		return elemType;
	}
    /**
     * 通过扩展实现的属性配置页面配置的属性值更新到ACTIVITI默认的流程定义结构中
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
	@RequestMapping(value = "/workflow/updateActivitiSource")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] updateActivitiSource(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		String modelId = request.getParameter("modelId");
		JSONObject retJonObj = new JSONObject();
		try {
			JSONObject sourceObj = workFLowJsonConvertService
					.ConvertWorkFlowSourceJson(modelId);
			repositoryService.addModelEditorSource(modelId, JSON
					.toJSONString(sourceObj).getBytes("utf-8"));

			retJonObj.put("result", "1");
		} catch (Exception e) {
			logger.error(e.getMessage());
			retJonObj.put("result", "0");
		}
		return null;
	}
}
