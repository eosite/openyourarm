package com.glaf.base.project.web.springmvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.ComplexUserService;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.TreeModel;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.Database;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.identity.User;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.query.UserQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.tree.helper.JacksonTreeHelper;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.base.project.domain.Project;
import com.glaf.base.project.domain.ProjectAccess;
import com.glaf.base.project.domain.ProjectOwner;
import com.glaf.base.project.query.ProjectQuery;
import com.glaf.base.project.service.ProjectService;
import com.glaf.base.project.util.ProjectDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/my/project")
@RequestMapping("/my/project")
public class MyProjectController {
	protected static final Log logger = LogFactory.getLog(MyProjectController.class);

	protected ComplexUserService complexUserService;

	protected DictoryService dictoryService;

	protected IDatabaseService databaseService;

	protected ProjectService projectService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected SysDepartmentService sysDepartmentService;

	public MyProjectController() {

	}

	@RequestMapping("/choose")
	public ModelAndView choose(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		Project project = projectService.getProject(RequestUtils.getLong(request, "projectId"));
		if (project != null) {
			request.setAttribute("project", project);
		}

		List<Long> databaseIds = projectService.getDatabaseIds(RequestUtils.getLong(request, "projectId"));

		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> activeDatabases = new ArrayList<Database>();

		List<Database> databases = null;
		if (loginContext.isSystemAdministrator()) {
			databases = databaseService.list(query);
		} else {
			databases = databaseService.getDatabases(loginContext.getActorId());
		}

		if (databases != null && !databases.isEmpty()) {
			for (Database database : databases) {
				if ("1".equals(database.getActive()) && "Y".equals(database.getVerify())) {
					activeDatabases.add(database);
				}
			}
		}
		request.setAttribute("databaseIds", databaseIds);
		request.setAttribute("databases", activeDatabases);

		return new ModelAndView("/datamgr/myproject/choose");
	}

