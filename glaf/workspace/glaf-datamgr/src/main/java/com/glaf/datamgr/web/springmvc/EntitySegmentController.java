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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.factory.EntityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.bean.MapperParseThread;
import com.glaf.datamgr.domain.EntitySegment;
import com.glaf.datamgr.query.EntitySegmentQuery;
import com.glaf.datamgr.service.EntitySegmentService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/entity")
@RequestMapping("/datamgr/entity")
public class EntitySegmentController {
	protected static final Log logger = LogFactory.getLog(EntitySegmentController.class);

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	protected EntitySegmentService entitySegmentService;

	public EntitySegmentController() {

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		EntitySegment entitySegment = entitySegmentService.getEntitySegment(request.getParameter("id"));
		if (entitySegment != null) {
			request.setAttribute("entitySegment", entitySegment);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("entitySegment.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/entity/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EntitySegmentQuery query = new EntitySegmentQuery();
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
		int total = entitySegmentService.getEntitySegmentCountByQueryCriteria(query);
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

			List<EntitySegment> list = entitySegmentService.getEntitySegmentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EntitySegment entitySegment : list) {
					JSONObject rowJSON = entitySegment.toJsonObject();
					rowJSON.put("id", entitySegment.getId());
					rowJSON.put("rowId", entitySegment.getId());
					rowJSON.put("entitySegmentId", entitySegment.getId());
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

		return new ModelAndView("/datamgr/entity/list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/reload")
	public byte[] reload(HttpServletRequest request, ModelMap modelMap) {
		if (!loading.get()) {
			try {
				loading.set(true);
				EntityFactory.getInstance();
				String path = SystemProperties.getConfigRootPath() + "/conf/mapper";
				File dir = new File(path);
				if (dir.exists() && dir.isDirectory()) {
					List<EntitySegment> list = new ArrayList<EntitySegment>();
					File[] filelist = dir.listFiles();
					if (filelist != null && filelist.length > 0) {
						long ts = 0;
						AtomicInteger total = new AtomicInteger(0);
						AtomicInteger counter = new AtomicInteger(0);
						for (File file : filelist) {
							if (!file.getName().endsWith(".xml")) {
								continue;
							}
							total.incrementAndGet();
							MapperParseThread command = new MapperParseThread(file, list, counter);
							com.glaf.core.util.threads.ThreadFactory.execute(command);
						}
						do {
							ts++;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
						} while (total.get() != counter.get() && ts < 60);
					}

					if (!list.isEmpty()) {
						try {
							List<EntitySegment> bulkInserts = new ArrayList<EntitySegment>();
							List<String> exists = new ArrayList<String>();
							EntitySegmentQuery query = new EntitySegmentQuery();
							List<EntitySegment> rows = entitySegmentService.list(query);
							for (EntitySegment model : rows) {
								exists.add(model.getId());
							}

							int len = list.size();
							for (int index = 0; index < len; index++) {
								EntitySegment bean = list.get(index);
								if (!exists.contains(bean.getId())) {
									bulkInserts.add(bean.clone());
								}
								if (bulkInserts.size() > 0 && bulkInserts.size() % 200 == 0) {
									entitySegmentService.bulkInsert(bulkInserts);
									bulkInserts.clear();
								}
							}

							if (bulkInserts.size() > 0) {
								entitySegmentService.bulkInsert(bulkInserts);
								bulkInserts.clear();
							}

							return ResponseUtils.responseResult(true);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			} finally {
				loading.set(false);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("entitySegment.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/entity/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		EntitySegment entitySegment = new EntitySegment();
		entitySegment.setId(request.getParameter("id"));
		entitySegment.setTitle(request.getParameter("title"));
		entitySegment.setDescription(request.getParameter("description"));
		entitySegment.setLocked(RequestUtils.getInt(request, "locked"));
		entitySegmentService.save(entitySegment);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveEntitySegment")
	public byte[] saveEntitySegment(HttpServletRequest request) {
		EntitySegment entitySegment = new EntitySegment();
		try {
			entitySegment.setId(request.getParameter("id"));
			entitySegment.setTitle(request.getParameter("title"));
			entitySegment.setDescription(request.getParameter("description"));
			entitySegment.setLocked(RequestUtils.getInt(request, "locked"));
			this.entitySegmentService.save(entitySegment);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setEntitySegmentService(EntitySegmentService entitySegmentService) {
		this.entitySegmentService = entitySegmentService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		EntitySegment entitySegment = entitySegmentService.getEntitySegment(request.getParameter("id"));
		entitySegment.setTitle(request.getParameter("title"));
		entitySegment.setDescription(request.getParameter("description"));
		entitySegment.setLocked(RequestUtils.getInt(request, "locked"));
		entitySegmentService.save(entitySegment);
		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		EntitySegment entitySegment = entitySegmentService.getEntitySegment(request.getParameter("id"));
		request.setAttribute("entitySegment", entitySegment);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("entitySegment.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/entity/view");
	}

}
