package com.glaf.matrix.aggregate.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.matrix.aggregate.domain.*;
import com.glaf.matrix.aggregate.query.*;
import com.glaf.matrix.aggregate.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/tableAggregateItem")
@RequestMapping("/sys/tableAggregateItem")
public class TableAggregateItemController {
	protected static final Log logger = LogFactory.getLog(TableAggregateItemController.class);

	protected TableAggregateItemService tableAggregateItemService;

	public TableAggregateItemController() {

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
					TableAggregateItem tableAggregateItem = tableAggregateItemService.getTableAggregateItem(Long.valueOf(x));
					if (tableAggregateItem != null && (StringUtils.equals(tableAggregateItem.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						tableAggregateItemService.deleteById(tableAggregateItem.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TableAggregateItem tableAggregateItem = tableAggregateItemService.getTableAggregateItem(Long.valueOf(id));
			if (tableAggregateItem != null && (StringUtils.equals(tableAggregateItem.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				tableAggregateItemService.deleteById(tableAggregateItem.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableAggregateItem tableAggregateItem = tableAggregateItemService.getTableAggregateItem(RequestUtils.getLong(request, "id"));
		if (tableAggregateItem != null) {
			request.setAttribute("tableAggregateItem", tableAggregateItem);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableAggregateItem.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/tableAggregateItem/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableAggregateItemQuery query = new TableAggregateItemQuery();
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
		int total = tableAggregateItemService.getTableAggregateItemCountByQueryCriteria(query);
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

			List<TableAggregateItem> list = tableAggregateItemService.getTableAggregateItemsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableAggregateItem tableAggregateItem : list) {
					JSONObject rowJSON = tableAggregateItem.toJsonObject();
					rowJSON.put("id", tableAggregateItem.getId());
					rowJSON.put("rowId", tableAggregateItem.getId());
					rowJSON.put("tableAggregateItemId", tableAggregateItem.getId());
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

		return new ModelAndView("/matrix/tableAggregateItem/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("tableAggregateItem.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/matrix/tableAggregateItem/query", modelMap);
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableAggregateItem tableAggregateItem = new TableAggregateItem();
		try {
			Tools.populate(tableAggregateItem, params);
			tableAggregateItem.setSyncId(RequestUtils.getLong(request, "syncId"));
			tableAggregateItem.setDeploymentId(request.getParameter("deploymentId"));
			tableAggregateItem.setTitle(request.getParameter("title"));
			tableAggregateItem.setSql(request.getParameter("sql"));
			tableAggregateItem.setRemoveSql(request.getParameter("removeSql"));
			tableAggregateItem.setPrimaryKey(request.getParameter("primaryKey"));
			tableAggregateItem.setExpression(request.getParameter("expression"));
			tableAggregateItem.setCreateTableFlag(request.getParameter("createTableFlag"));
			tableAggregateItem.setDeleteFetch(request.getParameter("deleteFetch"));
			tableAggregateItem.setSortNo(RequestUtils.getInt(request, "sortNo"));
			tableAggregateItem.setCreateBy(actorId);
			this.tableAggregateItemService.save(tableAggregateItem);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.aggregate.service.tableAggregateItemService")
	public void setTableAggregateItemService(TableAggregateItemService tableAggregateItemService) {
		this.tableAggregateItemService = tableAggregateItemService;
	}

}
