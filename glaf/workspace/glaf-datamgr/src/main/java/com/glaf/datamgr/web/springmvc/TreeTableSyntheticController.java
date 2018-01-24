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
import com.glaf.core.domain.Database;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.DictoryDefinitionService;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.bean.TreeTableSyntheticMetaBean;
import com.glaf.datamgr.domain.TableExecution;
import com.glaf.datamgr.domain.TreeTableSynthetic;
import com.glaf.datamgr.domain.TreeTableSyntheticRule;
import com.glaf.datamgr.query.TableExecutionQuery;
import com.glaf.datamgr.query.TreeTableSyntheticQuery;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.ExecutionLogService;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.TableExecutionService;
import com.glaf.datamgr.service.TreeTableSyntheticService;
import com.glaf.datamgr.task.TreeTableSyntheticTask;
import com.glaf.datamgr.util.ExceptionUtils;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/treeTableSynthetic")
@RequestMapping("/sys/treeTableSynthetic")
public class TreeTableSyntheticController {
	protected static final Log logger = LogFactory.getLog(TreeTableSyntheticController.class);

	protected DataSetService dataSetService;

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	protected DictoryDefinitionService dictoryDefinitionService;

	protected ExecutionLogService executionLogService;

	protected SqlDefinitionService sqlDefinitionService;

	protected TableExecutionService tableExecutionService;

	protected TreeTableSyntheticService treeTableSyntheticService;

	public TreeTableSyntheticController() {

	}

