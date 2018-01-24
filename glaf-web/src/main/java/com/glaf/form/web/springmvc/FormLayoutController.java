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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.form.core.dataimport.ExcelLayoutImport;
import com.glaf.form.core.domain.FormLayout;
import com.glaf.form.core.query.FormLayoutQuery;
import com.glaf.form.core.service.FormLayoutService;
import com.glaf.form.core.util.FormLayoutDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/layout")
@RequestMapping("/form/layout")
public class FormLayoutController {
	protected static final Log logger = LogFactory.getLog(FormLayoutController.class);

	protected FormLayoutService formLayoutService;

	public FormLayoutController() {

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
					FormLayout formLayout = formLayoutService.getFormLayout(String.valueOf(x));

					if (formLayout != null && (StringUtils.equals(formLayout.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {

					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormLayout formLayout = formLayoutService.getFormLayout(String.valueOf(id));

			if (formLayout != null && (StringUtils.equals(formLayout.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {

				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/doImport")
	public ModelAndView doImport(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile mFile, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		try {
			String filename = mFile.getOriginalFilename();
			String name = filename;
			name = name.toLowerCase();
			if (filename.indexOf("/") != -1) {
				filename = filename.substring(filename.lastIndexOf("/") + 1, filename.length());
			} else if (filename.indexOf("\\") != -1) {
				filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
			}

			if (mFile.getInputStream() != null) {
				ExcelLayoutImport imp = new ExcelLayoutImport();
				List<FormLayout> layouts = imp.read(mFile.getInputStream());
				if (layouts != null && !layouts.isEmpty()) {
					for (FormLayout layout : layouts) {
						layout.setCreateBy(loginContext.getActorId());
					}
					formLayoutService.saveAll(layouts);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formLayout.doImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		FormLayout formLayout = formLayoutService.getFormLayout(request.getParameter("id"));
		if (formLayout != null) {
			if (StringUtils.isNotEmpty(formLayout.getJson())) {
				String json = formLayout.getJson();
				json = StringTools.replace(json, "\"", "\\\"");
				formLayout.setJson(json);
			}
			request.setAttribute("formLayout", formLayout);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formLayout.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/layout/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormLayoutQuery query = new FormLayoutQuery();
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
		int total = formLayoutService.getFormLayoutCountByQueryCriteria(query);
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

			List<FormLayout> list = formLayoutService.getFormLayoutsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormLayout formLayout : list) {
					JSONObject rowJSON = formLayout.toJsonObject();
					rowJSON.put("id", formLayout.getId());
					rowJSON.put("rowId", formLayout.getId());
					rowJSON.put("formLayoutId", formLayout.getId());
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

		return new ModelAndView("/form/layout/list", modelMap);
	}

	@RequestMapping("/listview")
	public ModelAndView listview(HttpServletRequest request, ModelMap modelMap) {
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

		return new ModelAndView("/form/layout/listview", modelMap);
	}

	@RequestMapping("/preview")
	public ModelAndView preview(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		FormLayout formLayout = formLayoutService.getFormLayout(request.getParameter("id"));
		if (formLayout != null) {
			if (StringUtils.isNotEmpty(formLayout.getJson())) {
				String json = formLayout.getJson();
				json = StringTools.replace(json, "\"", "\\\"");
				formLayout.setJson(json);
			}
			request.setAttribute("formLayout", formLayout);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formLayout.preview");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/layout/preview", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formLayout.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/layout/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormLayoutQuery query = new FormLayoutQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormLayoutDomainFactory.processDataRequest(dataRequest);

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
		int total = formLayoutService.getFormLayoutCountByQueryCriteria(query);
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

			List<FormLayout> list = formLayoutService.getFormLayoutsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormLayout formLayout : list) {
					JSONObject rowJSON = formLayout.toJsonObject();
					rowJSON.put("id", formLayout.getId());
					rowJSON.put("formLayoutId", formLayout.getId());
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
	public ModelAndView save(HttpServletRequest request, @RequestParam("file") MultipartFile mFile, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

			Map<String, Object> params = RequestUtils.getParameterMap(req);
			params.remove("status");
			params.remove("wfStatus");

			FormLayout formLayout = new FormLayout();
			Tools.populate(formLayout, params);

			formLayout.setName(req.getParameter("name"));
			try {
				formLayout.setCreateBy(actorId);

				if (mFile.getBytes() != null) {
					String filename = mFile.getOriginalFilename();
					String name = filename;
					name = name.toLowerCase();
					if (filename.indexOf("/") != -1) {
						filename = filename.substring(filename.lastIndexOf("/") + 1, filename.length());
					} else if (filename.indexOf("\\") != -1) {
						filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
					}

					formLayout.setImageFileName(filename);
					formLayout.setBytes(mFile.getBytes());
				}

				formLayoutService.save(formLayout);
				request.setAttribute("formLayout", formLayout);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return new ModelAndView("/form/layout/view");
	}

	@ResponseBody
	@RequestMapping("/saveFormLayout")
	public byte[] saveFormLayout(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormLayout formLayout = new FormLayout();
		try {
			Tools.populate(formLayout, params);
			formLayout.setDeploymentId(request.getParameter("deploymentId"));
			formLayout.setName(request.getParameter("name"));
			formLayout.setType(request.getParameter("type"));
			formLayout.setJson(request.getParameter("json"));
			formLayout.setStatus(RequestUtils.getInt(request, "status"));
			formLayout.setHeight(RequestUtils.getInt(request, "height"));
			formLayout.setWidth(RequestUtils.getInt(request, "width"));
			formLayout.setCreateBy(actorId);
			this.formLayoutService.save(formLayout);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormLayout saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormLayout formLayout = new FormLayout();
		try {
			Tools.populate(formLayout, model);
			formLayout.setDeploymentId(ParamUtils.getString(model, "deploymentId"));
			formLayout.setName(ParamUtils.getString(model, "name"));
			formLayout.setType(ParamUtils.getString(model, "type"));
			formLayout.setJson(ParamUtils.getString(model, "json"));
			formLayout.setCreateDate(ParamUtils.getDate(model, "createDate"));
			formLayout.setCreateBy(ParamUtils.getString(model, "createBy"));
			formLayout.setStatus(ParamUtils.getInt(model, "status"));
			formLayout.setHeight(ParamUtils.getInt(model, "height"));
			formLayout.setWidth(ParamUtils.getInt(model, "width"));
			formLayout.setCreateBy(actorId);
			this.formLayoutService.save(formLayout);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formLayout;
	}

	@javax.annotation.Resource
	public void setFormLayoutService(FormLayoutService formLayoutService) {
		this.formLayoutService = formLayoutService;
	}

	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formLayout.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/layout/showImport", modelMap);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormLayout formLayout = formLayoutService.getFormLayout(request.getParameter("id"));

		Tools.populate(formLayout, params);

		formLayout.setDeploymentId(request.getParameter("deploymentId"));
		formLayout.setName(request.getParameter("name"));
		formLayout.setType(request.getParameter("type"));
		formLayout.setJson(request.getParameter("json"));
		formLayout.setStatus(RequestUtils.getInt(request, "status"));
		formLayout.setHeight(RequestUtils.getInt(request, "height"));
		formLayout.setWidth(RequestUtils.getInt(request, "width"));

		formLayoutService.save(formLayout);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		FormLayout formLayout = formLayoutService.getFormLayout(request.getParameter("id"));
		request.setAttribute("formLayout", formLayout);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formLayout.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/layout/view");
	}

}
