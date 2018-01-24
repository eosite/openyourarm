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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.domain.Database;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.SqlResult;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.query.SqlResultQuery;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlResultService;
import com.glaf.datamgr.util.SqlResultDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller("/rs/datamgr/sql/result")
@Path("/rs/datamgr/sql/result")
public class SqlResultResource {
	protected static final Log logger = LogFactory
			.getLog(SqlResultResource.class);

	protected IDatabaseService databaseService;

	protected SqlResultService sqlResultService;

	protected SqlDefinitionService sqlDefinitionService;

	@POST
	@Path("/array")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] array(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("jobNo");
		String jobNo = request.getParameter("jobNo");
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		SqlResultQuery query = new SqlResultQuery();
		Tools.populate(query, params);

		if (sqlDefId > 0) {
			query.setSqlDefId(sqlDefId);
		}
		if (databaseId > 0) {
			query.setDatabaseId(databaseId);
		}
		if (StringUtils.isNotEmpty(jobNo)
				&& !StringUtils.equals(jobNo, "undefined")) {
			query.setJobNo(jobNo);
		}

		Map<Long, String> sqlNameMap = new HashMap<Long, String>();

		List<SqlDefinition> rows = null;
		if (loginContext.isSystemAdministrator()) {
			SqlDefinitionQuery q = new SqlDefinitionQuery();
			rows = sqlDefinitionService.list(q);
		} else {
			rows = sqlDefinitionService.getSqlDefinitions(loginContext
					.getActorId());
		}
		List<Long> sqlDefIds = new ArrayList<Long>();
		sqlDefIds.add(0L);
		if (rows != null && !rows.isEmpty()) {
			for (SqlDefinition def : rows) {
				sqlNameMap.put(def.getId(), def.getTitle());
				if (!sqlDefIds.contains(def.getId())) {
					sqlDefIds.add(def.getId());
				}
			}
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);

		Map<Long, String> nameMap = new HashMap<Long, String>();
		List<Long> databaseIds = new ArrayList<Long>();
		databaseIds.add(0L);
		if (databases != null && !databases.isEmpty()) {
			for (Database database : databases) {
				databaseIds.add(database.getId());
				nameMap.put(database.getId(), database.getTitle());
			}
		}
		query.setDatabaseIds(databaseIds);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
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

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONArray result = new JSONArray();
		int total = sqlResultService.getSqlResultCountByQueryCriteria(query);
		if (total > 0) {

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<SqlResult> list = sqlResultService
					.getSqlResultsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {

				for (SqlResult sqlResult : list) {
					JSONObject rowJSON = sqlResult.toJsonObject();
					rowJSON.put("id", sqlResult.getId());
					rowJSON.put("resultId", sqlResult.getId());
					rowJSON.put("startIndex", ++start);
					if (nameMap.get(sqlResult.getDatabaseId()) != null) {
						rowJSON.put("db_title",
								nameMap.get(sqlResult.getDatabaseId()));
					}
					if (sqlNameMap.get(sqlResult.getSqlDefId()) != null) {
						rowJSON.put("sql_title",
								sqlNameMap.get(sqlResult.getSqlDefId()));
					}
					result.add(rowJSON);
				}

			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("jobNo");
		String jobNo = request.getParameter("jobNo");
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		SqlResultQuery query = new SqlResultQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		if (sqlDefId > 0) {
			query.setSqlDefId(sqlDefId);
		}
		if (databaseId > 0) {
			query.setDatabaseId(databaseId);
		}
		if (StringUtils.isNotEmpty(jobNo)
				&& !StringUtils.equals(jobNo, "undefined")) {
			query.setJobNo(jobNo);
		}
		SqlResultDomainFactory.processDataRequest(dataRequest);

		Map<Long, String> sqlNameMap = new HashMap<Long, String>();

		List<SqlDefinition> rows = null;
		if (loginContext.isSystemAdministrator()) {
			SqlDefinitionQuery q = new SqlDefinitionQuery();
			rows = sqlDefinitionService.list(q);
		} else {
			rows = sqlDefinitionService.getSqlDefinitions(loginContext
					.getActorId());
		}
		List<Long> sqlDefIds = new ArrayList<Long>();
		sqlDefIds.add(0L);
		if (rows != null && !rows.isEmpty()) {
			for (SqlDefinition def : rows) {
				sqlNameMap.put(def.getId(), def.getTitle());
				if (!sqlDefIds.contains(def.getId())) {
					sqlDefIds.add(def.getId());
				}
			}
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);

		Map<Long, String> nameMap = new HashMap<Long, String>();
		List<Long> databaseIds = new ArrayList<Long>();
		databaseIds.add(0L);
		if (databases != null && !databases.isEmpty()) {
			for (Database database : databases) {
				databaseIds.add(database.getId());
				nameMap.put(database.getId(), database.getTitle());
			}
		}
		query.setDatabaseIds(databaseIds);

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
		int total = sqlResultService.getSqlResultCountByQueryCriteria(query);
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

			List<SqlResult> list = sqlResultService
					.getSqlResultsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlResult sqlResult : list) {
					JSONObject rowJSON = sqlResult.toJsonObject();
					rowJSON.put("id", sqlResult.getId());
					rowJSON.put("resultId", sqlResult.getId());
					rowJSON.put("startIndex", ++start);
					if (nameMap.get(sqlResult.getDatabaseId()) != null) {
						rowJSON.put("db_title",
								nameMap.get(sqlResult.getDatabaseId()));
					}
					if (sqlNameMap.get(sqlResult.getSqlDefId()) != null) {
						rowJSON.put("sql_title",
								sqlNameMap.get(sqlResult.getSqlDefId()));
					}
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
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] json(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("jobNo");
		String jobNo = request.getParameter("jobNo");
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		SqlResultQuery query = new SqlResultQuery();
		Tools.populate(query, params);

		if (sqlDefId > 0) {
			query.setSqlDefId(sqlDefId);
		}
		if (databaseId > 0) {
			query.setDatabaseId(databaseId);
		}
		if (StringUtils.isNotEmpty(jobNo)
				&& !StringUtils.equals(jobNo, "undefined")) {
			query.setJobNo(jobNo);
		}

		Map<Long, String> sqlNameMap = new HashMap<Long, String>();

		List<SqlDefinition> rows = null;
		if (loginContext.isSystemAdministrator()) {
			SqlDefinitionQuery q = new SqlDefinitionQuery();
			rows = sqlDefinitionService.list(q);
		} else {
			rows = sqlDefinitionService.getSqlDefinitions(loginContext
					.getActorId());
		}
		List<Long> sqlDefIds = new ArrayList<Long>();
		sqlDefIds.add(0L);
		if (rows != null && !rows.isEmpty()) {
			for (SqlDefinition def : rows) {
				sqlNameMap.put(def.getId(), def.getTitle());
				if (!sqlDefIds.contains(def.getId())) {
					sqlDefIds.add(def.getId());
				}
			}
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);

		Map<Long, String> nameMap = new HashMap<Long, String>();
		List<Long> databaseIds = new ArrayList<Long>();
		databaseIds.add(0L);
		if (databases != null && !databases.isEmpty()) {
			for (Database database : databases) {
				databaseIds.add(database.getId());
				nameMap.put(database.getId(), database.getTitle());
			}
		}
		query.setDatabaseIds(databaseIds);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
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

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sqlResultService.getSqlResultCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<SqlResult> list = sqlResultService
					.getSqlResultsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlResult sqlResult : list) {
					JSONObject rowJSON = sqlResult.toJsonObject();
					rowJSON.put("id", sqlResult.getId());
					rowJSON.put("resultId", sqlResult.getId());
					rowJSON.put("startIndex", ++start);
					if (nameMap.get(sqlResult.getDatabaseId()) != null) {
						rowJSON.put("db_title",
								nameMap.get(sqlResult.getDatabaseId()));
					}
					if (sqlNameMap.get(sqlResult.getSqlDefId()) != null) {
						rowJSON.put("sql_title",
								sqlNameMap.get(sqlResult.getSqlDefId()));
					}
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
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(
			SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@javax.annotation.Resource
	public void setSqlResultService(SqlResultService sqlResultService) {
		this.sqlResultService = sqlResultService;
	}

}
