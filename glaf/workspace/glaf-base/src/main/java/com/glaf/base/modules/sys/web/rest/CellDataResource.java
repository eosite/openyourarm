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

package com.glaf.base.modules.sys.web.rest;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.model.TableEntity;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

@Controller("/rs/isdp/cell/data")
@Path("/rs/isdp/cell/data")
public class CellDataResource {

	protected static final Log logger = LogFactory.getLog(CellDataResource.class);

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	protected IFieldInterfaceService fieldInterfaceService;

	@GET
	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request) throws IOException {
		logger.debug("-----------------------data---------------------------");
		JSONObject result = new JSONObject();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		long databaseId = RequestUtils.getLong(request, "databaseId");

		Database currentDB = null;

		if (databaseId > 0) {
			currentDB = config.getDatabase(loginContext, databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		logger.debug("params:" + params);

		String systemName = Environment.getCurrentSystemName();
		try {
			if (currentDB != null) {
				logger.debug("currentDB:" + currentDB.getName());
				Environment.setCurrentSystemName(currentDB.getName());
			}

			TableEntity tableEntity = new TableEntity();
			String tableName = request.getParameter("tableName");
			String tableName_enc = request.getParameter("tableName_enc");
			if (StringUtils.isNotEmpty(tableName_enc)) {
				tableName = RequestUtils.decodeString(tableName_enc);
			}

			if (!DBUtils.isAllowedTable(tableName)) {
				return result.toJSONString().getBytes("UTF-8");
			}

			Tools.populate(tableEntity, params);
			List<FieldInterface> fields = fieldInterfaceService.getListShowFieldsByFrmType(tableName);
			tableEntity.setFields(fields);
			tableEntity.setTableName(tableName);

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

			int total = fieldInterfaceService.getRowDataCountByQueryCriteria(tableEntity);
			if (total > 0 && fields != null && !fields.isEmpty()) {
				List<Map<String, Object>> list = fieldInterfaceService.getRowDataListByQueryCriteria(start, limit,
						tableEntity);
				if (list != null && !list.isEmpty()) {
					int index = 1;
					JSONArray array = new JSONArray();
					for (Map<String, Object> dataMap : list) {
						JSONObject row = new JSONObject();
						for (FieldInterface field : fields) {
							row.put(field.getDname(), ParamUtils.getObject(dataMap, field.getDname()));
							if (StringUtils.equalsIgnoreCase(field.getDtype(), "datetime")) {
								Date date = ParamUtils.getDate(dataMap, field.getDname());
								if (date != null) {
									row.put(field.getDname(), DateUtils.getDate(date));
									row.put(field.getDname() + "_datetime", DateUtils.getDateTime(date));
								}
							}
						}
						row.put("startIndex", start + index++);
						array.add(row);
					}
					result.put("rows", array);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(systemName);
		}

		// logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] json(@Context HttpServletRequest request) throws IOException {
		logger.debug("-----------------------json---------------------------");
		JSONObject result = new JSONObject();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
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

		Database currentDB = null;

		if (databaseId > 0) {
			currentDB = config.getDatabase(loginContext, databaseId);
			if (!config.checkConfig(currentDB)) {
				currentDB = null;
			}
		}

		logger.debug("params:" + params);

		String sql = " SELECT t1.id fid, t2.dname, t2.fname, t2.dtype, t2.strlen as length FROM  cell_data_field t1 ,  interface t2 WHERE   t1.id = t2.listid AND t1.tableid =  #{tableId} ";

		QueryHelper helper = new QueryHelper();
		Map<String, String> colMap = new HashMap<String, String>();

		String systemName = Environment.getCurrentSystemName();
		try {
			if (currentDB != null) {
				logger.debug("currentDB:" + currentDB.getName());
				Environment.setCurrentSystemName(currentDB.getName());
			}
			List<Map<String, Object>> dataList = helper.getResultList(Environment.getCurrentSystemName(), sql, params);
			if (dataList != null && !dataList.isEmpty()) {
				for (Map<String, Object> dataMap : dataList) {
					colMap.put(ParamUtils.getString(dataMap, "fname"), ParamUtils.getString(dataMap, "dname"));
				}
			}

			sql = " select * from cell_data_table where id = #{tableId} ";
			Map<String, Object> dataMap = helper.selectOne(Environment.getCurrentSystemName(), sql, params);
			logger.debug("->dataMap:" + dataMap);
			if (dataMap != null && ParamUtils.getString(dataMap, "tablename") != null) {
				sql = "select * from " + ParamUtils.getString(dataMap, "tablename");
			} else {
				sql = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(systemName);
		}

		logger.debug("sql:" + sql);

		if (!DBUtils.isAllowedSql(sql)) {
			logger.debug("deny sql:" + sql);
			return result.toJSONString().getBytes("UTF-8");
		}

		String gridType = request.getParameter("gridType");

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = 2000;
		}

		try {
			if (currentDB != null) {
				logger.debug("currentDB:" + currentDB.getName());
				Environment.setCurrentSystemName(currentDB.getName());
			}

			Map<String, Object> parameters = new HashMap<String, Object>();
			Map<String, Object> dataMap = SystemConfig.getContextMap();
			parameters.putAll(dataMap);
			parameters.put("loginContext", loginContext);
			parameters.put("actorId", loginContext.getActorId());
			parameters.put("currentUser", loginContext.getUser());

			int total = 0;
			if (currentDB != null) {
				total = helper.getTotalRecords(currentDB.getName(), sql, parameters);
			} else {
				total = helper.getTotalRecords(systemName, sql, parameters);
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
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(systemName);
		}

		// logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
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
