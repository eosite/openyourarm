package com.glaf.matrix.combination.web.springmvc;

import java.io.IOException;
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
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.matrix.combination.domain.SqlToTableItem;
import com.glaf.matrix.combination.query.SqlToTableItemQuery;
import com.glaf.matrix.combination.service.SqlToTableItemService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/sqlToTableItem")
@RequestMapping("/sys/sqlToTableItem")
public class SqlToTableItemController {
	protected static final Log logger = LogFactory.getLog(SqlToTableItemController.class);

	protected SqlToTableItemService sqlToTableItemService;

	public SqlToTableItemController() {

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
					SqlToTableItem sqlToTableItem = sqlToTableItemService.getSqlToTableItem(Long.valueOf(x));
					if (sqlToTableItem != null
							&& (StringUtils.equals(sqlToTableItem.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						sqlToTableItemService.deleteById(sqlToTableItem.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SqlToTableItem sqlToTableItem = sqlToTableItemService.getSqlToTableItem(Long.valueOf(id));
			if (sqlToTableItem != null && (StringUtils.equals(sqlToTableItem.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				sqlToTableItemService.deleteById(sqlToTableItem.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SqlToTableItem sqlToTableItem = sqlToTableItemService.getSqlToTableItem(RequestUtils.getLong(request, "id"));
		if (sqlToTableItem != null) {
			request.setAttribute("sqlToTableItem", sqlToTableItem);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sqlToTableItem.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/sqlToTableItem/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlToTableItemQuery query = new SqlToTableItemQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
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
		int total = sqlToTableItemService.getSqlToTableItemCountByQueryCriteria(query);
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

			List<SqlToTableItem> list = sqlToTableItemService.getSqlToTableItemsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlToTableItem sqlToTableItem : list) {
					JSONObject rowJSON = sqlToTableItem.toJsonObject();
					rowJSON.put("id", sqlToTableItem.getId());
					rowJSON.put("rowId", sqlToTableItem.getId());
					rowJSON.put("sqlToTableItemId", sqlToTableItem.getId());
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

		return new ModelAndView("/matrix/sqlToTableItem/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sqlToTableItem.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/matrix/sqlToTableItem/query", modelMap);
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlToTableItem sqlToTableItem = new SqlToTableItem();
		try {
			Tools.populate(sqlToTableItem, params);
			sqlToTableItem.setSyncId(RequestUtils.getLong(request, "syncId"));
			sqlToTableItem.setDeploymentId(request.getParameter("deploymentId"));
			sqlToTableItem.setTitle(request.getParameter("title"));
			sqlToTableItem.setSql(request.getParameter("sql"));
			sqlToTableItem.setRecursionSql(request.getParameter("recursionSql"));
			sqlToTableItem.setRecursionColumns(request.getParameter("recursionColumns"));
			sqlToTableItem.setPrimaryKey(request.getParameter("primaryKey"));
			sqlToTableItem.setExpression(request.getParameter("expression"));
			sqlToTableItem.setCreateTableFlag(request.getParameter("createTableFlag"));
			sqlToTableItem.setDeleteFetch(request.getParameter("deleteFetch"));
			sqlToTableItem.setSortNo(RequestUtils.getInt(request, "sortNo"));
			sqlToTableItem.setLocked(RequestUtils.getInt(request, "locked"));
			sqlToTableItem.setCreateBy(actorId);
			this.sqlToTableItemService.save(sqlToTableItem);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.combination.service.sqlToTableItemService")
	public void setSqlToTableItemService(SqlToTableItemService sqlToTableItemService) {
		this.sqlToTableItemService = sqlToTableItemService;
	}

}
