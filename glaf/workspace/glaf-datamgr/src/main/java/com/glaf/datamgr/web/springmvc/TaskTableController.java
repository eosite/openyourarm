package com.glaf.datamgr.web.springmvc;

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
 
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;
import com.glaf.datamgr.task.TaskTableTask;
 

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/taskTable")
@RequestMapping("/sys/taskTable")
public class TaskTableController {
	protected static final Log logger = LogFactory.getLog(TaskTableController.class);

	protected IDatabaseService databaseService;

	protected TaskTableService taskTableService;

	public TaskTableController() {

	}

	@RequestMapping("/chooseColumns")
	public ModelAndView chooseColumns(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String sourceDatabaseIds = request.getParameter("databaseIds");
		List<Long> databaseIds = StringTools.splitToLong(sourceDatabaseIds);
		if (databaseIds != null && !databaseIds.isEmpty()) {
			String tableName = request.getParameter("tableName");
			for (Long databaseId : databaseIds) {
				Database database = databaseService.getDatabaseById(databaseId);
				List<ColumnDefinition> columns = null;
				if (database != null) {
					columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
				} else {
					columns = DBUtils.getColumnDefinitions(tableName);
				}

				TaskTable taskTable = taskTableService.getTaskTable(RequestUtils.getLong(request, "taskTableId"));
				if (taskTable != null) {
					request.setAttribute("taskTable", taskTable);
				}

				if (columns != null && !columns.isEmpty()) {
					List<String> selected = new ArrayList<String>();
					List<ColumnDefinition> unselected = new ArrayList<ColumnDefinition>();
					if (taskTable != null && StringUtils.isNotEmpty(taskTable.getSyncColumns())) {
						List<String> cols = StringTools.split(taskTable.getSyncColumns());
						for (ColumnDefinition col : columns) {
							if (cols.contains(col.getColumnName()) || cols.contains(col.getColumnName().toLowerCase())
									|| cols.contains(col.getColumnName().toUpperCase())) {
								selected.add(col.getColumnName());
							} else {
								unselected.add(col);
							}
						}
						request.setAttribute("selected", selected);
						request.setAttribute("unselected", columns);
					} else {
						request.setAttribute("selected", selected);
						request.setAttribute("unselected", columns);
					}
					break;
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("taskTable.chooseColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/taskTable/chooseColumns", modelMap);
	}

	@RequestMapping("/chooseDatabases")
	public ModelAndView chooseDatabases(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TaskTable taskTable = taskTableService.getTaskTable(RequestUtils.getLong(request, "taskTableId"));
		if (taskTable != null) {
			request.setAttribute("taskTable", taskTable);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<Long> selected = new ArrayList<Long>();
			List<Database> unselected = new ArrayList<Database>();
			if (taskTable != null && StringUtils.isNotEmpty(taskTable.getDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(taskTable.getDatabaseIds());
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

		String x_view = ViewProperties.getString("taskTable.chooseDatabases");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/taskTable/chooseDatabases", modelMap);
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
					TaskTable taskTable = taskTableService.getTaskTable(Long.valueOf(x));
					if (taskTable != null && (StringUtils.equals(taskTable.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						taskTableService.deleteById(taskTable.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TaskTable taskTable = taskTableService.getTaskTable(Long.valueOf(id));
			if (taskTable != null && (StringUtils.equals(taskTable.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				taskTableService.deleteById(taskTable.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		TaskTable taskTable = taskTableService.getTaskTable(RequestUtils.getLong(request, "id"));
		if (taskTable != null) {
			request.setAttribute("taskTable", taskTable);

			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<Database> databases = config.getDatabases();
			if (databases != null && !databases.isEmpty()) {
				StringBuilder buffer = new StringBuilder();
				if (taskTable != null && StringUtils.isNotEmpty(taskTable.getDatabaseIds())) {
					List<Long> ids = StringTools.splitToLong(taskTable.getDatabaseIds());
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

		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("taskTable.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/taskTable/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TaskTable taskTable = taskTableService.getTaskTable(RequestUtils.getLong(request, "id"));
		if (taskTable != null && taskTable.getLocked() == 0) {
			String jobNo = null;
			String runDay = DateUtils.getNowYearMonthDayHHmmss();
			StringTokenizer token = new StringTokenizer(taskTable.getDatabaseIds(), ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					long databaseId = Long.parseLong(x);
					if (databaseId != 0) {
						jobNo = "task_table_" + taskTable.getId() + "_" + databaseId + "_" + runDay;
						TaskTableTask taskTableBean = new TaskTableTask(databaseId, taskTable.getId(), jobNo, params);
						taskTableBean.execute();
					}
				}
			}
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/executeRT")
	public byte[] executeRT(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TaskTable taskTable = taskTableService.getTaskTable(RequestUtils.getLong(request, "id"));
		if (taskTable != null && taskTable.getLocked() == 0) {
			taskTable.setStartTime(RequestUtils.getDate(request, "startTime"));
			taskTable.setEndTime(RequestUtils.getDate(request, "endTime"));
			taskTable.setFrequency(RequestUtils.getInt(request, "frequency"));
			String jobNo = null;
			String runDay = DateUtils.getNowYearMonthDayHHmmss();
			StringTokenizer token = new StringTokenizer(taskTable.getDatabaseIds(), ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					long databaseId = Long.parseLong(x);
					if (databaseId != 0) {
						jobNo = "task_table_" + taskTable.getId() + "_" + databaseId + "_" + runDay;
						TaskTableTask taskTableBean = new TaskTableTask(databaseId, taskTable.getId(), jobNo, params);
						taskTableBean.execute();
					}
				}
			}
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TaskTableQuery query = new TaskTableQuery();
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
		int total = taskTableService.getTaskTableCountByQueryCriteria(query);
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

			List<TaskTable> list = taskTableService.getTaskTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TaskTable taskTable : list) {
					JSONObject rowJSON = taskTable.toJsonObject();
					rowJSON.put("id", taskTable.getId());
					rowJSON.put("rowId", taskTable.getId());
					rowJSON.put("taskTableId", taskTable.getId());
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

		return new ModelAndView("/datamgr/taskTable/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("taskTable.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/taskTable/query", modelMap);
	}

	 
	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		TaskTable taskTable = new TaskTable();
		Tools.populate(taskTable, params);

		taskTable.setTitle(request.getParameter("title"));
		taskTable.setType(request.getParameter("type"));
		taskTable.setTableName(request.getParameter("tableName"));
		taskTable.setIdColumn(request.getParameter("idColumn"));
		taskTable.setNameColumn(request.getParameter("nameColumn"));
		taskTable.setNameExpression(request.getParameter("nameExpression"));
		taskTable.setValueColumn(request.getParameter("valueColumn"));
		taskTable.setValueExpression(request.getParameter("valueExpression"));
		taskTable.setTypeColumn(request.getParameter("typeColumn"));
		taskTable.setStartDateColumn(request.getParameter("startDateColumn"));
		taskTable.setEndDateColumn(request.getParameter("endDateColumn"));
		taskTable.setDatabaseIds(request.getParameter("databaseIds"));
		taskTable.setSyncColumns(request.getParameter("syncColumns"));
		taskTable.setStartTime(RequestUtils.getDate(request, "startTime"));
		taskTable.setEndTime(RequestUtils.getDate(request, "endTime"));
		taskTable.setFrequency(RequestUtils.getInt(request, "frequency"));
		taskTable.setExecuteDay(RequestUtils.getInt(request, "executeDay"));
		taskTable.setSortNo(RequestUtils.getInt(request, "sortNo"));
		taskTable.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		taskTable.setLocked(RequestUtils.getInt(request, "locked"));
		taskTable.setCreateBy(actorId);
		taskTable.setUpdateBy(actorId);

		taskTableService.save(taskTable);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody TaskTable saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		TaskTable taskTable = new TaskTable();
		try {
			Tools.populate(taskTable, model);
			taskTable.setTitle(ParamUtils.getString(model, "title"));
			taskTable.setType(ParamUtils.getString(model, "type"));
			taskTable.setTableName(ParamUtils.getString(model, "tableName"));
			taskTable.setIdColumn(ParamUtils.getString(model, "idColumn"));
			taskTable.setNameColumn(ParamUtils.getString(model, "nameColumn"));
			taskTable.setNameExpression(ParamUtils.getString(model, "nameExpression"));
			taskTable.setValueColumn(request.getParameter("valueColumn"));
			taskTable.setValueExpression(request.getParameter("valueExpression"));
			taskTable.setTypeColumn(ParamUtils.getString(model, "typeColumn"));
			taskTable.setStartDateColumn(ParamUtils.getString(model, "startDateColumn"));
			taskTable.setEndDateColumn(ParamUtils.getString(model, "endDateColumn"));
			taskTable.setDatabaseIds(request.getParameter("databaseIds"));
			taskTable.setSyncColumns(request.getParameter("syncColumns"));
			taskTable.setStartTime(ParamUtils.getDate(model, "startTime"));
			taskTable.setEndTime(ParamUtils.getDate(model, "endTime"));
			taskTable.setFrequency(ParamUtils.getInt(model, "frequency"));
			taskTable.setExecuteDay(RequestUtils.getInt(request, "executeDay"));
			taskTable.setSortNo(ParamUtils.getInt(model, "sortNo"));
			taskTable.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			taskTable.setLocked(ParamUtils.getInt(model, "locked"));
			taskTable.setCreateBy(actorId);
			taskTable.setUpdateBy(actorId);
			this.taskTableService.save(taskTable);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return taskTable;
	}

	@ResponseBody
	@RequestMapping("/saveTaskTable")
	public byte[] saveTaskTable(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TaskTable taskTable = new TaskTable();
		try {
			Tools.populate(taskTable, params);
			taskTable.setTitle(request.getParameter("title"));
			taskTable.setType(request.getParameter("type"));
			taskTable.setTableName(request.getParameter("tableName"));
			taskTable.setIdColumn(request.getParameter("idColumn"));
			taskTable.setNameColumn(request.getParameter("nameColumn"));
			taskTable.setNameExpression(request.getParameter("nameExpression"));
			taskTable.setValueColumn(request.getParameter("valueColumn"));
			taskTable.setValueExpression(request.getParameter("valueExpression"));
			taskTable.setTypeColumn(request.getParameter("typeColumn"));
			taskTable.setStartDateColumn(request.getParameter("startDateColumn"));
			taskTable.setEndDateColumn(request.getParameter("endDateColumn"));
			taskTable.setDatabaseIds(request.getParameter("databaseIds"));
			taskTable.setSyncColumns(request.getParameter("syncColumns"));
			taskTable.setStartTime(RequestUtils.getDate(request, "startTime"));
			taskTable.setEndTime(RequestUtils.getDate(request, "endTime"));
			taskTable.setFrequency(RequestUtils.getInt(request, "frequency"));
			taskTable.setExecuteDay(RequestUtils.getInt(request, "executeDay"));
			taskTable.setSortNo(RequestUtils.getInt(request, "sortNo"));
			taskTable.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			taskTable.setLocked(RequestUtils.getInt(request, "locked"));
			taskTable.setCreateBy(actorId);
			taskTable.setUpdateBy(actorId);
			this.taskTableService.save(taskTable);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.taskTableService")
	public void setTaskTableService(TaskTableService taskTableService) {
		this.taskTableService = taskTableService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TaskTable taskTable = taskTableService.getTaskTable(RequestUtils.getLong(request, "id"));

		Tools.populate(taskTable, params);

		taskTable.setTitle(request.getParameter("title"));
		taskTable.setType(request.getParameter("type"));
		taskTable.setTableName(request.getParameter("tableName"));
		taskTable.setIdColumn(request.getParameter("idColumn"));
		taskTable.setNameColumn(request.getParameter("nameColumn"));
		taskTable.setNameExpression(request.getParameter("nameExpression"));
		taskTable.setValueColumn(request.getParameter("valueColumn"));
		taskTable.setValueExpression(request.getParameter("valueExpression"));
		taskTable.setTypeColumn(request.getParameter("typeColumn"));
		taskTable.setStartDateColumn(request.getParameter("startDateColumn"));
		taskTable.setEndDateColumn(request.getParameter("endDateColumn"));
		taskTable.setDatabaseIds(request.getParameter("databaseIds"));
		taskTable.setSyncColumns(request.getParameter("syncColumns"));
		taskTable.setStartTime(RequestUtils.getDate(request, "startTime"));
		taskTable.setEndTime(RequestUtils.getDate(request, "endTime"));
		taskTable.setFrequency(RequestUtils.getInt(request, "frequency"));
		taskTable.setExecuteDay(RequestUtils.getInt(request, "executeDay"));
		taskTable.setSortNo(RequestUtils.getInt(request, "sortNo"));
		taskTable.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		taskTable.setLocked(RequestUtils.getInt(request, "locked"));
		taskTable.setUpdateBy(user.getActorId());

		taskTableService.save(taskTable);

		return this.list(request, modelMap);
	}

}
