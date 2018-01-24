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

package com.glaf.base.modules.branch.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysRoleQuery;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.sys.util.SysRoleDomainFactory;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.TreeModel;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.security.LoginContext;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/rs/branch/role")
@Path("/rs/branch/role")
// /rs/branch/rolebatchDelete?rowIds=181819
public class BranchRoleResource {
	private static final Log logger = LogFactory.getLog(BranchRoleResource.class);

	protected SysApplicationService sysApplicationService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected SysUserRoleService sysUserRoleService;

	/**
	 * 批量删除信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Path("batchDelete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] batchDelete(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = false;
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		String objectIds = request.getParameter("rowIds");
		List<Long> list = StringTools.splitToLong(objectIds);
		if (list != null && !list.isEmpty()) {
			int index = 0;
			long[] ids = new long[list.size()];
			for (Long x : list) {
				ids[index++] = x;
			}
			try {
				if (ids != null && ids.length > 0) {
					for (int i = 0; i < ids.length; i++) {
						long id = ids[i];
						SysRole sysRole = sysRoleService.findById(id);
						if (sysRole != null && (StringUtils.equalsIgnoreCase(actorId, sysRole.getCreateBy())
								|| loginContext.isSystemAdministrator())) {
							sysRoleService.delete(id);
						}
					}
				}
			} catch (Exception ex) {
				logger.error(ex);
				ret = false;
			}
		}

		if (ret) {// 成功
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@POST
	@Path("data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysRoleQuery query = new SysRoleQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		SysRoleDomainFactory.processDataRequest(dataRequest);

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		if (!loginContext.isSystemAdministrator()) {
			query.createBy(actorId);
		}

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
		int total = sysRoleService.getSysRoleCountByQueryCriteria(query);
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

			List<SysRole> list = sysRoleService.getSysRolesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (SysRole sysRole : list) {
					JSONObject rowJSON = sysRole.toJsonObject();
					rowJSON.put("id", sysRole.getId());
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
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] json(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysRoleQuery query = new SysRoleQuery();
		Tools.populate(query, params);

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		if (!loginContext.isSystemAdministrator()) {
			query.createBy(actorId);
		}

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
		int total = sysRoleService.getSysRoleCountByQueryCriteria(query);
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

			List<SysRole> list = sysRoleService.getSysRolesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (SysRole sysRole : list) {
					JSONObject rowJSON = sysRole.toJsonObject();
					rowJSON.put("id", sysRole.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("roleMenusJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] roleMenusJson(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);
		SysRole sysRole = sysRoleService.findByCode("BranchAdmin");
		SysRole role = sysRoleService.findById(roleId);
		if (sysRole != null && role != null) {
			if (loginContext.isSystemAdministrator()
					|| StringUtils.equalsIgnoreCase(loginContext.getActorId(), role.getCreateBy())) {
				List<SysTree> list = new ArrayList<SysTree>();
				List<TreeModel> treeModels = new ArrayList<TreeModel>();

				List<SysApplication> myapps = sysApplicationService.getSysApplicationsByRoleId(sysRole.getId());
				if (myapps != null && !myapps.isEmpty()) {
					List<Long> nodeIds = new ArrayList<Long>();
					for (SysApplication app : myapps) {
						nodeIds.add(app.getNodeId());
					}
					SysTreeQuery query = new SysTreeQuery();
					query.nodeIds(nodeIds);
					List<SysTree> treeList = sysTreeService.getApplicationSysTrees(query);
					if (treeList != null && !treeList.isEmpty()) {
						list.addAll(treeList);
						for (SysTree tree : list) {
							treeModels.add(tree);
						}
					}
				}

				List<SysApplication> apps = sysApplicationService.getSysApplicationsByRoleId(role.getId());
				if (apps != null && !apps.isEmpty()) {
					for (SysTree tree : list) {
						for (SysApplication app : apps) {
							if (tree.getId() == app.getNodeId()) {
								tree.setChecked(true);
							}
						}
					}
				}

				List<SysRole> roles = sysRoleService.getSysRoleList();
				if (roles != null && !roles.isEmpty()) {
					for (SysRole r : roles) {
						if (r.getId() == sysRole.getId() || r.getId() == roleId) {
							continue;
						}
						if (StringUtils.equalsIgnoreCase(r.getIsUseBranch(), "Y")) {
							myapps = sysApplicationService.getSysApplicationsByRoleId(r.getId());
							if (myapps != null && !myapps.isEmpty()) {
								List<Long> nodeIds = new ArrayList<Long>();
								for (SysApplication app : myapps) {
									nodeIds.add(app.getNodeId());
								}
								SysTreeQuery query = new SysTreeQuery();
								query.nodeIds(nodeIds);
								List<SysTree> treeList = sysTreeService.getApplicationSysTrees(query);
								if (treeList != null && !treeList.isEmpty()) {
									list.addAll(treeList);
									for (SysTree tree : list) {
										if (!treeModels.contains(tree)) {
											treeModels.add(tree);
										}
									}
								}
							}
						}
					}
				}

				logger.debug("treeModels:" + treeModels.size());
				TreeHelper treeHelper = new TreeHelper();
				JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
				// JacksonTreeHelper treeHlper = new JacksonTreeHelper();
				// ArrayNode jsonArray = treeHlper.getTreeArrayNode(treeModels);
				logger.debug(jsonArray.toJSONString());
				return jsonArray.toJSONString().getBytes("UTF-8");
			}
		}
		JSONArray jsonArray = new JSONArray();
		return jsonArray.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("roleUsersJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] roleUsersJson(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		List<SysUser> roleUsers = null;
		JSONObject result = new JSONObject();
		long roleId = RequestUtils.getLong(request, "roleId");

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		SysRole sysRole = null;
		if (roleId > 0) {
			sysRole = sysRoleService.findById(roleId);
		}

		if (sysRole != null && (loginContext.isSystemAdministrator()
				|| StringUtils.equalsIgnoreCase(actorId, sysRole.getCreateBy()))) {
		} else {
			throw new RuntimeException(" role is null or access deny.");
		}

		if (roleId > 0) {
			roleUsers = sysUserService.getSysUsersByRoleId(roleId);
		} else {
			String roleCode = request.getParameter("roleCode");
			if (StringUtils.isNotEmpty(roleCode)) {
				roleUsers = sysUserService.getSysUsersByRoleCode(roleCode);
			}
		}
		Collection<String> userIds = new HashSet<String>();
		if (roleUsers != null && !roleUsers.isEmpty()) {
			for (SysUser u : roleUsers) {
				userIds.add(u.getAccount());
			}
		}
		List<SysUser> allUsers = sysUserService.getSysUserList();
		List<SysUser> users = sysUserService.getSysUserWithDeptList();
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		if (root != null && users != null) {
			logger.debug(root.toJsonObject().toJSONString());
			logger.debug("users size:" + users.size());
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					if (dept != null) {
						treeMap.put(dept.getId(), tree);
					}
				}

				for (SysTree tree : trees) {
					treeModels.add(tree);
					SysDepartment dept = tree.getDepartment();
					if (dept != null && dept.getId() > 0) {
						for (SysUser user : users) {
							if (user.isSystemAdmin()) {
								continue;
							}
							SysTree t = treeMap.get(user.getDeptId());
							if (dept.getId() == user.getDeptId() && t != null) {
								TreeModel treeModel = new BaseTree();
								treeModel.setParentId(t.getId());
								treeModel.setId(SysConstants.BILLION + user.getAccount().hashCode());
								treeModel.setCode(user.getAccount());
								treeModel.setName(user.getAccount() + " " + user.getName());
								treeModel.setIconCls("icon-user");
								treeModel.setIcon(request.getContextPath() + "/images/user.png");
								if (userIds != null && userIds.contains(user.getAccount())) {
									treeModel.setChecked(true);
								}
								treeModels.add(treeModel);
							}
						}
					}
				}
			}
			if (allUsers != null && !allUsers.isEmpty()) {
				for (SysUser user : allUsers) {
					if (user.isSystemAdmin()) {
						continue;
					}
					if (user.getDeptId() == 0) {
						TreeModel treeModel = new BaseTree();
						treeModel.setParentId(root.getId());
						treeModel.setId(SysConstants.BILLION + user.getAccount().hashCode());
						treeModel.setCode(user.getAccount());
						treeModel.setName(user.getAccount() + " " + user.getName());
						treeModel.setIconCls("icon-user");
						treeModel.setIcon(request.getContextPath() + "/images/user.png");
						if (userIds != null && userIds.contains(user.getAccount())) {
							treeModel.setChecked(true);
						}
						if (!treeModels.contains(treeModel)) {
							treeModels.add(treeModel);
						}
					}
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			// logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Path("saveAdd")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveAdd(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		boolean ret = false;
		try {
			if (loginContext.isSystemAdministrator() || loginContext.getRoles().contains("BranchAdmin")) {
				SysRole bean = new SysRole();
				bean.setName(ParamUtil.getParameter(request, "name"));
				bean.setContent(ParamUtil.getParameter(request, "content"));
				bean.setIndexUrl(ParamUtil.getParameter(request, "indexUrl"));
				bean.setIsUseBranch("N");
				bean.setSort(ParamUtil.getIntParameter(request, "sort", 0));
				bean.setCreateBy(RequestUtils.getActorId(request));
				bean.setUpdateBy(RequestUtils.getActorId(request));
				ret = sysRoleService.create(bean);
			}
		} catch (Exception ex) {
			logger.error(ex);
			ret = false;
		}

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
	@POST
	@Path("saveModify")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveModify(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		boolean ret = false;
		try {
			SysRole bean = null;
			if (id > 0) {
				bean = sysRoleService.findById(id);
			}

			if (bean != null && (loginContext.isSystemAdministrator()
					|| StringUtils.equalsIgnoreCase(actorId, bean.getCreateBy()))) {
				bean.setName(ParamUtil.getParameter(request, "name"));
				bean.setContent(ParamUtil.getParameter(request, "content"));
				bean.setIndexUrl(ParamUtil.getParameter(request, "indexUrl"));
				bean.setIsUseBranch(ParamUtil.getParameter(request, "isUseBranch"));
				bean.setSort(ParamUtil.getIntParameter(request, "sort", 0));
				bean.setUpdateBy(RequestUtils.getActorId(request));
				ret = sysRoleService.update(bean);
			} else {
				throw new RuntimeException(" role is null or access deny.");
			}
		} catch (Exception ex) {
			logger.error(ex);
			ret = false;
		}

		if (ret) {// 保存成功
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@POST
	@Path("saveRoleMenus")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveRoleMenus(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		long roleId = RequestUtils.getLong(request, "roleId");
		if (roleId > 0) {
			SysRole role = sysRoleService.findById(roleId);
			if (role != null) {
				LoginContext loginContext = RequestUtils.getLoginContext(request);
				String actorId = loginContext.getActorId();
				if (loginContext.isSystemAdministrator() || StringUtils.equalsIgnoreCase(actorId, role.getCreateBy())) {
					String x_nodeIds = request.getParameter("nodeIds");
					List<Long> nodeIds = new ArrayList<Long>();
					String[] ids = x_nodeIds.split(",");
					for (int i = 0; i < ids.length; i++) {
						String nodeId = ids[i];
						nodeIds.add(Long.parseLong(nodeId));
					}
					if (!nodeIds.isEmpty()) {
						long parentId = ParamUtil.getIntParameter(request, "parentId", 3);
						List<Long> appIds = new ArrayList<Long>();
						List<SysTree> list = sysApplicationService.getTreeWithApplicationList(parentId);
						for (SysTree tree : list) {
							if (nodeIds.contains(tree.getId())) {
								if (!appIds.contains(tree.getApp().getId())) {
									appIds.add(tree.getApp().getId());
								}
							}
						}
						if (appIds != null && !appIds.isEmpty()) {
							sysApplicationService.saveRoleApplications(roleId, appIds);
						}
						return ResponseUtils.responseJsonResult(true);
					}
				}
			}
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@POST
	@Path("saveRoleUsers")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveRoleUsers(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		long roleId = RequestUtils.getLong(request, "roleId");
		if (roleId > 0) {
			SysRole role = sysRoleService.findById(roleId);
			if (role != null) {
				LoginContext loginContext = RequestUtils.getLoginContext(request);
				String actorId = loginContext.getActorId();
				if (loginContext.isSystemAdministrator() || StringUtils.equalsIgnoreCase(actorId, role.getCreateBy())) {
					String userIds = request.getParameter("userIds");
					List<String> actorIds = new ArrayList<String>();
					String[] ids = userIds.split(",");
					for (int i = 0; i < ids.length; i++) {
						String userId = ids[i];
						actorIds.add(userId);
					}
					if (!actorIds.isEmpty()) {
						sysUserRoleService.saveRoleUsers(roleId, 0, actorIds);
						return ResponseUtils.responseJsonResult(true);
					}
				}
			}
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@javax.annotation.Resource
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@POST
	@Path("sort")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] sort(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		long id = ParamUtil.getIntParameter(request, "id", 0);
		int operate = ParamUtil.getIntParameter(request, "operate", 0);
		logger.debug("id:" + id + ",operate:" + operate);
		if (id > 0) {
			SysRole role = sysRoleService.findById(id);
			if (role != null) {
				LoginContext loginContext = RequestUtils.getLoginContext(request);
				String actorId = loginContext.getActorId();
				if (loginContext.isSystemAdministrator() || StringUtils.equalsIgnoreCase(actorId, role.getCreateBy())) {
					sysRoleService.sort(role, operate);
					return ResponseUtils.responseResult(true);
				}
			}
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
	@POST
	@Path("update")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] update(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getIntParameter(request, "id", 0);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		boolean ret = false;
		try {
			SysRole bean = null;
			if (id > 0) {
				bean = sysRoleService.findById(id);
			}
			if (bean != null && (loginContext.isSystemAdministrator()
					|| StringUtils.equalsIgnoreCase(actorId, bean.getCreateBy()))) {
				bean.setName(ParamUtil.getParameter(request, "name"));
				bean.setContent(ParamUtil.getParameter(request, "content"));
				bean.setIndexUrl(ParamUtil.getParameter(request, "indexUrl"));
				bean.setIsUseBranch(ParamUtil.getParameter(request, "isUseBranch"));
				bean.setSort(ParamUtil.getIntParameter(request, "sort", 0));
				bean.setUpdateBy(RequestUtils.getActorId(request));
				ret = sysRoleService.update(bean);
			} else {
				throw new RuntimeException(" role is null or access deny.");
			}
		} catch (Exception ex) {
			logger.error(ex);
			ret = false;
		}
		if (ret) {// 保存成功
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}
}