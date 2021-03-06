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
import com.glaf.core.util.*;

import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.query.FormComponentQuery;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/component")
public class FormComponentResource {
	protected static final Log logger = LogFactory
			.getLog(FormComponentResource.class);

	protected FormComponentService formComponentService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentQuery query = new FormComponentQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		query.setLocked(0);
		query.setDeleteFlag(0);
		FormComponentDomainFactory.processDataRequest(dataRequest);

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
		int total = formComponentService
				.getFormComponentCountByQueryCriteria(query);
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

			List<FormComponent> list = formComponentService
					.getFormComponentsByQueryCriteria(start, limit, query);

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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentQuery query = new FormComponentQuery();
		Tools.populate(query, params);

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
		int total = formComponentService
				.getFormComponentCountByQueryCriteria(query);
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

			List<FormComponent> list = formComponentService
					.getFormComponentsByQueryCriteria(start, limit, query);

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

	@POST
	@Path("/read")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] read(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentQuery query = new FormComponentQuery();
		Tools.populate(query, params);

		JSONArray result = new JSONArray();
		int total = formComponentService
				.getFormComponentCountByQueryCriteria(query);
		if (total > 0) {

			List<FormComponent> list = formComponentService
					.getFormComponentsByQueryCriteria(0, 10000, query);

			if (list != null && !list.isEmpty()) {
				int start = 0;

				for (FormComponent formComponent : list) {
					JSONObject rowJSON = formComponent.toJsonObject();
					rowJSON.put("id", formComponent.getId());
					rowJSON.put("componentId", formComponent.getId());
					rowJSON.put("startIndex", ++start);
					result.add(rowJSON);
				}

			}
		}
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setFormComponentService(
			FormComponentService formComponentService) {
		this.formComponentService = formComponentService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormComponent formComponent = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			formComponent = formComponentService.getFormComponent(RequestUtils
					.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (formComponent != null) {
			result = formComponent.toJsonObject();
			result.put("id", formComponent.getId());
			result.put("componentId", formComponent.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/createRenderjs")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] createRenderjs(@Context HttpServletRequest request) throws IOException {
		String webRootPath=request.getSession().getServletContext().getRealPath("/");
		String contextPath=request.getContextPath();
		try {
			formComponentService.createRenderjs(webRootPath);
			formComponentService.createStyleTemplate(webRootPath,contextPath);
			formComponentService.createStyleTemplateContent(webRootPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return ResponseUtils.responseJsonResult(false);
		}
		return ResponseUtils.responseJsonResult(true);
	}
}
