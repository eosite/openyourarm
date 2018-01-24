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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.business.DeptmentImporter;
import com.glaf.base.modules.sys.model.DeptRole;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysDepartmentQuery;
import com.glaf.base.modules.sys.query.SysRoleQuery;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.AuthorityUserService;
import com.glaf.base.modules.sys.service.ComplexUserService;
import com.glaf.base.modules.sys.service.DeptRoleService;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.base.res.MessageUtils;
import com.glaf.base.res.ViewMessage;
import com.glaf.base.res.ViewMessages;
import com.glaf.core.security.LoginContext;
import com.glaf.core.tree.helper.JacksonTreeHelper;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/branch/department")
@RequestMapping("/branch/department")
public class BranchDepartmentController {
	private static final Log logger = LogFactory.getLog(BranchDepartmentController.class);

	protected AuthorityUserService authorityUserService;

	protected ComplexUserService complexUserService;

	protected DictoryService dictoryService;

	protected DeptRoleService deptRoleService;

	protected SysDepartmentService sysDepartmentService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected SysRoleService sysRoleService;

	@RequestMapping("/branchAdmin")
	public ModelAndView branchAdmin(HttpServletRequest request, ModelMap modelMap) {
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

		String x_view = ViewProperties.getString("branch.department.branchAdmin");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/branchAdmin", modelMap);
	}

