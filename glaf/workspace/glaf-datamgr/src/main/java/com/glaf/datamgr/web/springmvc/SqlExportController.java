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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.core.util.UUID32;
import com.glaf.core.util.ZipUtils;

import com.glaf.datamgr.bean.SqlExportToJson;
import com.glaf.datamgr.bean.SqlExportToSqlite;
import com.glaf.datamgr.bean.SqlExportToStatement;
import com.glaf.datamgr.bean.SqlExportToText;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.SqlExport;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.query.SqlExportQuery;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlExportService;
 

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/sql/export")
@RequestMapping("/datamgr/sql/export")
public class SqlExportController {
	protected static final Log logger = LogFactory.getLog(SqlExportController.class);

	protected IDatabaseService databaseService;

	protected SqlDefinitionService sqlDefinitionService;

	protected SqlExportService sqlExportService;

	public SqlExportController() {

	}

	@RequestMapping("/choose")
	public ModelAndView choose(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		List<Long> ids = new ArrayList<Long>();
		SqlExport sqlExport = sqlExportService.getSqlExport(RequestUtils.getLong(request, "id"));
		if (sqlExport != null) {
			if (StringUtils.isNotEmpty(sqlExport.getSqlDefIds())) {
				ids = StringTools.splitToLong(sqlExport.getSqlDefIds());
				request.setAttribute("selectedSqlDefIds", ids);
			}
			request.setAttribute("sqlExport", sqlExport);
		}

		SqlDefinitionQuery query = new SqlDefinitionQuery();
		query.locked(0);
		List<SqlDefinition> list = sqlDefinitionService.list(query);
		if (list != null && !list.isEmpty()) {
			StringBuffer sqlDefIds = new StringBuffer();
			StringBuffer sqlDefNames = new StringBuffer();
			for (SqlDefinition sqlDef : list) {
				if (ids.contains(sqlDef.getId())) {
					sqlDefIds.append(sqlDef.getId()).append(",");
					sqlDefNames.append(sqlDef.getTitle()).append(",");
				}
			}

			request.setAttribute("sqlDefs", list);
			request.setAttribute("sqlDefIds", sqlDefIds.toString());
			request.setAttribute("sqlDefNames", sqlDefNames.toString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sqlExport.choose");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/export/choose", modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SqlExport sqlExport = sqlExportService.getSqlExport(Long.valueOf(x));
					if (sqlExport != null && (StringUtils.equals(sqlExport.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						sqlExport.setDeleteFlag(1);
						sqlExportService.save(sqlExport);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SqlExport sqlExport = sqlExportService.getSqlExport(Long.valueOf(id));
			if (sqlExport != null && (StringUtils.equals(sqlExport.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				sqlExport.setDeleteFlag(1);
				sqlExportService.save(sqlExport);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		SqlExport sqlExport = sqlExportService.getSqlExport(RequestUtils.getLong(request, "id"));
		if (sqlExport != null) {
			SqlDefinitionQuery query = new SqlDefinitionQuery();
			query.locked(0);
			if (StringUtils.isNotEmpty(sqlExport.getSqlDefIds())) {
				List<Long> ids = StringTools.splitToLong(sqlExport.getSqlDefIds());
				List<SqlDefinition> list = sqlDefinitionService.list(query);
				if (list != null && !list.isEmpty()) {
					StringBuffer sqlDefIds = new StringBuffer();
					StringBuffer sqlDefNames = new StringBuffer();
					for (SqlDefinition sqlDef : list) {
						if (ids.contains(sqlDef.getId())) {
							sqlDefIds.append(sqlDef.getId()).append(",");
							sqlDefNames.append(sqlDef.getTitle()).append(",");
						}
					}
					sqlDefNames.delete(sqlDefNames.length() - 1, sqlDefNames.length());
					request.setAttribute("sqlDefIds", sqlDefIds.toString());
					request.setAttribute("sqlDefNames", sqlDefNames.toString());
				}
			}
			request.setAttribute("sqlExport", sqlExport);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);
		request.setAttribute("databases", databases);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sqlExport.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/export/edit", modelMap);
	}

	@RequestMapping("/exportAllJson")
	@ResponseBody
	public void exportAllJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlExportId = RequestUtils.getLong(request, "sqlExportId");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			paramMap.put("lastExportTime", sqlExport.getLastExportTime());
			paramMap.put("currDate", new java.util.Date());
			paramMap.put("calendar", java.util.Calendar.getInstance());
			paramMap.put("time", java.util.Calendar.getInstance().getTime());
			paramMap.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			String zipFilePath = SystemProperties.getConfigRootPath() + "/temp/" + UUID32.getUUID() + ".zip";
			String exportDB = sqlExport.getExportDBName();
			InputStream inputStream = null;
			try {
				SqlExportToJson bean = new SqlExportToJson();
				List<File> files = bean.exportAll(sqlExportId);
				if (files != null && !files.isEmpty()) {
					File[] filelist = new File[files.size()];
					int i = 0;
					for (File file : files) {
						filelist[i++] = file;
					}
					ZipUtils.compressFile(filelist, zipFilePath);
					inputStream = new FileInputStream(new File(zipFilePath));
					ResponseUtils.download(request, response, inputStream, exportDB + ".zip");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					Thread.sleep(2000);
				} catch (Exception ex) {
				}
				IOUtils.closeStream(inputStream);
				FileUtils.deleteFile(zipFilePath);
			}
		}
	}

	@RequestMapping("/exportAllSql")
	@ResponseBody
	public void exportAllSql(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlExportId = RequestUtils.getLong(request, "sqlExportId");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			paramMap.put("lastExportTime", sqlExport.getLastExportTime());
			paramMap.put("currDate", new java.util.Date());
			paramMap.put("calendar", java.util.Calendar.getInstance());
			paramMap.put("time", java.util.Calendar.getInstance().getTime());
			paramMap.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			String zipFilePath = SystemProperties.getConfigRootPath() + "/temp/" + UUID32.getUUID() + ".zip";
			String sqlDB = sqlExport.getExportDBName();
			InputStream inputStream = null;
			try {
				SqlExportToStatement bean = new SqlExportToStatement();
				List<File> files = bean.exportAll(sqlExportId);
				if (files != null && !files.isEmpty()) {
					File[] filelist = new File[files.size()];
					int i = 0;
					for (File file : files) {
						filelist[i++] = file;
					}
					ZipUtils.compressFile(filelist, zipFilePath);
					inputStream = new FileInputStream(new File(zipFilePath));
					ResponseUtils.download(request, response, inputStream, sqlDB + ".zip");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					Thread.sleep(2000);
				} catch (Exception ex) {
				}
				IOUtils.closeStream(inputStream);
				FileUtils.deleteFile(zipFilePath);
			}
		}
	}

	@RequestMapping("/exportAllSqlite")
	@ResponseBody
	public void exportAllSqlite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlExportId = RequestUtils.getLong(request, "sqlExportId");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			paramMap.put("lastExportTime", sqlExport.getLastExportTime());
			paramMap.put("currDate", new java.util.Date());
			paramMap.put("calendar", java.util.Calendar.getInstance());
			paramMap.put("time", java.util.Calendar.getInstance().getTime());
			paramMap.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			try {

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@RequestMapping("/exportSql")
	@ResponseBody
	public void exportSql(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlExportId = RequestUtils.getLong(request, "sqlExportId");
		String databaseIds = request.getParameter("databaseIds");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			paramMap.put("lastExportTime", sqlExport.getLastExportTime());
			paramMap.put("currDate", new java.util.Date());
			paramMap.put("calendar", java.util.Calendar.getInstance());
			paramMap.put("time", java.util.Calendar.getInstance().getTime());
			paramMap.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			String zipFilePath = SystemProperties.getConfigRootPath() + "/temp/" + UUID32.getUUID() + ".zip";
			String sqlDB = sqlExport.getExportDBName();
			InputStream inputStream = null;
			try {
				if (databaseIds != null && databaseIds.trim().length() > 0) {
					Collection<Long> ids = StringTools.splitToLong(databaseIds);
					SqlExportToStatement bean = new SqlExportToStatement();
					List<File> files = bean.exportAll(sqlExportId, ids);
					if (files != null && !files.isEmpty()) {
						File[] filelist = new File[files.size()];
						int i = 0;
						for (File file : files) {
							filelist[i++] = file;
						}
						ZipUtils.compressFile(filelist, zipFilePath);
						inputStream = new FileInputStream(new File(zipFilePath));
						ResponseUtils.download(request, response, inputStream, sqlDB + ".zip");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					Thread.sleep(2000);
				} catch (Exception ex) {
				}
				IOUtils.closeStream(inputStream);
				FileUtils.deleteFile(zipFilePath);
			}
		}
	}

	@RequestMapping("/exportSqlite")
	@ResponseBody
	public void exportSqlite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlExportId = RequestUtils.getLong(request, "sqlExportId");
		String databaseIds = request.getParameter("databaseIds");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			paramMap.put("lastExportTime", sqlExport.getLastExportTime());
			paramMap.put("currDate", new java.util.Date());
			paramMap.put("calendar", java.util.Calendar.getInstance());
			paramMap.put("time", java.util.Calendar.getInstance().getTime());
			paramMap.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			String zipFilePath = SystemProperties.getConfigRootPath() + "/temp/" + UUID32.getUUID() + ".zip";
			InputStream inputStream = null;
			try {
				if (databaseIds != null && databaseIds.trim().length() > 0) {
					Collection<Long> ids = StringTools.splitToLong(databaseIds);
					SqlExportToSqlite bean = new SqlExportToSqlite();
					List<File> files = bean.exportAll(sqlExportId, ids);
					if (files != null && !files.isEmpty()) {
						File[] filelist = new File[files.size()];
						int i = 0;
						for (File file : files) {
							filelist[i++] = file;
						}
						String sqlDB = sqlExport.getExportDBName();
						ZipUtils.compressFile(filelist, zipFilePath);
						inputStream = new FileInputStream(new File(zipFilePath));
						ResponseUtils.download(request, response, inputStream, sqlDB + ".zip");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					Thread.sleep(2000);
				} catch (Exception ex) {
				}
				IOUtils.closeStream(inputStream);
				FileUtils.deleteFile(zipFilePath);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/exportSqliteAndPackage")
	public byte[] exportSqliteAndPackage(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		long sqlExportId = RequestUtils.getLong(request, "sqlExportId");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			SqlExportToSqlite export = new SqlExportToSqlite();
			export.exportAndPackage(sqlExportId, actorId);
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/exportText")
	@ResponseBody
	public void exportText(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		long sqlExportId = RequestUtils.getLong(request, "sqlExportId");
		String databaseIds = request.getParameter("databaseIds");
		SqlExport sqlExport = sqlExportService.getSqlExport(sqlExportId);
		if (sqlExport != null) {
			paramMap.put("lastExportTime", sqlExport.getLastExportTime());
			paramMap.put("currDate", new java.util.Date());
			paramMap.put("calendar", java.util.Calendar.getInstance());
			paramMap.put("time", java.util.Calendar.getInstance().getTime());
			paramMap.put("currYearMonthDay", DateUtils.getYearMonthDay(new Date()));
			String zipFilePath = SystemProperties.getConfigRootPath() + "/temp/" + UUID32.getUUID() + ".zip";
			String dbName = sqlExport.getExportDBName();
			InputStream inputStream = null;
			try {
				if (databaseIds != null && databaseIds.trim().length() > 0) {
					Collection<Long> ids = StringTools.splitToLong(databaseIds);
					SqlExportToText bean = new SqlExportToText();
					List<File> files = bean.exportAll(sqlExportId, ids);
					if (files != null && !files.isEmpty()) {
						File[] filelist = new File[files.size()];
						int i = 0;
						for (File file : files) {
							filelist[i++] = file;
						}
						ZipUtils.compressFile(filelist, zipFilePath);
						inputStream = new FileInputStream(new File(zipFilePath));
						ResponseUtils.download(request, response, inputStream, dbName + ".zip");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					Thread.sleep(2000);
				} catch (Exception ex) {
				}
				IOUtils.closeStream(inputStream);
				FileUtils.deleteFile(zipFilePath);
			}
		}
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlExportQuery query = new SqlExportQuery();
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
					rowJSON.put("rowId", sqlExport.getId());
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

		return new ModelAndView("/datamgr/sql/export/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sqlExport.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/sql/export/query", modelMap);
	}

	 

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SqlExport sqlExport = new SqlExport();
		Tools.populate(sqlExport, params);

		sqlExport.setName(request.getParameter("name"));
		sqlExport.setTitle(request.getParameter("title"));
		sqlExport.setType(request.getParameter("type"));
		sqlExport.setServiceKey(request.getParameter("serviceKey"));
		sqlExport.setUserId(request.getParameter("userId"));
		sqlExport.setOperation(request.getParameter("operation"));
		sqlExport.setExportFlag(request.getParameter("exportFlag"));
		sqlExport.setExportDBName(request.getParameter("exportDBName"));
		sqlExport.setDatasetIds(request.getParameter("datasetIds"));
		sqlExport.setSqlDefIds(request.getParameter("sqlDefIds"));
		sqlExport.setScheduleFlag(request.getParameter("scheduleFlag"));
		sqlExport.setCreateTableFlag(request.getParameter("createTableFlag"));
		sqlExport.setDeleteFetch(request.getParameter("deleteFetch"));
		sqlExport.setPublicFlag(request.getParameter("publicFlag"));
		sqlExport.setLocked(RequestUtils.getInt(request, "locked"));
		sqlExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
		sqlExport.setCreateBy(actorId);
		sqlExport.setUpdateBy(actorId);

		sqlExportService.save(sqlExport);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SqlExport saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SqlExport sqlExport = new SqlExport();
		try {
			Tools.populate(sqlExport, model);
			sqlExport.setName(ParamUtils.getString(model, "name"));
			sqlExport.setTitle(ParamUtils.getString(model, "title"));
			sqlExport.setType(ParamUtils.getString(model, "type"));
			sqlExport.setServiceKey(ParamUtils.getString(model, "serviceKey"));
			sqlExport.setUserId(ParamUtils.getString(model, "userId"));
			sqlExport.setOperation(ParamUtils.getString(model, "operation"));
			sqlExport.setExportFlag(ParamUtils.getString(model, "exportFlag"));
			sqlExport.setExportDBName(ParamUtils.getString(model, "exportDBName"));
			sqlExport.setDatasetIds(request.getParameter("datasetIds"));
			sqlExport.setSqlDefIds(ParamUtils.getString(model, "sqlDefIds"));
			sqlExport.setScheduleFlag(ParamUtils.getString(model, "scheduleFlag"));
			sqlExport.setCreateTableFlag(request.getParameter("createTableFlag"));
			sqlExport.setDeleteFetch(ParamUtils.getString(model, "deleteFetch"));
			sqlExport.setPublicFlag(ParamUtils.getString(model, "publicFlag"));
			sqlExport.setLocked(ParamUtils.getInt(model, "locked"));
			sqlExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
			sqlExport.setCreateBy(actorId);
			sqlExport.setUpdateBy(actorId);
			this.sqlExportService.save(sqlExport);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sqlExport;
	}

	@ResponseBody
	@RequestMapping("/saveSqlExport")
	public byte[] saveSqlExport(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
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
			sqlExport.setDatasetIds(request.getParameter("datasetIds"));
			sqlExport.setSqlDefIds(request.getParameter("sqlDefIds"));
			sqlExport.setScheduleFlag(request.getParameter("scheduleFlag"));
			sqlExport.setCreateTableFlag(request.getParameter("createTableFlag"));
			sqlExport.setDeleteFetch(request.getParameter("deleteFetch"));
			sqlExport.setPublicFlag(request.getParameter("publicFlag"));
			sqlExport.setLocked(RequestUtils.getInt(request, "locked"));
			sqlExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
			sqlExport.setCreateBy(actorId);
			sqlExport.setUpdateBy(actorId);
			this.sqlExportService.save(sqlExport);

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
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@javax.annotation.Resource
	public void setSqlExportService(SqlExportService sqlExportService) {
		this.sqlExportService = sqlExportService;
	}

	@RequestMapping("/showExport")
	public ModelAndView showExport(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		SqlExport sqlExport = sqlExportService.getSqlExport(RequestUtils.getLong(request, "sqlExportId"));
		if (sqlExport != null) {
			request.setAttribute("sqlExport", sqlExport);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);
		request.setAttribute("databases", databases);
		// logger.debug("databases:" + databases);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sqlExport.showExport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/export/showExport", modelMap);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SqlExport sqlExport = sqlExportService.getSqlExport(RequestUtils.getLong(request, "id"));

		Tools.populate(sqlExport, params);

		sqlExport.setName(request.getParameter("name"));
		sqlExport.setTitle(request.getParameter("title"));
		sqlExport.setType(request.getParameter("type"));
		sqlExport.setOperation(request.getParameter("operation"));
		sqlExport.setExportFlag(request.getParameter("exportFlag"));
		sqlExport.setExportDBName(request.getParameter("exportDBName"));
		sqlExport.setDatasetIds(request.getParameter("datasetIds"));
		sqlExport.setSqlDefIds(request.getParameter("sqlDefIds"));
		sqlExport.setScheduleFlag(request.getParameter("scheduleFlag"));
		sqlExport.setCreateTableFlag(request.getParameter("createTableFlag"));
		sqlExport.setDeleteFetch(request.getParameter("deleteFetch"));
		sqlExport.setPublicFlag(request.getParameter("publicFlag"));
		sqlExport.setLocked(RequestUtils.getInt(request, "locked"));
		sqlExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
		sqlExport.setUpdateBy(user.getActorId());

		sqlExportService.save(sqlExport);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SqlExport sqlExport = sqlExportService.getSqlExport(RequestUtils.getLong(request, "id"));
		request.setAttribute("sqlExport", sqlExport);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sqlExport.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/sql/export/view");
	}

}
