package com.glaf.dep.report.web.rest;

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


import com.glaf.dep.report.domain.DepReportVariable;
import com.glaf.dep.report.query.DepReportVariableQuery;
import com.glaf.dep.report.service.DepReportVariableService;
import com.glaf.dep.report.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/dep/report/depReportVariable")
public class DepReportVariableResource {
	protected static final Log logger = LogFactory.getLog(DepReportVariableResource.class);

	protected DepReportVariableService depReportVariableService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				depReportVariableService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                depReportVariableService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportVariableQuery query = new DepReportVariableQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		DepReportVariableDomainFactory.processDataRequest(dataRequest);

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
		int total = depReportVariableService.getDepReportVariableCountByQueryCriteria(query);
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
			List<DepReportVariable> list = depReportVariableService.getDepReportVariablesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportVariable depReportVariable : list) {
					JSONObject rowJSON = depReportVariable.toJsonObject();
					rowJSON.put("id", depReportVariable.getId());
					rowJSON.put("depReportVariableId", depReportVariable.getId());
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
		DepReportVariableQuery query = new DepReportVariableQuery();
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
		int total = depReportVariableService.getDepReportVariableCountByQueryCriteria(query);
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
			List<DepReportVariable> list = depReportVariableService.getDepReportVariablesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportVariable depReportVariable : list) {
					JSONObject rowJSON = depReportVariable.toJsonObject();
					rowJSON.put("id", depReportVariable.getId());
					rowJSON.put("depReportVariableId", depReportVariable.getId());
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
	@Path("/saveDepReportVariable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepReportVariable(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportVariable depReportVariable = new DepReportVariable();
		try {
		    Tools.populate(depReportVariable, params);

                    depReportVariable.setRepTemplateId(RequestUtils.getLong(request, "repTemplateId"));
                    depReportVariable.setName(request.getParameter("name"));
                    depReportVariable.setDtype(request.getParameter("dtype"));
                    depReportVariable.setDefaultVal(request.getParameter("defaultVal"));
                    depReportVariable.setCreator(request.getParameter("creator"));
                    depReportVariable.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                    depReportVariable.setModifier(request.getParameter("modifier"));
                    depReportVariable.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));

		    this.depReportVariableService.save(depReportVariable);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.dep.report.service.depReportVariableService")
	public void setDepReportVariableService(DepReportVariableService depReportVariableService) {
		this.depReportVariableService = depReportVariableService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepReportVariable depReportVariable = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  depReportVariable = depReportVariableService.getDepReportVariable(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (depReportVariable != null) {
		    result =  depReportVariable.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", depReportVariable.getId());
		    result.put("depReportVariableId", depReportVariable.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
