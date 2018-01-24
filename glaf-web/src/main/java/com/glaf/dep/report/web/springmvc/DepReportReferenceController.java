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

@Controller("/dep/report/depReportReference")
@RequestMapping("/dep/report/depReportReference")
public class DepReportReferenceController {
	protected static final Log logger = LogFactory.getLog(DepReportReferenceController.class);

	protected DepReportReferenceService depReportReferenceService;

	public DepReportReferenceController() {

	}

        @javax.annotation.Resource(name = "com.glaf.dep.report.service.depReportReferenceService")
	public void setDepReportReferenceService(DepReportReferenceService depReportReferenceService) {
		this.depReportReferenceService = depReportReferenceService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		DepReportReference depReportReference = new DepReportReference();
		Tools.populate(depReportReference, params);

                depReportReference.setRepDataSetId(RequestUtils.getLong(request, "repDataSetId"));
                depReportReference.setRepDataId(RequestUtils.getLong(request, "repDataId"));
                depReportReference.setRefType(request.getParameter("refType"));
                depReportReference.setRefMode(request.getParameter("refMode"));
                depReportReference.setEnCondition(request.getParameter("enCondition"));
                depReportReference.setColumnName(request.getParameter("columnName"));
                depReportReference.setTableName(request.getParameter("tableName"));
                depReportReference.setReportId(request.getParameter("reportId"));
                depReportReference.setReportCellId(request.getParameter("reportCellId"));
                depReportReference.setRuleJson(request.getParameter("ruleJson"));
                depReportReference.setCreator(request.getParameter("creator"));
                depReportReference.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                depReportReference.setModifier(request.getParameter("modifier"));
                depReportReference.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		
		//depReportReference.setCreator(actorId);
         
		depReportReferenceService.save(depReportReference);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveDepReportReference")
	public byte[] saveDepReportReference(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportReference depReportReference = new DepReportReference();
		try {
		    Tools.populate(depReportReference, params);
                    depReportReference.setRepDataSetId(RequestUtils.getLong(request, "repDataSetId"));
                    depReportReference.setRepDataId(RequestUtils.getLong(request, "repDataId"));
                    depReportReference.setRefType(request.getParameter("refType"));
                    depReportReference.setRefMode(request.getParameter("refMode"));
                    depReportReference.setEnCondition(request.getParameter("enCondition"));
                    depReportReference.setColumnName(request.getParameter("columnName"));
                    depReportReference.setTableName(request.getParameter("tableName"));
                    depReportReference.setReportId(request.getParameter("reportId"));
                    depReportReference.setReportCellId(request.getParameter("reportCellId"));
                    depReportReference.setRuleJson(request.getParameter("ruleJson"));
                    depReportReference.setCreator(request.getParameter("creator"));
                    depReportReference.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                    depReportReference.setModifier(request.getParameter("modifier"));
                    depReportReference.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		    //depReportReference.setCreator(actorId);
		    this.depReportReferenceService.save(depReportReference);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DepReportReference saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		DepReportReference depReportReference = new DepReportReference();
		try {
		    Tools.populate(depReportReference, model);
                    depReportReference.setRepDataSetId(ParamUtils.getLong(model, "repDataSetId"));
                    depReportReference.setRepDataId(ParamUtils.getLong(model, "repDataId"));
                    depReportReference.setRefType(ParamUtils.getString(model, "refType"));
                    depReportReference.setRefMode(ParamUtils.getString(model, "refMode"));
                    depReportReference.setEnCondition(ParamUtils.getString(model, "enCondition"));
                    depReportReference.setColumnName(ParamUtils.getString(model, "columnName"));
                    depReportReference.setTableName(ParamUtils.getString(model, "tableName"));
                    depReportReference.setReportId(ParamUtils.getString(model, "reportId"));
                    depReportReference.setReportCellId(ParamUtils.getString(model, "reportCellId"));
                    depReportReference.setRuleJson(ParamUtils.getString(model, "ruleJson"));
                    depReportReference.setCreator(ParamUtils.getString(model, "creator"));
                    depReportReference.setCreateDateTime(ParamUtils.getDate(model, "createDateTime"));
                    depReportReference.setModifier(ParamUtils.getString(model, "modifier"));
                    depReportReference.setModifyDateTime(ParamUtils.getDate(model, "modifyDateTime"));
		    depReportReference.setCreator(actorId);
		    this.depReportReferenceService.save(depReportReference);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return depReportReference;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                DepReportReference depReportReference = depReportReferenceService.getDepReportReference(RequestUtils.getLong(request, "id"));

		Tools.populate(depReportReference, params);

                depReportReference.setRepDataSetId(RequestUtils.getLong(request, "repDataSetId"));
                depReportReference.setRepDataId(RequestUtils.getLong(request, "repDataId"));
                depReportReference.setRefType(request.getParameter("refType"));
                depReportReference.setRefMode(request.getParameter("refMode"));
                depReportReference.setEnCondition(request.getParameter("enCondition"));
                depReportReference.setColumnName(request.getParameter("columnName"));
                depReportReference.setTableName(request.getParameter("tableName"));
                depReportReference.setReportId(request.getParameter("reportId"));
                depReportReference.setReportCellId(request.getParameter("reportCellId"));
                depReportReference.setRuleJson(request.getParameter("ruleJson"));
                depReportReference.setCreator(request.getParameter("creator"));
                depReportReference.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                depReportReference.setModifier(request.getParameter("modifier"));
                depReportReference.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
	
		depReportReferenceService.save(depReportReference);   

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
					DepReportReference depReportReference = depReportReferenceService.getDepReportReference(Long.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (depReportReference != null && (StringUtils.equals(depReportReference.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//depReportReference.setDeleteFlag(1);
						depReportReferenceService.save(depReportReference);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepReportReference depReportReference = depReportReferenceService
					.getDepReportReference(Long.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (depReportReference != null && ( StringUtils.equals(depReportReference.getCreator(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//depReportReference.setDeleteFlag(1);
				depReportReferenceService.save(depReportReference);
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
                DepReportReference depReportReference = depReportReferenceService.getDepReportReference(RequestUtils.getLong(request, "id"));
		if(depReportReference != null) {
		    request.setAttribute("depReportReference", depReportReference);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depReportReference.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/report/depReportReference/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                DepReportReference depReportReference = depReportReferenceService.getDepReportReference(RequestUtils.getLong(request, "id"));
		request.setAttribute("depReportReference", depReportReference);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depReportReference.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/report/depReportReference/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depReportReference.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/report/depReportReference/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportReferenceQuery query = new DepReportReferenceQuery();
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
		int total = depReportReferenceService.getDepReportReferenceCountByQueryCriteria(query);
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
			List<DepReportReference> list = depReportReferenceService.getDepReportReferencesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportReference depReportReference : list) {
					JSONObject rowJSON = depReportReference.toJsonObject();
					rowJSON.put("id", depReportReference.getId());
					rowJSON.put("rowId", depReportReference.getId());
					rowJSON.put("depReportReferenceId", depReportReference.getId());
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
		DepReportReferenceQuery query = new DepReportReferenceQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepReportReferenceDomainFactory.processDataRequest(dataRequest);

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
		int total = depReportReferenceService.getDepReportReferenceCountByQueryCriteria(query);
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
			List<DepReportReference> list = depReportReferenceService.getDepReportReferencesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportReference depReportReference : list) {
					JSONObject rowJSON = depReportReference.toJsonObject();
					rowJSON.put("id", depReportReference.getId());
					rowJSON.put("depReportReferenceId", depReportReference.getId());
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
 

		return new ModelAndView("/dep/report/depReportReference/list", modelMap);
	}

}
