package com.glaf.pageworkflow.web.springmvc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.explorer.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.glaf.form.core.domain.FormPageEventProcDef;
import com.glaf.form.core.service.FormPageEventProcDefService;
import com.glaf.pageworkflow.core.service.PageWorkFlowRepositoryService;

@Controller("/page/workflow")
@RequestMapping("/page/workflow")
public class PageWorkFlowController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected PageWorkFlowRepositoryService pageWorkFlowRepositoryService;
	@Resource
	public void setPageWorkFlowRepositoryService(
			PageWorkFlowRepositoryService pageWorkFlowRepositoryService) {
		this.pageWorkFlowRepositoryService = pageWorkFlowRepositoryService;
	}
	protected RepositoryService repositoryService;
	@Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	protected FormPageEventProcDefService formPageEventProcDefService;
	@Resource
	public void setFormPageEventProcDefService(
			FormPageEventProcDefService formPageEventProcDefService) {
		this.formPageEventProcDefService = formPageEventProcDefService;
	}
	@RequestMapping("/view")
	public ModelAndView gotoPage(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
        String pageId=RequestUtils.getString(request, "pageId");
		return new ModelAndView("/pageworkflow/linkage", modelMap);
	}
	/**
	 * 流程管理主页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		
		 String pageId=RequestUtils.getString(request, "pageId");
		 return new ModelAndView("/pageworkflow/main", modelMap);
	}
	/**
	 * 流程设计
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/design")
	public ModelAndView design(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
        String pageId=RequestUtils.getString(request, "pageId");
		return new ModelAndView("/pageworkflow/design", modelMap);
	}
	/**
	 * 导入流程模型
	 * @param request
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/importPageWorkFlow")
	@ResponseBody
	public byte[] importPageWorkFlow(@Context HttpServletRequest request,@RequestParam("uploadfile") MultipartFile file)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		ByteArrayOutputStream outputStream = null;
		Model modelData = null;
		String fileName = file.getOriginalFilename();
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
							modelData.setKey(bpmnModel.getMainProcess().getId());
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
							formPageEventProcDef.setProcDeployStatus("0");
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

	@RequestMapping("/componentRuleSetting")
	public ModelAndView definedEx(HttpServletRequest request) {
		return new ModelAndView("/pageworkflow/component_rule_setting");
	}
}
