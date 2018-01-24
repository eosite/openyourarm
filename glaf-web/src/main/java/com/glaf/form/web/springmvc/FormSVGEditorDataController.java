package com.glaf.form.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.util.RequestUtils;
import com.glaf.dep.base.service.DepBaseWdataSetService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;

@Controller("/form/svgEditor")
@RequestMapping("/form/svgEditor")
public class FormSVGEditorDataController {

	protected static final Log logger = LogFactory.getLog(FormSVGEditorDataController.class);

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRulePropertyService formRulePropertyService;
	
	protected DepBaseWdataSetService depBaseWdataSetService;

	@Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

	@Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}
	
	@javax.annotation.Resource
	public void setDepBaseWdataSetService(
			DepBaseWdataSetService depBaseWdataSetService) {
		this.depBaseWdataSetService = depBaseWdataSetService;
	}

	/**
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		String rid = RequestUtils.getString(request, "rid", "");
		String frameId = RequestUtils.getString(request, "frameId", "");
		String dataRole = RequestUtils.getString(request, "dataRole", "");
		modelMap.addAttribute("comId", frameId.replace("_iframe", ""));
		modelMap.addAttribute("rid", rid);
		modelMap.addAttribute("frameId", frameId);
		modelMap.addAttribute("dataRole", dataRole);
		return new ModelAndView("/svgEditor/editor", modelMap);
	}

}
