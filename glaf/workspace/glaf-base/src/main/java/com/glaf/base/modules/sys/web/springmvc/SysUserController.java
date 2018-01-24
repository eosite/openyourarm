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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.business.GnUserImporter;
import com.glaf.base.modules.sys.business.UserImporter;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.cache.CacheUtils;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

@Controller("/sys/user")
@RequestMapping("/sys/user")
public class SysUserController {
	private static final Log logger = LogFactory.getLog(SysUserController.class);

	protected DictoryService dictoryService;

	protected SysDepartmentService sysDepartmentService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected ITableDataService tableDataService;

	protected IServerEntityService serverEntityService;

	/**
	 * 增加角色用户
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/addRoleUser")
	public ModelAndView addRoleUser(HttpServletRequest request, ModelMap modelMap) {
		logger.debug("---------addRoleUser---------------------------");
		RequestUtils.setRequestParameterToAttribute(request);

		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);

		boolean success = false;

		String[] userIds = ParamUtil.getParameterValues(request, "id");
		for (int i = 0; i < userIds.length; i++) {
			SysUser user = sysUserService.findById(userIds[i]);
			if (user != null) {
				sysUserService.createRoleUser(roleId, user.getActorId());
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
		int roleId = ParamUtil.getIntParameter(request, "roleId", 0);
		SysRole role = sysRoleService.findById(roleId);
		boolean sucess = false;
		try {
			String[] userIds = ParamUtil.getParameterValues(request, "id");
			sysUserService.deleteRoleUsers(role, userIds);
			sucess = true;
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

		String x_view = ViewProperties.getString("user.deptUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/deptUsers", modelMap);
	}

	/**
	 * 
	 * @param model
	 * @param mFile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/doImport", method = RequestMethod.POST)
	public ModelAndView doImport(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("file") MultipartFile mFile) throws IOException {
		if (mFile != null && !mFile.isEmpty()) {
			UserImporter imp = new UserImporter();
			imp.doImport(mFile.getInputStream());
		}
		return this.list(request, modelMap);
	}

	/**
	 * 得到部门下所有部门列表
	 * 
	 * @param list
	 * @param parentId
	 */
	public void getAllSysDepartmentList(List<SysDepartment> list, long parentId) {
		List<SysDepartment> temp = new ArrayList<SysDepartment>();
		temp = this.sysDepartmentService.getSysDepartmentList(parentId);
		if (temp != null && temp.size() != 0) {
			for (int i = 0; i < temp.size(); i++) {
				SysDepartment element = (SysDepartment) temp.get(i);
				getAllSysDepartmentList(list, element.getId());
			}
			list.addAll(temp);
		}
	}

