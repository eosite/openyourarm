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


import com.glaf.monitor.server.domain.MonitorTerminalBusCategory;
import com.glaf.monitor.server.query.MonitorTerminalBusCategoryQuery;
import com.glaf.monitor.server.service.MonitorTerminalBusCategoryService;
import com.glaf.monitor.server.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/monitor/monitorTerminalBusCategory")
public class MonitorTerminalBusCategoryResource {
	protected static final Log logger = LogFactory.getLog(MonitorTerminalBusCategoryResource.class);

	protected MonitorTerminalBusCategoryService monitorTerminalBusCategoryService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("terminalIds");
		if (rowIds != null) {
 			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				monitorTerminalBusCategoryService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                monitorTerminalBusCategoryService.deleteById(request.getParameter("terminalId"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalBusCategoryQuery query = new MonitorTerminalBusCategoryQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		MonitorTerminalBusCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = monitorTerminalBusCategoryService.getMonitorTerminalBusCategoryCountByQueryCriteria(query);
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
			List<MonitorTerminalBusCategory> list = monitorTerminalBusCategoryService.getMonitorTerminalBusCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorTerminalBusCategory monitorTerminalBusCategory : list) {
					JSONObject rowJSON = monitorTerminalBusCategory.toJsonObject();
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
		MonitorTerminalBusCategoryQuery query = new MonitorTerminalBusCategoryQuery();
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
		int total = monitorTerminalBusCategoryService.getMonitorTerminalBusCategoryCountByQueryCriteria(query);
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
			List<MonitorTerminalBusCategory> list = monitorTerminalBusCategoryService.getMonitorTerminalBusCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorTerminalBusCategory monitorTerminalBusCategory : list) {
					JSONObject rowJSON = monitorTerminalBusCategory.toJsonObject();
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
	@Path("/saveMonitorTerminalBusCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveMonitorTerminalBusCategory(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalBusCategory monitorTerminalBusCategory = new MonitorTerminalBusCategory();
		try {
		    Tools.populate(monitorTerminalBusCategory, params);

                    monitorTerminalBusCategory.setCategoryId(RequestUtils.getInt(request, "categoryId"));
                    monitorTerminalBusCategory.setCreateby(request.getParameter("createby"));
                    monitorTerminalBusCategory.setCreatetime(RequestUtils.getDate(request, "createtime"));

		    this.monitorTerminalBusCategoryService.save(monitorTerminalBusCategory);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorTerminalBusCategoryService")
	public void setMonitorTerminalBusCategoryService(MonitorTerminalBusCategoryService monitorTerminalBusCategoryService) {
		this.monitorTerminalBusCategoryService = monitorTerminalBusCategoryService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		MonitorTerminalBusCategory monitorTerminalBusCategory = null;
		if (StringUtils.isNotEmpty(request.getParameter("terminalId"))) {
                  monitorTerminalBusCategory = monitorTerminalBusCategoryService.getMonitorTerminalBusCategory(request.getParameter("terminalId"));
		}
		JSONObject result = new JSONObject();
		if (monitorTerminalBusCategory != null) {
		    result =  monitorTerminalBusCategory.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
