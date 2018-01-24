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

package com.glaf.datamgr.web.rest;

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

import com.glaf.datamgr.domain.SqlExport;
import com.glaf.datamgr.query.SqlExportQuery;
import com.glaf.datamgr.service.SqlExportService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/datamgr/sql/export")
public class SqlExportResource {
	protected static final Log logger = LogFactory.getLog(SqlExportResource.class);

	protected SqlExportService sqlExportService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlExportQuery query = new SqlExportQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		SqlExportDomainFactory.processDataRequest(dataRequest);

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
		int total = sqlExportService.getSqlExportCountByQueryCriteria(query);
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

			List<SqlExport> list = sqlExportService.getSqlExportsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlExport sqlExport : list) {
					JSONObject rowJSON = sqlExport.toJsonObject();
					rowJSON.put("id", sqlExport.getId());
					rowJSON.put("sqlExportId", sqlExport.getId());
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
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				sqlExportService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		sqlExportService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlExportQuery query = new SqlExportQuery();
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
		int total = sqlExportService.getSqlExportCountByQueryCriteria(query);
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

			List<SqlExport> list = sqlExportService.getSqlExportsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlExport sqlExport : list) {
					JSONObject rowJSON = sqlExport.toJsonObject();
					rowJSON.put("id", sqlExport.getId());
					rowJSON.put("sqlExportId", sqlExport.getId());
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
	@Path("/saveSqlExport")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSqlExport(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlExport sqlExport = new SqlExport();
		try {
			Tools.populate(sqlExport, params);

			sqlExport.setName(request.getParameter("name"));
			sqlExport.setTitle(request.getParameter("title"));
			sqlExport.setType(request.getParameter("type"));
			sqlExport.setServiceKey(request.getParameter("serviceKey"));
			sqlExport.setUserId(request.getParameter("userId"));
			sqlExport.setOperation(request.getParameter("operation"));
			sqlExport.setExportFlag(request.getParameter("exportFlag"));
			sqlExport.setExportDBName(request.getParameter("exportDBName"));
			sqlExport.setLastExportTime(RequestUtils.getDate(request, "lastExportTime"));
			sqlExport.setDatasetIds(request.getParameter("datasetIds"));
			sqlExport.setSqlDefIds(request.getParameter("sqlDefIds"));
			sqlExport.setScheduleFlag(request.getParameter("scheduleFlag"));
			sqlExport.setCreateTableFlag(request.getParameter("createTableFlag"));
			sqlExport.setDeleteFetch(request.getParameter("deleteFetch"));
			sqlExport.setPublicFlag(request.getParameter("publicFlag"));
			sqlExport.setLocked(RequestUtils.getInt(request, "locked"));
			sqlExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
			sqlExport.setCreateBy(RequestUtils.getActorId(request));
			sqlExport.setUpdateBy(RequestUtils.getActorId(request));

			this.sqlExportService.save(sqlExport);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSqlExportService(SqlExportService sqlExportService) {
		this.sqlExportService = sqlExportService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SqlExport sqlExport = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			sqlExport = sqlExportService.getSqlExport(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (sqlExport != null) {
			result = sqlExport.toJsonObject();
			result.put("id", sqlExport.getId());
			result.put("sqlExportId", sqlExport.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
