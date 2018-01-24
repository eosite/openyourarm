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

import com.glaf.form.core.domain.FormComponentInstance;
import com.glaf.form.core.query.FormComponentInstanceQuery;
import com.glaf.form.core.service.FormComponentInstanceService;
import com.glaf.form.core.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/component/instance")
public class FormComponentInstanceResource {
	protected static final Log logger = LogFactory
			.getLog(FormComponentInstanceResource.class);

	protected FormComponentInstanceService formComponentInstanceService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentInstanceQuery query = new FormComponentInstanceQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FormComponentInstanceDomainFactory.processDataRequest(dataRequest);

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

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				formComponentInstanceService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		formComponentInstanceService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentInstanceQuery query = new FormComponentInstanceQuery();
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

	@POST
	@Path("/saveFormComponentInstance")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveFormComponentInstance(@Context HttpServletRequest request) {
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
			formComponentInstance.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			formComponentInstance.setCreateBy(request.getParameter("createBy"));

			this.formComponentInstanceService.save(formComponentInstance);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setFormComponentInstanceService(
			FormComponentInstanceService formComponentInstanceService) {
		this.formComponentInstanceService = formComponentInstanceService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormComponentInstance formComponentInstance = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			formComponentInstance = formComponentInstanceService
					.getFormComponentInstance(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (formComponentInstance != null) {
			result = formComponentInstance.toJsonObject();
			// Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", formComponentInstance.getId());
			result.put("formComponentInstanceId", formComponentInstance.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
