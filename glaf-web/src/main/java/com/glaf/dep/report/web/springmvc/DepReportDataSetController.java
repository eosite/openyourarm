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

@Controller("/dep/report/depReportDataSet")
@RequestMapping("/dep/report/depReportDataSet")
public class DepReportDataSetController {
	protected static final Log logger = LogFactory.getLog(DepReportDataSetController.class);

	protected DepReportDataSetService depReportDataSetService;

	public DepReportDataSetController() {

	}

        @javax.annotation.Resource(name = "com.glaf.dep.report.service.depReportDataSetService")
	public void setDepReportDataSetService(DepReportDataSetService depReportDataSetService) {
		this.depReportDataSetService = depReportDataSetService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		DepReportDataSet depReportDataSet = new DepReportDataSet();
		Tools.populate(depReportDataSet, params);

                depReportDataSet.setRepTemplateId(RequestUtils.getLong(request, "repTemplateId"));
                depReportDataSet.setDataSetId(RequestUtils.getLong(request, "dataSetId"));
                depReportDataSet.setEnCondition(request.getParameter("enCondition"));
                depReportDataSet.setRuleJson(request.getParameter("ruleJson"));
                depReportDataSet.setCreator(request.getParameter("creator"));
                depReportDataSet.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                depReportDataSet.setModifier(request.getParameter("modifier"));
                depReportDataSet.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		
		//depReportDataSet.setCreator(actorId);
         
		depReportDataSetService.save(depReportDataSet);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveDepReportDataSet")
	public byte[] saveDepReportDataSet(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportDataSet depReportDataSet = new DepReportDataSet();
		try {
		    Tools.populate(depReportDataSet, params);
                    depReportDataSet.setRepTemplateId(RequestUtils.getLong(request, "repTemplateId"));
                    depReportDataSet.setDataSetId(RequestUtils.getLong(request, "dataSetId"));
                    depReportDataSet.setEnCondition(request.getParameter("enCondition"));
                    depReportDataSet.setRuleJson(request.getParameter("ruleJson"));
                    depReportDataSet.setCreator(request.getParameter("creator"));
                    depReportDataSet.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                    depReportDataSet.setModifier(request.getParameter("modifier"));
                    depReportDataSet.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		    //depReportDataSet.setCreator(actorId);
		    this.depReportDataSetService.save(depReportDataSet);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DepReportDataSet saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		DepReportDataSet depReportDataSet = new DepReportDataSet();
		try {
		    Tools.populate(depReportDataSet, model);
                    depReportDataSet.setRepTemplateId(ParamUtils.getLong(model, "repTemplateId"));
                    depReportDataSet.setDataSetId(ParamUtils.getLong(model, "dataSetId"));
                    depReportDataSet.setEnCondition(ParamUtils.getString(model, "enCondition"));
                    depReportDataSet.setRuleJson(ParamUtils.getString(model, "ruleJson"));
                    depReportDataSet.setCreator(ParamUtils.getString(model, "creator"));
                    depReportDataSet.setCreateDateTime(ParamUtils.getDate(model, "createDateTime"));
                    depReportDataSet.setModifier(ParamUtils.getString(model, "modifier"));
                    depReportDataSet.setModifyDateTime(ParamUtils.getDate(model, "modifyDateTime"));
		    depReportDataSet.setCreator(actorId);
		    this.depReportDataSetService.save(depReportDataSet);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return depReportDataSet;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                DepReportDataSet depReportDataSet = depReportDataSetService.getDepReportDataSet(RequestUtils.getLong(request, "id"));

		Tools.populate(depReportDataSet, params);

                depReportDataSet.setRepTemplateId(RequestUtils.getLong(request, "repTemplateId"));
                depReportDataSet.setDataSetId(RequestUtils.getLong(request, "dataSetId"));
                depReportDataSet.setEnCondition(request.getParameter("enCondition"));
                depReportDataSet.setRuleJson(request.getParameter("ruleJson"));
                depReportDataSet.setCreator(request.getParameter("creator"));
                depReportDataSet.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                depReportDataSet.setModifier(request.getParameter("modifier"));
                depReportDataSet.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
	
		depReportDataSetService.save(depReportDataSet);   

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
					DepReportDataSet depReportDataSet = depReportDataSetService.getDepReportDataSet(Long.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (depReportDataSet != null && (StringUtils.equals(depReportDataSet.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//depReportDataSet.setDeleteFlag(1);
						depReportDataSetService.save(depReportDataSet);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepReportDataSet depReportDataSet = depReportDataSetService
					.getDepReportDataSet(Long.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (depReportDataSet != null && ( StringUtils.equals(depReportDataSet.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//depReportDataSet.setDeleteFlag(1);
				depReportDataSetService.save(depReportDataSet);
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
                DepReportDataSet depReportDataSet = depReportDataSetService.getDepReportDataSet(RequestUtils.getLong(request, "id"));
		if(depReportDataSet != null) {
		    request.setAttribute("depReportDataSet", depReportDataSet);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depReportDataSet.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/report/depReportDataSet/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                DepReportDataSet depReportDataSet = depReportDataSetService.getDepReportDataSet(RequestUtils.getLong(request, "id"));
		request.setAttribute("depReportDataSet", depReportDataSet);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depReportDataSet.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/report/depReportDataSet/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depReportDataSet.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/report/depReportDataSet/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportDataSetQuery query = new DepReportDataSetQuery();
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
		int total = depReportDataSetService.getDepReportDataSetCountByQueryCriteria(query);
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
			List<DepReportDataSet> list = depReportDataSetService.getDepReportDataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportDataSet depReportDataSet : list) {
					JSONObject rowJSON = depReportDataSet.toJsonObject();
					rowJSON.put("id", depReportDataSet.getId());
					rowJSON.put("rowId", depReportDataSet.getId());
					rowJSON.put("depReportDataSetId", depReportDataSet.getId());
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
		DepReportDataSetQuery query = new DepReportDataSetQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepReportDataSetDomainFactory.processDataRequest(dataRequest);

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
		int total = depReportDataSetService.getDepReportDataSetCountByQueryCriteria(query);
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
			List<DepReportDataSet> list = depReportDataSetService.getDepReportDataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportDataSet depReportDataSet : list) {
					JSONObject rowJSON = depReportDataSet.toJsonObject();
					rowJSON.put("id", depReportDataSet.getId());
					rowJSON.put("depReportDataSetId", depReportDataSet.getId());
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
 

		return new ModelAndView("/dep/report/depReportDataSet/list", modelMap);
	}

}
