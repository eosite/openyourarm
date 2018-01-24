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
package com.glaf.core.web.springmvc;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.core.domain.*;
import com.glaf.core.domain.util.ModulePropertyDomainFactory;
import com.glaf.core.query.*;
import com.glaf.core.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */
@Controller("/sys/moduleProperty")
@RequestMapping("/sys/moduleProperty")
public class ModulePropertyController {
	protected static final Log logger = LogFactory.getLog(ModulePropertyController.class);

	protected ModulePropertyService modulePropertyService;

	public ModulePropertyController() {

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {

		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		String locale = request.getParameter("locale");
		if (StringUtils.isEmpty(locale)) {
			locale = "zh_CN";
		}

		List<ModuleProperty> list = modulePropertyService.getModuleProperties("SYS", locale);
		request.setAttribute("categories", list);

		ModuleProperty moduleProperty = modulePropertyService.getModuleProperty(request.getParameter("id"));
		if (moduleProperty != null) {
			request.setAttribute("moduleProperty", moduleProperty);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("moduleProperty.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/moduleProperty/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ModulePropertyQuery query = new ModulePropertyQuery();
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
		int total = modulePropertyService.getModulePropertyCountByQueryCriteria(query);
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

			List<ModuleProperty> list = modulePropertyService.getModulePropertiesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ModuleProperty moduleProperty : list) {
					JSONObject rowJSON = moduleProperty.toJsonObject();
					rowJSON.put("id", moduleProperty.getId());
					rowJSON.put("rowId", moduleProperty.getId());
					rowJSON.put("modulePropertyId", moduleProperty.getId());
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
	
		String locale = request.getParameter("locale");
		if (StringUtils.isEmpty(locale)) {
			locale = "zh_CN";
		}

		List<ModuleProperty> list = modulePropertyService.getModuleProperties("SYS", locale);
		request.setAttribute("categories", list);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/moduleProperty/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("moduleProperty.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/moduleProperty/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ModulePropertyQuery query = new ModulePropertyQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ModulePropertyDomainFactory.processDataRequest(dataRequest);

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
		int total = modulePropertyService.getModulePropertyCountByQueryCriteria(query);
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

			List<ModuleProperty> list = modulePropertyService.getModulePropertiesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ModuleProperty moduleProperty : list) {
					JSONObject rowJSON = moduleProperty.toJsonObject();
					rowJSON.put("id", moduleProperty.getId());
					rowJSON.put("modulePropertyId", moduleProperty.getId());
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
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ModuleProperty moduleProperty = new ModuleProperty();
		Tools.populate(moduleProperty, params);

		moduleProperty.setCategory(request.getParameter("category"));
		moduleProperty.setName(request.getParameter("name"));
		moduleProperty.setType(request.getParameter("type"));
		moduleProperty.setTitle(request.getParameter("title"));
		moduleProperty.setValue(request.getParameter("value"));
		moduleProperty.setLocked(RequestUtils.getInt(request, "locked"));
		moduleProperty.setCreateBy(actorId);
		moduleProperty.setUpdateBy(actorId);

		modulePropertyService.save(moduleProperty);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveModuleProperty")
	public byte[] saveModuleProperty(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ModuleProperty moduleProperty = new ModuleProperty();
		try {
			Tools.populate(moduleProperty, params);
			moduleProperty.setCategory(request.getParameter("category"));
			moduleProperty.setName(request.getParameter("name"));
			moduleProperty.setType(request.getParameter("type"));
			moduleProperty.setTitle(request.getParameter("title"));
			moduleProperty.setValue(request.getParameter("value"));
			moduleProperty.setLocked(RequestUtils.getInt(request, "locked"));
			moduleProperty.setCreateBy(request.getParameter("createBy"));
			moduleProperty.setCreateBy(actorId);
			moduleProperty.setUpdateBy(actorId);
			this.modulePropertyService.save(moduleProperty);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ModuleProperty saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		ModuleProperty moduleProperty = new ModuleProperty();
		try {
			Tools.populate(moduleProperty, model);
			moduleProperty.setCategory(ParamUtils.getString(model, "category"));
			moduleProperty.setName(ParamUtils.getString(model, "name"));
			moduleProperty.setType(ParamUtils.getString(model, "type"));
			moduleProperty.setTitle(ParamUtils.getString(model, "title"));
			moduleProperty.setValue(ParamUtils.getString(model, "value"));
			moduleProperty.setLocked(ParamUtils.getInt(model, "locked"));
			moduleProperty.setCreateBy(actorId);
			moduleProperty.setUpdateBy(actorId);
			moduleProperty.setCreateBy(actorId);
			this.modulePropertyService.save(moduleProperty);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return moduleProperty;
	}

	@javax.annotation.Resource
	public void setModulePropertyService(ModulePropertyService modulePropertyService) {
		this.modulePropertyService = modulePropertyService;
	}

	@RequestMapping("/showUpload")
	public ModelAndView showUpload(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String locale = request.getParameter("locale");
		if (StringUtils.isEmpty(locale)) {
			locale = "zh_CN";
		}

		List<ModuleProperty> list = modulePropertyService.getModuleProperties("SYS", locale);
		request.setAttribute("categories", list);

		String view = request.getParameter("view");
		if (StringUtils.isEmpty(view)) {
			view = "/modules/sys/moduleProperty/showUpload";
		}

		return new ModelAndView(view, modelMap);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ModuleProperty moduleProperty = modulePropertyService.getModuleProperty(request.getParameter("id"));

		Tools.populate(moduleProperty, params);

		moduleProperty.setCategory(request.getParameter("category"));
		moduleProperty.setName(request.getParameter("name"));
		moduleProperty.setType(request.getParameter("type"));
		moduleProperty.setTitle(request.getParameter("title"));
		moduleProperty.setValue(request.getParameter("value"));
		moduleProperty.setLocked(RequestUtils.getInt(request, "locked"));
		moduleProperty.setCreateBy(actorId);
		moduleProperty.setUpdateBy(actorId);

		modulePropertyService.save(moduleProperty);

		return this.list(request, modelMap);
	}

	@RequestMapping("/upload")
	public ModelAndView upload(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			String type = req.getParameter("type");
			if (StringUtils.isEmpty(type)) {
				type = "0";
			}

			String category = req.getParameter("category");
			if (StringUtils.isEmpty(category)) {
				modelMap.put("error_message", "您没有提供必要的信息，分类是必须的！");
				return new ModelAndView("/error");
			}

			String locale = req.getParameter("locale");
			if (StringUtils.isEmpty(locale)) {
				locale = "zh_CN";
			}

			try {
				List<ModuleProperty> list = modulePropertyService.getModuleProperties(category, locale);
				Map<String, String> existMap = new HashMap<String, String>();
				if (list != null && !list.isEmpty()) {
					for (ModuleProperty p : list) {
						existMap.put(p.getName(), p.getValue());
					}
				}
				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
						if (StringUtils.endsWith(mFile.getOriginalFilename(), ".properties")) {
							List<ModuleProperty> rows = new ArrayList<ModuleProperty>();
							LinkedHashMap<String, String> dataMap = PropertiesUtils.load(mFile.getInputStream());
							Set<Entry<String, String>> entrySet2 = dataMap.entrySet();
							for (Entry<String, String> entry2 : entrySet2) {
								String key = entry2.getKey();
								String value = entry2.getValue();
								if (existMap.get(key) == null) {
									ModuleProperty p = new ModuleProperty();
									p.setCategory(category);
									p.setCreateBy(loginContext.getActorId());
									p.setLocale(locale);
									p.setLocked(0);
									p.setName(key);
									p.setValue(value);
									p.setType(type);
									if (StringUtils.equals(category, "jobs")) {
										p.setTitle(value);
										p.setValue(key);
									} else if (StringUtils.equals(category, "parser")) {
										p.setTitle(value);
										p.setValue(key);
									}
									rows.add(p);
								}
							}
							if (!rows.isEmpty()) {
								modulePropertyService.bulkInsert(rows);
							}
						}
					}
				}
			} catch (Exception ex) {
				logger.debug(ex);
			}
		}
		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		ModuleProperty moduleProperty = modulePropertyService.getModuleProperty(request.getParameter("id"));
		request.setAttribute("moduleProperty", moduleProperty);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("moduleProperty.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/sys/moduleProperty/view");
	}

}
