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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
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
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.query.FormComponentQuery;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.util.FormComponentDomainFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/form/component")
@RequestMapping("/form/component")
public class FormComponentController {
	protected static final Log logger = LogFactory.getLog(FormComponentController.class);

	protected FormComponentService formComponentService;

	public FormComponentController() {

	}
	
	/**
	 * 设置为常用控件
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setFavoriteComp")
	public byte[] setFavoriteComp(HttpServletRequest request){
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		FormComponent formComponent = formComponentService.getFormComponent(id);
		if(formComponent != null){
			//设置为常用控件
			formComponentService.updateBaseComp(formComponent.getId(), 1);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	/**
	 * 去掉常用控件
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/removeFavoriteComp")
	public byte[] removeFavoriteComp(HttpServletRequest request){
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		FormComponent formComponent = formComponentService.getFormComponent(id);
		if(formComponent != null){
			//去掉常用控件
			formComponentService.updateBaseComp(formComponent.getId(), 0);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
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
					FormComponent formComponent = formComponentService.getFormComponent(Long.valueOf(x));
					if (formComponent != null
							&& (StringUtils.equals(formComponent.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						formComponent.setLocked(1);
						formComponent.setDeleteFlag(1);
						formComponentService.save(formComponent);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormComponent formComponent = formComponentService.getFormComponent(Long.valueOf(id));
			if (formComponent != null && (StringUtils.equals(formComponent.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				formComponent.setLocked(1);
				formComponent.setDeleteFlag(1);
				formComponentService.save(formComponent);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FormComponent formComponent = formComponentService.getFormComponent(RequestUtils.getLong(request, "id"));
		if (formComponent != null) {
			request.setAttribute("component", formComponent);
		}

		FormComponentQuery query = new FormComponentQuery();
		query.setParentId(0L);
		List<FormComponent> components = formComponentService.list(query);
		request.setAttribute("components", components);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formComponent.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/component/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentQuery query = new FormComponentQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setLocked(0);
		query.setDeleteFlag(0);
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
		int total = formComponentService.getFormComponentCountByQueryCriteria(query);
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

			List<FormComponent> list = formComponentService.getFormComponentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormComponent formComponent : list) {
					JSONObject rowJSON = formComponent.toJsonObject();
					rowJSON.put("id", formComponent.getId());
					rowJSON.put("componentId", formComponent.getId());
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

		return new ModelAndView("/form/component/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formComponent.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/component/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentQuery query = new FormComponentQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormComponentDomainFactory.processDataRequest(dataRequest);

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
		int total = formComponentService.getFormComponentCountByQueryCriteria(query);
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

			List<FormComponent> list = formComponentService.getFormComponentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormComponent formComponent : list) {
					JSONObject rowJSON = formComponent.toJsonObject();
					rowJSON.put("id", formComponent.getId());
					rowJSON.put("componentId", formComponent.getId());
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
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, Object> params = RequestUtils.getParameterMap(req);
			logger.debug("params:" + params);
			FormComponent formComponent = new FormComponent();

			try {
				Tools.populate(formComponent, params);

				formComponent.setParentId(RequestUtils.getLong(req, "parentId"));
				formComponent.setDeploymentId(req.getParameter("deploymentId"));
				formComponent.setName(req.getParameter("name"));
				formComponent.setType(req.getParameter("type"));
				formComponent.setDataRole(req.getParameter("dataRole"));
				formComponent.setKendoComponent(request.getParameter("kendoComponent"));
				formComponent.setJsEngine(req.getParameter("jsEngine"));
				formComponent.setCategory(request.getParameter("category"));
				formComponent.setLocked(RequestUtils.getInt(request, "locked"));
				formComponent.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {

						String filename = mFile.getOriginalFilename();
						String name = filename;
						name = name.toLowerCase();
						if (filename.indexOf("/") != -1) {
							filename = filename.substring(filename.lastIndexOf("/") + 1, filename.length());
						} else if (filename.indexOf("\\") != -1) {
							filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
						}

						if (StringUtils.equals(mFile.getName(), "jsFile")) {
							formComponent.setJsBytes(mFile.getBytes());
						} else if (StringUtils.equals(mFile.getName(), "imageFile")) {
							formComponent.setImageFileName(filename);
							formComponent.setImageBytes(mFile.getBytes());
						} else if (StringUtils.equals(mFile.getName(), "smallImageFile")) {
							formComponent.setSmallImageFileName(filename);
							formComponent.setSmallImageBytes(mFile.getBytes());
						} else if (StringUtils.equals(mFile.getName(), "mediumImageFile")) {
							formComponent.setMediumImageFileName(filename);
							formComponent.setMediumImageBytes(mFile.getBytes());
						}
					}
				}

				formComponent.setCreateBy(actorId);
				formComponent.setUpdateBy(actorId);

				formComponentService.save(formComponent);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormComponent")
	public byte[] saveFormComponent(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponent formComponent = new FormComponent();
		try {
			Tools.populate(formComponent, params);
			formComponent.setParentId(RequestUtils.getLong(request, "parentId"));
			formComponent.setDeploymentId(request.getParameter("deploymentId"));
			formComponent.setName(request.getParameter("name"));
			formComponent.setType(request.getParameter("type"));
			formComponent.setDataRole(request.getParameter("dataRole"));
			formComponent.setKendoComponent(request.getParameter("kendoComponent"));
			formComponent.setJsEngine(request.getParameter("jsEngine"));
			formComponent.setCategory(request.getParameter("category"));
			formComponent.setLocked(RequestUtils.getInt(request, "locked"));
			formComponent.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

			formComponent.setCreateBy(actorId);
			formComponent.setUpdateBy(actorId);
			this.formComponentService.save(formComponent);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormComponent saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormComponent formComponent = new FormComponent();
		try {
			Tools.populate(formComponent, model);
			formComponent.setParentId(ParamUtils.getLong(model, "parentId"));
			formComponent.setDeploymentId(ParamUtils.getString(model, "deploymentId"));
			formComponent.setName(ParamUtils.getString(model, "name"));
			formComponent.setType(ParamUtils.getString(model, "type"));
			formComponent.setJsEngine(ParamUtils.getString(model, "jsEngine"));
			formComponent.setDataRole(request.getParameter("dataRole"));
			formComponent.setKendoComponent(request.getParameter("kendoComponent"));
			formComponent.setCategory(request.getParameter("category"));
			formComponent.setLocked(RequestUtils.getInt(request, "locked"));
			formComponent.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			formComponent.setCreateBy(actorId);
			formComponent.setUpdateBy(actorId);
			this.formComponentService.save(formComponent);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formComponent;
	}

	@ResponseBody
	@RequestMapping("/queryFormComponent")
	public byte[] queryFormComponent(HttpServletRequest request) throws IOException {
		FormComponentQuery query = new FormComponentQuery();
		query.setDeleteFlag(0);
		query.setLocked(0);
		List<FormComponent> list = formComponentService.list(query);
		List<FormComponent> retList = new ArrayList<FormComponent>();
		for (FormComponent fc : list) {
//			if (fc.getParentId() != 0) {
				retList.add(fc);
//			}
		}
		return JSON.toJSONString(retList).getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setFormComponentService(FormComponentService formComponentService) {
		this.formComponentService = formComponentService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormComponent formComponent = formComponentService.getFormComponent(RequestUtils.getLong(request, "id"));

		Tools.populate(formComponent, params);

		formComponent.setParentId(RequestUtils.getLong(request, "parentId"));
		formComponent.setDeploymentId(request.getParameter("deploymentId"));
		formComponent.setName(request.getParameter("name"));
		formComponent.setType(request.getParameter("type"));
		formComponent.setJsEngine(request.getParameter("jsEngine"));
		formComponent.setDataRole(request.getParameter("dataRole"));
		formComponent.setKendoComponent(request.getParameter("kendoComponent"));
		formComponent.setCategory(request.getParameter("category"));
		formComponent.setLocked(RequestUtils.getInt(request, "locked"));
		formComponent.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		formComponent.setUpdateBy(actorId);

		formComponentService.save(formComponent);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FormComponent formComponent = formComponentService.getFormComponent(RequestUtils.getLong(request, "id"));
		request.setAttribute("formComponent", formComponent);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formComponent.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/component/view");
	}

}
