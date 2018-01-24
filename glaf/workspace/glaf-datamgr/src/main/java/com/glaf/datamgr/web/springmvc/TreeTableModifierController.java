package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.User;

import com.glaf.core.security.LoginContext;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.bean.TreeTableModifierBean;
import com.glaf.datamgr.domain.TreeTableModifier;
import com.glaf.datamgr.query.TreeTableModifierQuery;
import com.glaf.datamgr.service.TreeTableModifierService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/treeTableModifier")
@RequestMapping("/sys/treeTableModifier")
public class TreeTableModifierController {
	protected static final Log logger = LogFactory.getLog(TreeTableModifierController.class);

	protected TreeTableModifierService treeTableModifierService;

	public TreeTableModifierController() {

	}

	@RequestMapping("/chooseDatabases")
	public ModelAndView chooseDatabases(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TreeTableModifier treeTableModifier = treeTableModifierService
				.getTreeTableModifier(RequestUtils.getLong(request, "id"));
		if (treeTableModifier != null) {
			request.setAttribute("treeTableModifier", treeTableModifier);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<Long> selected = new ArrayList<Long>();
			List<Database> unselected = new ArrayList<Database>();
			if (treeTableModifier != null && StringUtils.isNotEmpty(treeTableModifier.getDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(treeTableModifier.getDatabaseIds());
				for (Database database : databases) {
					if (ids.contains(database.getId())) {
						selected.add(database.getId());
					} else {
						unselected.add(database);
					}
				}
				request.setAttribute("selected", selected);
				request.setAttribute("unselected", databases);
			} else {
				request.setAttribute("selected", selected);
				request.setAttribute("unselected", databases);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("treeTableModifier.chooseDatabases");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/treeTableModifier/chooseDatabases", modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					TreeTableModifier treeTableModifier = treeTableModifierService
							.getTreeTableModifier(Long.valueOf(x));
					if (treeTableModifier != null
							&& (StringUtils.equals(treeTableModifier.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						treeTableModifierService.deleteById(treeTableModifier.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TreeTableModifier treeTableModifier = treeTableModifierService.getTreeTableModifier(Long.valueOf(id));
			if (treeTableModifier != null
					&& (StringUtils.equals(treeTableModifier.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				treeTableModifierService.deleteById(treeTableModifier.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TreeTableModifier treeTableModifier = treeTableModifierService
				.getTreeTableModifier(RequestUtils.getLong(request, "id"));
		if (treeTableModifier != null) {
			request.setAttribute("treeTableModifier", treeTableModifier);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			if (treeTableModifier != null && StringUtils.isNotEmpty(treeTableModifier.getDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(treeTableModifier.getDatabaseIds());
				for (Database database : databases) {
					if (ids.contains(database.getId())) {
						buffer.append(database.getTitle()).append("[").append(database.getMapping()).append("]")
								.append(",");
					}
				}
			}
			request.setAttribute("databases", databases);
			request.setAttribute("selectedDB", buffer.toString());
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("treeTableModifier.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/treeTableModifier/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request) {
		long id = RequestUtils.getLong(request, "id");
		int errorCount = 0;
		TreeTableModifier tbl = treeTableModifierService.getTreeTableModifier(id);
		if (tbl != null && StringUtils.isNotEmpty(tbl.getDatabaseIds())) {
			TreeTableModifierBean bean = new TreeTableModifierBean();
			List<Long> databaseIds = StringTools.splitToLong(tbl.getDatabaseIds());
			for (Long databaseId : databaseIds) {
				try {
					bean.execute(databaseId, id);
				} catch (Exception ex) {
					ex.printStackTrace();
					errorCount++;
				}
			}
		}
		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableModifierQuery query = new TreeTableModifierQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
		int total = treeTableModifierService.getTreeTableModifierCountByQueryCriteria(query);
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

			List<TreeTableModifier> list = treeTableModifierService.getTreeTableModifiersByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeTableModifier treeTableModifier : list) {
					JSONObject rowJSON = treeTableModifier.toJsonObject();
					rowJSON.put("id", treeTableModifier.getId());
					rowJSON.put("rowId", treeTableModifier.getId());
					rowJSON.put("treeTableModifierId", treeTableModifier.getId());
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

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/treeTableModifier/list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveTreeTableModifier")
	public byte[] saveTreeTableModifier(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableModifier treeTableModifier = new TreeTableModifier();
		try {
			Tools.populate(treeTableModifier, params);
			treeTableModifier.setTableName(request.getParameter("tableName"));
			treeTableModifier.setTitle(request.getParameter("title"));
			treeTableModifier.setDatabaseIds(request.getParameter("databaseIds"));
			treeTableModifier.setPrimaryKey(request.getParameter("primaryKey"));
			treeTableModifier.setIdColumn(request.getParameter("idColumn"));
			treeTableModifier.setParentIdColumn(request.getParameter("parentIdColumn"));
			treeTableModifier.setTreeIdColumn(request.getParameter("treeIdColumn"));
			treeTableModifier.setLevelColumn(request.getParameter("levelColumn"));
			treeTableModifier.setLocked(RequestUtils.getInt(request, "locked"));
			treeTableModifier.setCreateBy(actorId);
			treeTableModifier.setUpdateBy(actorId);

			this.treeTableModifierService.save(treeTableModifier);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.treeTableModifierService")
	public void setTreeTableModifierService(TreeTableModifierService treeTableModifierService) {
		this.treeTableModifierService = treeTableModifierService;
	}

}
