package com.glaf.form.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;


import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.query.FormWorkflowPlanQuery;
import com.glaf.form.core.service.FormWorkflowPlanService;
import com.glaf.form.core.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/formWorkflowPlan/formWorkflowPlan")
public class FormWorkflowPlanResource {
	protected static final Log logger = LogFactory.getLog(FormWorkflowPlanResource.class);

	protected FormWorkflowPlanService formWorkflowPlanService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
 			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				formWorkflowPlanService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                formWorkflowPlanService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		FormWorkflowPlanDomainFactory.processDataRequest(dataRequest);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
		    gridType = "kendoui";
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
		int total = formWorkflowPlanService.getFormWorkflowPlanCountByQueryCriteria(query);
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
			List<FormWorkflowPlan> list = formWorkflowPlanService.getFormWorkflowPlansByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormWorkflowPlan formWorkflowPlan : list) {
					JSONObject rowJSON = formWorkflowPlan.toJsonObject();
					rowJSON.put("id", formWorkflowPlan.getId());
					rowJSON.put("formWorkflowPlanId", formWorkflowPlan.getId());
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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
		Tools.populate(query, params);

                String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = formWorkflowPlanService.getFormWorkflowPlanCountByQueryCriteria(query);
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
			List<FormWorkflowPlan> list = formWorkflowPlanService.getFormWorkflowPlansByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormWorkflowPlan formWorkflowPlan : list) {
					JSONObject rowJSON = formWorkflowPlan.toJsonObject();
					rowJSON.put("id", formWorkflowPlan.getId());
					rowJSON.put("formWorkflowPlanId", formWorkflowPlan.getId());
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

	@POST
	@Path("/saveFormWorkflowPlan")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveFormWorkflowPlan(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowPlan formWorkflowPlan = new FormWorkflowPlan();
		try {
		    Tools.populate(formWorkflowPlan, params);

                    formWorkflowPlan.setProcessDefId(RequestUtils.getString(request, "processDefId"));
                    formWorkflowPlan.setPageId(request.getParameter("pageId"));
                    formWorkflowPlan.setCreator(request.getParameter("creator"));
                    formWorkflowPlan.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                    formWorkflowPlan.setModifier(request.getParameter("modifier"));
                    formWorkflowPlan.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
                    formWorkflowPlan.setDefId(request.getParameter("defId"));
                    formWorkflowPlan.setVersion(RequestUtils.getInt(request, "version"));

		    this.formWorkflowPlanService.save(formWorkflowPlan);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.form.core.service.formWorkflowPlanService")
	public void setFormWorkflowPlanService(FormWorkflowPlanService formWorkflowPlanService) {
		this.formWorkflowPlanService = formWorkflowPlanService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormWorkflowPlan formWorkflowPlan = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  formWorkflowPlan = formWorkflowPlanService.getFormWorkflowPlan(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (formWorkflowPlan != null) {
		    result =  formWorkflowPlan.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", formWorkflowPlan.getId());
		    result.put("formWorkflowPlanId", formWorkflowPlan.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
