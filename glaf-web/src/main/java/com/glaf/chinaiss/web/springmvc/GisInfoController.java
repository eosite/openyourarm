package com.glaf.chinaiss.web.springmvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.utils.ParamUtil;
import com.glaf.chinaiss.model.GisInfo;
import com.glaf.chinaiss.service.GisInfoService;
import com.glaf.core.config.Environment;
import com.glaf.core.util.RequestUtils;

/**
 * GIS弹出窗
 *
 */
@Controller("/isdp/gisInfo")
@RequestMapping("/isdp/gisInfo")
public class GisInfoController {

	private GisInfoService gisInfoService;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/isdp/gis/gis_index");
	}

	/**
	 * 基础报表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/projDocNum")
	public ModelAndView projDocNum(HttpServletRequest request) {
		String systemName = RequestUtils.getString(request, "systemName");
		Environment.setCurrentSystemName(systemName);
		String id = ParamUtil.getParameter(request, "id");
		List<GisInfo> list = gisInfoService.getgisInfoListById(id);
		GisInfo gi = new GisInfo();
		int num = 0;
		if (list != null && list.size() != 0) {
			gi = list.get(0);
			num = gisInfoService.getProjDocNum(gi.getId());
			gi.setNum(String.valueOf(num));
		}

		request.setAttribute("GisInfo", gi);

		return new ModelAndView("/isdp/gis/projDocNum");
	}

	@Resource
	public void setGisInfoService(GisInfoService gisInfoService) {
		this.gisInfoService = gisInfoService;
	}

}
