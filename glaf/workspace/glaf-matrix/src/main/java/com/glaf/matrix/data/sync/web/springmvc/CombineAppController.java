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

package com.glaf.matrix.data.sync.web.springmvc;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.matrix.data.sync.bean.CombineSyncBean;
import com.glaf.matrix.data.sync.domain.CombineApp;
import com.glaf.matrix.data.sync.domain.CombineHistory;
import com.glaf.matrix.data.sync.query.CombineAppQuery;
import com.glaf.matrix.data.sync.service.CombineAppService;
import com.glaf.matrix.data.sync.service.CombineHistoryService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/combineApp")
@RequestMapping("/sys/combineApp")
public class CombineAppController {
	protected static final Log logger = LogFactory.getLog(CombineAppController.class);

	protected IDatabaseService databaseService;

	protected CombineAppService combineAppService;

	protected CombineHistoryService combineHistoryService;

	public CombineAppController() {

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
					CombineApp combineApp = combineAppService.getCombineApp(Long.valueOf(x));
					if (combineApp != null && (StringUtils.equals(combineApp.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {

					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			CombineApp combineApp = combineAppService.getCombineApp(Long.valueOf(id));
			if (combineApp != null && (StringUtils.equals(combineApp.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {

				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		CombineApp combineApp = combineAppService.getCombineApp(RequestUtils.getLong(request, "id"));
		if (combineApp != null) {
			request.setAttribute("combineApp", combineApp);
		}

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
		List<Database> activeDatabases = cfg.getActiveDatabases(loginContext);
		if (activeDatabases != null && !activeDatabases.isEmpty()) {
			if (combineApp != null && StringUtils.isNotEmpty(combineApp.getTargetDatabaseIds())) {
				List<Long> databaseIds = StringTools.splitToLong(combineApp.getTargetDatabaseIds().trim());
				for (Database db : activeDatabases) {
					if (databaseIds.contains(db.getId())) {
						db.setSelected("selected");
					} else {
						db.setSelected("");
					}
				}
			}
		}
		request.setAttribute("databases", activeDatabases);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("combineApp.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/combineApp/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long combineAppId = RequestUtils.getLong(request, "combineId");
		CombineSyncBean combineSyncBean = new CombineSyncBean();
		Database database = null;
		try {
			CombineApp combineApp = combineAppService.getCombineApp(combineAppId);
			if (combineApp != null && StringUtils.equals(combineApp.getActive(), "Y")) {
				List<Long> targetDBIds = StringTools.splitToLong(combineApp.getTargetDatabaseIds());
				for (Long targetDatabaseId : targetDBIds) {
					database = databaseService.getDatabaseById(targetDatabaseId);
					if (database != null) {
						long start = System.currentTimeMillis();
						String jobNo = "job_" + start;
						int total = combineSyncBean.execute(combineApp.getSrcDatabaseId(), targetDatabaseId, combineApp,
								jobNo);
						if (total > 0) {
							CombineHistory his = new CombineHistory();
							his.setCreateBy(loginContext.getActorId());
							his.setDatabaseId(targetDatabaseId);
							his.setCombineId(combineApp.getId());
							his.setStatus(1);
							his.setTotal(total);
							his.setJobNo(jobNo);
							his.setTotalTime((int) (System.currentTimeMillis() - start));
							his.setDatabaseName(
									database.getTitle() + "[" + database.getHost() + ":" + database.getUser() + "]");
							combineHistoryService.save(his);
						}
					}
				}
				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CombineAppQuery query = new CombineAppQuery();
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
		int total = combineAppService.getCombineAppCountByQueryCriteria(query);
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

			List<CombineApp> list = combineAppService.getCombineAppsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CombineApp combineApp : list) {
					JSONObject rowJSON = combineApp.toJsonObject();
					rowJSON.put("id", combineApp.getId());
					rowJSON.put("rowId", combineApp.getId());
					rowJSON.put("combineAppId", combineApp.getId());
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

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/matrix/combineApp/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("combineApp.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/matrix/combineApp/query", modelMap);
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CombineApp combineApp = new CombineApp();
		try {
			Tools.populate(combineApp, params);
			combineApp.setTitle(request.getParameter("title"));
			combineApp.setSourceTableName(request.getParameter("sourceTableName"));
			combineApp.setTargetTableName(request.getParameter("targetTableName"));
			combineApp.setSrcDatabaseId(RequestUtils.getLong(request, "srcDatabaseId"));
			combineApp.setTargetDatabaseIds(request.getParameter("targetDatabaseIds"));
			combineApp.setExcludeColumns(request.getParameter("excludeColumns"));
			combineApp.setSqlCriteria(request.getParameter("sqlCriteria"));
			combineApp.setType(request.getParameter("type"));
			combineApp.setAutoSyncFlag(request.getParameter("autoSyncFlag"));
			combineApp.setInterval(RequestUtils.getInt(request, "interval"));
			combineApp.setActive(request.getParameter("active"));
			combineApp.setCreateBy(actorId);
			combineApp.setUpdateBy(actorId);

			String[] targetDatabaseIds = request.getParameterValues("targetDatabaseIds");
			if (targetDatabaseIds != null && targetDatabaseIds.length > 0) {
				StringBuilder buff = new StringBuilder();
				for (String targetDatabaseId : targetDatabaseIds) {
					buff.append(targetDatabaseId).append(", ");
				}
				combineApp.setTargetDatabaseIds(buff.toString());
			}

			this.combineAppService.save(combineApp);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.data.sync.service.combineAppService")
	public void setCombineAppService(CombineAppService combineAppService) {
		this.combineAppService = combineAppService;
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.data.sync.service.combineHistoryService")
	public void setCombineHistoryService(CombineHistoryService combineHistoryService) {
		this.combineHistoryService = combineHistoryService;
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

}
