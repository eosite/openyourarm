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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.Role;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.domain.DataSetMapping;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.jdbc.Sqlite2DataBase;
import com.glaf.datamgr.query.DataSetMappingQuery;
import com.glaf.datamgr.query.DataSetQuery;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetMappingService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.util.DataSetDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/dataset")
@RequestMapping("/dataset")
public class DataSetController {
	protected static final Log logger = LogFactory.getLog(DataSetController.class);

	protected DataSetService dataSetService;

	protected DataSetAuditService dataSetAuditService;

	protected ITreeModelService treeModelService;

	public DataSetController() {

	}

	@RequestMapping("/chartDef")
	public ModelAndView chartDef(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DataSet dataSet = dataSetService.getDataSet(request.getParameter("id"));
		if (dataSet != null) {
			request.setAttribute("dataSet", dataSet);
			List<SelectSegment> selectSegments = dataSet.getSelectSegments();
			if (selectSegments != null && !selectSegments.isEmpty()) {
				for (SelectSegment seg : selectSegments) {
					if (StringUtils.equals(seg.getMapping(), "category")) {
						request.setAttribute("category", seg.getId());
					}
					if (StringUtils.equals(seg.getMapping(), "series")) {
						request.setAttribute("series", seg.getId());
					}
					if (StringUtils.equals(seg.getMapping(), "value")) {
						request.setAttribute("value", seg.getId());
					}
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataSet.chartDef");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataset/chartDef", modelMap);
	}

	@RequestMapping("/chooseDatabases")
	public ModelAndView chooseDatabases(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DataSet dataSet = dataSetService.getDataSet(RequestUtils.getString(request, "dataSetId"));
		if (dataSet != null) {
			request.setAttribute("dataSet", dataSet);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<Long> selected = new ArrayList<Long>();
			List<Database> unselected = new ArrayList<Database>();
			if (dataSet != null && StringUtils.isNotEmpty(dataSet.getExecuteDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(dataSet.getExecuteDatabaseIds());
				for (Database database : databases) {
					if (ids.contains(database.getId())) {
						selected.add(database.getId());
					} else {
						unselected.add(database);
					}
				}
				request.setAttribute("selected", selected);
				request.setAttribute("unselected", databases);
			} else {
				request.setAttribute("selected", selected);
				request.setAttribute("unselected", databases);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataset.chooseDatabases");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataset/chooseDatabases", modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DataSet dataSet = dataSetService.getDataSet(String.valueOf(x));
					if (dataSet != null && (StringUtils.equals(dataSet.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						dataSet.setDeleteFlag(1);
						dataSet.setUpdateBy(loginContext.getActorId());
						dataSetService.save(dataSet);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataSet dataSet = dataSetService.getDataSet(String.valueOf(id));
			if (dataSet != null && (StringUtils.equals(dataSet.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				dataSet.setDeleteFlag(1);
				dataSet.setUpdateBy(loginContext.getActorId());
				dataSetService.save(dataSet);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DataSet dataSet = dataSetService.getDataSet(request.getParameter("id"));
		if (dataSet != null) {
			request.setAttribute("dataSet", dataSet);
			List<String> perms = StringTools.split(dataSet.getPerms());
			StringBuffer buffer = new StringBuffer();
			List<Role> roles = IdentityFactory.getRoles();

			if (roles != null && !roles.isEmpty()) {
				for (Role role : roles) {
					if (perms.contains(String.valueOf(role.getId()))) {
						buffer.append(role.getName());
						buffer.append(FileUtils.newline);
					}
				}
			}
			request.setAttribute("x_role_names", buffer.toString());

			String text = dataSet.getExecuteDatabaseIds();
			List<Long> databaseIds = StringTools.splitToLong(text);
			if (databaseIds != null && !databaseIds.isEmpty()) {
				buffer = new StringBuffer();
				DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
				for (Long databaseId : databaseIds) {
					Database database = cfg.getDatabase(databaseId);
					buffer.append(database.getTitle()).append("[").append(database.getDbname()).append("], ");
				}
				request.setAttribute("database_names", buffer.toString());
			}
		}

		String category = request.getParameter("category");
		if (StringUtils.isEmpty(category)) {
			category = "report_category";
		}
		TreeModel treeModel = treeModelService.getTreeModelByCode(category);
		if (treeModel != null) {
			treeModel = treeModelService.getTreeModelWithAllChildren(treeModel.getId());
			List<TreeModel> treeModels = treeModel.getChildren();
			request.setAttribute("treeModels", treeModels);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataSet.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataset/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetQuery query = new DataSetQuery();
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

		String keywordsLike_base64 = request.getParameter("keywordsLike_base64");
		if (StringUtils.isNotEmpty(keywordsLike_base64)) {
			String keywordsLike = new String(Base64.decodeBase64(keywordsLike_base64));
			query.setKeywordsLike(keywordsLike);
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
		int total = dataSetService.getDataSetCountByQueryCriteria(query);
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

			List<DataSet> list = dataSetService.getDataSetsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				String dsId = MapUtils.getString(params, "dsId", null);
				Map<String, DataSetMapping> map = null;
				if (dsId != null) {
					try {
						map = getDataSetMappings(dsId);
					} finally {
						//map = null;
					}
				}

				result.put("rows", rowsJSON);

				for (DataSet dataSet : list) {
					JSONObject rowJSON = dataSet.toJsonObject();
					rowJSON.put("id", dataSet.getId());
					rowJSON.put("rowId", dataSet.getId());
					rowJSON.put("dataSetId", dataSet.getId());
					rowJSON.put("startIndex", ++start);
					if (map != null && map.containsKey(dataSet.getId())) {
						DataSetMapping mapping = map.get(dataSet.getId());
						rowJSON.put("status", mapping.getStatus());
						rowJSON.put("mappingId", mapping.getId());
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

	protected Map<String, DataSetMapping> getDataSetMappings(String id) {

		DataSetMappingService dataSetMappingService = //
				ContextFactory.getBean("com.glaf.datamgr.service.dataSetMappingService");
		DataSetMappingQuery query = new DataSetMappingQuery();
		query.setParentId_(id);

		List<DataSetMapping> list = dataSetMappingService.list(query);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, DataSetMapping> map = new HashMap<>();
			for (DataSetMapping mapping : list) {
				map.put(mapping.getDsmId(), mapping);
			}
			return map;
		}

		return null;
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

		String keywordsLike = request.getParameter("keywordsLike");
		if (StringUtils.isNotEmpty(keywordsLike)) {
			request.setAttribute("keywordsLike_base64", Base64.encodeBase64String(keywordsLike.getBytes()));
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

		return new ModelAndView("/datamgr/dataset/list", modelMap);
	}

	@RequestMapping("/preview")
	public ModelAndView preview(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		// DataSet dataSet =
		// dataSetService.getDataSet(request.getParameter("id"));

		//DataSet dataSet = null;

		DataSetAudit audit = dataSetAuditService.getLastestDataSetAudit(request.getParameter("id"));
		if (audit != null) {
			if (StringUtils.isNotBlank(audit.getContent())) {

				JSONObject ds = JSON.parseObject(audit.getContent());

				// dataSet = JSON.parseObject(audit.getContent(),
				// DataSet.class);

				request.setAttribute("dataSet", ds);
			}
		}

		// request.setAttribute("dataSet", dataSet);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataSet.preview");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/dataset/preview");
	}

	@ResponseBody
	@RequestMapping("/previewJSON")
	public byte[] previewJSON(HttpServletRequest request) throws IOException {
		return this.getJson(request).toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/api/json")
	public byte[] json(HttpServletRequest request) throws IOException {
		return this.getJson(request).toJSONString().getBytes("UTF-8");
	}

	private JSONObject getJson(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		DataSetBuilder builder = new DataSetBuilder();

		JSONObject result = new JSONObject();

		String datasetId = request.getParameter("id");

		int pageNo = RequestUtils.getInt(request, "page", 1);
		int limit = RequestUtils.getInt(request, "rows", 10), rows = limit;
		int start = (pageNo - 1) * limit;
		if (start <= 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		limit = pageNo * limit;

		JSONObject json = builder.getJson(datasetId, start, rows, params);

		if (json == null) {
			json = new JSONObject();
			json.put("total", 0);
			json.put("rows", new JSONArray());
		}

		for (String key : new String[] { "total", "rows" , "encrypts"}) {
			result.put(key, json.get(key));
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/api/total")
	public byte[] total(HttpServletRequest request) throws IOException {

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		DataSetBuilder builder = new DataSetBuilder();

		JSONObject result = new JSONObject();

		String datasetId = request.getParameter("id");

		JSONObject json = builder.getJson(datasetId, 1, 1, params);

		if (json == null) {
			json = new JSONObject();
			json.put("total", 0);
			// json.put("rows", new JSONArray());
		}

		// for(String key : new String[]{"total","rows"}){
		result.put("total", json.get("total"));
		// }
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataSet.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/dataSet/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetQuery query = new DataSetQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DataSetDomainFactory.processDataRequest(dataRequest);

		String keywordsLike_base64 = request.getParameter("keywordsLike_base64");
		if (StringUtils.isNotEmpty(keywordsLike_base64)) {
			String keywordsLike = new String(Base64.decodeBase64(keywordsLike_base64));
			query.setKeywordsLike(keywordsLike);
		}

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
		int total = dataSetService.getDataSetCountByQueryCriteria(query);
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

			List<DataSet> list = dataSetService.getDataSetsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataSet dataSet : list) {
					JSONObject rowJSON = dataSet.toJsonObject();
					rowJSON.put("id", dataSet.getId());
					rowJSON.put("dataSetId", dataSet.getId());
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

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();

		if (StringUtils.equals(SystemConfig.getString("RunEnvironment"), "product")) {
			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("Access Deny");
			}
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		DataSet dataSet = new DataSet();
		Tools.populate(dataSet, params);

		dataSet.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
		dataSet.setExecuteDatabaseIds(request.getParameter("executeDatabaseIds"));
		dataSet.setName(request.getParameter("name"));
		dataSet.setTitle(request.getParameter("title"));
		dataSet.setDescription(request.getParameter("description"));
		dataSet.setStatementId(request.getParameter("statementId"));
		dataSet.setSql(request.getParameter("sql"));
		dataSet.setSourceRuleId(request.getParameter("sourceRuleId"));
		dataSet.setSourceRuleType(request.getParameter("sourceRuleType"));
		dataSet.setSourceTables(request.getParameter("sourceTables"));
		dataSet.setAccessType(request.getParameter("accessType"));
		dataSet.setPerms(request.getParameter("perms"));
		dataSet.setAddressPerms(request.getParameter("addressPerms"));
		dataSet.setType(request.getParameter("type"));
		dataSet.setCacheFlag(request.getParameter("cacheFlag"));
		dataSet.setDynamicFlag(request.getParameter("dynamicFlag"));
		dataSet.setDistinctFlag(request.getParameter("distinctFlag"));
		dataSet.setUpdateFlag(request.getParameter("updateFlag"));
		dataSet.setDeleteFetch(request.getParameter("deleteFetch"));
		dataSet.setExportFlag(request.getParameter("exportFlag"));
		dataSet.setExportDBName(request.getParameter("exportDBName"));
		dataSet.setExportTableName(request.getParameter("exportTableName"));
		dataSet.setRowKey(request.getParameter("rowKey"));
		dataSet.setLocked(RequestUtils.getInt(request, "locked"));
		dataSet.setCreateBy(actorId);

		dataSetService.save(dataSet);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAsDataSet")
	public byte[] saveAsDataSet(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();

		if (StringUtils.equals(SystemConfig.getString("RunEnvironment"), "product")) {
			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("Access Deny");
			}
		}
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String id = request.getParameter("id");
		DataSet dataSet = null;
		try {
			dataSet = dataSetService.getDataSet(id);
			if (dataSet != null) {
				Tools.populate(dataSet, params);
				dataSet.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
				dataSet.setExecuteDatabaseIds(request.getParameter("executeDatabaseIds"));
				dataSet.setName(request.getParameter("name"));
				dataSet.setTitle(request.getParameter("title"));
				dataSet.setDescription(request.getParameter("description"));
				dataSet.setStatementId(request.getParameter("statementId"));
				dataSet.setSql(request.getParameter("sql"));
				dataSet.setSourceRuleId(request.getParameter("sourceRuleId"));
				dataSet.setSourceRuleType(request.getParameter("sourceRuleType"));
				dataSet.setSourceTables(request.getParameter("sourceTables"));
				dataSet.setAccessType(request.getParameter("accessType"));
				dataSet.setPerms(request.getParameter("perms"));
				dataSet.setAddressPerms(request.getParameter("addressPerms"));
				dataSet.setType(request.getParameter("type"));
				dataSet.setCacheFlag(request.getParameter("cacheFlag"));
				dataSet.setDynamicFlag(request.getParameter("dynamicFlag"));
				dataSet.setDistinctFlag(request.getParameter("distinctFlag"));
				dataSet.setUpdateFlag(request.getParameter("updateFlag"));
				dataSet.setDeleteFetch(request.getParameter("deleteFetch"));
				dataSet.setExportFlag(request.getParameter("exportFlag"));
				dataSet.setExportDBName(request.getParameter("exportDBName"));
				dataSet.setExportTableName(request.getParameter("exportTableName"));
				dataSet.setRowKey(request.getParameter("rowKey"));
				dataSet.setLocked(RequestUtils.getInt(request, "locked"));
				dataSet.setCreateBy(actorId);
				dataSet.setId(null);
				this.dataSetService.saveAs(dataSet);

				DataSetAudit audit = dataSetAuditService.getLastestDataSetAudit(id);

				if (audit != null) {
					audit.setDatasetId(dataSet.getId());
					audit.setId(null);
					JSONObject lastJson = JSON.parseObject(audit.getContent());
					lastJson.put("id", dataSet.getId());
					audit.setContent(lastJson.toJSONString());
					dataSetAuditService.save(audit);
				}

				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveChartDef")
	public byte[] saveChartDef(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		if (StringUtils.equals(SystemConfig.getString("RunEnvironment"), "product")) {
			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("Access Deny");
			}
		}
		try {
			DataSet dataSet = dataSetService.getDataSet(request.getParameter("id"));
			if (dataSet != null) {
				List<SelectSegment> selectSegments = dataSet.getSelectSegments();
				if (selectSegments != null && !selectSegments.isEmpty()) {
					Map<Long, SelectSegment> segMap = new HashMap<Long, SelectSegment>();
					for (SelectSegment seg : selectSegments) {
						segMap.put(seg.getId(), seg);
					}
					List<SelectSegment> rows = new ArrayList<SelectSegment>();
					Long category = RequestUtils.getLong(request, "category");
					if (segMap.get(category) != null) {
						SelectSegment seg = segMap.get(category);
						seg.setMapping("category");
						rows.add(seg);
					}
					Long series = RequestUtils.getLong(request, "series");
					if (segMap.get(series) != null) {
						SelectSegment seg = segMap.get(series);
						seg.setMapping("series");
						rows.add(seg);
					}
					Long value = RequestUtils.getLong(request, "value");
					if (segMap.get(value) != null) {
						SelectSegment seg = segMap.get(value);
						seg.setMapping("value");
						rows.add(seg);
					}
					dataSetService.updateMappings(dataSet.getId(), rows);
					return ResponseUtils.responseJsonResult(true);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveDataSet")
	public byte[] saveDataSet(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();

		if (StringUtils.equals(SystemConfig.getString("RunEnvironment"), "product")) {
			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("Access Deny");
			}
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSet dataSet = new DataSet();
		try {
			Tools.populate(dataSet, params);
			dataSet.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
			dataSet.setExecuteDatabaseIds(request.getParameter("executeDatabaseIds"));
			dataSet.setName(request.getParameter("name"));
			dataSet.setTitle(request.getParameter("title"));
			dataSet.setDescription(request.getParameter("description"));
			dataSet.setStatementId(request.getParameter("statementId"));
			dataSet.setSql(request.getParameter("sql"));
			dataSet.setSourceRuleId(request.getParameter("sourceRuleId"));
			dataSet.setSourceRuleType(request.getParameter("sourceRuleType"));
			dataSet.setSourceTables(request.getParameter("sourceTables"));
			dataSet.setAccessType(request.getParameter("accessType"));
			dataSet.setPerms(request.getParameter("perms"));
			dataSet.setAddressPerms(request.getParameter("addressPerms"));
			dataSet.setType(request.getParameter("type"));
			dataSet.setCacheFlag(request.getParameter("cacheFlag"));
			dataSet.setDynamicFlag(request.getParameter("dynamicFlag"));
			dataSet.setDistinctFlag(request.getParameter("distinctFlag"));
			dataSet.setUpdateFlag(request.getParameter("updateFlag"));
			dataSet.setDeleteFetch(request.getParameter("deleteFetch"));
			dataSet.setExportFlag(request.getParameter("exportFlag"));
			dataSet.setExportDBName(request.getParameter("exportDBName"));
			dataSet.setExportTableName(request.getParameter("exportTableName"));
			dataSet.setRowKey(request.getParameter("rowKey"));
			dataSet.setLocked(RequestUtils.getInt(request, "locked"));
			dataSet.setCreateBy(actorId);
			this.dataSetService.save(dataSet);

			JSONObject json = dataSet.toJsonObject();

			// return ResponseUtils.responseJsonResult(true);
			return json.toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DataSet saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();

		if (StringUtils.equals(SystemConfig.getString("RunEnvironment"), "product")) {
			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("Access Deny");
			}
		}
		DataSet dataSet = new DataSet();
		try {
			Tools.populate(dataSet, model);
			dataSet.setDatabaseId(ParamUtils.getLong(model, "databaseId"));
			dataSet.setExecuteDatabaseIds(ParamUtils.getString(model, "executeDatabaseIds"));
			dataSet.setName(ParamUtils.getString(model, "name"));
			dataSet.setTitle(ParamUtils.getString(model, "title"));
			dataSet.setDescription(ParamUtils.getString(model, "description"));
			dataSet.setStatementId(ParamUtils.getString(model, "statementId"));
			dataSet.setSql(ParamUtils.getString(model, "sql"));
			dataSet.setSourceRuleId(ParamUtils.getString(model, "sourceRuleId"));
			dataSet.setSourceRuleType(ParamUtils.getString(model, "sourceRuleType"));
			dataSet.setSourceTables(ParamUtils.getString(model, "sourceTables"));
			dataSet.setAccessType(ParamUtils.getString(model, "accessType"));
			dataSet.setPerms(ParamUtils.getString(model, "perms"));
			dataSet.setAddressPerms(ParamUtils.getString(model, "addressPerms"));
			dataSet.setType(ParamUtils.getString(model, "type"));
			dataSet.setCacheFlag(ParamUtils.getString(model, "cacheFlag"));
			dataSet.setDynamicFlag(ParamUtils.getString(model, "dynamicFlag"));
			dataSet.setDistinctFlag(ParamUtils.getString(model, "distinctFlag"));
			dataSet.setUpdateFlag(ParamUtils.getString(model, "updateFlag"));
			dataSet.setDeleteFetch(request.getParameter("deleteFetch"));
			dataSet.setExportFlag(request.getParameter("exportFlag"));
			dataSet.setExportDBName(request.getParameter("exportDBName"));
			dataSet.setExportTableName(request.getParameter("exportTableName"));
			dataSet.setRowKey(request.getParameter("rowKey"));
			dataSet.setLocked(ParamUtils.getInt(model, "locked"));
			dataSet.setCreateBy(actorId);
			this.dataSetService.save(dataSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return dataSet;
	}

	@javax.annotation.Resource
	public void setDataSetAuditService(DataSetAuditService dataSetAuditService) {
		this.dataSetAuditService = dataSetAuditService;
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

	@RequestMapping("/showTree")
	public ModelAndView showTree(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String keywordsLike = request.getParameter("keywordsLike");
		if (StringUtils.isNotEmpty(keywordsLike)) {
			request.setAttribute("keywordsLike_base64", Base64.encodeBase64String(keywordsLike.getBytes()));
		}

		String x_view = ViewProperties.getString("dataSet.showTree");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataset/show_tree", modelMap);
	}

	@RequestMapping("/sqlbuilder")
	public ModelAndView sqlbuilder(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DataSet dataSet = dataSetService.getDataSet(request.getParameter("id"));
		String code = CustomProperties.getString("dataSet.code");
		code = RequestUtils.getString(request, "code", code == null ? "report_category" : code);
		List<Map<String, Object>> trees = dataSetService.getDataSetTree(code);

		if (CollectionUtils.isNotEmpty(trees)) {
			JSONArray jar = new JSONArray();
			jar.addAll(trees);
			modelMap.put("trees", jar);
		}

		if (dataSet != null) {
			
			if(StringUtils.isNotBlank(dataSet.getSql())){
				dataSet.setSql(dataSet.getSql().trim());
			}
			
			request.setAttribute("dataSet", dataSet);
			logger.debug("-------------------dataSet-------------------");
			logger.debug(dataSet.toJsonObject().toJSONString());
			// logger.debug(dataSet.getSelectSegments());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataSet.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataset/sqlbuilder", modelMap);
	}

	/**
	 * 获取数据集字段以及参数
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getDataSetFields")
	public byte[] getDataSetFields(HttpServletRequest request) throws IOException {
		JSONObject result = new JSONObject();

		String datasetId = RequestUtils.getString(request, "id");

		if (StringUtils.isEmpty(datasetId)) {
			throw new RuntimeException("datasetId is null !");
		}

		JSONArray rows = this.dataSetService.getDataSetFields(datasetId);
		JSONArray params = this.dataSetService.getDataSetParams(datasetId);

		result.put("rows", rows);
		result.put("params", params);

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();

		if (StringUtils.equals(SystemConfig.getString("RunEnvironment"), "product")) {
			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("Access Deny");
			}
		}
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataSet dataSet = dataSetService.getDataSet(request.getParameter("id"));

		Tools.populate(dataSet, params);

		dataSet.setDatabaseId(RequestUtils.getLong(request, "databaseId"));
		dataSet.setExecuteDatabaseIds(request.getParameter("executeDatabaseIds"));
		dataSet.setName(request.getParameter("name"));
		dataSet.setTitle(request.getParameter("title"));
		dataSet.setDescription(request.getParameter("description"));
		dataSet.setStatementId(request.getParameter("statementId"));
		dataSet.setSql(request.getParameter("sql"));
		dataSet.setSourceRuleId(request.getParameter("sourceRuleId"));
		dataSet.setSourceRuleType(request.getParameter("sourceRuleType"));
		dataSet.setSourceTables(request.getParameter("sourceTables"));
		dataSet.setAccessType(request.getParameter("accessType"));
		dataSet.setPerms(request.getParameter("perms"));
		dataSet.setAddressPerms(request.getParameter("addressPerms"));
		dataSet.setType(request.getParameter("type"));
		dataSet.setCacheFlag(request.getParameter("cacheFlag"));
		dataSet.setDynamicFlag(request.getParameter("dynamicFlag"));
		dataSet.setDistinctFlag(request.getParameter("distinctFlag"));
		dataSet.setUpdateFlag(request.getParameter("updateFlag"));
		dataSet.setDeleteFetch(request.getParameter("deleteFetch"));
		dataSet.setExportFlag(request.getParameter("exportFlag"));
		dataSet.setExportDBName(request.getParameter("exportDBName"));
		dataSet.setExportTableName(request.getParameter("exportTableName"));
		dataSet.setRowKey(request.getParameter("rowKey"));
		dataSet.setLocked(RequestUtils.getInt(request, "locked"));
		dataSet.setUpdateBy(actorId);

		dataSetService.save(dataSet);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		DataSet dataSet = dataSetService.getDataSet(request.getParameter("id"));
		request.setAttribute("dataSet", dataSet);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataSet.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/dataset/view");
	}

	/**
	 * 刷新表结构配置页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toMetaData")
	public ModelAndView toMetaData(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return new ModelAndView("/datamgr/dataset/toMetaData", modelMap);
	}

	/**
	 * 生成表结构
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/buildMetaData")
	public byte[] saveMetaData(HttpServletRequest request) throws Exception {
		String systemName = RequestUtils.getString(request, "systemName");
		String db = RequestUtils.getString(request, "db");
		String tns = RequestUtils.getString(request, "tns");
		if (StringUtils.isNotBlank(tns)) {
			String tableNames[] = tns.split(",");
			Sqlite2DataBase.transDataToSqlite(Arrays.asList(tableNames), db, systemName);
		} else {
			// 刷新所有
		}
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 刷新表结构
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/refreshMetaData")
	public byte[] refreshMetaData(HttpServletRequest request) throws Exception {

		String db = RequestUtils.getString(request, "db");

		String systemName = RequestUtils.getString(request, "systemName");

		JSONObject result = new JSONObject();

		Sqlite2DataBase.refreshMetaDataBySqlite(db, systemName);

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 下载表结构
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/downloadMetaData")
	public void downloadMetaData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String db = Sqlite2DataBase.METADATA;
		String filename = SystemProperties.getConfigRootPath() + "/db/" + db;

		byte[] bytes = FileUtils.getBytes(filename);

		ResponseUtils.download(request, response, bytes, db);

	}

	/**
	 * 上传界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFiles")
	public ModelAndView uploadFiles(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return new ModelAndView("/datamgr/dataset/uploadFiles", modelMap);
	}

	/**
	 * 导入并刷新表结构
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/importMetaData")
	public byte[] importMetaData(HttpServletRequest request) throws Exception {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		if (StringUtils.equals(SystemConfig.getString("RunEnvironment"), "product")) {
			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("Access Deny");
			}
		}
		String systemName = RequestUtils.getString(request, "systemName");

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		List<String> dbs = new ArrayList<String>();
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			String db, filename, basePath = SystemProperties.getConfigRootPath() + "/db/";
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				db = String.format("metadata_%s.db", System.currentTimeMillis() + "");
				filename = basePath + db;
				FileUtils.save(filename, mFile.getInputStream());
				dbs.add(db);
			}

			for (String d : dbs) {
				Sqlite2DataBase.refreshMetaDataBySqlite(d, systemName);
			}

		}
		return ResponseUtils.responseJsonResult(true);
	}

}
