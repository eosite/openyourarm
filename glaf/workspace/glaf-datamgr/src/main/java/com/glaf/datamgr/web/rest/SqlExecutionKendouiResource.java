package com.glaf.datamgr.web.rest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
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
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.Database;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.util.ExecutionLogFactory;

@Controller("/rs/sys/sql/execution/kendoui")
@Path("/rs/sys/sql/execution/kendoui")
public class SqlExecutionKendouiResource {

	protected static final Log logger = LogFactory.getLog(SqlExecutionKendouiResource.class);

	protected IDatabaseService databaseService;

	protected SqlDefinitionService sqlDefinitionService;

	@GET
	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		logger.debug("-----------------------data---------------------------");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (!"admin".equals(loginContext.getActorId())) {
			throw new RuntimeException("Access Deny");
		}
		logger.debug("dataRequest:" + dataRequest);
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

		long startTime = System.currentTimeMillis();
		boolean success = false;

		String sql = sqlDef.getSql();

		if (StringUtils.isNotEmpty(sql)) {

			logger.debug("sql:" + sql);

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

			String currentSystemName = Environment.getCurrentSystemName();
			QueryHelper helper = new QueryHelper();

			try {
				if (currentDB != null) {
					Environment.setCurrentSystemName(currentDB.getName());
				}

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.putAll(params);
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
				result.put("total", total);

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
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

}
