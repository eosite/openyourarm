package com.glaf.isdp.web.springmvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.isdp.service.DotUseService;
import com.glaf.isdp.service.FileDotService;

@Controller("/isdp/cell/data")
@RequestMapping("/isdp/cell/data")
public class CellDataShowController {
	protected static final Log logger = LogFactory
			.getLog(CellDataShowController.class);

	@Resource
	private FileDotService fileDotService;
	@Resource
	private DotUseService dotUseService;

	@RequestMapping("/show")
	public ModelAndView show(HttpServletRequest request, ModelMap modelMap) {
		return new ModelAndView("/isdp/cell/filedot/show_cell_data");
	}
}
