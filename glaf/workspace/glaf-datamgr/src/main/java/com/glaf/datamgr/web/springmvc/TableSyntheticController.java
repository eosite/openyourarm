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

import com.glaf.datamgr.domain.TableSynthetic;
import com.glaf.datamgr.query.TableSyntheticQuery;
import com.glaf.datamgr.service.ExecutionLogService;
import com.glaf.datamgr.service.TableSyntheticService;
import com.glaf.datamgr.task.TableSyntheticTask;
import com.glaf.datamgr.util.ExceptionUtils;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/tableSynthetic")
@RequestMapping("/sys/tableSynthetic")
public class TableSyntheticController {
	protected static final Log logger = LogFactory.getLog(TableSyntheticController.class);

	protected IDatabaseService databaseService;

	protected ExecutionLogService executionLogService;

	protected TableSyntheticService tableSyntheticService;

	public TableSyntheticController() {

	}

	@RequestMapping("/chooseColumns")
	public ModelAndView chooseColumns(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String sourceDatabaseIds = request.getParameter("sourceDatabaseIds");
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

				TableSynthetic tableSynthetic = tableSyntheticService
						.getTableSynthetic(RequestUtils.getLong(request, "tableSyntheticId"));
				if (tableSynthetic != null) {
					request.setAttribute("tableSynthetic", tableSynthetic);
				}

				if (columns != null && !columns.isEmpty()) {
					List<String> selected = new ArrayList<String>();
					List<ColumnDefinition> unselected = new ArrayList<ColumnDefinition>();
					if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSyncColumns())) {
						List<String> cols = StringTools.split(tableSynthetic.getSyncColumns());
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

		String x_view = ViewProperties.getString("tableSynthetic.chooseColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableSynthetic/chooseColumns", modelMap);
	}

