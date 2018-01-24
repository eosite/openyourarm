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
import com.glaf.form.core.domain.FormComponentInstance;
import com.glaf.form.core.query.FormComponentInstanceQuery;
import com.glaf.form.core.service.FormComponentInstanceService;
import com.glaf.form.core.util.FormComponentInstanceDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/component/instance")
@RequestMapping("/form/component/instance")
public class FormComponentInstanceController {
	protected static final Log logger = LogFactory
			.getLog(FormComponentInstanceController.class);

	protected FormComponentInstanceService formComponentInstanceService;

	public FormComponentInstanceController() {

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
					FormComponentInstance formComponentInstance = formComponentInstanceService
							.getFormComponentInstance(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (formComponentInstance != null
							&& (StringUtils.equals(
									formComponentInstance.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// formComponentInstance.setDeleteFlag(1);
						formComponentInstanceService
								.save(formComponentInstance);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormComponentInstance formComponentInstance = formComponentInstanceService
					.getFormComponentInstance(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (formComponentInstance != null
					&& (StringUtils.equals(formComponentInstance.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// formComponentInstance.setDeleteFlag(1);
				formComponentInstanceService.save(formComponentInstance);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FormComponentInstance formComponentInstance = formComponentInstanceService
				.getFormComponentInstance(request.getParameter("id"));
		if (formComponentInstance != null) {
			request.setAttribute("formComponentInstance", formComponentInstance);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formComponentInstance.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/component/instance/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentInstanceQuery query = new FormComponentInstanceQuery();
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
		int total = formComponentInstanceService
				.getFormComponentInstanceCountByQueryCriteria(query);
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
			List<FormComponentInstance> list = formComponentInstanceService
					.getFormComponentInstancesByQueryCriteria(start, limit,
							query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormComponentInstance formComponentInstance : list) {
					JSONObject rowJSON = formComponentInstance.toJsonObject();
					rowJSON.put("id", formComponentInstance.getId());
					rowJSON.put("rowId", formComponentInstance.getId());
					rowJSON.put("formComponentInstanceId",
							formComponentInstance.getId());
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

		return new ModelAndView("/form/component/instance/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formComponentInstance.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/component/instance/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentInstanceQuery query = new FormComponentInstanceQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormComponentInstanceDomainFactory.processDataRequest(dataRequest);

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
		int total = formComponentInstanceService
				.getFormComponentInstanceCountByQueryCriteria(query);
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
			List<FormComponentInstance> list = formComponentInstanceService
					.getFormComponentInstancesByQueryCriteria(start, limit,
							query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormComponentInstance formComponentInstance : list) {
					JSONObject rowJSON = formComponentInstance.toJsonObject();
					rowJSON.put("id", formComponentInstance.getId());
					rowJSON.put("formComponentInstanceId",
							formComponentInstance.getId());
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

		FormComponentInstance formComponentInstance = new FormComponentInstance();
		Tools.populate(formComponentInstance, params);

		formComponentInstance.setFormDesignId(request
				.getParameter("formDesignId"));
		formComponentInstance.setDeploymentId(request
				.getParameter("deploymentId"));
		formComponentInstance.setComponentId(request
				.getParameter("componentId"));
		formComponentInstance.setName(request.getParameter("name"));
		formComponentInstance.setTitle(request.getParameter("title"));
		formComponentInstance.setType(request.getParameter("type"));
		formComponentInstance.setDataType(request.getParameter("dataType"));
		formComponentInstance.setKendoComponent(request
				.getParameter("kendoComponent"));
		formComponentInstance.setValue(request.getParameter("value"));

		formComponentInstance.setCreateBy(actorId);

		formComponentInstanceService.save(formComponentInstance);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormComponentInstance")
	public byte[] saveFormComponentInstance(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentInstance formComponentInstance = new FormComponentInstance();
		try {
			Tools.populate(formComponentInstance, params);
			formComponentInstance.setFormDesignId(request
					.getParameter("formDesignId"));
			formComponentInstance.setDeploymentId(request
					.getParameter("deploymentId"));
			formComponentInstance.setComponentId(request
					.getParameter("componentId"));
			formComponentInstance.setName(request.getParameter("name"));
			formComponentInstance.setTitle(request.getParameter("title"));
			formComponentInstance.setType(request.getParameter("type"));
			formComponentInstance.setDataType(request.getParameter("dataType"));
			formComponentInstance.setKendoComponent(request
					.getParameter("kendoComponent"));
			formComponentInstance.setValue(request.getParameter("value"));

			formComponentInstance.setCreateBy(actorId);
			this.formComponentInstanceService.save(formComponentInstance);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormComponentInstance saveOrUpdate(
			HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormComponentInstance formComponentInstance = new FormComponentInstance();
		try {
			Tools.populate(formComponentInstance, model);
			formComponentInstance.setFormDesignId(ParamUtils.getString(model,
					"formDesignId"));
			formComponentInstance.setDeploymentId(ParamUtils.getString(model,
					"deploymentId"));
			formComponentInstance.setComponentId(ParamUtils.getString(model,
					"componentId"));
			formComponentInstance.setName(ParamUtils.getString(model, "name"));
			formComponentInstance
					.setTitle(ParamUtils.getString(model, "title"));
			formComponentInstance.setType(ParamUtils.getString(model, "type"));
			formComponentInstance.setDataType(ParamUtils.getString(model,
					"dataType"));
			formComponentInstance.setKendoComponent(ParamUtils.getString(model,
					"kendoComponent"));
			formComponentInstance
					.setValue(ParamUtils.getString(model, "value"));

			formComponentInstance.setCreateBy(actorId);
			this.formComponentInstanceService.save(formComponentInstance);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formComponentInstance;
	}

	@javax.annotation.Resource
	public void setFormComponentInstanceService(
			FormComponentInstanceService formComponentInstanceService) {
		this.formComponentInstanceService = formComponentInstanceService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormComponentInstance formComponentInstance = formComponentInstanceService
				.getFormComponentInstance(request.getParameter("id"));

		Tools.populate(formComponentInstance, params);

		formComponentInstance.setFormDesignId(request
				.getParameter("formDesignId"));
		formComponentInstance.setDeploymentId(request
				.getParameter("deploymentId"));
		formComponentInstance.setComponentId(request
				.getParameter("componentId"));
		formComponentInstance.setName(request.getParameter("name"));
		formComponentInstance.setTitle(request.getParameter("title"));
		formComponentInstance.setType(request.getParameter("type"));
		formComponentInstance.setDataType(request.getParameter("dataType"));
		formComponentInstance.setKendoComponent(request
				.getParameter("kendoComponent"));
		formComponentInstance.setValue(request.getParameter("value"));

		formComponentInstanceService.save(formComponentInstance);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FormComponentInstance formComponentInstance = formComponentInstanceService
				.getFormComponentInstance(request.getParameter("id"));
		request.setAttribute("formComponentInstance", formComponentInstance);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formComponentInstance.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/component/instance/view");
	}

}
