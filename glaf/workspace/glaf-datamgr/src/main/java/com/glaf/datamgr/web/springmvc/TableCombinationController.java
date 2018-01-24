/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.domain.TableCombination;
import com.glaf.datamgr.domain.TableCombinationColumn;
import com.glaf.datamgr.domain.TableCombinationRule;
import com.glaf.datamgr.query.TableCombinationQuery;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.TableCombinationService;
import com.glaf.datamgr.task.TableCombinationTask;
import com.glaf.datamgr.util.ExceptionUtils;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/tableCombination")
@RequestMapping("/sys/tableCombination")
public class TableCombinationController {
	protected static final Log logger = LogFactory.getLog(TableCombinationController.class);

	protected DataSetService dataSetService;

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	protected TableCombinationService tableCombinationService;

	public TableCombinationController() {

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
				if (database != null && DBUtils.tableExists(database.getName(), tableName)) {
					columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
				} else {
					columns = DBUtils.getColumnDefinitions(tableName);
				}

				TableCombination tableCombination = tableCombinationService
						.getTableCombination(RequestUtils.getLong(request, "tableCombinationId"));
				if (tableCombination != null) {
					request.setAttribute("tableCombination", tableCombination);
				}

				if (columns != null && !columns.isEmpty()) {
					List<String> selected = new ArrayList<String>();
					List<ColumnDefinition> unselected = new ArrayList<ColumnDefinition>();
					if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getSyncColumns())) {
						List<String> cols = StringTools.split(tableCombination.getSyncColumns());
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

		String x_view = ViewProperties.getString("tableCombination.chooseColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableCombination/chooseColumns", modelMap);
	}