	@RequestMapping("/chooseDatabases")
	public ModelAndView chooseDatabases(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TreeTableSynthetic model = treeTableSyntheticService
				.getTreeTableSynthetic(RequestUtils.getLong(request, "treeTableSyntheticId"));
		if (model != null) {
			request.setAttribute("treeTableSynthetic", model);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<Long> selected = new ArrayList<Long>();
			List<Database> unselected = new ArrayList<Database>();
			if (model != null && StringUtils.isNotEmpty(model.getSourceDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(model.getSourceDatabaseIds());
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

		String x_view = ViewProperties.getString("treeTableSynthetic.chooseDatabases");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/treeTableSynthetic/chooseDatabases", modelMap);
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
					TreeTableSynthetic treeTableSynthetic = treeTableSyntheticService
							.getTreeTableSynthetic(Long.valueOf(x));
					if (treeTableSynthetic != null
							&& (StringUtils.equals(treeTableSynthetic.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						treeTableSynthetic.setDeleteFlag(1);
						treeTableSyntheticService.save(treeTableSynthetic);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TreeTableSynthetic treeTableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(Long.valueOf(id));
			if (treeTableSynthetic != null
					&& (StringUtils.equals(treeTableSynthetic.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				treeTableSynthetic.setDeleteFlag(1);
				treeTableSyntheticService.save(treeTableSynthetic);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/dropAllTable")
	public byte[] dropAllTable(HttpServletRequest request, ModelMap modelMap) {
		boolean result = false;
		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
		query.locked(0);
		List<TreeTableSynthetic> rows = treeTableSyntheticService.list(query);
		if (rows != null && !rows.isEmpty()) {
			TableFactory.clear();
			long targetDatabaseId = 0;
			StringTokenizer token = null;
			for (TreeTableSynthetic tableSynthetic : rows) {
				if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getTargetTableName())) {
					token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
					while (token.hasMoreTokens()) {
						String x = token.nextToken();
						if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
							targetDatabaseId = Long.parseLong(x);
							if (tableSynthetic.getTargetDatabaseId() != null
									&& tableSynthetic.getTargetDatabaseId() > 0) {
								targetDatabaseId = tableSynthetic.getTargetDatabaseId();
							}
							try {
								Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
								if (targetDatabase != null) {
									if (StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "etl_")
											|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(),
													"tree_table_")) {
										if (DBUtils.tableExists(targetDatabase.getName(),
												tableSynthetic.getTargetTableName())) {
											executionLogService.deleteTodayExecutionLogs("treetable_synthetic",
													String.valueOf(tableSynthetic.getId()));
											String ddlStatements = " drop table " + tableSynthetic.getTargetTableName();
											logger.warn(ddlStatements);
											DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
											String monthTargetTable = tableSynthetic.getTargetTableName()
													+ DateUtils.getNowYearMonth();
											if (DBUtils.tableExists(targetDatabase.getName(), monthTargetTable)) {
												ddlStatements = " drop table " + monthTargetTable;
												DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
											}
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
					if (tableSynthetic.getTargetDatabaseId() != null && tableSynthetic.getTargetDatabaseId() > 0) {
						break;
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
		boolean result = false;
		long tableSyntheticId = RequestUtils.getLong(request, "tableSyntheticId");
		TreeTableSynthetic tableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(tableSyntheticId);
		if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getTargetTableName())) {
			long targetDatabaseId = 0;
			TableFactory.clear();
			StringTokenizer token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
					targetDatabaseId = Long.parseLong(x);
					if (tableSynthetic.getTargetDatabaseId() != null && tableSynthetic.getTargetDatabaseId() > 0) {
						targetDatabaseId = tableSynthetic.getTargetDatabaseId();
					}
					try {
						Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
						if (targetDatabase != null) {
							if (StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(), "etl_")
									|| StringUtils.startsWithIgnoreCase(tableSynthetic.getTargetTableName(),
											"tree_table_")) {
								if (DBUtils.tableExists(targetDatabase.getName(),
										tableSynthetic.getTargetTableName())) {
									executionLogService.deleteTodayExecutionLogs("treetable_synthetic",
											String.valueOf(tableSynthetic.getId()));
									String ddlStatements = " drop table " + tableSynthetic.getTargetTableName();
									logger.warn(ddlStatements);
									DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
									String monthTargetTable = tableSynthetic.getTargetTableName()
											+ DateUtils.getNowYearMonth();
									if (DBUtils.tableExists(targetDatabase.getName(), monthTargetTable)) {
										ddlStatements = " drop table " + monthTargetTable;
										DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
									}
								}
								result = true;
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex);
						result = false;
					}
					if (tableSynthetic.getTargetDatabaseId() != null && tableSynthetic.getTargetDatabaseId() > 0) {
						break;
					}
				}
			}
			TableFactory.clear();
		}
		return ResponseUtils.responseResult(result);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TreeTableSynthetic treeTableSynthetic = treeTableSyntheticService
				.getTreeTableSynthetic(RequestUtils.getLong(request, "id"));
		if (treeTableSynthetic != null) {
			request.setAttribute("treeTableSynthetic", treeTableSynthetic);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			if (treeTableSynthetic != null && StringUtils.isNotEmpty(treeTableSynthetic.getSourceDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(treeTableSynthetic.getSourceDatabaseIds());
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

		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
		query.locked(0);
		List<TreeTableSynthetic> list = treeTableSyntheticService.list(query);
		// logger.debug("tables:" + list);
		if (list != null && !list.isEmpty()) {
			request.setAttribute("targetTables", list);
		}

		TableExecutionQuery query2 = new TableExecutionQuery();
		query2.locked(0);
		List<TableExecution> executions = tableExecutionService.list(query2);
		if (executions != null && !executions.isEmpty()) {
			List<String> selected1Ids = new ArrayList<String>();
			List<String> selected2Ids = new ArrayList<String>();
			StringBuilder name1 = new StringBuilder();
			StringBuilder name2 = new StringBuilder();
			List<TableExecution> srcSelectedExecutions = new ArrayList<TableExecution>();
			List<TableExecution> destSelectedExecutions = new ArrayList<TableExecution>();
			for (TableExecution e : executions) {
				if (treeTableSynthetic.getSourceTableExecutionIds() != null
						&& StringUtils.contains(treeTableSynthetic.getSourceTableExecutionIds(), e.getId())) {
					srcSelectedExecutions.add(e);
					selected1Ids.add(e.getId());
					name1.append(e.getTitle()).append("  ");
				}
				if (treeTableSynthetic.getTargetTableExecutionIds() != null
						&& StringUtils.contains(treeTableSynthetic.getTargetTableExecutionIds(), e.getId())) {
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

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("treeTableSynthetic.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/treeTableSynthetic/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request) {
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		long targetDatabaseId = 0;
		TreeTableSyntheticTask task = null;
		StringBuilder buffer = new StringBuilder();
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		long tableSyntheticId = RequestUtils.getLong(request, "tableSyntheticId");
		TreeTableSynthetic tableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(tableSyntheticId);
		if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
			if (StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
				tableSynthetic.setSyncTime(new Date());
				StringTokenizer token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						jobNo = "treetable_synthetic_" + tableSynthetic.getId() + "_" + x + "_" + x + "_" + runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								targetDatabaseId = database.getId();
								if (tableSynthetic.getTargetDatabaseId() != null
										&& tableSynthetic.getTargetDatabaseId() > 0) {
									targetDatabaseId = tableSynthetic.getTargetDatabaseId();
								}
								task = new TreeTableSyntheticTask(database.getId(), targetDatabaseId,
										tableSynthetic.getId(), jobNo);
								if (task.execute()) {
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行成功。\n");
								} else {
									errorCount++;
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行失败。\n");
									StringBuffer sb = ExceptionUtils
											.getMsgBuffer("treetable_synthetic_" + tableSynthetic.getId());
									buffer.append(sb.toString());
								}
							}
						} catch (Exception ex) {
							errorCount++;
							logger.error(ex);
						} finally {
							ExceptionUtils.clear("treetable_synthetic_" + tableSynthetic.getId());
						}
					}
				}

				if (errorCount == 0) {
					tableSynthetic.setSyncStatus(1);
				} else {
					tableSynthetic.setSyncStatus(-1);
				}
				treeTableSyntheticService.updateTreeTableSyntheticStatus(tableSynthetic);
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@ResponseBody
	@RequestMapping("/executeAll")
	public byte[] executeAll(HttpServletRequest request) {
		int errorCount = 0;
		int perErrorCount = 0;
		String jobNo = null;
		Database database = null;
		long targetDatabaseId = 0;
		TreeTableSyntheticTask task = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
		query.locked(0);
		query.deleteFlag(0);
		List<TreeTableSynthetic> rows = treeTableSyntheticService.list(query);
		if (rows != null && !rows.isEmpty()) {
			for (TreeTableSynthetic tableSynthetic : rows) {
				if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
					if (StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
						perErrorCount = 0;
						tableSynthetic.setSyncTime(new Date());
						buffer.append("\n").append(tableSynthetic.getTitle()).append("执行结果：\n");
						StringTokenizer token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								jobNo = "treetable_synthetic_" + tableSynthetic.getId() + "_" + x + "_" + x + "_"
										+ runDay;
								try {
									database = databaseService.getDatabaseById(Long.parseLong(x));
									if (database != null) {
										targetDatabaseId = database.getId();
										if (tableSynthetic.getTargetDatabaseId() != null
												&& tableSynthetic.getTargetDatabaseId() > 0) {
											targetDatabaseId = tableSynthetic.getTargetDatabaseId();
										}
										task = new TreeTableSyntheticTask(database.getId(), targetDatabaseId,
												tableSynthetic.getId(), jobNo);
										if (task.execute()) {
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行成功。\n");
										} else {
											errorCount++;
											perErrorCount++;
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行失败。\n");
											StringBuffer sb = ExceptionUtils
													.getMsgBuffer("treetable_synthetic_" + tableSynthetic.getId());
											buffer.append(sb.toString());
										}
									}
								} catch (Exception ex) {
									errorCount++;
									perErrorCount++;
									logger.error(ex);
								} finally {
									ExceptionUtils.clear("treetable_synthetic_" + tableSynthetic.getId());
								}
							}
						}
						if (perErrorCount == 0) {
							tableSynthetic.setSyncStatus(1);
						} else {
							tableSynthetic.setSyncStatus(-1);
						}
						treeTableSyntheticService.updateTreeTableSyntheticStatus(tableSynthetic);
					}
				}
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@ResponseBody
	@RequestMapping("/executeAllToSourceDB")
	public byte[] executeAllToSourceDB(HttpServletRequest request) {
		int errorCount = 0;
		int perErrorCount = 0;
		String jobNo = null;
		Database database = null;
		TreeTableSyntheticTask task = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
		query.locked(0);
		query.deleteFlag(0);
		List<TreeTableSynthetic> rows = treeTableSyntheticService.list(query);
		if (rows != null && !rows.isEmpty()) {
			for (TreeTableSynthetic tableSynthetic : rows) {
				if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
					if (StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
						perErrorCount = 0;
						tableSynthetic.setSyncTime(new Date());
						buffer.append("\n").append(tableSynthetic.getTitle()).append("执行结果：\n");
						StringTokenizer token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								jobNo = "treetable_synthetic_" + tableSynthetic.getId() + "_" + x + "_" + x + "_"
										+ runDay;
								try {
									database = databaseService.getDatabaseById(Long.parseLong(x));
									if (database != null) {
										task = new TreeTableSyntheticTask(database.getId(), database.getId(),
												tableSynthetic.getId(), jobNo);
										if (task.execute()) {
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行成功。\n");
										} else {
											errorCount++;
											perErrorCount++;
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行失败。\n");
											StringBuffer sb = ExceptionUtils
													.getMsgBuffer("treetable_synthetic_" + tableSynthetic.getId());
											buffer.append(sb.toString());
										}
									}
								} catch (Exception ex) {
									errorCount++;
									perErrorCount++;
								} finally {
									ExceptionUtils.clear("treetable_synthetic_" + tableSynthetic.getId());
								}
							}
						}
						if (perErrorCount == 0) {
							tableSynthetic.setSyncStatus(1);
						} else {
							tableSynthetic.setSyncStatus(-1);
						}
						treeTableSyntheticService.updateTreeTableSyntheticStatus(tableSynthetic);
					}
				}
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@ResponseBody
	@RequestMapping("/executeToSourceDB")
	public byte[] executeToSourceDB(HttpServletRequest request) {
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		TreeTableSyntheticTask task = null;
		StringBuilder buffer = new StringBuilder();
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		long tableSyntheticId = RequestUtils.getLong(request, "tableSyntheticId");
		TreeTableSynthetic tableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(tableSyntheticId);
		if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
			if (StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
				tableSynthetic.setSyncTime(new Date());
				StringTokenizer token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						jobNo = "treetable_synthetic_" + tableSynthetic.getId() + "_" + x + "_" + x + "_" + runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								task = new TreeTableSyntheticTask(database.getId(), database.getId(),
										tableSynthetic.getId(), jobNo);
								if (task.execute()) {
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行成功。\n");
								} else {
									errorCount++;
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行失败。\n");
									StringBuffer sb = ExceptionUtils
											.getMsgBuffer("treetable_synthetic_" + tableSynthetic.getId());
									buffer.append(sb.toString());
								}
							}
						} catch (Exception ex) {
							errorCount++;
							logger.error(ex);
						} finally {
							ExceptionUtils.clear("treetable_synthetic_" + tableSynthetic.getId());
						}
					}
				}

				if (errorCount == 0) {
					tableSynthetic.setSyncStatus(1);
				} else {
					tableSynthetic.setSyncStatus(-1);
				}
				treeTableSyntheticService.updateTreeTableSyntheticStatus(tableSynthetic);
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
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
		int total = treeTableSyntheticService.getTreeTableSyntheticCountByQueryCriteria(query);
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

			List<TreeTableSynthetic> list = treeTableSyntheticService.getTreeTableSyntheticsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeTableSynthetic treeTableSynthetic : list) {
					JSONObject rowJSON = treeTableSynthetic.toJsonObject();
					rowJSON.put("id", treeTableSynthetic.getId());
					rowJSON.put("rowId", treeTableSynthetic.getId());
					rowJSON.put("treeTableSyntheticId", treeTableSynthetic.getId());
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

		return new ModelAndView("/datamgr/treeTableSynthetic/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("treeTableSynthetic.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/treeTableSynthetic/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TreeTableSynthetic treeTableSynthetic = new TreeTableSynthetic();
		Tools.populate(treeTableSynthetic, params);

		treeTableSynthetic.setName(request.getParameter("name"));
		treeTableSynthetic.setTitle(request.getParameter("title"));
		treeTableSynthetic.setType(request.getParameter("type"));
		treeTableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
		treeTableSynthetic.setSourceIdColumn(request.getParameter("sourceIdColumn"));
		treeTableSynthetic.setSourceIndexIdColumn(request.getParameter("sourceIndexIdColumn"));
		treeTableSynthetic.setSourceParentIdColumn(request.getParameter("sourceParentIdColumn"));
		treeTableSynthetic.setSourceTreeIdColumn(request.getParameter("sourceTreeIdColumn"));
		treeTableSynthetic.setSourceTextColumn(request.getParameter("sourceTextColumn"));
		treeTableSynthetic.setSourceWbsIndexColumn(request.getParameter("sourceWbsIndexColumn"));
		treeTableSynthetic.setSourceDatabaseIds(request.getParameter("sourceDatabaseIds"));
		treeTableSynthetic.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
		treeTableSynthetic.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
		treeTableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
		treeTableSynthetic.setTargetTableName(request.getParameter("targetTableName"));
		treeTableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
		treeTableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
		treeTableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
		treeTableSynthetic.setGenNewPrimaryKey(request.getParameter("genNewPrimaryKey"));
		treeTableSynthetic.setGenByMonth(request.getParameter("genByMonth"));
		treeTableSynthetic.setSyntheticFlag(request.getParameter("syntheticFlag"));
		treeTableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
		treeTableSynthetic.setSyncStatus(RequestUtils.getInt(request, "syncStatus"));
		treeTableSynthetic.setSyncTime(RequestUtils.getDate(request, "syncTime"));
		treeTableSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
		treeTableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
		treeTableSynthetic.setCreateBy(actorId);
		treeTableSynthetic.setUpdateBy(actorId);

		treeTableSyntheticService.save(treeTableSynthetic);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAs")
	public byte[] saveAs(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		long id = RequestUtils.getLong(request, "id");
		TreeTableSynthetic treeTableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(id);
		try {
			if (treeTableSynthetic == null) {
				treeTableSynthetic = new TreeTableSynthetic();
			}
			Tools.populate(treeTableSynthetic, params);
			treeTableSynthetic.setName(request.getParameter("name"));
			treeTableSynthetic.setTitle(request.getParameter("title"));
			treeTableSynthetic.setType(request.getParameter("type"));
			treeTableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
			treeTableSynthetic.setSourceIdColumn(request.getParameter("sourceIdColumn"));
			treeTableSynthetic.setSourceIndexIdColumn(request.getParameter("sourceIndexIdColumn"));
			treeTableSynthetic.setSourceParentIdColumn(request.getParameter("sourceParentIdColumn"));
			treeTableSynthetic.setSourceTreeIdColumn(request.getParameter("sourceTreeIdColumn"));
			treeTableSynthetic.setSourceTextColumn(request.getParameter("sourceTextColumn"));
			treeTableSynthetic.setSourceWbsIndexColumn(request.getParameter("sourceWbsIndexColumn"));
			treeTableSynthetic.setSourceDatabaseIds(request.getParameter("sourceDatabaseIds"));
			treeTableSynthetic.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
			treeTableSynthetic.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
			treeTableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
			treeTableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			treeTableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
			treeTableSynthetic.setGenNewPrimaryKey(request.getParameter("genNewPrimaryKey"));
			treeTableSynthetic.setGenByMonth(request.getParameter("genByMonth"));
			treeTableSynthetic.setSyntheticFlag(request.getParameter("syntheticFlag"));
			treeTableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
			treeTableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
			treeTableSynthetic.setSyncStatus(RequestUtils.getInt(request, "syncStatus"));
			treeTableSynthetic.setSyncTime(RequestUtils.getDate(request, "syncTime"));
			treeTableSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
			treeTableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
			treeTableSynthetic.setCreateBy(actorId);
			treeTableSynthetic.setUpdateBy(actorId);

			this.treeTableSyntheticService.saveAs(treeTableSynthetic);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody TreeTableSynthetic saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		TreeTableSynthetic treeTableSynthetic = new TreeTableSynthetic();
		try {
			Tools.populate(treeTableSynthetic, model);
			treeTableSynthetic.setName(ParamUtils.getString(model, "name"));
			treeTableSynthetic.setTitle(ParamUtils.getString(model, "title"));
			treeTableSynthetic.setType(ParamUtils.getString(model, "type"));
			treeTableSynthetic.setSourceTableName(ParamUtils.getString(model, "sourceTableName"));
			treeTableSynthetic.setSourceIdColumn(ParamUtils.getString(model, "sourceIdColumn"));
			treeTableSynthetic.setSourceIndexIdColumn(ParamUtils.getString(model, "sourceIndexIdColumn"));
			treeTableSynthetic.setSourceParentIdColumn(ParamUtils.getString(model, "sourceParentIdColumn"));
			treeTableSynthetic.setSourceTreeIdColumn(ParamUtils.getString(model, "sourceTreeIdColumn"));
			treeTableSynthetic.setSourceTextColumn(ParamUtils.getString(model, "sourceTextColumn"));
			treeTableSynthetic.setSourceWbsIndexColumn(ParamUtils.getString(model, "sourceWbsIndexColumn"));
			treeTableSynthetic.setSourceDatabaseIds(ParamUtils.getString(model, "sourceDatabaseIds"));
			treeTableSynthetic.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
			treeTableSynthetic.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
			treeTableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
			treeTableSynthetic.setTargetTableName(ParamUtils.getString(model, "targetTableName"));
			treeTableSynthetic.setTargetDatabaseId(ParamUtils.getLong(model, "targetDatabaseId"));
			treeTableSynthetic.setCreateTableFlag(ParamUtils.getString(model, "createTableFlag"));
			treeTableSynthetic.setGenNewPrimaryKey(request.getParameter("genNewPrimaryKey"));
			treeTableSynthetic.setGenByMonth(request.getParameter("genByMonth"));
			treeTableSynthetic.setSyntheticFlag(ParamUtils.getString(model, "syntheticFlag"));
			treeTableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
			treeTableSynthetic.setDeleteFetch(ParamUtils.getString(model, "deleteFetch"));
			treeTableSynthetic.setSyncStatus(ParamUtils.getInt(model, "syncStatus"));
			treeTableSynthetic.setSyncTime(ParamUtils.getDate(model, "syncTime"));
			treeTableSynthetic.setSortNo(ParamUtils.getInt(model, "sortNo"));
			treeTableSynthetic.setLocked(ParamUtils.getInt(model, "locked"));
			treeTableSynthetic.setCreateBy(actorId);
			treeTableSynthetic.setUpdateBy(actorId);
			this.treeTableSyntheticService.save(treeTableSynthetic);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return treeTableSynthetic;
	}

	@ResponseBody
	@RequestMapping("/saveTreeTableSynthetic")
	public byte[] saveTreeTableSynthetic(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableSynthetic treeTableSynthetic = new TreeTableSynthetic();
		try {
			Tools.populate(treeTableSynthetic, params);
			treeTableSynthetic.setName(request.getParameter("name"));
			treeTableSynthetic.setTitle(request.getParameter("title"));
			treeTableSynthetic.setType(request.getParameter("type"));
			treeTableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
			treeTableSynthetic.setSourceIdColumn(request.getParameter("sourceIdColumn"));
			treeTableSynthetic.setSourceIndexIdColumn(request.getParameter("sourceIndexIdColumn"));
			treeTableSynthetic.setSourceParentIdColumn(request.getParameter("sourceParentIdColumn"));
			treeTableSynthetic.setSourceTreeIdColumn(request.getParameter("sourceTreeIdColumn"));
			treeTableSynthetic.setSourceTextColumn(request.getParameter("sourceTextColumn"));
			treeTableSynthetic.setSourceWbsIndexColumn(request.getParameter("sourceWbsIndexColumn"));
			treeTableSynthetic.setSourceDatabaseIds(request.getParameter("sourceDatabaseIds"));
			treeTableSynthetic.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
			treeTableSynthetic.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
			treeTableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
			treeTableSynthetic.setTargetTableName(request.getParameter("targetTableName"));
			treeTableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			treeTableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
			treeTableSynthetic.setGenNewPrimaryKey(request.getParameter("genNewPrimaryKey"));
			treeTableSynthetic.setGenByMonth(request.getParameter("genByMonth"));
			treeTableSynthetic.setSyntheticFlag(request.getParameter("syntheticFlag"));
			treeTableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
			treeTableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
			treeTableSynthetic.setSyncStatus(RequestUtils.getInt(request, "syncStatus"));
			treeTableSynthetic.setSyncTime(RequestUtils.getDate(request, "syncTime"));
			treeTableSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
			treeTableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
			treeTableSynthetic.setCreateBy(actorId);
			treeTableSynthetic.setUpdateBy(actorId);

			this.treeTableSyntheticService.save(treeTableSynthetic);

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
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setDictoryDefinitionService(DictoryDefinitionService dictoryDefinitionService) {
		this.dictoryDefinitionService = dictoryDefinitionService;
	}

	@javax.annotation.Resource
	public void setExecutionLogService(ExecutionLogService executionLogService) {
		this.executionLogService = executionLogService;
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.tableExecutionService")
	public void setTableExecutionService(TableExecutionService tableExecutionService) {
		this.tableExecutionService = tableExecutionService;
	}

	@javax.annotation.Resource
	public void setTreeTableSyntheticService(TreeTableSyntheticService treeTableSyntheticService) {
		this.treeTableSyntheticService = treeTableSyntheticService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TreeTableSynthetic treeTableSynthetic = treeTableSyntheticService
				.getTreeTableSynthetic(RequestUtils.getLong(request, "id"));

		Tools.populate(treeTableSynthetic, params);

		treeTableSynthetic.setName(request.getParameter("name"));
		treeTableSynthetic.setTitle(request.getParameter("title"));
		treeTableSynthetic.setType(request.getParameter("type"));
		treeTableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
		treeTableSynthetic.setSourceIdColumn(request.getParameter("sourceIdColumn"));
		treeTableSynthetic.setSourceIndexIdColumn(request.getParameter("sourceIndexIdColumn"));
		treeTableSynthetic.setSourceParentIdColumn(request.getParameter("sourceParentIdColumn"));
		treeTableSynthetic.setSourceTreeIdColumn(request.getParameter("sourceTreeIdColumn"));
		treeTableSynthetic.setSourceTextColumn(request.getParameter("sourceTextColumn"));
		treeTableSynthetic.setSourceWbsIndexColumn(request.getParameter("sourceWbsIndexColumn"));
		treeTableSynthetic.setSourceDatabaseIds(request.getParameter("sourceDatabaseIds"));
		treeTableSynthetic.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
		treeTableSynthetic.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
		treeTableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
		treeTableSynthetic.setTargetTableName(request.getParameter("targetTableName"));
		treeTableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
		treeTableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
		treeTableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
		treeTableSynthetic.setGenNewPrimaryKey(request.getParameter("genNewPrimaryKey"));
		treeTableSynthetic.setGenByMonth(request.getParameter("genByMonth"));
		treeTableSynthetic.setSyntheticFlag(request.getParameter("syntheticFlag"));
		treeTableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
		treeTableSynthetic.setSyncStatus(RequestUtils.getInt(request, "syncStatus"));
		treeTableSynthetic.setSyncTime(RequestUtils.getDate(request, "syncTime"));
		treeTableSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
		treeTableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
		treeTableSynthetic.setUpdateBy(user.getActorId());

		treeTableSyntheticService.save(treeTableSynthetic);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/updateAllMeta")
	public byte[] updateAllMeta(HttpServletRequest request) {
		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
		query.setLocked(0);
		query.setDeleteFlag(0);
		List<TreeTableSynthetic> list = treeTableSyntheticService.list(query);
		if (list != null && !list.isEmpty()) {
			List<TreeTableSyntheticRule> rules = null;
			TreeTableSyntheticMetaBean metaBean = new TreeTableSyntheticMetaBean();
			for (TreeTableSynthetic tableSynthetic : list) {
				try {
					rules = treeTableSyntheticService
							.getTreeTableSyntheticRulesByTableName(tableSynthetic.getTargetTableName());
					metaBean.updateTreeTableSyntheticMeta(tableSynthetic, rules);
				} catch (Exception ex) {
				}
			}
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(true);
	}

	@ResponseBody
	@RequestMapping("/updateMeta")
	public byte[] updateMeta(HttpServletRequest request) {
		long tableSyntheticId = RequestUtils.getLong(request, "tableSyntheticId");
		TreeTableSynthetic tableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(tableSyntheticId);
		if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getTargetTableName())) {
			TreeTableSyntheticMetaBean metaBean = new TreeTableSyntheticMetaBean();
			List<TreeTableSyntheticRule> rules = tableSynthetic.getRules();
			metaBean.updateTreeTableSyntheticMeta(tableSynthetic, rules);
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TreeTableSynthetic treeTableSynthetic = treeTableSyntheticService
				.getTreeTableSynthetic(RequestUtils.getLong(request, "id"));
		request.setAttribute("treeTableSynthetic", treeTableSynthetic);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("treeTableSynthetic.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/treeTableSynthetic/view");
	}

}
