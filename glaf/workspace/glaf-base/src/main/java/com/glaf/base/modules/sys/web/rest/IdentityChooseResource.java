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
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;

import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;

import com.glaf.base.modules.sys.SysConstants;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;

import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;

@Controller("/rs/base/identity/choose")
@Path("/rs/base/identity/choose")
public class IdentityChooseResource {
	private static final Log logger = LogFactory.getLog(IdentityChooseResource.class);

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected SysDepartmentService sysDepartmentService;

	@GET
	@POST
	@Path("deptJson")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] deptJson(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		JSONObject result = new JSONObject();
		String selecteds = request.getParameter("selecteds");
		List<Long> deptIds = StringTools.splitToLong(selecteds);
		long parentId = RequestUtils.getLong(request, "parentId");
		SysTree root = null;
		if (parentId > 0) {
			root = sysTreeService.findById(parentId);
		}
		if (root == null) {
			root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		}
		if (root != null) {
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					deptMap.put(dept.getNodeId(), dept);
				}
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					if (dept != null) {
						Map<String, Object> dataMap = tree.getDataMap();
						if (dataMap == null) {
							dataMap = new HashMap<String, Object>();
						}
						if (deptIds.contains(dept.getId())) {
							tree.setChecked(true);
							dataMap.put("checked", true);
						} else {
							dataMap.put("checked", false);
						}
						dataMap.put("deptId", dept.getId());
						tree.setDataMap(dataMap);
						treeModels.add(tree);
					}
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("json")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] json(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		JSONObject result = new JSONObject();
		String selecteds = request.getParameter("selecteds");
		List<String> userIds = StringTools.split(selecteds);
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
					treeMap.put(dept.getId(), tree);
				}
				for (SysTree tree : trees) {
					treeModels.add(tree);
					SysDepartment dept = tree.getDepartment();
					if (dept != null && dept.getId() > 0) {
						for (SysUser user : users) {
							SysTree t = treeMap.get(user.getDeptId());
							if (dept.getId() == user.getDeptId() && t != null) {
								TreeModel treeModel = new BaseTree();
								treeModel.setParentId(t.getId());
								treeModel.setId(SysConstants.BILLION + user.getAccount().hashCode());
								treeModel.setCode(user.getAccount());
								treeModel.setName(user.getAccount() + " " + user.getName());
								treeModel.setIconCls("icon-user");
								treeModel.setIcon(request.getContextPath() + "/images/user.gif");
								if (userIds != null && userIds.contains(user.getAccount())) {
									treeModel.setChecked(true);
								}
								if (!treeModels.contains(treeModel)) {
									treeModels.add(treeModel);
								}
							}
						}
					}
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("nodeJson")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] nodeJson(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		logger.debug("params:" + RequestUtils.getParameterMap(request));
		JSONObject result = new JSONObject();
		String selecteds = request.getParameter("selecteds");
		List<Long> nodeIds = StringTools.splitToLong(selecteds);
		long parentId = RequestUtils.getLong(request, "parentId");
		SysTree root = null;
		if (parentId > 0) {
			root = sysTreeService.findById(parentId);
		}
		if (root != null) {
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			List<SysTree> trees = sysTreeService.getSysTreeListWithChildren(parentId);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("tree size:" + trees.size());
				for (SysTree tree : trees) {
					Map<String, Object> dataMap = tree.getDataMap();
					if (dataMap == null) {
						dataMap = new HashMap<String, Object>();
					}
					if (nodeIds.contains(tree.getId())) {
						tree.setChecked(true);
						dataMap.put("checked", true);
					} else {
						dataMap.put("checked", false);
					}
					tree.setDataMap(dataMap);
					treeModels.add(tree);
				}
			}
			treeModels.remove(root);
			logger.debug("treeModels size:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
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
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@GET
	@POST
	@Path("treeJson")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] treeJson(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		JSONObject result = new JSONObject();
		Long parentId = RequestUtils.getLong(request, "parentId");
		String selecteds = request.getParameter("selecteds");
		List<String> deptIds = StringTools.split(selecteds);
		SysTree root = sysTreeService.findById(parentId);
		if (root != null) {
			logger.debug(root.toJsonObject().toJSONString());

			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					treeMap.put(dept.getId(), tree);
				}

				for (SysTree tree : trees) {
					if (deptIds.contains(String.valueOf(tree.getId()))) {
						tree.setChecked(true);
					}
					treeModels.add(tree);
				}
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("userJson")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] userJson(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		logger.debug(RequestUtils.getParameterMap(request));
		JSONObject result = new JSONObject();
		long parentId = RequestUtils.getLong(request, "parentId");
		String selecteds = request.getParameter("selecteds");
		List<String> userIds = StringTools.split(selecteds);
		List<SysUser> users = sysUserService.getSysUserWithDeptList();
		SysTree root = null;
		if (parentId > 0) {
			root = sysTreeService.findById(parentId);
		}
		if (root == null) {
			root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		}
		if (root != null && users != null) {
			logger.debug(root.toJsonObject().toJSONString());
			logger.debug("users size:" + users.size());
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept(root.getId(), 0);
			if (trees != null && !trees.isEmpty()) {
				logger.debug("dept tree size:" + trees.size());
				Map<Long, SysTree> treeMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					SysDepartment dept = tree.getDepartment();
					treeMap.put(dept.getId(), tree);
				}
				for (SysTree tree : trees) {
					dataMap = new HashMap<String, Object>();
					dataMap.put("checked", false);
					tree.setDataMap(dataMap);
					treeModels.add(tree);
					SysDepartment dept = tree.getDepartment();
					if (dept != null && dept.getId() > 0) {
						for (SysUser user : users) {
							SysTree t = treeMap.get(user.getDeptId());
							if (dept.getId() == user.getDeptId() && t != null) {
								TreeModel treeModel = new BaseTree();
								dataMap = new HashMap<String, Object>();
								dataMap.put("actorId", user.getAccount());
								dataMap.put("actorId_enc", RequestUtils.encodeString(user.getAccount()));
								if (userIds.contains(user.getActorId())
										|| userIds.contains(user.getActorId().toLowerCase())
										|| userIds.contains(user.getActorId().toUpperCase())) {
									dataMap.put("checked", true);
								} else {
									dataMap.put("checked", false);
								}
								treeModel.setDataMap(dataMap);
								treeModel.setParentId(t.getId());
								treeModel.setId(SysConstants.BILLION + user.getAccount().hashCode());
								treeModel.setCode(user.getAccount());
								treeModel.setName(user.getAccount() + " " + user.getName());
								treeModel.setIconCls("icon-user");
								treeModel.setIcon(request.getContextPath() + "/images/user.gif");
								if (userIds.contains(user.getAccount())) {
									treeModel.setChecked(true);
								}
								treeModels.add(treeModel);
							}
						}
					}
				}
			}
			if (parentId == 0) {
				List<SysUser> allUsers = sysUserService.getSysUserList();
				if (allUsers != null && !allUsers.isEmpty()) {
					for (SysUser user : allUsers) {
						if (user.getDeptId() == 0) {
							TreeModel treeModel = new BaseTree();
							dataMap = new HashMap<String, Object>();
							dataMap.put("actorId", user.getAccount());
							dataMap.put("actorId_enc", RequestUtils.encodeString(user.getAccount()));
							if (userIds.contains(user.getActorId()) || userIds.contains(user.getActorId().toLowerCase())
									|| userIds.contains(user.getActorId().toUpperCase())) {
								dataMap.put("checked", true);
							} else {
								dataMap.put("checked", false);
							}
							treeModel.setDataMap(dataMap);
							treeModel.setParentId(root.getId());
							treeModel.setId(SysConstants.BILLION + user.getAccount().hashCode());
							treeModel.setCode(user.getAccount());
							treeModel.setName(user.getAccount() + " " + user.getName());
							treeModel.setIconCls("icon-user");
							treeModel.setIcon(request.getContextPath() + "/images/user.gif");
							if (userIds.contains(user.getAccount())) {
								treeModel.setChecked(true);
							}
							if (!treeModels.contains(treeModel)) {
								treeModels.add(treeModel);
							}
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

}