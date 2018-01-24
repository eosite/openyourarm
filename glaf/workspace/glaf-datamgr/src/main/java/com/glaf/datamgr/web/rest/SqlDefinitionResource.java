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

package com.glaf.datamgr.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.*;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.*;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller("/rs/datamgr/sql/definition")
@Path("/rs/datamgr/sql/definition")
public class SqlDefinitionResource {
	protected static final Log logger = LogFactory.getLog(SqlDefinitionResource.class);

	protected SqlDefinitionService sqlDefinitionService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlDefinitionQuery query = new SqlDefinitionQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);

		SqlDefinitionDomainFactory.processDataRequest(dataRequest);

		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
			query.setShowShare("Y");
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
		int total = sqlDefinitionService.getSqlDefinitionCountByQueryCriteria(query);
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

			List<SqlDefinition> list = sqlDefinitionService.getSqlDefinitionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlDefinition sqlDefinition : list) {
					JSONObject rowJSON = sqlDefinition.toJsonObject();
					rowJSON.put("id", sqlDefinition.getId());
					rowJSON.put("sqlDefinitionId", sqlDefinition.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.remove("sql");
					rowJSON.remove("countSql");
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
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

	@GET
	@POST
	@Path("/displayType")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] displayType(@Context HttpServletRequest request) throws IOException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("text", "不显示");
		array.add(json);

		JSONObject json2 = new JSONObject();
		json2.put("code", "1");
		json2.put("text", "表单");
		array.add(json2);

		JSONObject json3 = new JSONObject();
		json3.put("code", "2");
		json3.put("text", "列表");
		array.add(json3);

		JSONObject json4 = new JSONObject();
		json4.put("code", "4");
		json4.put("text", "表单及列表");
		array.add(json4);

		return array.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/javaType")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] javaType(@Context HttpServletRequest request) throws IOException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("code", "Integer");
		json.put("text", "整数型");
		array.add(json);

		JSONObject json2 = new JSONObject();
		json2.put("code", "Long");
		json2.put("text", "长整数型");
		array.add(json2);

		JSONObject json3 = new JSONObject();
		json3.put("code", "Double");
		json3.put("text", "数值型");
		array.add(json3);

		JSONObject json4 = new JSONObject();
		json4.put("code", "Date");
		json4.put("text", "日期型");
		array.add(json4);

		JSONObject json5 = new JSONObject();
		json5.put("code", "String");
		json5.put("text", "字符串型");
		array.add(json5);

		return array.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/sortNo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] sortNo(@Context HttpServletRequest request) throws IOException {
		JSONArray array = new JSONArray();

		JSONObject json0 = new JSONObject();
		json0.put("code", "0");
		json0.put("text", "最前面");
		array.add(json0);

		for (int i = 1; i < 99; i++) {
			JSONObject json = new JSONObject();
			json.put("code", String.valueOf(i));
			json.put("text", String.valueOf(i));
			array.add(json);
		}

		JSONObject json99 = new JSONObject();
		json99.put("code", "99");
		json99.put("text", "最后面");
		array.add(json99);

		return array.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlDefinitionQuery query = new SqlDefinitionQuery();
		Tools.populate(query, params);

		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

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
			limit = PageResult.DEFAULT_PAGE_SIZE;
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
					rowJSON.put("sqlDefinitionId", sqlDefinition.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.remove("sql");
					rowJSON.remove("countSql");
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/required")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] required(@Context HttpServletRequest request) throws IOException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("text", "否");
		array.add(json);

		JSONObject json2 = new JSONObject();
		json2.put("code", "1");
		json2.put("text", "是");
		array.add(json2);

		return array.toString().getBytes("UTF-8");
	}

	@POST
	@Path("/saveSqlDefinition")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSqlDefinition(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
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
			sqlDefinition.setDeleteFetch(request.getParameter("deleteFetch"));
			sqlDefinition.setScheduleFlag(request.getParameter("scheduleFlag"));
			sqlDefinition.setShareFlag(request.getParameter("shareFlag"));
			sqlDefinition.setSaveFlag(request.getParameter("saveFlag"));
			sqlDefinition.setPublicFlag(request.getParameter("publicFlag"));
			sqlDefinition.setFetchFlag(request.getParameter("fetchFlag"));
			sqlDefinition.setExportFlag(request.getParameter("exportFlag"));
			sqlDefinition.setExportTableName(request.getParameter("exportTableName"));
			sqlDefinition.setTargetTableName(request.getParameter("targetTableName"));
			sqlDefinition.setAggregationFlag(request.getParameter("aggregationFlag"));
			sqlDefinition.setCreateBy(loginContext.getActorId());
			sqlDefinition.setUpdateBy(loginContext.getActorId());

			if (!DBUtils.isLegalQuerySql(sqlDefinition.getSql())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			if (!DBUtils.isAllowedSql(sqlDefinition.getSql())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			this.sqlDefinitionService.save(sqlDefinition);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SqlDefinition sqlDefinition = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			sqlDefinition = sqlDefinitionService.getSqlDefinition(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (sqlDefinition != null) {
			result = sqlDefinition.toJsonObject();
			result.put("id", sqlDefinition.getId());
			result.put("sqlDefinitionId", sqlDefinition.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
