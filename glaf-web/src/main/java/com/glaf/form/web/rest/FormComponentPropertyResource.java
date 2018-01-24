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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormComponentProperty;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.query.FormComponentPropertyQuery;
import com.glaf.form.core.service.FormComponentPropertyService;
import com.glaf.form.core.util.FormComponentPropertyDomainFactory;
import com.glaf.form.core.util.FormDictoryFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/component/property")
public class FormComponentPropertyResource {
	protected static final Log logger = LogFactory
			.getLog(FormComponentPropertyResource.class);

	protected FormComponentPropertyService formComponentPropertyService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentPropertyQuery query = new FormComponentPropertyQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		query.setLocked(0);
		query.setDeleteFlag(0);
		FormComponentPropertyDomainFactory.processDataRequest(dataRequest);

		String kendoComponent = request.getParameter("kendoComponent");
		query.kendoComponent(kendoComponent);

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
				query.setOrderBy(orderName);
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

				//List<BaseDataInfo>  bdiList = BaseDataManager.getInstance().getBaseData("property_sort");
				
				List<FormDictory> bdiList = FormDictoryFactory.getInstance()
						.getFormDictoryListByTreeCode("property_sort");
				Map<String,String> map = null;
				if(bdiList != null){
					String id,code;
					JSONObject json0,json1;
					map = new HashMap<String,String>();
					for(FormDictory bdi : bdiList){
						json0 = bdi.toJsonObject();
						json1 = new JSONObject();
						code = json0.getString("code");
						id = "99" + json0.get("id");
						map.put(code, id);
						json1.put("id", id);
						json1.put("isSort", 0);
						json1.put("title", json0.get("name"));
						json1.put("name", code);
						json1.put("createDate", json0.get("createDate"));
						json1.put("type", code);
						json1.put("root", true);
						rowsJSON.add(json1);
					}
				}

				for (FormComponentProperty formComponentProperty : list) {
					JSONObject rowJSON = formComponentProperty.toJsonObject();
					if(formComponentProperty.getParentId() == null && map != null){
						rowJSON.put("parentId", map.get(formComponentProperty.getType()));
					}
					rowJSON.put("id", formComponentProperty.getId());
					rowJSON.put("formComponentPropertyId",
							formComponentProperty.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
				
				result.put("rows", rowsJSON);
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
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				formComponentPropertyService.deleteByIds(ids);
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
		formComponentPropertyService.deleteById(RequestUtils.getLong(request,
				"id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormComponentPropertyQuery query = new FormComponentPropertyQuery();
		Tools.populate(query, params);

		String kendoComponent = request.getParameter("kendoComponent");
		query.kendoComponent(kendoComponent);

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

	@POST
	@Path("/saveFormComponentProperty")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveFormComponentProperty(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
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

			formComponentProperty.setCreateBy(loginContext.getActorId());
			formComponentProperty.setUpdateBy(loginContext.getActorId());

			this.formComponentPropertyService.save(formComponentProperty);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setFormComponentPropertyService(
			FormComponentPropertyService formComponentPropertyService) {
		this.formComponentPropertyService = formComponentPropertyService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormComponentProperty formComponentProperty = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			formComponentProperty = formComponentPropertyService
					.getFormComponentProperty(RequestUtils.getLong(request,
							"id"));
		}
		JSONObject result = new JSONObject();
		if (formComponentProperty != null) {
			result = formComponentProperty.toJsonObject();
			// Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", formComponentProperty.getId());
			result.put("formComponentPropertyId", formComponentProperty.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
