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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
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
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.query.FormPageQuery;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.util.FormPageDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/page")
public class FormPageResource {
	protected static final Log logger = LogFactory.getLog(FormPageResource.class);

	protected FormPageService formPageService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormPageQuery query = new FormPageQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		query.setLocked(0);
		query.setDeleteFlag(0);
		query.setKeywordsLike(request.getParameter("keywordsLike"));
		query.setNodeParentId(request.getParameter("nodeParentId"));
		FormPageDomainFactory.processDataRequest(dataRequest);
		
		String keywordsLike_base64 = request.getParameter("keywordsLike_base64");
		if (StringUtils.isNotEmpty(keywordsLike_base64)) {
			String keywordsLike = new String(Base64.decodeBase64(keywordsLike_base64));
			query.setKeywordsLike(keywordsLike);
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
		int total = formPageService.getFormPageCountByQueryCriteria(query);
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

			List<FormPage> list = formPageService.getFormPagesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormPage formPage : list) {
					JSONObject rowJSON = formPage.toJsonObject();
					rowJSON.put("id", formPage.getId());
					rowJSON.put("componentId", formPage.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.remove("formHtml");
					rowJSON.remove("formConfig");
					rowJSON.remove("outputHtml");
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
		FormPageQuery query = new FormPageQuery();
		query.setNodeParentId(request.getParameter("nodeParentId"));
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
		int total = formPageService.getFormPageCountByQueryCriteria(query);
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

			List<FormPage> list = formPageService.getFormPagesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormPage formPage : list) {
					JSONObject rowJSON = formPage.toJsonObject();
					rowJSON.put("id", formPage.getId());
					rowJSON.put("componentId", formPage.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.remove("formHtml");
					rowJSON.remove("formConfig");
					rowJSON.remove("outputHtml");
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
	public byte[] read(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormPageQuery query = new FormPageQuery();
		Tools.populate(query, params);
		query.setKeywordsLike(request.getParameter("keywordsLike"));
		query.setNodeParentId(request.getParameter("nodeParentId"));
		logger.debug("params:" + params);
		
		String keywordsLike_base64 = request.getParameter("keywordsLike_base64");
		if (StringUtils.isNotEmpty(keywordsLike_base64)) {
			String keywordsLike = new String(Base64.decodeBase64(keywordsLike_base64));
			query.setKeywordsLike(keywordsLike);
		}

		JSONArray result = new JSONArray();
		int total = formPageService.getFormPageCountByQueryCriteria(query);
		if (total > 0) {
			List<FormPage> list = formPageService.getFormPagesByQueryCriteria(0, 10000, query);
			if (list != null && !list.isEmpty()) {
				int start = 0;
				for (FormPage formPage : list) {
					JSONObject rowJSON = formPage.toJsonObject();
					rowJSON.remove("formHtml");
					rowJSON.remove("formConfig");
					rowJSON.remove("outputHtml");
					rowJSON.put("startIndex", ++start);
					result.add(rowJSON);
				}
			}
		}
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setFormPageService(FormPageService formPageService) {
		this.formPageService = formPageService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormPage formPage = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			formPage = formPageService.getFormPage(RequestUtils.getString(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (formPage != null) {
			result = formPage.toJsonObject();
			result.put("id", formPage.getId());
			result.put("pageId", formPage.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
