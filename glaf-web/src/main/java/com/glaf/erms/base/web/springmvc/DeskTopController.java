package com.glaf.erms.base.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/erms/deskTop")
@RequestMapping("/erms/deskTop")
public class DeskTopController {
	protected static final Log logger = LogFactory.getLog(DeskTopController.class);

	public DeskTopController() {

	}
	@RequestMapping("/view")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		//System.out.println("******************");
		return new ModelAndView("/erms/deskTop", modelMap);
	}
	@ResponseBody
	@RequestMapping("/test")
	public byte[] test(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {

		byte[] json=null;
		String content=HttpClientTest.getResponseContent("http://mtdx.fzmt.cn:88/glaf/website/isdp/websiteWBS/getTreepInfoJSON?systemName=cmVwb19kYl84");
		return content.getBytes("UTF-8");
	}
}
