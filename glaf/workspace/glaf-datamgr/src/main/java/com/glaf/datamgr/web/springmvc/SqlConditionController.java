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
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.domain.SqlCondition;
import com.glaf.datamgr.query.SqlConditionQuery;
import com.glaf.datamgr.service.SqlConditionService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/sql/condition")
@RequestMapping("/datamgr/sql/condition")
public class SqlConditionController {
	protected static final Log logger = LogFactory.getLog(SqlConditionController.class);

	protected SqlConditionService sqlConditionService;

	public SqlConditionController() {

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
					SqlCondition sqlCondition = sqlConditionService.getSqlCondition(Long.valueOf(x));

					if (sqlCondition != null
							&& (StringUtils.equals(sqlCondition.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						sqlCondition.setDeleteFlag(1);
						sqlConditionService.save(sqlCondition);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SqlCondition sqlCondition = sqlConditionService.getSqlCondition(Long.valueOf(id));

			if (sqlCondition != null && (StringUtils.equals(sqlCondition.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				sqlCondition.setDeleteFlag(1);
				sqlConditionService.save(sqlCondition);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		SqlCondition sqlCondition = sqlConditionService.getSqlCondition(RequestUtils.getLong(request, "id"));
		if (sqlCondition != null) {
			request.setAttribute("sqlCondition", sqlCondition);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sqlCondition.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/sql/condition/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlConditionQuery query = new SqlConditionQuery();
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
		int total = sqlConditionService.getSqlConditionCountByQueryCriteria(query);
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

			List<SqlCondition> list = sqlConditionService.getSqlConditionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlCondition sqlCondition : list) {
					JSONObject rowJSON = sqlCondition.toJsonObject();
					rowJSON.put("id", sqlCondition.getId());
					rowJSON.put("rowId", sqlCondition.getId());
					rowJSON.put("sqlConditionId", sqlCondition.getId());
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

		return new ModelAndView("/datamgr/sql/condition/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sqlCondition.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/sql/condition/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SqlCondition sqlCondition = new SqlCondition();
		Tools.populate(sqlCondition, params);

		sqlCondition.setSqlDefId(RequestUtils.getLong(request, "sqlDefId"));
		sqlCondition.setName(request.getParameter("name"));
		sqlCondition.setType(request.getParameter("type"));
		sqlCondition.setRequired(request.getParameter("required"));
		sqlCondition.setCollection(request.getParameter("collection"));
		sqlCondition.setOperator(request.getParameter("operator"));
		sqlCondition.setSegment(request.getParameter("segment"));
		sqlCondition.setLocked(RequestUtils.getInt(request, "locked"));
		sqlCondition.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		sqlCondition.setCreateBy(actorId);
		sqlCondition.setUpdateBy(actorId);

		sqlConditionService.save(sqlCondition);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SqlCondition saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SqlCondition sqlCondition = new SqlCondition();
		try {
			Tools.populate(sqlCondition, model);
			sqlCondition.setSqlDefId(ParamUtils.getLong(model, "sqlDefId"));
			sqlCondition.setName(ParamUtils.getString(model, "name"));
			sqlCondition.setType(ParamUtils.getString(model, "type"));
			sqlCondition.setRequired(ParamUtils.getString(model, "required"));
			sqlCondition.setCollection(ParamUtils.getString(model, "collection"));
			sqlCondition.setOperator(ParamUtils.getString(model, "operator"));
			sqlCondition.setSegment(ParamUtils.getString(model, "segment"));
			sqlCondition.setLocked(ParamUtils.getInt(model, "locked"));
			sqlCondition.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			sqlCondition.setCreateBy(actorId);
			sqlCondition.setUpdateBy(actorId);
			this.sqlConditionService.save(sqlCondition);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sqlCondition;
	}

	@ResponseBody
	@RequestMapping("/saveSqlCondition")
	public byte[] saveSqlCondition(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlCondition sqlCondition = new SqlCondition();
		try {
			Tools.populate(sqlCondition, params);
			sqlCondition.setSqlDefId(RequestUtils.getLong(request, "sqlDefId"));
			sqlCondition.setName(request.getParameter("name"));
			sqlCondition.setType(request.getParameter("type"));
			sqlCondition.setRequired(request.getParameter("required"));
			sqlCondition.setCollection(request.getParameter("collection"));
			sqlCondition.setOperator(request.getParameter("operator"));
			sqlCondition.setSegment(request.getParameter("segment"));
			sqlCondition.setLocked(RequestUtils.getInt(request, "locked"));
			sqlCondition.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			sqlCondition.setCreateBy(actorId);
			sqlCondition.setUpdateBy(actorId);
			this.sqlConditionService.save(sqlCondition);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSqlConditionService(SqlConditionService sqlConditionService) {
		this.sqlConditionService = sqlConditionService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SqlCondition sqlCondition = sqlConditionService.getSqlCondition(RequestUtils.getLong(request, "id"));

		Tools.populate(sqlCondition, params);

		sqlCondition.setSqlDefId(RequestUtils.getLong(request, "sqlDefId"));
		sqlCondition.setName(request.getParameter("name"));
		sqlCondition.setType(request.getParameter("type"));
		sqlCondition.setRequired(request.getParameter("required"));
		sqlCondition.setCollection(request.getParameter("collection"));
		sqlCondition.setOperator(request.getParameter("operator"));
		sqlCondition.setSegment(request.getParameter("segment"));
		sqlCondition.setLocked(RequestUtils.getInt(request, "locked"));
		sqlCondition.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		sqlCondition.setUpdateBy(user.getActorId());

		sqlConditionService.save(sqlCondition);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SqlCondition sqlCondition = sqlConditionService.getSqlCondition(RequestUtils.getLong(request, "id"));
		request.setAttribute("sqlCondition", sqlCondition);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sqlCondition.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/sql/condition/view");
	}

}
