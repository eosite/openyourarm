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
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.SysDataItem;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryConnectionFactory;
import com.glaf.core.query.SysDataItemQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ISysDataItemService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FieldType;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/system/dataitem")
@RequestMapping("/system/dataitem")
public class SysDataItemController {
	protected static final Log logger = LogFactory.getLog(SysDataItemController.class);

	protected IQueryDefinitionService queryDefinitionService;

	protected ISysDataItemService sysDataItemService;

	public SysDataItemController() {

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
					SysDataItem sysDataItem = sysDataItemService.getSysDataItemById(Long.valueOf(x));

					if (sysDataItem != null && (StringUtils.equals(sysDataItem.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {

						sysDataItemService.deleteById(sysDataItem.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SysDataItem sysDataItem = sysDataItemService.getSysDataItemById(id);
			if (sysDataItem != null && (StringUtils.equals(sysDataItem.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				sysDataItemService.deleteById(sysDataItem.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysDataItem sysDataItem = sysDataItemService.getSysDataItemById(RequestUtils.getLong(request, "id"));
		if (sysDataItem != null) {
			request.setAttribute("sysDataItem", sysDataItem);
			if (StringUtils.isNotEmpty(sysDataItem.getQuerySQL())) {
				if (!DBUtils.isLegalQuerySql(sysDataItem.getQuerySQL())) {
					throw new RuntimeException(" SQL statement illegal ");
				}
				List<ColumnDefinition> cloumns = new ArrayList<ColumnDefinition>();
				Connection conn = null;
				PreparedStatement psmt = null;
				ResultSet rs = null;
				long ts = 0;
				try {
					conn = DBConnectionFactory.getConnection();
					ts = System.currentTimeMillis();
					QueryConnectionFactory.getInstance().register(ts, conn);
					psmt = conn.prepareStatement(sysDataItem.getQuerySQL());
					rs = psmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int count = rsmd.getColumnCount();
					for (int i = 1; i <= count; i++) {
						ColumnDefinition col = new ColumnDefinition();
						col.setName(rsmd.getColumnLabel(i));
						col.setColumnLabel(rsmd.getColumnLabel(i));
						col.setColumnName(rsmd.getColumnName(i));
						col.setDataType(rsmd.getColumnType(i));
						cloumns.add(col);
					}
					request.setAttribute("cloumns", cloumns);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				} finally {
					if (conn != null) {
						QueryConnectionFactory.getInstance().unregister(ts, conn);
					}
					JdbcUtils.close(rs);
					JdbcUtils.close(psmt);
					JdbcUtils.close(conn);
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysDataItem.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dataitem/edit", modelMap);
	}

	@RequestMapping("/editColumn")
	public ModelAndView editColumn(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String id = request.getParameter("id");

		ColumnDefinition columnDefinition = queryDefinitionService.getColumnDefinitionById(id);
		request.setAttribute("columnDefinition", columnDefinition);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysDataItem.editColumn");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dataitem/editColumn", modelMap);
	}

	@RequestMapping("/fields")
	public ModelAndView fields(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysDataItem sysDataItem = sysDataItemService.getSysDataItemById(RequestUtils.getLong(request, "dataItemId"));
		if (sysDataItem != null) {
			String targetId = "sys_dataitem_" + sysDataItem.getName().toLowerCase();
			request.setAttribute("sysDataItem", sysDataItem);
			List<ColumnDefinition> columns = queryDefinitionService.getColumnDefinitions(targetId);
			request.setAttribute("columns", columns);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysDataItem.fields");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dataitem/fields", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataItemQuery query = new SysDataItemQuery();
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

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
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
		int total = sysDataItemService.getSysDataItemCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SysDataItem> list = sysDataItemService.getSysDataItemsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDataItem sysDataItem : list) {
					JSONObject rowJSON = sysDataItem.toJsonObject();
					rowJSON.put("id", sysDataItem.getId());
					rowJSON.put("rowId", sysDataItem.getId());
					rowJSON.put("sysDataItemId", sysDataItem.getId());
					rowJSON.put("startIndex", ++start);
					if (userMap.get(sysDataItem.getCreateBy()) != null) {
						result.put("createByName", userMap.get(sysDataItem.getCreateBy()).getName());
					}
					if (userMap.get(sysDataItem.getUpdateBy()) != null) {
						result.put("updateByName", userMap.get(sysDataItem.getUpdateBy()).getName());
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

		return new ModelAndView("/modules/sys/dataitem/list", modelMap);
	}

	protected void processQuery(SysDataItem sysDataItem) {
		if (!DBUtils.isLegalQuerySql(sysDataItem.getQuerySQL())) {
			throw new RuntimeException(" SQL statement illegal ");
		}
		long ts = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			conn = DBConnectionFactory.getConnection();
			ts = System.currentTimeMillis();
			QueryConnectionFactory.getInstance().register(ts, conn);
			psmt = conn.prepareStatement(sysDataItem.getQuerySQL());
			rs = psmt.executeQuery();
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
			for (int index = 1; index <= count; index++) {
				int sqlType = rsmd.getColumnType(index);
				ColumnDefinition column = new ColumnDefinition();
				column.setIndex(index);
				column.setName(rsmd.getColumnLabel(index));
				column.setColumnName(rsmd.getColumnName(index));
				column.setColumnLabel(rsmd.getColumnLabel(index));
				column.setJavaType(FieldType.getJavaType(sqlType));
				column.setPrecision(rsmd.getPrecision(index));
				column.setScale(rsmd.getScale(index));
				if (column.getScale() == 0 && sqlType == Types.NUMERIC) {
					column.setJavaType("Long");
				}
				columns.add(column);
			}
			sysDataItem.setColumns(columns);
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (conn != null) {
				QueryConnectionFactory.getInstance().unregister(ts, conn);
			}
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sysDataItem.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/dataitem/query", modelMap);
	}
 
	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysDataItem sysDataItem = new SysDataItem();
		Tools.populate(sysDataItem, params);

		sysDataItem.setName(request.getParameter("name"));
		sysDataItem.setTitle(request.getParameter("title"));
		sysDataItem.setQueryId(request.getParameter("queryId"));
		sysDataItem.setQuerySQL(request.getParameter("querySQL"));
		sysDataItem.setParameter(request.getParameter("parameter"));
		sysDataItem.setTextField(request.getParameter("textField"));
		sysDataItem.setValueField(request.getParameter("valueField"));
		sysDataItem.setTreeIdField(request.getParameter("treeIdField"));
		sysDataItem.setTreeParentIdField(request.getParameter("treeParentIdField"));
		sysDataItem.setTreeTreeIdField(request.getParameter("treeTreeIdField"));
		sysDataItem.setTreeNameField(request.getParameter("treeNameField"));
		sysDataItem.setTreeListNoField(request.getParameter("treeListNoField"));
		sysDataItem.setUrl(request.getParameter("url"));
		sysDataItem.setCacheFlag(request.getParameter("cacheFlag"));
		sysDataItem.setLocked(RequestUtils.getInt(request, "locked"));
		sysDataItem.setCreateBy(actorId);
		sysDataItem.setUpdateBy(actorId);
		sysDataItem.setType("USER");
		if (!DBUtils.isLegalQuerySql(sysDataItem.getQuerySQL())) {
			throw new RuntimeException(" SQL statement illegal ");
		}

		this.processQuery(sysDataItem);
		sysDataItemService.save(sysDataItem);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SysDataItem saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SysDataItem sysDataItem = new SysDataItem();
		try {
			Tools.populate(sysDataItem, model);
			sysDataItem.setName(ParamUtils.getString(model, "name"));
			sysDataItem.setTitle(ParamUtils.getString(model, "title"));
			sysDataItem.setQueryId(ParamUtils.getString(model, "queryId"));
			sysDataItem.setQuerySQL(ParamUtils.getString(model, "querySQL"));
			sysDataItem.setParameter(ParamUtils.getString(model, "parameter"));
			sysDataItem.setTextField(ParamUtils.getString(model, "textField"));
			sysDataItem.setValueField(ParamUtils.getString(model, "valueField"));
			sysDataItem.setTreeIdField(ParamUtils.getString(model, "treeIdField"));
			sysDataItem.setTreeParentIdField(ParamUtils.getString(model, "treeParentIdField"));
			sysDataItem.setTreeTreeIdField(ParamUtils.getString(model, "treeTreeIdField"));
			sysDataItem.setTreeNameField(ParamUtils.getString(model, "treeNameField"));
			sysDataItem.setTreeListNoField(ParamUtils.getString(model, "treeListNoField"));
			sysDataItem.setUrl(ParamUtils.getString(model, "url"));
			sysDataItem.setCacheFlag(request.getParameter("cacheFlag"));
			sysDataItem.setCreateBy(actorId);
			sysDataItem.setUpdateBy(actorId);
			sysDataItem.setLocked(RequestUtils.getInt(request, "locked"));
			sysDataItem.setType("USER");

			if (!DBUtils.isLegalQuerySql(sysDataItem.getQuerySQL())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			this.processQuery(sysDataItem);

			this.sysDataItemService.save(sysDataItem);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sysDataItem;
	}

	@ResponseBody
	@RequestMapping("/saveSysDataItem")
	public byte[] saveSysDataItem(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysDataItem sysDataItem = new SysDataItem();
		try {
			Tools.populate(sysDataItem, params);
			sysDataItem.setName(request.getParameter("name"));
			sysDataItem.setTitle(request.getParameter("title"));
			sysDataItem.setQueryId(request.getParameter("queryId"));
			sysDataItem.setQuerySQL(request.getParameter("querySQL"));
			sysDataItem.setParameter(request.getParameter("parameter"));
			sysDataItem.setTextField(request.getParameter("textField"));
			sysDataItem.setValueField(request.getParameter("valueField"));
			sysDataItem.setTreeIdField(request.getParameter("treeIdField"));
			sysDataItem.setTreeParentIdField(request.getParameter("treeParentIdField"));
			sysDataItem.setTreeTreeIdField(request.getParameter("treeTreeIdField"));
			sysDataItem.setTreeNameField(request.getParameter("treeNameField"));
			sysDataItem.setTreeListNoField(request.getParameter("treeListNoField"));
			sysDataItem.setUrl(request.getParameter("url"));
			sysDataItem.setLocked(RequestUtils.getInt(request, "locked"));
			sysDataItem.setCacheFlag(request.getParameter("cacheFlag"));
			sysDataItem.setCreateBy(actorId);
			sysDataItem.setUpdateBy(actorId);
			sysDataItem.setType("USER");

			if (!DBUtils.isLegalQuerySql(sysDataItem.getQuerySQL())) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			this.processQuery(sysDataItem);

			this.sysDataItemService.save(sysDataItem);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	@javax.annotation.Resource
	public void setSysDataItemService(ISysDataItemService sysDataItemService) {
		this.sysDataItemService = sysDataItemService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysDataItem sysDataItem = sysDataItemService.getSysDataItemById(RequestUtils.getLong(request, "id"));

		Tools.populate(sysDataItem, params);

		sysDataItem.setName(request.getParameter("name"));
		sysDataItem.setTitle(request.getParameter("title"));
		sysDataItem.setQueryId(request.getParameter("queryId"));
		sysDataItem.setQuerySQL(request.getParameter("querySQL"));
		sysDataItem.setParameter(request.getParameter("parameter"));
		sysDataItem.setTextField(request.getParameter("textField"));
		sysDataItem.setValueField(request.getParameter("valueField"));
		sysDataItem.setTreeIdField(request.getParameter("treeIdField"));
		sysDataItem.setTreeParentIdField(request.getParameter("treeParentIdField"));
		sysDataItem.setTreeTreeIdField(request.getParameter("treeTreeIdField"));
		sysDataItem.setTreeNameField(request.getParameter("treeNameField"));
		sysDataItem.setTreeListNoField(request.getParameter("treeListNoField"));
		sysDataItem.setUrl(request.getParameter("url"));
		sysDataItem.setUpdateBy(user.getActorId());
		sysDataItem.setLocked(RequestUtils.getInt(request, "locked"));
		sysDataItem.setCacheFlag(request.getParameter("cacheFlag"));

		if (!DBUtils.isLegalQuerySql(sysDataItem.getQuerySQL())) {
			throw new RuntimeException(" SQL statement illegal ");
		}

		this.processQuery(sysDataItem);

		sysDataItemService.save(sysDataItem);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/updateColumn")
	public byte[] updateColumn(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String id = request.getParameter("id");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ColumnDefinition columnDefinition = null;
		try {
			columnDefinition = queryDefinitionService.getColumnDefinitionById(id);
			if (columnDefinition != null) {
				Tools.populate(columnDefinition, params);
				sysDataItemService.updateColumn(columnDefinition);
				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);

	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SysDataItem sysDataItem = sysDataItemService.getSysDataItemById(RequestUtils.getLong(request, "id"));
		request.setAttribute("sysDataItem", sysDataItem);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysDataItem.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/dataitem/view");
	}

}
