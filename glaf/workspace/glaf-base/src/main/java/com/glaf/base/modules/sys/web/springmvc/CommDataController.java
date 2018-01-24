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
package com.glaf.base.modules.sys.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.model.TableEntity;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/common/data")
@RequestMapping("/common/data")
public class CommDataController {
	protected static final Log logger = LogFactory.getLog(CommDataController.class);

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	protected IFieldInterfaceService fieldInterfaceService;

	@RequestMapping("/datagrid")
	public ModelAndView datagrid(HttpServletRequest request, ModelMap modelMap) {
		logger.debug("-----------------------datagrid---------------------------");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RequestUtils.setRequestParameterToAttribute(request);

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String tableId = null;
		String tableId_enc = request.getParameter("tableId_enc");

		if (StringUtils.isNotEmpty(tableId_enc)) {
			tableId = RequestUtils.decodeString(tableId_enc);
		}

		if (StringUtils.isEmpty(tableId)) {
			tableId = request.getParameter("tableId");
		}
		params.put("tableId", tableId);
		request.setAttribute("tableId", tableId);

		int height = RequestUtils.getInt(request, "height", 320);
		request.setAttribute("height", height);

		logger.debug("->params:" + params);

		Database currentDB = null;

		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> databases = config.getDatabases(loginContext);

		request.setAttribute("databases", databases);

		if (databaseId > 0) {
			currentDB = databaseService.getDatabaseById(databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		if (currentDB != null && databases.contains(currentDB)) {
			QueryHelper helper = new QueryHelper();
			request.setAttribute("databaseId", databaseId);
			String systemName = Environment.getCurrentSystemName();
			String sql = " SELECT t1.id fid, t2.dname, t2.fname, t2.dtype, t2.strlen as length FROM  cell_data_field t1 ,  interface t2 WHERE   t1.id = t2.listid AND t1.tableid =  #{tableId} ";
			try {
				Environment.setCurrentSystemName(currentDB.getName());
				List<Map<String, Object>> dataList = helper.getResultList(Environment.getCurrentSystemName(), sql,
						params);
				if (dataList != null && !dataList.isEmpty()) {
					logger.debug("dataList size:" + dataList.size());
					int totalLenth = 0;
					List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
					for (Map<String, Object> dataMap : dataList) {
						ColumnDefinition col = new ColumnDefinition();
						col.setId(ParamUtils.getString(dataMap, "fid"));
						col.setColumnName(ParamUtils.getString(dataMap, "dname"));
						col.setColumnLabel(ParamUtils.getString(dataMap, "fname"));
						col.setTitle(ParamUtils.getString(dataMap, "fname"));
						col.setName(ParamUtils.getString(dataMap, "dname"));
						col.setType(ParamUtils.getString(dataMap, "dtype"));
						col.setLength(ParamUtils.getInt(dataMap, "length"));
						if (col.getLength() < 90) {
							col.setLength(90);
						}
						if (StringUtils.equalsIgnoreCase(col.getType(), "datetime")) {
							if (col.getLength() < 120) {
								col.setLength(120);
							}
						}
						totalLenth = totalLenth + col.getLength();
						columns.add(col);
					}
					request.setAttribute("columns", columns);
					request.setAttribute("width", totalLenth + 90);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				Environment.setCurrentSystemName(systemName);
			}
		}

		return new ModelAndView("/isdp/data/datagrid", modelMap);
	}

	@RequestMapping("/datalist")
	public ModelAndView datalist(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableEntity query = new TableEntity();
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		Tools.populate(query, params);
		List<FieldInterface> fields = fieldInterfaceService.getListShowFieldsByFrmType(tableName);
		query.setFields(fields);
		query.setTableName(tableName);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {

		}

		int start = 0;
		int limit = RequestUtils.getInt(request, "rows");

		int pageNo = 1;

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		FieldInterface idField = null;
		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface field : fields) {
				if ("1".equals(field.getIsPrimaryKey())) {
					idField = field;
					break;
				}
			}
			request.setAttribute("idField", idField);
			request.setAttribute("fields", fields);
		}

		int height = RequestUtils.getInt(request, "height", 320);
		request.setAttribute("height", height);

		return new ModelAndView("/isdp/data/datalist", modelMap);
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		List<FieldInterface> fields = fieldInterfaceService.getFieldsByFrmType(tableName);
		FieldInterface idField = null;
		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface field : fields) {
				if ("1".equals(field.getIsPrimaryKey())) {
					idField = field;
					break;
				}
			}
			request.setAttribute("fields", fields);
		}
		if (idField != null) {
			Object rowId = null;
			if (StringUtils.equalsIgnoreCase(idField.getDtype(), "i4")) {
				rowId = RequestUtils.getInteger(request, idField.getDname());
			} else if (StringUtils.equalsIgnoreCase(idField.getDtype(), "int")) {
				rowId = RequestUtils.getInteger(request, idField.getDname());
			} else if (StringUtils.equalsIgnoreCase(idField.getDtype(), "integer")) {
				rowId = RequestUtils.getInteger(request, idField.getDname());
			} else if (StringUtils.equalsIgnoreCase(idField.getDtype(), "i8")) {
				rowId = RequestUtils.getLong(request, idField.getDname());
			} else if (StringUtils.equalsIgnoreCase(idField.getDtype(), "long")) {
				rowId = RequestUtils.getLong(request, idField.getDname());
			} else {
				rowId = RequestUtils.getString(request, idField.getDname());
			}
			if (rowId != null) {
				Map<String, Object> dataMap = fieldInterfaceService.getRowData(tableName, rowId);
				if (dataMap != null && !dataMap.isEmpty()) {
					request.setAttribute("dataMap", dataMap);
					request.setAttribute("rowId", rowId);
					for (FieldInterface field : fields) {
						field.setValue(ParamUtils.getObject(dataMap, field.getDname()));
					}
				}
			}
			request.setAttribute("idField", idField);
		}