	@RequestMapping("/chooseDatabases")
	public ModelAndView chooseDatabases(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableSynthetic tableSynthetic = tableSyntheticService
				.getTableSynthetic(RequestUtils.getLong(request, "tableSyntheticId"));
		if (tableSynthetic != null) {
			request.setAttribute("tableSynthetic", tableSynthetic);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<Long> selected = new ArrayList<Long>();
			List<Database> unselected = new ArrayList<Database>();
			if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(tableSynthetic.getSourceDatabaseIds());
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

		String x_view = ViewProperties.getString("tableSynthetic.chooseDatabases");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableSynthetic/chooseDatabases", modelMap);
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
					TableSynthetic tableSynthetic = tableSyntheticService.getTableSynthetic(Long.valueOf(x));
					if (tableSynthetic != null
							&& (StringUtils.equals(tableSynthetic.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						tableSyntheticService.deleteById(tableSynthetic.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TableSynthetic tableSynthetic = tableSyntheticService.getTableSynthetic(Long.valueOf(id));
			if (tableSynthetic != null && (StringUtils.equals(tableSynthetic.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				tableSyntheticService.deleteById(tableSynthetic.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/dropAllTable")
	public byte[] dropAllTable(HttpServletRequest request, ModelMap modelMap) {
		boolean result = false;
		TableSyntheticQuery query = new TableSyntheticQuery();
		query.locked(0);
		List<TableSynthetic> rows = tableSyntheticService.list(query);
		if (rows != null && !rows.isEmpty()) {
			TableFactory.clear();
			for (TableSynthetic tableSynthetic : rows) {
				if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getTargetTableName())) {
					try {
						Database targetDatabase = databaseService.getDatabaseById(tableSynthetic.getTargetDatabaseId());
						if (targetDatabase != null) {
							if (StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "etl_")
									|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "sync_")
									|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "tmp")
									|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "useradd_")
									|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(),
											"tree_table_")) {
								if (DBUtils.tableExists(targetDatabase.getName(),
										tableSynthetic.getTargetTableName())) {
									executionLogService.deleteTodayExecutionLogs("table_synthetic",
											String.valueOf(tableSynthetic.getId()));
									String ddlStatements = " drop table " + tableSynthetic.getTargetTableName();
									logger.warn(ddlStatements);
									DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
								}
								result = true;
							}
						}
					} catch (Exception ex) {
						result = false;
						logger.error(ex);
					}
				}
			}
			TableFactory.clear();
		}
		return ResponseUtils.responseResult(result);
	}

	@ResponseBody
	@RequestMapping("/dropTable")
	public byte[] dropTable(HttpServletRequest request, ModelMap modelMap) {
		long tableSyntheticId = RequestUtils.getLong(request, "syntheticId");
		TableSynthetic tableSynthetic = tableSyntheticService.getTableSynthetic(tableSyntheticId);
		if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getTargetTableName())) {
			try {
				Database targetDatabase = databaseService.getDatabaseById(tableSynthetic.getTargetDatabaseId());
				if (targetDatabase != null) {
					if (StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "etl_")
							|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "sync_")
							|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "tmp")
							|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "useradd_")
							|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "tree_table_")) {
						if (DBUtils.tableExists(targetDatabase.getName(), tableSynthetic.getTargetTableName())) {
							executionLogService.deleteTodayExecutionLogs("table_synthetic",
									String.valueOf(tableSynthetic.getId()));
							String ddlStatements = " drop table " + tableSynthetic.getTargetTableName();
							logger.warn(ddlStatements);
							DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
							TableFactory.clear();
						}
						return ResponseUtils.responseResult(true);
					}
				}
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableSynthetic tableSynthetic = tableSyntheticService.getTableSynthetic(RequestUtils.getLong(request, "id"));
		if (tableSynthetic != null) {
			request.setAttribute("tableSynthetic", tableSynthetic);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(tableSynthetic.getSourceDatabaseIds());
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

		String x_view = ViewProperties.getString("tableSynthetic.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableSynthetic/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request, ModelMap modelMap) {
		long tableSyntheticId = RequestUtils.getLong(request, "tableSyntheticId");
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		TableSynthetic tableSynthetic = tableSyntheticService.getTableSynthetic(tableSyntheticId);
		if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
			if (StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
				tableSynthetic.setSyncTime(new Date());
				StringTokenizer token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						jobNo = "table_synthetic_" + tableSynthetic.getId() + "_" + x + "_"
								+ tableSynthetic.getTargetDatabaseId() + "_" + runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								TableSyntheticTask task = new TableSyntheticTask(database.getId(),
										tableSynthetic.getTargetDatabaseId(), tableSynthetic.getId(), jobNo);
								if (task.execute()) {
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行成功。\n");
								} else {
									errorCount++;
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行失败。\n");
									StringBuffer sb = ExceptionUtils
											.getMsgBuffer("table_synthetic_" + tableSynthetic.getId());
									buffer.append(sb != null ? sb.toString() : "");
								}
							}
						} catch (Exception ex) {
							errorCount++;
						} finally {
							ExceptionUtils.clear("table_synthetic_" + tableSynthetic.getId());
						}
					}
				}
				if (errorCount == 0) {
					tableSynthetic.setSyncStatus(1);
				} else {
					tableSynthetic.setSyncStatus(-1);
				}
				tableSyntheticService.updateTableSyntheticStatus(tableSynthetic);
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
		TableSyntheticQuery query = new TableSyntheticQuery();
		query.locked(0);
		query.deleteFlag(0);
		List<TableSynthetic> rows = tableSyntheticService.list(query);
		if (rows != null && !rows.isEmpty()) {
			for (TableSynthetic tableSynthetic : rows) {
				if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
					if (StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
						perErrorCount = 0;
						tableSynthetic.setSyncTime(new Date());
						buffer.append("\n").append(tableSynthetic.getTitle()).append("执行结果：\n");
						StringTokenizer token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								jobNo = "table_synthetic_" + tableSynthetic.getId() + "_" + x + "_"
										+ tableSynthetic.getTargetDatabaseId() + "_" + runDay;
								try {
									database = databaseService.getDatabaseById(Long.parseLong(x));
									if (database != null) {
										TableSyntheticTask task = new TableSyntheticTask(database.getId(),
												tableSynthetic.getTargetDatabaseId(), tableSynthetic.getId(), jobNo);
										if (task.execute()) {
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行成功。\n");
										} else {
											errorCount++;
											perErrorCount++;
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行失败。\n");
											StringBuffer sb = ExceptionUtils
													.getMsgBuffer("table_synthetic_" + tableSynthetic.getId());
											buffer.append(sb != null ? sb.toString() : "");
										}
									}
								} catch (Exception ex) {
									errorCount++;
									perErrorCount++;
								} finally {
									ExceptionUtils.clear("table_synthetic_" + tableSynthetic.getId());
								}
							}
						}
						if (perErrorCount == 0) {
							tableSynthetic.setSyncStatus(1);
						} else {
							tableSynthetic.setSyncStatus(-1);
						}
						tableSyntheticService.updateTableSyntheticStatus(tableSynthetic);
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
		TableSyntheticQuery query = new TableSyntheticQuery();
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
		int total = tableSyntheticService.getTableSyntheticCountByQueryCriteria(query);
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

			List<TableSynthetic> list = tableSyntheticService.getTableSyntheticsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableSynthetic tableSynthetic : list) {
					JSONObject rowJSON = tableSynthetic.toJsonObject();
					rowJSON.put("id", tableSynthetic.getId());
					rowJSON.put("rowId", tableSynthetic.getId());
					rowJSON.put("tableSyntheticId", tableSynthetic.getId());
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

		return new ModelAndView("/datamgr/tableSynthetic/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("tableSynthetic.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/tableSynthetic/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		TableSynthetic tableSynthetic = new TableSynthetic();
		Tools.populate(tableSynthetic, params);

		tableSynthetic.setName(request.getParameter("name"));
		tableSynthetic.setTitle(request.getParameter("title"));
		tableSynthetic.setType(request.getParameter("type"));
		tableSynthetic.setSourceDatabaseIds(RequestUtils.getString(request, "sourceDatabaseIds"));
		tableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
		tableSynthetic.setPrimaryKey(request.getParameter("primaryKey"));
		tableSynthetic.setSyncColumns(request.getParameter("syncColumns"));
		tableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
		tableSynthetic.setTargetTableName(request.getParameter("targetTableName"));
		tableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
		tableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
		tableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
		tableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
		tableSynthetic.setInsertOnly(request.getParameter("insertOnly"));
		tableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
		tableSynthetic.setCreateBy(actorId);
		tableSyntheticService.save(tableSynthetic);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody TableSynthetic saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		TableSynthetic tableSynthetic = new TableSynthetic();
		try {
			Tools.populate(tableSynthetic, model);
			tableSynthetic.setName(ParamUtils.getString(model, "name"));
			tableSynthetic.setTitle(ParamUtils.getString(model, "title"));
			tableSynthetic.setType(ParamUtils.getString(model, "type"));
			tableSynthetic.setSourceDatabaseIds(ParamUtils.getString(model, "sourceDatabaseIds"));
			tableSynthetic.setSourceTableName(ParamUtils.getString(model, "sourceTableName"));
			tableSynthetic.setPrimaryKey(request.getParameter("primaryKey"));
			tableSynthetic.setSyncColumns(request.getParameter("syncColumns"));
			tableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
			tableSynthetic.setTargetTableName(ParamUtils.getString(model, "targetTableName"));
			tableSynthetic.setTargetDatabaseId(ParamUtils.getLong(model, "targetDatabaseId"));
			tableSynthetic.setScheduleFlag(ParamUtils.getString(model, "scheduleFlag"));
			tableSynthetic.setCreateTableFlag(ParamUtils.getString(model, "createTableFlag"));
			tableSynthetic.setDeleteFetch(ParamUtils.getString(model, "deleteFetch"));
			tableSynthetic.setInsertOnly(ParamUtils.getString(model, "insertOnly"));
			tableSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
			tableSynthetic.setLocked(ParamUtils.getInt(model, "locked"));
			tableSynthetic.setCreateBy(actorId);
			this.tableSyntheticService.save(tableSynthetic);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return tableSynthetic;
	}

	@ResponseBody
	@RequestMapping("/saveTableSynthetic")
	public byte[] saveTableSynthetic(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSynthetic tableSynthetic = new TableSynthetic();
		try {
			Tools.populate(tableSynthetic, params);
			tableSynthetic.setName(request.getParameter("name"));
			tableSynthetic.setTitle(request.getParameter("title"));
			tableSynthetic.setType(request.getParameter("type"));
			tableSynthetic.setSourceDatabaseIds(RequestUtils.getString(request, "sourceDatabaseIds"));
			tableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
			tableSynthetic.setPrimaryKey(request.getParameter("primaryKey"));
			tableSynthetic.setSyncColumns(request.getParameter("syncColumns"));
			tableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
			tableSynthetic.setTargetTableName(request.getParameter("targetTableName"));
			tableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			tableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
			tableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
			tableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
			tableSynthetic.setInsertOnly(request.getParameter("insertOnly"));
			tableSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
			tableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
			tableSynthetic.setCreateBy(actorId);
			this.tableSyntheticService.save(tableSynthetic);

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

	@javax.annotation.Resource
	public void setTableSyntheticService(TableSyntheticService tableSyntheticService) {
		this.tableSyntheticService = tableSyntheticService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		TableSynthetic tableSynthetic = tableSyntheticService.getTableSynthetic(RequestUtils.getLong(request, "id"));

		Tools.populate(tableSynthetic, params);

		tableSynthetic.setName(request.getParameter("name"));
		tableSynthetic.setTitle(request.getParameter("title"));
		tableSynthetic.setType(request.getParameter("type"));
		tableSynthetic.setSourceDatabaseIds(RequestUtils.getString(request, "sourceDatabaseIds"));
		tableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
		tableSynthetic.setPrimaryKey(request.getParameter("primaryKey"));
		tableSynthetic.setSyncColumns(request.getParameter("syncColumns"));
		tableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
		tableSynthetic.setTargetTableName(request.getParameter("targetTableName"));
		tableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
		tableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
		tableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
		tableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
		tableSynthetic.setInsertOnly(request.getParameter("insertOnly"));
		tableSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
		tableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
		tableSynthetic.setUpdateBy(user.getActorId());
		tableSyntheticService.save(tableSynthetic);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		TableSynthetic tableSynthetic = tableSyntheticService.getTableSynthetic(RequestUtils.getLong(request, "id"));
		request.setAttribute("tableSynthetic", tableSynthetic);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("tableSynthetic.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/tableSynthetic/view");
	}

}
