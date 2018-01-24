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

package com.glaf.base.modules.branch.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.factory.SysLogFactory;
 
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.model.UserRole;
import com.glaf.base.modules.sys.query.SysRoleQuery;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.query.UserRoleQuery;
import com.glaf.base.modules.sys.service.ComplexUserService;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;

import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;

@Controller("/branch/user")
@RequestMapping("/branch/user")
public class BranchUserController {
	private static final Log logger = LogFactory.getLog(BranchUserController.class);

	protected ComplexUserService complexUserService;

	protected DictoryService dictoryService;

	protected SysDepartmentService sysDepartmentService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	/**
	 * 增加角色用户
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/addRoleUser")
	public ModelAndView addRoleUser(HttpServletRequest request, ModelMap modelMap) {
		logger.debug("--------------------addRoleUser----------------");
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		boolean success = false;
		String actorId = RequestUtils.getActorId(request);
		List<Long> nodeIds = complexUserService.getUserManageBranchNodeIds(actorId);

		SysDepartment department = sysDepartmentService.getSysDepartment(deptId);
		if (department != null) {
			SysTree tree = sysTreeService.findById(department.getNodeId());
			if (tree != null && nodeIds.contains(tree.getId())) {

				String[] userIds = ParamUtil.getParameterValues(request, "id");
				for (int i = 0; i < userIds.length; i++) {
					SysUser user = sysUserService.findById(userIds[i]);
					if (user != null) {
						logger.debug(user.getName());
						sysUserService.createRoleUser(roleId, user.getActorId());
					}
				}

			}
		}

		ViewMessages messages = new ViewMessages();
		if (success) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.add_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.add_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("/show_msg", modelMap);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public byte[] delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String ids = request.getParameter("userIds");
		if (StringUtils.isNotEmpty(ids)) {
			List<String> userIds = StringTools.split(ids);
			if (userIds != null && !userIds.isEmpty()) {
				try {
					List<SysDepartment> depts = complexUserService.getUserManageDeptments(loginContext.getActorId());
					List<Long> deptIds = new ArrayList<Long>();
					if (depts != null && !depts.isEmpty()) {
						for (SysDepartment dept : depts) {
							deptIds.add(dept.getId());
						}
					}

					boolean success = false;
					for (String userId : userIds) {
						success = false;
						SysUser user = sysUserService.findById(userId);
						if (!user.isSystemAdmin()) {
							if (loginContext.isSystemAdministrator()) {
								success = sysUserService.delete(user);
							} else if (StringUtils.equals(user.getCreateBy(), loginContext.getActorId())) {
								success = sysUserService.delete(user);
							} else {
								if (deptIds.contains(user.getDeptId())) {
									success = sysUserService.delete(user);
								}
							}

							if (success) {
								SysDataLog sysLog = new SysDataLog();
								sysLog.setIp(RequestUtils.getIPAddress(request));
								sysLog.setActorId(loginContext.getActorId());
								sysLog.setContent(user.toJsonObject().toJSONString());
								sysLog.setTimeMS((int) 100);
								sysLog.setCreateTime(new Date());
								sysLog.setBusinessKey("UserInfo");
								sysLog.setModuleId("UserInfo");
								sysLog.setOperate("delete");
								sysLog.setActorId(loginContext.getActorId());
								SysLogFactory.getInstance().addLog(sysLog);
							}
						}
					}
					return ResponseUtils.responseResult(true);
				} catch (Exception ex) {
					return ResponseUtils.responseResult(false);
				}
			}
		}
		return ResponseUtils.responseResult(false);
	}

	/**
	 * 删除角色用户
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/delRoleUser")
	public ModelAndView delRoleUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		boolean sucess = false;
		String actorId = RequestUtils.getActorId(request);
		try {
			List<Long> nodeIds = complexUserService.getUserManageBranchNodeIds(actorId);
			SysDepartment department = sysDepartmentService.getSysDepartment(deptId);
			if (department != null) {
				SysTree tree = sysTreeService.findById(department.getNodeId());
				if (tree != null && nodeIds.contains(tree.getId())) {
					SysRole role = sysRoleService.findById(roleId);
					String[] userIds = ParamUtil.getParameterValues(request, "id");
					if (role != null && !StringUtils.equals(role.getCode(), SysConstants.BRANCH_ADMIN)) {
						sysUserService.deleteRoleUsers(role, userIds);
						sucess = true;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			sucess = false;
		}
		ViewMessages messages = new ViewMessages();
		if (sucess) {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.delete_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.delete_failure"));
		}
		MessageUtils.addMessages(request, messages);

		return new ModelAndView("/show_msg2", modelMap);
	}

	@RequestMapping("/deptUsers")
	public ModelAndView deptUsers(HttpServletRequest request, ModelMap modelMap) {
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

		String x_view = ViewProperties.getString("branch.user.deptUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/deptUsers", modelMap);
	}

	/**
	 * 得到部门下所有部门列表
	 * 
	 * @param list
	 * @param parentId
	 */
	public void getAllSysDepartmentList(List<SysDepartment> list, long parentId) {
		List<SysDepartment> temp = new java.util.ArrayList<SysDepartment>();
		temp = this.sysDepartmentService.getSysDepartmentList(parentId);
		if (temp != null && temp.size() != 0) {
			for (int i = 0; i < temp.size(); i++) {
				SysDepartment element = (SysDepartment) temp.get(i);
				getAllSysDepartmentList(list, element.getId());
			}
			list.addAll(temp);
		}
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Long deptId = ParamUtil.getLongParameter(request, "deptId", 0);
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysUserQuery query = new SysUserQuery();
		Tools.populate(query, params);
		if (nodeId > 0) {
			SysDepartment dept = sysDepartmentService.getSysDepartmentByNodeId(nodeId);
			if (dept != null) {
				query.deptId(dept.getId());
			}
		} else if (deptId > 0) {
			query.deptId(deptId);
		} else {
			query.deptId(-1L);
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sysUserService.getSysUserCountByQueryCriteria(query);
		logger.debug("total-->"+total);
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

			List<SysUser> list = sysUserService.getSysUsersByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysUser sysUser : list) {
					JSONObject rowJSON = sysUser.toJsonObject();
					rowJSON.put("id", sysUser.getId());
					rowJSON.put("actorId", sysUser.getAccount());
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

		String x_view = ViewProperties.getString("branch.user.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/list", modelMap);
	}

	@RequestMapping("/permission")
	public ModelAndView permission(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		logger.debug("->params:" + RequestUtils.getParameterMap(request));
		String nameLike = request.getParameter("nameLike");

		String op_view = request.getParameter("op_view");
		if (StringUtils.isEmpty(op_view)) {
			op_view = "user";
		}

		SysRoleQuery roleQuery = new SysRoleQuery();
		if (StringUtils.isNotEmpty(nameLike) && StringUtils.equals(op_view, "role")) {
			roleQuery.nameLike(nameLike);
		}
		List<SysRole> roleList = new ArrayList<SysRole>();
		List<SysRole> roles = sysRoleService.getSysRolesByQueryCriteria(0, 1000, roleQuery);

		for (SysRole role : roles) {
			if (StringUtils.isNotEmpty(role.getCode())
					&& (StringUtils.startsWithIgnoreCase(role.getCode(), SysConstants.BRANCH_PREFIX)
							|| StringUtils.equals(role.getIsUseBranch(), "Y"))) {
				roleList.add(role);
			}
		}

		SysRoleQuery q = new SysRoleQuery();

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		if (!loginContext.isSystemAdministrator()) {
			q.createBy(actorId);
		}

		List<SysRole> list = sysRoleService.getSysRolesByQueryCriteria(0, 100, q);

		if (list != null && !list.isEmpty()) {
			for (SysRole sysRole : list) {
				if (StringUtils.equals(sysRole.getCreateBy(), actorId)) {
					roleList.add(sysRole);
				}
			}
		}

		request.setAttribute("roleList", roleList);

		request.setAttribute("op_view", op_view);

		long parentId = 0;
		if (StringUtils.isNotEmpty(request.getParameter("parentId"))) {
			parentId = RequestUtils.getLong(request, "parentId");
		} else {
			SysUser user = com.glaf.base.utils.RequestUtil.getLoginUser(request);
			parentId = user.getDeptId();
		}

		List<Long> deptIds = new ArrayList<Long>();
		List<SysTree> treeList = new ArrayList<SysTree>();
		sysTreeService.loadSysTrees(treeList, parentId, 1);
		if (treeList != null && !treeList.isEmpty()) {
			for (SysTree tree : treeList) {
				if (tree.getDepartment() != null) {
					deptIds.add(tree.getDepartment().getId());
				}
			}
		}

		SysDepartment dept = sysDepartmentService.getSysDepartmentByNodeId(parentId);
		if (dept != null) {
			deptIds.add(dept.getId());
		}

		logger.debug("----deptIds:" + deptIds);

		SysUserQuery query = new SysUserQuery();
		query.deptIds(deptIds);
		if (StringUtils.isNotEmpty(nameLike) && StringUtils.equals(op_view, "user")) {
			query.nameLike(nameLike);
		}
		List<SysUser> users = sysUserService.getSysUsersByQueryCriteria(0, 1000, query);
		if (users != null && !users.isEmpty()) {
			List<String> actorIds = new ArrayList<String>();
			for (SysUser user : users) {
				actorIds.add(user.getAccount());
			}
			UserRoleQuery userRoleQuery = new UserRoleQuery();
			userRoleQuery.setActorIds(actorIds);
			List<UserRole> userRoles = sysUserService.getRoleUserViews(userRoleQuery);
			if (userRoles != null && !userRoles.isEmpty()) {
				for (SysUser user : users) {
					for (UserRole userRole : userRoles) {
						if (StringUtils.equals(user.getAccount(), userRole.getActorId())) {
							user.getRoleCodes().add(userRole.getRoleCode());
						}
					}
				}
			}
			request.setAttribute("users", users);
		}

		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String x_view = ViewProperties.getString("branch.department.permission");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/permission", modelMap);
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
		List<Dictory> dictories = dictoryService.getDictoryList(SysConstants.USER_HEADSHIP);
		modelMap.put("dictories", dictories);
		
		String x_view = ViewProperties.getString("branch.user.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_add", modelMap);
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
		String id = ParamUtil.getParameter(request, "id");
		id = RequestUtils.decodeString(id);
		SysUser bean = sysUserService.findByAccountWithAll(id);
		if (bean != null) {
			request.setAttribute("bean", bean);
			request.setAttribute("roles", bean.getRoles());

			if (StringUtils.isNotEmpty(bean.getSuperiorIds())) {
				List<String> userIds = StringTools.split(bean.getSuperiorIds());
				StringBuilder buffer = new StringBuilder(500);
				if (userIds != null && !userIds.isEmpty()) {
					for (String userId : userIds) {
						SysUser u = sysUserService.findByAccount(userId);
						if (u != null) {
							buffer.append(u.getName()).append("[").append(u.getAccount()).append("] ");
						}
					}
					request.setAttribute("x_users_name", buffer.toString());
				}
			}
		}

		List<Dictory> dictories = dictoryService.getDictoryList(SysConstants.USER_HEADSHIP);
		modelMap.put("dictories", dictories);

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		// List<Long> nodeIds = new ArrayList<Long>();

		// if (!loginContext.isSystemAdministrator()) {
		// nodeIds =
		// complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
		// nodeIds.add(-1L);
		// }

		List<TreeModel> manageTreeModels = null;

		if (loginContext.isSystemAdministrator()) {
			manageTreeModels = complexUserService.getAllDepartmentTrees();
		} else {
			manageTreeModels = complexUserService.getUserManageBranch(loginContext.getActorId());
		}

		logger.debug("manageTreeModels:" + manageTreeModels);

		SysTreeQuery query = new SysTreeQuery();

		if (manageTreeModels != null && !manageTreeModels.isEmpty()) {
			List<Long> treeIds = new ArrayList<Long>();
			for (TreeModel t : manageTreeModels) {
				treeIds.add(t.getId());
			}
			query.nodeIds(treeIds);
		}

		// query.nodeIds(nodeIds);
		List<SysTree> trees = sysTreeService.getDepartmentSysTrees(query);
		modelMap.put("trees", trees);

		String x_view = ViewProperties.getString("branch.user.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_modify", modelMap);
	}

	/**
	 * 显示重置密码页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareResetPwd")
	public ModelAndView prepareResetPwd(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String id = ParamUtil.getParameter(request, "id");
		id = RequestUtils.decodeString(id);
		SysUser bean = sysUserService.findById(id);
		request.setAttribute("bean", bean);

		String x_view = ViewProperties.getString("user.prepareResetPwd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_reset_pwd", modelMap);
	}

	/**
	 * 重置用户密码
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/resetPwd")
	public ModelAndView resetPwd(HttpServletRequest request, ModelMap modelMap) {
		boolean ret = false;

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		List<Long> nodeIds = new ArrayList<Long>();

		if (!loginContext.isSystemAdministrator()) {
			nodeIds = complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
		}

		String id = ParamUtil.getParameter(request, "id");
		id = RequestUtils.decodeString(id);
		logger.debug("userId:" + id);
		SysUser bean = sysUserService.findById(id);

		/**
		 * 系统管理员的密码不允许重置
		 */
		if (bean != null && !bean.isSystemAdministrator()) {
			/**
			 * 保修改的用户所属部门是分级管理员管辖的部门
			 */
			SysDepartment department = sysDepartmentService.findById(bean.getDeptId());
			if (department != null) {
				SysTree tree = sysTreeService.findById(department.getNodeId());
				if (tree != null && (loginContext.isSystemAdministrator() || nodeIds.contains(tree.getId()))) {
					String newPwd = ParamUtil.getParameter(request, "newPwd");
					if (bean != null && StringUtils.isNotEmpty(newPwd)) {
						try {
							sysUserService.changePassword(bean.getAccount(), newPwd,0);
							ret = true;
						} catch (Exception ex) {
							ex.printStackTrace();
							ret = false;
						}
					}
				}
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("/show_msg", modelMap);
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
		SysUser bean = new SysUser();
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		List<Long> nodeIds = new ArrayList<Long>();

		if (!loginContext.isSystemAdministrator()) {
			nodeIds = complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
		}

		int ret = 0;
		SysDepartment department = null;
		long nodeId = RequestUtils.getLong(request, "nodeId");
		long deptId = RequestUtils.getLong(request, "deptId");
		if (nodeId > 0) {
			department = sysDepartmentService.getSysDepartmentByNodeId(nodeId);
			bean.setDepartment(department);
		} else {
			if (deptId > 0) {
				department = sysDepartmentService.findById(deptId);
				bean.setDepartment(department);
			}
		}

		if (loginContext.getUser().getDeptId() == nodeId) {
			nodeIds.add(nodeId);
		}

		logger.debug("nodeIds:" + nodeIds);

		logger.debug("department:" + department);
		/**
		 * 保证添加的用户所属部门是分级管理员管辖的部门
		 */
		if (department != null) {
			SysTree tree = sysTreeService.findById(department.getNodeId());
			logger.debug("tree:" + tree);
			if (tree != null && (loginContext.isSystemAdministrator() || nodeIds.contains(tree.getId()))) {
				bean.setDeptId(department.getId());
				// bean.setCode(ParamUtil.getParameter(request, "code"));
				bean.setAccount(ParamUtil.getParameter(request, "account"));
				bean.setName(ParamUtil.getParameter(request, "name"));
				bean.setCode(ParamUtil.getParameter(request, "code"));
				bean.setShowName(request.getParameter("showName"));
				String password = ParamUtil.getParameter(request, "password");
				bean.setPasswordHash(password);
				bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
				bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
				bean.setMobile(ParamUtil.getParameter(request, "mobile"));
				bean.setEmail(ParamUtil.getParameter(request, "email"));
				bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
				bean.setLocked(ParamUtil.getIntParameter(request, "status", 0));
				bean.setHeadship(ParamUtil.getParameter(request, "headship"));
				bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
				bean.setEvection(0);
				bean.setCreateTime(new Date());
				bean.setLastLoginTime(new Date());
				bean.setCreateBy(RequestUtils.getActorId(request));
				bean.setUpdateBy(RequestUtils.getActorId(request));
				bean.setStatus("0");

				if (sysUserService.findByAccount(bean.getAccount()) == null) {
					if (sysUserService.create(bean)) {
						ret = 2;
						//加上默认角色
						//角色ID
						String rolesIds = SystemConfig.getString("rolesIds");
						if(rolesIds != null && !rolesIds.isEmpty()){
							String[] rolesIdsAry = rolesIds.split(",");
							Set<SysRole> newRoles = new HashSet<SysRole>();
							for(String roleId : rolesIdsAry){
								SysRole role = sysRoleService.findById(Long.parseLong(roleId));// 查找角色对象
								if (role != null) {
									newRoles.add(role);// 加入到角色列表
								}
							}
							if (sysUserService.updateUserRole(bean, newRoles)) {
								// 授权成功
								ret = 2;
							}
						}
					}
				} else {// 帐号存在
					ret = 1;
				}
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret == 2) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.add_success"));
		} else if (ret == 1) {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.existed"));
		} else {
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.add_failure"));
		}
		MessageUtils.addMessages(request, messages);

		// 显示列表页面
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
		String id = ParamUtil.getParameter(request, "id");
		logger.debug("userId:" + id);
		id = RequestUtils.decodeString(id);
		logger.debug(">userId:" + id);

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		List<Long> nodeIds = new ArrayList<Long>();

		if (!loginContext.isSystemAdministrator()) {
			nodeIds = complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
		}

		SysUser bean = sysUserService.findById(id);
		boolean ret = false;
		if (bean != null) {
			SysDepartment department = sysDepartmentService.findById(ParamUtil.getIntParameter(request, "deptId", 0));

			/**
			 * 保证添加的用户所属部门是分级管理员管辖的部门
			 */
			if (department != null) {
				SysTree tree = sysTreeService.findById(department.getNodeId());
				if (tree != null && (loginContext.isSystemAdministrator() || nodeIds.contains(tree.getId()))) {
					bean.setDepartment(department);
					bean.setName(ParamUtil.getParameter(request, "name"));
					bean.setCode(ParamUtil.getParameter(request, "code"));
					bean.setShowName(request.getParameter("showName"));
					bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
					bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
					bean.setMobile(ParamUtil.getParameter(request, "mobile"));
					bean.setEmail(ParamUtil.getParameter(request, "email"));
					bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
					bean.setEvection(ParamUtil.getIntParameter(request, "evection", 0));
					bean.setLocked(ParamUtil.getIntParameter(request, "status", 0));
					bean.setHeadship(ParamUtil.getParameter(request, "headship"));
					bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
					bean.setUpdateBy(RequestUtils.getActorId(request));
					bean.setStatus(request.getParameter("status"));
					ret = sysUserService.update(bean);
				}
			}
		}

		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.modify_failure"));
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
	@ResponseBody
	@RequestMapping("/saveUserRole")
	public byte[] saveUserRole(HttpServletRequest request) {
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);
		String actorId = request.getParameter("actorId");
		String operation = request.getParameter("operation");
		SysRole bean = sysRoleService.findById(roleId);
		SysUser user = sysUserService.findByAccountWithAll(actorId);
		if (bean != null && user != null) {
			LoginContext loginContext = RequestUtils.getLoginContext(request);

			List<Long> nodeIds = new ArrayList<Long>();

			if (!loginContext.isSystemAdministrator()) {
				nodeIds = complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
			}

			/**
			 * 保证添加的部门是分级管理员管辖的部门
			 */
			if (loginContext.isSystemAdministrator() || nodeIds.contains(user.getDepartment().getNodeId())) {
				if (StringUtils.equals(operation, "revoke")) {
					sysUserService.deleteRoleUser(roleId, actorId);
				} else {
					sysUserService.createRoleUser(roleId, actorId);
				}
				return ResponseUtils.responseResult(true);
			}
		}

		return ResponseUtils.responseResult(false);
	}

	/**
	 * 设置用户角色
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveUserRoles")
	public byte[] saveUserRoles(HttpServletRequest request, ModelMap modelMap) {
		logger.debug(RequestUtils.getParameterMap(request));
		logger.debug("actorId:" + request.getParameter("actorId"));
		String userId = request.getParameter("actorId");
		userId = RequestUtils.decodeString(userId);
		String objectIds = request.getParameter("objectIds");
		logger.debug("userId:" + userId);
		SysUser user = sysUserService.findById(userId);// 查找用户对象
		logger.debug("user:" + user);
		if (user != null) {

			LoginContext loginContext = RequestUtils.getLoginContext(request);

			List<Long> nodeIds = new ArrayList<Long>();

			if (!loginContext.isSystemAdministrator()) {
				nodeIds = complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
			}

			SysDepartment department = sysDepartmentService.findById(user.getDeptId());
			/**
			 * 保证添加的用户所属部门是分级管理员管辖的部门
			 */
			if (department != null && department.getNodeId() > 0) {
				SysTree tree = sysTreeService.findById(department.getNodeId());
				if (tree != null && (nodeIds.contains(tree.getId()) || loginContext.isSystemAdministrator())) {
					Set<SysRole> newRoles = new HashSet<SysRole>();
					if (StringUtils.isNotEmpty(objectIds)) {
						List<Long> ids = StringTools.splitToLong(objectIds);// 获取页面参数
						if (ids != null) {
							for (int i = 0; i < ids.size(); i++) {
								logger.debug("id[" + i + "]=" + ids.get(i));
								SysRole role = sysRoleService.findById(ids.get(i));// 查找角色对象
								if (role != null) {
									newRoles.add(role);// 加入到角色列表
								}
							}
						}
					}
					logger.debug("newRoles:" + newRoles);
					user.setUpdateBy(RequestUtils.getActorId(request));
					if (sysUserService.updateUserRole(user, newRoles)) {
						// 授权成功
						return ResponseUtils.responseResult(true);
					}
				}
			}
		}

		return ResponseUtils.responseResult(false);
	}

