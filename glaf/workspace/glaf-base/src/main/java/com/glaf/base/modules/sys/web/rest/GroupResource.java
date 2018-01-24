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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.Group;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.GroupQuery;
import com.glaf.base.modules.sys.service.GroupService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

@Controller("/rs/identity/group")
@Path("/rs/identity/group")
public class GroupResource {
	private static final Log logger = LogFactory.getLog(GroupResource.class);

	private GroupService groupService;

	private SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	@GET
	@POST
	@Path("detail")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] detail(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		String groupId = request.getParameter("groupId");
		if (groupId != null) {
			Group group = groupService.getGroup(groupId);
			if (group != null) {
				return group.toJsonObject().toJSONString().getBytes("UTF-8");
			}
		}
		return "".getBytes();
	}

	@GET
	@POST
	@Path("json")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] json(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		JSONObject result = new JSONObject();
		String groupId = request.getParameter("groupId");
		List<String> userIds = groupService.getUserIdsByGroupId(groupId);
		List<SysUser> users = sysUserService.getSysUserWithDeptList();
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		if (root != null && users != null) {
			logger.debug(root.toJsonObject().toJSONString());
			logger.debug("users size:" + users.size());
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept((int) root.getId(), 0);
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
							SysTree t = treeMap.get(Long.valueOf(user.getDeptId()));
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
								if (!treeModels.contains(treeModel)) {
									treeModels.add(treeModel);
								}
							}
						}
					}
				}
			}

			List<SysUser> allUsers = sysUserService.getSysUserList();
			if (allUsers != null && !allUsers.isEmpty()) {
				for (SysUser user : allUsers) {
					if (user.getDeptId() == 0) {
						TreeModel treeModel = new BaseTree();
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("actorId", user.getAccount());
						dataMap.put("actorId_enc", RequestUtils.encodeString(user.getAccount()));
						treeModel.setDataMap(dataMap);
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
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("jsonLeader")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] jsonLeader(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		JSONObject result = new JSONObject();
		String groupId = request.getParameter("groupId");
		List<String> userIds = groupService.getLeaderUserIdsByGroupId(groupId);
		List<SysUser> users = sysUserService.getSysUserWithDeptList();
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		if (root != null && users != null) {
			logger.debug(root.toJsonObject().toJSONString());
			logger.debug("users size:" + users.size());
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			// treeModels.add(root);
			List<SysTree> trees = sysTreeService.getAllSysTreeListForDept((int) root.getId(), 0);
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
							SysTree t = treeMap.get(Long.valueOf(user.getDeptId()));
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
								if (!treeModels.contains(treeModel)) {
									treeModels.add(treeModel);
								}
							}
						}
					}
				}
			}
			List<SysUser> allUsers = sysUserService.getSysUserList();
			if (allUsers != null && !allUsers.isEmpty()) {
				for (SysUser user : allUsers) {
					if (user.getDeptId() == 0) {
						TreeModel treeModel = new BaseTree();
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("actorId", user.getAccount());
						dataMap.put("actorId_enc", RequestUtils.encodeString(user.getAccount()));
						treeModel.setDataMap(dataMap);
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
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("listJson")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] listJson(@Context HttpServletRequest request, @Context UriInfo uriInfo) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		GroupQuery query = new GroupQuery();
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		SysUser user = RequestUtil.getLoginUser(request);
		String actorId = user.getAccount();
		String type = request.getParameter("type");
		query.type(type);
		query.actorId(actorId);
		query.createBy(actorId);

		JSONObject result = new JSONObject();
		int total = groupService.getGroupCountByQueryCriteria(query);
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

			List<Group> list = groupService.getGroupsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Group group : list) {
					JSONObject rowJSON = group.toJsonObject();
					rowJSON.put("groupId", group.getGroupId());
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
	@POST
	@Path("saveAdd")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public byte[] saveAdd(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		boolean ret = false;

		String type = request.getParameter("type");
		Group bean = new Group();
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		bean.setDesc(ParamUtil.getParameter(request, "desc"));
		bean.setCreateBy(RequestUtils.getActorId(request));
		bean.setUpdateBy(RequestUtils.getActorId(request));
		bean.setType(type);
		try {
			groupService.save(bean);
			ret = true;
		} catch (Exception ex) {
			logger.error(ex);
		}

		return ResponseUtils.responseResult(ret);
	}

	@POST
	@Path("saveGroupUsers")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public byte[] saveGroupUsers(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		String groupId = request.getParameter("groupId");
		String objectId = request.getParameter("userIds");
		if (StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(objectId)) {
			Set<String> userIds = new HashSet<String>();
			StringTokenizer token = new StringTokenizer(objectId, ",");
			while (token.hasMoreTokens()) {
				String userId = token.nextToken();
				userIds.add(userId);
			}
			try {
				groupService.saveGroupUsers(groupId, userIds);
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return ResponseUtils.responseJsonResult(false);
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
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public byte[] saveModify(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		RequestUtils.setRequestParameterToAttribute(request);
		String id = request.getParameter("groupId");
		Group bean = groupService.getGroup(id);
		if (bean != null) {
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setUpdateBy(RequestUtils.getActorId(request));
		}
		boolean ret = false;
		try {
			groupService.save(bean);
			ret = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ResponseUtils.responseResult(ret);
	}

	@javax.annotation.Resource
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@POST
	@Path("sort")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public byte[] sort(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		try {
			String groupId = request.getParameter("groupId");
			int operate = ParamUtil.getIntParameter(request, "operate", 0);
			logger.debug("groupId:" + groupId + ",operate:" + operate);
			groupService.sort(groupService.getGroup(groupId), operate);
			return ResponseUtils.responseResult(true);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return ResponseUtils.responseResult(false);
	}
}