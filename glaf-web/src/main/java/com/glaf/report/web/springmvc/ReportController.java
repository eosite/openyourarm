package com.glaf.report.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.report.core.domain.ReportTmpMapping;
import com.glaf.report.core.service.ReportTmpMappingService;

@Controller("/stml/report")
@RequestMapping("/stml/report/")
public class ReportController {
	protected ReportTmpMappingService reportTmpMappingService;

	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpMappingService")
	public void setReportTmpMappingService(ReportTmpMappingService reportTmpMappingService) {
		this.reportTmpMappingService = reportTmpMappingService;
	}
	@RequestMapping("/designer")
	public ModelAndView designer(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
       
		return new ModelAndView("/report/report_designer", modelMap);
	}

	@RequestMapping("/view/js")
	public ModelAndView jsView(HttpServletRequest request, ModelMap modelMap) {
		//根据MappingId获取templateId
		String mappingId=request.getParameter("mappingId");
		if(StringUtils.isNotEmpty(mappingId)){
			ReportTmpMapping reportTmpMapping=reportTmpMappingService.getReportTmpMapping(mappingId);
			if(reportTmpMapping!=null)
			{
				modelMap.put("templateId", reportTmpMapping.getTemplateId());
			}
		}
		return new ModelAndView("/report/report_view_js", modelMap);
	}
}