	@RequestMapping("/chooseSubs")
	public ModelAndView chooseSubs(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Project project = projectService.getProject(RequestUtils.getLong(request, "projectId"));
		if (project != null) {
			request.setAttribute("project", project);
			if (project.getSubordinateIds() != null && !project.getSubordinateIds().isEmpty()) {
				ProjectQuery query = new ProjectQuery();
				query.setProjectIds(project.getSubordinateIds());

				List<Project> projects = projectService.list(query);

				if (projects != null && !projects.isEmpty()) {
					request.setAttribute("trees", projects);
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("myproject.chooseSubs");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/myproject/choose_subs", modelMap);
	}

	@ResponseBody
	@RequestMapping("/deleteById")
	public byte[] deleteById(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Project project = projectService.getProject(RequestUtils.getLong(request, "id"));
		if (project != null) {
			if (loginContext.isSystemAdministrator()
					|| StringUtils.equals(loginContext.getActorId(), project.getCreateBy())) {
				ProjectQuery query = new ProjectQuery();
				query.parentId(project.getId());
				int count = projectService.getProjectCountByQueryCriteria(query);
				if (count > 0) {
					return ResponseUtils.responseJsonResult(false, "不能删除存在子节点的工程。");
				}
				projectService.deleteById(project.getId());
				return ResponseUtils.responseJsonResult(true);
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);

		Project project = projectService.getProject(RequestUtils.getLong(request, "id"));
		if (project != null) {
			request.setAttribute("project", project);
			request.setAttribute("parentId", project.getParentId());
			request.setAttribute("category", project.getCategory());
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjectQuery query = new ProjectQuery();
		Tools.populate(query, params);

		if (project != null && StringUtils.isNotEmpty(project.getCategory())) {
			query.category(project.getCategory());
		}

		long parentId = RequestUtils.getLong(request, "parentId");
		Project parent = null;
		if (parentId > 0) {
			parent = projectService.getProject(parentId);
		}
		if (parent != null) {
			query.treeIdLike(parent.getTreeId() + "%");
		}

		List<Project> projects = null;
		if (loginContext.isSystemAdministrator()) {
			query.setOrderBy(" E.TREEID_ asc ");
			projects = projectService.list(query);
		} else {
			projects = projectService.getProjects(loginContext.getActorId());
		}

		if (projects != null && !projects.isEmpty()) {
			request.setAttribute("projects", projects);
		}

		List<Dictory> dicts = dictoryService.getDictoryList("project_area");
		request.setAttribute("dicts", dicts);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("myproject.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/myproject/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjectQuery query = new ProjectQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		long parentId = RequestUtils.getLong(request, "parentId");
		Project parent = null;
		if (parentId > 0) {
			parent = projectService.getProject(parentId);
		}
		if (parent != null) {
			query.treeIdLike(parent.getTreeId() + "%");
		}
		if (!loginContext.isSystemAdministrator()) {
			List<Long> projectIds = new ArrayList<Long>();
			projectIds.add(-1L);
			List<Project> list = projectService.getProjects(loginContext.getActorId());
			if (list != null && !list.isEmpty()) {
				for (Project p : list) {
					projectIds.add(p.getId());
				}
			}
			query.setProjectIds(projectIds);
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
		int total = projectService.getProjectCountByQueryCriteria(query);
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

			List<Project> list = projectService.getProjectsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Project project : list) {
					JSONObject rowJSON = project.toJsonObject();
					rowJSON.put("id", project.getId());
					rowJSON.put("rowId", project.getId());
					rowJSON.put("projectId", project.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
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

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/myproject/list", modelMap);
	}

	@RequestMapping("/permission")
	public ModelAndView permission(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		logger.debug("->params:" + RequestUtils.getParameterMap(request));
		String nameLike = request.getParameter("nameLike");

		String op_view = request.getParameter("op_view");
		if (StringUtils.isEmpty(op_view)) {
			op_view = "user";
		}

		request.setAttribute("op_view", op_view);

		ProjectQuery databaseQuery = new ProjectQuery();
		databaseQuery.setActive("1");
		databaseQuery.createBy(loginContext.getActorId());

		if (StringUtils.isNotEmpty(nameLike) && StringUtils.equals(op_view, "project")) {
			databaseQuery.setTitleLike(nameLike);
		}
		List<Project> projectList = projectService.list(databaseQuery);
		request.setAttribute("projectList", projectList);

		List<ProjectAccess> accesses = projectService.getAllProjectAccesses();

		UserQuery query = new UserQuery();

		if (StringUtils.isNotEmpty(nameLike) && StringUtils.equals(op_view, "user")) {
			query.nameLike(nameLike);
		}
		List<User> users = null;
		if (StringUtils.isNotEmpty(nameLike) && StringUtils.equals(op_view, "user")) {
			users = IdentityFactory.searchUsers(nameLike);
		} else {
			users = IdentityFactory.getUsers();
		}

		if (users != null && !users.isEmpty()) {
			List<User> userList = new ArrayList<User>();
			for (User user : users) {
				if (user.isSystemAdministrator()) {
					continue;
				}
				if (accesses != null && !accesses.isEmpty()) {
					for (ProjectAccess access : accesses) {
						if (StringUtils.equalsIgnoreCase(user.getActorId(), access.getActorId())) {
							user.addRowKey(String.valueOf(access.getProjectId()));
						}
					}
				}
				userList.add(user);
			}
			request.setAttribute("users", userList);
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

		String x_view = ViewProperties.getString("myproject.permission");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/myproject/permission", modelMap);
	}

	@RequestMapping("/projectDatabases")
	@ResponseBody
	public void projectDatabases(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = null;
		try {
			Long projectId = RequestUtils.getLong(request, "projectId");
			List<Long> databaseIds = projectService.getDatabaseIds(projectId);
			DatabaseQuery query = new DatabaseQuery();
			query.setDatabaseIds(databaseIds);
			List<Database> databases = databaseService.list(query);
			JSONArray result = new JSONArray();
			if (databases != null && !databases.isEmpty()) {
				for (Database model : databases) {
					JSONObject jsonObject = model.toJsonObject();
					jsonObject.remove("dbname");
					jsonObject.remove("user");
					jsonObject.remove("password");
					jsonObject.remove("key");
					result.add(jsonObject);
				}
			}

			response.setContentType("application/json");
			writer = response.getWriter();
			writer.write(result.toJSONString());
			writer.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.close(writer);
		}
	}

	@RequestMapping("projectUsersJson")
	@ResponseBody
	public byte[] projectUsersJson(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		JSONObject result = new JSONObject();
		long projectId = RequestUtils.getLong(request, "projectId");
		String xtype = request.getParameter("xtype");
		String utype = request.getParameter("utype");
		Collection<String> userIds = new HashSet<String>();
		Project project = null;
		if (projectId > 0) {
			project = projectService.getProject(projectId);
			if (StringUtils.equals(xtype, "owners")) {
				if (project != null && project.getOwners() != null) {
					for (ProjectOwner owner : project.getOwners()) {
						userIds.add(owner.getActorId());
					}
				}
			} else {
				if (project != null && project.getActorIds() != null) {
					for (String actorId : project.getActorIds()) {
						userIds.add(actorId);
					}
				}
			}
		}

		List<TreeModel> manageTreeModels = null;

		if (loginContext.isSystemAdministrator()) {
			manageTreeModels = complexUserService.getAllDepartmentTrees();
		} else {
			manageTreeModels = complexUserService.getUserManageBranch(loginContext.getActorId());
		}

		logger.debug("manageTreeModels:" + manageTreeModels);

		Map<Long, TreeModel> myTreeMap = new HashMap<Long, TreeModel>();
		if (manageTreeModels != null && !manageTreeModels.isEmpty()) {
			for (TreeModel t : manageTreeModels) {
				myTreeMap.put(t.getId(), t);
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

			List<SysDepartment> list = sysDepartmentService.getSysDepartmentList();
			Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
			for (SysDepartment dept : list) {
				deptMap.put(dept.getNodeId(), dept);
			}

			if (manageTreeModels != null && !manageTreeModels.isEmpty()) {
				logger.debug("dept tree size:" + manageTreeModels.size());
				Map<Long, TreeModel> treeMap = new HashMap<Long, TreeModel>();
				for (TreeModel tree : manageTreeModels) {
					if (myTreeMap.get(tree.getId()) == null) {
						continue;
					}
					SysDepartment dept = deptMap.get(tree.getId());
					treeMap.put(dept.getId(), tree);
				}

				for (TreeModel tree : manageTreeModels) {
					if (myTreeMap.get(tree.getId()) == null) {
						continue;
					}
					treeModels.add(tree);
					SysDepartment dept = deptMap.get(tree.getId());
					if (dept != null && dept.getId() > 0) {
						for (SysUser user : users) {
							if (user.isSystemAdmin()) {
								continue;
							}
							TreeModel t = treeMap.get(user.getDeptId());
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

			if (StringUtils.equals(utype, "ALL")) {
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
			}
			// logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			JSONArray jsonArray = treeHelper.getTreeJSONArray(treeModels);
			// logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("myproject.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/myproject/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjectQuery query = new ProjectQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ProjectDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
		int total = projectService.getProjectCountByQueryCriteria(query);
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

			List<Project> list = projectService.getProjectsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Project project : list) {
					JSONObject rowJSON = project.toJsonObject();
					rowJSON.put("id", project.getId());
					rowJSON.put("projectId", project.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		Project project = new Project();
		Tools.populate(project, params);

		project.setArea(request.getParameter("area"));
		project.setName(request.getParameter("name"));
		project.setCode(request.getParameter("code"));
		project.setTitle(request.getParameter("title"));
		project.setCategory(request.getParameter("category"));
		project.setDiscriminator(request.getParameter("discriminator"));
		project.setIcon(request.getParameter("icon"));
		project.setIconCls(request.getParameter("iconCls"));
		project.setType(request.getParameter("type"));
		project.setSort(RequestUtils.getInt(request, "sort"));
		project.setActive(request.getParameter("active"));
		project.setCreateBy(actorId);

		if (StringUtils.isNotBlank(request.getParameter("owners"))) {
			StringTokenizer token = new StringTokenizer(request.getParameter("owners"), ",");
			while (token.hasMoreTokens()) {
				String str = token.nextToken();
				if (StringUtils.isNotEmpty(str)) {
					ProjectOwner owner = new ProjectOwner();
					owner.setActorId(str);
					project.addOwner(owner);
				}
			}
		}

		if (StringUtils.isNotBlank(request.getParameter("accessors"))) {
			StringTokenizer token2 = new StringTokenizer(request.getParameter("accessors"), ",");
			while (token2.hasMoreTokens()) {
				String str = token2.nextToken();
				if (StringUtils.isNotEmpty(str)) {
					project.addAccessor(str);
				}
			}
		}

		if (StringUtils.isNotBlank(request.getParameter("subordinateIds"))) {
			StringTokenizer token3 = new StringTokenizer(request.getParameter("subordinateIds"), ",");
			while (token3.hasMoreTokens()) {
				String str = token3.nextToken();
				if (StringUtils.isNotEmpty(str)) {
					project.addSubordinate(Long.parseLong(str));
				}
			}
			project.setSubIds(request.getParameter("subordinateIds"));
		}

		projectService.save(project);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAccessor")
	public byte[] saveAccessor(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long projectId = RequestUtils.getLong(request, "projectId");
		String actorId = request.getParameter("actorId");
		String operation = request.getParameter("operation");
		if (projectId > 0 && actorId != null) {
			Project project = projectService.getProject(projectId);
			if (project != null) {
				if (loginContext.isSystemAdministrator()
						|| StringUtils.equals(loginContext.getActorId(), project.getCreateBy())) {
					if (StringUtils.equals(operation, "revoke")) {
						projectService.deleteAccessor(projectId, actorId);
					} else {
						projectService.createAccessor(projectId, actorId);
					}
					return ResponseUtils.responseResult(true);
				}
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveDB")
	public byte[] saveDB(HttpServletRequest request) {
		logger.debug(RequestUtils.getParameterMap(request));
		String dbIds = request.getParameter("databaseIds");
		String projectId = request.getParameter("projectId");
		List<Long> databaseIds = StringTools.splitToLong(dbIds);
		if (databaseIds != null && !databaseIds.isEmpty() && projectId != null) {
			try {
				projectService.saveAll(Long.parseLong(projectId), databaseIds);
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Project saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Project project = new Project();
		try {
			Tools.populate(project, model);
			project.setArea(request.getParameter("area"));
			project.setName(ParamUtils.getString(model, "name"));
			project.setCode(ParamUtils.getString(model, "code"));
			project.setTitle(ParamUtils.getString(model, "title"));
			project.setCategory(request.getParameter("category"));
			project.setDiscriminator(request.getParameter("discriminator"));
			project.setIcon(request.getParameter("icon"));
			project.setIconCls(request.getParameter("iconCls"));
			project.setType(request.getParameter("type"));
			project.setSort(RequestUtils.getInt(request, "sort"));
			project.setActive(ParamUtils.getString(model, "active"));
			project.setCreateBy(actorId);
			project.setUpdateBy(actorId);

			if (StringUtils.isNotBlank(request.getParameter("owners"))) {
				StringTokenizer token = new StringTokenizer(request.getParameter("owners"), ",");
				while (token.hasMoreTokens()) {
					String str = token.nextToken();
					if (StringUtils.isNotEmpty(str)) {
						ProjectOwner owner = new ProjectOwner();
						owner.setActorId(str);
						project.addOwner(owner);
					}
				}
			}

			if (StringUtils.isNotBlank(request.getParameter("accessors"))) {
				StringTokenizer token2 = new StringTokenizer(request.getParameter("accessors"), ",");
				while (token2.hasMoreTokens()) {
					String str = token2.nextToken();
					if (StringUtils.isNotEmpty(str)) {
						project.addAccessor(str);
					}
				}
			}

			if (StringUtils.isNotBlank(request.getParameter("subordinateIds"))) {
				StringTokenizer token3 = new StringTokenizer(request.getParameter("subordinateIds"), ",");
				while (token3.hasMoreTokens()) {
					String str = token3.nextToken();
					if (StringUtils.isNotEmpty(str)) {
						project.addSubordinate(Long.parseLong(str));
					}
				}
				project.setSubIds(request.getParameter("subordinateIds"));
			}

			this.projectService.save(project);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return project;
	}

	@ResponseBody
	@RequestMapping("/saveProject")
	public byte[] saveProject(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		Project project = new Project();
		try {
			Tools.populate(project, params);
			project.setArea(request.getParameter("area"));
			project.setName(request.getParameter("name"));
			project.setCode(request.getParameter("code"));
			project.setTitle(request.getParameter("title"));
			project.setCategory(request.getParameter("category"));
			project.setDiscriminator(request.getParameter("discriminator"));
			project.setIcon(request.getParameter("icon"));
			project.setIconCls(request.getParameter("iconCls"));
			project.setType(request.getParameter("type"));
			project.setSort(RequestUtils.getInt(request, "sort"));
			project.setActive(request.getParameter("active"));
			project.setCreateBy(actorId);
			project.setUpdateBy(actorId);

			if (StringUtils.isNotBlank(request.getParameter("owners"))) {
				StringTokenizer token = new StringTokenizer(request.getParameter("owners"), ",");
				while (token.hasMoreTokens()) {
					String str = token.nextToken();
					if (StringUtils.isNotEmpty(str)) {
						ProjectOwner owner = new ProjectOwner();
						owner.setActorId(str);
						project.addOwner(owner);
					}
				}
			}

			if (StringUtils.isNotBlank(request.getParameter("accessors"))) {
				StringTokenizer token2 = new StringTokenizer(request.getParameter("accessors"), ",");
				while (token2.hasMoreTokens()) {
					String str = token2.nextToken();
					if (StringUtils.isNotEmpty(str)) {
						project.addAccessor(str);
					}
				}
			}

			if (StringUtils.isNotBlank(request.getParameter("subordinateIds"))) {
				StringTokenizer token3 = new StringTokenizer(request.getParameter("subordinateIds"), ",");
				while (token3.hasMoreTokens()) {
					String str = token3.nextToken();
					if (StringUtils.isNotEmpty(str)) {
						project.addSubordinate(Long.parseLong(str));
					}
				}
				project.setSubIds(request.getParameter("subordinateIds"));
			}

			this.projectService.save(project);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
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
					t1.setTableName("PROJECT_QUERY");
					ColumnModel idColumn1 = new ColumnModel();
					idColumn1.setColumnName("ID_");
					idColumn1.setValue(Long.parseLong(item));
					t1.setIdColumn(idColumn1);
					ColumnModel column = new ColumnModel();
					column.setColumnName("SORT_");
					column.setValue(sort);
					t1.addColumn(column);
					rows.add(t1);
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
	public void setComplexUserService(ComplexUserService complexUserService) {
		this.complexUserService = complexUserService;
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	@javax.annotation.Resource
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
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
	 * 显示排序页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showSort")
	public ModelAndView showSort(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long parentId = RequestUtils.getLongParameter(request, "parentId", 0);

		ProjectQuery query = new ProjectQuery();
		query.parentId(parentId);

		if (!loginContext.isSystemAdministrator()) {
			List<Long> projectIds = new ArrayList<Long>();
			projectIds.add(-1L);
			List<Project> list = projectService.getProjects(loginContext.getActorId());
			if (list != null && !list.isEmpty()) {
				for (Project p : list) {
					projectIds.add(p.getId());
				}
			}
			query.setProjectIds(projectIds);
		}

		List<Project> projects = projectService.list(query);
		request.setAttribute("projects", projects);

		String x_view = ViewProperties.getString("project.showSort");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/myproject/showSort", modelMap);
	}

	@ResponseBody
	@RequestMapping("/treeJson")
	public byte[] treeJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("------------------------treeJson--------------------");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		List<Long> selected = new ArrayList<Long>();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjectQuery query = new ProjectQuery();
		Tools.populate(query, params);
		Project project = projectService.getProject(RequestUtils.getLong(request, "projectId"));
		if (project != null) {
			request.setAttribute("project", project);
			if (project.getSubordinateIds() != null && !project.getSubordinateIds().isEmpty()) {
				query.setProjectIds(project.getSubordinateIds());
				List<Project> projects = projectService.list(query);
				if (projects != null && !projects.isEmpty()) {
					for (Project p : projects) {
						selected.add(p.getId());
					}
				}
			}
		}

		query = new ProjectQuery();
		Tools.populate(query, params);
		List<Project> projects = null;
		if (loginContext.isSystemAdministrator()) {
			query.setOrderBy(" E.TREEID_ asc ");
			projects = projectService.list(query);
		} else {
			projects = projectService.getProjects(loginContext.getActorId());
		}

		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		if (projects != null && !projects.isEmpty()) {
			for (Project p : projects) {
				TreeModel tree = new BaseTree();
				tree.setId(p.getId());
				tree.setParentId(p.getParentId());
				tree.setName(p.getName());
				tree.setTreeId(p.getTreeId());
				tree.setIcon(p.getIcon());
				tree.setIconCls(p.getIcon());
				tree.setDiscriminator(p.getDiscriminator());
				tree.setCode(p.getCode());
				tree.setSortNo(p.getSort());
				tree.setLevel(p.getLevel());
				Map<String, Object> dataMap = new HashMap<String, Object>();
				if (selected.contains(p.getId())) {
					tree.setChecked(true);
					dataMap.put("checked", true);
				} else {
					dataMap.put("checked", false);
				}
				tree.setDataMap(dataMap);
				if (StringUtils.equals(p.getActive(), "0")) {
					tree.setLocked(1);
				}
				treeModels.add(tree);
			}
		}

		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		ArrayNode responseJSON = treeHelper.getTreeArrayNode(treeModels);
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}

	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		Project project = projectService.getProject(RequestUtils.getLong(request, "id"));

		Tools.populate(project, params);

		project.setArea(request.getParameter("area"));
		project.setName(request.getParameter("name"));
		project.setCode(request.getParameter("code"));
		project.setTitle(request.getParameter("title"));
		project.setCategory(request.getParameter("category"));
		project.setDiscriminator(request.getParameter("discriminator"));
		project.setIcon(request.getParameter("icon"));
		project.setIconCls(request.getParameter("iconCls"));
		project.setType(request.getParameter("type"));
		project.setSort(RequestUtils.getInt(request, "sort"));
		project.setActive(request.getParameter("active"));
		project.setUpdateBy(actorId);

		if (StringUtils.isNotBlank(request.getParameter("owners"))) {
			StringTokenizer token = new StringTokenizer(request.getParameter("owners"), ",");
			while (token.hasMoreTokens()) {
				String str = token.nextToken();
				if (StringUtils.isNotEmpty(str)) {
					ProjectOwner owner = new ProjectOwner();
					owner.setActorId(str);
					project.addOwner(owner);
				}
			}
		}

		if (StringUtils.isNotBlank(request.getParameter("accessors"))) {
			StringTokenizer token2 = new StringTokenizer(request.getParameter("accessors"), ",");
			while (token2.hasMoreTokens()) {
				String str = token2.nextToken();
				if (StringUtils.isNotEmpty(str)) {
					project.addAccessor(str);
				}
			}
		}

		if (StringUtils.isNotBlank(request.getParameter("subordinateIds"))) {
			StringTokenizer token3 = new StringTokenizer(request.getParameter("subordinateIds"), ",");
			while (token3.hasMoreTokens()) {
				String str = token3.nextToken();
				if (StringUtils.isNotEmpty(str)) {
					project.addSubordinate(Long.parseLong(str));
				}
			}
			project.setSubIds(request.getParameter("subordinateIds"));
		}

		projectService.save(project);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Project project = projectService.getProject(RequestUtils.getLong(request, "id"));
		request.setAttribute("project", project);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("myproject.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/myproject/view");
	}
}
