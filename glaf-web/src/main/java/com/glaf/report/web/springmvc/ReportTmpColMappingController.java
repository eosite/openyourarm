package com.glaf.report.web.springmvc;

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
 
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;
import com.glaf.report.core.service.*;
import com.glaf.report.core.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/report/reportTmpColMapping")
@RequestMapping("/report/reportTmpColMapping")
public class ReportTmpColMappingController {
	protected static final Log logger = LogFactory.getLog(ReportTmpColMappingController.class);

	protected ReportTmpColMappingService reportTmpColMappingService;

	public ReportTmpColMappingController() {

	}

        @javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpColMappingService")
	public void setReportTmpColMappingService(ReportTmpColMappingService reportTmpColMappingService) {
		this.reportTmpColMappingService = reportTmpColMappingService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		ReportTmpColMapping reportTmpColMapping = new ReportTmpColMapping();
		Tools.populate(reportTmpColMapping, params);

                reportTmpColMapping.setDataSetMappingId(request.getParameter("dataSetMappingId"));
                reportTmpColMapping.setColCode(request.getParameter("colCode"));
                reportTmpColMapping.setColName(request.getParameter("colName"));
                reportTmpColMapping.setColTitle(request.getParameter("colTitle"));
                reportTmpColMapping.setColDtype(request.getParameter("colDtype"));
                reportTmpColMapping.setColMappingCode(request.getParameter("colMappingCode"));
                reportTmpColMapping.setColMappingName(request.getParameter("colMappingName"));
                reportTmpColMapping.setColMappingTitle(request.getParameter("colMappingTitle"));
                reportTmpColMapping.setColMappingDtype(request.getParameter("colMappingDtype"));
                reportTmpColMapping.setCreator(request.getParameter("creator"));
                reportTmpColMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                reportTmpColMapping.setModifier(request.getParameter("modifier"));
                reportTmpColMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		
		//reportTmpColMapping.setCreateBy(actorId);
         
		reportTmpColMappingService.save(reportTmpColMapping);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveReportTmpColMapping")
	public byte[] saveReportTmpColMapping(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpColMapping reportTmpColMapping = new ReportTmpColMapping();
		try {
		    Tools.populate(reportTmpColMapping, params);
                    reportTmpColMapping.setDataSetMappingId(request.getParameter("dataSetMappingId"));
                    reportTmpColMapping.setColCode(request.getParameter("colCode"));
                    reportTmpColMapping.setColName(request.getParameter("colName"));
                    reportTmpColMapping.setColTitle(request.getParameter("colTitle"));
                    reportTmpColMapping.setColDtype(request.getParameter("colDtype"));
                    reportTmpColMapping.setColMappingCode(request.getParameter("colMappingCode"));
                    reportTmpColMapping.setColMappingName(request.getParameter("colMappingName"));
                    reportTmpColMapping.setColMappingTitle(request.getParameter("colMappingTitle"));
                    reportTmpColMapping.setColMappingDtype(request.getParameter("colMappingDtype"));
                    reportTmpColMapping.setCreator(request.getParameter("creator"));
                    reportTmpColMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                    reportTmpColMapping.setModifier(request.getParameter("modifier"));
                    reportTmpColMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		    //reportTmpColMapping.setCreateBy(actorId);
		    this.reportTmpColMappingService.save(reportTmpColMapping);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ReportTmpColMapping saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		ReportTmpColMapping reportTmpColMapping = new ReportTmpColMapping();
		try {
		    Tools.populate(reportTmpColMapping, model);
                    reportTmpColMapping.setDataSetMappingId(ParamUtils.getString(model, "dataSetMappingId"));
                    reportTmpColMapping.setColCode(ParamUtils.getString(model, "colCode"));
                    reportTmpColMapping.setColName(ParamUtils.getString(model, "colName"));
                    reportTmpColMapping.setColTitle(ParamUtils.getString(model, "colTitle"));
                    reportTmpColMapping.setColDtype(ParamUtils.getString(model, "colDtype"));
                    reportTmpColMapping.setColMappingCode(ParamUtils.getString(model, "colMappingCode"));
                    reportTmpColMapping.setColMappingName(ParamUtils.getString(model, "colMappingName"));
                    reportTmpColMapping.setColMappingTitle(ParamUtils.getString(model, "colMappingTitle"));
                    reportTmpColMapping.setColMappingDtype(ParamUtils.getString(model, "colMappingDtype"));
                    reportTmpColMapping.setCreator(ParamUtils.getString(model, "creator"));
                    reportTmpColMapping.setCreateDatetime(ParamUtils.getDate(model, "createDatetime"));
                    reportTmpColMapping.setModifier(ParamUtils.getString(model, "modifier"));
                    reportTmpColMapping.setModifyDatetime(ParamUtils.getDate(model, "modifyDatetime"));
		    reportTmpColMapping.setCreator(actorId);
		    this.reportTmpColMappingService.save(reportTmpColMapping);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return reportTmpColMapping;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                ReportTmpColMapping reportTmpColMapping = reportTmpColMappingService.getReportTmpColMapping(request.getParameter("id"));

		Tools.populate(reportTmpColMapping, params);

                reportTmpColMapping.setDataSetMappingId(request.getParameter("dataSetMappingId"));
                reportTmpColMapping.setColCode(request.getParameter("colCode"));
                reportTmpColMapping.setColName(request.getParameter("colName"));
                reportTmpColMapping.setColTitle(request.getParameter("colTitle"));
                reportTmpColMapping.setColDtype(request.getParameter("colDtype"));
                reportTmpColMapping.setColMappingCode(request.getParameter("colMappingCode"));
                reportTmpColMapping.setColMappingName(request.getParameter("colMappingName"));
                reportTmpColMapping.setColMappingTitle(request.getParameter("colMappingTitle"));
                reportTmpColMapping.setColMappingDtype(request.getParameter("colMappingDtype"));
                reportTmpColMapping.setCreator(request.getParameter("creator"));
                reportTmpColMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                reportTmpColMapping.setModifier(request.getParameter("modifier"));
                reportTmpColMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
	
		reportTmpColMappingService.save(reportTmpColMapping);   

		return this.list(request, modelMap);
	}

    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					ReportTmpColMapping reportTmpColMapping = reportTmpColMappingService.getReportTmpColMapping(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (reportTmpColMapping != null && (StringUtils.equals(reportTmpColMapping.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//reportTmpColMapping.setDeleteFlag(1);
						reportTmpColMappingService.save(reportTmpColMapping);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			ReportTmpColMapping reportTmpColMapping = reportTmpColMappingService
					.getReportTmpColMapping(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (reportTmpColMapping != null && ( StringUtils.equals(reportTmpColMapping.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//reportTmpColMapping.setDeleteFlag(1);
				reportTmpColMappingService.save(reportTmpColMapping);
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
                ReportTmpColMapping reportTmpColMapping = reportTmpColMappingService.getReportTmpColMapping(request.getParameter("id"));
		if(reportTmpColMapping != null) {
		    request.setAttribute("reportTmpColMapping", reportTmpColMapping);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("reportTmpColMapping.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/report/reportTmpColMapping/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                ReportTmpColMapping reportTmpColMapping = reportTmpColMappingService.getReportTmpColMapping(request.getParameter("id"));
		request.setAttribute("reportTmpColMapping", reportTmpColMapping);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("reportTmpColMapping.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/report/reportTmpColMapping/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("reportTmpColMapping.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/report/reportTmpColMapping/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpColMappingQuery query = new ReportTmpColMappingQuery();
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
		int total = reportTmpColMappingService.getReportTmpColMappingCountByQueryCriteria(query);
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
			List<ReportTmpColMapping> list = reportTmpColMappingService.getReportTmpColMappingsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (ReportTmpColMapping reportTmpColMapping : list) {
					JSONObject rowJSON = reportTmpColMapping.toJsonObject();
					rowJSON.put("id", reportTmpColMapping.getId());
					rowJSON.put("rowId", reportTmpColMapping.getId());
					rowJSON.put("reportTmpColMappingId", reportTmpColMapping.getId());
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
		ReportTmpColMappingQuery query = new ReportTmpColMappingQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ReportTmpColMappingDomainFactory.processDataRequest(dataRequest);

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
		int total = reportTmpColMappingService.getReportTmpColMappingCountByQueryCriteria(query);
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
			List<ReportTmpColMapping> list = reportTmpColMappingService.getReportTmpColMappingsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (ReportTmpColMapping reportTmpColMapping : list) {
					JSONObject rowJSON = reportTmpColMapping.toJsonObject();
					rowJSON.put("id", reportTmpColMapping.getId());
					rowJSON.put("reportTmpColMappingId", reportTmpColMapping.getId());
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
 

		return new ModelAndView("/report/reportTmpColMapping/list", modelMap);
	}

}