	@RequestMapping("/chooseDatabases")
	public ModelAndView chooseDatabases(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableCombination tableCombination = tableCombinationService
				.getTableCombination(RequestUtils.getLong(request, "tableCombinationId"));
		if (tableCombination != null) {
			request.setAttribute("tableCombination", tableCombination);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<Long> selected = new ArrayList<Long>();
			List<Database> unselected = new ArrayList<Database>();
			if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(tableCombination.getDatabaseIds());
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

		String x_view = ViewProperties.getString("tableCombination.chooseDatabases");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableCombination/chooseDatabases", modelMap);
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
					TableCombination tableCombination = tableCombinationService.getTableCombination(Long.valueOf(x));
					if (tableCombination != null
							&& (StringUtils.equals(tableCombination.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						tableCombinationService.deleteById(tableCombination.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TableCombination tableCombination = tableCombinationService.getTableCombination(Long.valueOf(id));
			if (tableCombination != null
					&& (StringUtils.equals(tableCombination.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				tableCombinationService.deleteById(tableCombination.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/dropTable")
	public byte[] dropTable(HttpServletRequest request, ModelMap modelMap) {
		long tableCombinationId = RequestUtils.getLong(request, "tableCombinationId");
		TableCombination tableCombination = tableCombinationService.getTableCombination(tableCombinationId);
		if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getTargetTableName())) {
			boolean result = true;
			try {
				if (tableCombination.getTargetDatabaseId() > 0) {
					Database targetDatabase = databaseService.getDatabaseById(tableCombination.getTargetDatabaseId());
					if (targetDatabase != null) {
						if (StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "etl_")
								|| StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "sync_")
								|| StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "tmp")
								|| StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "useradd_")
								|| StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(),
										"tree_table_")) {
							if (DBUtils.tableExists(targetDatabase.getName(), tableCombination.getTargetTableName())) {
								String ddlStatements = " drop table " + tableCombination.getTargetTableName();
								logger.warn(ddlStatements);
								DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
								result = true;
							}
						}
					}
				} else {
					StringTokenizer token = new StringTokenizer(tableCombination.getDatabaseIds(), ",");
					while (token.hasMoreTokens()) {
						String x = token.nextToken();
						if (StringUtils.isNotEmpty(x)) {
							long databaseId = Long.parseLong(x);
							Database targetDatabase = databaseService.getDatabaseById(databaseId);
							if (targetDatabase != null) {
								if (StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(), "etl_")
										|| StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(),
												"sync_")
										|| StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(),
												"tmp")
										|| StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(),
												"useradd_")
										|| StringUtils.startsWithIgnoreCase(tableCombination.getTargetTableName(),
												"tree_table_")) {
									if (DBUtils.tableExists(targetDatabase.getName(),
											tableCombination.getTargetTableName())) {
										String ddlStatements = " drop table " + tableCombination.getTargetTableName();
										logger.warn(ddlStatements);
										DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
									}
								}
							}
						}
					}
				}
			} catch (Exception ex) {
				result = false;
				logger.error(ex);
			} finally {
				TableFactory.clear();
			}
			return ResponseUtils.responseResult(result);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableCombination tableCombination = tableCombinationService
				.getTableCombination(RequestUtils.getLong(request, "id"));
		if (tableCombination != null) {
			request.setAttribute("tableCombination", tableCombination);

			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<Database> databases = config.getDatabases();
			if (databases != null && !databases.isEmpty()) {
				StringBuilder buffer = new StringBuilder();
				if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
					List<Long> ids = StringTools.splitToLong(tableCombination.getDatabaseIds());
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

			if (StringUtils.isNotEmpty(tableCombination.getDatasetIds())) {
				DataSet dataSet = null;
				StringBuilder datasetName = new StringBuilder();
				StringTokenizer token = new StringTokenizer(tableCombination.getDatasetIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						dataSet = dataSetService.getDataSet(x);
						if (dataSet != null) {
							datasetName.append(dataSet.getName()).append("[").append(dataSet.getTitle()).append("],");
						}
					}
				}
				request.setAttribute("datasetName", datasetName.toString());
			}
		} else {
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<Database> databases = config.getDatabases();
			if (databases != null && !databases.isEmpty()) {
				StringBuilder buffer = new StringBuilder();
				for (Database database : databases) {
					buffer.append(database.getTitle()).append("[").append(database.getMapping()).append("]")
							.append(",");
				}
				request.setAttribute("databases", databases);
				request.setAttribute("selectedDB", buffer.toString());
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableCombination.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableCombination/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request, ModelMap modelMap) {
		long tableCombinationId = RequestUtils.getLong(request, "tableCombinationId");
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		long targetDatabaseId = 0;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		TableCombination tableCombination = tableCombinationService.getTableCombination(tableCombinationId);
		if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
			if (StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
				tableCombination.setSyncTime(new Date());
				StringTokenizer token = new StringTokenizer(tableCombination.getDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						if (tableCombination.getTargetDatabaseId() == 0) {
							targetDatabaseId = Long.parseLong(x);
						} else {
							targetDatabaseId = tableCombination.getTargetDatabaseId();
						}
						jobNo = "table_combination_" + tableCombination.getId() + "_" + x + "_" + targetDatabaseId + "_"
								+ runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								TableCombinationTask task = new TableCombinationTask(database.getId(), targetDatabaseId,
										tableCombination.getId(), jobNo);
								if (task.execute()) {
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行成功。\n");
								} else {
									errorCount++;
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行失败。\n");
									StringBuffer sb = ExceptionUtils
											.getMsgBuffer("table_combination_" + tableCombination.getId());
									buffer.append(sb != null ? sb.toString() : "");
								}
							}
						} catch (Exception ex) {
							errorCount++;
						} finally {
							ExceptionUtils.clear("table_combination_" + tableCombination.getId());
						}
					}
				}

				if (errorCount == 0) {
					tableCombination.setSyncStatus(1);
				} else {
					tableCombination.setSyncStatus(-1);
				}
				tableCombinationService.updateTableCombinationStatus(tableCombination);
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableCombinationQuery query = new TableCombinationQuery();
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
		int total = tableCombinationService.getTableCombinationCountByQueryCriteria(query);
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

			List<TableCombination> list = tableCombinationService.getTableCombinationsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableCombination tableCombination : list) {
					JSONObject rowJSON = tableCombination.toJsonObject();
					rowJSON.put("id", tableCombination.getId());
					rowJSON.put("rowId", tableCombination.getId());
					rowJSON.put("tableCombinationId", tableCombination.getId());
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

		return new ModelAndView("/datamgr/tableCombination/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("tableCombination.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/tableCombination/query", modelMap);
	}

	 

	@RequestMapping("/rule")
	public ModelAndView rule(HttpServletRequest request, ModelMap modelMap) {
//		RequestUtils.setRequestParameterToAttribute(request);
//
//		TableCombination tableCombination = tableCombinationService
//				.getTableCombination(RequestUtils.getLong(request, "tableCombinationId"));
//		if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getTemplateTableName())) {
//			request.setAttribute("tableCombination", tableCombination);
//			Map<String, String> titleMap = new HashMap<String, String>();
//			if (tableCombination.getTargetDatabaseId() > 0) {
//				Database db = databaseService.getDatabaseById(tableCombination.getTargetDatabaseId());
//				if (DBUtils.tableExists(db.getName(), "interface")) {
//					QueryHelper helper = new QueryHelper();
//					List<Map<String, Object>> list2 = helper
//							.getResultList(db.getName(),
//									" select dname, fname from interface where frmtype = '"
//											+ tableCombination.getTargetTableName() + "' ",
//									new HashMap<String, Object>());
//					if (list2 != null && !list2.isEmpty()) {
//						for (Map<String, Object> dataMap : list2) {
//							if (ParamUtils.getString(dataMap, "dname") != null) {
//								titleMap.put(ParamUtils.getString(dataMap, "dname"),
//										ParamUtils.getString(dataMap, "fname"));
//								titleMap.put(ParamUtils.getString(dataMap, "dname").toLowerCase(),
//										ParamUtils.getString(dataMap, "fname"));
//								titleMap.put(ParamUtils.getString(dataMap, "dname").toUpperCase(),
//										ParamUtils.getString(dataMap, "fname"));
//							}
//						}
//					}
//				}
//			} else {
//				List<Long> databaseIds = StringTools.splitToLong(tableCombination.getDatabaseIds());
//				if (databaseIds != null && !databaseIds.isEmpty()) {
//					Database db = databaseService.getDatabaseById(databaseIds.get(0));
//					if (DBUtils.tableExists(db.getName(), "interface")) {
//						QueryHelper helper = new QueryHelper();
//						List<Map<String, Object>> list2 = helper
//								.getResultList(db.getName(),
//										" select dname, fname from interface where frmtype = '"
//												+ tableCombination.getTargetTableName() + "' ",
//										new HashMap<String, Object>());
//						if (list2 != null && !list2.isEmpty()) {
//							for (Map<String, Object> dataMap : list2) {
//								if (ParamUtils.getString(dataMap, "dname") != null) {
//									titleMap.put(ParamUtils.getString(dataMap, "dname"),
//											ParamUtils.getString(dataMap, "fname"));
//									titleMap.put(ParamUtils.getString(dataMap, "dname").toLowerCase(),
//											ParamUtils.getString(dataMap, "fname"));
//									titleMap.put(ParamUtils.getString(dataMap, "dname").toUpperCase(),
//											ParamUtils.getString(dataMap, "fname"));
//								}
//							}
//						}
//					}
//				}
//			}
//			if (tableCombination.getRules() != null && !tableCombination.getRules().isEmpty()) {
//				List<TableCombinationRule> rules = tableCombination.getRules();
//				for (TableCombinationRule rule : rules) {
//					request.setAttribute(rule.getTargetTableName() + "_rule", rule);
//				}
//			}
//
//			if (tableCombination.getColumns() != null && !tableCombination.getColumns().isEmpty()) {
//				List<TableCombinationColumn> columns = tableCombination.getColumns();
//				for (TableCombinationColumn column : columns) {
//					if (column.getTargetTableName() != null) {
//						request.setAttribute(column.getTargetTableName() + "_" + column.getSourceColumnName(), column);
//						request.setAttribute(column.getTargetTableName() + "_" + column.getSourceColumnName() + "_col",
//								column.getTargetColumnName());
//						request.setAttribute(column.getTargetTableName() + "_" + column.getSourceColumnName() + "_init",
//								column.getInitValue());
//					}
//
//					if (column.getDatasetId() != null) {
//						request.setAttribute(column.getDatasetId() + "_" + column.getSourceColumnName(), column);
//						request.setAttribute(column.getDatasetId() + "_" + column.getSourceColumnName() + "_col",
//								column.getTargetColumnLabel());
//					}
//				}
//			}
//
//			long databaseId = tableCombination.getTargetDatabaseId();
//			Database database = databaseService.getDatabaseById(databaseId);
//			if (database != null) {
//				List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(database.getName(),
//						tableCombination.getTemplateTableName());
//				if (StringUtils.isNotEmpty(tableCombination.getSyncColumns())) {
//					List<String> syncColumns = StringTools.split(tableCombination.getSyncColumns());
//					List<ColumnDefinition> list = new ArrayList<ColumnDefinition>();
//					Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
//					for (ColumnDefinition col : columns) {
//						col.setTitle(titleMap.get(col.getColumnName().toLowerCase()));
//						columnMap.put(col.getColumnName().toLowerCase(), col);
//					}
//					for (String syncColumn : syncColumns) {
//						if (columnMap.get(syncColumn.toLowerCase()) != null) {
//							list.add(columnMap.get(syncColumn.toLowerCase()));
//						}
//					}
//					request.setAttribute("syncColumns", syncColumns);
//					request.setAttribute("columns", list);
//				}
//				List<String> tableNames = StringTools.split(tableCombination.getSyncTableNames());
//				request.setAttribute("tableNames", tableNames);
//				for (String tableName : tableNames) {
//					List<ColumnDefinition> columns2 = DBUtils.getColumnDefinitions(database.getName(), tableName);
//					request.setAttribute(tableName + "_columns", columns2);
//				}
//			}
//
//			if (StringUtils.isNotEmpty(tableCombination.getDatasetIds())) {
//				DataSet dataSet = null;
//				List<DataSet> dataSetList = new ArrayList<DataSet>();
//				StringTokenizer token = new StringTokenizer(tableCombination.getDatasetIds(), ",");
//				while (token.hasMoreTokens()) {
//					String x = token.nextToken();
//					if (StringUtils.isNotEmpty(x)) {
//						dataSet = dataSetService.getDataSet(x);
//						if (dataSet != null && dataSet.getSelectSegments() != null) {
//							dataSetList.add(dataSet);
//							List<SelectSegment> selectSegments = dataSet.getSelectSegments();
//							if (selectSegments != null && !selectSegments.isEmpty()) {
//								List<ColumnDefinition> columns2 = new ArrayList<ColumnDefinition>();
//								for (SelectSegment seg : selectSegments) {
//									ColumnDefinition col = new ColumnDefinition();
//									col.setColumnName(seg.getColumnName());
//									col.setColumnLabel(seg.getColumnLabel());
//									col.setTitle(seg.getTitle());
//									columns2.add(col);
//								}
//
//								request.setAttribute(dataSet.getId() + "_columns", columns2);
//							}
//						}
//					}
//				}
//				request.setAttribute("dataSetList", dataSetList);
//			}
//		}
//
//		String view = request.getParameter("view");
//		if (StringUtils.isNotEmpty(view)) {
//			return new ModelAndView(view, modelMap);
//		}
//
//		String x_view = ViewProperties.getString("tableCombination.rule");
//		if (StringUtils.isNotEmpty(x_view)) {
//			return new ModelAndView(x_view, modelMap);
//		}
//
//		return new ModelAndView("/datamgr/tableCombination/rule", modelMap);
		RequestUtils.setRequestParameterToAttribute(request);

		TableCombination tableCombination = tableCombinationService
				.getTableCombination(RequestUtils.getLong(request, "tableCombinationId"));
		if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getTemplateTableName())) {
			request.setAttribute("tableCombination", tableCombination);
			Map<String, String> titleMap = new HashMap<String, String>();
			if (tableCombination.getTargetDatabaseId() > 0) {
				Database db = databaseService.getDatabaseById(tableCombination.getTargetDatabaseId());
				if (DBUtils.tableExists(db.getName(), "interface")) {
					QueryHelper helper = new QueryHelper();
					List<Map<String, Object>> list2 = helper
							.getResultList(db.getName(),
									" select dname, fname from interface where frmtype = '"
											+ tableCombination.getTargetTableName() + "' ",
									new HashMap<String, Object>());
					if (list2 != null && !list2.isEmpty()) {
						for (Map<String, Object> dataMap : list2) {
							if (ParamUtils.getString(dataMap, "dname") != null) {
								titleMap.put(ParamUtils.getString(dataMap, "dname"),
										ParamUtils.getString(dataMap, "fname"));
								titleMap.put(ParamUtils.getString(dataMap, "dname").toLowerCase(),
										ParamUtils.getString(dataMap, "fname"));
								titleMap.put(ParamUtils.getString(dataMap, "dname").toUpperCase(),
										ParamUtils.getString(dataMap, "fname"));
							}
						}
					}
				}
			} else {
				List<Long> databaseIds = StringTools.splitToLong(tableCombination.getDatabaseIds());
				if (databaseIds != null && !databaseIds.isEmpty()) {
					Database db = databaseService.getDatabaseById(databaseIds.get(0));
					if (DBUtils.tableExists(db.getName(), "interface")) {
						QueryHelper helper = new QueryHelper();
						List<Map<String, Object>> list2 = helper
								.getResultList(db.getName(),
										" select dname, fname from interface where frmtype = '"
												+ tableCombination.getTargetTableName() + "' ",
										new HashMap<String, Object>());
						if (list2 != null && !list2.isEmpty()) {
							for (Map<String, Object> dataMap : list2) {
								if (ParamUtils.getString(dataMap, "dname") != null) {
									titleMap.put(ParamUtils.getString(dataMap, "dname"),
											ParamUtils.getString(dataMap, "fname"));
									titleMap.put(ParamUtils.getString(dataMap, "dname").toLowerCase(),
											ParamUtils.getString(dataMap, "fname"));
									titleMap.put(ParamUtils.getString(dataMap, "dname").toUpperCase(),
											ParamUtils.getString(dataMap, "fname"));
								}
							}
						}
					}
				}
			}
			if (tableCombination.getRules() != null && !tableCombination.getRules().isEmpty()) {
				List<TableCombinationRule> rules = tableCombination.getRules();
				for (TableCombinationRule rule : rules) {
					request.setAttribute(rule.getTargetTableName() + "_rule", rule);
				}
			}

			if (tableCombination.getColumns() != null && !tableCombination.getColumns().isEmpty()) {
				List<TableCombinationColumn> columns = tableCombination.getColumns();
				for (TableCombinationColumn column : columns) {
					if (column.getTargetTableName() != null) {
						request.setAttribute(column.getTargetTableName() + "_" + column.getSourceColumnName(), column);
						request.setAttribute(column.getTargetTableName() + "_" + column.getSourceColumnName() + "_col",
								column.getTargetColumnName());
					}

					if (column.getDatasetId() != null) {
						request.setAttribute(column.getDatasetId() + "_" + column.getSourceColumnName(), column);
						request.setAttribute(column.getDatasetId() + "_" + column.getSourceColumnName() + "_col",
								column.getTargetColumnLabel());
					}
				}
			}

			if (StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
				StringTokenizer token = new StringTokenizer(tableCombination.getDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						long databaseId = Long.parseLong(x);
						Database database = databaseService.getDatabaseById(databaseId);
						if (database != null) {
							List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(database.getName(),
									tableCombination.getTemplateTableName());
							if (StringUtils.isNotEmpty(tableCombination.getSyncColumns())) {
								List<String> syncColumns = StringTools.split(tableCombination.getSyncColumns());
								List<ColumnDefinition> list = new ArrayList<ColumnDefinition>();
								Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
								for (ColumnDefinition col : columns) {
									col.setTitle(titleMap.get(col.getColumnName().toLowerCase()));
									columnMap.put(col.getColumnName().toLowerCase(), col);
								}
								for (String syncColumn : syncColumns) {
									if (columnMap.get(syncColumn.toLowerCase()) != null) {
										list.add(columnMap.get(syncColumn.toLowerCase()));
									}
								}
								request.setAttribute("syncColumns", syncColumns);
								request.setAttribute("columns", list);
							}
							List<String> tableNames = StringTools.split(tableCombination.getSyncTableNames());
							request.setAttribute("tableNames", tableNames);
							for (String tableName : tableNames) {
								List<ColumnDefinition> columns2 = DBUtils.getColumnDefinitions(database.getName(),
										tableName);
								request.setAttribute(tableName + "_columns", columns2);
							}
							break;
						}
					}
				}
			}

