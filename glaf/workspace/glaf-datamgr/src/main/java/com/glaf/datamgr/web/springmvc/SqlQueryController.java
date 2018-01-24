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
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.RSAUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlResultService;
import com.glaf.template.Template;
import com.glaf.template.service.ITemplateService;
import com.glaf.template.util.TemplateUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Controller("/sql/query")
@RequestMapping("/sql/query")
public class SqlQueryController {

	protected static final Log logger = LogFactory.getLog(SqlQueryController.class);

	protected static final Cache<String, Integer> cache = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterAccess(10, TimeUnit.MINUTES).build();

	protected IDatabaseService databaseService;

	protected IQueryDefinitionService queryDefinitionService;

	protected ITemplateService templateService;

	protected SqlDefinitionService sqlDefinitionService;

	protected SqlResultService sqlResultService;

	@RequestMapping("/json")
	@ResponseBody
	public void json(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		JSONObject result = new JSONObject();
		Long sqlDefId = -1L;
		String sqlDefId_enc = request.getParameter("sqlDefId_enc");
		if (StringUtils.isNotEmpty(sqlDefId_enc)) {
			String str = RSAUtils.decryptString(sqlDefId_enc);
			if (StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)) {
				sqlDefId = Long.parseLong(str);
			}
		}
		Long databaseId = RequestUtils.getLong(request, "databaseId");
		String databaseName = request.getParameter("databaseName");
		String databaseMapping = request.getParameter("databaseMapping");
		Database database = databaseService.getDatabaseById(databaseId);
		if (StringUtils.isNotEmpty(databaseName)) {
			database = databaseService.getDatabaseByName(databaseName);
		}
		if (StringUtils.isNotEmpty(databaseMapping)) {
			database = databaseService.getDatabaseByMapping(databaseMapping);
		}

		Collection<Long> databaseIds = new HashSet<Long>();
		if (loginContext != null) {
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<Database> databases = config.getDatabases(loginContext);
			if (databases != null && !databases.isEmpty()) {
				for (Database db : databases) {
					databaseIds.add(db.getId());
				}
			}
		}

		if (databaseIds.isEmpty()) {
			throw new RuntimeException("access deny");
		}

		if (database != null && !databaseIds.contains(database.getId())) {
			throw new RuntimeException("access deny");
		}

		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(paramMap);

		if (sqlDef != null && StringUtils.equals(sqlDef.getPublicFlag(), "Y")) {
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

			QueryHelper helper = new QueryHelper();
			String currentSystemName = Environment.getCurrentSystemName();
			String sql = sqlDef.getSql();
			boolean isHBase = false;

			try {
				if (database != null) {
					Environment.setCurrentSystemName(database.getName());
					if ("hbase".equals(database.getType())) {
						isHBase = true;
					}
				}

				sql = QueryUtils.replaceDollarSQLParas(sql, params);

				int total = 0;

				if (isHBase) {
					String key = DigestUtils.sha1Hex(sql);
					if (cache.getIfPresent(key) != null) {
						total = cache.getIfPresent(key);
					} else {
						total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, params);
						cache.put(key, total);
					}
				} else {
					total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, params);
				}

