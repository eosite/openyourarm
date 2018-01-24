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

@Controller("/report/reportTmpDataSet")
@RequestMapping("/report/reportTmpDataSet")
public class ReportTmpDataSetController {
	protected static final Log logger = LogFactory.getLog(ReportTmpDataSetController.class);

	protected ReportTmpDataSetService reportTmpDataSetService;

	public ReportTmpDataSetController() {

	}

        @javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpDataSetService")
	public void setReportTmpDataSetService(ReportTmpDataSetService reportTmpDataSetService) {
		this.reportTmpDataSetService = reportTmpDataSetService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		ReportTmpDataSet reportTmpDataSet = new ReportTmpDataSet();
		Tools.populate(reportTmpDataSet, params);

                reportTmpDataSet.setTemplateId(request.getParameter("templateId"));
                reportTmpDataSet.setCode(request.getParameter("code"));
                reportTmpDataSet.setName(request.getParameter("name"));
                reportTmpDataSet.setTitle(request.getParameter("title"));
                reportTmpDataSet.setType(request.getParameter("tYPE"));
                reportTmpDataSet.setCreator(request.getParameter("creator"));
                reportTmpDataSet.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                reportTmpDataSet.setModifier(request.getParameter("modifier"));
                reportTmpDataSet.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		
		//reportTmpDataSet.setCreator(actorId);
         
		reportTmpDataSetService.save(reportTmpDataSet);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveReportTmpDataSet")
	public byte[] saveReportTmpDataSet(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpDataSet reportTmpDataSet = new ReportTmpDataSet();
		try {
		    Tools.populate(reportTmpDataSet, params);
                    reportTmpDataSet.setTemplateId(request.getParameter("templateId"));
                    reportTmpDataSet.setCode(request.getParameter("code"));
                    reportTmpDataSet.setName(request.getParameter("name"));
                    reportTmpDataSet.setTitle(request.getParameter("title"));
                    reportTmpDataSet.setType(request.getParameter("tYPE"));
                    reportTmpDataSet.setCreator(request.getParameter("creator"));
                    reportTmpDataSet.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                    reportTmpDataSet.setModifier(request.getParameter("modifier"));
                    reportTmpDataSet.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		    //reportTmpDataSet.setCreator(actorId);
		    this.reportTmpDataSetService.save(reportTmpDataSet);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ReportTmpDataSet saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		ReportTmpDataSet reportTmpDataSet = new ReportTmpDataSet();
		try {
		    Tools.populate(reportTmpDataSet, model);
                    reportTmpDataSet.setTemplateId(ParamUtils.getString(model, "templateId"));
                    reportTmpDataSet.setCode(ParamUtils.getString(model, "code"));
                    reportTmpDataSet.setName(ParamUtils.getString(model, "name"));
                    reportTmpDataSet.setTitle(ParamUtils.getString(model, "title"));
                    reportTmpDataSet.setType(ParamUtils.getString(model, "tYPE"));
                    reportTmpDataSet.setCreator(ParamUtils.getString(model, "creator"));
                    reportTmpDataSet.setCreateDatetime(ParamUtils.getDate(model, "createDatetime"));
                    reportTmpDataSet.setModifier(ParamUtils.getString(model, "modifier"));
                    reportTmpDataSet.setModifyDatetime(ParamUtils.getDate(model, "modifyDatetime"));
		    reportTmpDataSet.setCreator(actorId);
		    this.reportTmpDataSetService.save(reportTmpDataSet);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return reportTmpDataSet;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                ReportTmpDataSet reportTmpDataSet = reportTmpDataSetService.getReportTmpDataSet(request.getParameter("id"));

		Tools.populate(reportTmpDataSet, params);

                reportTmpDataSet.setTemplateId(request.getParameter("templateId"));
                reportTmpDataSet.setCode(request.getParameter("code"));
                reportTmpDataSet.setName(request.getParameter("name"));
                reportTmpDataSet.setTitle(request.getParameter("title"));
                reportTmpDataSet.setType(request.getParameter("tYPE"));
                reportTmpDataSet.setCreator(request.getParameter("creator"));
                reportTmpDataSet.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                reportTmpDataSet.setModifier(request.getParameter("modifier"));
                reportTmpDataSet.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
	
		reportTmpDataSetService.save(reportTmpDataSet);   

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
					ReportTmpDataSet reportTmpDataSet = reportTmpDataSetService.getReportTmpDataSet(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (reportTmpDataSet != null && (StringUtils.equals(reportTmpDataSet.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//reportTmpDataSet.setDeleteFlag(1);
						reportTmpDataSetService.save(reportTmpDataSet);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			ReportTmpDataSet reportTmpDataSet = reportTmpDataSetService
					.getReportTmpDataSet(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (reportTmpDataSet != null && ( StringUtils.equals(reportTmpDataSet.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//reportTmpDataSet.setDeleteFlag(1);
				reportTmpDataSetService.save(reportTmpDataSet);
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
                ReportTmpDataSet reportTmpDataSet = reportTmpDataSetService.getReportTmpDataSet(request.getParameter("id"));
		if(reportTmpDataSet != null) {
		    request.setAttribute("reportTmpDataSet", reportTmpDataSet);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("reportTmpDataSet.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/report/reportTmpDataSet/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                ReportTmpDataSet reportTmpDataSet = reportTmpDataSetService.getReportTmpDataSet(request.getParameter("id"));
		request.setAttribute("reportTmpDataSet", reportTmpDataSet);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("reportTmpDataSet.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/report/reportTmpDataSet/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("reportTmpDataSet.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/report/reportTmpDataSet/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpDataSetQuery query = new ReportTmpDataSetQuery();
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
		int total = reportTmpDataSetService.getReportTmpDataSetCountByQueryCriteria(query);
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
			List<ReportTmpDataSet> list = reportTmpDataSetService.getReportTmpDataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (ReportTmpDataSet reportTmpDataSet : list) {
					JSONObject rowJSON = reportTmpDataSet.toJsonObject();
					rowJSON.put("id", reportTmpDataSet.getId());
					rowJSON.put("rowId", reportTmpDataSet.getId());
					rowJSON.put("reportTmpDataSetId", reportTmpDataSet.getId());
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
		ReportTmpDataSetQuery query = new ReportTmpDataSetQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ReportTmpDataSetDomainFactory.processDataRequest(dataRequest);

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
		int total = reportTmpDataSetService.getReportTmpDataSetCountByQueryCriteria(query);
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
			List<ReportTmpDataSet> list = reportTmpDataSetService.getReportTmpDataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (ReportTmpDataSet reportTmpDataSet : list) {
					JSONObject rowJSON = reportTmpDataSet.toJsonObject();
					rowJSON.put("id", reportTmpDataSet.getId());
					rowJSON.put("reportTmpDataSetId", reportTmpDataSet.getId());
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
 

		return new ModelAndView("/report/reportTmpDataSet/list", modelMap);
	}

}
