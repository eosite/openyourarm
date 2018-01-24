/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.access.web.rest;

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
import com.glaf.core.util.*;

import com.glaf.core.access.domain.AccessLog;
import com.glaf.core.access.query.AccessLogQuery;
import com.glaf.core.access.service.AccessLogService;
import com.glaf.core.access.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller("/rs/accessLog")
@Path("/rs/accessLog")
public class AccessLogResource {
	protected static final Log logger = LogFactory.getLog(AccessLogResource.class);

	protected AccessLogService accessLogService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AccessLogQuery query = new AccessLogQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		AccessLogDomainFactory.processDataRequest(dataRequest);

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
		int total = accessLogService.getAccessLogCountByQueryCriteria(query);
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

			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
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

			List<AccessLog> list = accessLogService.getAccessLogsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (AccessLog accessLog : list) {
					JSONObject rowJSON = accessLog.toJsonObject();
					rowJSON.put("id", accessLog.getId());
					rowJSON.put("accessLogId", accessLog.getId());
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
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] delete(@Context HttpServletRequest request) throws IOException {
		Date dateBefore = RequestUtils.getDate(request, "dateBefore");
		boolean result = false;
		if (dateBefore != null) {
			logger.debug("delete date before:"+DateUtils.getDateTime(dateBefore));
			result = accessLogService.deleteAccessLogs(dateBefore);
		}
		return ResponseUtils.responseJsonResult(result);
	}

	@POST
	@Path("/deleteLastWeek")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteLastWeek(@Context HttpServletRequest request) throws IOException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.WEEK_OF_MONTH, (cal.get(Calendar.WEEK_OF_MONTH) - 1));
		Date dateBefore = cal.getTime();
		logger.debug("delete date before:"+DateUtils.getDateTime(dateBefore));
		boolean result = accessLogService.deleteAccessLogs(dateBefore);
		return ResponseUtils.responseJsonResult(result);
	}

	@POST
	@Path("/deleteYesterday")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteYesterday(@Context HttpServletRequest request) throws IOException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, (cal.get(Calendar.DAY_OF_MONTH) - 1));
		Date dateBefore = cal.getTime();
		logger.debug("delete date before:"+DateUtils.getDateTime(dateBefore));
		boolean result = accessLogService.deleteAccessLogs(dateBefore);
		return ResponseUtils.responseJsonResult(result);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AccessLogQuery query = new AccessLogQuery();
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
		int total = accessLogService.getAccessLogCountByQueryCriteria(query);
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

			List<AccessLog> list = accessLogService.getAccessLogsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (AccessLog accessLog : list) {
					JSONObject rowJSON = accessLog.toJsonObject();
					rowJSON.put("id", accessLog.getId());
					rowJSON.put("accessLogId", accessLog.getId());
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

	@javax.annotation.Resource
	public void setAccessLogService(AccessLogService accessLogService) {
		this.accessLogService = accessLogService;
	}

}
