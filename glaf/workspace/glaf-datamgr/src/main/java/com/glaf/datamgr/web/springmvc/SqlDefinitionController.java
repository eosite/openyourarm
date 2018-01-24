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

package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.RSAUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.service.SqlDefinitionService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/sql/definition")
@RequestMapping("/datamgr/sql/definition")
public class SqlDefinitionController {
	protected static final Log logger = LogFactory.getLog(SqlDefinitionController.class);

	protected IDatabaseService databaseService;

	protected SqlDefinitionService sqlDefinitionService;

	protected IQueryDefinitionService queryDefinitionService;

	public SqlDefinitionController() {

	}

	@RequestMapping("/columns")
	@ResponseBody
	public byte[] columns(HttpServletRequest request, ModelMap modelMap) throws IOException {
		String sqlDefId = request.getParameter("sqlDefId");
		logger.debug("sqlDefId:" + sqlDefId);
		JSONObject result = new JSONObject();
		List<ColumnDefinition> list = queryDefinitionService.getColumnDefinitions("sql_ref_" + sqlDefId);

		if (list != null && !list.isEmpty()) {
			int start = 0;
			JSONArray rowsJSON = new JSONArray();

			for (ColumnDefinition c : list) {
				JSONObject rowJSON = c.toJsonObject();
				rowJSON.put("id", c.getId());
				rowJSON.put("columnId", c.getId());
				rowJSON.put("columnDefinitionId", c.getId());
				rowJSON.put("startIndex", ++start);
				rowsJSON.add(rowJSON);
			}

			result.put("rows", rowsJSON);
			result.put("total", list.size());

		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", 0);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		if (id != null && id > 0) {
			SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(id);
			if (sqlDefinition != null && (StringUtils.equals(sqlDefinition.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				sqlDefinitionService.deleteById(id);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/drop")
	public byte[] drop(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext != null && loginContext.isSystemAdministrator()) {
			SqlDefinitionQuery query = new SqlDefinitionQuery();
			List<SqlDefinition> list = sqlDefinitionService.list(query);
			if (list != null && !list.isEmpty()) {
				Connection connection = null;
				Statement statement = null;
				try {
					connection = DBConnectionFactory.getConnection();
					connection.setAutoCommit(false);
					for (SqlDefinition sqlDef : list) {
						String tableName = "SQL_RESULT" + sqlDef.getId();
						if (StringUtils.isNotEmpty(sqlDef.getTargetTableName())
								&& StringUtils.startsWithIgnoreCase(sqlDef.getTargetTableName(), "SQL_RESULT")) {
							tableName = sqlDef.getTargetTableName();
						}
						if (DBUtils.tableExists(connection, tableName)) {
							statement = connection.createStatement();
							statement.executeUpdate(" drop table " + tableName);
							connection.commit();
							JdbcUtils.close(statement);
							logger.info("drop table:" + tableName);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new RuntimeException(ex);
				} finally {
					JdbcUtils.close(connection);
					JdbcUtils.close(statement);
				}
			}
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(RequestUtils.getLong(request, "id"));

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlDefinitionQuery query = new SqlDefinitionQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		List<SqlDefinition> list = sqlDefinitionService.list(query);
		if (list != null && !list.isEmpty() && sqlDefinition != null) {
			for (SqlDefinition m : list) {
				if (m.getId().longValue() == sqlDefinition.getId().longValue()) {
					list.remove(m);
					break;
				}
			}
		}

		request.setAttribute("submitFlag", "true");

		if (sqlDefinition != null) {
			if (!StringUtils.equals(sqlDefinition.getCreateBy(), loginContext.getActorId())) {
				request.setAttribute("submitFlag", "false");
			}
			request.setAttribute("sqlDefinition", sqlDefinition);
			request.setAttribute("sqlDefId_enc", RSAUtils.encryptString(String.valueOf(sqlDefinition.getId())));
		}

		if (loginContext.isSystemAdministrator()) {
			request.setAttribute("submitFlag", "true");
		}

		request.setAttribute("parentList", list);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sqlDefinition.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/definition/edit", modelMap);
	}

	@RequestMapping("/editColumns")
	public ModelAndView editColumns(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(RequestUtils.getLong(request, "sqlDefId"));
		if (sqlDefinition != null && DBUtils.isLegalQuerySql(sqlDefinition.getSql())) {
			List<ColumnDefinition> list = queryDefinitionService
					.getColumnDefinitions("sql_ref_" + sqlDefinition.getId());
			if (list == null || list.isEmpty()) {
				QueryHelper queryHelper = new QueryHelper();
				DatabaseQuery query = new DatabaseQuery();
				query.active("1");

				List<Database> databases = null;
				if (loginContext.isSystemAdministrator()) {
					databases = databaseService.list(query);
				} else {
					databases = databaseService.getDatabases(loginContext.getActorId());
				}

				DatabaseConnectionConfig config = new DatabaseConnectionConfig();

				for (Database database : databases) {
					if ("1".equals(database.getActive())) {
						if (config.checkConfig(database)) {
							try {
								List<ColumnDefinition> columns = queryHelper.getColumnDefinitions(database.getName(),
										sqlDefinition.getSql(), params);
								sqlDefinitionService.saveColumns(sqlDefinition.getId(), columns);
								break;
							} catch (Exception ex) {
							}
						}
					}
				}
			}
			request.setAttribute("sqlDefinition", sqlDefinition);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/definition/columns", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlDefinitionQuery query = new SqlDefinitionQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sqlDefinitionService.getSqlDefinitionCountByQueryCriteria(query);
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

			List<SqlDefinition> list = sqlDefinitionService.getSqlDefinitionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlDefinition sqlDefinition : list) {
					JSONObject rowJSON = sqlDefinition.toJsonObject();
					rowJSON.put("id", sqlDefinition.getId());
					rowJSON.put("rowId", sqlDefinition.getId());
					rowJSON.put("sqlDefinitionId", sqlDefinition.getId());
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

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/definition/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sqlDefinition.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/sql/definition/query", modelMap);
	}

	@RequestMapping("/reloadColumns")
	public ModelAndView reloadColumns(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(RequestUtils.getLong(request, "sqlDefId"));
		if (sqlDefinition != null && DBUtils.isLegalQuerySql(sqlDefinition.getSql())) {

			QueryHelper queryHelper = new QueryHelper();
			DatabaseQuery query = new DatabaseQuery();
			query.active("1");

			List<Database> databases = null;
			if (loginContext.isSystemAdministrator()) {
				databases = databaseService.list(query);
			} else {
				databases = databaseService.getDatabases(loginContext.getActorId());
			}

			DatabaseConnectionConfig config = new DatabaseConnectionConfig();

			for (Database database : databases) {
				if ("1".equals(database.getActive())) {
					if (config.checkConfig(database)) {
						try {
							List<ColumnDefinition> columns = queryHelper.getColumnDefinitions(database.getName(),
									sqlDefinition.getSql(), params);
							sqlDefinitionService.saveColumns(sqlDefinition.getId(), columns);
							break;
						} catch (Exception ex) {
						}
					}
				}
			}

			request.setAttribute("sqlDefinition", sqlDefinition);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/definition/columns", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SqlDefinition sqlDefinition = new SqlDefinition();
		Tools.populate(sqlDefinition, params);

		sqlDefinition.setParentId(RequestUtils.getLong(request, "parentId"));
		sqlDefinition.setName(request.getParameter("name"));
		sqlDefinition.setCode(request.getParameter("code"));
		sqlDefinition.setTitle(request.getParameter("title"));
		sqlDefinition.setSql(request.getParameter("sql"));
		sqlDefinition.setCountSql(request.getParameter("countSql"));
		sqlDefinition.setRowKey(request.getParameter("rowKey"));
		sqlDefinition.setFetchFlag(request.getParameter("fetchFlag"));
		sqlDefinition.setDeleteFetch(request.getParameter("deleteFetch"));
		sqlDefinition.setScheduleFlag(request.getParameter("scheduleFlag"));
		sqlDefinition.setShareFlag(request.getParameter("shareFlag"));
		sqlDefinition.setSaveFlag(request.getParameter("saveFlag"));
		sqlDefinition.setPublicFlag(request.getParameter("publicFlag"));
		sqlDefinition.setExportFlag(request.getParameter("exportFlag"));
		sqlDefinition.setExportTableName(request.getParameter("exportTableName"));
		sqlDefinition.setTargetTableName(request.getParameter("targetTableName"));
		sqlDefinition.setAggregationFlag(request.getParameter("aggregationFlag"));
		sqlDefinition.setCreateBy(actorId);
		sqlDefinition.setUpdateBy(actorId);

		if (!DBUtils.isLegalQuerySql(sqlDefinition.getSql())) {
			throw new RuntimeException(" SQL statement illegal ");
		}

		if (!DBUtils.isAllowedSql(sqlDefinition.getSql())) {
			throw new RuntimeException(" SQL statement illegal ");
		}

		sqlDefinition.setOperation("select");
		sqlDefinitionService.save(sqlDefinition);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAsSqlDefinition")
	public byte[] saveAsSqlDefinition(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlDefinition sqlDefinition = new SqlDefinition();
		try {
			Tools.populate(sqlDefinition, params);
			sqlDefinition.setParentId(RequestUtils.getLong(request, "parentId"));
			sqlDefinition.setName(request.getParameter("name"));
			sqlDefinition.setCode(request.getParameter("code"));
			sqlDefinition.setTitle(request.getParameter("title"));
			sqlDefinition.setSql(request.getParameter("sql"));
			sqlDefinition.setCountSql(request.getParameter("countSql"));
			sqlDefinition.setRowKey(request.getParameter("rowKey"));
			sqlDefinition.setFetchFlag(request.getParameter("fetchFlag"));
			sqlDefinition.setDeleteFetch(request.getParameter("deleteFetch"));
			sqlDefinition.setScheduleFlag(request.getParameter("scheduleFlag"));
			sqlDefinition.setShareFlag(request.getParameter("shareFlag"));
			sqlDefinition.setSaveFlag(request.getParameter("saveFlag"));
			sqlDefinition.setPublicFlag(request.getParameter("publicFlag"));
			sqlDefinition.setExportFlag(request.getParameter("exportFlag"));
			sqlDefinition.setExportTableName(request.getParameter("exportTableName"));
			sqlDefinition.setTargetTableName(request.getParameter("targetTableName"));
			sqlDefinition.setAggregationFlag(request.getParameter("aggregationFlag"));
			sqlDefinition.setCreateBy(actorId);
			sqlDefinition.setUpdateBy(actorId);
			sqlDefinition.setId(null);

			if (!DBUtils.isLegalQuerySql(sqlDefinition.getSql())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			if (!DBUtils.isAllowedSql(sqlDefinition.getSql())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			sqlDefinition.setOperation("select");
			this.sqlDefinitionService.save(sqlDefinition);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveColumn")
	public byte[] saveColumn(HttpServletRequest request) {
		String sqlDefId = request.getParameter("sqlDefId");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ColumnDefinition columnDefinition = new ColumnDefinition();
		try {
			Tools.populate(columnDefinition, params);
			sqlDefinitionService.saveColumn(Long.parseLong(sqlDefId), columnDefinition);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SqlDefinition saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		SqlDefinition sqlDefinition = new SqlDefinition();
		try {
			Tools.populate(sqlDefinition, model);
			sqlDefinition.setParentId(ParamUtils.getLong(model, "parentId"));
			sqlDefinition.setName(ParamUtils.getString(model, "name"));
			sqlDefinition.setCode(ParamUtils.getString(model, "code"));
			sqlDefinition.setTitle(ParamUtils.getString(model, "title"));
			sqlDefinition.setSql(ParamUtils.getString(model, "sql"));
			sqlDefinition.setCountSql(ParamUtils.getString(model, "countSql"));
			sqlDefinition.setRowKey(ParamUtils.getString(model, "rowKey"));
			sqlDefinition.setFetchFlag(request.getParameter("fetchFlag"));
			sqlDefinition.setDeleteFetch(request.getParameter("deleteFetch"));
			sqlDefinition.setScheduleFlag(ParamUtils.getString(model, "scheduleFlag"));
			sqlDefinition.setShareFlag(ParamUtils.getString(model, "shareFlag"));
			sqlDefinition.setSaveFlag(request.getParameter("saveFlag"));
			sqlDefinition.setPublicFlag(request.getParameter("publicFlag"));
			sqlDefinition.setExportFlag(request.getParameter("exportFlag"));
			sqlDefinition.setExportTableName(request.getParameter("exportTableName"));
			sqlDefinition.setTargetTableName(request.getParameter("targetTableName"));
			sqlDefinition.setAggregationFlag(request.getParameter("aggregationFlag"));
			sqlDefinition.setCreateBy(actorId);
			sqlDefinition.setUpdateBy(actorId);

			if (!DBUtils.isLegalQuerySql(sqlDefinition.getSql())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			if (!DBUtils.isAllowedSql(sqlDefinition.getSql())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			sqlDefinition.setOperation("select");
			this.sqlDefinitionService.save(sqlDefinition);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sqlDefinition;
	}

	@ResponseBody
	@RequestMapping("/saveSqlDefinition")
	public byte[] saveSqlDefinition(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlDefinition sqlDefinition = new SqlDefinition();
		try {
			Tools.populate(sqlDefinition, params);
			sqlDefinition.setParentId(RequestUtils.getLong(request, "parentId"));
			sqlDefinition.setName(request.getParameter("name"));
			sqlDefinition.setCode(request.getParameter("code"));
			sqlDefinition.setTitle(request.getParameter("title"));
			sqlDefinition.setSql(request.getParameter("sql"));
			sqlDefinition.setCountSql(request.getParameter("countSql"));
			sqlDefinition.setRowKey(request.getParameter("rowKey"));
			sqlDefinition.setFetchFlag(request.getParameter("fetchFlag"));
			sqlDefinition.setDeleteFetch(request.getParameter("deleteFetch"));
			sqlDefinition.setScheduleFlag(request.getParameter("scheduleFlag"));
			sqlDefinition.setShareFlag(request.getParameter("shareFlag"));
			sqlDefinition.setSaveFlag(request.getParameter("saveFlag"));
			sqlDefinition.setPublicFlag(request.getParameter("publicFlag"));
			sqlDefinition.setExportFlag(request.getParameter("exportFlag"));
			sqlDefinition.setExportTableName(request.getParameter("exportTableName"));
			sqlDefinition.setTargetTableName(request.getParameter("targetTableName"));
			sqlDefinition.setAggregationFlag(request.getParameter("aggregationFlag"));
			sqlDefinition.setCreateBy(actorId);
			sqlDefinition.setUpdateBy(actorId);

			if (!DBUtils.isLegalQuerySql(sqlDefinition.getSql())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			if (!DBUtils.isAllowedSql(sqlDefinition.getSql())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			sqlDefinition.setOperation("select");
			this.sqlDefinitionService.save(sqlDefinition);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@RequestMapping("/showList")
	public ModelAndView showList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/definition/showList", modelMap);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(RequestUtils.getLong(request, "id"));

		Tools.populate(sqlDefinition, params);

		sqlDefinition.setParentId(RequestUtils.getLong(request, "parentId"));
		sqlDefinition.setName(request.getParameter("name"));
		sqlDefinition.setCode(request.getParameter("code"));
		sqlDefinition.setTitle(request.getParameter("title"));
		sqlDefinition.setSql(request.getParameter("sql"));
		sqlDefinition.setCountSql(request.getParameter("countSql"));
		sqlDefinition.setRowKey(request.getParameter("rowKey"));
		sqlDefinition.setFetchFlag(request.getParameter("fetchFlag"));
		sqlDefinition.setDeleteFetch(request.getParameter("deleteFetch"));
		sqlDefinition.setScheduleFlag(request.getParameter("scheduleFlag"));
		sqlDefinition.setPublicFlag(request.getParameter("publicFlag"));
		sqlDefinition.setExportFlag(request.getParameter("exportFlag"));
		sqlDefinition.setExportTableName(request.getParameter("exportTableName"));
		sqlDefinition.setTargetTableName(request.getParameter("targetTableName"));
		sqlDefinition.setAggregationFlag(request.getParameter("aggregationFlag"));
		sqlDefinition.setUpdateBy(user.getActorId());

		if (!DBUtils.isLegalQuerySql(sqlDefinition.getSql())) {
			throw new RuntimeException(" SQL statement illegal ");
		}

		if (!DBUtils.isAllowedSql(sqlDefinition.getSql())) {
			throw new RuntimeException(" SQL statement illegal ");
		}

		sqlDefinition.setOperation("select");
		sqlDefinitionService.save(sqlDefinition);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(RequestUtils.getLong(request, "id"));
		request.setAttribute("sqlDefinition", sqlDefinition);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sqlDefinition.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/sql/definition/view");
	}

}
