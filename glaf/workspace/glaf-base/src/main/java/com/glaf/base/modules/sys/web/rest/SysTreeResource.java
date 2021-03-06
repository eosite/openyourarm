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

package com.glaf.base.modules.sys.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.TreeModel;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.tree.helper.JacksonTreeHelper;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/rs/sys/tree")
@Path("/rs/sys/tree")
public class SysTreeResource {
	private static final Log logger = LogFactory.getLog(SysTreeResource.class);

	private SysTreeService sysTreeService;

	@GET
	@POST
	@Path("/allTreeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] allTreeJson(@Context HttpServletRequest request) {
		logger.debug("params:" + RequestUtils.getParameterMap(request));
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		List<SysTree> trees = sysTreeService.getAllSysTreeList();
		if (trees != null && !trees.isEmpty()) {
			for (SysTree tree : trees) {
				treeModels.add(tree);
			}
		}
		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		ArrayNode array = treeHelper.getTreeArrayNode(treeModels);
		try {
			return array.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return array.toString().getBytes();
		}
	}

	@POST
	@Path("data")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysTreeQuery query = new SysTreeQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);
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
		int total = sysTreeService.getSysTreeCountByQueryCriteria(query);
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

			List<SysTree> list = sysTreeService.getSysTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysTree sysTree : list) {
					JSONObject rowJSON = sysTree.toJsonObject();
					rowJSON.put("id", sysTree.getId());
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
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] json(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysTreeQuery query = new SysTreeQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);

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
		int total = sysTreeService.getSysTreeCountByQueryCriteria(query);
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

			List<SysTree> list = sysTreeService.getSysTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysTree sysTree : list) {
					JSONObject rowJSON = sysTree.toJsonObject();
					rowJSON.put("id", sysTree.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveAdd")
	@POST
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveAdd(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysTree bean = new SysTree();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(bean, params);
		bean.setParentId(ParamUtil.getIntParameter(request, "parent", 0));
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setCreateBy(RequestUtils.getActorId(request));
		bean.setUpdateBy(RequestUtils.getActorId(request));
		boolean ret = sysTreeService.create(bean);

		if (ret) {// 保存成功
			return ResponseUtils.responseResult(true);
		}

		return ResponseUtils.responseResult(false);
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@Path("saveModify")
	@POST
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveModify(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysTree bean = sysTreeService.findById(id);
		if (bean != null) {
			Map<String, Object> params = RequestUtils.getParameterMap(request);
			Tools.populate(bean, params);
			bean.setParentId(ParamUtil.getIntParameter(request, "parent", 0));
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setUpdateBy(RequestUtils.getActorId(request));
		}
		boolean ret = false;
		try {
			ret = sysTreeService.update(bean);
		} catch (Exception ex) {
			ret = false;
			logger.error(ex);
		}

		if (ret) {// 保存成功
			return ResponseUtils.responseResult(true);
		}

		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@POST
	@ResponseBody
	@Path("sort")
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] sort(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		int id = ParamUtil.getIntParameter(request, "id", 0);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		int operate = ParamUtil.getIntParameter(request, "operate", 0);
		logger.debug("parent:" + parent + "; id:" + id + "; operate:" + operate);
		SysTree bean = sysTreeService.findById(id);
		sysTreeService.sort(parent, bean, operate);
		return ResponseUtils.responseResult(true);
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] treeJson(@Context HttpServletRequest request) {
		logger.debug("params:" + RequestUtils.getParameterMap(request));
		String nodeCode = request.getParameter("nodeCode");

		String parent_id = request.getParameter("node");
		if (StringUtils.isEmpty(parent_id)) {
			parent_id = request.getParameter("parentId");
		}
		if (StringUtils.isEmpty(parent_id)) {
			parent_id = request.getParameter("pId");
		}
		if (StringUtils.isEmpty(parent_id)) {
			parent_id = request.getParameter("parent_id");
		}
		TreeModel treeModel = null;
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		if (StringUtils.isNotEmpty(parent_id) && StringUtils.isNumeric(parent_id)) {
			treeModel = sysTreeService.findById(Long.parseLong(parent_id));
			if (treeModel != null) {
				List<SysTree> trees = sysTreeService.getSysTreeList(treeModel.getId());
				if (trees != null && !trees.isEmpty()) {
					for (SysTree tree : trees) {
						treeModels.add(tree);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(nodeCode)) {
			treeModel = sysTreeService.getSysTreeByCode(nodeCode);
			if (treeModel != null) {
				List<SysTree> trees = sysTreeService.getSysTreeList(treeModel.getId());
				if (trees != null && !trees.isEmpty()) {
					for (SysTree tree : trees) {
						treeModels.add(tree);
					}
				}
			}
		}

		JSONArray array = new JSONArray();
		if (treeModel != null && treeModels != null && !treeModels.isEmpty()) {
			for (TreeModel t : treeModels) {
				JSONObject json = t.toJsonObject();
				json.put("isParent", true);
				// json.put("halfCheck", true);
				// json.put("check", true);
				json.put("pId", treeModel.getId());
				array.add(json);
			}
		}

		logger.debug(array.toJSONString());

		// JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		// ArrayNode responseJSON = treeHelper.getTreeArrayNode(treeModels);
		try {
			return array.toJSONString().getBytes("UTF-8");
		} catch (IOException e) {
			return array.toJSONString().getBytes();
		}
	}
}