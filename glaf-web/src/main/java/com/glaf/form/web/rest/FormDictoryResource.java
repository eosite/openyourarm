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
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.service.DictoryDefinitionService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.query.FormDictoryQuery;
import com.glaf.form.core.service.IFormDictTreeService;
import com.glaf.form.core.service.IFormDictoryService;
import com.glaf.form.core.util.FormDictoryFactory;

@Controller("/rs/form/formDictory")
@Path("/rs/form/formDictory")
public class FormDictoryResource {
	private static final Log logger = LogFactory
			.getLog(FormDictoryResource.class);

	protected DictoryDefinitionService dictoryDefinitionService;

	protected IFormDictoryService formDictoryService;

	protected IFormDictTreeService formDictTreeService;

	/**
	 * 提交删除
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("batchDelete")
	@POST
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] batchDelete(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = true;
		long[] id = ParamUtil.getLongParameterValues(request, "id");
		ret = formDictoryService.deleteAll(id);
		if (ret) {
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@POST
	@Path("data")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormDictoryQuery query = new FormDictoryQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);

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
		int total = formDictoryService
				.getFormDictoryCountByQueryCriteria(query);
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

			List<FormDictory> list = formDictoryService
					.getFormDictorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormDictory dictory : list) {
					JSONObject rowJSON = dictory.toJsonObject();
					rowJSON.put("id", dictory.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("json")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] json(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormDictoryQuery query = new FormDictoryQuery();
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
		int total = formDictoryService
				.getFormDictoryCountByQueryCriteria(query);
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

			List<FormDictory> list = formDictoryService
					.getFormDictorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormDictory dictory : list) {
					JSONObject rowJSON = dictory.toJsonObject();
					rowJSON.put("id", dictory.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 提交增加字典信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveAdd")
	@POST
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveAdd(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		FormDictory bean = new FormDictory();
		try {
			Tools.populate(bean, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		bean.setCreateBy(RequestUtils.getActorId(request));

		if (formDictoryService.create(bean)) {// 保存成功
			BaseDataManager.getInstance().loadDictInfo();
			return ResponseUtils.responseResult(true);
		}

		return ResponseUtils.responseResult(false);
	}

	/**
	 * 提交修改字典信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveModify")
	@POST
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveModify(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		FormDictory bean = formDictoryService.find(id);
		logger.debug("params:" + params);
		try {
			Tools.populate(bean, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		bean.setUpdateBy(RequestUtils.getActorId(request));

		if (formDictoryService.update(bean)) {// 保存成功
			BaseDataManager.getInstance().loadDictInfo();
			return ResponseUtils.responseResult(true);
		}

		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setDictoryDefinitionService(
			DictoryDefinitionService dictoryDefinitionService) {
		this.dictoryDefinitionService = dictoryDefinitionService;
	}

	@Resource
	public void setFormDictoryService(IFormDictoryService formDictoryService) {
		this.formDictoryService = formDictoryService;
	}

	@Resource
	public void setFormDictTreeService(IFormDictTreeService formDictTreeService) {
		this.formDictTreeService = formDictTreeService;
	}

	@POST
	@ResponseBody
	@Path("sort")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] sort(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		long parent = ParamUtil.getIntParameter(request, "parent", 0);
		int operate = ParamUtil.getIntParameter(request, "operate", 0);
		logger.debug("parent:" + parent + "; id:" + id + "; operate:" + operate);
		FormDictory bean = formDictoryService.find(id);
		formDictoryService.sort(parent, bean, operate);
		return ResponseUtils.responseResult(true);
	}

	@POST
	@GET
	@ResponseBody
	@Path("/dataByTreeCode")
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] dataByTreeCode(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {

		String treeCode = request.getParameter("treeCode");
		FormDictoryFactory factory = FormDictoryFactory.getInstance();
		List<FormDictory> list = factory.getFormDictoryListByTreeCode(treeCode);

		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormDictory dict : list) {
				JSONObject rowJson = dict.toJsonObject();
				rowJson.put("text", dict.getName());
				array.add(rowJson);
			}
		}

		return array.toJSONString().getBytes("utf-8");
	}
}