	@ResponseBody
	@RequestMapping("/deleteById")
	public byte[] deleteById(HttpServletRequest request) {
		long deptId = RequestUtils.getLong(request, "deptId");
		if (deptId > 0) {
			List<SysUser> users = sysUserService.getSysUserList(deptId);
			if (users != null && !users.isEmpty()) {
				logger.debug("不能删除具有用户的机构");
				// return ResponseUtils.responseJsonResult(false,
				// "不能删除具有用户的机构");
			}
			List<SysDepartment> list = new ArrayList<SysDepartment>();
			sysDepartmentService.findNestingDepartment(list, deptId);
			if (list.size() > 1) {
				logger.debug("不能删除具有下级节点的机构");
				return ResponseUtils.responseJsonResult(false, "不能删除具有下级节点的机构");
			} else {
				sysDepartmentService.delete(deptId);
				return ResponseUtils.responseJsonResult(true);
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/frame")
	public ModelAndView frame(HttpServletRequest request, ModelMap modelMap) {
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

		String x_view = ViewProperties.getString("branch.department.frame");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/frame", modelMap);
	}
	
	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody Map<String, Object> params) throws IOException {
		logger.debug("----------------------json----------------------");
		request.setCharacterEncoding("UTF-8");
		String actorId = RequestUtils.getActorId(request);
		logger.debug("params:" + params);
		SysDepartmentQuery query = new SysDepartmentQuery();
		Tools.populate(query, params);
		
		if (params.get("params") != null ) {
			String paramstr = (String)params.get("params");
			if(StringUtils.isNotEmpty(paramstr)){
				Tools.populate(query, JSON.parseObject(paramstr));
			}
		}

		if (!RequestUtils.getLoginContext(request).isSystemAdministrator()) {
			List<Long> nodeIds = complexUserService.getUserManageBranchNodeIds(actorId);
			logger.debug("#nodeIds:" + nodeIds);
			query.nodeIds(nodeIds);
		}

		if (StringUtils.isNotEmpty(request.getParameter("parentId"))) {
			query.setParentId(RequestUtils.getLong(request, "parentId"));
		} else {
			// SysUser user =
			// com.glaf.base.utils.RequestUtil.getLoginUser(request);
			// query.setParentId(user.getDeptId());
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
		limit = ParamUtils.getInt(params, "pageSize");
		start = (pageNo - 1) * limit;
		
		String paramstr = ParamUtils.getString(params, "sort");
		if(StringUtils.isNotEmpty(paramstr)){
			JSONArray sortAry = JSON.parseArray(paramstr);
			if(sortAry != null && sortAry.size() > 0){
				JSONObject sortObj = sortAry.getJSONObject(0);
				orderName = sortObj.getString("field");
				order = sortObj.getString("dir");
			}
		}

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.MAX_RECORD_COUNT;
		}

		JSONObject result = new JSONObject();
		int total = sysDepartmentService.getSysDepartmentCountByQueryCriteria(query);
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

			List<SysDepartment> list = sysDepartmentService.getSysDepartmentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDepartment sysDepartment : list) {
					JSONObject rowJSON = sysDepartment.toJsonObject();
					rowJSON.put("id", sysDepartment.getId());
					rowJSON.put("departmentId", sysDepartment.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			result.put("total", 0);
			result.put("totalCount", 0);
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
		}
		logger.debug(result.toString());
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("----------------------json----------------------");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String actorId = RequestUtils.getActorId(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		SysDepartmentQuery query = new SysDepartmentQuery();
		Tools.populate(query, params);

		if (!RequestUtils.getLoginContext(request).isSystemAdministrator()) {
			List<Long> nodeIds = complexUserService.getUserManageBranchNodeIds(actorId);
			logger.debug("#nodeIds:" + nodeIds);
			query.nodeIds(nodeIds);
		}

		if (StringUtils.isNotEmpty(request.getParameter("parentId"))) {
			query.setParentId(RequestUtils.getLong(request, "parentId"));
		} else {
			// SysUser user =
			// com.glaf.base.utils.RequestUtil.getLoginUser(request);
			// query.setParentId(user.getDeptId());
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
			limit = Paging.MAX_RECORD_COUNT;
		}

		JSONObject result = new JSONObject();
		int total = sysDepartmentService.getSysDepartmentCountByQueryCriteria(query);
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

			List<SysDepartment> list = sysDepartmentService.getSysDepartmentsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysDepartment sysDepartment : list) {
					JSONObject rowJSON = sysDepartment.toJsonObject();
					rowJSON.put("id", sysDepartment.getId());
					rowJSON.put("departmentId", sysDepartment.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			result.put("total", 0);
			result.put("totalCount", 0);
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
		}
		logger.debug(result.toString());
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

		String x_view = ViewProperties.getString("branch.department.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/list", modelMap);
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
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		request.setAttribute("root", root);

		List<TreeModel> manageTreeModels = null;

		if (loginContext.isSystemAdministrator()) {
			manageTreeModels = complexUserService.getAllDepartmentTrees();
		} else {
			manageTreeModels = complexUserService.getUserManageBranch(loginContext.getActorId());
		}

		// logger.debug("manageTreeModels:" + manageTreeModels);

		if (manageTreeModels != null && !manageTreeModels.isEmpty()) {
			List<Long> treeIds = new ArrayList<Long>();
			for (TreeModel t : manageTreeModels) {
				treeIds.add(t.getId());
			}
			SysTreeQuery query = new SysTreeQuery();
			query.nodeIds(treeIds);
			List<SysTree> trees = sysTreeService.getSysTrees(query);
			request.setAttribute("trees", trees);
		}

		List<Dictory> dictories = dictoryService.getDictoryList(SysConstants.DEPT_LEVEL);
		request.setAttribute("dictories", dictories);

		String x_view = ViewProperties.getString("branch.department.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/dept_add", modelMap);
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
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		SysDepartment bean = sysDepartmentService.getSysDepartment(id);
		if (bean != null) {
			request.setAttribute("bean", bean);
		}

		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		request.setAttribute("root", root);

		List<TreeModel> manageTreeModels = null;

		if (loginContext.isSystemAdministrator()) {
			manageTreeModels = complexUserService.getAllDepartmentTrees();
		} else {
			manageTreeModels = complexUserService.getUserManageBranch(loginContext.getActorId());
		}

		// logger.debug("manageTreeModels:" + manageTreeModels);

		if (manageTreeModels != null && !manageTreeModels.isEmpty()) {
			List<Long> treeIds = new ArrayList<Long>();
			for (TreeModel t : manageTreeModels) {
				treeIds.add(t.getId());
			}
			SysTreeQuery query = new SysTreeQuery();
			query.nodeIds(treeIds);
			List<SysTree> trees = sysTreeService.getSysTrees(query);
			request.setAttribute("trees", trees);
		}

		List<Dictory> dictories = dictoryService.getDictoryList(SysConstants.DEPT_LEVEL);
		modelMap.put("dictories", dictories);

		String x_view = ViewProperties.getString("branch.department.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/branch/dept/dept_modify", modelMap);
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
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long parentId = ParamUtil.getLongParameter(request, "parent", 0);
		boolean ret = false;

		List<Long> nodeIds = new ArrayList<Long>();

		if (!loginContext.isSystemAdministrator()) {
			nodeIds = complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
			long deptId = loginContext.getUser().getDeptId();
			if (deptId > 0) {
				SysDepartment dept = sysDepartmentService.getSysDepartment(deptId);
				if (dept != null) {
					nodeIds.add(dept.getNodeId());
				}
			}
		}

		if (loginContext.getUser().getDeptId() == parentId) {
			nodeIds.add(parentId);
		}

		logger.debug("nodeIds:" + nodeIds);

		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);

		/**
		 * 保证添加的部门是分级管理员管辖的部门
		 */
		if (loginContext.isSystemAdministrator() || nodeIds.contains(parentId) || parentId == 0) {
			// 增加部门时，同时要增加对应节点
			SysDepartment bean = new SysDepartment();
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setAnotherName(request.getParameter("anotherName"));
			bean.setShortName(request.getParameter("shortName"));
			bean.setFormalFlag(request.getParameter("formalFlag"));
			bean.setDesc(ParamUtil.getParameter(request, "desc"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setCode2(ParamUtil.getParameter(request, "code2"));
			bean.setNo(ParamUtil.getParameter(request, "no"));
			bean.setAddress(request.getParameter("address"));
			bean.setTelphone(request.getParameter("telphone"));
			bean.setPrincipal(request.getParameter("principal"));
			bean.setLevel(RequestUtils.getInt(request, "level"));
			bean.setCreateTime(new Date());
			bean.setCreateBy(RequestUtils.getActorId(request));

			SysTree node = new SysTree();
			node.setCreateBy(RequestUtils.getActorId(request));
			node.setName(bean.getName());
			node.setDesc(bean.getName());
			node.setCode(bean.getCode());
			if (parentId == 0 && root != null) {
				node.setParentId(root.getId());
			} else {
				node.setParentId(parentId);
			}
			bean.setNode(node);

			ret = sysDepartmentService.create(bean);

			ViewMessages messages = new ViewMessages();
			if (ret) {// 保存成功
				messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("department.add_success"));
			} else {// 保存失败
				messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("department.add_failure"));
			}
			MessageUtils.addMessages(request, messages);
		}

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
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long id = ParamUtil.getLongParameter(request, "id", 0);
		long parentId = ParamUtil.getLongParameter(request, "parent", 0);
		SysDepartment bean = sysDepartmentService.getSysDepartment(id);
		boolean ret = false;
		if (bean != null) {
			List<Long> nodeIds = new ArrayList<Long>();
			if (!loginContext.isSystemAdministrator()) {
				nodeIds = complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
				long deptId = loginContext.getUser().getDeptId();
				if (deptId > 0) {
					SysDepartment dept = sysDepartmentService.getSysDepartment(deptId);
					if (dept != null) {
						nodeIds.add(dept.getNodeId());
					}
				}
			}

			if (loginContext.getUser().getDeptId() == parentId) {
				nodeIds.add(parentId);
			}

			SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);

			/**
			 * 保证添加的部门是分级管理员管辖的部门
			 */
			if (loginContext.isSystemAdministrator() || nodeIds.contains(parentId) || nodeIds.contains(bean.getNodeId())
					|| parentId == 0) {
				bean.setUpdateBy(RequestUtils.getActorId(request));
				bean.setName(ParamUtil.getParameter(request, "name"));
				bean.setAnotherName(request.getParameter("anotherName"));
				bean.setShortName(request.getParameter("shortName"));
				bean.setFormalFlag(request.getParameter("formalFlag"));
				bean.setDesc(ParamUtil.getParameter(request, "desc"));
				bean.setCode(ParamUtil.getParameter(request, "code"));
				bean.setCode2(ParamUtil.getParameter(request, "code2"));
				bean.setNo(ParamUtil.getParameter(request, "no"));
				bean.setAddress(request.getParameter("address"));
				bean.setTelphone(request.getParameter("telphone"));
				bean.setPrincipal(request.getParameter("principal"));
				bean.setStatus(ParamUtil.getIntParameter(request, "status", 0));
				bean.setLevel(RequestUtils.getInt(request, "level"));

				SysTree node = bean.getNode();
				node.setUpdateBy(RequestUtils.getActorId(request));
				node.setName(bean.getName());
				if (parentId == 0) {
					/**
					 * 只有系统管理员及创建者才能更改父节点为根节点
					 */
					if (root != null && loginContext.isSystemAdministrator()
							|| StringUtils.equals(node.getCreateBy(), loginContext.getActorId())) {
						node.setParentId(root.getId());
					}
					node.setLocked(0);
					bean.setStatus(0);
				} else {
					node.setParentId(parentId);
				}
				bean.setNode(node);
				try {
					ret = sysDepartmentService.update(bean);
				} catch (Exception ex) {
					ret = false;
					logger.error(ex);
				}
			}
		}
		ViewMessages messages = new ViewMessages();
		if (ret) {// 保存成功
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("department.modify_success"));
		} else {// 保存失败
			messages.add(ViewMessages.GLOBAL_MESSAGE, new ViewMessage("department.modify_failure"));
		}
		MessageUtils.addMessages(request, messages);

		// 显示列表页面
		return new ModelAndView("/show_msg", modelMap);
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveRoles")
	@ResponseBody
	public byte[] saveRoles(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String isPropagationAllowed = request.getParameter("isPropagationAllowed");
		int menuFlag = RequestUtils.getInt(request, "menuFlag");
		long deptId = RequestUtils.getLong(request, "deptId");
		String items = request.getParameter("items");
		if (items != null && deptId > 0) {
			List<DeptRole> rows = new ArrayList<DeptRole>();
			SysTree node = null;
			try {
				SysDepartment dept = sysDepartmentService.findById(deptId);
				if (dept != null) {
					node = dept.getNode();
				}
				List<TreeModel> manageTreeModels = null;
				if (loginContext.isSystemAdministrator()) {
					manageTreeModels = complexUserService.getAllDepartmentTrees();
				} else {
					manageTreeModels = complexUserService.getUserManageBranch(loginContext.getActorId());
				}
				// logger.debug("manageTreeModels:" + manageTreeModels);
				if (manageTreeModels != null && !manageTreeModels.isEmpty()) {
					List<Long> treeIds = new ArrayList<Long>();
					for (TreeModel t : manageTreeModels) {
						treeIds.add(t.getId());
					}
					if (node != null && treeIds.contains(node.getId())) {
						List<Long> roleIds = authorityUserService.getRoleIdsByActorId(loginContext.getActorId());
						StringTokenizer token = new StringTokenizer(items, ",");
						while (token.hasMoreTokens()) {
							String item = token.nextToken();
							if (StringUtils.isNotEmpty(item)) {
								if (!loginContext.isSystemAdministrator()) {
									if (roleIds != null && !roleIds.isEmpty()) {
										if (roleIds.contains(Long.parseLong(item))) {
											DeptRole bean = new DeptRole();
											bean.setDeptId(deptId);
											bean.setRoleId(Long.parseLong(item));
											bean.setIsPropagationAllowed(isPropagationAllowed);
											rows.add(bean);
										}
									}
								} else {
									DeptRole bean = new DeptRole();
									bean.setDeptId(deptId);
									bean.setRoleId(Long.parseLong(item));
									bean.setIsPropagationAllowed(isPropagationAllowed);
									rows.add(bean);
								}
							}
						}
					}
				}
				logger.debug("rows:" + rows);
				deptRoleService.saveAll(deptId, menuFlag, rows);
				return ResponseUtils.responseResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
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
	public void setAuthorityUserService(AuthorityUserService authorityUserService) {
		this.authorityUserService = authorityUserService;
	}

	@javax.annotation.Resource
	public void setComplexUserService(ComplexUserService complexUserService) {
		this.complexUserService = complexUserService;
	}

	@javax.annotation.Resource
	public void setDeptRoleService(DeptRoleService deptRoleService) {
		this.deptRoleService = deptRoleService;
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
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
	 * 显示部门导入页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String x_view = ViewProperties.getString("branch.department.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/showImport", modelMap);
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
			DeptmentImporter bean = new DeptmentImporter();
			bean.doImport(mFile.getInputStream());
		}
		return this.list(request, modelMap);
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
		int parent = ParamUtil.getIntParameter(request, "parent", 0);
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", SysConstants.PAGE_SIZE);
		PageResult pager = sysDepartmentService.getSysDepartmentList(parent, pageNo, pageSize);
		request.setAttribute("pager", pager);

		SysTree treeNode = sysTreeService.findById(parent);
		SysDepartment dept = treeNode.getDepartment();
		List<SysDepartment> list = new java.util.ArrayList<SysDepartment>();
		sysDepartmentService.findNestingDepartment(list, dept);
		request.setAttribute("nav", list);

		String x_view = ViewProperties.getString("branch.department.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/dept_list", modelMap);
	}

	/**
	 * 显示增加页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showRole")
	public ModelAndView showRole(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		SysTree node = null;
		int menuFlag = RequestUtils.getInt(request, "menuFlag");
		long deptId = RequestUtils.getLong(request, "deptId");
		if (deptId > 0) {
			SysDepartment dept = sysDepartmentService.findById(deptId);
			if (dept != null) {
				node = dept.getNode();
				request.setAttribute("dept", dept);
			}
		}

		request.setAttribute("menuFlag", menuFlag);

		List<TreeModel> manageTreeModels = null;

		if (loginContext.isSystemAdministrator()) {
			manageTreeModels = complexUserService.getAllDepartmentTrees();
		} else {
			manageTreeModels = complexUserService.getUserManageBranch(loginContext.getActorId());
		}

		// logger.debug("manageTreeModels:" + manageTreeModels);

		if (manageTreeModels != null && !manageTreeModels.isEmpty()) {
			List<Long> treeIds = new ArrayList<Long>();
			for (TreeModel t : manageTreeModels) {
				treeIds.add(t.getId());
			}
			if (node != null && treeIds.contains(node.getId())) {
				SysRoleQuery query = new SysRoleQuery();
				query.setIsUseDept("Y");
				List<SysRole> roles = sysRoleService.getSysRolesByQueryCriteria(0, 1000, query);
				List<Long> roleIds = authorityUserService.getRoleIdsByActorId(loginContext.getActorId());
				logger.debug("roleIds:" + roleIds);
				if (roles != null && !roles.isEmpty()) {
					List<DeptRole> deptRoles = deptRoleService.getDeptRolesByDeptId(deptId);
					List<SysRole> selecteds = new ArrayList<SysRole>();
					List<SysRole> unselecteds = new ArrayList<SysRole>();
					List<Long> selectedIds = new ArrayList<Long>();
					if (deptRoles != null && !deptRoles.isEmpty()) {
						for (DeptRole deptRole : deptRoles) {
							if (deptRole.getMenuFlag() == menuFlag) {
								selectedIds.add(deptRole.getRoleId());
								if (StringUtils.equals(deptRole.getIsPropagationAllowed(), "Y")) {
									request.setAttribute("isPropagationAllowed", deptRole.getIsPropagationAllowed());
								}
							}
						}
					}
					List<SysRole> list = new ArrayList<SysRole>();
					for (SysRole role : roles) {
						if (loginContext.isSystemAdministrator()) {
							list.add(role);
						} else {
							if (roleIds != null && !roleIds.isEmpty()) {
								if (roleIds.contains(role.getId())) {
									list.add(role);
								}
							}
						}
					}
					for (SysRole role : list) {
						if (selectedIds.contains(role.getId())) {
							selecteds.add(role);
						} else {
							unselecteds.add(role);
						}
					}
					request.setAttribute("roles", list);
					request.setAttribute("selecteds", selecteds);
					request.setAttribute("selectedIds", selectedIds);
					request.setAttribute("unselecteds", unselecteds);
				}
			}
		}

		String x_view = ViewProperties.getString("branch.department.showRole");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/showRole", modelMap);
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
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		SysTree root = sysTreeService.getSysTreeByCode(SysConstants.TREE_DEPT);
		long parentId = ParamUtil.getLongParameter(request, "nodeId", root.getId());

		SysTreeQuery query = new SysTreeQuery();
		query.parentId(parentId);
		query.setDiscriminator("D");
		if (!loginContext.isSystemAdministrator()) {
			List<Long> nodeIds = complexUserService.getUserManageBranchNodeIds(loginContext.getActorId());
			query.nodeIds(nodeIds);
		}

		List<SysTree> trees = sysTreeService.getDepartmentSysTrees(query);
		request.setAttribute("trees", trees);

		String x_view = ViewProperties.getString("sys.tree.showSort");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/showSort", modelMap);
	}

	@RequestMapping("/tree")
	public ModelAndView tree(HttpServletRequest request, ModelMap modelMap) {
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

		String x_view = ViewProperties.getString("branch.department.tree");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/branch/dept/tree", modelMap);
	}

	@ResponseBody
	@RequestMapping("/treeJson")
	public byte[] treeJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("------------------------treeJson--------------------");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		List<TreeModel> treeModels = null;

		if (loginContext.isSystemAdministrator()) {
			treeModels = complexUserService.getAllDepartmentTrees();
		} else {
			treeModels = complexUserService.getUserManageBranch(loginContext.getActorId());
		}

		// logger.debug("#treeModels:" + treeModels);
		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		ArrayNode responseJSON = treeHelper.getTreeArrayNode(treeModels);
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}