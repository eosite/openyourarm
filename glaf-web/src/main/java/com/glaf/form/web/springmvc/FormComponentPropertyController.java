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

package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormComponentProperty;
import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.query.FormComponentPropertyQuery;
import com.glaf.form.core.query.FormDictTreeQuery;
import com.glaf.form.core.service.FormComponentPropertyService;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.util.FormComponentPropertyDomainFactory;
import com.glaf.form.core.util.FormDictoryFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/form/component/property")
@RequestMapping("/form/component/property")
public class FormComponentPropertyController {
	protected static final Log logger = LogFactory
			.getLog(FormComponentPropertyController.class);

	protected FormComponentService formComponentService;

	protected FormComponentPropertyService formComponentPropertyService;

	public FormComponentPropertyController() {

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
					FormComponentProperty formComponentProperty = formComponentPropertyService
							.getFormComponentProperty(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (formComponentProperty != null
							&& (StringUtils.equals(
									formComponentProperty.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						formComponentProperty.setLocked(1);
						formComponentProperty.setDeleteFlag(1);
						formComponentPropertyService
								.save(formComponentProperty);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormComponentProperty formComponentProperty = formComponentPropertyService
					.getFormComponentProperty(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (formComponentProperty != null
					&& (StringUtils.equals(formComponentProperty.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				formComponentProperty.setLocked(1);
				formComponentProperty.setDeleteFlag(1);
				formComponentPropertyService.save(formComponentProperty);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		// RequestUtils.setRequestParameterToAttribute(request);

		modelMap.putAll(params);

		FormComponentProperty formComponentProperty = formComponentPropertyService
				.getFormComponentProperty(RequestUtils.getLong(request, "id"));

		Long componentId = RequestUtils.getLong(request, "componentId");

		if (formComponentProperty != null) {

			modelMap.put("formComponentProperty", formComponentProperty);

			componentId = formComponentProperty.getComponentId();

			if (formComponentProperty.getParentId() != null) {
				formComponentProperty = formComponentPropertyService
						.getFormComponentProperty(Long
								.parseLong(formComponentProperty.getParentId()
										+ ""));
				if (formComponentProperty !=null && StringUtils
						.isNotEmpty(formComponentProperty.getEnumValue())) {

					// List<Dictory> pValues = dictoryService
					// .getDictoryList(Long.parseLong(formComponentProperty.getEnumValue()));
					// modelMap.put("pValues", pValues);

					List<FormDictory> dicts = FormDictoryFactory
							.getInstance()
							.getFormDictoryService()
							.getAvailableDictoryList(
									Long.parseLong(formComponentProperty
											.getEnumValue()));
					modelMap.put("pValues", dicts);

				}
			}
		}

		if (componentId != null) {
			FormComponentPropertyQuery query = new FormComponentPropertyQuery();
			query.setComponentId(componentId);
			List<FormComponentProperty> list = formComponentPropertyService
					.list(query);
			modelMap.put("props", list);
		}

		// List<Dictory> dictories =
		// dictoryService.getDictoryList("form_editor");
		List<FormDictory> dictories = FormDictoryFactory.getInstance()
				.getFormDictoryListByTreeCode("form_editor");
		request.setAttribute("dictories", dictories);

		// List<Dictory> property_sorts =
		// dictoryService.getDictoryList("property_sort");
		List<FormDictory> property_sorts = FormDictoryFactory.getInstance()
				.getFormDictoryListByTreeCode("property_sort");
		request.setAttribute("property_sort", property_sorts);

		// SysTreeQuery query = new SysTreeQuery();
		// query.setLocked(0);
		// List<SysTree> trees = sysTreeService.getDictorySysTrees(query);
		FormDictTreeQuery query = new FormDictTreeQuery();
		List<FormDictTree> trees = FormDictoryFactory.getInstance()
				.getFormDictTreeService().getDictoryFormDictTrees(query);
		request.setAttribute("categories", trees);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formComponentProperty.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/component/property/edit", modelMap);
	}

	@RequestMapping("/dict")
	@ResponseBody
	public byte[] dict(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		Long id = RequestUtils.getLong(request, "id");

		if (id != null && id > 0) {
			// List<Dictory> LIST = dictoryService.getDictoryList(id);
			List<FormDictory> LIST = FormDictoryFactory.getInstance()
					.getFormDictoryService().getFormDictoryList(id);
			return JSON.toJSONString(LIST).getBytes("UTF-8");
		}

		return "".getBytes("UTF-8");
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentPropertyQuery query = new FormComponentPropertyQuery();
		Tools.populate(query, params);
		query.setLocked(0);
		query.setDeleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		String kendoComponent = request.getParameter("kendoComponent");
		query.kendoComponent(kendoComponent);
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
		int total = formComponentPropertyService
				.getFormComponentPropertyCountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<FormComponentProperty> list = formComponentPropertyService
					.getFormComponentPropertysByQueryCriteria(start, limit,
							query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormComponentProperty formComponentProperty : list) {
					JSONObject rowJSON = formComponentProperty.toJsonObject();
					rowJSON.put("id", formComponentProperty.getId());
					rowJSON.put("rowId", formComponentProperty.getId());
					rowJSON.put("formComponentPropertyId",
							formComponentProperty.getId());
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

		long componentId = RequestUtils.getLong(request, "componentId");
		if (componentId > 0) {
			FormComponent formComponent = formComponentService
					.getFormComponent(componentId);
			if (formComponent != null) {
				request.setAttribute("formComponent", formComponent);
			}
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/form/component/property/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formComponentProperty.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/component/property/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentPropertyQuery query = new FormComponentPropertyQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormComponentPropertyDomainFactory.processDataRequest(dataRequest);

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
		int total = formComponentPropertyService
				.getFormComponentPropertyCountByQueryCriteria(query);
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

			if (dataRequest.getSort() != null
					&& !dataRequest.getSort().isEmpty()) {
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<FormComponentProperty> list = formComponentPropertyService
					.getFormComponentPropertysByQueryCriteria(start, limit,
							query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormComponentProperty formComponentProperty : list) {
					JSONObject rowJSON = formComponentProperty.toJsonObject();
					rowJSON.put("id", formComponentProperty.getId());
					rowJSON.put("formComponentPropertyId",
							formComponentProperty.getId());
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

		FormComponentProperty formComponentProperty = new FormComponentProperty();
		Tools.populate(formComponentProperty, params);

		formComponentProperty.setComponentId(RequestUtils.getLong(request,
				"componentId"));
		formComponentProperty.setDeploymentId(request
				.getParameter("deploymentId"));
		formComponentProperty.setName(request.getParameter("name"));
		formComponentProperty.setDataType(request.getParameter("dataType"));
		formComponentProperty.setType(request.getParameter("type"));
		formComponentProperty.setCategory(request.getParameter("category"));
		formComponentProperty.setKendoComponent(request
				.getParameter("kendoComponent"));
		formComponentProperty.setKendoMapping(request
				.getParameter("kendoMapping"));
		formComponentProperty.setTitle(request.getParameter("title"));
		formComponentProperty.setDesc(request.getParameter("desc"));
		formComponentProperty.setValue(request.getParameter("value"));
		formComponentProperty.setEnumValue(request.getParameter("enumValue"));
		formComponentProperty.setEditor(request.getParameter("editor"));
		formComponentProperty.setValidator(request.getParameter("validator"));
		formComponentProperty.setLocked(RequestUtils.getInt(request, "locked"));
		formComponentProperty.setDeleteFlag(RequestUtils.getInt(request,
				"deleteFlag"));

		formComponentProperty.setCreateBy(actorId);
		formComponentProperty.setUpdateBy(actorId);

		formComponentPropertyService.save(formComponentProperty);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormComponentProperty")
	public byte[] saveFormComponentProperty(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentProperty formComponentProperty = new FormComponentProperty();
		try {
			Tools.populate(formComponentProperty, params);
			formComponentProperty.setComponentId(RequestUtils.getLong(request,
					"componentId"));
			formComponentProperty.setDeploymentId(request
					.getParameter("deploymentId"));
			formComponentProperty.setName(request.getParameter("name"));
			formComponentProperty.setDataType(request.getParameter("dataType"));
			formComponentProperty.setType(request.getParameter("type"));
			formComponentProperty.setCategory(request.getParameter("category"));
			formComponentProperty.setKendoComponent(request
					.getParameter("kendoComponent"));
			formComponentProperty.setKendoMapping(request
					.getParameter("kendoMapping"));
			formComponentProperty.setTitle(request.getParameter("title"));
			formComponentProperty.setDesc(request.getParameter("desc"));
			formComponentProperty.setValue(request.getParameter("value"));

			formComponentProperty.setEnumValue(request
					.getParameter("enumValue"));
			formComponentProperty.setEditor(request.getParameter("editor"));
			formComponentProperty.setValidator(request
					.getParameter("validator"));
			formComponentProperty.setLocked(RequestUtils.getInt(request,
					"locked"));
			formComponentProperty.setDeleteFlag(RequestUtils.getInt(request,
					"deleteFlag"));

			formComponentProperty.setCreateBy(actorId);
			formComponentProperty.setUpdateBy(actorId);
			this.formComponentPropertyService.save(formComponentProperty);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody
	FormComponentProperty saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormComponentProperty formComponentProperty = new FormComponentProperty();
		try {
			Tools.populate(formComponentProperty, model);
			formComponentProperty.setComponentId(RequestUtils.getLong(request,
					"componentId"));
			formComponentProperty.setDeploymentId(ParamUtils.getString(model,
					"deploymentId"));
			formComponentProperty.setName(ParamUtils.getString(model, "name"));
			formComponentProperty.setDataType(ParamUtils.getString(model,
					"dataType"));
			formComponentProperty.setType(ParamUtils.getString(model, "type"));
			formComponentProperty.setCategory(ParamUtils.getString(model,
					"category"));
			formComponentProperty.setKendoComponent(ParamUtils.getString(model,
					"kendoComponent"));
			formComponentProperty.setKendoMapping(ParamUtils.getString(model,
					"kendoMapping"));
			formComponentProperty
					.setTitle(ParamUtils.getString(model, "title"));
			formComponentProperty.setDesc(ParamUtils.getString(model, "desc"));
			formComponentProperty
					.setValue(ParamUtils.getString(model, "value"));

			formComponentProperty.setEnumValue(request
					.getParameter("enumValue"));
			formComponentProperty.setEditor(request.getParameter("editor"));
			formComponentProperty.setValidator(request
					.getParameter("validator"));
			formComponentProperty.setLocked(RequestUtils.getInt(request,
					"locked"));
			formComponentProperty.setDeleteFlag(RequestUtils.getInt(request,
					"deleteFlag"));

			formComponentProperty.setCreateBy(actorId);
			formComponentProperty.setUpdateBy(actorId);
			this.formComponentPropertyService.save(formComponentProperty);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formComponentProperty;
	}

	@RequestMapping("/importProperties")
	public ModelAndView importProperties(HttpServletRequest request,
			ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		modelMap.putAll(params);
		return new ModelAndView("/form/component/property/import", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveImportProperties")
	public byte[] saveImportProperties(HttpServletRequest request) {

		String ids = RequestUtils.getString(request, "ids");
		if (ids != null) {
			String kendoComponent = RequestUtils.getString(request,
					"kendoComponent");
			Long componentId = RequestUtils.getLong(request, "componentId");
			FormComponentPropertyQuery query = new FormComponentPropertyQuery();
			query.setComponentId(componentId);
			List<FormComponentProperty> list0 = formComponentPropertyService
					.list(query);
			Map<String, FormComponentProperty> map = new HashMap<String, FormComponentProperty>();
			if (list0 != null) {
				for (FormComponentProperty fcp : list0) {
					map.put(fcp.getName(), fcp);
				}
			}

			List<Long> idList = new ArrayList<Long>();
			for (String id : ids.split(",")) {
				idList.add(Long.parseLong(id));
			}
			query = new FormComponentPropertyQuery();
			query.setIds(idList);
			List<FormComponentProperty> list = formComponentPropertyService
					.list(query);
			if (list != null) {
				User user = RequestUtils.getUser(request);
				String actorId = user.getActorId();
				for (FormComponentProperty fcp : list) {
					if (map.containsKey(fcp.getName()))
						continue;
					fcp.setId(0);
					fcp.setParentId(null);
					fcp.setComponentId(componentId);
					fcp.setKendoComponent(kendoComponent);
					fcp.setCreateDate(new Date());
					fcp.setCreateBy(actorId);

					this.formComponentPropertyService.save(fcp);
				}
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@ResponseBody
	@RequestMapping("/upsidedown")
	public byte[] upsidedown(HttpServletRequest request) {

		Long componentId = RequestUtils.getLong(request, "componentId");
		Long id = RequestUtils.getLong(request, "id");
		String type = RequestUtils.getString(request, "type");
		FormComponentPropertyQuery query = new FormComponentPropertyQuery();
		query.setComponentId(componentId);
		query.setOrderBy("listno_");
		query.setSortOrder("desc");
		Long parentId = RequestUtils.getLong(request, "parentId");
		if (parentId > 0 && parentId < 99000000)
			query.setParentId(parentId);
		List<FormComponentProperty> list0 = formComponentPropertyService
				.list(query);
		FormComponentProperty fcp = null;
		FormComponentProperty fcp0 = null;
		FormComponentProperty fcp1 = null;
		int size = list0.size();
		for (int i = 0; i < size; i++) {
			fcp = list0.get(i);
			fcp.setListNo(size - i);
			formComponentPropertyService.save(fcp);
			if (fcp.getId() == id) {
				if ("up".equalsIgnoreCase(type)) {
					if (i != 0) {
						fcp0 = list0.get(i - 1);// 获取上一条数据
					}
				} else {
					if (i != (size - 1)) {
						fcp0 = list0.get(i + 1);// 获取下一条数据
					}
				}
				fcp1 = fcp;
			}
		}
		if (fcp1 != null && fcp0 != null)
			this.exchangeListNo(fcp1, fcp0);
		return null;
	}

	public void exchangeListNo(FormComponentProperty fcp1,
			FormComponentProperty fcp0) {
		Integer listNo0 = fcp0.getListNo();
		fcp0.setListNo(fcp1.getListNo());
		fcp1.setListNo(listNo0);
		formComponentPropertyService.save(fcp1);
		formComponentPropertyService.save(fcp0);
	}

	@ResponseBody
	@RequestMapping("/sortListFunc")
	public byte[] sortListFunc(HttpServletRequest request) {

		String ids = RequestUtils.getString(request, "ids");

		Integer parentId = RequestUtils.getInteger(request, "parentId");

		if (parentId != null && ids != null) {
			for (String id : ids.split(",")) {
				FormComponentProperty formComponentProperty = new FormComponentProperty();
				formComponentProperty.setId(Long.parseLong(id));
				formComponentProperty.setParentId(parentId);
				this.formComponentPropertyService.save(formComponentProperty);
			}

		}

		return null;
	}

	@javax.annotation.Resource
	public void setFormComponentPropertyService(
			FormComponentPropertyService formComponentPropertyService) {
		this.formComponentPropertyService = formComponentPropertyService;
	}

	@javax.annotation.Resource
	public void setFormComponentService(
			FormComponentService formComponentService) {
		this.formComponentService = formComponentService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();

		FormComponentProperty formComponentProperty = formComponentPropertyService
				.getFormComponentProperty(RequestUtils.getLong(request, "id"));

		Tools.populate(formComponentProperty, params);

		formComponentProperty.setComponentId(RequestUtils.getLong(request,
				"componentId"));
		formComponentProperty.setDeploymentId(request
				.getParameter("deploymentId"));
		formComponentProperty.setName(request.getParameter("name"));
		formComponentProperty.setDataType(request.getParameter("dataType"));
		formComponentProperty.setType(request.getParameter("type"));
		formComponentProperty.setKendoComponent(request
				.getParameter("kendoComponent"));
		formComponentProperty.setKendoMapping(request
				.getParameter("kendoMapping"));
		formComponentProperty.setTitle(request.getParameter("title"));
		formComponentProperty.setDesc(request.getParameter("desc"));
		formComponentProperty.setValue(request.getParameter("value"));
		formComponentProperty.setEnumValue(request.getParameter("enumValue"));
		formComponentProperty.setEditor(request.getParameter("editor"));
		formComponentProperty.setValidator(request.getParameter("validator"));
		formComponentProperty.setLocked(RequestUtils.getInt(request, "locked"));
		formComponentProperty.setDeleteFlag(RequestUtils.getInt(request,
				"deleteFlag"));

		formComponentProperty.setUpdateBy(actorId);
		formComponentPropertyService.save(formComponentProperty);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FormComponentProperty formComponentProperty = formComponentPropertyService
				.getFormComponentProperty(RequestUtils.getLong(request, "id"));
		request.setAttribute("formComponentProperty", formComponentProperty);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formComponentProperty.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/component/property/view");
	}

}
