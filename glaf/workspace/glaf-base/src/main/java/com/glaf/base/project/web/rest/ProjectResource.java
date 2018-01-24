package com.glaf.base.project.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TreeModel;
import com.glaf.core.security.LoginContext;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.*;

import com.glaf.base.project.domain.Project;
import com.glaf.base.project.query.ProjectQuery;
import com.glaf.base.project.service.ProjectService;
import com.glaf.base.project.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller("/rs/datamgr/project")
@Path("/rs/datamgr/project")
public class ProjectResource {
	protected static final Log logger = LogFactory.getLog(ProjectResource.class);

	protected ProjectService projectService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjectQuery query = new ProjectQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		ProjectDomainFactory.processDataRequest(dataRequest);

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
					rowJSON.put("projectQueryId", project.getId());
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

	@POST
	@Path("/deleteById")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjectQuery query = new ProjectQuery();
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

	@javax.annotation.Resource
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] treeJson(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long parentId = RequestUtils.getLong(request, "parentId");
		Project parent = null;
		if (parentId > 0) {
			parent = projectService.getProject(parentId);
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjectQuery query = new ProjectQuery();
		Tools.populate(query, params);

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
		List<Project> projects = projectService.list(query);
		if (projects != null && !projects.isEmpty()) {
			List<TreeModel> trees = new ArrayList<TreeModel>();
			for (Project p : projects) {
				TreeModel t = new BaseTree();
				t.setId(p.getId());
				t.setParentId(p.getParentId());
				t.setTreeId(p.getTreeId());
				t.setIcon(p.getIcon());
				t.setIconCls(p.getIconCls());
				t.setName(p.getName());
				t.setCode(p.getCode());
				t.setDiscriminator(p.getDiscriminator());
				if (StringUtils.equals(p.getActive(), "0")) {
					t.setLocked(1);
				}
				t.setCreateBy(p.getCreateBy());
				t.setCreateDate(p.getCreateTime());
				trees.add(t);
			}
			TreeHelper treeHelper = new TreeHelper();
			JSONArray result = treeHelper.getTreeJSONArray(trees);
			logger.debug(result.toJSONString());
			return result.toJSONString().getBytes("UTF-8");
		}

		JSONArray result = new JSONArray();
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		Project project = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			project = projectService.getProject(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (project != null) {
			result = project.toJsonObject();
			result.put("id", project.getId());
			result.put("projectId", project.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
