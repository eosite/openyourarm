package com.glaf.isdp.web.springmvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/isdp/wbs/treepinfo")
@RequestMapping("/isdp/wbs/treepinfo")
public class TreepInfoController {
	
	@RequestMapping(params = "method=show")
	public ModelAndView show(HttpServletRequest request){
		
		return new ModelAndView("/isdp/wbs/treepinfo");
	}
}
