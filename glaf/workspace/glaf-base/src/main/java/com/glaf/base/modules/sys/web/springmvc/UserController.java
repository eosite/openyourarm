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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.base.tag.ResponseUtils;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

import com.glaf.core.cache.CacheUtils;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/identity/user")
@RequestMapping("/identity/user")
public class UserController {
	protected static final Log logger = LogFactory.getLog(UserController.class);

	protected SysDepartmentService sysDepartmentService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected ITableDataService tableDataService;

	@RequestMapping("json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		String nameOrCode = RequestUtils.getString(request, "nameOrCode");
		String type = RequestUtils.getString(request, "type", "");
		// String subType = RequestUtils.getString(request, "subType","");
		String rowId = RequestUtils.getString(request, "rowId", "");
		long sysDeptId = RequestUtils.getLong(request, "sysDeptId", 0);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysUserQuery query = new SysUserQuery();
		Tools.populate(query, params);
		if (StringUtils.isNotEmpty(nameOrCode)) {
			query.setDeptNameOrCode("1");
			query.setDeptNameLike(nameOrCode);
			query.setDeptCodeLike(nameOrCode);
		}
		// type判断增加过滤sql
		if ("roleAddUser".equals(type)) {// 全局角色或部门角色添加用户
			query.setSysDeptId(sysDeptId);
			if (sysDeptId != 0) {// 部门角色配置用户时只能选择本部门的用户
				query.setDeptId(sysDeptId);
			}
			if (StringUtils.isNotEmpty(rowId)) {
				query.setSysRoleId(Long.parseLong(rowId));
			}
		} else if ("groupUserAddUser".equals(type)) {// 群组用户添加用户
			query.setGroupUserId(rowId);
		} else if ("groupLeaderAddUser".equals(type)) {// 群组领导添加用户
			query.setGroupLeaderId(rowId);
		} else if ("addReturn".equals(type)) {// 群组或角色添加管理用户
			String accountsNotIn = RequestUtils.getString(request, "accountsNotIn", "");
			if (StringUtils.isNotEmpty(accountsNotIn)) {
				List<String> list = new ArrayList<String>();
				String[] accountArray = accountsNotIn.split(",");
				for (String x : accountArray) {
					if (StringUtils.isNotEmpty(x))
						list.add(x);
				}
				query.setAccountsNotIn(list);
			}
		}
		query.setLocked(0);// 有效用户
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

		JSONObject result = new JSONObject();
		int total = sysUserService.getSysUserCountByQueryCriteria(query);
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
			}

