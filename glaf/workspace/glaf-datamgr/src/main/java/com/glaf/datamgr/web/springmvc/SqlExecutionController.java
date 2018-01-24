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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.factory.SysLogFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.SysKeyService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;

import com.glaf.datamgr.bean.SqlCountBean;
import com.glaf.datamgr.bean.SqlExecutionBean;
import com.glaf.datamgr.bean.SqlExportDataBean;
import com.glaf.datamgr.bean.SqlExportJsonDataBean;
import com.glaf.datamgr.bean.SqlExportToSqlite;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.SqlParameter;
import com.glaf.datamgr.domain.SqlResult;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlResultService;
import com.glaf.datamgr.util.SqlExecutionThreadLocal;

/**
 * 
 * SpringMVC控制器
 *
 */
@Controller("/datamgr/sql/execution")
@RequestMapping("/datamgr/sql/execution")
public class SqlExecutionController {
	protected static final Log logger = LogFactory.getLog(SqlExecutionController.class);

	protected IDatabaseService databaseService;

	protected IQueryDefinitionService queryDefinitionService;

	protected SqlDefinitionService sqlDefinitionService;

	protected SqlResultService sqlResultService;

	protected SysKeyService sysKeyService;

	public SqlExecutionController() {

	}

	@RequestMapping("/array")
	@ResponseBody
	public byte[] array(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);

		JSONArray result = new JSONArray();
		int start = 0;
		int limit = 10;

