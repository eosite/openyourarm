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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
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
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

@Controller("/identity/group")
@RequestMapping("/identity/group")
public class GroupController {
	private static final Log logger = LogFactory.getLog(GroupController.class);

	private GroupService groupService;

	private SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	/**
	 * 批量删除信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/batchDelete")
	public ModelAndView batchDelete(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String rowId = RequestUtils.getString(request, "groupIds", "");
		boolean ret = true;
		try {
			if (!StringUtils.isEmpty(rowId)) {
				groupService.deleteById(rowId);
			}
		} catch (Exception ex) {
			logger.error(ex);
			ret = false;
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.delete_success"));
		} else {// 失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);

		// 显示列表页面
		return new ModelAndView("/show_msg2", modelMap);
	}

	@RequestMapping("/batchDeleteGroupLeader")
	public ModelAndView batchDeleteGroupLeader(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String groupId = RequestUtils.getString(request, "groupId", "");
		String userIds = RequestUtils.getString(request, "userIds", "");
		GroupQuery query = new GroupQuery();
		boolean ret = true;
		try {
			if (!StringUtils.isEmpty(groupId) && !StringUtils.isEmpty(userIds)) {
				String[] userIdsArray = userIds.split(",");
				List<String> userIdsList = new ArrayList<String>();
				for (int i = 0; i < userIdsArray.length; i++) {
					userIdsList.add(userIdsArray[i]);
				}
				query.setGroupId(groupId);
				query.setUserIds(userIdsList);
				groupService.deleteGroupLeadersByQuery(query);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			ret = false;
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.delete_success"));
		} else {// 失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示列表页面
		return new ModelAndView("/show_msg2", modelMap);
	}

	@RequestMapping("/batchDeleteGroupUser")
	public ModelAndView batchDeleteGroupUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String groupId = RequestUtils.getString(request, "groupId", "");
		String userIds = RequestUtils.getString(request, "userIds", "");
		GroupQuery query = new GroupQuery();
		boolean ret = true;
		try {
			if (!StringUtils.isEmpty(groupId) && !StringUtils.isEmpty(userIds)) {
				String[] userIdsArray = userIds.split(",");
				List<String> userIdsList = new ArrayList<String>();
				for (int i = 0; i < userIdsArray.length; i++) {
					userIdsList.add(userIdsArray[i]);
				}
				query.setGroupId(groupId);
				query.setUserIds(userIdsList);
				groupService.deleteGroupUsersByQuery(query);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			ret = false;
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.delete_success"));
		} else {// 失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示列表页面
		return new ModelAndView("/show_msg2", modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String id = request.getParameter("groupId");
		Group bean = groupService.getGroup(id);
		request.setAttribute("bean", bean);

		String x_view = ViewProperties.getString("group.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/identity/group/group_edit", modelMap);
	}

	/**
	 * 显示群组用户页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("groupLeaders")
	public ModelAndView groupLeaders(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("group.groupLeaders");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示群组用户页面
		return new ModelAndView("/modules/identity/group/groupLeaders", modelMap);
	}

	/**
	 * 显示群组用户页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/groupUsers")
	public ModelAndView groupUsers(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("group.groupUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示群组用户页面
		return new ModelAndView("/modules/identity/group/group_users", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
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
					treeMap.put(dept.getId(), tree);
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
								treeModel.setIcon(request.getContextPath() + "/icons/icons/user.gif");
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
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("group.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/identity/group/list", modelMap);
	}

	@RequestMapping("/listGroupLeader")
	public ModelAndView listGroupLeader(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("group.listGroupLeader");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/identity/group/listGroupLeader", modelMap);
	}

	@RequestMapping("/listGroupLeaderJson")
	@ResponseBody
	public byte[] listGroupLeaderJson(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		GroupQuery query = new GroupQuery();
		Tools.populate(query, params);
		// String groupId=RequestUtils.getString(request, "groupId","");
		List<Group> list = groupService.getGroupLeadersByGroupId(query);
		JSONObject result = new JSONObject();
		int start = 0;
		if (list != null && !list.isEmpty()) {
			JSONArray rowsJSON = new JSONArray();

			result.put("rows", rowsJSON);

			for (Group group : list) {
				JSONObject rowJSON = group.toJsonObject();
				if (group.getUserId() != null) {
					rowJSON.put("userId", group.getUserId());
				}
				if (group.getUname() != null) {
					rowJSON.put("uname", group.getUname());
				}
				if (group.getDname() != null) {
					rowJSON.put("dname", group.getDname());
				}
				rowJSON.put("startIndex", ++start);
				rowsJSON.add(rowJSON);
			}

		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", 0);
		}
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping("/listGroupUser")
	public ModelAndView listGroupUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("group.listGroupUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/identity/group/listGroupUser", modelMap);
	}

	@RequestMapping("/listGroupUserJson")
	@ResponseBody
	public byte[] listGroupUserJson(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		GroupQuery query = new GroupQuery();
		Tools.populate(query, params);
		// String groupId=RequestUtils.getString(request, "groupId","");
		List<Group> list = groupService.getGroupUsersByGroupId(query);
		JSONObject result = new JSONObject();
		int start = 0;
		if (list != null && !list.isEmpty()) {
			JSONArray rowsJSON = new JSONArray();

			result.put("rows", rowsJSON);

			for (Group group : list) {
				JSONObject rowJSON = group.toJsonObject();
				if (group.getUserId() != null) {
					rowJSON.put("userId", group.getUserId());
				}
				if (group.getUname() != null) {
					rowJSON.put("uname", group.getUname());
				}
				if (group.getDname() != null) {
					rowJSON.put("dname", group.getDname());
				}
				rowJSON.put("startIndex", ++start);
				rowsJSON.add(rowJSON);
			}

		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", 0);
		}
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping("/listJson")
	@ResponseBody
	public byte[] listJson(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		GroupQuery query = new GroupQuery();
		Tools.populate(query, params);

		SysUser login = RequestUtil.getLoginUser(request);
		if (!login.isSystemAdmin()) {
			query.setManageUsersLike(login.getAccount());
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
		orderName = ParamUtil.getParameter(request, "sort", "");
		order = ParamUtil.getParameter(request, "order", "");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		String type = request.getParameter("type");
		query.type(type);

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
				query.setOrderBy(orderName + " " + order);
				// query.setSortOrder(orderName);
				// if (StringUtils.equals(order, "desc")) {
				// query.setSortOrder(" desc ");
				// }
			}

			List<Group> list = groupService.getGroupsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Group group : list) {
					JSONObject rowJSON = group.toJsonObject();
					rowJSON.put("startIndex", ++start);
					rowJSON.put("groupId", group.getGroupId());
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", 0);
		}
		return result.toString().getBytes("UTF-8");
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
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("group.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/identity/group/group_add", modelMap);
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
		String id = request.getParameter("groupId");
		Group bean = groupService.getGroup(id);
		request.setAttribute("bean", bean);

		String x_view = ViewProperties.getString("group.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/identity/group/group_modify", modelMap);
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
			ex.printStackTrace();
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.add_failure"));
		}
		MessageUtils.addMessages(request, messages);

		// 显示列表页面
		return new ModelAndView("/show_msg", modelMap);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public byte[] saveEdit(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String type = request.getParameter("type");
		String id = request.getParameter("groupId");
		Group bean = groupService.getGroup(id);
		if (null == bean) {
			bean = new Group();
		}
		Tools.populate(bean, params);
		// bean.setName(ParamUtil.getParameter(request, "name"));
		// bean.setDesc(ParamUtil.getParameter(request, "desc"));

		bean.setCreateBy(RequestUtils.getActorId(request));
		// bean.setUpdateBy(RequestUtils.getActorId(request));
		bean.setType(type);
		try {
			groupService.save(bean);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/saveGroupLeaders")
	@ResponseBody
	public byte[] saveGroupLeaders(HttpServletRequest request) {
		String groupId = request.getParameter("rowId");
		String objectId = request.getParameter("userIds");
		String type = RequestUtils.getString(request, "type", "");
		if (StringUtils.isNotEmpty(groupId)) {
			Map<String, User> userMap = IdentityFactory.getUserMap();
			Set<String> userIds = new HashSet<String>();
			if (StringUtils.isNotEmpty(objectId)) {
				StringTokenizer token = new StringTokenizer(objectId, ",");
				while (token.hasMoreTokens()) {
					String userId = token.nextToken();
					if (userMap.containsKey(userId)) {
						userIds.add(userId);
					}
				}
			}
			try {
				if (StringUtils.isEmpty(type)) {
					groupService.saveGroupLeaders(groupId, userIds);
				} else {
					groupService.saveOrUpdateGroupLeaders(groupId, userIds);
				}
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return ResponseUtils.responseJsonResult(true);
	}

	@RequestMapping("/saveGroupUsers")
	@ResponseBody
	public byte[] saveGroupUsers(HttpServletRequest request) {
		String groupId = request.getParameter("rowId");
		String objectId = request.getParameter("userIds");
		String type = RequestUtils.getString(request, "type", "");
		if (StringUtils.isNotEmpty(groupId)) {
			Map<String, User> userMap = IdentityFactory.getUserMap();
			Set<String> userIds = new HashSet<String>();
			if (StringUtils.isNotEmpty(objectId)) {
				StringTokenizer token = new StringTokenizer(objectId, ",");
				while (token.hasMoreTokens()) {
					String userId = token.nextToken();
					if (userMap.containsKey(userId)) {
						userIds.add(userId);
					}
				}
			}
			try {
				if (StringUtils.isEmpty(type)) {
					groupService.saveGroupUsers(groupId, userIds);
				} else {
					groupService.saveOrUpdateGroupUsers(groupId, userIds);
				}
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return ResponseUtils.responseJsonResult(true);
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

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("group.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		// 显示列表页面
		return new ModelAndView("/show_msg", modelMap);
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
		String type = request.getParameter("type");
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", SysConstants.PAGE_SIZE);
		GroupQuery query = new GroupQuery();
		String nameLike = ParamUtil.getParameter(request, "nameLike", "");
		String descLike = ParamUtil.getParameter(request, "descLike", "");
		if (StringUtils.isNotEmpty(nameLike)) {
			query.setNameLike(nameLike);
		}
		if (StringUtils.isNotEmpty(descLike)) {
			query.setDescLike(descLike);
		}
		request.setAttribute("nameLike", nameLike);
		request.setAttribute("descLike", descLike);
		PageResult pager = groupService.getGroupList(type, null, pageNo, pageSize, query);
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("group.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		// 显示列表页面
		return new ModelAndView("/modules/identity/group/group_list", modelMap);
	}

	@RequestMapping("/sort")
	@ResponseBody
	public byte[] sort(@RequestParam(value = "groupId") String groupId, @RequestParam(value = "operate") int operate) {
		try {
			// String groupId = request.getParameter("groupId");
			// int operate = ParamUtil.getIntParameter(request, "operate", 0);
			logger.debug("groupId:" + groupId + ",operate:" + operate);
			groupService.sort(groupService.getGroup(groupId), operate);
			return ResponseUtils.responseResult(true);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return ResponseUtils.responseResult(false);
	}
}