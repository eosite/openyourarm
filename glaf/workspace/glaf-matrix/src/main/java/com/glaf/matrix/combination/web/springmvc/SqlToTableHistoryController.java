package com.glaf.matrix.combination.web.springmvc;

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
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.matrix.combination.domain.*;
import com.glaf.matrix.combination.query.*;
import com.glaf.matrix.combination.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/sqlToTableHistory")
@RequestMapping("/sys/sqlToTableHistory")
public class SqlToTableHistoryController {
	protected static final Log logger = LogFactory.getLog(SqlToTableHistoryController.class);

	protected SqlToTableHistoryService sqlToTableHistoryService;

	public SqlToTableHistoryController() {

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SqlToTableHistory sqlToTableHistory = sqlToTableHistoryService
				.getSqlToTableHistory(RequestUtils.getLong(request, "id"));
		if (sqlToTableHistory != null) {
			request.setAttribute("sqlToTableHistory", sqlToTableHistory);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sqlToTableHistory.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/sqlToTableHistory/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlToTableHistoryQuery query = new SqlToTableHistoryQuery();
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
		int total = sqlToTableHistoryService.getSqlToTableHistoryCountByQueryCriteria(query);
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

			List<SqlToTableHistory> list = sqlToTableHistoryService.getSqlToTableHistorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlToTableHistory sqlToTableHistory : list) {
					JSONObject rowJSON = sqlToTableHistory.toJsonObject();
					rowJSON.put("id", sqlToTableHistory.getId());
					rowJSON.put("rowId", sqlToTableHistory.getId());
					rowJSON.put("sqlToTableHistoryId", sqlToTableHistory.getId());
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

		return new ModelAndView("/matrix/sqlToTableHistory/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sqlToTableHistory.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/matrix/sqlToTableHistory/query", modelMap);
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.combination.service.sqlToTableHistoryService")
	public void setSqlToTableHistoryService(SqlToTableHistoryService sqlToTableHistoryService) {
		this.sqlToTableHistoryService = sqlToTableHistoryService;
	}

}
