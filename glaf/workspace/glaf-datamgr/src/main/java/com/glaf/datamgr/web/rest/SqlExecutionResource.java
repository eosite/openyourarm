package com.glaf.datamgr.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.Database;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.SysKeyService;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.service.ExecutionLogService;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.util.ExecutionLogFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Controller("/rs/sys/sql/execution")
@Path("/rs/sys/sql/execution")
public class SqlExecutionResource {

	protected static final Log logger = LogFactory.getLog(SqlExecutionResource.class);

	protected static final Cache<String, Integer> cache = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterAccess(10, TimeUnit.MINUTES).build();

	protected IDatabaseService databaseService;

	protected ExecutionLogService executionLogService;

	protected SqlDefinitionService sqlDefinitionService;

	protected SysKeyService sysKeyService;

	@GET
	@POST
	@Path("/array")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] array(@Context HttpServletRequest request) throws IOException {
		logger.debug("-----------------------array---------------------------");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (!"admin".equals(loginContext.getActorId())) {
			throw new RuntimeException("Access Deny");
		}
		JSONArray result = new JSONArray();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		long databaseId = RequestUtils.getLong(request, "databaseId");
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		Database currentDB = null;

		logger.debug("params:" + params);

		if (databaseId > 0) {
			currentDB = config.getDatabase(loginContext, databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		if (currentDB == null) {
			return result.toJSONString().getBytes("UTF-8");
		}

		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null && sqlDef.getSql() != null && DBUtils.isAllowedSql(sqlDef.getSql())) {

			int start = 0;
			int limit = 0;

			int pageNo = ParamUtils.getInt(params, "page");
			limit = ParamUtils.getInt(params, "rows");
			start = (pageNo - 1) * limit;

			if (start < 0) {
				start = 0;
			}

			if (limit <= 0) {
				limit = 1000;
			}

			String currentSystemName = Environment.getCurrentSystemName();
			QueryHelper helper = new QueryHelper();
			long startTime = System.currentTimeMillis();
			boolean success = false;
			String sql = null;
			sql = sqlDef.getSql();
			boolean isHBase = false;
			try {
				if (currentDB != null) {
					Environment.setCurrentSystemName(currentDB.getName());
					if ("hbase".equals(currentDB.getType())) {
						isHBase = true;
					}
				}

				Map<String, Object> parameters = new HashMap<String, Object>();
				Map<String, Object> dataMap = SystemConfig.getContextMap();
				parameters.putAll(dataMap);
				parameters.put("loginContext", loginContext);
				parameters.put("actorId", loginContext.getActorId());
				parameters.put("currentUser", loginContext.getUser());

				int total = 0;

				if (isHBase) {
					String key = DigestUtils.sha1Hex(sql);
					if (cache.getIfPresent(key) != null) {
						total = cache.getIfPresent(key);
					} else {
						if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
							SqlExecutor sqlExecutor = DBUtils.replaceSQL(sqlDef.getCountSql(), parameters);
							total = helper.getTotal(Environment.getCurrentSystemName(), sqlExecutor);
						} else {
							total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, parameters);
						}
						cache.put(key, total);
					}
				} else {
					if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
						SqlExecutor sqlExecutor = DBUtils.replaceSQL(sqlDef.getCountSql(), parameters);
						total = helper.getTotal(Environment.getCurrentSystemName(), sqlExecutor);
					} else {
						total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, parameters);
					}
				}

				List<Map<String, Object>> rows = null;
				if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
					logger.debug("query count...");
					rows = helper.getResultList(Environment.getCurrentSystemName(), sql, parameters);
				} else {
					if (total > 0 && start < total) {
						logger.debug("->begin:" + start);
						rows = helper.getResultList(Environment.getCurrentSystemName(), sql, parameters, start, limit);
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
				success = true;
			} catch (Exception ex) {
				ex.printStackTrace();
				success = false;
			} finally {
				Environment.setCurrentSystemName(currentSystemName);
			}

			try {
				ExecutionLog log = new ExecutionLog();
				log.setContent(sql);
				log.setBusinessKey(String.valueOf(databaseId));
				log.setCreateBy(loginContext.getActorId());
				log.setStartTime(new Date(startTime));
				log.setEndTime(new Date());
				log.setRunDay(DateUtils.getNowYearMonthDay());
				log.setRunTime(System.currentTimeMillis() - startTime);
				log.setStatus(success ? 1 : -1);
				log.setType("sql_query");
				log.setJobNo("sql_query_" + loginContext.getActorId() + "_" + DateUtils.getNowYearMonthDayHHmmss());
				ExecutionLogFactory.getInstance().addLog(log);
			} catch (Exception ex) {
			}
		}

		// logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request) throws IOException {
		logger.debug("-----------------------data---------------------------");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (!"admin".equals(loginContext.getActorId())) {
			throw new RuntimeException("Access Deny");
		}
		JSONObject result = new JSONObject();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String sql = request.getParameter("sql");
		Database currentDB = null;

		logger.debug("params:" + params);

		if (!DBUtils.isAllowedSql(sql)) {
			return result.toJSONString().getBytes("UTF-8");
		}

		if (databaseId > 0) {
			currentDB = config.getDatabase(loginContext, databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		if (currentDB == null) {
			return result.toJSONString().getBytes("UTF-8");
		}

		if (StringUtils.isNotEmpty(sql)) {
			long startTime = System.currentTimeMillis();
			boolean success = false;
			// SysKey sysKey = sysKeyService.getSysKey("des_" + loginContext.getActorId());
			// byte[] data = Hex.hex2byte(sql);
			// sql = new String(DESUtils.decode(sysKey.getData(), data), "UTF-8");
			sql = RequestUtils.decodeString(sql);
			sql = StringTools.replace(sql, "\r\n", " ");
			if (DBUtils.isAllowedSql(sql)) {
				logger.debug("->sql:" + sql);

				String gridType = request.getParameter("gridType");

				int start = 0;
				int limit = 0;

				int pageNo = ParamUtils.getInt(params, "page");
				limit = ParamUtils.getInt(params, "rows");
				start = (pageNo - 1) * limit;

				if (start < 0) {
					start = 0;
				}

				if (limit <= 0) {
					limit = 1000;
				}

				String currentSystemName = Environment.getCurrentSystemName();
				QueryHelper helper = new QueryHelper();
				boolean isHBase = false;
				try {
					if (currentDB != null) {
						logger.debug("currentDB:" + currentDB.getName());
						Environment.setCurrentSystemName(currentDB.getName());
						if ("hbase".equals(currentDB.getType())) {
							isHBase = true;
						}
					}

					logger.debug("currentDB:" + Environment.getCurrentSystemName());

					Map<String, Object> parameters = new HashMap<String, Object>();
					Map<String, Object> dataMap = SystemConfig.getContextMap();
					parameters.putAll(dataMap);
					parameters.put("loginContext", loginContext);
					parameters.put("actorId", loginContext.getActorId());
					parameters.put("currentUser", loginContext.getUser());

					int total = 0;
					if (isHBase) {
						String key = DigestUtils.sha1Hex(sql);
						if (cache.getIfPresent(key) != null) {
							total = cache.getIfPresent(key);
							logger.debug("get total from cache.");
						} else {
							total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, parameters);
						}
						cache.put(key, total);
					} else {
						long startTs = System.currentTimeMillis();
						total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, parameters);
						logger.debug("-> count time:" + (System.currentTimeMillis() - startTs));
					}
					List<Map<String, Object>> rows = null;
					if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
						logger.debug("query count...");
						rows = helper.getResultList(Environment.getCurrentSystemName(), sql, parameters);
					} else {
						if (total > 0 && start < total) {
							logger.debug("->begin:" + start);
							long startTs = System.currentTimeMillis();
							rows = helper.getResultList(Environment.getCurrentSystemName(), sql, parameters, start,
									limit);
							logger.debug("-> list time:" + (System.currentTimeMillis() - startTs));
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

					success = true;
				} catch (Exception ex) {
					ex.printStackTrace();
					success = false;
				} finally {
					Environment.setCurrentSystemName(currentSystemName);
				}
			}
			try {
				ExecutionLog log = new ExecutionLog();
				log.setContent(sql);
				log.setBusinessKey(String.valueOf(databaseId));
				log.setCreateBy(loginContext.getActorId());
				log.setStartTime(new Date(startTime));
				log.setEndTime(new Date());
				log.setRunDay(DateUtils.getNowYearMonthDay());
				log.setRunTime(System.currentTimeMillis() - startTime);
				log.setStatus(success ? 1 : -1);
				log.setType("sql_query");
				log.setJobNo("sql_query_" + loginContext.getActorId() + "_" + DateUtils.getNowYearMonthDayHHmmss());
				ExecutionLogFactory.getInstance().addLog(log);
			} catch (Exception ex) {
			}
		}
		// logger.debug(result.toJSONString());
		logger.debug("数据处理完成.");
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] json(@Context HttpServletRequest request) throws IOException {
		logger.debug("-----------------------json---------------------------");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (!"admin".equals(loginContext.getActorId())) {
			throw new RuntimeException("Access Deny");
		}
		JSONObject result = new JSONObject();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		long databaseId = RequestUtils.getLong(request, "databaseId");
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		Database currentDB = null;
		String x_complex_query = request.getParameter("x_complex_query");
		Map<String, Object> paramMap = null;
		if (StringUtils.isNotEmpty(x_complex_query)) {
			x_complex_query = RequestUtils.decodeString(x_complex_query);
			paramMap = JsonUtils.decode(x_complex_query);
			if (paramMap != null && !paramMap.isEmpty()) {
				params.putAll(paramMap);
			}
		}

		logger.debug("params:" + params);

		if (!DBUtils.isAllowedSql(sqlDef.getSql())) {
			return result.toJSONString().getBytes("UTF-8");
		}

		if (databaseId > 0) {
			currentDB = config.getDatabase(loginContext, databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		String sql = sqlDef.getSql();

		if (StringUtils.isNotEmpty(sql)) {

			long startTime = System.currentTimeMillis();
			boolean success = false;

			logger.debug("sql:" + sql);

			String gridType = request.getParameter("gridType");

			int start = 0;
			int limit = 0;

			int pageNo = ParamUtils.getInt(params, "page");
			limit = ParamUtils.getInt(params, "rows");
			start = (pageNo - 1) * limit;

			if (start < 0) {
				start = 0;
			}

			if (limit <= 0) {
				limit = 1000;
			}

			String currentSystemName = Environment.getCurrentSystemName();
			QueryHelper helper = new QueryHelper();
			boolean isHBase = false;
			try {
				if (currentDB != null) {
					Environment.setCurrentSystemName(currentDB.getName());
					if ("hbase".equals(currentDB.getType())) {
						isHBase = true;
					}
				}

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.putAll(params);
				Map<String, Object> dataMap = SystemConfig.getContextMap();
				parameters.putAll(dataMap);
				parameters.put("loginContext", loginContext);
				parameters.put("actorId", loginContext.getActorId());
				parameters.put("currentUser", loginContext.getUser());

				int total = 0;

				if (isHBase) {
					String key = DigestUtils.sha1Hex(sql);
					if (cache.getIfPresent(key) != null) {
						total = cache.getIfPresent(key);
					} else {
						if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
							SqlExecutor sqlExecutor = DBUtils.replaceSQL(sqlDef.getCountSql(), parameters);
							total = helper.getTotal(Environment.getCurrentSystemName(), sqlExecutor);
						} else {
							total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, parameters);
						}
					}
					cache.put(key, total);
				} else {
					if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
						SqlExecutor sqlExecutor = DBUtils.replaceSQL(sqlDef.getCountSql(), parameters);
						total = helper.getTotal(Environment.getCurrentSystemName(), sqlExecutor);
					} else {
						total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, parameters);
					}
				}

				logger.debug("@@total:" + total);

				List<Map<String, Object>> rows = null;
				if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
					logger.debug("query count...");
					rows = helper.getResultList(Environment.getCurrentSystemName(), sql, parameters);
				} else {
					if (total > 0 && start < total) {
						logger.debug("->begin:" + start);
						rows = helper.getResultList(Environment.getCurrentSystemName(), sql, parameters, start, limit);
					}
				}

				logger.debug("start:" + start);
				logger.debug("limit:" + limit);

				if (rows != null && !rows.isEmpty()) {
					logger.debug("rows:" + rows.size());
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

				success = true;
			} catch (Exception ex) {
				ex.printStackTrace();
				success = false;
			} finally {
				Environment.setCurrentSystemName(currentSystemName);
			}
			try {
				ExecutionLog log = new ExecutionLog();
				log.setContent(sql);
				log.setBusinessKey(String.valueOf(databaseId));
				log.setCreateBy(loginContext.getActorId());
				log.setStartTime(new Date(startTime));
				log.setEndTime(new Date());
				log.setRunDay(DateUtils.getNowYearMonthDay());
				log.setRunTime(System.currentTimeMillis() - startTime);
				log.setStatus(success ? 1 : -1);
				log.setType("sql_query");
				log.setJobNo("sql_query_" + loginContext.getActorId() + "_" + DateUtils.getNowYearMonthDayHHmmss());
				ExecutionLogFactory.getInstance().addLog(log);
			} catch (Exception ex) {
			}
		}
		// logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setExecutionLogService(ExecutionLogService executionLogService) {
		this.executionLogService = executionLogService;
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@javax.annotation.Resource
	public void setSysKeyService(SysKeyService sysKeyService) {
		this.sysKeyService = sysKeyService;
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] treeJson(@Context HttpServletRequest request) throws IOException {
		logger.debug("-----------------------treeJson---------------------------");
		JSONArray result = new JSONArray();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		long databaseId = RequestUtils.getLong(request, "databaseId");
		long sqlDefId = RequestUtils.getLong(request, "sqlDefId");
		Database currentDB = null;

		logger.debug("params:" + params);

		if (databaseId > 0) {
			currentDB = config.getDatabase(loginContext, databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null && sqlDef.getSql() != null && DBUtils.isAllowedSql(sqlDef.getSql())) {

			int start = 0;
			int limit = 0;

			int pageNo = ParamUtils.getInt(params, "page");
			limit = ParamUtils.getInt(params, "rows");
			start = (pageNo - 1) * limit;

			if (start < 0) {
				start = 0;
			}

			if (limit <= 0) {
				limit = 1000;
			}

			String currentSystemName = Environment.getCurrentSystemName();
			QueryHelper helper = new QueryHelper();
			String sql = sqlDef.getSql();

			try {
				if (currentDB != null) {
					Environment.setCurrentSystemName(currentDB.getName());
				}

				Map<String, Object> parameters = new HashMap<String, Object>();
				Map<String, Object> dataMap = SystemConfig.getContextMap();
				parameters.putAll(dataMap);
				parameters.put("loginContext", loginContext);
				parameters.put("actorId", loginContext.getActorId());
				parameters.put("currentUser", loginContext.getUser());

				int total = 0;
				if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
					SqlExecutor sqlExecutor = DBUtils.replaceSQL(sqlDef.getCountSql(), parameters);
					total = helper.getTotal(Environment.getCurrentSystemName(), sqlExecutor);
				} else {
					total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, parameters);
				}

				List<Map<String, Object>> rows = null;
				if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
					logger.debug("query count...");
					rows = helper.getResultList(Environment.getCurrentSystemName(), sql, parameters);
				} else {
					if (total > 0 && start < total) {
						logger.debug("->begin:" + start);
						rows = helper.getResultList(Environment.getCurrentSystemName(), sql, parameters, start, limit);
					}
				}

				logger.debug("start:" + start);
				logger.debug("total:" + total);

				if (rows != null && !rows.isEmpty()) {
					List<TreeModel> treeModels = new ArrayList<TreeModel>();
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

						TreeModel tree = new BaseTree();
						tree.setId(ParamUtils.getLong(rowMap, "id"));
						tree.setParentId(ParamUtils.getLong(rowMap, "parentId"));
						tree.setTreeId(ParamUtils.getString(rowMap, "treeId"));
						tree.setName(ParamUtils.getString(rowMap, "text"));
						tree.setDataMap(rowMap);
						treeModels.add(tree);
					}
					TreeHelper treehelper = new TreeHelper();
					result = treehelper.getTreeJSONArray(treeModels);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				Environment.setCurrentSystemName(currentSystemName);
			}
		}

		// logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}
}
