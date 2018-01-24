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
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.TableModel;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.*;
import com.glaf.datamgr.bean.DataExportAllSqliteBean;
import com.glaf.datamgr.bean.DataExportSqliteBean;
import com.glaf.datamgr.bean.DataExportUpdateAllBean;
import com.glaf.datamgr.bean.DataExportUpdateBean;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;
import com.glaf.datamgr.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/data/export")
@RequestMapping("/datamgr/data/export")
public class DataExportController {
	protected static final Log logger = LogFactory.getLog(DataExportController.class);

	protected IDatabaseService databaseService;

	protected DataExportService dataExportService;

	protected SqlDefinitionService sqlDefinitionService;

	public DataExportController() {

	}

	@RequestMapping("/choose")
	public ModelAndView choose(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		List<String> tables = new ArrayList<String>();
		DataExport dataExport = dataExportService.getDataExport(RequestUtils.getLong(request, "id"));
		if (dataExport != null) {
			if (StringUtils.isNotEmpty(dataExport.getTables())) {
				tables = StringTools.split(dataExport.getTables());
				request.setAttribute("selectedTables", tables);
			}
			request.setAttribute("dataExport", dataExport);
		}

		TableModelReader reader = new TableModelReader();
		List<TableModel> tableModels = reader.readModels();
		request.setAttribute("tableModels", tableModels);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataExport.choose");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/data/export/choose", modelMap);
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
					DataExport dataExport = dataExportService.getDataExport(Long.valueOf(x));
					if (dataExport != null && (StringUtils.equals(dataExport.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						dataExport.setDeleteFlag(1);
						dataExportService.save(dataExport);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataExport dataExport = dataExportService.getDataExport(Long.valueOf(id));
			if (dataExport != null && (StringUtils.equals(dataExport.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				dataExport.setDeleteFlag(1);
				dataExportService.save(dataExport);
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
		DataExport dataExport = dataExportService.getDataExport(RequestUtils.getLong(request, "id"));
		if (dataExport != null) {
			SqlDefinitionQuery query = new SqlDefinitionQuery();
			query.locked(0);
			if (StringUtils.isNotEmpty(dataExport.getTables())) {
				List<String> tables = StringTools.split(dataExport.getTables());
				if (tables.size() > 0) {
					TableModelReader reader = new TableModelReader();
					List<TableModel> list = reader.readModels();
					if (list != null && !list.isEmpty()) {
						StringBuffer tableIds = new StringBuffer();
						StringBuffer tableNames = new StringBuffer();
						for (TableModel tableModel : list) {
							if (tables.contains(tableModel.getTableName())) {
								tableIds.append(tableModel.getTableName()).append(",");
								tableNames.append(tableModel.getTitle()).append(",");
							}
						}
						tableNames.delete(tableNames.length() - 1, tableNames.length());
						request.setAttribute("tableIds", tableIds.toString());
						request.setAttribute("tableNames", tableNames.toString());
					}
				}
			}
			request.setAttribute("dataExport", dataExport);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);
		request.setAttribute("databases", databases);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataExport.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/data/export/edit", modelMap);
	}

	@RequestMapping("/exportSqlite")
	@ResponseBody
	public void exportSqlite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long dataExportId = RequestUtils.getLong(request, "dataExportId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		try {
			DataExport dataExport = dataExportService.getDataExport(dataExportId);
			if (dataExport != null && StringUtils.isNotEmpty(dataExport.getServiceKey())) {
				DataExportSqliteBean bean = new DataExportSqliteBean();
				DataExportAllSqliteBean bean2 = new DataExportAllSqliteBean();
				TableModelReader reader = new TableModelReader();
				List<TableModel> tables = reader.readModels();
				List<String> list = StringTools.split(dataExport.getTables());
				logger.debug("table list:" + list);
				if (tables != null && !tables.isEmpty() && list != null && !list.isEmpty()) {
					String dbpath = SystemProperties.getConfigRootPath() + "/db/" + dataExport.getServiceKey();
					FileUtils.deleteFile(dbpath);
					DataExportUpdateBean updateBean = new DataExportUpdateBean();
					DataExportUpdateAllBean updateAllBean = new DataExportUpdateAllBean();
					for (TableModel tableModel : tables) {
						if (list.contains(tableModel.getTableName())
								|| list.contains(tableModel.getTableName().toUpperCase())) {
							logger.debug("prepare export :" + tableModel.getTableName());
							if (tableModel.getIdColumn() != null) {
								if (StringUtils.equals(tableModel.getIdColumn().getJavaType(), "String")) {
									updateAllBean.updateExportFlag(databaseId, tableModel);
									bean2.exportSqlite(databaseId, dataExport.getServiceKey(), tableModel, false);
								} else {
									updateBean.updateExportFlag(databaseId, tableModel);
									bean.exportSqlite(databaseId, dataExport.getServiceKey(), tableModel, false);
								}
							}
							updateBean.updateExportFlag(databaseId, tableModel);
							bean.exportSqlite(databaseId, dataExport.getServiceKey(), tableModel, false);
						}
					}
					File file = new File(dbpath);
					if (file.exists() && file.isFile()) {
						byte[] data = FileUtils.getBytes(dbpath);
						data = GZIPUtils.zip(data);
						ResponseUtils.download(request, response, data, dataExport.getExportName() + ".gz");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping("/resetExportFlag")
	@ResponseBody
	public byte[] resetExportFlag(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean result = true;
		long dataExportId = RequestUtils.getLong(request, "dataExportId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		try {
			DataExport dataExport = dataExportService.getDataExport(dataExportId);
			if (dataExport != null && StringUtils.isNotEmpty(dataExport.getServiceKey())) {
				TableModelReader reader = new TableModelReader();
				List<TableModel> tables = reader.readModels();
				List<String> list = StringTools.split(dataExport.getTables());
				logger.debug("table list:" + list);
				if (tables != null && !tables.isEmpty() && list != null && !list.isEmpty()) {
					String dbpath = SystemProperties.getConfigRootPath() + "/db/" + dataExport.getServiceKey();
					FileUtils.deleteFile(dbpath);
					DataExportUpdateBean updateBean = new DataExportUpdateBean();
					for (TableModel tableModel : tables) {
						if (list.contains(tableModel.getTableName())
								|| list.contains(tableModel.getTableName().toUpperCase())) {
							logger.debug("reset export status:" + tableModel.getTableName());
							if (tableModel.getIdColumn() != null) {
								updateBean.resetExportFlag(databaseId, tableModel);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return ResponseUtils.responseResult(result);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataExportQuery query = new DataExportQuery();
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
		int total = dataExportService.getDataExportCountByQueryCriteria(query);
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

			List<DataExport> list = dataExportService.getDataExportsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataExport dataExport : list) {
					JSONObject rowJSON = dataExport.toJsonObject();
					rowJSON.put("id", dataExport.getId());
					rowJSON.put("rowId", dataExport.getId());
					rowJSON.put("dataExportId", dataExport.getId());
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

		return new ModelAndView("/datamgr/data/export/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataExport.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/data/export/query", modelMap);
	}

	
	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataExport dataExport = new DataExport();
		Tools.populate(dataExport, params);

		dataExport.setName(request.getParameter("name"));
		dataExport.setTitle(request.getParameter("title"));
		dataExport.setType(request.getParameter("type"));
		dataExport.setServiceKey(request.getParameter("serviceKey"));
		dataExport.setUserId(request.getParameter("userId"));
		dataExport.setOperation(request.getParameter("operation"));
		dataExport.setExportFlag(request.getParameter("exportFlag"));
		dataExport.setExportName(request.getParameter("exportName"));
		dataExport.setExportType(request.getParameter("exportType"));
		dataExport.setTables(request.getParameter("tables"));
		dataExport.setScheduleFlag(request.getParameter("scheduleFlag"));
		dataExport.setCreateTableFlag(request.getParameter("createTableFlag"));
		dataExport.setDeleteFetch(request.getParameter("deleteFetch"));
		dataExport.setPublicFlag(request.getParameter("publicFlag"));
		dataExport.setLocked(RequestUtils.getInt(request, "locked"));
		dataExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
		dataExport.setCreateBy(actorId);
		dataExport.setUpdateBy(actorId);

		dataExportService.save(dataExport);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDataExport")
	public byte[] saveDataExport(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataExport dataExport = new DataExport();
		try {
			Tools.populate(dataExport, params);
			dataExport.setName(request.getParameter("name"));
			dataExport.setTitle(request.getParameter("title"));
			dataExport.setType(request.getParameter("type"));
			dataExport.setServiceKey(request.getParameter("serviceKey"));
			dataExport.setUserId(request.getParameter("userId"));
			dataExport.setOperation(request.getParameter("operation"));
			dataExport.setExportFlag(request.getParameter("exportFlag"));
			dataExport.setExportName(request.getParameter("exportName"));
			dataExport.setExportType(request.getParameter("exportType"));
			dataExport.setTables(request.getParameter("tables"));
			dataExport.setScheduleFlag(request.getParameter("scheduleFlag"));
			dataExport.setCreateTableFlag(request.getParameter("createTableFlag"));
			dataExport.setDeleteFetch(request.getParameter("deleteFetch"));
			dataExport.setPublicFlag(request.getParameter("publicFlag"));
			dataExport.setLocked(RequestUtils.getInt(request, "locked"));
			dataExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
			dataExport.setCreateBy(actorId);
			dataExport.setUpdateBy(actorId);
			this.dataExportService.save(dataExport);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DataExport saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DataExport dataExport = new DataExport();
		try {
			Tools.populate(dataExport, model);
			dataExport.setName(ParamUtils.getString(model, "name"));
			dataExport.setTitle(ParamUtils.getString(model, "title"));
			dataExport.setType(ParamUtils.getString(model, "type"));
			dataExport.setServiceKey(ParamUtils.getString(model, "serviceKey"));
			dataExport.setUserId(ParamUtils.getString(model, "userId"));
			dataExport.setOperation(ParamUtils.getString(model, "operation"));
			dataExport.setExportFlag(ParamUtils.getString(model, "exportFlag"));
			dataExport.setExportName(ParamUtils.getString(model, "exportName"));
			dataExport.setExportType(request.getParameter("exportType"));
			dataExport.setTables(request.getParameter("tables"));
			dataExport.setScheduleFlag(ParamUtils.getString(model, "scheduleFlag"));
			dataExport.setCreateTableFlag(request.getParameter("createTableFlag"));
			dataExport.setDeleteFetch(ParamUtils.getString(model, "deleteFetch"));
			dataExport.setPublicFlag(ParamUtils.getString(model, "publicFlag"));
			dataExport.setLocked(ParamUtils.getInt(model, "locked"));
			dataExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
			dataExport.setCreateBy(actorId);
			dataExport.setUpdateBy(actorId);
			this.dataExportService.save(dataExport);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return dataExport;
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setDataExportService(DataExportService dataExportService) {
		this.dataExportService = dataExportService;
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@RequestMapping("/showExport")
	public ModelAndView showExport(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		DataExport dataExport = dataExportService.getDataExport(RequestUtils.getLong(request, "dataExportId"));
		if (dataExport != null) {
			request.setAttribute("dataExport", dataExport);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases(loginContext);
		request.setAttribute("databases", databases);
		// logger.debug("databases:" + databases);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataExport.showExport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/data/export/showExport", modelMap);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataExport dataExport = dataExportService.getDataExport(RequestUtils.getLong(request, "id"));

		Tools.populate(dataExport, params);

		dataExport.setName(request.getParameter("name"));
		dataExport.setTitle(request.getParameter("title"));
		dataExport.setType(request.getParameter("type"));
		dataExport.setOperation(request.getParameter("operation"));
		dataExport.setExportFlag(request.getParameter("exportFlag"));
		dataExport.setExportName(request.getParameter("exportName"));
		dataExport.setExportType(request.getParameter("exportType"));
		dataExport.setTables(request.getParameter("tables"));
		dataExport.setScheduleFlag(request.getParameter("scheduleFlag"));
		dataExport.setCreateTableFlag(request.getParameter("createTableFlag"));
		dataExport.setDeleteFetch(request.getParameter("deleteFetch"));
		dataExport.setPublicFlag(request.getParameter("publicFlag"));
		dataExport.setLocked(RequestUtils.getInt(request, "locked"));
		dataExport.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
		dataExport.setUpdateBy(user.getActorId());

		dataExportService.save(dataExport);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		DataExport dataExport = dataExportService.getDataExport(RequestUtils.getLong(request, "id"));
		request.setAttribute("dataExport", dataExport);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataExport.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/data/export/view");
	}

}
