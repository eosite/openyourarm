package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
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
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetSynthetic;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.query.DataSetSyntheticQuery;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.DataSetSyntheticService;
import com.glaf.datamgr.service.ExecutionLogService;
import com.glaf.datamgr.task.DataSetSyntheticTask;
import com.glaf.datamgr.util.ExceptionUtils;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/dataSetSynthetic")
@RequestMapping("/sys/dataSetSynthetic")
public class DataSetSyntheticController {
	protected static final Log logger = LogFactory.getLog(DataSetSyntheticController.class);

	protected IDatabaseService databaseService;

	protected DataSetService dataSetService;

	protected DataSetSyntheticService dataSetSyntheticService;

	protected ExecutionLogService executionLogService;

	public DataSetSyntheticController() {

	}

	@RequestMapping("/chooseColumns")
	public ModelAndView chooseColumns(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String iType = request.getParameter("iType");
		String sourceDatabaseIds = request.getParameter("sourceDatabaseIds");
		List<Long> databaseIds = StringTools.splitToLong(sourceDatabaseIds);
		if (databaseIds != null && !databaseIds.isEmpty()) {
			String sourceDataSetId = request.getParameter("sourceDataSetId");
			logger.debug("sourceDataSetId:" + sourceDataSetId);
			for (Long databaseId : databaseIds) {
				Database database = databaseService.getDatabaseById(databaseId);
				List<ColumnDefinition> columns = null;
				Date now = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int week = calendar.get(Calendar.WEEK_OF_YEAR);
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("_date_", now);
				parameter.put("_now_", now);
				parameter.put("_year_", year);
				parameter.put("_month_", month);
				parameter.put("_week_", week);
				parameter.put("_day_", day);
				parameter.put("_nowYearMonth_", DateUtils.getNowYearMonth());
				parameter.put("_nowYearMonthDay_", DateUtils.getNowYearMonthDay());

				DataSetBuilder builder = new DataSetBuilder();
				Query query = builder.buildQuery(database.getName(), sourceDataSetId, parameter);
				String sql = query.toSql();
				QueryHelper helper = new QueryHelper();
				columns = helper.getColumnDefinitions(database.getName(), sql, parameter);

				DataSet dataSet = dataSetService.getDataSet(sourceDataSetId);
				List<SelectSegment> selectSegments = dataSet.getSelectSegments();
				Map<String, String> columnMap = new HashMap<String, String>();
				for (SelectSegment seg : selectSegments) {
					if (StringUtils.isNotEmpty(seg.getMapping())) {
						columnMap.put(seg.getColumnLabel(), seg.getMapping().toLowerCase());
					} else {
						columnMap.put(seg.getColumnLabel(), seg.getColumnName().toLowerCase());
					}
				}

				if (columns != null && !columns.isEmpty()) {
					for (ColumnDefinition c : columns) {
						if (columnMap.get(c.getColumnLabel()) != null) {
							c.setColumnName(columnMap.get(c.getColumnLabel()).toLowerCase());
						} else {
							if (columnMap.get(c.getColumnName()) != null) {
								c.setColumnName(columnMap.get(c.getColumnName()).toLowerCase());
							}
						}
					}
					request.setAttribute("unselected", columns);
				}

				DataSetSynthetic dataSetSynthetic = dataSetSyntheticService
						.getDataSetSynthetic(RequestUtils.getLong(request, "dataSetSyntheticId"));
				if (dataSetSynthetic != null) {
					request.setAttribute("dataSetSynthetic", dataSetSynthetic);
				}

				List<String> selected = new ArrayList<String>();
				if (StringUtils.isNotEmpty(request.getParameter("selected"))) {
					selected = StringTools.split(request.getParameter("selected"));
					request.setAttribute("selected", selected);
				}
				if (dataSetSynthetic != null) {
					List<ColumnDefinition> unselected = new ArrayList<ColumnDefinition>();
					String keys = dataSetSynthetic.getSyncColumns();
					if (StringUtils.equals("AGGR", iType)) {
						keys = dataSetSynthetic.getAggregationKeys();
					}
					if (StringUtils.isNotEmpty(keys)) {
						List<String> cols = StringTools.split(keys, ",");
						if (columns != null && !columns.isEmpty()) {
							for (ColumnDefinition col : columns) {
								if (cols.contains(col.getColumnName())
										|| cols.contains(col.getColumnName().toLowerCase())
										|| cols.contains(col.getColumnName().toUpperCase())) {
									selected.add(col.getColumnName());
								} else {
									unselected.add(col);
								}
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

		String x_view = ViewProperties.getString("dataSetSynthetic.chooseColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/datasetSynthetic/chooseColumns", modelMap);
	}

	@RequestMapping("/chooseDatabases")
	public ModelAndView chooseDatabases(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DataSetSynthetic dataSetSynthetic = dataSetSyntheticService
				.getDataSetSynthetic(RequestUtils.getLong(request, "dataSetSyntheticId"));
		if (dataSetSynthetic != null) {
			request.setAttribute("dataSetSynthetic", dataSetSynthetic);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<Long> selected = new ArrayList<Long>();
			List<Database> unselected = new ArrayList<Database>();
			if (dataSetSynthetic != null && StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(dataSetSynthetic.getSourceDatabaseIds());
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

		String x_view = ViewProperties.getString("dataSetSynthetic.chooseDatabases");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/datasetSynthetic/chooseDatabases", modelMap);
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
					DataSetSynthetic dataSetSynthetic = dataSetSyntheticService.getDataSetSynthetic(Long.valueOf(x));
					if (dataSetSynthetic != null
							&& (StringUtils.equals(dataSetSynthetic.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						dataSetSynthetic.setDeleteFlag(1);
						dataSetSyntheticService.save(dataSetSynthetic);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataSetSynthetic dataSetSynthetic = dataSetSyntheticService.getDataSetSynthetic(Long.valueOf(id));
			if (dataSetSynthetic != null
					&& (StringUtils.equals(dataSetSynthetic.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				dataSetSynthetic.setDeleteFlag(1);
				dataSetSyntheticService.save(dataSetSynthetic);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/dropAllTable")
	public byte[] dropAllTable(HttpServletRequest request, ModelMap modelMap) {
		boolean result = false;
		DataSetSyntheticQuery query = new DataSetSyntheticQuery();
		query.locked(0);
		List<DataSetSynthetic> rows = dataSetSyntheticService.list(query);
		if (rows != null && !rows.isEmpty()) {
			TableFactory.clear();
			for (DataSetSynthetic synthetic : rows) {
				if (synthetic != null && StringUtils.isNotEmpty(synthetic.getTargetTableName())) {
					try {
						Database targetDatabase = databaseService.getDatabaseById(synthetic.getTargetDatabaseId());
						if (targetDatabase != null) {
							if (StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "etl_")
									|| StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "sync_")
									|| StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "tmp")
									|| StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "useradd_")
									|| StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(),
											"tree_table_")) {
								if (DBUtils.tableExists(targetDatabase.getName(), synthetic.getTargetTableName())) {
									executionLogService.deleteTodayExecutionLogs("dataset_synthetic",
											String.valueOf(synthetic.getId()));
									String ddlStatements = " drop table " + synthetic.getTargetTableName();
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
		}
		return ResponseUtils.responseResult(result);
	}

	@ResponseBody
	@RequestMapping("/dropTable")
	public byte[] dropTable(HttpServletRequest request, ModelMap modelMap) {
		long syntheticId = RequestUtils.getLong(request, "syntheticId");
		DataSetSynthetic synthetic = dataSetSyntheticService.getDataSetSynthetic(syntheticId);
		if (synthetic != null && StringUtils.isNotEmpty(synthetic.getTargetTableName())) {
			try {
				Database targetDatabase = databaseService.getDatabaseById(synthetic.getTargetDatabaseId());
				if (targetDatabase != null) {
					if (StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "etl_")
							|| StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "sync_")
							|| StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "tmp")
							|| StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "useradd_")
							|| StringUtils.startsWithIgnoreCase(synthetic.getTargetTableName(), "tree_table_")) {
						if (DBUtils.tableExists(targetDatabase.getName(), synthetic.getTargetTableName())) {
							executionLogService.deleteTodayExecutionLogs("dataset_synthetic",
									String.valueOf(synthetic.getId()));
							String ddlStatements = " drop table " + synthetic.getTargetTableName();
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

		DataSetSynthetic dataSetSynthetic = dataSetSyntheticService
				.getDataSetSynthetic(RequestUtils.getLong(request, "id"));
		if (dataSetSynthetic != null) {
			if (StringUtils.isNotEmpty(dataSetSynthetic.getSourceDataSetId())) {
				DataSet dataSet = dataSetService.getDataSet(dataSetSynthetic.getSourceDataSetId());
				request.setAttribute("datasetName", dataSet.getName());
			}
			request.setAttribute("dataSetSynthetic", dataSetSynthetic);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			if (dataSetSynthetic != null && StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(dataSetSynthetic.getSourceDatabaseIds());
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

		String x_view = ViewProperties.getString("dataSetSynthetic.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/datasetSynthetic/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request, ModelMap modelMap) {
		long dataSetSyntheticId = RequestUtils.getLong(request, "dataSetSyntheticId");
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		DataSetSynthetic dataSetSynthetic = dataSetSyntheticService.getDataSetSynthetic(dataSetSyntheticId);
		if (dataSetSynthetic != null && StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
			if (StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
				dataSetSynthetic.setSyncTime(new Date());
				StringTokenizer token = new StringTokenizer(dataSetSynthetic.getSourceDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						database = null;
						jobNo = "dataset_synthetic_" + dataSetSynthetic.getId() + "_" + x + "_"
								+ dataSetSynthetic.getTargetDatabaseId() + "_" + runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								DataSetSyntheticTask task = new DataSetSyntheticTask(database.getId(),
										dataSetSynthetic.getTargetDatabaseId(), dataSetSynthetic.getId(), jobNo);
								if (task.execute()) {
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行成功。\n");
								} else {
									errorCount++;
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行失败。\n");
									StringBuffer sb = ExceptionUtils
											.getMsgBuffer("dataset_synthetic_" + dataSetSynthetic.getId());
									buffer.append(sb != null ? sb.toString() : "");
								}
							}
						} catch (Exception ex) {
							errorCount++;
						} finally {
							ExceptionUtils.clear("dataset_synthetic_" + dataSetSynthetic.getId());
						}
					}
				}
				if (errorCount == 0) {
					dataSetSynthetic.setSyncStatus(1);
				} else {
					dataSetSynthetic.setSyncStatus(-1);
				}
				dataSetSyntheticService.updateDataSetSyntheticStatus(dataSetSynthetic);
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
		DataSetSyntheticQuery query = new DataSetSyntheticQuery();
		query.locked(0);
		query.deleteFlag(0);
		List<DataSetSynthetic> rows = dataSetSyntheticService.list(query);
		if (rows != null && !rows.isEmpty()) {
			for (DataSetSynthetic dataSetSynthetic : rows) {
				if (dataSetSynthetic != null && StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
					if (StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
						perErrorCount = 0;
						dataSetSynthetic.setSyncTime(new Date());
						buffer.append("\n").append(dataSetSynthetic.getTitle()).append("执行结果：\n");
						StringTokenizer token = new StringTokenizer(dataSetSynthetic.getSourceDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								database = null;
								jobNo = "dataset_synthetic_" + dataSetSynthetic.getId() + "_" + x + "_"
										+ dataSetSynthetic.getTargetDatabaseId() + "_" + runDay;
								try {
									database = databaseService.getDatabaseById(Long.parseLong(x));
									if (database != null) {
										DataSetSyntheticTask task = new DataSetSyntheticTask(database.getId(),
												dataSetSynthetic.getTargetDatabaseId(), dataSetSynthetic.getId(),
												jobNo);
										if (task.execute()) {
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行成功。\n");
										} else {
											errorCount++;
											perErrorCount++;
											buffer.append(database.getTitle()).append("[").append(database.getMapping())
													.append("]执行失败。\n");
											StringBuffer sb = ExceptionUtils
													.getMsgBuffer("dataset_synthetic_" + dataSetSynthetic.getId());
											buffer.append(sb != null ? sb.toString() : "");
										}
									}
								} catch (Exception ex) {
									errorCount++;
									perErrorCount++;
								} finally {
									ExceptionUtils.clear("dataset_synthetic_" + dataSetSynthetic.getId());
								}
							}
						}
						if (perErrorCount == 0) {
							dataSetSynthetic.setSyncStatus(1);
						} else {
							dataSetSynthetic.setSyncStatus(-1);
						}
						dataSetSyntheticService.updateDataSetSyntheticStatus(dataSetSynthetic);
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
		DataSetSyntheticQuery query = new DataSetSyntheticQuery();
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
		int total = dataSetSyntheticService.getDataSetSyntheticCountByQueryCriteria(query);
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

			List<DataSetSynthetic> list = dataSetSyntheticService.getDataSetSyntheticsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataSetSynthetic dataSetSynthetic : list) {
					JSONObject rowJSON = dataSetSynthetic.toJsonObject();
					rowJSON.put("id", dataSetSynthetic.getId());
					rowJSON.put("rowId", dataSetSynthetic.getId());
					rowJSON.put("dataSetSyntheticId", dataSetSynthetic.getId());
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

		return new ModelAndView("/datamgr/datasetSynthetic/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataSetSynthetic.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/datasetSynthetic/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataSetSynthetic dataSetSynthetic = new DataSetSynthetic();
		Tools.populate(dataSetSynthetic, params);

		dataSetSynthetic.setName(request.getParameter("name"));
		dataSetSynthetic.setTitle(request.getParameter("title"));
		dataSetSynthetic.setType(request.getParameter("type"));
		dataSetSynthetic.setSourceDatabaseIds(RequestUtils.getString(request, "sourceDatabaseIds"));
		dataSetSynthetic.setSourceDataSetId(request.getParameter("sourceDataSetId"));
		dataSetSynthetic.setAggregationKeys(request.getParameter("aggregationKeys"));
		dataSetSynthetic.setSyncColumns(request.getParameter("syncColumns"));
		dataSetSynthetic.setTargetTableName(request.getParameter("targetTableName"));
		dataSetSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
		dataSetSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
		dataSetSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
		dataSetSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
		dataSetSynthetic.setInsertOnly(request.getParameter("insertOnly"));
		dataSetSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
		dataSetSynthetic.setCreateBy(actorId);
		dataSetSyntheticService.save(dataSetSynthetic);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDataSetSynthetic")
	public byte[] saveDataSetSynthetic(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetSynthetic dataSetSynthetic = new DataSetSynthetic();
		try {
			Tools.populate(dataSetSynthetic, params);
			dataSetSynthetic.setName(request.getParameter("name"));
			dataSetSynthetic.setTitle(request.getParameter("title"));
			dataSetSynthetic.setType(request.getParameter("type"));
			dataSetSynthetic.setSourceDatabaseIds(RequestUtils.getString(request, "sourceDatabaseIds"));
			dataSetSynthetic.setSourceDataSetId(request.getParameter("sourceDataSetId"));
			dataSetSynthetic.setAggregationKeys(request.getParameter("aggregationKeys"));
			dataSetSynthetic.setSyncColumns(request.getParameter("syncColumns"));
			dataSetSynthetic.setTargetTableName(request.getParameter("targetTableName"));
			dataSetSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			dataSetSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
			dataSetSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
			dataSetSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
			dataSetSynthetic.setInsertOnly(request.getParameter("insertOnly"));
			dataSetSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
			dataSetSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
			dataSetSynthetic.setCreateBy(actorId);
			this.dataSetSyntheticService.save(dataSetSynthetic);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DataSetSynthetic saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DataSetSynthetic dataSetSynthetic = new DataSetSynthetic();
		try {
			Tools.populate(dataSetSynthetic, model);
			dataSetSynthetic.setName(ParamUtils.getString(model, "name"));
			dataSetSynthetic.setTitle(ParamUtils.getString(model, "title"));
			dataSetSynthetic.setType(ParamUtils.getString(model, "type"));
			dataSetSynthetic.setSourceDatabaseIds(ParamUtils.getString(model, "sourceDatabaseIds"));
			dataSetSynthetic.setSourceDataSetId(ParamUtils.getString(model, "sourceDataSetId"));
			dataSetSynthetic.setAggregationKeys(request.getParameter("aggregationKeys"));
			dataSetSynthetic.setSyncColumns(request.getParameter("syncColumns"));
			dataSetSynthetic.setTargetTableName(ParamUtils.getString(model, "targetTableName"));
			dataSetSynthetic.setTargetDatabaseId(ParamUtils.getLong(model, "targetDatabaseId"));
			dataSetSynthetic.setScheduleFlag(ParamUtils.getString(model, "scheduleFlag"));
			dataSetSynthetic.setCreateTableFlag(ParamUtils.getString(model, "createTableFlag"));
			dataSetSynthetic.setDeleteFetch(ParamUtils.getString(model, "deleteFetch"));
			dataSetSynthetic.setInsertOnly(ParamUtils.getString(model, "insertOnly"));
			dataSetSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
			dataSetSynthetic.setLocked(ParamUtils.getInt(model, "locked"));
			dataSetSynthetic.setCreateBy(actorId);
			this.dataSetSyntheticService.save(dataSetSynthetic);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return dataSetSynthetic;
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
	public void setDataSetSyntheticService(DataSetSyntheticService dataSetSyntheticService) {
		this.dataSetSyntheticService = dataSetSyntheticService;
	}

	@javax.annotation.Resource
	public void setExecutionLogService(ExecutionLogService executionLogService) {
		this.executionLogService = executionLogService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataSetSynthetic dataSetSynthetic = dataSetSyntheticService
				.getDataSetSynthetic(RequestUtils.getLong(request, "id"));

		Tools.populate(dataSetSynthetic, params);

		dataSetSynthetic.setName(request.getParameter("name"));
		dataSetSynthetic.setTitle(request.getParameter("title"));
		dataSetSynthetic.setType(request.getParameter("type"));
		dataSetSynthetic.setSourceDatabaseIds(RequestUtils.getString(request, "sourceDatabaseIds"));
		dataSetSynthetic.setSourceDataSetId(request.getParameter("sourceDataSetId"));
		dataSetSynthetic.setAggregationKeys(request.getParameter("aggregationKeys"));
		dataSetSynthetic.setSyncColumns(request.getParameter("syncColumns"));
		dataSetSynthetic.setTargetTableName(request.getParameter("targetTableName"));
		dataSetSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
		dataSetSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
		dataSetSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
		dataSetSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
		dataSetSynthetic.setInsertOnly(request.getParameter("insertOnly"));
		dataSetSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
		dataSetSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
		dataSetSynthetic.setUpdateBy(user.getActorId());
		dataSetSyntheticService.save(dataSetSynthetic);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		DataSetSynthetic dataSetSynthetic = dataSetSyntheticService
				.getDataSetSynthetic(RequestUtils.getLong(request, "id"));
		request.setAttribute("dataSetSynthetic", dataSetSynthetic);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataSetSynthetic.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/datasetSynthetic/view");
	}

}
