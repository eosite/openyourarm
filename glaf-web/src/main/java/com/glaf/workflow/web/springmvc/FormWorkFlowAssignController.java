package com.glaf.workflow.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/form/workflow/assign")
@RequestMapping("/form/workflow/assign")
public class FormWorkFlowAssignController {
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
		String assignType=request.getParameter("assignType");
		String selectedVal=request.getParameter("selectedVal");
		if(selectedVal!=null){
		selectedVal= URLDecoder.decode(selectedVal, "UTF-8");}
		String selectedNameVal=request.getParameter("selectedNameVal");
		if(selectedNameVal!=null){
		selectedNameVal= URLDecoder.decode(selectedNameVal, "UTF-8");
		}
		modelMap.put("selectedVal", selectedVal);
		modelMap.put("selectedNameVal", selectedNameVal);
		String url=null;
		if(assignType.equals("user")){
			url="/workflow/assign_choose";
		}
		else if(assignType.equals("role")){
			url="/workflow/role_choose";
		}
        else if(assignType.equals("departRole")){
        	url="/workflow/dept_role_choose";
		}else{
        	url="/workflow/post_choose";
		}
		return new ModelAndView(url, modelMap);
	}
}