				List<Map<String, Object>> rows = null;
				if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
					logger.debug("query count...");
					rows = helper.getResultList(Environment.getCurrentSystemName(), sql, params);
				} else {
					if (total > 0) {
						if (total > 20000) {
							total = 20000;
						}
						rows = helper.getResultList(Environment.getCurrentSystemName(), sql, params, 0, total);
					}
				}

				logger.debug("start:" + 0);
				logger.debug("total:" + total);

				if (rows != null && !rows.isEmpty()) {
					logger.debug("rows:" + rows.size());
					int index = 1;
					JSONArray array = new JSONArray();
					Iterator<Map<String, Object>> iterator = rows.iterator();
					while (iterator.hasNext()) {
						Map<String, Object> rowMap = (Map<String, Object>) iterator.next();
						rowMap.put("startIndex", index++);
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
				result.put("start", 0);
				result.put("startIndex", 0);
				result.put("limit", total);
				result.put("pageSize", total);
				result.put("total", total);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				Environment.setCurrentSystemName(currentSystemName);
			}
		}
		logger.debug(result.toJSONString());

		PrintWriter writer = null;
		try {
			response.setContentType("application/json");
			writer = response.getWriter();
			writer.write(result.toJSONString());
			writer.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.close(writer);
		}

	}

	@RequestMapping("/jsonArray")
	@ResponseBody
	public void jsonArray(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		JSONArray result = new JSONArray();
		Long sqlDefId = -1L;
		String sqlDefId_enc = request.getParameter("sqlDefId_enc");
		if (StringUtils.isNotEmpty(sqlDefId_enc)) {
			String str = RSAUtils.decryptString(sqlDefId_enc);
			if (StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)) {
				sqlDefId = Long.parseLong(str);
			}
		}
		Long databaseId = RequestUtils.getLong(request, "databaseId");
		String databaseName = request.getParameter("databaseName");
		String databaseMapping = request.getParameter("databaseMapping");
		Database database = databaseService.getDatabaseById(databaseId);
		if (StringUtils.isNotEmpty(databaseName)) {
			database = databaseService.getDatabaseByName(databaseName);
		}
		if (StringUtils.isNotEmpty(databaseMapping)) {
			database = databaseService.getDatabaseByMapping(databaseMapping);
		}

		Collection<Long> databaseIds = new HashSet<Long>();
		if (loginContext != null) {
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<Database> databases = config.getDatabases(loginContext);
			if (databases != null && !databases.isEmpty()) {
				for (Database db : databases) {
					databaseIds.add(db.getId());
				}
			}
		}

		if (databaseIds.isEmpty()) {
			throw new RuntimeException("access deny");
		}

		if (database != null && !databaseIds.contains(database.getId())) {
			throw new RuntimeException("access deny");
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(paramMap);
		SqlDefinition sqlDef = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (sqlDef != null && StringUtils.equals(sqlDef.getPublicFlag(), "Y")) {
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

			QueryHelper helper = new QueryHelper();
			String currentSystemName = Environment.getCurrentSystemName();
			String systemName = Environment.DEFAULT_SYSTEM_NAME;
			String sql = sqlDef.getSql();
			boolean isHBase = false;
			try {
				if (database != null) {
					systemName = database.getName();
					if ("hbase".equals(database.getType())) {
						isHBase = true;
					}
				}
				Environment.setCurrentSystemName(systemName);

				sql = QueryUtils.replaceDollarSQLParas(sql, params);

				int total = 0;

				if (isHBase) {
					String key = DigestUtils.sha1Hex(sql);
					if (cache.getIfPresent(key) != null) {
						total = cache.getIfPresent(key);
					} else {
						total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, params);
						cache.put(key, total);
					}
				} else {
					total = helper.getTotalRecords(Environment.getCurrentSystemName(), sql, params);
				}

				List<Map<String, Object>> rows = null;
				if (StringUtils.contains(sql.toLowerCase(), " count(*) ")) {
					logger.debug("query count...");
					rows = helper.getResultList(Environment.getCurrentSystemName(), sql, params);
				} else {
					if (total > 0) {
						if (total > 20000) {
							total = 20000;
						}
						rows = helper.getResultList(Environment.getCurrentSystemName(), sql, params, 0, total);
					}
				}

				logger.debug("start:" + 0);
				logger.debug("total:" + total);

				if (rows != null && !rows.isEmpty()) {
					logger.debug("rows:" + rows.size());
					int index = 1;
					Iterator<Map<String, Object>> iterator = rows.iterator();
					while (iterator.hasNext()) {
						Map<String, Object> rowMap = (Map<String, Object>) iterator.next();
						rowMap.put("startIndex", index++);
						rowMap.remove("password");
						rowMap.remove("PASSWORD");
						rowMap.remove("password_");
						rowMap.remove("PASSWORD_");
						rowMap.remove("key");
						rowMap.remove("KEY_");
						rowMap.remove("sPassword");
						result.add(JsonUtils.toJSONObject(rowMap));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				Environment.setCurrentSystemName(currentSystemName);
			}
		}
		logger.debug(result.toJSONString());
		PrintWriter writer = null;
		try {
			response.setContentType("application/json");
			writer = response.getWriter();
			writer.write(result.toJSONString());
			writer.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.close(writer);
		}
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
	public void setSqlResultService(SqlResultService sqlResultService) {
		this.sqlResultService = sqlResultService;
	}

	@javax.annotation.Resource
	public void setTemplateService(ITemplateService templateService) {
		this.templateService = templateService;
	}

	@RequestMapping
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("databaseId");
		Long sqlDefId = -1L;
		String sqlDefId_enc = request.getParameter("sqlDefId_enc");
		if (StringUtils.isNotEmpty(sqlDefId_enc)) {
			String str = RSAUtils.decryptString(sqlDefId_enc);
			if (StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)) {
				sqlDefId = Long.parseLong(str);
			}
		}

		SqlDefinition def = sqlDefinitionService.getSqlDefinition(sqlDefId);
		if (def != null && StringUtils.equals(def.getPublicFlag(), "Y")) {
			List<ColumnDefinition> columns = queryDefinitionService.getColumnDefinitions("sql_ref_" + sqlDefId);
			if (columns != null && !columns.isEmpty()) {
				Collections.sort(columns);
				for (ColumnDefinition col : columns) {
					if (StringUtils.isEmpty(col.getWidth())) {
						col.setWidth("120");
					}
				}
				request.setAttribute("columns", columns);
			}

			String databaseName = request.getParameter("databaseName");
			String databaseMapping = request.getParameter("databaseMapping");
			Database database = null;

			if (StringUtils.isNotEmpty(databaseMapping)) {
				database = databaseService.getDatabaseByMapping(databaseMapping);
			} else if (StringUtils.isNotEmpty(databaseName)) {
				database = databaseService.getDatabaseByName(databaseName);
			}

			LoginContext loginContext = RequestUtils.getLoginContext(request);
			if (loginContext != null) {
				DatabaseConnectionConfig config = new DatabaseConnectionConfig();
				List<Database> databases = config.getDatabases(loginContext);
				request.setAttribute("databases", databases);
			}

			// logger.debug("@@@@@@@@@@@@@@@database:" + database);
			if (database != null) {
				request.setAttribute("database", database);
				request.setAttribute("databaseId", database.getId());
			}

			// String actorId = RequestUtils.getActorId(request);
			// ProjectQuery query = new ProjectQuery();
			// List<Project> projects = projectService.getProjects(actorId);
			// request.setAttribute("projects", projects);
			// logger.debug("projects:" + projects);

			request.setAttribute("sqlDef", def);
			request.setAttribute("sqlDefId_enc", RSAUtils.encryptString(String.valueOf(def.getId())));
			Map<String, Object> params = RequestUtils.getParameterMap(request);
			params.put("contextPath", request.getContextPath());
			params.put("serviceUrl", RequestUtils.getServiceUrl(request));
			String templateId = request.getParameter("templateId");
			if (StringUtils.isNotEmpty(templateId)) {
				Template template = templateService.getTemplate(templateId);
				if (template != null) {
					PrintWriter writer = null;
					try {
						writer = response.getWriter();
						String templateContent = TemplateUtils.process(params, template.getContent());
						TemplateUtils.process(params, templateContent, writer);
						writer.flush();
						return null;
					} catch (IOException ex) {
						ex.printStackTrace();
					} finally {
						IOUtils.close(writer);
					}
				}
			}

			String view = request.getParameter("view");
			if (StringUtils.isNotEmpty(view)) {
				return new ModelAndView("/datamgr/query/" + view);
			}
		}

		return new ModelAndView("/datamgr/query/show");
	}
}
