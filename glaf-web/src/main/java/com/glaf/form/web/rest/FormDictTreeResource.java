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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TreeModel;
import com.glaf.core.tree.component.TreeComponent;
import com.glaf.core.tree.component.TreeRepository;
import com.glaf.core.tree.helper.JacksonTreeHelper;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.query.FormDictTreeQuery;
import com.glaf.form.core.service.IFormDictTreeService;
import com.glaf.form.core.util.FormDictoryFactory;

@Controller("/rs/form/formDictTree")
@Path("/rs/form/formDictTree")
public class FormDictTreeResource {

	private static final Log logger = LogFactory
			.getLog(FormDictTreeResource.class);

	private IFormDictTreeService formDictTreeService;

	@GET
	@POST
	@Path("/allTreeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] allTreeJson(@Context HttpServletRequest request) {
		logger.debug("params:" + RequestUtils.getParameterMap(request));
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		List<FormDictTree> trees = formDictTreeService.getAllFormDictTreeList();
		if (trees != null && !trees.isEmpty()) {
			for (FormDictTree tree : trees) {
				treeModels.add(tree);
			}
		}
		
		ArrayNode array = this.getTreeArrayNode(treeModels,true);
		try {
			return array.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return array.toString().getBytes();
		}
	}
	
	private ArrayNode getTreeArrayNode(List<TreeModel> treeModels, boolean showParentIfNotChildren) {
		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		
		ArrayNode result = new ObjectMapper().createArrayNode();
		if (treeModels != null && treeModels.size() > 0) {
			TreeRepository repository = treeHelper.build(treeModels);
			List<?> topTrees = repository.getTopTrees();
			if (topTrees != null && topTrees.size() > 0) {
				for (int i = 0, len = topTrees.size(); i < len; i++) {
					TreeComponent component = (TreeComponent) topTrees.get(i);
					ObjectNode child = new ObjectMapper().createObjectNode();
					//this.addDataMap(component, child);

					child.put("id", component.getId());
					child.put("code", component.getCode());
					child.put("text", component.getTitle());
					child.put("name", component.getTitle());
					child.put("checked", component.isChecked());
					child.put("icon", component.getImage());
					child.put("img", component.getImage());
					child.put("image", component.getImage());
					child.put("open", true);
					
					if (component.getComponents() != null && component.getComponents().size() > 0) {
						child.put("leaf", Boolean.valueOf(false));
						child.put("cls", "folder");
						child.put("isParent", true);
						child.put("classes", "folder");
						result.add(child);
						treeHelper.buildTreeModel(child, component);
					} else {
						child.put("leaf", Boolean.valueOf(true));
						child.put("isParent", false);
						if (showParentIfNotChildren) {
							result.add(child);
						}
					}
				}
			}
		}

		return result;
	}

	@POST
	@Path("data")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormDictTreeQuery query = new FormDictTreeQuery();
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
		int total = formDictTreeService
				.getFormDictTreeCountByQueryCriteria(query);
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

