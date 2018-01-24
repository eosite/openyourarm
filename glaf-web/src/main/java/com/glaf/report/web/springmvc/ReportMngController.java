package com.glaf.report.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/report/mng")
@RequestMapping("/report/mng")
public class ReportMngController {
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
        String url="/report/report_list";
		return new ModelAndView(url, modelMap);
	}
}
