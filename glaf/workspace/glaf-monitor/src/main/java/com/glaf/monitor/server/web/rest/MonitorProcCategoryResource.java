package com.glaf.monitor.server.web.rest;

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


import com.glaf.monitor.server.domain.MonitorProcCategory;
import com.glaf.monitor.server.query.MonitorProcCategoryQuery;
import com.glaf.monitor.server.service.MonitorProcCategoryService;
import com.glaf.monitor.server.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/monitor/monitorProcCategory")
public class MonitorProcCategoryResource {
	protected static final Log logger = LogFactory.getLog(MonitorProcCategoryResource.class);

	protected MonitorProcCategoryService monitorProcCategoryService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				monitorProcCategoryService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                monitorProcCategoryService.deleteById(RequestUtils.getInt(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcCategoryQuery query = new MonitorProcCategoryQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		MonitorProcCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = monitorProcCategoryService.getMonitorProcCategoryCountByQueryCriteria(query);
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
			List<MonitorProcCategory> list = monitorProcCategoryService.getMonitorProcCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorProcCategory monitorProcCategory : list) {
					JSONObject rowJSON = monitorProcCategory.toJsonObject();
					rowJSON.put("id", monitorProcCategory.getId());
					rowJSON.put("monitorProcCategoryId", monitorProcCategory.getId());
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
		MonitorProcCategoryQuery query = new MonitorProcCategoryQuery();
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
		int total = monitorProcCategoryService.getMonitorProcCategoryCountByQueryCriteria(query);
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
			List<MonitorProcCategory> list = monitorProcCategoryService.getMonitorProcCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorProcCategory monitorProcCategory : list) {
					JSONObject rowJSON = monitorProcCategory.toJsonObject();
					rowJSON.put("id", monitorProcCategory.getId());
					rowJSON.put("monitorProcCategoryId", monitorProcCategory.getId());
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
	@Path("/saveMonitorProcCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveMonitorProcCategory(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcCategory monitorProcCategory = new MonitorProcCategory();
		try {
		    Tools.populate(monitorProcCategory, params);

                    monitorProcCategory.setName(request.getParameter("name"));
                    monitorProcCategory.setCode(request.getParameter("code"));
                    monitorProcCategory.setPid(RequestUtils.getInt(request, "pid"));
                    monitorProcCategory.setTreeid(request.getParameter("treeid"));
                    monitorProcCategory.setCreateby(request.getParameter("createby"));
                    monitorProcCategory.setCreatetime(RequestUtils.getDate(request, "createtime"));
                    monitorProcCategory.setUpdateby(request.getParameter("updateby"));
                    monitorProcCategory.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                    monitorProcCategory.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		    this.monitorProcCategoryService.save(monitorProcCategory);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorProcCategoryService")
	public void setMonitorProcCategoryService(MonitorProcCategoryService monitorProcCategoryService) {
		this.monitorProcCategoryService = monitorProcCategoryService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		MonitorProcCategory monitorProcCategory = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  monitorProcCategory = monitorProcCategoryService.getMonitorProcCategory(RequestUtils.getInt(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (monitorProcCategory != null) {
		    result =  monitorProcCategory.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", monitorProcCategory.getId());
		    result.put("monitorProcCategoryId", monitorProcCategory.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
