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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.glaf.datamgr.bean.TableExecutionBean;
import com.glaf.datamgr.domain.TableExecution;
import com.glaf.datamgr.query.TableExecutionQuery;
import com.glaf.datamgr.service.TableExecutionService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/tableExecution")
@RequestMapping("/sys/tableExecution")
public class TableExecutionController {
	protected static final Log logger = LogFactory.getLog(TableExecutionController.class);

	protected TableExecutionService tableExecutionService;

	public TableExecutionController() {

	}

	@RequestMapping("/choose")
	public ModelAndView choose(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableExecutionQuery query = new TableExecutionQuery();
		query.locked(0);

		String selected = request.getParameter("selected");
		List<TableExecution> executions = tableExecutionService.list(query);
		if (executions != null && !executions.isEmpty()) {
			List<String> selectedIds = new ArrayList<String>();
			List<TableExecution> selectedExecutions = new ArrayList<TableExecution>();
			List<TableExecution> unselectedExecutions = new ArrayList<TableExecution>();
			for (TableExecution e : executions) {
				if (StringUtils.contains(selected, e.getId())) {
					selectedExecutions.add(e);
					selectedIds.add(e.getId());
				} else {
					unselectedExecutions.add(e);
				}
			}
			request.setAttribute("selectedIds", selectedIds);
			request.setAttribute("executions", executions);
			request.setAttribute("selectedExecutions", selectedExecutions);
			request.setAttribute("unselectedExecutions", unselectedExecutions);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableExecution.choose");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableExecution/choose", modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					TableExecution tableExecution = tableExecutionService.getTableExecution(String.valueOf(x));
					if (tableExecution != null
							&& (StringUtils.equals(tableExecution.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						tableExecutionService.deleteById(x);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TableExecution tableExecution = tableExecutionService.getTableExecution(String.valueOf(id));
			if (tableExecution != null && (StringUtils.equals(tableExecution.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				tableExecutionService.deleteById(id);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableExecution tableExecution = tableExecutionService.getTableExecution(request.getParameter("id"));
		if (tableExecution != null) {
			request.setAttribute("tableExecution", tableExecution);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableExecution.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableExecution/edit", modelMap);
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request) {
		String executionId = RequestUtils.getString(request, "executionId");
		long databaseId = RequestUtils.getLong(request, "databaseId");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableExecution tableExecution = tableExecutionService.getTableExecution(executionId);
		if (tableExecution != null) {
			// https://127.0.0.1:8443/glaf/mx/sys/tableExecution/execute?databaseId=19&id=111&executionId=9d9c583feb8342ab9eb9ac426eec9b16
			/// mx/sys/tableExecution/execute?databaseId=19&id=111&executionId=9d9c583feb8342ab9eb9ac426eec9b16
			List<String> executionIds = new ArrayList<String>();
			executionIds.add(executionId);
			TableExecutionBean bean = new TableExecutionBean();
			boolean result = bean.execute(databaseId, executionIds, params);
			return ResponseUtils.responseResult(result);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableExecutionQuery query = new TableExecutionQuery();
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
		int total = tableExecutionService.getTableExecutionCountByQueryCriteria(query);
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

			List<TableExecution> list = tableExecutionService.getTableExecutionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableExecution tableExecution : list) {
					JSONObject rowJSON = tableExecution.toJsonObject();
					rowJSON.put("id", tableExecution.getId());
					rowJSON.put("rowId", tableExecution.getId());
					rowJSON.put("tableExecutionId", tableExecution.getId());
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

		return new ModelAndView("/datamgr/tableExecution/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("tableExecution.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/tableExecution/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TableExecution tableExecution = new TableExecution();
		Tools.populate(tableExecution, params);

		tableExecution.setTitle(request.getParameter("title"));
		tableExecution.setContent(request.getParameter("content"));
		tableExecution.setTableName(request.getParameter("tableName"));
		tableExecution.setType(request.getParameter("type"));
		tableExecution.setLocked(RequestUtils.getInt(request, "locked"));
		tableExecution.setCreateBy(actorId);

		tableExecutionService.save(tableExecution);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody TableExecution saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		TableExecution tableExecution = new TableExecution();
		try {
			Tools.populate(tableExecution, model);
			tableExecution.setTitle(ParamUtils.getString(model, "title"));
			tableExecution.setContent(ParamUtils.getString(model, "content"));
			tableExecution.setTableName(ParamUtils.getString(model, "tableName"));
			tableExecution.setType(ParamUtils.getString(model, "type"));
			tableExecution.setLocked(ParamUtils.getInt(model, "locked"));
			tableExecution.setCreateBy(actorId);
			this.tableExecutionService.save(tableExecution);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return tableExecution;
	}

	@ResponseBody
	@RequestMapping("/saveTableExecution")
	public byte[] saveTableExecution(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableExecution tableExecution = new TableExecution();
		try {
			Tools.populate(tableExecution, params);
			tableExecution.setTitle(request.getParameter("title"));
			tableExecution.setContent(request.getParameter("content"));
			tableExecution.setTableName(request.getParameter("tableName"));
			tableExecution.setType(request.getParameter("type"));
			tableExecution.setLocked(RequestUtils.getInt(request, "locked"));
			tableExecution.setCreateBy(actorId);
			this.tableExecutionService.save(tableExecution);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.tableExecutionService")
	public void setTableExecutionService(TableExecutionService tableExecutionService) {
		this.tableExecutionService = tableExecutionService;
	}

}