		String x_view = ViewProperties.getString("common.data.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/isdp/data/edit", modelMap);
	}

	/**
	 * 显示列表页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		List<FieldInterface> fields = fieldInterfaceService.getFieldsByFrmType(tableName);
		FieldInterface idField = null;
		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface field : fields) {
				if ("1".equals(field.getIsPrimaryKey())) {
					idField = field;
					break;
				}
			}
			request.setAttribute("fields", fields);

			StringBuilder buffer = new StringBuilder(500);
			for (FieldInterface field : fields) {
				buffer.append("\n                      ");
				buffer.append("\"").append(field.getDname()).append("\"").append(":{");
				if (StringUtils.equalsIgnoreCase(field.getDtype(), "i4")) {
					buffer.append("\"type\": \"number\"");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(), "i8")) {
					buffer.append("\"type\": \"number\"");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(), "r8")) {
					buffer.append("\"type\": \"number\"");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(), "datetime")) {
					buffer.append("\"type\": \"date\"");
					buffer.append(",\"format\": \"{0: yyyy-MM-dd}\"");
				} else {
					buffer.append("\"type\": \"string\"");
				}
				buffer.append("},");
			}
			buffer.append("\n\"                      startIndex\": {").append("\"type\": \"number\"}");
			request.setAttribute("fields_buffer", buffer.toString());
		}

		if (idField != null) {
			request.setAttribute("idField", idField);
		}

		String x_view = ViewProperties.getString("common.data.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/isdp/data/list", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableEntity query = new TableEntity();
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		Tools.populate(query, params);
		List<FieldInterface> fields = fieldInterfaceService.getFieldsByFrmType(tableName);
		query.setFields(fields);
		query.processDataRequest(dataRequest);
		query.setTableName(tableName);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {

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
		int total = fieldInterfaceService.getRowDataCountByQueryCriteria(query);
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

			List<Map<String, Object>> list = fieldInterfaceService.getRowDataListByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Map<String, Object> dataMap : list) {
					JSONObject rowJSON = JsonUtils.toJSONObject(dataMap);
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

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		List<FieldInterface> fields = fieldInterfaceService.getFieldsByFrmType(tableName);
		FieldInterface idField = null;
		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface field : fields) {
				if ("1".equals(field.getIsPrimaryKey())) {
					idField = field;
					break;
				}
			}
			request.setAttribute("fields", fields);
		}
		if (idField != null) {
			LoginContext loginContext = RequestUtils.getLoginContext(request);
			Map<String, Object> dataMap = RequestUtils.getParameterMap(request);
			logger.debug(dataMap);
			dataMap.put("createBy", loginContext.getActorId());
			dataMap.put("updateBy", loginContext.getActorId());
			fieldInterfaceService.saveData(tableName, dataMap);
			return ResponseUtils.responseJsonResult(true);
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setFieldInterfaceService(IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

}