			List<SysUser> list = sysUserService.getSysUsersByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysUser sysUser : list) {
					JSONObject rowJSON = sysUser.toJsonObject();
					rowJSON.put("id", sysUser.getId());
					rowJSON.put("actorId", sysUser.getAccount());
					rowJSON.put("deptName", sysUser.getDeptName());
					rowJSON.put("deptCode", sysUser.getDeptCode());
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

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareModifyInfo")
	public ModelAndView prepareModifyInfo(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysUser user = RequestUtil.getLoginUser(request);
		SysUser bean = sysUserService.findByAccount(user.getAccount());
		request.setAttribute("bean", bean);

		if (bean != null && StringUtils.isNotEmpty(bean.getSuperiorIds())) {
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

		String x_view = ViewProperties.getString("identity.user.prepareModifyInfo");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/identity/user/user_change_info", modelMap);
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareModifyPwd")
	public ModelAndView prepareModifyPwd(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysUser bean = RequestUtil.getLoginUser(request);
		request.setAttribute("bean", bean);

		SysTree parent = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("identity.user.prepareModifyPwd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/identity/user/user_modify_pwd", modelMap);
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
		SysUser bean = sysUserService.findById(id);
		boolean ret = false;
		if (bean != null) {
			SysDepartment department = sysDepartmentService.findById(ParamUtil.getIntParameter(request, "parent", 0));
			bean.setDepartment(department);
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setShowName(request.getParameter("showName"));
			bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
			bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
			bean.setMobile(ParamUtil.getParameter(request, "mobile"));
			bean.setEmail(ParamUtil.getParameter(request, "email"));
			bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
			bean.setEvection(ParamUtil.getIntParameter(request, "evection", 0));
			bean.setStatus(ParamUtil.getParameter(request, "status"));
			bean.setHeadship(ParamUtil.getParameter(request, "headship"));
			bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			ret = sysUserService.update(bean);
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
	@RequestMapping("/saveModifyInfo")
	public ModelAndView saveModifyInfo(HttpServletRequest request, ModelMap modelMap) {
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		if (bean != null) {
			SysUser user = sysUserService.findById(bean.getActorId());
			user.setMobile(ParamUtil.getParameter(request, "mobile"));
			user.setEmail(ParamUtil.getParameter(request, "email"));
			user.setTelephone(ParamUtil.getParameter(request, "telephone"));
			user.setUpdateBy(RequestUtils.getActorId(request));
			ret = sysUserService.update(user);
			CacheUtils.clearUserCache(user.getAccount());
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
	 * 修改用户密码
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/savePwd2")
	@ResponseBody
	public byte[] savePwd2(HttpServletRequest request, ModelMap modelMap) throws Exception {
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		String oldPwd = ParamUtil.getParameter(request, "oldPwd");
		String newPwd = ParamUtil.getParameter(request, "newPwd");
		if (bean != null && StringUtils.isNotEmpty(oldPwd) && StringUtils.isNotEmpty(newPwd)) {
			try {
				SysUser user = sysUserService.findById(bean.getActorId());
				if (sysUserService.checkPassword(user.getAccount(), oldPwd)) {
					sysUserService.changePassword(user.getAccount(), newPwd,1);
					ret = true;
				}
			} catch (Exception ex) {
				ret = false;
			}
		}
		JSONObject result = new JSONObject();
		if(ret){
			result.put("statusCode", 200);
			result.put("msg", "修改成功");
		}else{
			result.put("statusCode", 400);
			result.put("msg", "修改失败");
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/savePwd")
	public ModelAndView savePwd(HttpServletRequest request, ModelMap modelMap) {
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		String oldPwd = ParamUtil.getParameter(request, "oldPwd");
		String newPwd = ParamUtil.getParameter(request, "newPwd");
		if (bean != null && StringUtils.isNotEmpty(oldPwd) && StringUtils.isNotEmpty(newPwd)) {
			try {
				SysUser user = sysUserService.findById(bean.getActorId());
				if (sysUserService.checkPassword(user.getAccount(), oldPwd)) {
					sysUserService.changePassword(user.getAccount(), newPwd,1);
					ret = true;
				}
			} catch (Exception ex) {
				ret = false;
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

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	/**
	 * 显示修改页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String actorId = ParamUtil.getParameter(request, "actorId");
		actorId = RequestUtils.decodeString(actorId);
		SysUser bean = sysUserService.findByAccount(actorId);
		request.setAttribute("bean", bean);

		if (bean != null && StringUtils.isNotEmpty(bean.getSuperiorIds())) {
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

		SysTree parent = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("user.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/identity/user/user_view", modelMap);
	}

	@RequestMapping("/viewRoleUsers")
	public ModelAndView viewRoleUsers(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		long id = ParamUtil.getIntParameter(request, "id", 0);
		SysRole sysRole = null;
		if (id > 0) {
			sysRole = sysRoleService.findById(id);
			request.setAttribute("sysRole", sysRole);
		}

		if (sysRole == null) {
			id = ParamUtil.getIntParameter(request, "roleId", 0);
			sysRole = sysRoleService.findById(id);
			request.setAttribute("sysRole", sysRole);
		}

		String x_view = ViewProperties.getString("role.viewRoleUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/role/viewRoleUsers", modelMap);
	}

}