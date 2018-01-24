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


import com.glaf.monitor.server.domain.MonitorTerminalItem;
import com.glaf.monitor.server.query.MonitorTerminalItemQuery;
import com.glaf.monitor.server.service.MonitorTerminalItemService;
import com.glaf.monitor.server.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/monitor/monitorTerminalItem")
public class MonitorTerminalItemResource {
	protected static final Log logger = LogFactory.getLog(MonitorTerminalItemResource.class);

	protected MonitorTerminalItemService monitorTerminalItemService;

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
				monitorTerminalItemService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                monitorTerminalItemService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalItemQuery query = new MonitorTerminalItemQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		MonitorTerminalItemDomainFactory.processDataRequest(dataRequest);

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
		int total = monitorTerminalItemService.getMonitorTerminalItemCountByQueryCriteria(query);
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
			List<MonitorTerminalItem> list = monitorTerminalItemService.getMonitorTerminalItemsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorTerminalItem monitorTerminalItem : list) {
					JSONObject rowJSON = monitorTerminalItem.toJsonObject();
					rowJSON.put("id", monitorTerminalItem.getId());
					rowJSON.put("monitorTerminalItemId", monitorTerminalItem.getId());
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
		MonitorTerminalItemQuery query = new MonitorTerminalItemQuery();
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
		int total = monitorTerminalItemService.getMonitorTerminalItemCountByQueryCriteria(query);
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
			List<MonitorTerminalItem> list = monitorTerminalItemService.getMonitorTerminalItemsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorTerminalItem monitorTerminalItem : list) {
					JSONObject rowJSON = monitorTerminalItem.toJsonObject();
					rowJSON.put("id", monitorTerminalItem.getId());
					rowJSON.put("monitorTerminalItemId", monitorTerminalItem.getId());
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
	@Path("/saveMonitorTerminalItem")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveMonitorTerminalItem(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalItem monitorTerminalItem = new MonitorTerminalItem();
		try {
		    Tools.populate(monitorTerminalItem, params);

                    monitorTerminalItem.setTerminalId(request.getParameter("terminalId"));
                    monitorTerminalItem.setCode(request.getParameter("code"));
                    monitorTerminalItem.setName(request.getParameter("name"));
                    monitorTerminalItem.setUnit(request.getParameter("unit"));
                    monitorTerminalItem.setAlarmVal(RequestUtils.getInt(request, "alarmVal"));
                    monitorTerminalItem.setRefType(request.getParameter("refType"));
                    monitorTerminalItem.setWarningType(request.getParameter("warningType"));
                    monitorTerminalItem.setMonitorServiceAddress(request.getParameter("monitorServiceAddress"));
                    monitorTerminalItem.setCreateby(request.getParameter("createby"));
                    monitorTerminalItem.setCreatetime(RequestUtils.getDate(request, "createtime"));
                    monitorTerminalItem.setUpdateby(request.getParameter("updateby"));
                    monitorTerminalItem.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                    monitorTerminalItem.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		    this.monitorTerminalItemService.save(monitorTerminalItem);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorTerminalItemService")
	public void setMonitorTerminalItemService(MonitorTerminalItemService monitorTerminalItemService) {
		this.monitorTerminalItemService = monitorTerminalItemService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		MonitorTerminalItem monitorTerminalItem = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  monitorTerminalItem = monitorTerminalItemService.getMonitorTerminalItem(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (monitorTerminalItem != null) {
		    result =  monitorTerminalItem.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", monitorTerminalItem.getId());
		    result.put("monitorTerminalItemId", monitorTerminalItem.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