			if (StringUtils.isNotEmpty(tableCombination.getDatasetIds())) {
				DataSet dataSet = null;
				List<DataSet> dataSetList = new ArrayList<DataSet>();
				StringTokenizer token = new StringTokenizer(tableCombination.getDatasetIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						dataSet = dataSetService.getDataSet(x);
						if (dataSet != null && dataSet.getSelectSegments() != null) {
							dataSetList.add(dataSet);
							List<SelectSegment> selectSegments = dataSet.getSelectSegments();
							if (selectSegments != null && !selectSegments.isEmpty()) {
								List<ColumnDefinition> columns2 = new ArrayList<ColumnDefinition>();
								for (SelectSegment seg : selectSegments) {
									ColumnDefinition col = new ColumnDefinition();
									col.setColumnName(seg.getColumnName());
									col.setColumnLabel(seg.getColumnLabel());
									col.setTitle(seg.getTitle());
									columns2.add(col);
								}

								request.setAttribute(dataSet.getId() + "_columns", columns2);
							}
						}
					}
				}
				request.setAttribute("dataSetList", dataSetList);
			}

		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableCombination.rule");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableCombination/rule", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TableCombination tableCombination = new TableCombination();
		Tools.populate(tableCombination, params);

		tableCombination.setName(request.getParameter("name"));
		tableCombination.setTitle(request.getParameter("title"));
		tableCombination.setType(request.getParameter("type"));
		tableCombination.setDatabaseIds(request.getParameter("databaseIds"));
		tableCombination.setTemplateTableName(request.getParameter("templateTableName"));
		tableCombination.setPrimaryKey(request.getParameter("primaryKey"));
		tableCombination.setUniquenessKey(request.getParameter("uniquenessKey"));
		tableCombination.setSyncColumns(request.getParameter("syncColumns"));
		tableCombination.setSyncTableNames(request.getParameter("syncTableNames"));
		tableCombination.setDatasetIds(request.getParameter("datasetIds"));
		tableCombination.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
		tableCombination.setTargetTableName(request.getParameter("targetTableName"));
		tableCombination.setTargetTableStruct(request.getParameter("targetTableStruct"));
		tableCombination.setCurrentUserFlag(request.getParameter("currentUserFlag"));
		tableCombination.setCreateTableFlag(request.getParameter("createTableFlag"));
		tableCombination.setDeleteFetch(request.getParameter("deleteFetch"));
		tableCombination.setSortNo(RequestUtils.getInt(request, "sortNo"));
		tableCombination.setLocked(RequestUtils.getInt(request, "locked"));
		tableCombination.setCreateBy(actorId);

		if (StringUtils.equals(tableCombination.getTemplateTableName(), tableCombination.getTargetTableName())) {
			// throw new RuntimeException("目标表不能是模板表！");
		}

		tableCombinationService.save(tableCombination);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveTableCombination")
	public byte[] saveTableCombination(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableCombination tableCombination = new TableCombination();
		try {
			Tools.populate(tableCombination, params);
			tableCombination.setName(request.getParameter("name"));
			tableCombination.setTitle(request.getParameter("title"));
			tableCombination.setType(request.getParameter("type"));
			tableCombination.setDatabaseIds(request.getParameter("databaseIds"));
			tableCombination.setTemplateTableName(request.getParameter("templateTableName"));
			tableCombination.setPrimaryKey(request.getParameter("primaryKey"));
			tableCombination.setUniquenessKey(request.getParameter("uniquenessKey"));
			tableCombination.setSyncColumns(request.getParameter("syncColumns"));
			tableCombination.setSyncTableNames(request.getParameter("syncTableNames"));
			tableCombination.setDatasetIds(request.getParameter("datasetIds"));
			tableCombination.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			tableCombination.setTargetTableName(request.getParameter("targetTableName"));
			tableCombination.setTargetTableStruct(request.getParameter("targetTableStruct"));
			tableCombination.setCurrentUserFlag(request.getParameter("currentUserFlag"));
			tableCombination.setCreateTableFlag(request.getParameter("createTableFlag"));
			tableCombination.setScheduleFlag(request.getParameter("scheduleFlag"));
			tableCombination.setDeleteFetch(request.getParameter("deleteFetch"));
			tableCombination.setSortNo(RequestUtils.getInt(request, "sortNo"));
			tableCombination.setLocked(RequestUtils.getInt(request, "locked"));
			tableCombination.setCreateBy(actorId);

			if (StringUtils.equals(tableCombination.getTemplateTableName(), tableCombination.getTargetTableName())) {
				// return ResponseUtils.responseJsonResult(false, "目标表不能是模板表！");
			}

			this.tableCombinationService.save(tableCombination);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveAs")
	public byte[] saveAs(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableCombination tableCombination = new TableCombination();
		try {
			Tools.populate(tableCombination, params);
			tableCombination.setName(request.getParameter("name"));
			tableCombination.setTitle(request.getParameter("title"));
			tableCombination.setType(request.getParameter("type"));
			tableCombination.setDatabaseIds(request.getParameter("databaseIds"));
			tableCombination.setTemplateTableName(request.getParameter("templateTableName"));
			tableCombination.setPrimaryKey(request.getParameter("primaryKey"));
			tableCombination.setUniquenessKey(request.getParameter("uniquenessKey"));
			tableCombination.setSyncColumns(request.getParameter("syncColumns"));
			tableCombination.setSyncTableNames(request.getParameter("syncTableNames"));
			tableCombination.setDatasetIds(request.getParameter("datasetIds"));
			tableCombination.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			tableCombination.setTargetTableName(request.getParameter("targetTableName"));
			tableCombination.setTargetTableStruct(request.getParameter("targetTableStruct"));
			tableCombination.setCurrentUserFlag(request.getParameter("currentUserFlag"));
			tableCombination.setCreateTableFlag(request.getParameter("createTableFlag"));
			tableCombination.setScheduleFlag(request.getParameter("scheduleFlag"));
			tableCombination.setDeleteFetch(request.getParameter("deleteFetch"));
			tableCombination.setSortNo(RequestUtils.getInt(request, "sortNo"));
			tableCombination.setLocked(RequestUtils.getInt(request, "locked"));
			tableCombination.setCreateBy(actorId);

			if (StringUtils.equals(tableCombination.getTemplateTableName(), tableCombination.getTargetTableName())) {
				// return ResponseUtils.responseJsonResult(false, "目标表不能是模板表！");
			}
			this.tableCombinationService.saveAs(tableCombination);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveTableCombinationRules")
	public byte[] saveTableCombinationRules(HttpServletRequest request) {
		long tableCombinationId = RequestUtils.getLong(request, "tableCombinationId");
		TableCombination tableCombination = null;
		try {
			tableCombination = tableCombinationService.getTableCombination(tableCombinationId);
			if (tableCombination != null) {
				tableCombination.getRules().clear();
				tableCombination.getColumns().clear();
				Map<String, String> titleMap = new HashMap<String, String>();
				List<String> tableNames = StringTools.split(tableCombination.getSyncTableNames());
				for (String tableName : tableNames) {
					logger.debug("process table:" + tableName);
					TableCombinationRule rule = new TableCombinationRule();
					rule.setTargetTableName(tableName);
					String sqlCondition = request.getParameter(tableName + "_sqlCondition");
					if (StringUtils.isNotEmpty(sqlCondition)) {
						rule.setSqlCondition(sqlCondition);
					}
					rule.setTargetTablePrimaryKey(request.getParameter(tableName + "_targetTablePrimaryKey"));
					rule.setUniquenessColumn(request.getParameter(tableName + "_uniquenessColumn"));
					tableCombination.addRule(rule);
					List<String> syncColumns = StringTools.split(tableCombination.getSyncColumns());
					for (String syncColumn : syncColumns) {
						TableCombinationColumn column = new TableCombinationColumn();
						column.setSourceColumnName(syncColumn);
						column.setTargetTableName(tableName);
						column.setTargetColumnName(request.getParameter(tableName + "_" + syncColumn));
						column.setInitValue(request.getParameter(tableName + "_" + syncColumn + "_initValue"));
						tableCombination.addColumn(column);
					}
				}

				if (StringUtils.isNotEmpty(tableCombination.getDatasetIds())) {
					DataSet dataSet = null;
					StringTokenizer token = new StringTokenizer(tableCombination.getDatasetIds(), ",");
					while (token.hasMoreTokens()) {
						String x = token.nextToken();
						if (StringUtils.isNotEmpty(x)) {
							dataSet = dataSetService.getDataSet(x);
							if (dataSet != null) {
								List<String> syncColumns = StringTools.split(tableCombination.getSyncColumns());
								for (String syncColumn : syncColumns) {
									String value = request.getParameter(dataSet.getId() + "_" + syncColumn);
									logger.debug(syncColumn + "\t" + value);
									if (StringUtils.isNotEmpty(value) && !StringUtils.equals(value, "null")) {
										TableCombinationColumn column = new TableCombinationColumn();
										column.setSourceColumnName(syncColumn);
										column.setTargetColumnLabel(value);
										column.setDatasetId(dataSet.getId());
										tableCombination.addColumn(column);
									}
								}
							}
						}
					}
				}

				this.tableCombinationService.saveRules(tableCombination);
				tableCombination.getRules().clear();
				tableCombination.getColumns().clear();

				List<String> syncColumns = StringTools.split(tableCombination.getSyncColumns());
				for (String syncColumn : syncColumns) {
					if (StringUtils.isNotEmpty(request.getParameter("title_" + syncColumn))) {
						titleMap.put(syncColumn, request.getParameter("title_" + syncColumn));
					} else {
						titleMap.put(syncColumn, syncColumn);
					}
				}

				this.updateMetaData(tableCombination, titleMap);

				return ResponseUtils.responseJsonResult(true);
			}
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
	public void setTableCombinationService(TableCombinationService tableCombinationService) {
		this.tableCombinationService = tableCombinationService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	protected void updateMetaData(TableCombination tableCombination, Map<String, String> titleMap) {
		String systemName = null;
		Database targetDatabase = null;
		try {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
			if (tableCombination.getTargetDatabaseId() > 0) {
				targetDatabase = databaseService.getDatabaseById(tableCombination.getTargetDatabaseId());
				if (targetDatabase != null) {
					systemName = targetDatabase.getName();
				}
			}

			List<TableModel> rows = new ArrayList<TableModel>();

			List<String> syncColumns = StringTools.splitLowerCase(tableCombination.getSyncColumns());
			if (syncColumns != null && !syncColumns.isEmpty()
					&& StringUtils.isNotEmpty(tableCombination.getTargetTableName())) {
				String templateTableName = tableCombination.getTemplateTableName();
				List<Long> databaseIds = StringTools.splitToLong(tableCombination.getDatabaseIds());
				if (databaseIds != null && !databaseIds.isEmpty()) {
					Database db = databaseService.getDatabaseById(databaseIds.get(0));
					List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(db.getName(), templateTableName);
					if (columns != null && !columns.isEmpty()) {
						int sort = 1;
						for (ColumnDefinition column : columns) {
							if (syncColumns.contains(column.getColumnName().trim().toLowerCase())) {
								TableModel tableModel3 = new TableModel();
								tableModel3.setTableName("interface");

								ColumnModel cm30 = new ColumnModel();
								cm30.setColumnName("index_id");
								cm30.setJavaType("String");
								cm30.setValue("1001");
								tableModel3.addColumn(cm30);

								ColumnModel cm31 = new ColumnModel();
								cm31.setColumnName("frmtype");
								cm31.setJavaType("String");
								cm31.setValue(tableCombination.getTargetTableName());
								tableModel3.addColumn(cm31);

								ColumnModel cm32 = new ColumnModel();
								cm32.setColumnName("listId");
								cm32.setJavaType("String");
								cm32.setValue(tableCombination.getTargetTableName() + "_"
										+ column.getColumnName().toLowerCase());
								tableModel3.addColumn(cm32);

								ColumnModel cm33 = new ColumnModel();
								cm33.setColumnName("issystem");
								cm33.setJavaType("String");
								cm33.setValue("1");
								tableModel3.addColumn(cm33);

								ColumnModel cm34 = new ColumnModel();
								cm34.setColumnName("fname");
								cm34.setJavaType("String");
								cm34.setValue(titleMap.get(column.getColumnName().toLowerCase()));
								tableModel3.addColumn(cm34);

								ColumnModel cm35 = new ColumnModel();
								cm35.setColumnName("dname");
								cm35.setJavaType("String");
								cm35.setValue(column.getColumnName().toLowerCase());
								tableModel3.addColumn(cm35);

								ColumnModel cm36 = new ColumnModel();
								cm36.setColumnName("dtype");
								cm36.setJavaType("String");

								ColumnModel cm37 = new ColumnModel();
								cm37.setColumnName("strlen");
								cm37.setJavaType("Integer");

								ColumnModel cm38 = new ColumnModel();
								cm38.setColumnName("intype");
								cm38.setJavaType("String");

								if ("Date".equals(column.getJavaType())) {
									cm36.setValue("datetime");
									cm37.setValue(-1);
									cm38.setValue("dp");
								} else if ("Integer".equals(column.getJavaType())) {
									cm36.setValue("i4");
									cm37.setValue(-1);
									cm38.setValue("edt");
								} else if ("Long".equals(column.getJavaType())) {
									cm36.setValue("r8");
									cm37.setValue(-1);
									cm38.setValue("edt");
								} else if ("Double".equals(column.getJavaType())) {
									cm36.setValue("r8");
									cm37.setValue(-1);
									cm38.setValue("edt");
								} else {
									cm36.setValue("string");
									cm37.setValue(500);
									cm38.setValue("edt");
								}
								tableModel3.addColumn(cm36);
								tableModel3.addColumn(cm37);
								tableModel3.addColumn(cm38);

								ColumnModel cm39 = new ColumnModel();
								cm39.setColumnName("listno");
								cm39.setJavaType("Integer");
								cm39.setValue(sort++);
								tableModel3.addColumn(cm39);

								ColumnModel cm40 = new ColumnModel();
								cm40.setColumnName("isListShow");
								cm40.setJavaType("String");
								cm40.setValue("1");
								tableModel3.addColumn(cm40);

								rows.add(tableModel3);

								TableModel tableModel4 = new TableModel();
								tableModel4.setTableName("cell_data_field");

								ColumnModel cm42 = new ColumnModel();
								cm42.setColumnName("tableid");
								cm42.setJavaType("String");
								cm42.setValue(tableCombination.getTargetTableName());
								tableModel4.addColumn(cm42);

								ColumnModel cm41 = new ColumnModel();
								cm41.setColumnName("id");
								cm41.setJavaType("String");
								cm41.setValue(tableCombination.getTargetTableName() + "_"
										+ column.getColumnName().toLowerCase());
								tableModel4.addColumn(cm41);

								ColumnModel cm43 = new ColumnModel();
								cm43.setColumnName("fieldname");
								cm43.setJavaType("String");
								cm43.setValue(column.getColumnName().toLowerCase());
								tableModel4.addColumn(cm43);

								ColumnModel cm44 = new ColumnModel();
								cm44.setColumnName("userid");
								cm44.setJavaType("String");
								cm44.setValue("system");
								tableModel4.addColumn(cm44);

								ColumnModel cm45 = new ColumnModel();
								cm45.setColumnName("maxuser");
								cm45.setJavaType("Integer");
								cm45.setValue(sort);
								tableModel4.addColumn(cm45);

								ColumnModel cm46 = new ColumnModel();
								cm46.setColumnName("ctime");
								cm46.setJavaType("Date");
								cm46.setValue(new Date());
								tableModel4.addColumn(cm46);

								rows.add(tableModel4);
							}
						}
					}
				}
			}

			if (StringUtils.isNotEmpty(tableCombination.getTargetTableName())) {
				TableModel tableModel1 = new TableModel();
				tableModel1.setTableName("cell_data_table");

				ColumnModel idColumn = new ColumnModel();
				idColumn.setColumnName("id");
				idColumn.setJavaType("String");
				idColumn.setValue(tableCombination.getTargetTableName());
				tableModel1.addColumn(idColumn);

				ColumnModel cm1 = new ColumnModel();
				cm1.setColumnName("tablename");
				cm1.setJavaType("String");
				cm1.setValue(tableCombination.getTargetTableName());
				tableModel1.addColumn(cm1);

				ColumnModel cm2 = new ColumnModel();
				cm2.setColumnName("index_id");
				cm2.setJavaType("Integer");
				cm2.setValue(1001);// 增加的分类编号
				tableModel1.addColumn(cm2);

				ColumnModel cm3 = new ColumnModel();
				cm3.setColumnName("name");
				cm3.setJavaType("String");
				cm3.setValue(tableCombination.getTitle());
				tableModel1.addColumn(cm3);

				ColumnModel cm4 = new ColumnModel();
				cm4.setColumnName("addtype");
				cm4.setJavaType("String");
				cm4.setValue("3");
				tableModel1.addColumn(cm4);

				ColumnModel cm5 = new ColumnModel();
				cm5.setColumnName("userid");
				cm5.setJavaType("String");
				cm5.setValue("system");
				tableModel1.addColumn(cm5);

				ColumnModel cm6 = new ColumnModel();
				cm6.setColumnName("ctime");
				cm6.setJavaType("Date");
				cm6.setValue(new Date());
				tableModel1.addColumn(cm6);

				ColumnModel cm7 = new ColumnModel();
				cm7.setColumnName("content");
				cm7.setJavaType("String");
				cm7.setValue(tableCombination.getTitle());
				tableModel1.addColumn(cm7);

				ColumnModel cm8 = new ColumnModel();
				cm8.setColumnName("issubtable");
				cm8.setJavaType("String");
				cm8.setValue("0");
				tableModel1.addColumn(cm8);

				rows.add(tableModel1);

				TableModel tableModel2 = new TableModel();
				tableModel2.setTableName("cell_data_table");

				ColumnModel cm21 = new ColumnModel();
				cm21.setColumnName("id");
				cm21.setJavaType("String");
				cm21.setValue(tableCombination.getTargetTableName());
				tableModel2.addColumn(cm21);

				TableModel tableModel5 = new TableModel();
				tableModel5.setTableName("interface");

				ColumnModel cm51 = new ColumnModel();
				cm51.setColumnName("frmtype");
				cm51.setJavaType("String");
				cm51.setValue(tableCombination.getTargetTableName());
				tableModel5.addColumn(cm51);

				TableModel tableModel6 = new TableModel();
				tableModel6.setTableName("cell_data_field");

				ColumnModel cm61 = new ColumnModel();
				cm61.setColumnName("tableid");
				cm61.setJavaType("String");
				cm61.setValue(tableCombination.getTargetTableName());
				tableModel6.addColumn(cm61);

				try {
					Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
					tableDataService.deleteTableData(tableModel2);
					tableDataService.deleteTableData(tableModel5);
					tableDataService.deleteTableData(tableModel6);
					tableDataService.insertAllTableData(rows);
				} catch (Exception ex) {
					logger.error(ex);
				}

				if (StringUtils.isNotEmpty(systemName)
						&& !StringUtils.equalsIgnoreCase(systemName, Environment.DEFAULT_SYSTEM_NAME)) {
					try {
						Environment.setCurrentSystemName(systemName);
						tableDataService.deleteTableData(tableModel2);
						tableDataService.deleteTableData(tableModel5);
						tableDataService.deleteTableData(tableModel6);
						tableDataService.insertAllTableData(rows);
					} catch (Exception ex) {
						logger.error(ex);
					}
				}

				List<Long> databaseIds = StringTools.splitToLong(tableCombination.getDatabaseIds());
				if (databaseIds != null && !databaseIds.isEmpty()) {
					for (Long databaseId : databaseIds) {
						try {
							targetDatabase = databaseService.getDatabaseById(databaseId);
							Environment.setCurrentSystemName(targetDatabase.getName());
							tableDataService.deleteTableData(tableModel2);
							tableDataService.deleteTableData(tableModel5);
							tableDataService.deleteTableData(tableModel6);
							tableDataService.insertAllTableData(rows);
						} catch (Exception ex) {
							logger.error(ex);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		TableCombination tableCombination = tableCombinationService
				.getTableCombination(RequestUtils.getLong(request, "id"));
		request.setAttribute("tableCombination", tableCombination);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("tableCombination.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/tableCombination/view");
	}

}