		int pageNo = ParamUtils.getInt(paramMap, "page");
		limit = ParamUtils.getInt(paramMap, "rows");
		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);

		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(paramMap);

		if (sqlDef != null && database != null) {
			params.put("rowKey", request.getParameter("rowKey"));
			if (sqlDef.getParentId() != null) {
				String jsonParam = request.getParameter("jsonParam");
				if (StringUtils.isNotEmpty(jsonParam)) {
					jsonParam = new String(Base64.decodeBase64(jsonParam), "GBK");
					logger.debug("jsonParam:" + jsonParam);
					JSONObject json = JSON.parseObject(jsonParam);
					Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						String key = (String) entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							params.put(key, value);
						}
					}
				}
			}

			List<SqlParameter> parameters = sqlDef.getParameters();
			if (parameters != null && !parameters.isEmpty()) {
				for (SqlParameter p : parameters) {
					if (p.getName() != null && p.getType() != null && p.getMapping() != null) {
						if (StringUtils.equalsIgnoreCase(p.getType(), "String")) {
							String value = ParamUtils.getString(params, p.getName());
							if (StringUtils.endsWithIgnoreCase(p.getMapping(), "like")) {
								params.put(p.getMapping(), value + "%");
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "startswith")) {
								params.put(p.getMapping(), value + "%");
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "endswith")) {
								params.put(p.getMapping(), "%" + value);
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "contains")) {
								params.put(p.getMapping(), "%" + value + "%");
							} else {
								params.put(p.getMapping(), value);
							}
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Integer")) {
							Integer value = ParamUtils.getInt(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Long")) {
							Long value = ParamUtils.getLong(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Double")) {
							Double value = ParamUtils.getDouble(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Date")) {
							Date value = ParamUtils.getDate(params, p.getName());
							params.put(p.getMapping(), value);
						}
					}
				}
			}

			logger.debug("params:" + params);

			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			if (config.checkConfig(database)) {
				String currentSystemName = Environment.getCurrentSystemName();
				QueryHelper helper = new QueryHelper();
				String sql = sqlDef.getSql();
				try {
					Environment.setCurrentSystemName(database.getName());

					sql = QueryUtils.replaceDollarSQLParas(sql, params);

					int total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, params);

					List<Map<String, Object>> rows = null;
					if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
						logger.debug("query count...");
						rows = helper.getResultList(Environment.getCurrentSystemName(), sql, params);
					} else {
						if (total > 0 && start < total) {
							logger.debug("->begin:" + start);
							rows = helper.getResultList(Environment.getCurrentSystemName(), sql, params, start, limit);
						}
					}

					logger.debug("start:" + start);
					logger.debug("total:" + total);

					if (rows != null && !rows.isEmpty()) {
						logger.debug("rows:" + rows.size());
						int index = 1;

						Iterator<Map<String, Object>> iterator = rows.iterator();
						while (iterator.hasNext()) {
							Map<String, Object> rowMap = (Map<String, Object>) iterator.next();
							rowMap.put("startIndex", start + index++);
							rowMap.remove("password");
							rowMap.remove("PASSWORD");
							rowMap.remove("password_");
							rowMap.remove("PASSWORD_");
							rowMap.remove("key");
							rowMap.remove("KEY_");
							rowMap.remove("sPassword");
							Object parentId = rowMap.get("parentId");
							if (parentId != null && (StringUtils.equals("0", parentId.toString())
									|| StringUtils.equals("-1", parentId.toString()))) {
								rowMap.remove("parentId");
							}
							result.add(JsonUtils.toJSONObject(rowMap));
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					Environment.setCurrentSystemName(currentSystemName);
				}
			}
		}
		// logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/choose")
	public ModelAndView choose(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();

		List<Database> databases = config.getDatabases(loginContext);

		if (databases != null && !databases.isEmpty()) {
			List<SqlDefinition> sqlDefinitions = null;
			if (loginContext.isSystemAdministrator()) {
				SqlDefinitionQuery q = new SqlDefinitionQuery();
				q.setLocked(0);
				sqlDefinitions = sqlDefinitionService.list(q);
			} else {
				sqlDefinitions = sqlDefinitionService.getSqlDefinitions(loginContext.getActorId());
			}

			request.setAttribute("databases", databases);
			request.setAttribute("sqlDefinitions", sqlDefinitions);
		}

		return new ModelAndView("/datamgr/sql/execution/choose");
	}

	@RequestMapping("/datagrid")
	public ModelAndView datagrid(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();

		List<Database> databases = config.getDatabases(loginContext);

		if (databases != null && !databases.isEmpty()) {
			request.setAttribute("databases", databases);
		}

		int height = RequestUtils.getInt(request, "height", 320);
		request.setAttribute("height", height);

		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null) {
			List<ColumnDefinition> columns = queryDefinitionService.getColumnDefinitions("sql_ref_" + sqlDefId);
			if (columns != null && !columns.isEmpty()) {
				int totalLenth = 0;
				for (ColumnDefinition col : columns) {
					if (col.getLength() < 90) {
						col.setLength(90);
					}
					if (StringUtils.equalsIgnoreCase(col.getType(), "datetime")) {
						if (col.getLength() < 120) {
							col.setLength(120);
						}
					}
					totalLenth = totalLenth + col.getLength();
				}
				Collections.sort(columns);
				request.setAttribute("columns", columns);
				request.setAttribute("width", totalLenth + 90);
			}
			request.setAttribute("sqlDef", sqlDef);
		}

		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		return new ModelAndView("/datamgr/sql/execution/datagrid");
	}

	@ResponseBody
	@RequestMapping("/executeAllQuery")
	public byte[] executeAllQuery(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String jobNo = DateUtils.getNowYearMonthDayHHmmss();
		SqlExecutionThreadLocal.setJobNo(jobNo);
		SqlCountBean bean = new SqlCountBean();
		bean.executeAll(loginContext.getActorId());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("jobNo", jobNo);
		return ResponseUtils.responseJsonResult(true, dataMap);
	}

	@ResponseBody
	@RequestMapping("/executeAllQueryToResults")
	public byte[] executeAllQueryToResults(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String jobNo = DateUtils.getNowYearMonthDayHHmmss();
		SqlExecutionThreadLocal.setJobNo(jobNo);
		SqlExecutionBean bean = new SqlExecutionBean();
		bean.executeAll(loginContext.getActorId());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("jobNo", jobNo);
		return ResponseUtils.responseJsonResult(true, dataMap);
	}

	@ResponseBody
	@RequestMapping("/executeAllQueryToResultsAsync")
	public byte[] executeAllQueryToResultsAsync(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String jobNo = DateUtils.getNowYearMonthDayHHmmss();
		SqlExecutionThreadLocal.setJobNo(jobNo);
		SqlExecutionBean bean = new SqlExecutionBean();
		bean.executeAllAsync(loginContext.getActorId());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("jobNo", jobNo);
		return ResponseUtils.responseJsonResult(true, dataMap);
	}

	@ResponseBody
	@RequestMapping("/executeAllUpdate")
	public byte[] executeAllUpdate(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String jobNo = DateUtils.getNowYearMonthDayHHmmss();
		SqlExecutionThreadLocal.setJobNo(jobNo);
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> sqlDefIds = StringTools.splitToLong(rowIds);
			if (sqlDefIds != null && !sqlDefIds.isEmpty()) {
				query.setDatabaseIds(sqlDefIds);
			}
		}
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);
		if (sqlDef != null && databases != null && !databases.isEmpty()) {
			int total = 0;
			long ts = 0;
			long start = System.currentTimeMillis();
			StringBuffer buffer = new StringBuffer();
			for (Database database : databases) {
				Connection connection = null;
				PreparedStatement psmt = null;
				int count = 0;
				try {
					connection = DBConnectionFactory.getConnection(database.getName());
					ts = System.currentTimeMillis();
					QueryConnectionFactory.getInstance().register(ts, connection);
					connection.setAutoCommit(false);
					psmt = connection.prepareStatement(sqlDef.getSql());
					count = psmt.executeUpdate();
					JdbcUtils.close(psmt);
					connection.commit();
					total = total + 1;
					buffer.append("\n\r").append(database.getTitle() + "执行成功。");
				} catch (SQLException ex) {
					count = -1;
					buffer.append("\n\r").append(database.getTitle() + "执行失败。");
					logger.error("update sql:" + sqlDef.getSql());
					logger.error("execute update error", ex);
				} finally {
					if (connection != null) {
						QueryConnectionFactory.getInstance().unregister(ts, connection);
					}
					JdbcUtils.close(psmt);
					JdbcUtils.close(connection);
				}

				try {
					SqlResult bean = new SqlResult();
					bean.setCreateBy(RequestUtils.getActorId(request));
					bean.setCreateTime(new Date());
					bean.setRunDay(DateUtils.getNowYearMonthDay());
					bean.setDatabaseId(database.getId());
					bean.setSqlDefId(sqlDef.getId());
					// bean.setSql(sqlDef.getSql());
					bean.setOperation(sqlDef.getOperation());
					bean.setCount(count);
					if (StringUtils.equals("Y", sqlDef.getSaveFlag())) {
						sqlResultService.save(bean);
					} else {
						if (total >= 0) {
							sqlResultService.save(bean);
						}
					}
				} catch (Exception ex) {
				}
			}

			if (total > 0) {
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("rowKey", sqlDef.getId());
					jsonObject.put("subject", sqlDef.getTitle());
					jsonObject.put("total", total);
					SysDataLog log = new SysDataLog();
					log.setActorId(RequestUtils.getActorId(request));
					log.setCreateTime(new Date());
					log.setFlag(1);
					log.setIp(RequestUtils.getIPAddress(request));
					log.setModuleId("database");
					log.setTimeMS((int) (System.currentTimeMillis() - start));
					log.setContent(jsonObject.toJSONString());
					SysLogFactory.getInstance().addLog(log);
				} catch (Exception ex) {
				}
				if (databases.size() == total) {
					return ResponseUtils.responseJsonResult(true, "成功执行更新语句，总共更新" + total + "个数据库。");
				} else {
					return ResponseUtils.responseJsonResult(true,
							"部分更新语句执行成功，总共更新" + total + "个数据库。" + buffer.toString());
				}
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/executeQuery")
	public byte[] executeQuery(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		logger.debug(RequestUtils.getParameterMap(request));
		String dbIds = request.getParameter("databaseIds");
		String sqlIds = request.getParameter("sqlDefIds");
		List<Long> databaseIds = StringTools.splitToLong(dbIds);
		List<Long> sqlDefIds = StringTools.splitToLong(sqlIds);
		if (databaseIds != null && !databaseIds.isEmpty() && sqlDefIds != null && !sqlDefIds.isEmpty()) {
			String jobNo = DateUtils.getNowYearMonthDayHHmmss();
			SqlExecutionThreadLocal.setJobNo(jobNo);
			SqlCountBean bean = new SqlCountBean();
			bean.execute(loginContext.getActorId(), databaseIds, sqlDefIds);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("jobNo", jobNo);
			return ResponseUtils.responseJsonResult(true, dataMap);
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/executeUpdate")
	public byte[] executeUpdate(HttpServletRequest request) {
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String jobNo = DateUtils.getNowYearMonthDayHHmmss();
		SqlExecutionThreadLocal.setJobNo(jobNo);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		Database database = databaseService.getDatabaseById(databaseId);
		if (sqlDef != null && database != null) {
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			if (config.checkConfig(database)) {
				Connection connection = null;
				PreparedStatement psmt = null;
				int total = 0;
				long ts = 0;
				long start = System.currentTimeMillis();
				try {
					connection = DBConnectionFactory.getConnection(database.getName());
					ts = System.currentTimeMillis();
					QueryConnectionFactory.getInstance().register(ts, connection);
					connection.setAutoCommit(false);
					psmt = connection.prepareStatement(sqlDef.getSql());
					total = psmt.executeUpdate();
					JdbcUtils.close(psmt);
					connection.commit();
				} catch (SQLException ex) {
					total = -1;
					logger.error("update sql:" + sqlDef.getSql());
					logger.error("execute update error", ex);
				} finally {
					if (connection != null) {
						QueryConnectionFactory.getInstance().unregister(ts, connection);
					}
					JdbcUtils.close(psmt);
					JdbcUtils.close(connection);
				}

				try {
					SqlResult bean = new SqlResult();
					bean.setCreateBy(RequestUtils.getActorId(request));
					bean.setCreateTime(new Date());
					bean.setRunDay(DateUtils.getNowYearMonthDay());
					bean.setDatabaseId(database.getId());
					bean.setSqlDefId(sqlDef.getId());
					// bean.setSql(sqlDef.getSql());
					bean.setOperation(sqlDef.getOperation());
					bean.setCount(total);
					if (StringUtils.equals("Y", sqlDef.getSaveFlag())) {
						sqlResultService.save(bean);
					} else {
						if (total >= 0) {
							sqlResultService.save(bean);
						}
					}
				} catch (Exception ex) {
				}
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("rowKey", sqlDef.getId());
					jsonObject.put("subject", sqlDef.getTitle());
					jsonObject.put("total", total);
					SysDataLog log = new SysDataLog();
					log.setActorId(RequestUtils.getActorId(request));
					log.setCreateTime(new Date());
					log.setFlag(1);
					log.setIp(RequestUtils.getIPAddress(request));
					log.setModuleId("database");
					log.setTimeMS((int) (System.currentTimeMillis() - start));
					log.setContent(jsonObject.toJSONString());
					SysLogFactory.getInstance().addLog(log);
				} catch (Exception ex) {
				}
				if (total == -1) {
					return ResponseUtils.responseJsonResult(false, "执行更新语句失败。");
				}
				return ResponseUtils.responseJsonResult(true, "成功执行更新语句，总共更新" + total + "条记录。");
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/exportData")
	@ResponseBody
	public void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null && database != null) {
			SqlExportDataBean bean = new SqlExportDataBean();
			StringBuffer buffer = bean.doExport(sqlDefId, databaseId, paramMap);
			byte[] bytes = buffer.toString().getBytes("UTF-8");
			try {
				ResponseUtils.download(request, response, bytes,
						(sqlDef.getCode() != null ? sqlDef.getCode() : sqlDefId) + ".txt");
			} catch (ServletException ex) {
				ex.printStackTrace();
			}
		}
	}

	@RequestMapping("/exportJsonData")
	@ResponseBody
	public void exportJsonData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null && database != null) {
			SqlExportJsonDataBean bean = new SqlExportJsonDataBean();
			StringBuffer buffer = bean.doExport(sqlDefId, databaseId, paramMap);
			buffer.insert(0, "[");
			buffer.append("]");
			byte[] bytes = buffer.toString().getBytes("UTF-8");
			try {
				ResponseUtils.download(request, response, bytes,
						(sqlDef.getCode() != null ? sqlDef.getCode() : sqlDefId) + ".json");
			} catch (ServletException ex) {
				ex.printStackTrace();
			}
		}
	}

	@RequestMapping("/exportSqlite")
	@ResponseBody
	public void exportSqlite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null && database != null && StringUtils.isNotEmpty(sqlDef.getRowKey())
				&& StringUtils.isNotEmpty(sqlDef.getExportTableName())) {
			String sqliteDB = databaseId + "_" + sqlDefId + ".db";
			String dbpath = SystemProperties.getConfigRootPath() + "/db/" + sqliteDB;
			try {
				FileUtils.deleteFile(dbpath);
				SqlExportToSqlite sqlite = new SqlExportToSqlite();
				sqlite.doExport(sqlDefId, databaseId, paramMap, sqliteDB);
				byte[] bytes = FileUtils.getBytes(dbpath);
				ResponseUtils.download(request, response, bytes, sqliteDB);
			} catch (ServletException ex) {
				ex.printStackTrace();
			}
		}
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String gridType = request.getParameter("gridType");
		JSONObject result = new JSONObject();
		int start = 0;
		int limit = 10;

		int pageNo = ParamUtils.getInt(paramMap, "page");
		limit = ParamUtils.getInt(paramMap, "rows");
		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		Database database = databaseService.getDatabaseById(databaseId);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(paramMap);
		if (sqlDef != null && database != null) {
			params.put("rowKey", request.getParameter("rowKey"));
			if (sqlDef.getParentId() != null) {
				String jsonParam = request.getParameter("jsonParam");
				if (StringUtils.isNotEmpty(jsonParam)) {
					jsonParam = new String(Base64.decodeBase64(jsonParam), "GBK");
					logger.debug("jsonParam:" + jsonParam);
					JSONObject json = JSON.parseObject(jsonParam);
					Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						String key = (String) entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							params.put(key, value);
						}
					}
				}
			}

			List<SqlParameter> parameters = sqlDef.getParameters();
			if (parameters != null && !parameters.isEmpty()) {
				for (SqlParameter p : parameters) {
					if (p.getName() != null && p.getType() != null && p.getMapping() != null) {
						if (StringUtils.equalsIgnoreCase(p.getType(), "String")) {
							String value = ParamUtils.getString(params, p.getName());
							if (StringUtils.endsWithIgnoreCase(p.getMapping(), "like")) {
								params.put(p.getMapping(), value + "%");
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "startswith")) {
								params.put(p.getMapping(), value + "%");
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "endswith")) {
								params.put(p.getMapping(), "%" + value);
							} else if (StringUtils.endsWithIgnoreCase(p.getMapping(), "contains")) {
								params.put(p.getMapping(), "%" + value + "%");
							} else {
								params.put(p.getMapping(), value);
							}
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Integer")) {
							Integer value = ParamUtils.getInt(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Long")) {
							Long value = ParamUtils.getLong(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Double")) {
							Double value = ParamUtils.getDouble(params, p.getName());
							params.put(p.getMapping(), value);
						} else if (StringUtils.equalsIgnoreCase(p.getType(), "Date")) {
							Date value = ParamUtils.getDate(params, p.getName());
							params.put(p.getMapping(), value);
						}
					}
				}
			}

			logger.debug("params:" + params);

			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			if (config.checkConfig(database)) {
				String currentSystemName = Environment.getCurrentSystemName();
				QueryHelper helper = new QueryHelper();
				int total = 0;
				String sql = sqlDef.getSql();
				String countSql = sqlDef.getCountSql();
				try {
					sql = QueryUtils.replaceDollarSQLParas(sql, params);
					countSql = QueryUtils.replaceDollarSQLParas(countSql, params);
					if (StringUtils.isNotEmpty(countSql)) {
						logger.debug("getTotal...");
						logger.debug("count sql:" + countSql);
						SqlExecutor sqlExecutor = DBUtils.replaceSQL(countSql, params);
						total = helper.getTotal(database.getName(), sqlExecutor);
						logger.debug("->total:" + total);
					} else {
						logger.debug("getTotalRecords...");
						logger.debug("count sql:" + sql);
						total = helper.getTotalRecords(database.getName(), sql, params);
						logger.debug("->total:" + total);
					}

					Environment.setCurrentSystemName(database.getName());

					List<Map<String, Object>> rows = null;
					if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
						logger.debug("query count...");
						rows = helper.getResultList(database.getName(), sql, params);
					} else {
						if (total > 0 && start < total) {
							logger.debug("->begin:" + start);
							rows = helper.getResultList(database.getName(), sql, params, start, limit);
						}
					}
					logger.debug("start:" + start);
					logger.debug("total:" + total);
					if (rows != null && !rows.isEmpty()) {
						// logger.debug("rows:" + rows.size());
						int index = 1;
						JSONArray array = new JSONArray();
						Iterator<Map<String, Object>> iterator = rows.iterator();
						while (iterator.hasNext()) {
							Map<String, Object> rowMap = (Map<String, Object>) iterator.next();
							rowMap.put("startIndex", start + index++);
							rowMap.remove("password");
							rowMap.remove("PASSWORD");
							rowMap.remove("password_");
							rowMap.remove("PASSWORD_");
							rowMap.remove("key");
							rowMap.remove("KEY_");
							rowMap.remove("sPassword");
							array.add(JsonUtils.toJSONObject(rowMap));
						}
						result.put("rows", array);
					}

					result.put("start", start);
					result.put("startIndex", start);
					result.put("limit", limit);
					result.put("pageSize", limit);

					if (StringUtils.equalsIgnoreCase(gridType, "jqgrid")) {
						result.put("records", total);
						result.put("total", total / limit + 1);
					} else {
						result.put("total", total);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					Environment.setCurrentSystemName(currentSystemName);
				}
			}
		}
		// logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/saveCountResult")
	public byte[] saveCountResult(HttpServletRequest request) {
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String jobNo = DateUtils.getNowYearMonthDayHHmmss();
		SqlExecutionThreadLocal.setJobNo(jobNo);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		Database database = databaseService.getDatabaseById(databaseId);
		if (sqlDef != null && database != null) {
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			if (config.checkConfig(database)) {
				String sql = null;
				Connection connection = null;
				PreparedStatement psmt = null;
				ResultSet rs = null;
				int total = 0;
				long ts = 0;
				long start = System.currentTimeMillis();
				try {
					connection = DBConnectionFactory.getConnection(database.getName());
					ts = System.currentTimeMillis();
					QueryConnectionFactory.getInstance().register(ts, connection);
					if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
						sql = sqlDef.getCountSql();
						psmt = connection.prepareStatement(sqlDef.getCountSql());
						rs = psmt.executeQuery();
						if (rs.next()) {
							total = rs.getInt(1);
						}
					} else {
						sql = sqlDef.getSql();
						psmt = connection.prepareStatement(sqlDef.getSql());
						rs = psmt.executeQuery();
						while (rs.next()) {
							total = total + 1;
						}
					}
				} catch (SQLException ex) {
					total = -1;
					logger.error("count sql:" + sql);
					logger.error("execute count error", ex);
				} finally {
					if (connection != null) {
						QueryConnectionFactory.getInstance().unregister(ts, connection);
					}
					JdbcUtils.close(rs);
					JdbcUtils.close(psmt);
					JdbcUtils.close(connection);
				}

				try {
					SqlResult bean = new SqlResult();
					bean.setCreateBy(RequestUtils.getActorId(request));
					bean.setCreateTime(new Date());
					bean.setRunDay(DateUtils.getNowYearMonthDay());
					bean.setDatabaseId(database.getId());
					bean.setSqlDefId(sqlDefId);
					bean.setOperation(sqlDef.getOperation());
					bean.setCount(total);
					if (StringUtils.equals("Y", sqlDef.getSaveFlag())) {
						sqlResultService.save(bean);
					} else {
						if (total >= 0) {
							sqlResultService.save(bean);
						}
					}

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("rowKey", sqlDef.getId());
					jsonObject.put("subject", sqlDef.getTitle());
					jsonObject.put("total", total);
					SysDataLog log = new SysDataLog();
					log.setActorId(RequestUtils.getActorId(request));
					log.setCreateTime(new Date());
					log.setFlag(1);
					log.setIp(RequestUtils.getIPAddress(request));
					log.setModuleId("database");
					log.setTimeMS((int) (System.currentTimeMillis() - start));
					log.setContent(jsonObject.toJSONString());
					SysLogFactory.getInstance().addLog(log);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return ResponseUtils.responseJsonResult(true, "成功执行统计语句，总共获取" + total + "条记录。");
			}

		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveExecutionResult")
	public byte[] saveExecutionResult(HttpServletRequest request) {
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		SqlExecutionBean bean = new SqlExecutionBean();
		bean.execute(loginContext.getActorId(), sqlDefId);
		return ResponseUtils.responseJsonResult(true);
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

	@javax.annotation.Resource
	public void setSysKeyService(SysKeyService sysKeyService) {
		this.sysKeyService = sysKeyService;
	}

	@javax.annotation.Resource
	public void setSqlResultService(SqlResultService sqlResultService) {
		this.sqlResultService = sqlResultService;
	}

	@RequestMapping("/show")
	public ModelAndView show(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(sqlDefId);
		Database currentDB = null;
		if (sqlDefinition == null) {
			return null;
		}

		List<Database> databases = config.getDatabases(loginContext);
		request.setAttribute("sqlDefinition", sqlDefinition);
		request.setAttribute("databases", databases);
		if (request.getParameter("rows") == null) {
			request.setAttribute("rows", 15);
		}

		if (StringUtils.equalsIgnoreCase(sqlDefinition.getOperation(), "update")) {
			return new ModelAndView("/datamgr/sql/execution/update");
		}

		if (databaseId > 0) {
			currentDB = databaseService.getDatabaseById(databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		QueryHelper queryHelper = new QueryHelper();
		SqlDefinitionQuery q = new SqlDefinitionQuery();
		q.parentId(sqlDefinition.getId());
		List<SqlDefinition> list = sqlDefinitionService.list(q);
		if (list != null && !list.isEmpty()) {
			for (SqlDefinition def : list) {
				if (currentDB != null) {
					try {
						List<ColumnDefinition> columns = queryHelper.getColumnDefinitions(currentDB.getName(),
								def.getSql(), params);
						if (columns != null && !columns.isEmpty()) {
							if (columns.size() > 8) {
								def.setWidth(columns.size() * 150 + 90);
							}
							for (ColumnDefinition col : columns) {
								if (StringUtils.equalsIgnoreCase(sqlDefinition.getRowKey(), col.getColumnLabel())) {
									col.setPrimaryKey(true);
									break;
								}
							}
							def.setColumns(columns);
						}
					} catch (Exception ex) {
					}
				} else {
					for (Database database : databases) {
						try {
							List<ColumnDefinition> columns = queryHelper.getColumnDefinitions(database.getName(),
									def.getSql(), params);
							if (columns != null && !columns.isEmpty()) {
								if (columns.size() > 8) {
									def.setWidth(columns.size() * 150 + 90);
								}
								for (ColumnDefinition col : columns) {
									if (StringUtils.equalsIgnoreCase(sqlDefinition.getRowKey(), col.getColumnLabel())) {
										col.setPrimaryKey(true);
										break;
									}
								}
								def.setColumns(columns);
								break;
							}
						} catch (Exception ex) {
						}
					}
				}
			}
			request.setAttribute("sqlDefinitions", list);
		}
		if (currentDB != null) {
			try {
				List<ColumnDefinition> columns = queryHelper.getColumnDefinitions(currentDB.getName(),
						sqlDefinition.getSql(), params);
				if (columns != null && !columns.isEmpty()) {
					if (columns.size() > 8) {
						sqlDefinition.setWidth(columns.size() * 150 + 90);
					}
					for (ColumnDefinition col : columns) {
						if (StringUtils.equalsIgnoreCase(sqlDefinition.getRowKey(), col.getColumnLabel())) {
							col.setPrimaryKey(true);
							break;
						}
					}
					sqlDefinition.setColumns(columns);
				}
			} catch (Exception ex) {
			}
		} else {
			for (Database database : databases) {
				try {
					List<ColumnDefinition> columns = queryHelper.getColumnDefinitions(database.getName(),
							sqlDefinition.getSql(), params);
					if (columns != null && !columns.isEmpty()) {
						if (columns.size() > 8) {
							sqlDefinition.setWidth(columns.size() * 150 + 90);
						}
						for (ColumnDefinition col : columns) {
							if (StringUtils.equalsIgnoreCase(sqlDefinition.getRowKey(), col.getColumnLabel())) {
								col.setPrimaryKey(true);
								break;
							}
						}
						sqlDefinition.setColumns(columns);
						break;
					}
				} catch (Exception ex) {
				}
			}
		}

		request.setAttribute("sqlDefinition", sqlDefinition);

		return new ModelAndView("/datamgr/sql/execution/show");
	}

	@RequestMapping("/showHistory")
	public ModelAndView showHistory(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);

		request.setAttribute("sqlDefinition", sqlDef);
		request.setAttribute("databases", databases);

		return new ModelAndView("/datamgr/sql/execution/showHistory");
	}

	@RequestMapping("/showQuery")
	public ModelAndView showQuery(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String sql = request.getParameter("sql");
		Database currentDB = null;

		List<Database> databases = config.getDatabases(loginContext);

		request.setAttribute("databases", databases);
		request.setAttribute("width", 880);
		if (request.getParameter("rows") == null) {
			request.setAttribute("rows", 15);
		}

		if (databaseId > 0) {
			currentDB = databaseService.getDatabaseById(databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		if (currentDB != null && StringUtils.isNotEmpty(sql)) {
			request.setAttribute("currentDB", currentDB);
			QueryHelper queryHelper = new QueryHelper();
			try {
				List<ColumnDefinition> columns = queryHelper.getColumnDefinitions(currentDB.getName(), sql, params);
				if (columns != null && !columns.isEmpty()) {
					request.setAttribute("width", columns.size() * 180);
				}
				logger.debug(columns);
				request.setAttribute("columns", columns);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return new ModelAndView("/datamgr/sql/execution/showQuery");
	}

	@RequestMapping("/treegrid")
	public ModelAndView treegrid(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();

		List<Database> databases = config.getDatabases(loginContext);

		if (databases != null && !databases.isEmpty()) {
			request.setAttribute("databases", databases);
		}

		int height = RequestUtils.getInt(request, "height", 320);
		request.setAttribute("height", height);

		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null) {
			List<ColumnDefinition> columns = queryDefinitionService.getColumnDefinitions("sql_ref_" + sqlDefId);
			if (columns != null && !columns.isEmpty()) {
				int totalLenth = 0;
				for (ColumnDefinition col : columns) {
					if (col.getLength() < 90) {
						col.setLength(90);
					}
					if (StringUtils.equalsIgnoreCase(col.getType(), "datetime")) {
						if (col.getLength() < 120) {
							col.setLength(120);
						}
					}
					totalLenth = totalLenth + col.getLength();
				}
				Collections.sort(columns);
				request.setAttribute("columns", columns);
				request.setAttribute("width", totalLenth + 90);
			}
			request.setAttribute("sqlDef", sqlDef);
		}

		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		return new ModelAndView("/datamgr/sql/execution/treegrid");
	}

}
