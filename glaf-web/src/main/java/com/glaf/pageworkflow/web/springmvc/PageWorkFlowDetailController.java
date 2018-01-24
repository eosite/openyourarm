package com.glaf.pageworkflow.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.util.RequestUtils;
import com.glaf.pageworkflow.core.service.PageWorkFlowRepositoryService;

@Controller("/page/workflowDetail")
@RequestMapping("/page/workflowDetail")
public class PageWorkFlowDetailController {
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

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String pageId = RequestUtils.getString(request, "pageId");
		String procModelId = RequestUtils.getString(request, "procModelId", "");
		String procDeployId = RequestUtils.getString(request, "procDeployId",
				"");
		String compName = RequestUtils.getString(request, "compName", "");
		String event = RequestUtils.getString(request, "event", "");
		Model modelData = null;

		if (procDeployId.trim().length() > 0) {
			ProcessDefinition processDefinition = pageWorkFlowRepositoryService
					.getProcessDefinitionByDeploymentId(procDeployId);
			modelMap.put("procName", processDefinition.getName());
			modelMap.put("procVersion", processDefinition.getVersion());
			String rootPath = request.getServletContext().getRealPath("/");
			String filePath = pageWorkFlowRepositoryService
					.getProcessDiagramByDeployId(rootPath, procDeployId);
			modelMap.put("filePath", filePath);
		} else if (procModelId.trim().length() > 0) {
			modelData = repositoryService.getModel(procModelId);
			modelMap.put("procName", modelData.getName());
			modelMap.put("procVersion", modelData.getVersion());
			String rootPath = request.getServletContext().getRealPath("/");
			String filePath = pageWorkFlowRepositoryService
					.getProcessDiagramByModelId(rootPath, modelData);
			modelMap.put("filePath", filePath);
		} else {
			modelMap.put("procName", "流程未定义");
		}
		modelMap.put("procModelId", procModelId);
		modelMap.put("compName", URLDecoder.decode(compName, "UTF-8"));
		modelMap.put("event", URLDecoder.decode(event, "UTF-8"));
		return new ModelAndView("/pageworkflow/workflow_view", modelMap);
	}
}
