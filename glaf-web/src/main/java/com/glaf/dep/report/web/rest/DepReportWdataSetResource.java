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


import com.glaf.dep.report.domain.DepReportWdataSet;
import com.glaf.dep.report.query.DepReportWdataSetQuery;
import com.glaf.dep.report.service.DepReportWdataSetService;
import com.glaf.dep.report.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/dep/report/depReportWdataSet")
public class DepReportWdataSetResource {
	protected static final Log logger = LogFactory.getLog(DepReportWdataSetResource.class);

	protected DepReportWdataSetService depReportWdataSetService;

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
				depReportWdataSetService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                depReportWdataSetService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportWdataSetQuery query = new DepReportWdataSetQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		DepReportWdataSetDomainFactory.processDataRequest(dataRequest);

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
		int total = depReportWdataSetService.getDepReportWdataSetCountByQueryCriteria(query);
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
			List<DepReportWdataSet> list = depReportWdataSetService.getDepReportWdataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportWdataSet depReportWdataSet : list) {
					JSONObject rowJSON = depReportWdataSet.toJsonObject();
					rowJSON.put("id", depReportWdataSet.getId());
					rowJSON.put("depReportWdataSetId", depReportWdataSet.getId());
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
		DepReportWdataSetQuery query = new DepReportWdataSetQuery();
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
		int total = depReportWdataSetService.getDepReportWdataSetCountByQueryCriteria(query);
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
			List<DepReportWdataSet> list = depReportWdataSetService.getDepReportWdataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepReportWdataSet depReportWdataSet : list) {
					JSONObject rowJSON = depReportWdataSet.toJsonObject();
					rowJSON.put("id", depReportWdataSet.getId());
					rowJSON.put("depReportWdataSetId", depReportWdataSet.getId());
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
	@Path("/saveDepReportWdataSet")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepReportWdataSet(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportWdataSet depReportWdataSet = new DepReportWdataSet();
		try {
		    Tools.populate(depReportWdataSet, params);

                    depReportWdataSet.setWdatasetId(RequestUtils.getLong(request, "wdatasetId"));
                    depReportWdataSet.setRepTemplateId(RequestUtils.getLong(request, "repTemplateId"));
                    depReportWdataSet.setCode(request.getParameter("code"));
                    depReportWdataSet.setName(request.getParameter("name"));
                    depReportWdataSet.setTableName(request.getParameter("tableName"));
                    depReportWdataSet.setDataTableName(request.getParameter("dataTableName"));
                    depReportWdataSet.setEnConditon(request.getParameter("enConditon"));
                    depReportWdataSet.setOrder(RequestUtils.getInt(request, "order"));
                    depReportWdataSet.setRuleJson(request.getParameter("ruleJson"));
                    depReportWdataSet.setPsql(request.getParameter("psql"));
                    depReportWdataSet.setCreator(request.getParameter("creator"));
                    depReportWdataSet.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
                    depReportWdataSet.setModifier(request.getParameter("modifier"));
                    depReportWdataSet.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));

		    this.depReportWdataSetService.save(depReportWdataSet);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.dep.report.service.depReportWdataSetService")
	public void setDepReportWdataSetService(DepReportWdataSetService depReportWdataSetService) {
		this.depReportWdataSetService = depReportWdataSetService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepReportWdataSet depReportWdataSet = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  depReportWdataSet = depReportWdataSetService.getDepReportWdataSet(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (depReportWdataSet != null) {
		    result =  depReportWdataSet.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", depReportWdataSet.getId());
		    result.put("depReportWdataSetId", depReportWdataSet.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
