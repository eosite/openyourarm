package com.glaf.workflow.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.identity.User;
import com.glaf.core.util.RequestUtils;
import com.glaf.workflow.core.service.WorkFlowDefinedService;

@Controller("/form/workflow/nodeDefined")
@RequestMapping("/form/workflow/nodeDefined")
public class FormWorkFlowNodeDefinedController {
	
	protected WorkFlowDefinedService workFlowDefinedService;
	@Resource
	public void setWorkFlowDefinedService(
			WorkFlowDefinedService workFlowDefinedService) {
		this.workFlowDefinedService = workFlowDefinedService;
	}
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
		String taskId = request.getParameter("taskId") != null ? request
				.getParameter("taskId") : "";
		taskId = URLDecoder.decode(taskId, "UTF-8");
		modelMap.put("taskId", taskId);
		String taskName = request.getParameter("taskName") != null ? request
				.getParameter("taskName") : "";
		taskName = URLDecoder.decode(taskName, "UTF-8");
		modelMap.put("taskName", taskName);
		String description = request.getParameter("description") != null ? request
				.getParameter("description") : "";
		description = URLDecoder.decode(description, "UTF-8");
		modelMap.put("description", description);
		return new ModelAndView("/workflow/form_wf_node_defined", modelMap);
	}
	@RequestMapping("/viewProp")
	public ModelAndView viewProp(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
		return new ModelAndView("/workflow/form_wf_node_defined_view", modelMap);
	}
	/**
	 * 保存
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/save")
	@ResponseBody
	public byte[] save(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		Map<String, String[]> paramMap = request.getParameterMap();
		String elemType=request.getParameter("elemType");
		String modelId=request.getParameter("modelId");
		String resourceId=request.getParameter("resourceId");
		User currentUser=RequestUtils.getUser(request);
		jsonObj=workFlowDefinedService.saveElemSet(currentUser.getActorId(),modelId, elemType, resourceId, paramMap);
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}
}
