package com.glaf.isdp.web.springmvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.Environment;
import com.glaf.core.util.RequestUtils;
import com.glaf.isdp.domain.TreepName;
import com.glaf.isdp.service.TreepNameService;

@Controller("/isdp/wbs")
@RequestMapping("/isdp/wbs")
public class WbsController {

	@Resource(name = "com.glaf.isdp.service.treepNameService")
	private TreepNameService treepNameService;

	@RequestMapping(params = "method=projcheck")
	public ModelAndView projcheck(HttpServletRequest request) {
		int domainIndexId = 2;
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", systemName);
		Environment.setCurrentSystemName(databaseCode);

		TreepName treepName = treepNameService.getTreepNameByDomainIndexId(domainIndexId);
		request.setAttribute("treepName", treepName);

		Environment.setCurrentSystemName(systemName);
		return new ModelAndView("/isdp/wbs/wbs_proj_check");
	}

	@RequestMapping(params = "method=projexec")
	public ModelAndView projexec(HttpServletRequest request) {
		int domainIndexId = 2;
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", systemName);
		Environment.setCurrentSystemName(databaseCode);

		TreepName treepName = treepNameService.getTreepNameByDomainIndexId(domainIndexId);
		request.setAttribute("treepName", treepName);

		Environment.setCurrentSystemName(systemName);
		return new ModelAndView("/isdp/wbs/wbs_proj_exec");
	}
	
	@RequestMapping(params = "method=cellSearch")
	public ModelAndView cellSearch(HttpServletRequest request) {
		String currentSystemName = Environment.getCurrentSystemName();
		String systemName = RequestUtils.getString(request, "systemName",
				currentSystemName);
		request.setAttribute("systemName", systemName);

		return new ModelAndView("/isdp/wbs/cell_search");
	}

	public void setTreepNameService(TreepNameService treepNameService) {
		this.treepNameService = treepNameService;
	}
}