	/**
	 * 查询获取用户列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectSysUser")
	public ModelAndView selectSysUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = SysConstants.PAGE_SIZE;
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int multDate = ParamUtil.getIntParameter(request, "multDate", 0);
		String name = ParamUtil.getParameter(request, "fullName", null);
		PageResult pager = sysUserService.getSysUserList(deptId, name, pageNo, pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("multDate", new Integer(multDate));

		String x_view = ViewProperties.getString("branch.user.selectSysUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_select", modelMap);
	}

	/**
	 * 查询获取特定部门用户列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectSysUserByDept")
	public ModelAndView selectSysUserByDept(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = SysConstants.PAGE_SIZE;
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		String name = ParamUtil.getParameter(request, "fullName", null);

		SysDepartment sysDepartment = sysDepartmentService.findById(deptId);
		request.setAttribute("sysDepartment", sysDepartment);
		PageResult pager = null;
		if (StringUtils.isNotEmpty(name)) {
			pager = sysUserService.getSysUserList(deptId, name, pageNo, pageSize);
		} else {
			pager = sysUserService.getSysUserList(deptId, pageNo, pageSize);
		}
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("branch.user.selectSysUserByDept");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/userByDept_list", modelMap);
	}

	@javax.annotation.Resource
	public void setComplexUserService(ComplexUserService complexUserService) {
		this.complexUserService = complexUserService;
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	/**
	 * 设置用户角色
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/setRole")
	public ModelAndView setRole(HttpServletRequest request, ModelMap modelMap) {
		logger.debug(RequestUtils.getParameterMap(request));
		ViewMessages messages = new ViewMessages();
		String userId = ParamUtil.getParameter(request, "actorId");
		userId = RequestUtils.decodeString(userId);
		SysUser user = sysUserService.findById(userId);// 查找用户对象
		if (user != null && user.getDeptId() > 0) {// 用户存在
			String actorId = RequestUtils.getActorId(request);
			List<Long> nodeIds = complexUserService.getUserManageBranchNodeIds(actorId);

			SysDepartment department = sysDepartmentService.findById(user.getDeptId());
			/**
			 * 保证添加的用户所属部门是分级管理员管辖的部门
			 */
			if (department != null && department.getNodeId() > 0) {
				SysTree tree = sysTreeService.findById(department.getNodeId());
				if (tree != null && nodeIds.contains(tree.getId())) {
					long[] ids = ParamUtil.getLongParameterValues(request, "id");// 获取页面参数
					if (userId != null) {

						Set<SysRole> newRoles = new HashSet<SysRole>();
						for (int i = 0; i < ids.length; i++) {
							logger.debug("id[" + i + "]=" + ids[i]);
							SysRole role = sysRoleService.findById(ids[i]);// 查找角色对象
							if (role != null) {
								newRoles.add(role);// 加入到角色列表
							}
						}

						user.setUpdateBy(RequestUtils.getActorId(request));

						if (sysUserService.updateUserRole(user, newRoles)) {// 授权成功
							messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.role_success"));
						} else {// 保存失败
							messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("user.role_failure"));
						}
					}
				}
			}
		}
		MessageUtils.addMessages(request, messages);
		return new ModelAndView("/show_msg", modelMap);
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

	/**
	 * 显示部门下所有人
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showDeptUsers")
	public ModelAndView showDeptUsers(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		List<SysDepartment> list = new java.util.ArrayList<SysDepartment>();
		Set<SysUser> set = new HashSet<SysUser>();

		long deptId = ParamUtil.getLongParameter(request, "dept", 5);

		SysDepartment node = this.sysDepartmentService.findById(deptId);
		if (node != null) {
			list.add(node);
			this.getAllSysDepartmentList(list, node.getId());
		} else {
			this.getAllSysDepartmentList(list, deptId);
		}

		request.setAttribute("user", set);

		String x_view = ViewProperties.getString("branch.user.showDeptUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/duty_select", modelMap);
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
		int deptId = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", SysConstants.PAGE_SIZE);
		PageResult pager = sysUserService.getSysUserList(deptId, pageNo, pageSize);
		request.setAttribute("department", sysDepartmentService.getSysDepartment(deptId));
		request.setAttribute("pager", pager);

		SysDepartment dept = sysDepartmentService.getSysDepartment(deptId);
		List<SysDepartment> list = new java.util.ArrayList<SysDepartment>();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		String x_view = ViewProperties.getString("branch.user.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_list", modelMap);
	}

	/**
	 * 显示角色
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showRole")
	public ModelAndView showRole(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String actorId = ParamUtil.getParameter(request, "actorId");
		actorId = RequestUtils.decodeString(actorId);

		SysUser user = sysUserService.findByAccountWithAll(actorId);

		List<SysRole> list = new ArrayList<SysRole>();
		List<SysRole> roles = sysRoleService.getSysRoleList();
		if (roles != null && !roles.isEmpty()) {
			for (SysRole role : roles) {
				if (StringUtils.isNotEmpty(role.getCode())
						&& (StringUtils.startsWithIgnoreCase(role.getCode(), SysConstants.BRANCH_PREFIX))) {
					list.add(role);
				} else {
					if (StringUtils.equals(role.getIsUseBranch(), "Y")) {
						list.add(role);
					} else if (StringUtils.equals(role.getCreateBy(), actorId)) {
						list.add(role);
					}
				}
			}
		}

		request.setAttribute("user", user);
		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("branch.user.showRole");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/branch/user/user_role", modelMap);
	}

	/**
	 * 显示角色用户列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showRoleUser")
	public ModelAndView showRoleUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);

		// 部门信息
		SysDepartment dept = sysDepartmentService.findById(deptId);
		List<SysDepartment> list = new java.util.ArrayList<SysDepartment>();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		// 角色
		SysRole role = sysRoleService.findById(roleId);
		request.setAttribute("role", role.getName());

		String x_view = ViewProperties.getString("branch.user.showRoleUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/deptRole_user", modelMap);
	}

	/**
	 * 显示角色用户列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showSelUser")
	public ModelAndView showSelUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		int deptId = ParamUtil.getIntParameter(request, "deptId2", 0);
		if (deptId != 0) {
			request.setAttribute("list", sysUserService.getSysUserList(deptId));
		}

		String x_view = ViewProperties.getString("branch.user.showSelUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/dept_user_sel", modelMap);
	}

	/**
	 * 显示用户信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showUser")
	public ModelAndView showUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String id = ParamUtil.getParameter(request, "userId");
		id = RequestUtils.decodeString(id);
		SysUser user = sysUserService.findById(id);
		user.setPasswordHash(null);
		request.setAttribute("user", user);

		String x_view = ViewProperties.getString("branch.user.showUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/user/user_info", modelMap);
	}
}