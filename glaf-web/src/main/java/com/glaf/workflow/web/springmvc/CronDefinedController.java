package com.glaf.workflow.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/form/workflow/cron")
@RequestMapping("/form/workflow/cron")
public class CronDefinedController {
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
        String url="/quartz/cron_defined";
		return new ModelAndView(url, modelMap);
	}
}
