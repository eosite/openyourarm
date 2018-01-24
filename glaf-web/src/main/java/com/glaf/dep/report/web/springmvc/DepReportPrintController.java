package com.glaf.dep.report.web.springmvc;

import java.io.IOException;
import java.util.*;
 
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;
 
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
 
import com.glaf.dep.report.domain.*;
import com.glaf.dep.report.query.*;
import com.glaf.dep.report.service.*;
import com.glaf.dep.report.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/dep/report/depReportPrint")
@RequestMapping("/dep/report/depReportPrint")
public class DepReportPrintController {
	protected static final Log logger = LogFactory.getLog(DepReportPrintController.class);

	protected DepReportPrintService depReportPrintService;

	public DepReportPrintController() {

	}

        @javax.annotation.Resource(name = "com.glaf.dep.report.service.depReportPrintService")
	public void setDepReportPrintService(DepReportPrintService depReportPrintService) {
		this.depReportPrintService = depReportPrintService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		DepReportPrint depReportPrint = new DepReportPrint();
		Tools.populate(depReportPrint, params);

                depReportPrint.setCellId(RequestUtils.getLong(request, "cellId"));
                depReportPrint.setPrintFlag(request.getParameter("printFlag"));
                depReportPrint.setPageCells(RequestUtils.getInt(request, "pageCells"));
                depReportPrint.setLoopFlag(request.getParameter("loopFlag"));
                depReportPrint.setEnCondition(request.getParameter("enCondition"));
                depReportPrint.setCreator(request.getParameter("creator"));
                depReportPrint.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                depReportPrint.setModifier(request.getParameter("modifier"));
                depReportPrint.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		
		//depReportPrint.setCreator(actorId);
         
		depReportPrintService.save(depReportPrint);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveDepReportPrint")
	public byte[] saveDepReportPrint(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportPrint depReportPrint = new DepReportPrint();
		try {
		    Tools.populate(depReportPrint, params);
                    depReportPrint.setCellId(RequestUtils.getLong(request, "cellId"));
                    depReportPrint.setPrintFlag(request.getParameter("printFlag"));
                    depReportPrint.setPageCells(RequestUtils.getInt(request, "pageCells"));
                    depReportPrint.setLoopFlag(request.getParameter("loopFlag"));
                    depReportPrint.setEnCondition(request.getParameter("enCondition"));
                    depReportPrint.setCreator(request.getParameter("creator"));
                    depReportPrint.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                    depReportPrint.setModifier(request.getParameter("modifier"));
                    depReportPrint.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		    //depReportPrint.setCreator(actorId);
		    this.depReportPrintService.save(depReportPrint);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DepReportPrint saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		DepReportPrint depReportPrint = new DepReportPrint();
		try {
		    Tools.populate(depReportPrint, model);
                    depReportPrint.setCellId(ParamUtils.getLong(model, "cellId"));
                    depReportPrint.setPrintFlag(ParamUtils.getString(model, "printFlag"));
                    depReportPrint.setPageCells(ParamUtils.getInt(model, "pageCells"));
                    depReportPrint.setLoopFlag(ParamUtils.getString(model, "loopFlag"));
                    depReportPrint.setEnCondition(ParamUtils.getString(model, "enCondition"));
                    depReportPrint.setCreator(ParamUtils.getString(model, "creator"));
                    depReportPrint.setCreateDateTime(ParamUtils.getDate(model, "createDateTime"));
                    depReportPrint.setModifier(ParamUtils.getString(model, "modifier"));
                    depReportPrint.setModifyDateTime(ParamUtils.getDate(model, "modifyDateTime"));
		    depReportPrint.setCreator(actorId);
		    this.depReportPrintService.save(depReportPrint);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return depReportPrint;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                DepReportPrint depReportPrint = depReportPrintService.getDepReportPrint(RequestUtils.getLong(request, "id"));

		Tools.populate(depReportPrint, params);

                depReportPrint.setCellId(RequestUtils.getLong(request, "cellId"));
                depReportPrint.setPrintFlag(request.getParameter("printFlag"));
                depReportPrint.setPageCells(RequestUtils.getInt(request, "pageCells"));
                depReportPrint.setLoopFlag(request.getParameter("loopFlag"));
                depReportPrint.setEnCondition(request.getParameter("enCondition"));
                depReportPrint.setCreator(request.getParameter("creator"));
                depReportPrint.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                depReportPrint.setModifier(request.getParameter("modifier"));
                depReportPrint.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
	
		depReportPrintService.save(depReportPrint);   

		return this.list(request, modelMap);
	}

    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DepReportPrint depReportPrint = depReportPrintService.getDepReportPrint(Long.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (depReportPrint != null && (StringUtils.equals(depReportPrint.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//depReportPrint.setDeleteFlag(1);
						depReportPrintService.save(depReportPrint);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepReportPrint depReportPrint = depReportPrintService
					.getDepReportPrint(Long.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (depReportPrint != null && ( StringUtils.equals(depReportPrint.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//depReportPrint.setDeleteFlag(1);
				depReportPrintService.save(depReportPrint);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

    
        @RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                DepReportPrint depReportPrint = depReportPrintService.getDepReportPrint(RequestUtils.getLong(request, "id"));
		if(depReportPrint != null) {
		    request.setAttribute("depReportPrint", depReportPrint);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depReportPrint.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/report/depReportPrint/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                DepReportPrint depReportPrint = depReportPrintService.getDepReportPrint(RequestUtils.getLong(request, "id"));
		request.setAttribute("depReportPrint", depReportPrint);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depReportPrint.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/report/depReportPrint/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depReportPrint.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/report/depReportPrint/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportPrintQuery query = new DepReportPrintQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		*/
		if(!loginContext.isSystemAdministrator()){
		  String actorId = loginContext.getActorId();
		  query.createBy(actorId);
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
	 
		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");
		 

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = depReportPrintService.getDepReportPrintCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

                        if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DepReportPrint> list = depReportPrintService.getDepReportPrintsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportPrint depReportPrint : list) {
					JSONObject rowJSON = depReportPrint.toJsonObject();
					rowJSON.put("id", depReportPrint.getId());
					rowJSON.put("rowId", depReportPrint.getId());
					rowJSON.put("depReportPrintId", depReportPrint.getId());
                                        rowJSON.put("startIndex", ++start);
 					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}


	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportPrintQuery query = new DepReportPrintQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepReportPrintDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		*/
		if(!loginContext.isSystemAdministrator()){
		  String actorId = loginContext.getActorId();
		  query.createBy(actorId);
		}

                int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = depReportPrintService.getDepReportPrintCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

                        String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null
					&& !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DepReportPrint> list = depReportPrintService.getDepReportPrintsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportPrint depReportPrint : list) {
					JSONObject rowJSON = depReportPrint.toJsonObject();
					rowJSON.put("id", depReportPrint.getId());
					rowJSON.put("depReportPrintId", depReportPrint.getId());
                                        rowJSON.put("startIndex", ++start);
 					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}


        @RequestMapping 
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

                String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
 

		return new ModelAndView("/dep/report/depReportPrint/list", modelMap);
	}

}
