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

package com.glaf.base.modules.file.web.rest;

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
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TreeModel;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.*;

import com.glaf.base.modules.file.domain.FileAttachment;
import com.glaf.base.modules.file.query.FileAttachmentQuery;
import com.glaf.base.modules.file.service.FileAttachmentService;
import com.glaf.base.modules.file.util.*;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysTreeService;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/file/attachment")
public class FileAttachmentResource {
	protected static final Log logger = LogFactory.getLog(FileAttachmentResource.class);

	protected FileAttachmentService fileAttachmentService;

	protected SysTreeService sysTreeService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileAttachmentQuery query = new FileAttachmentQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FileAttachmentDomainFactory.processDataRequest(dataRequest);

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
		int total = fileAttachmentService.getFileAttachmentCountByQueryCriteria(query);
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

			List<FileAttachment> list = fileAttachmentService.getFileAttachmentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileAttachment fileAttachment : list) {
					JSONObject rowJSON = fileAttachment.toJsonObject();
					rowJSON.put("id", fileAttachment.getId());
					rowJSON.put("fileAttachmentId", fileAttachment.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.put("path",
							request.getContextPath() + "/mx/lob/lob/download?fileId=" + fileAttachment.getId());
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
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				fileAttachmentService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		fileAttachmentService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileAttachmentQuery query = new FileAttachmentQuery();
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
		int total = fileAttachmentService.getFileAttachmentCountByQueryCriteria(query);
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

			List<FileAttachment> list = fileAttachmentService.getFileAttachmentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileAttachment fileAttachment : list) {
					JSONObject rowJSON = fileAttachment.toJsonObject();
					rowJSON.put("id", fileAttachment.getId());
					rowJSON.put("fileAttachmentId", fileAttachment.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.put("path", request.getContextPath()+"/mx/lob/lob/download?fileId="+fileAttachment.getId());
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

	@javax.annotation.Resource
	public void setFileAttachmentService(FileAttachmentService fileAttachmentService) {
		this.fileAttachmentService = fileAttachmentService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] treeJson(@Context HttpServletRequest request) throws IOException {
		JSONArray array = new JSONArray();
		String category = request.getParameter("category");
		Long nodeId = RequestUtils.getLong(request, "parentId", 1051);
		List<SysTree> trees = null;
		if (StringUtils.isNotEmpty(category)) {
			SysTree tree = sysTreeService.getSysTreeByCode(category);
			if (tree != null) {
				trees = sysTreeService.getSysTreeListWithChildren(tree.getId());
			}
		} else if (nodeId != null && nodeId > 0) {
			trees = sysTreeService.getSysTreeListWithChildren(nodeId);
		}

		if (trees != null && !trees.isEmpty()) {
			Map<Long, TreeModel> treeMap = new HashMap<Long, TreeModel>();
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			List<Long> categoryIds = new ArrayList<Long>();
			for (SysTree t : trees) {
				TreeModel tree = new BaseTree();
				tree.setId(t.getId());
				tree.setParentId(t.getParentId());
				tree.setCode(t.getCode());
				tree.setName(t.getName());
				tree.setSortNo(t.getSort());
				tree.setDescription(t.getDesc());
				tree.setCreateBy(t.getCreateBy());
				tree.setIconCls("tree_folder");
				tree.setTreeId(t.getTreeId());
				treeModels.add(tree);
				categoryIds.add(t.getId());
				treeMap.put(t.getId(), tree);
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			// logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return array.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FileAttachment fileAttachment = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			fileAttachment = fileAttachmentService.getFileAttachment(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (fileAttachment != null) {
			result = fileAttachment.toJsonObject();
			result.put("id", fileAttachment.getId());
			result.put("fileAttachmentId", fileAttachment.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
