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
package com.glaf.form.web.rest;

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
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.*;
import com.glaf.form.core.domain.FormLayout;
import com.glaf.form.core.query.FormLayoutQuery;
import com.glaf.form.core.service.FormLayoutService;
import com.glaf.form.core.util.FormLayoutDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/layout")
public class FormLayoutResource {
	protected static final Log logger = LogFactory
			.getLog(FormLayoutResource.class);

	protected FormLayoutService formLayoutService;

	@POST
	@Path("data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormLayoutQuery query = new FormLayoutQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FormLayoutDomainFactory.processDataRequest(dataRequest);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
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

			List<FormLayout> list = formLayoutService
					.getFormLayoutsByQueryCriteria(start, limit, query);

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
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				formLayoutService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		formLayoutService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] json(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormLayoutQuery query = new FormLayoutQuery();
		Tools.populate(query, params);

		JSONArray result = new JSONArray();
		int total = formLayoutService.getFormLayoutCountByQueryCriteria(query);
		if (total > 0) {

			int start = 0;

			List<FormLayout> list = formLayoutService
					.getFormLayoutsByQueryCriteria(start, 1000, query);

			if (list != null && !list.isEmpty()) {

				for (FormLayout formLayout : list) {
					JSONObject rowJSON = formLayout.toJsonObject();
					rowJSON.put("id", formLayout.getId());
					rowJSON.put("layoutId", formLayout.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.remove("json");
					result.add(rowJSON);
				}

			}
		}
		if (request.getParameter("refreshJS") != null) {
			String filename = SystemProperties.getAppPath()
					+ "/deploy/form/layout/layout.js";
			StringBuffer buffer = new StringBuffer();
			buffer.append("var layouts = ");
			buffer.append(result.toJSONString());
			buffer.append(";");
			try {
				FileUtils.save(filename, buffer.toString().getBytes("UTF-8"));
			} catch (Exception ex) {
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/jsonp")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] jsonp(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormLayoutQuery query = new FormLayoutQuery();
		Tools.populate(query, params);

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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONArray result = new JSONArray();
		int total = formLayoutService.getFormLayoutCountByQueryCriteria(query);
		if (total > 0) {

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<FormLayout> list = formLayoutService
					.getFormLayoutsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {

				for (FormLayout formLayout : list) {
					JSONObject rowJSON = formLayout.toJsonObject();
					rowJSON.put("id", formLayout.getId());
					rowJSON.put("layoutId", formLayout.getId());
					rowJSON.put("startIndex", ++start);
					result.add(rowJSON);
				}

			}
		}
		return ("callback(" + result.toJSONString() + ")").getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormLayoutQuery query = new FormLayoutQuery();
		Tools.populate(query, params);

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

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<FormLayout> list = formLayoutService
					.getFormLayoutsByQueryCriteria(start, limit, query);

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

	@POST
	@Path("/saveFormLayout")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveFormLayout(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormLayout formLayout = new FormLayout();
		try {
			Tools.populate(formLayout, params);

			formLayout.setDeploymentId(request.getParameter("deploymentId"));
			formLayout.setName(request.getParameter("name"));
			formLayout.setType(request.getParameter("type"));
			formLayout.setJson(request.getParameter("json"));
			formLayout.setCreateDate(RequestUtils
					.getDate(request, "createDate"));
			formLayout.setCreateBy(request.getParameter("createBy"));
			formLayout.setStatus(RequestUtils.getInt(request, "status"));
			formLayout.setHeight(RequestUtils.getInt(request, "height"));
			formLayout.setWidth(RequestUtils.getInt(request, "width"));

			this.formLayoutService.save(formLayout);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setFormLayoutService(FormLayoutService formLayoutService) {
		this.formLayoutService = formLayoutService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormLayout formLayout = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			formLayout = formLayoutService.getFormLayout(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (formLayout != null) {
			result = formLayout.toJsonObject();

			result.put("id", formLayout.getId());
			result.put("formLayoutId", formLayout.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
