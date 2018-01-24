package com.glaf.datamgr.web.springmvc;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.domain.TableExecutionColumn;
import com.glaf.datamgr.query.TableExecutionColumnQuery;
import com.glaf.datamgr.service.TableExecutionColumnService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/tableExecutionColumn")
@RequestMapping("/sys/tableExecutionColumn")
public class TableExecutionColumnController {
	protected static final Log logger = LogFactory.getLog(TableExecutionColumnController.class);

	protected TableExecutionColumnService tableExecutionColumnService;

	public TableExecutionColumnController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					TableExecutionColumn tableExecutionColumn = tableExecutionColumnService
							.getTableExecutionColumn(String.valueOf(x));
					if (tableExecutionColumn != null && loginContext.isSystemAdministrator()) {
						tableExecutionColumnService.deleteById(x);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TableExecutionColumn tableExecutionColumn = tableExecutionColumnService
					.getTableExecutionColumn(String.valueOf(id));
			if (tableExecutionColumn != null && loginContext.isSystemAdministrator()) {
				tableExecutionColumnService.deleteById(id);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableExecutionColumn tableExecutionColumn = tableExecutionColumnService
				.getTableExecutionColumn(request.getParameter("id"));
		if (tableExecutionColumn != null) {
			request.setAttribute("tableExecutionColumn", tableExecutionColumn);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableExecutionColumn.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableExecution/editColumn", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableExecutionColumnQuery query = new TableExecutionColumnQuery();
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
		
		String executionId = RequestUtils.getString(request, "executionId");
		query.executionId(executionId);

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
		int total = tableExecutionColumnService.getTableExecutionColumnCountByQueryCriteria(query);
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

			List<TableExecutionColumn> list = tableExecutionColumnService.getTableExecutionColumnsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableExecutionColumn tableExecutionColumn : list) {
					JSONObject rowJSON = tableExecutionColumn.toJsonObject();
					rowJSON.put("id", tableExecutionColumn.getId());
					rowJSON.put("rowId", tableExecutionColumn.getId());
					rowJSON.put("tableExecutionColumnId", tableExecutionColumn.getId());
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

		return new ModelAndView("/datamgr/tableExecution/columns", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TableExecutionColumn tableExecutionColumn = new TableExecutionColumn();
		Tools.populate(tableExecutionColumn, params);

		tableExecutionColumn.setExecutionId(request.getParameter("executionId"));
		tableExecutionColumn.setTitle(request.getParameter("title"));
		tableExecutionColumn.setColumnName(request.getParameter("columnName"));
		tableExecutionColumn.setType(request.getParameter("type"));
		tableExecutionColumn.setValueExpression(request.getParameter("valueExpression"));
		tableExecutionColumn.setLocked(RequestUtils.getInt(request, "locked"));

		tableExecutionColumnService.save(tableExecutionColumn);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody TableExecutionColumn saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		TableExecutionColumn tableExecutionColumn = new TableExecutionColumn();
		try {
			Tools.populate(tableExecutionColumn, model);
			tableExecutionColumn.setExecutionId(ParamUtils.getString(model, "executionId"));
			tableExecutionColumn.setTitle(ParamUtils.getString(model, "title"));
			tableExecutionColumn.setColumnName(ParamUtils.getString(model, "columnName"));
			tableExecutionColumn.setType(ParamUtils.getString(model, "type"));
			tableExecutionColumn.setValueExpression(ParamUtils.getString(model, "valueExpression"));
			tableExecutionColumn.setLocked(ParamUtils.getInt(model, "locked"));

			this.tableExecutionColumnService.save(tableExecutionColumn);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return tableExecutionColumn;
	}

	@ResponseBody
	@RequestMapping("/saveTableExecutionColumn")
	public byte[] saveTableExecutionColumn(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableExecutionColumn tableExecutionColumn = new TableExecutionColumn();
		try {
			Tools.populate(tableExecutionColumn, params);
			tableExecutionColumn.setExecutionId(request.getParameter("executionId"));
			tableExecutionColumn.setTitle(request.getParameter("title"));
			tableExecutionColumn.setColumnName(request.getParameter("columnName"));
			tableExecutionColumn.setType(request.getParameter("type"));
			tableExecutionColumn.setValueExpression(request.getParameter("valueExpression"));
			tableExecutionColumn.setLocked(RequestUtils.getInt(request, "locked"));

			this.tableExecutionColumnService.save(tableExecutionColumn);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.tableExecutionColumnService")
	public void setTableExecutionColumnService(TableExecutionColumnService tableExecutionColumnService) {
		this.tableExecutionColumnService = tableExecutionColumnService;
	}

}
