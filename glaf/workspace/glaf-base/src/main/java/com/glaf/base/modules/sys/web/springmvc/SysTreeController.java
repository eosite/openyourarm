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

package com.glaf.base.modules.sys.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.EntityEntry;
import com.glaf.core.domain.EntryPoint;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.identity.Role;
import com.glaf.core.identity.User;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.service.IEntryService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/sys/tree")
@RequestMapping("/sys/tree")
public class SysTreeController {
	private static final Log logger = LogFactory.getLog(SysTreeController.class);

	private SysTreeService sysTreeService;

	private IEntryService entryService;

	@RequestMapping("/exportTrees")
	public void exportTrees(HttpServletRequest request, HttpServletResponse response) {
		String objectIds = request.getParameter("nodeIds");
		List<Long> nodeIds = StringTools.splitToLong(objectIds);
		if (nodeIds != null && !nodeIds.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			SysTreeQuery query = new SysTreeQuery();
			query.locked(0);
			query.setDeleteFlag(0);
			query.nodeIds(nodeIds);
			List<SysTree> trees = sysTreeService.getSysTreesByQueryCriteria(0, 50000, query);
			if (trees != null && !trees.isEmpty()) {
				for (SysTree tree : trees) {
					if (nodeIds.contains(tree.getId())) {
						buffer.append(
								"insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator, treeid, moveable, icon, iconCls, url, ALLOWEDFILEEXTS, ALLOWEDFIZESIZE, PROVIDERCLASS) values (");
						buffer.append(tree.getId()).append(",");
						buffer.append(tree.getParentId()).append(",");

						if (StringUtils.isNotEmpty(tree.getName())) {
							String text = tree.getName();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(tree.getDesc())) {
							String text = tree.getDesc();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						buffer.append(tree.getSort()).append(", ");

						if (StringUtils.isNotEmpty(tree.getCode())) {
							String text = tree.getCode();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(tree.getDiscriminator())) {
							String text = tree.getDiscriminator();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(tree.getTreeId())) {
							String text = tree.getTreeId();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(tree.getMoveable())) {
							String text = tree.getMoveable();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(tree.getIcon())) {
							String text = tree.getIcon();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(tree.getIconCls())) {
							String text = tree.getIconCls();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(tree.getUrl())) {
							String text = tree.getUrl();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						if (StringUtils.isNotEmpty(tree.getAllowedFileExts())) {
							String text = tree.getAllowedFileExts();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("', ");
						} else {
							buffer.append("null, ");
						}

						buffer.append(tree.getAllowedFizeSize()).append(", ");

						if (StringUtils.isNotEmpty(tree.getProviderClass())) {
							String text = tree.getProviderClass();
							text = StringTools.replace(text, "'", "\'");
							buffer.append("'").append(text).append("' ");
						} else {
							buffer.append("null ");
						}

						buffer.append(");");
						buffer.append(FileUtils.newline);
					}
				}
			}

			try {
				ResponseUtils.download(request, response, buffer.toString().getBytes("UTF-8"), "sys_tree.insert.sql");
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 显示下级节点
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/getSubTree")
	public ModelAndView getSubTree(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int id = ParamUtil.getIntParameter(request, "id", 0);
		List<SysTree> list = sysTreeService.getSysTreeList(id);
		Collections.sort(list);
		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("tree.getSubTree");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/subtree_list", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		} else {
			result.put("total", total);
			result.put("totalCount", total);
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
		}
		return result.toString().getBytes("UTF-8");
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

		String x_view = ViewProperties.getString("tree.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/list", modelMap);
	}

	/**
	 * 显示增加页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareAdd")
	public ModelAndView prepareAdd(HttpServletRequest request, ModelMap modelMap) {
		request.setAttribute("contextPath", request.getContextPath());

		String x_view = ViewProperties.getString("tree.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/tree_add", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareModify")
	public ModelAndView prepareModify(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysTree bean = sysTreeService.findById(id);
		if (bean != null && bean.getParentId() > 0) {
			SysTree parent = sysTreeService.findById(bean.getParentId());
			bean.setParent(parent);
		}
		request.setAttribute("bean", bean);
		List<SysTree> list = new ArrayList<SysTree>();
		sysTreeService.getSysTree(list, 1, 0);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("tree.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/tree_modify", modelMap);
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveAdd")
	public ModelAndView saveAdd(HttpServletRequest request, ModelMap modelMap) {
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
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("tree.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("tree.add_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("/show_msg", modelMap);
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveModify")
	public ModelAndView saveModify(HttpServletRequest request, ModelMap modelMap) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysTree bean = sysTreeService.findById(id);
		if (bean != null) {
			Map<String, Object> params = RequestUtils.getParameterMap(request);
			Tools.populate(bean, params);
			bean.setParentId(ParamUtil.getIntParameter(request, "parent", 0));
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setSort(ParamUtil.getIntParameter(request, "sort", 50));
			bean.setUpdateBy(RequestUtils.getActorId(request));
		}
		boolean ret = false;
		try {
			ret = sysTreeService.update(bean);
		} catch (Exception ex) {
			ret = false;
			logger.error(ex);
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("tree.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("tree.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示列表页面
		return new ModelAndView("/show_msg", modelMap);
	}

	@ResponseBody
	@RequestMapping("/savePerms")
	public byte[] savePerms(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug(paramMap);

		long nodeId = ParamUtil.getIntParameter(request, "nodeId", 0);
		String moduleId = ParamUtils.getString(paramMap, "moduleId");
		String entityId = ParamUtils.getString(paramMap, "entityId");
		String entryKey = ParamUtils.getString(paramMap, "entryKey");
		String dataCode = ParamUtils.getString(paramMap, "dataCode");

		EntityEntry entityEntry = null;

		if (nodeId > 0) {
			entityEntry = entryService.getEntityEntry(nodeId, entryKey);
		} else if (StringUtils.isNotEmpty(moduleId) && StringUtils.isNotEmpty(entityId)
				&& StringUtils.isNotEmpty(entryKey)) {
			entityEntry = entryService.getEntityEntry(moduleId, entityId, entryKey);
		}

		if (entityEntry != null && entityEntry.getId() != null) {
			entryService.deleteEntityEntry(entityEntry.getId());
			logger.debug(entityEntry.getId() + " remove ok.");
		}

		entityEntry = new EntityEntry();

		if (StringUtils.isEmpty(dataCode)) {
			dataCode = "entityEntryCode";
		}

		if (StringUtils.isEmpty(entityId)) {
			entityId = nodeId + "_" + entryKey;
		}

		Tools.populate(entityEntry, paramMap);
		entityEntry.setNodeId(nodeId);
		entityEntry.setEntityId(entityId);

		String x_departments = request.getParameter("x_departments");
		List<String> departments = StringTools.split(x_departments, ",");
		if (departments != null && departments.size() > 0) {
			Iterator<String> iter = departments.iterator();
			while (iter.hasNext()) {
				String value = iter.next();
				EntryPoint entryPoint = new EntryPoint();
				entryPoint.setEntityEntry(entityEntry);
				entryPoint.setEntryKey(entryKey);
				entryPoint.setEntityId(entityId);
				entryPoint.setName("DEPT");
				entryPoint.setValue(value);
				entityEntry.addEntryPoint(entryPoint);
			}
		}

		String x_roles = request.getParameter("x_roles");
		List<String> roles = StringTools.split(x_roles, ",");
		if (roles != null && roles.size() > 0) {
			Iterator<String> iter = roles.iterator();
			while (iter.hasNext()) {
				String value = iter.next();
				EntryPoint entryPoint = new EntryPoint();
				entryPoint.setEntityEntry(entityEntry);
				entryPoint.setEntryKey(entryKey);
				entryPoint.setEntityId(entityId);
				entryPoint.setName("ROLE");
				entryPoint.setValue(value);
				entityEntry.addEntryPoint(entryPoint);
			}
		}

		String x_users = request.getParameter("x_users");
		List<String> users = StringTools.split(x_users, ",");
		if (users != null && users.size() > 0) {
			Iterator<String> iter = users.iterator();
			while (iter.hasNext()) {
				String value = iter.next();
				EntryPoint entryPoint = new EntryPoint();
				entryPoint.setEntityEntry(entityEntry);
				entryPoint.setEntryKey(entryKey);
				entryPoint.setEntityId(entityId);
				entryPoint.setName("USER");
				entryPoint.setValue(value);
				entityEntry.addEntryPoint(entryPoint);
			}
		}

		entityEntry.setDataCode(dataCode);
		entityEntry.setCreateBy(RequestUtils.getActorId(request));

		if (entityEntry.getEntryPoints().size() > 0) {
			entryService.saveEntityEntry(entityEntry);
			return ResponseUtils.responseJsonResult(true);
		}

		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveSort")
	@ResponseBody
	public byte[] saveSort(HttpServletRequest request) {
		String items = request.getParameter("items");
		if (StringUtils.isNotEmpty(items)) {
			int sort = 0;
			List<TableModel> rows = new ArrayList<TableModel>();
			StringTokenizer token = new StringTokenizer(items, ",");
			while (token.hasMoreTokens()) {
				String item = token.nextToken();
				if (StringUtils.isNotEmpty(item)) {
					sort++;
					TableModel t1 = new TableModel();
					t1.setTableName("sys_tree");
					ColumnModel idColumn1 = new ColumnModel();
					idColumn1.setColumnName("ID");
					idColumn1.setValue(Long.parseLong(item));
					t1.setIdColumn(idColumn1);
					ColumnModel column = new ColumnModel();
					column.setColumnName("SORT");
					column.setValue(sort);
					t1.addColumn(column);
					rows.add(t1);

					TableModel t2 = new TableModel();
					t2.setTableName("sys_application");
					ColumnModel idColumn2 = new ColumnModel();
					idColumn2.setColumnName("NODEID");
					idColumn2.setValue(Long.parseLong(item));
					t2.setIdColumn(idColumn2);
					ColumnModel column2 = new ColumnModel();
					column2.setColumnName("SORT");
					column2.setValue(sort);
					t2.addColumn(column2);
					rows.add(t2);

					TableModel t3 = new TableModel();
					t3.setTableName("sys_department");
					ColumnModel idColumn3 = new ColumnModel();
					idColumn3.setColumnName("NODEID");
					idColumn3.setValue(Long.parseLong(item));
					t3.setIdColumn(idColumn3);
					ColumnModel column3 = new ColumnModel();
					column3.setColumnName("SORT");
					column3.setValue(sort);
					t3.addColumn(column3);
					rows.add(t3);
				}
			}
			try {
				DataServiceFactory.getInstance().updateAllTableData(rows);
				return ResponseUtils.responseResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setEntryService(IEntryService entryService) {
		this.entryService = entryService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	/**
	 * 显示导入页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("tree.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示列表页面
		return new ModelAndView("/modules/sys/tree/showImport", modelMap);
	}

	/**
	 * 显示左边菜单
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showLeft")
	public ModelAndView showLeft(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		ModelAndView forward = new ModelAndView("/modules/sys/tree/tree_left", modelMap);
		int parent = ParamUtil.getIntParameter(request, "parent", SysConstants.TREE_ROOT);
		request.setAttribute("parent", sysTreeService.findById(parent));
		String url = ParamUtil.getParameter(request, "url");
		request.setAttribute("url", url);

		String x_view = ViewProperties.getString("tree.showLeft");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return forward;
	}

	/**
	 * 显示所有列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showList")
	public ModelAndView showList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int parentId = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", SysConstants.PAGE_SIZE);
		if (parentId > 0) {
			SysTree parent = sysTreeService.findById(parentId);
			request.setAttribute("parent", parent);
		}
		PageResult pager = sysTreeService.getSysTreeList(parentId, pageNo, pageSize);
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("tree.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/tree_list", modelMap);
	}

	/**
	 * 显示主页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showMain")
	public ModelAndView showMain(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("tree.showMain");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/tree_frame", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showPerms")
	public ModelAndView showPerms(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long nodeId = ParamUtil.getIntParameter(request, "nodeId", 0);
		SysTree bean = sysTreeService.findById(nodeId);
		if (bean != null && bean.getParentId() > 0) {
			SysTree parent = sysTreeService.findById(bean.getParentId());
			bean.setParent(parent);
		}
		request.setAttribute("bean", bean);
		List<SysTree> list = new ArrayList<SysTree>();
		sysTreeService.getSysTree(list, 0, 0);
		request.setAttribute("parent", list);

		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

		String rowId = ParamUtils.getString(paramMap, "id");
		String moduleId = ParamUtils.getString(paramMap, "moduleId");
		String entityId = ParamUtils.getString(paramMap, "entityId");
		String entryKey = ParamUtils.getString(paramMap, "entryKey");
		String dataCode = ParamUtils.getString(paramMap, "dataCode");
		StringBuilder rolesBuffer = new StringBuilder();
		StringBuilder rolesNameBuffer = new StringBuilder();
		StringBuilder deptsBuffer = new StringBuilder();
		StringBuilder deptsNameBuffer = new StringBuilder();
		StringBuilder usersBuffer = new StringBuilder();
		StringBuilder usersNameBuffer = new StringBuilder();

		Map<Long, Role> roleMap = IdentityFactory.getRoleMap();
		Map<String, User> userMap = IdentityFactory.getUserMap();
		Map<Long, TreeModel> deptMap = IdentityFactory.getDepartmentMap();

		if (entryKey == null) {
			entryKey = "r";
		}

		EntityEntry entityEntry = null;

		if (StringUtils.isNotEmpty(moduleId) && StringUtils.isNotEmpty(entityId) && StringUtils.isNotEmpty(entryKey)) {
			entityEntry = entryService.getEntityEntry(moduleId, entityId, entryKey);
		} else if (nodeId > 0) {
			entityEntry = entryService.getEntityEntry(nodeId, entryKey);
		} else if (StringUtils.isNotEmpty(rowId)) {
			entityEntry = entryService.getEntityEntry(rowId);
		}

		if (entityEntry != null && entityEntry.getEntryPoints() != null) {
			Iterator<EntryPoint> iter = entityEntry.getEntryPoints().iterator();
			while (iter.hasNext()) {
				EntryPoint entryPoint = iter.next();
				String value = entryPoint.getValue();
				if ("ROLE".equals(entryPoint.getName())) {
					Role role = roleMap.get(Long.parseLong(value));
					if (role != null) {
						rolesBuffer.append(role.getRoleId()).append(',');
						rolesNameBuffer.append(role.getName()).append(',');
					}
				} else if ("DEPT".equals(entryPoint.getName())) {
					TreeModel treeModel = deptMap.get(Long.parseLong(value));
					if (treeModel != null) {
						deptsBuffer.append(treeModel.getId()).append(',');
						deptsNameBuffer.append(treeModel.getName()).append(',');
					}
				} else if ("USER".equals(entryPoint.getName())) {
					User user = userMap.get(value);
					if (user != null) {
						usersBuffer.append(user.getActorId()).append(',');
						usersNameBuffer.append(user.getName()).append(',');
					}
				}
			}
		}

		if (rolesBuffer.length() > 0) {
			rolesBuffer.delete(rolesBuffer.length() - 1, rolesBuffer.length());
		}

		if (rolesNameBuffer.length() > 0) {
			rolesNameBuffer.delete(rolesNameBuffer.length() - 1, rolesNameBuffer.length());
		}

		if (deptsBuffer.length() > 0) {
			deptsBuffer.delete(deptsBuffer.length() - 1, deptsBuffer.length());
		}

		if (deptsNameBuffer.length() > 0) {
			deptsNameBuffer.delete(deptsNameBuffer.length() - 1, deptsNameBuffer.length());
		}

		if (usersBuffer.length() > 0) {
			usersBuffer.delete(usersBuffer.length() - 1, usersBuffer.length());
		}

		if (usersNameBuffer.length() > 0) {
			usersNameBuffer.delete(usersNameBuffer.length() - 1, usersNameBuffer.length());
		}

		if (StringUtils.isEmpty(dataCode)) {
			dataCode = "entityEntryCode";
		}

		modelMap.put("entityEntry", entityEntry);
		modelMap.put("roleMap", roleMap);
		modelMap.put("x_roles", rolesBuffer.toString());
		modelMap.put("x_roles_name", rolesNameBuffer.toString());
		modelMap.put("x_users", usersBuffer.toString());
		modelMap.put("x_users_name", usersNameBuffer.toString());
		modelMap.put("x_departments", deptsBuffer.toString());
		modelMap.put("x_departments_name", deptsNameBuffer.toString());

		String x_view = ViewProperties.getString("tree.showPerms");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/showPerms", modelMap);
	}

	/**
	 * 显示排序页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showSort")
	public ModelAndView showSort(HttpServletRequest request, ModelMap modelMap) {
		// RequestUtils.setRequestParameterToAttribute(request);
		long nodeId = ParamUtil.getIntParameter(request, "nodeId", 0);
		if (nodeId > 0) {
			List<SysTree> trees = sysTreeService.getSysTreeList(nodeId);
			request.setAttribute("trees", trees);
		}

		String x_view = ViewProperties.getString("sys.tree.showSort");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/showSort", modelMap);
	}

	@RequestMapping("/showTop")
	public ModelAndView showTop(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("tree.showTop");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/tree_top", modelMap);
	}

	@RequestMapping("/showTree")
	public ModelAndView showTree(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("tree.showTree");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/tree/showTree", modelMap);
	}

	@ResponseBody
	@RequestMapping("/sort")
	public byte[] sort(@RequestParam(value = "parent") int parent, @RequestParam(value = "id") int id,
			@RequestParam(value = "operate") int operate) {
		logger.debug("parent:" + parent + "; id:" + id + "; operate:" + operate);
		try {
			SysTree bean = sysTreeService.findById(id);
			sysTreeService.sort(parent, bean, operate);
			return ResponseUtils.responseResult(true);
		} catch (Exception ex) {
		}
		return ResponseUtils.responseResult(false);
	}
}