			List<FormDictTree> list = formDictTreeService
					.getFormDictTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormDictTree tree : list) {
					JSONObject rowJSON = tree.toJsonObject();
					rowJSON.put("id", tree.getId());
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
		FormDictTreeQuery query = new FormDictTreeQuery();
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
		int total = formDictTreeService
				.getFormDictTreeCountByQueryCriteria(query);
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

			List<FormDictTree> list = formDictTreeService
					.getFormDictTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormDictTree tree : list) {
					JSONObject rowJSON = tree.toJsonObject();
					rowJSON.put("id", tree.getId());
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
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveAdd(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		FormDictTree bean = new FormDictTree();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(bean, params);
		bean.setParentId(ParamUtil.getIntParameter(request, "parent", 0));
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setCreateBy(RequestUtils.getActorId(request));
		bean.setUpdateBy(RequestUtils.getActorId(request));
		boolean ret = formDictTreeService.create(bean);

		if (ret) {// 保存成功
			FormDictoryFactory.getInstance().reload();
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
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveModify(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		FormDictTree bean = formDictTreeService.findById(id);
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
			ret = formDictTreeService.update(bean);
		} catch (Exception ex) {
			ret = false;
			logger.error(ex);
		}

		if (ret) {// 保存成功
			FormDictoryFactory.getInstance().reload();
			return ResponseUtils.responseResult(true);
		}

		return ResponseUtils.responseResult(false);
	}

	@POST
	@ResponseBody
	@Path("sort")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] sort(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		int id = ParamUtil.getIntParameter(request, "id", 0);
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		int operate = ParamUtil.getIntParameter(request, "operate", 0);
		logger.debug("parent:" + parent + "; id:" + id + "; operate:" + operate);
		FormDictTree bean = formDictTreeService.findById(id);
		formDictTreeService.sort(parent, bean, operate);
		return ResponseUtils.responseResult(true);
	}

	@javax.annotation.Resource
	public void setFormDictTreeService(IFormDictTreeService formDictTreeService) {
		this.formDictTreeService = formDictTreeService;
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
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
		if (StringUtils.isNotEmpty(parent_id)
				&& StringUtils.isNumeric(parent_id)) {
			treeModel = formDictTreeService.findById(Long.parseLong(parent_id));
			if (treeModel != null) {
				List<FormDictTree> trees = formDictTreeService
						.getFormDictTreeList(treeModel.getId());
				if (trees != null && !trees.isEmpty()) {
					for (FormDictTree tree : trees) {
						treeModels.add(tree);
					}
				}
			}
		} else if (StringUtils.isNotEmpty(nodeCode)) {
			treeModel = formDictTreeService.getFormDictTreeByCode(nodeCode);
			if (treeModel != null) {
				List<FormDictTree> trees = formDictTreeService
						.getFormDictTreeList(treeModel.getId());
				if (trees != null && !trees.isEmpty()) {
					for (FormDictTree tree : trees) {
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

	@GET
	@POST
	@Path("/ajaxSubTreeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] ajaxSubTreeJson(@Context HttpServletRequest request) {
		logger.debug("params:" + RequestUtils.getParameterMap(request));
		String nodeCode = request.getParameter("nodeCode");

		String parent_id = request.getParameter("parent");
		if (parent_id == null) {
			parent_id = "-1";
		}

		TreeModel treeModel = null;
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		if (StringUtils.isNotEmpty(parent_id)) {
			List<FormDictTree> trees = formDictTreeService
					.getFormDictTreeList(Long.parseLong(parent_id));

			if (trees != null && !trees.isEmpty()) {
				for (FormDictTree tree : trees) {
					treeModels.add(tree);
				}
			}
		} else if (StringUtils.isNotEmpty(nodeCode)) {
			treeModel = formDictTreeService.getFormDictTreeByCode(nodeCode);
			if (treeModel != null) {
				List<FormDictTree> trees = formDictTreeService
						.getFormDictTreeList(treeModel.getId());
				if (trees != null && !trees.isEmpty()) {
					for (FormDictTree tree : trees) {
						treeModels.add(tree);
					}
				}
			}
		}

		JSONArray array = new JSONArray();
		if (treeModels != null && !treeModels.isEmpty()) {
			for (TreeModel t : treeModels) {
				JSONObject json = t.toJsonObject();
				json.put("isParent", true);
				json.put("pId", t.getParentId());
				json.put("open", t.getParentId() == -1 ? true : false);
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

	@GET
	@POST
	@Path("/batchDelete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] batchDelete(@Context HttpServletRequest request)
			throws Exception {

		JSONObject jobject = new JSONObject();
		try {
			List<String> rowIds = new ArrayList<String>();
			String id = RequestUtils.getString(request, "id");
			rowIds.add(id);
			
			FormDictTree tree = formDictTreeService.findById(Long.parseLong(id));
			
			FormDictTreeQuery query = new FormDictTreeQuery();
			query.setTreeIdLeftLike(tree.getTreeId());
			query.setRowIds(rowIds);
			formDictTreeService.deleteAll(query);

			FormDictoryFactory.getInstance().reload();
			jobject.put("resultCode", 200);
			jobject.put("message", "数据删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			jobject.put("resultCode", 400);
			jobject.put("message", "数据删除失败，Error=" + e.getLocalizedMessage());
		}

		return jobject.toJSONString().getBytes("UTF-8");
	}
}