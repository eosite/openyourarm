package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.domain.TableExecution;
import com.glaf.datamgr.domain.TableSync;
import com.glaf.datamgr.query.TableExecutionQuery;
import com.glaf.datamgr.query.TableSyncQuery;
import com.glaf.datamgr.service.ExecutionLogService;
import com.glaf.datamgr.service.TableExecutionService;
import com.glaf.datamgr.service.TableSyncService;
import com.glaf.datamgr.task.TableSyncTask;
import com.glaf.datamgr.util.ExceptionUtils;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/tableSync")
@RequestMapping("/sys/tableSync")
public class TableSyncController {
	protected static final Log logger = LogFactory.getLog(TableSyncController.class);

	protected IDatabaseService databaseService;

	protected TableSyncService tableSyncService;

	protected TableExecutionService tableExecutionService;

	protected ExecutionLogService executionLogService;

	public TableSyncController() {

	}

	@RequestMapping("/chooseColumns")
	public ModelAndView chooseColumns(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		long databaseId = RequestUtils.getLong(request, "databaseId");
		String tableName = request.getParameter("tableName");
		Database database = databaseService.getDatabaseById(databaseId);
		List<ColumnDefinition> columns = null;
		if (database != null) {
			columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
		} else {
			columns = DBUtils.getColumnDefinitions(tableName);
		}

		TableSync tableSync = tableSyncService.getTableSync(RequestUtils.getLong(request, "tableSyncId"));
		if (tableSync != null) {
			request.setAttribute("tableSync", tableSync);
		}

		if (columns != null && !columns.isEmpty()) {
			List<String> selected = new ArrayList<String>();
			List<ColumnDefinition> unselected = new ArrayList<ColumnDefinition>();
			if (tableSync != null && StringUtils.isNotEmpty(tableSync.getSyncColumns())) {
				List<String> cols = StringTools.split(tableSync.getSyncColumns());
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
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableSync.chooseColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableSync/chooseColumns", modelMap);
	}

	@RequestMapping("/chooseDatabases")
	public ModelAndView chooseDatabases(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableSync tableSync = tableSyncService.getTableSync(RequestUtils.getLong(request, "tableSyncId"));
		if (tableSync != null) {
			request.setAttribute("tableSync", tableSync);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<Long> selected = new ArrayList<Long>();
			List<Database> unselected = new ArrayList<Database>();
			if (tableSync != null && StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(tableSync.getTargetDatabaseIds());
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

		String x_view = ViewProperties.getString("tableSync.chooseDatabases");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableSync/chooseDatabases", modelMap);
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
					TableSync tableSync = tableSyncService.getTableSync(Long.valueOf(x));
					if (tableSync != null && (StringUtils.equals(tableSync.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						tableSyncService.deleteById(tableSync.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TableSync tableSync = tableSyncService.getTableSync(Long.valueOf(id));
			if (tableSync != null && (StringUtils.equals(tableSync.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				tableSyncService.deleteById(tableSync.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/dropAllTable")
	public byte[] dropAllTable(HttpServletRequest request, ModelMap modelMap) {
		boolean result = false;
		TableSyncQuery query = new TableSyncQuery();
		query.locked(0);
		List<TableSync> rows = tableSyncService.list(query);
		if (rows != null && !rows.isEmpty()) {
			TableFactory.clear();
			boolean error = false;
			for (TableSync tableSync : rows) {
				if (tableSync != null && StringUtils.isNotEmpty(tableSync.getTargetTableName())) {
					List<Long> databaseIds = StringTools.splitToLong(tableSync.getTargetDatabaseIds());
					for (Long databaseId : databaseIds) {
						try {
							Database targetDatabase = databaseService.getDatabaseById(databaseId);
							if (targetDatabase != null) {
								if (StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "etl_")
										|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "sync_")
										|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "tmp")
										|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "useradd_")
										|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(),
												"tree_table_")) {
									if (DBUtils.tableExists(targetDatabase.getName(), tableSync.getTargetTableName())) {
										//executionLogService.deleteTodayExecutionLogs("table_sync",
										//		String.valueOf(tableSync.getId()));
										String ddlStatements = " drop table " + tableSync.getTargetTableName();
										logger.warn(ddlStatements);
										DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
									}
								}
							}
						} catch (Exception ex) {
							error = true;
							logger.error(ex);
						}
					}
				}
			}
			if (!error) {
				TableFactory.clear();
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(result);
	}

	@ResponseBody
	@RequestMapping("/dropTable")
	public byte[] dropTable(HttpServletRequest request, ModelMap modelMap) {
		long tableSyncId = RequestUtils.getLong(request, "tableSyncId");
		TableSync tableSync = tableSyncService.getTableSync(tableSyncId);
		if (tableSync != null && StringUtils.isNotEmpty(tableSync.getTargetTableName())) {
			TableFactory.clear();
			boolean error = false;
			List<Long> databaseIds = StringTools.splitToLong(tableSync.getTargetDatabaseIds());
			for (Long databaseId : databaseIds) {
				try {
					Database targetDatabase = databaseService.getDatabaseById(databaseId);
					if (targetDatabase != null) {
						if (StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "etl_")
								|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "sync_")
								|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "tmp")
								|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "useradd_")
								|| StringUtils.startsWithIgnoreCase(tableSync.getTargetTableName(), "tree_table_")) {
							if (DBUtils.tableExists(targetDatabase.getName(), tableSync.getTargetTableName())) {
								//executionLogService.deleteTodayExecutionLogs("table_sync",
								//		String.valueOf(tableSync.getId()));
								String ddlStatements = " drop table " + tableSync.getTargetTableName();
								logger.warn(ddlStatements);
								DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
							}
						}
					}
				} catch (Exception ex) {
					error = true;
					logger.error(ex);
				}
			}
			if (!error) {
				TableFactory.clear();
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableSync tableSync = tableSyncService.getTableSync(RequestUtils.getLong(request, "id"));
		if (tableSync != null) {
			request.setAttribute("tableSync", tableSync);
		}

		TableExecutionQuery query = new TableExecutionQuery();
		query.locked(0);
		List<TableExecution> executions = tableExecutionService.list(query);
		if (executions != null && !executions.isEmpty()) {
			List<String> selected1Ids = new ArrayList<String>();
			List<String> selected2Ids = new ArrayList<String>();
			StringBuilder name1 = new StringBuilder();
			StringBuilder name2 = new StringBuilder();
			List<TableExecution> srcSelectedExecutions = new ArrayList<TableExecution>();
			List<TableExecution> destSelectedExecutions = new ArrayList<TableExecution>();
			for (TableExecution e : executions) {
				if (tableSync.getSourceTableExecutionIds() != null
						&& StringUtils.contains(tableSync.getSourceTableExecutionIds(), e.getId())) {
					srcSelectedExecutions.add(e);
					selected1Ids.add(e.getId());
					name1.append(e.getTitle()).append("  ");
				}
				if (tableSync.getTargetTableExecutionIds() != null
						&& StringUtils.contains(tableSync.getTargetTableExecutionIds(), e.getId())) {
					destSelectedExecutions.add(e);
					selected2Ids.add(e.getId());
					name2.append(e.getTitle()).append("  ");
				}
			}
			request.setAttribute("selected1Ids", selected1Ids);
			request.setAttribute("selected2Ids", selected2Ids);
			request.setAttribute("selected1Name", name1.toString());
			request.setAttribute("selected2Name", name2.toString());

			request.setAttribute("srcSelectedExecutions", srcSelectedExecutions);
			request.setAttribute("destSelectedExecutions", destSelectedExecutions);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			if (tableSync != null && StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(tableSync.getTargetDatabaseIds());
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

		String x_view = ViewProperties.getString("tableSync.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableSync/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request, ModelMap modelMap) {
		long tableSyncId = RequestUtils.getLong(request, "tableSyncId");
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		TableSync tableSync = tableSyncService.getTableSync(tableSyncId);
		if (tableSync != null && StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
			if (StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
				tableSync.setSyncTime(new Date());
				StringTokenizer token = new StringTokenizer(tableSync.getTargetDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						jobNo = "table_sync_" + tableSync.getId() + "_" + tableSync.getSourceDatabaseId() + "_" + x
								+ "_" + runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								TableSyncTask task = new TableSyncTask(tableSync.getSourceDatabaseId(),
										database.getId(), tableSync.getId(), jobNo);
								if (task.execute()) {
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行成功。\n");
								} else {
									errorCount++;
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行失败。\n");
									StringBuffer sb = ExceptionUtils.getMsgBuffer("table_sync_" + tableSync.getId());
									buffer.append(sb != null ? sb.toString() : "");
								}
							}
						} catch (Exception ex) {
							errorCount++;
						} finally {
							ExceptionUtils.clear("table_sync_" + tableSync.getId());
						}
					}
				}

				if (errorCount == 0) {
					tableSync.setSyncStatus(1);
				} else {
					tableSync.setSyncStatus(-1);
				}
				tableSyncService.updateTableSyncStatus(tableSync);
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@ResponseBody
	@RequestMapping("/executeAll")
	public byte[] executeAll(HttpServletRequest request, ModelMap modelMap) {
		int errorCount = 0;
		int perErrorCount = 0;
		String jobNo = null;
		Database database = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		TableSyncQuery query = new TableSyncQuery();
		query.locked(0);
		query.deleteFlag(0);
		List<TableSync> rows = tableSyncService.list(query);
		if (rows != null && !rows.isEmpty()) {
			for (TableSync tableSync : rows) {
				if (tableSync != null && StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
					if (StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
						perErrorCount = 0;
						tableSync.setSyncTime(new Date());
						buffer.append("\n").append(tableSync.getTitle()).append("执行结果：\n");
						StringTokenizer token = new StringTokenizer(tableSync.getTargetDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								jobNo = "table_sync_" + tableSync.getId() + "_" + tableSync.getSourceDatabaseId() + "_"
										+ x + "_" + runDay;
								try {
									database = databaseService.getDatabaseById(Long.parseLong(x));
									if (database != null) {
										TableSyncTask task = new TableSyncTask(tableSync.getSourceDatabaseId(),
												database.getId(), tableSync.getId(), jobNo);
										if (task.execute()) {
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行成功。\n");
										} else {
											errorCount++;
											perErrorCount++;
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行失败。\n");
											StringBuffer sb = ExceptionUtils
													.getMsgBuffer("table_sync_" + tableSync.getId());
											buffer.append(sb != null ? sb.toString() : "");
										}
									}
								} catch (Exception ex) {
									errorCount++;
									perErrorCount++;
								} finally {
									ExceptionUtils.clear("table_sync_" + tableSync.getId());
								}
							}
						}
						if (perErrorCount == 0) {
							tableSync.setSyncStatus(1);
						} else {
							tableSync.setSyncStatus(-1);
						}
						tableSyncService.updateTableSyncStatus(tableSync);
					}
				}
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSyncQuery query = new TableSyncQuery();
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
		int total = tableSyncService.getTableSyncCountByQueryCriteria(query);
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

			List<TableSync> list = tableSyncService.getTableSyncsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableSync tableSync : list) {
					JSONObject rowJSON = tableSync.toJsonObject();
					rowJSON.put("id", tableSync.getId());
					rowJSON.put("rowId", tableSync.getId());
					rowJSON.put("tableSyncId", tableSync.getId());
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

		return new ModelAndView("/datamgr/tableSync/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("tableSync.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/tableSync/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TableSync tableSync = new TableSync();
		Tools.populate(tableSync, params);

		tableSync.setName(request.getParameter("name"));
		tableSync.setTitle(request.getParameter("title"));
		tableSync.setType(request.getParameter("type"));
		tableSync.setSourceDatabaseId(RequestUtils.getLong(request, "sourceDatabaseId"));
		tableSync.setSourceTableName(request.getParameter("sourceTableName"));
		tableSync.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
		tableSync.setPrimaryKey(request.getParameter("primaryKey"));
		tableSync.setSyncColumns(request.getParameter("syncColumns"));
		tableSync.setSqlCriteria(request.getParameter("sqlCriteria"));
		tableSync.setTargetTableName(request.getParameter("targetTableName"));
		tableSync.setTargetDatabaseIds(request.getParameter("targetDatabaseIds"));
		tableSync.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
		tableSync.setScheduleFlag(request.getParameter("scheduleFlag"));
		tableSync.setCreateTableFlag(request.getParameter("createTableFlag"));
		tableSync.setDeleteFetch(request.getParameter("deleteFetch"));
		tableSync.setInsertOnly(request.getParameter("insertOnly"));
		tableSync.setLocked(RequestUtils.getInt(request, "locked"));
		tableSync.setCreateBy(actorId);
		tableSyncService.save(tableSync);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody TableSync saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		TableSync tableSync = new TableSync();
		try {
			Tools.populate(tableSync, model);
			tableSync.setName(ParamUtils.getString(model, "name"));
			tableSync.setTitle(ParamUtils.getString(model, "title"));
			tableSync.setType(ParamUtils.getString(model, "type"));
			tableSync.setSourceDatabaseId(ParamUtils.getLong(model, "sourceDatabaseId"));
			tableSync.setSourceTableName(ParamUtils.getString(model, "sourceTableName"));
			tableSync.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
			tableSync.setPrimaryKey(request.getParameter("primaryKey"));
			tableSync.setSyncColumns(request.getParameter("syncColumns"));
			tableSync.setSqlCriteria(request.getParameter("sqlCriteria"));
			tableSync.setTargetTableName(ParamUtils.getString(model, "targetTableName"));
			tableSync.setTargetDatabaseIds(ParamUtils.getString(model, "targetDatabaseIds"));
			tableSync.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
			tableSync.setScheduleFlag(ParamUtils.getString(model, "scheduleFlag"));
			tableSync.setCreateTableFlag(ParamUtils.getString(model, "createTableFlag"));
			tableSync.setDeleteFetch(ParamUtils.getString(model, "deleteFetch"));
			tableSync.setInsertOnly(ParamUtils.getString(model, "insertOnly"));
			tableSync.setSortNo(RequestUtils.getInt(request, "sortNo"));
			tableSync.setLocked(ParamUtils.getInt(model, "locked"));
			tableSync.setCreateBy(actorId);
			this.tableSyncService.save(tableSync);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return tableSync;
	}

	@ResponseBody
	@RequestMapping("/saveTableSync")
	public byte[] saveTableSync(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSync tableSync = new TableSync();
		try {
			Tools.populate(tableSync, params);
			tableSync.setName(request.getParameter("name"));
			tableSync.setTitle(request.getParameter("title"));
			tableSync.setType(request.getParameter("type"));
			tableSync.setSourceDatabaseId(RequestUtils.getLong(request, "sourceDatabaseId"));
			tableSync.setSourceTableName(request.getParameter("sourceTableName"));
			tableSync.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
			tableSync.setPrimaryKey(request.getParameter("primaryKey"));
			tableSync.setSyncColumns(request.getParameter("syncColumns"));
			tableSync.setSqlCriteria(request.getParameter("sqlCriteria"));
			tableSync.setTargetTableName(request.getParameter("targetTableName"));
			tableSync.setTargetDatabaseIds(request.getParameter("targetDatabaseIds"));
			tableSync.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
			tableSync.setScheduleFlag(request.getParameter("scheduleFlag"));
			tableSync.setCreateTableFlag(request.getParameter("createTableFlag"));
			tableSync.setDeleteFetch(request.getParameter("deleteFetch"));
			tableSync.setInsertOnly(request.getParameter("insertOnly"));
			tableSync.setSortNo(RequestUtils.getInt(request, "sortNo"));
			tableSync.setLocked(RequestUtils.getInt(request, "locked"));
			tableSync.setCreateBy(actorId);
			this.tableSyncService.save(tableSync);

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

	@javax.annotation.Resource
	public void setExecutionLogService(ExecutionLogService executionLogService) {
		this.executionLogService = executionLogService;
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.tableExecutionService")
	public void setTableExecutionService(TableExecutionService tableExecutionService) {
		this.tableExecutionService = tableExecutionService;
	}

	@javax.annotation.Resource
	public void setTableSyncService(TableSyncService tableSyncService) {
		this.tableSyncService = tableSyncService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TableSync tableSync = tableSyncService.getTableSync(RequestUtils.getLong(request, "id"));

		Tools.populate(tableSync, params);

		tableSync.setName(request.getParameter("name"));
		tableSync.setTitle(request.getParameter("title"));
		tableSync.setType(request.getParameter("type"));
		tableSync.setSourceDatabaseId(RequestUtils.getLong(request, "sourceDatabaseId"));
		tableSync.setSourceTableName(request.getParameter("sourceTableName"));
		tableSync.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
		tableSync.setPrimaryKey(request.getParameter("primaryKey"));
		tableSync.setSyncColumns(request.getParameter("syncColumns"));
		tableSync.setSqlCriteria(request.getParameter("sqlCriteria"));
		tableSync.setTargetTableName(request.getParameter("targetTableName"));
		tableSync.setTargetDatabaseIds(request.getParameter("targetDatabaseIds"));
		tableSync.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
		tableSync.setScheduleFlag(request.getParameter("scheduleFlag"));
		tableSync.setCreateTableFlag(request.getParameter("createTableFlag"));
		tableSync.setDeleteFetch(request.getParameter("deleteFetch"));
		tableSync.setInsertOnly(request.getParameter("insertOnly"));
		tableSync.setSortNo(RequestUtils.getInt(request, "sortNo"));
		tableSync.setLocked(RequestUtils.getInt(request, "locked"));
		tableSync.setUpdateBy(user.getActorId());
		tableSyncService.save(tableSync);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		TableSync tableSync = tableSyncService.getTableSync(RequestUtils.getLong(request, "id"));
		request.setAttribute("tableSync", tableSync);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("tableSync.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/tableSync/view");
	}

}
