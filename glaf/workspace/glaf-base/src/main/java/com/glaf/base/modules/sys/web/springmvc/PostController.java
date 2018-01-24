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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
 
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.Post;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.PostQuery;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.PostService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.sys.util.PostDomainFactory;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.TreeModel;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/sys/post")
@RequestMapping("/sys/post")
public class PostController {
	private static final Log logger = LogFactory.getLog(PostController.class);

	protected SysApplicationService sysApplicationService;

	protected PostService postService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	@RequestMapping(value = { "/create" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Post create(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		Post bean = new Post();
		String code = (String) model.get("code");
		if (postService.findByCode(code) == null) {
			bean.setName((String) model.get("name"));
			bean.setContent((String) model.get("content"));
			bean.setCode((String) model.get("code"));
			bean.setIsUseBranch((String) model.get("isUseBranch"));
			bean.setCreateBy(RequestUtils.getActorId(request));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			postService.create(bean);
		}

		return bean;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		long id = ParamUtil.getIntParameter(request, "id", 0);
		Post post = null;
		if (id > 0) {
			post = postService.findById(id);
			request.setAttribute("post", post);
		}

		String x_view = ViewProperties.getString("post.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/post/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PostQuery query = new PostQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);
		query.setDataRequest(dataRequest);
		PostDomainFactory.processDataRequest(dataRequest);
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
		int total = postService.getPostCountByQueryCriteria(query);
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

			List<Post> list = postService.getPostsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (Post post : list) {
					JSONObject rowJSON = post.toJsonObject();
					rowJSON.put("id", post.getId());
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

		String x_view = ViewProperties.getString("post.list");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/post/list", modelMap);
	}

	@RequestMapping("/postUsers")
	public ModelAndView postUsers(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		long id = ParamUtil.getIntParameter(request, "id", 0);
		Post post = null;
		if (id > 0) {
			post = postService.findById(id);
			request.setAttribute("post", post);
		}

		String x_view = ViewProperties.getString("post.postUsers");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/post/postUsers", modelMap);
	}

	@RequestMapping("/postUsersJson")
	@ResponseBody
	public byte[] postUsersJson(HttpServletRequest request, ModelMap modelMap) throws IOException {
		JSONObject result = new JSONObject();
		String postCode = request.getParameter("postCode");
		List<SysUser> postUsers = sysUserService.getSysUsersByRoleCode(postCode);
		Collection<String> userIds = new HashSet<String>();
		if (postUsers != null && !postUsers.isEmpty()) {
			for (SysUser u : postUsers) {
				userIds.add(u.getAccount());
			}
		}
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
			// logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
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

		String x_view = ViewProperties.getString("post.prepareAdd");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/post/post_add", modelMap);
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
		Post bean = postService.findById(id);
		request.setAttribute("bean", bean);

		String x_view = ViewProperties.getString("post.prepareModify");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/post/post_modify", modelMap);
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	public byte[] saveAdd(HttpServletRequest request, ModelMap modelMap) {
		boolean ret = false;
		if (postService.findByCode(ParamUtil.getParameter(request, "code")) == null) {
			Post bean = new Post();
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setContent(ParamUtil.getParameter(request, "content"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setIndexUrl(ParamUtil.getParameter(request, "indexUrl"));
			bean.setIsUseBranch(ParamUtil.getParameter(request, "isUseBranch"));
			bean.setSort(ParamUtil.getIntParameter(request, "sort", 0));
			bean.setCreateBy(RequestUtils.getActorId(request));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			ret = postService.create(bean);
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
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveModify")
	@ResponseBody
	public byte[] saveModify(HttpServletRequest request, ModelMap modelMap) {
		long id = ParamUtil.getIntParameter(request, "postId", 0);
		Post bean = postService.findById(id);
		if (bean != null) {
			bean.setName(ParamUtil.getParameter(request, "name"));
			bean.setContent(ParamUtil.getParameter(request, "content"));
			bean.setCode(ParamUtil.getParameter(request, "code"));
			bean.setIndexUrl(ParamUtil.getParameter(request, "indexUrl"));
			bean.setIsUseBranch(ParamUtil.getParameter(request, "isUseBranch"));
			bean.setSort(ParamUtil.getIntParameter(request, "sort", 0));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			boolean ret = postService.update(bean);
			if (ret) {// 保存成功
				return ResponseUtils.responseResult(true);
			}
		}

		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	@javax.annotation.Resource
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
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
		logger.debug("->params:" + RequestUtils.getRequestParameters(request));
		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size", SysConstants.PAGE_SIZE);

		PostQuery query = new PostQuery();
		String rq = ParamUtil.getParameter(request, "_rq_", "");
		logger.debug("_rq_:" + rq);
		String nameLike_encode = ParamUtil.getParameter(request, "nameLike_encode", "");
		String codeLike_encode = ParamUtil.getParameter(request, "codeLike_encode", "");

		if ("1".equals(rq)) {
			logger.debug("-----------------------参数查询-----------------------");
			String nameLike = ParamUtil.getParameter(request, "nameLike", "");
			String codeLike = ParamUtil.getParameter(request, "codeLike", "");
			if (StringUtils.isNotEmpty(nameLike)) {
				query.setNameLike(nameLike);
				request.setAttribute("nameLike_encode", RequestUtils.encodeString(nameLike));
				request.setAttribute("nameLike", nameLike);
			} else {
				request.removeAttribute("nameLike");
				request.removeAttribute("nameLike_encode");
				request.setAttribute("nameLike", "");
			}
			if (StringUtils.isNotEmpty(codeLike)) {
				query.setCodeLike(codeLike);
				request.setAttribute("codeLike_encode", RequestUtils.encodeString(codeLike));
				request.setAttribute("codeLike", codeLike);
			} else {
				request.removeAttribute("codeLike");
				request.removeAttribute("codeLike_encode");
				request.setAttribute("codeLike", "");
			}
		} else {
			logger.debug("-----------------------链接查询-----------------------");
			if (StringUtils.isNotEmpty(nameLike_encode)) {
				String nameLike = RequestUtils.decodeString(nameLike_encode);
				query.setNameLike(nameLike);
				request.setAttribute("nameLike_encode", nameLike_encode);
				request.setAttribute("nameLike", nameLike);
			}
			if (StringUtils.isNotEmpty(codeLike_encode)) {
				String codeLike = RequestUtils.decodeString(codeLike_encode);
				query.setCodeLike(codeLike);
				request.setAttribute("codeLike_encode", codeLike_encode);
				request.setAttribute("codeLike", codeLike);
			}

		}

		query.setDeleteFlag(0);

		// Tools.populate(query, params);
		// PageResult pager = sysRoleService.getPostList(pageNo, pageSize);
		PageResult pager = postService.getPostList(pageNo, pageSize, query);
		request.setAttribute("pager", pager);

		String x_view = ViewProperties.getString("post.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		// 显示列表页面
		return new ModelAndView("/modules/sys/post/post_list", modelMap);
	}

	/**
	 * 更新岗位信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/update" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Post update(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		long postId = ParamUtils.getLong(model, "postId");
		Post bean = postService.findById(postId);
		if (bean != null) {
			bean.setName((String) model.get("name"));
			bean.setContent((String) model.get("content"));
			bean.setCode((String) model.get("code"));
			bean.setIsUseBranch((String) model.get("isUseBranch"));
			bean.setIndexUrl(ParamUtil.getParameter(request, "indexUrl"));
			bean.setSort(ParamUtil.getIntParameter(request, "sort", 0));
			bean.setUpdateBy(RequestUtils.getActorId(request));
			postService.update(bean);
		}

		return bean;
	}

}