	/**
	 * 提交修改信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/gnUserImport")
	public byte[] gnUserImport(HttpServletRequest request) {
		logger.debug(RequestUtils.getParameterMap(request));
		ServerEntity serverEntity = serverEntityService.getServerEntityByMapping("GNRemote");
		if (serverEntity != null) {
			GnUserImporter imp = new GnUserImporter();
			imp.doImport();
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		Long deptId = ParamUtil.getLongParameter(request, "parent", 0);
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysUserQuery query = new SysUserQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);

		if (nodeId > 0) {
			SysDepartment dept = sysDepartmentService.getSysDepartmentByNodeId(nodeId);
			if (dept != null) {
				query.deptId(dept.getId());
			}
		} else if (deptId > 0) {
			query.deptId(deptId);
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
		request.setAttribute("parent", request.getParameter("parent"));
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		ServerEntity serverEntity = serverEntityService.getServerEntityByMapping("GNRemote");
		if (serverEntity != null) {
			request.setAttribute("serverEntity", serverEntity);
		}

		String x_view = ViewProperties.getString("user.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/list", modelMap);
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

		List<Dictory> dictories = dictoryService.getDictoryList(SysConstants.USER_HEADSHIP);
		modelMap.put("dictories", dictories);

		List<Dictory> accounts = dictoryService.getDictoryList(SysConstants.USER_ACCOUNTTYPE);
		modelMap.put("accountTypeDictories", accounts);

		String x_view = ViewProperties.getString("user.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_add", modelMap);
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
		SysUser bean = sysUserService.findById(id);
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

		List<Dictory> dictories = dictoryService.getDictoryList(SysConstants.USER_HEADSHIP);
		modelMap.put("dictories", dictories);

		List<Dictory> accounts = dictoryService.getDictoryList(SysConstants.USER_ACCOUNTTYPE);
		modelMap.put("accountTypeDictories", accounts);

		SysTree parent = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("user.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_modify", modelMap);
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

		List<Dictory> dictories = dictoryService.getDictoryList(SysConstants.USER_HEADSHIP);
		modelMap.put("dictories", dictories);

		String x_view = ViewProperties.getString("user.prepareModifyInfo");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_change_info", modelMap);
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

		String x_view = ViewProperties.getString("user.prepareModifyPwd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_modify_pwd", modelMap);
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

		return new ModelAndView("/modules/sys/user/user_reset_pwd", modelMap);
	}

	/**
	 * 显示重置密锁页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/prepareResetToken")
	public ModelAndView prepareResetToken(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String id = ParamUtil.getParameter(request, "id");
		id = RequestUtils.decodeString(id);
		SysUser bean = sysUserService.findById(id);
		request.setAttribute("bean", bean);

		String x_view = ViewProperties.getString("user.prepareResetToken");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_reset_token", modelMap);
	}

	/**
	 * 重置登录信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetLoginStatus")
	public byte[] resetLoginStatus(HttpServletRequest request) {
		logger.debug(RequestUtils.getParameterMap(request));
		String actorId = request.getParameter("actorId");
		actorId = RequestUtils.decodeString(actorId);
		SysUser user = sysUserService.findByAccount(actorId);
		if (user != null) {
			sysUserService.resetLoginStatus(actorId);
			return ResponseUtils.responseJsonResult(true, "重置登录信息成功。");
		}
		return ResponseUtils.responseResult(false);
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
		RequestUtils.setRequestParameterToAttribute(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		boolean ret = false;

		if (loginContext.isSystemAdministrator()) {
			logger.debug(loginContext.getActorId() + " is system admin");
		}

		/**
		 * 只允许root或admin账户才能重置其他用户的密码
		 */
		if (loginContext.isSystemAdministrator() && (StringUtils.equalsIgnoreCase(loginContext.getActorId(), "admin")
				|| StringUtils.equalsIgnoreCase(loginContext.getActorId(), "root"))) {
			String id = ParamUtil.getParameter(request, "id");
			SysUser bean = sysUserService.findById(id);
			if (bean != null) {
				/**
				 * root或admin系统管理员的密码不允许重置
				 */
				if (!(StringUtils.equalsIgnoreCase(bean.getAccount(), "admin")
						|| StringUtils.equalsIgnoreCase(bean.getAccount(), "root"))) {
					String newPwd = ParamUtil.getParameter(request, "newPwd");
					if (bean != null && StringUtils.isNotEmpty(newPwd)) {
						logger.info(loginContext.getActorId() + "重置" + bean.getName() + "的密码。");
						try {
							sysUserService.changePassword(bean.getAccount(), newPwd, 0);
							ret = true;
						} catch (Exception ex) {
							logger.error(ex);
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
	 * 重置用户令牌及密锁
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetUserToken")
	public byte[] resetUserToken(HttpServletRequest request) {
		logger.debug(RequestUtils.getParameterMap(request));
		String actorId = request.getParameter("actorId");
		actorId = RequestUtils.decodeString(actorId);
		SysUser user = sysUserService.resetUserToken(actorId);
		if (user != null) {
			return ResponseUtils.responseJsonResult(true, "令牌重置成功，请通知客户端程序更新令牌。");
		}
		return ResponseUtils.responseResult(false);
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
		long nodeId = RequestUtils.getLong(request, "nodeId");
		if (nodeId > 0) {
			SysDepartment department = sysDepartmentService.getSysDepartmentByNodeId(nodeId);
			bean.setDepartment(department);
			bean.setDeptId(department.getId());
		} else {
			SysDepartment department = sysDepartmentService.findById(ParamUtil.getIntParameter(request, "parent", 0));
			if (department != null) {
				bean.setDepartment(department);
				bean.setDeptId(department.getId());
			}
		}

		bean.setAccount(ParamUtil.getParameter(request, "id"));
		bean.setName(ParamUtil.getParameter(request, "name"));
		bean.setShowName(request.getParameter("showName"));
		bean.setCode(ParamUtil.getParameter(request, "code"));
		String password = ParamUtil.getParameter(request, "password");
		bean.setPasswordHash(password);

		bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
		bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
		bean.setMobile(ParamUtil.getParameter(request, "mobile"));
		bean.setEmail(ParamUtil.getParameter(request, "email"));
		bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
		bean.setStatus(ParamUtil.getParameter(request, "status"));
		bean.setHeadship(ParamUtil.getParameter(request, "headship"));
		bean.setAccountType(ParamUtil.getIntParameter(request, "accountType", 0));
		bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
		bean.setMqLoginFlag(request.getParameter("mqLoginFlag"));
		bean.setSecretLoginFlag(request.getParameter("secretLoginFlag"));
		bean.setEvection(0);
		bean.setCreateTime(new Date());
		bean.setLastLoginTime(new Date());
		bean.setCreateBy(RequestUtils.getActorId(request));
		bean.setUpdateBy(RequestUtils.getActorId(request));
		
		String deadlineTime = ParamUtil.getParameter(request, "deadlineTime");
		if(StringUtils.isNotEmpty("deadlineTime")){
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				bean.setDeadlineTime(sdf.parse(deadlineTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int ret = 0;
		if (sysUserService.findByAccount(bean.getAccount()) == null) {
			if (sysUserService.create(bean))
				ret = 2;
		} else {// 帐号存在
			ret = 1;
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
		logger.debug(RequestUtils.getParameterMap(request));
		String id = ParamUtil.getParameter(request, "id");
		id = RequestUtils.decodeString(id);
		logger.debug("id = " + id);
		SysUser bean = sysUserService.findById(id);
		logger.debug("user = " + bean);
		boolean ret = false;
		if (bean != null) {
			SysDepartment department = sysDepartmentService.findById(ParamUtil.getIntParameter(request, "parent", 0));
			bean.setDepartment(department);
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setShowName(request.getParameter("showName"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setSuperiorIds(ParamUtil.getParameter(request, "superiorIds"));
			bean.setGender(ParamUtil.getIntParameter(request, "gender", 0));
			bean.setMobile(ParamUtil.getParameter(request, "mobile"));
			bean.setEmail(ParamUtil.getParameter(request, "email"));
			bean.setTelephone(ParamUtil.getParameter(request, "telephone"));
			bean.setEvection(ParamUtil.getIntParameter(request, "evection", 0));
			bean.setStatus(ParamUtil.getParameter(request, "status"));
			bean.setHeadship(ParamUtil.getParameter(request, "headship"));
			bean.setAccountType(ParamUtil.getIntParameter(request, "accountType", 0));
			bean.setUserType(ParamUtil.getIntParameter(request, "userType", 0));
			bean.setMqLoginFlag(request.getParameter("mqLoginFlag"));
			bean.setSecretLoginFlag(request.getParameter("secretLoginFlag"));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			//设置截止日期（有效期）
			String deadlineTime = ParamUtil.getParameter(request, "deadlineTime");
			if(StringUtils.isNotEmpty("deadlineTime")){
				SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					bean.setDeadlineTime(sdf.parse(deadlineTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
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
					sysUserService.changePassword(user.getAccount(), newPwd, 1);
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

	/**
	 * 设置用户角色
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveRole")
	public ModelAndView saveRole(HttpServletRequest request, ModelMap modelMap) {
		logger.debug(RequestUtils.getParameterMap(request));
		ViewMessages messages = new ViewMessages();
		String userId = ParamUtil.getParameter(request, "actorId");
		SysUser user = sysUserService.findById(userId);// 查找用户对象

		if (user != null) {// 用户存在
			long[] ids = ParamUtil.getLongParameterValues(request, "id");// 获取页面参数
			if (ids != null) {

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
		logger.debug(RequestUtils.getParameterMap(request));
		long roleId = ParamUtil.getLongParameter(request, "roleId", 0);
		String actorId = request.getParameter("actorId");
		String operation = request.getParameter("operation");
		SysRole bean = sysRoleService.findById(roleId);
		SysUser user = sysUserService.findByAccountWithAll(actorId);
		if (bean != null && user != null) {
			if (StringUtils.equals(operation, "revoke")) {
				sysUserService.deleteRoleUser(roleId, actorId);
			} else {
				sysUserService.createRoleUser(roleId, actorId);
			}
			return ResponseUtils.responseResult(true);
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
		String userId = RequestUtils.decodeString(request.getParameter("actorId"));
		String objectIds = request.getParameter("objectIds");
		logger.debug("userId:" + userId);
		SysUser user = sysUserService.findById(userId);// 查找用户对象
		logger.debug("user:" + user);
		if (user != null) {
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

		String x_view = ViewProperties.getString("user.selectSysUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_select", modelMap);
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
		if (name != null && !"".equals(name)) {
			pager = sysUserService.getSysUserList(deptId, name, pageNo, pageSize);
		} else {
			pager = sysUserService.getSysUserList(deptId, pageNo, pageSize);
		}
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("user.selectSysUserByDept");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/userByDept_list", modelMap);
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	@javax.annotation.Resource
	public void setServerEntityService(IServerEntityService serverEntityService) {
		this.serverEntityService = serverEntityService;
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
	 * 显示所有列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showAllList")
	public ModelAndView showAllList(HttpServletRequest request, ModelMap modelMap) {
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", SysConstants.PAGE_SIZE);

		SysUserQuery query = new SysUserQuery();
		String rq = ParamUtil.getParameter(request, "_rq_", "");
		logger.debug("_rq_:" + rq);
		String nameLike_encode = ParamUtil.getParameter(request, "nameLike_encode", "");
		String actorIdLike_encode = ParamUtil.getParameter(request, "actorIdLike_encode", "");

		if ("1".equals(rq)) {
			logger.debug("-----------------------参数查询-----------------------");
			String nameLike = ParamUtil.getParameter(request, "nameLike", "");
			String actorIdLike = ParamUtil.getParameter(request, "actorIdLike", "");
			if (StringUtils.isNotEmpty(nameLike)) {
				query.setNameLike(nameLike);
				request.setAttribute("nameLike_encode", RequestUtils.encodeString(nameLike));
				request.setAttribute("nameLike", nameLike);
			} else {
				request.removeAttribute("nameLike");
				request.removeAttribute("nameLike_encode");
				request.setAttribute("nameLike", "");
			}
			if (StringUtils.isNotEmpty(actorIdLike)) {
				query.setAccountLike(actorIdLike);
				request.setAttribute("actorIdLike_encode", RequestUtils.encodeString(actorIdLike));
				request.setAttribute("actorIdLike", actorIdLike);
			} else {
				request.removeAttribute("actorIdLike");
				request.removeAttribute("actorIdLike_encode");
				request.setAttribute("actorIdLike", "");
			}
		} else {
			logger.debug("-----------------------链接查询-----------------------");
			if (StringUtils.isNotEmpty(nameLike_encode)) {
				String nameLike = RequestUtils.decodeString(nameLike_encode);
				query.setNameLike(nameLike);
				request.setAttribute("nameLike_encode", nameLike_encode);
				request.setAttribute("nameLike", nameLike);
			}
			if (StringUtils.isNotEmpty(actorIdLike_encode)) {
				String actorIdLike = RequestUtils.decodeString(actorIdLike_encode);
				query.setAccountLike(actorIdLike);
				request.setAttribute("actorIdLike_encode", actorIdLike_encode);
				request.setAttribute("actorIdLike", actorIdLike);
			}
		}

		query.setDeleteFlag(0);

		ServerEntity serverEntity = serverEntityService.getServerEntityByMapping("GNRemote");
		if (serverEntity != null) {
			request.setAttribute("serverEntity", serverEntity);
		}

		PageResult pager = sysUserService.getSysUserList(pageNo, pageSize, query);
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("user.showAllList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/all_user_list", modelMap);
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
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		Set<SysUser> set = new HashSet<SysUser>();
		// 6:
		long deptId = ParamUtil.getLongParameter(request, "dept", 5);
		// String roleCode = ParamUtil.getParameter(request, "code", "");
		SysDepartment node = this.sysDepartmentService.findById(deptId);
		if (node != null) {
			list.add(node);
			this.getAllSysDepartmentList(list, node.getId());
		} else {
			this.getAllSysDepartmentList(list, deptId);
		}

		request.setAttribute("user", set);

		String x_view = ViewProperties.getString("user.showDeptUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/duty_select", modelMap);
	}

	/**
	 * 显示框架页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showFrame")
	public ModelAndView showFrame(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		SysTree bean = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		request.setAttribute("parent", bean.getId() + "");

		String x_view = ViewProperties.getString("user.showFrame");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_frame", modelMap);
	}

	/**
	 * 显示用户导入页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("user.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/showImport", modelMap);
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
		long deptId = ParamUtil.getLongParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", SysConstants.PAGE_SIZE);

		SysUserQuery query = new SysUserQuery();
		String rq = ParamUtil.getParameter(request, "_rq_", "");
		logger.debug("_rq_:" + rq);
		String nameLike_encode = ParamUtil.getParameter(request, "nameLike_encode", "");
		String actorIdLike_encode = ParamUtil.getParameter(request, "actorIdLike_encode", "");

		if ("1".equals(rq)) {
			logger.debug("-----------------------参数查询-----------------------");
			String nameLike = ParamUtil.getParameter(request, "nameLike", "");
			String actorIdLike = ParamUtil.getParameter(request, "actorIdLike", "");
			if (StringUtils.isNotEmpty(nameLike)) {
				query.setNameLike(nameLike);
				request.setAttribute("nameLike_encode", RequestUtils.encodeString(nameLike));
				request.setAttribute("nameLike", nameLike);
			} else {
				request.removeAttribute("nameLike");
				request.removeAttribute("nameLike_encode");
				request.setAttribute("nameLike", "");
			}
			if (StringUtils.isNotEmpty(actorIdLike)) {
				query.setAccountLike(actorIdLike);
				request.setAttribute("actorIdLike_encode", RequestUtils.encodeString(actorIdLike));
				request.setAttribute("actorIdLike", actorIdLike);
			} else {
				request.removeAttribute("actorIdLike");
				request.removeAttribute("actorIdLike_encode");
				request.setAttribute("actorIdLike", "");
			}
		} else {
			logger.debug("-----------------------链接查询-----------------------");
			if (StringUtils.isNotEmpty(nameLike_encode)) {
				String nameLike = RequestUtils.decodeString(nameLike_encode);
				query.setNameLike(nameLike);
				request.setAttribute("nameLike_encode", nameLike_encode);
				request.setAttribute("nameLike", nameLike);
			}
			if (StringUtils.isNotEmpty(actorIdLike_encode)) {
				String actorIdLike = RequestUtils.decodeString(actorIdLike_encode);
				query.setAccountLike(actorIdLike);
				request.setAttribute("actorIdLike_encode", actorIdLike_encode);
				request.setAttribute("actorIdLike", actorIdLike);
			}

		}

		if (deptId > 0) {
			query.setDeptId(deptId);
			SysDepartment dept = sysDepartmentService.findById(deptId);
			List<SysDepartment> list = new ArrayList<SysDepartment>();
			sysDepartmentService.findNestingDepartment(list, dept);
			request.setAttribute("nav", list);
			request.setAttribute("department", sysDepartmentService.findById(deptId));
		}

		ServerEntity serverEntity = serverEntityService.getServerEntityByMapping("GNRemote");
		if (serverEntity != null) {
			request.setAttribute("serverEntity", serverEntity);
		}

		query.setDeleteFlag(0);

		PageResult pager = sysUserService.getSysUserList(pageNo, pageSize, query);
		// PageResult pager = sysUserService.getSysUserList(deptId, pageNo,
		// pageSize);

		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("user.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_list", modelMap);
	}

	/**
	 * 部门用户树
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/deptUsersTree")
	public ModelAndView showDeptUserTree(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Long deptId = RequestUtils.getLong(request, "deptId");
		SysDepartment dept = sysDepartmentService.findById(deptId);
		modelMap.put("dept", dept);
		String x_view = ViewProperties.getString("user.deptUsersTree");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/sys/user/deptUsersTree", modelMap);
	}

	@ResponseBody
	@RequestMapping("/deptUsersJson")
	public byte[] roleUsersJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		List<SysUser> deptUsers = null;
		JSONObject result = new JSONObject();
		Long deptId = RequestUtils.getLong(request, "deptId");
		if (deptId > 0) {
			// roleUsers = sysUserService.getSysUsersByRoleId(roleId);
		} else {
			// String roleCode = request.getParameter("roleCode");
			// if (StringUtils.isNotEmpty(roleCode)) {
			// roleUsers = sysUserService.getSysUsersByRoleCode(roleCode);
			// }
		}

		deptUsers = sysUserService.getSysUserList(deptId);

		Collection<String> userIds = new HashSet<String>();
		if (deptUsers != null && !deptUsers.isEmpty()) {
			for (SysUser u : deptUsers) {
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
			if (allUsers != null && !allUsers.isEmpty()) {
				for (SysUser user : allUsers) {
					if (user.getDeptId() == 0) {
						TreeModel treeModel = new BaseTree();
						treeModel.setParentId(root.getId());
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
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			// logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/saveDeptUsers")
	public byte[] saveDeptUsers(HttpServletRequest request) throws IOException {
		Long deptId = RequestUtils.getLong(request, "deptId", 0);
		String userIds = RequestUtils.getString(request, "userIds");
		if (StringUtils.isNotEmpty(userIds)) {
			String ids[] = StringUtils.split(userIds, ",");
			for (String id : ids) {
				SysUser user = new SysUser();
				user.setAccount(id);
				user.setDeptId(deptId);
				this.sysUserService.update(user);
			}
		}
		return ResponseUtils.responseResult(true);
	}

	/**
	 * 显示所有列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showPasswordList")
	public ModelAndView showPasswordList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String userName = ParamUtil.getParameter(request, "userName");
		String account = ParamUtil.getParameter(request, "account");
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", 20);

		PageResult pager = sysUserService.getSysUserList(deptId, userName, account, pageNo, pageSize);
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("user.showPasswordList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_password_list", modelMap);
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
		String userId = ParamUtil.getParameter(request, "actorId");
		userId = RequestUtils.decodeString(userId);
		logger.debug("userId:" + userId);
		SysUser user = sysUserService.findByAccountWithAll(userId);

		List<SysRole> list = new ArrayList<SysRole>();
		List<SysRole> roles = sysRoleService.getSysRoleList();
		if (roles != null && !roles.isEmpty()) {
			for (SysRole role : roles) {
				list.add(role);
			}
		}

		request.setAttribute("user", user);
		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("user.showRole");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/user/user_role", modelMap);
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
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		// 角色
		SysRole role = sysRoleService.findById(roleId);
		request.setAttribute("role", role.getName());

		String x_view = ViewProperties.getString("user.showRoleUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/deptRole_user", modelMap);
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
		int deptId = ParamUtil.getIntParameter(request, "deptId", 0);
		if (deptId != 0) {
			request.setAttribute("list", sysUserService.getSysUserList(deptId));
		}

		deptId = ParamUtil.getIntParameter(request, "deptId2", 0);
		if (deptId != 0) {
			request.setAttribute("list", sysUserService.getSysUserList(deptId));
		}

		String x_view = ViewProperties.getString("user.showSelUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/dept_user_sel", modelMap);
	}

	/**
	 * 增加角色用户
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showUser")
	public ModelAndView showUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String userId = ParamUtil.getParameter(request, "userId");
		SysUser user = sysUserService.findById(userId);
		request.setAttribute("user", user);

		SysTree parent = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		List<SysTree> list = new ArrayList<SysTree>();
		parent.setDeep(0);
		list.add(parent);
		sysTreeService.getSysTree(list, parent.getId(), 1);
		request.setAttribute("parent", list);

		String x_view = ViewProperties.getString("user.showUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/user_info", modelMap);
	}

	/**
	 * 显示角色菜单
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showUserMenus")
	public ModelAndView showUserMenus(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String userId = ParamUtil.getParameter(request, "actorId");
		userId = RequestUtils.decodeString(userId);
		SysUser user = sysUserService.findById(userId);
		request.setAttribute("user", user);

		String x_view = ViewProperties.getString("user.userMenus");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/user/userMenus", modelMap);
	}
}