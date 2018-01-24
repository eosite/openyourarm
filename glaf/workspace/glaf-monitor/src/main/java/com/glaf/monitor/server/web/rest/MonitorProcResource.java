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


import com.glaf.monitor.server.domain.MonitorProc;
import com.glaf.monitor.server.query.MonitorProcQuery;
import com.glaf.monitor.server.service.MonitorProcService;
import com.glaf.monitor.server.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/monitor/monitorProc")
public class MonitorProcResource {
	protected static final Log logger = LogFactory.getLog(MonitorProcResource.class);

	protected MonitorProcService monitorProcService;

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
				monitorProcService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                monitorProcService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcQuery query = new MonitorProcQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		MonitorProcDomainFactory.processDataRequest(dataRequest);

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
		int total = monitorProcService.getMonitorProcCountByQueryCriteria(query);
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
			List<MonitorProc> list = monitorProcService.getMonitorProcsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorProc monitorProc : list) {
					JSONObject rowJSON = monitorProc.toJsonObject();
					rowJSON.put("id", monitorProc.getId());
					rowJSON.put("monitorProcId", monitorProc.getId());
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
		MonitorProcQuery query = new MonitorProcQuery();
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
		int total = monitorProcService.getMonitorProcCountByQueryCriteria(query);
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
			List<MonitorProc> list = monitorProcService.getMonitorProcsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorProc monitorProc : list) {
					JSONObject rowJSON = monitorProc.toJsonObject();
					rowJSON.put("id", monitorProc.getId());
					rowJSON.put("monitorProcId", monitorProc.getId());
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
	@Path("/saveMonitorProc")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveMonitorProc(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProc monitorProc = new MonitorProc();
		try {
		    Tools.populate(monitorProc, params);

                    monitorProc.setTerminalId(request.getParameter("terminalId"));
                    monitorProc.setLevel(request.getParameter("level"));
                    monitorProc.setProcessName(request.getParameter("processName"));
                    monitorProc.setName(request.getParameter("name"));
                    monitorProc.setProd(request.getParameter("prod"));
                    monitorProc.setVer(request.getParameter("ver"));
                    monitorProc.setType(request.getParameter("type"));
                    monitorProc.setDesc(request.getParameter("desc"));
                    monitorProc.setPort(RequestUtils.getInt(request, "port"));
                    monitorProc.setMonitorServiceAddress(request.getParameter("monitorServiceAddress"));
                    monitorProc.setStartAddress(request.getParameter("startAddress"));
                    monitorProc.setStopAddress(request.getParameter("stopAddress"));
                    monitorProc.setTerminateAddress(request.getParameter("terminateAddress"));
                    monitorProc.setStatus(RequestUtils.getInt(request, "status"));
                    monitorProc.setParentProcId(request.getParameter("parentProcId"));
                    monitorProc.setOtherItems(request.getParameter("otherItems"));
                    monitorProc.setCreateby(request.getParameter("createby"));
                    monitorProc.setCreatetime(RequestUtils.getDate(request, "createtime"));
                    monitorProc.setUpdateby(request.getParameter("updateby"));
                    monitorProc.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                    monitorProc.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		    this.monitorProcService.save(monitorProc);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorProcService")
	public void setMonitorProcService(MonitorProcService monitorProcService) {
		this.monitorProcService = monitorProcService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		MonitorProc monitorProc = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  monitorProc = monitorProcService.getMonitorProc(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (monitorProc != null) {
		    result =  monitorProc.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", monitorProc.getId());
		    result.put("monitorProcId", monitorProc.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
