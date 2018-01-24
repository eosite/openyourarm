package com.glaf.model.web.springmvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.util.RequestUtils;
import com.glaf.model.domain.SystemDef;
import com.glaf.model.service.SystemDefService;

@Controller("/system/model")
@RequestMapping("/system/model")
public class SystemModelController {
	protected static final Log logger = LogFactory.getLog(SystemModelController.class);

	protected SystemDefService systemDefService;

	@javax.annotation.Resource(name = "com.glaf.model.service.systemDefService")
	public void setSystemDefService(SystemDefService systemDefService) {
		this.systemDefService = systemDefService;
	}

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/model/system_list", modelMap);
	}
	/**
	 * 系统功能结构图
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/systemstructure")
	public ModelAndView systemstructure(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/model/system_structure", modelMap);
	}
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDef systemDef = systemDefService.getSystemDef(RequestUtils.getString(request, "sysId"));
		if (systemDef != null) {
			request.setAttribute("systemDef", systemDef);
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("systemDef.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/model/system_add", modelMap);
	}
	@RequestMapping("/modeling")
	public ModelAndView modeling(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/model/system_modeling", modelMap);
	}
}
