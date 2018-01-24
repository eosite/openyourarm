package com.glaf.matrix.aggregate.web.springmvc;

import java.io.IOException;
import java.sql.Connection;
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
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.matrix.aggregate.bean.SqlToAggregateTableBean;
import com.glaf.matrix.aggregate.domain.TableAggregateApp;
import com.glaf.matrix.aggregate.domain.TableAggregateHistory;
import com.glaf.matrix.aggregate.domain.TableAggregateItem;
import com.glaf.matrix.aggregate.query.TableAggregateAppQuery;
import com.glaf.matrix.aggregate.service.TableAggregateAppService;
import com.glaf.matrix.aggregate.service.TableAggregateHistoryService;
import com.glaf.matrix.util.SysParams;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/tableAggregateApp")
@RequestMapping("/sys/tableAggregateApp")
public class TableAggregateAppController {
	protected static final Log logger = LogFactory.getLog(TableAggregateAppController.class);

	protected IDatabaseService databaseService;

	protected TableAggregateAppService tableAggregateAppService;

	protected TableAggregateHistoryService tableAggregateHistoryService;

	public TableAggregateAppController() {

	}

	@RequestMapping("/chooseColumns")
	public ModelAndView chooseColumns(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		long srcDatabaseId = RequestUtils.getLong(request, "srcDatabaseId");
		String tableName = request.getParameter("sourceTableName");
		logger.debug("tableName:" + tableName);
		Database database = databaseService.getDatabaseById(srcDatabaseId);
		List<ColumnDefinition> columns = null;
		if (database != null) {
			logger.debug("database:" + database.getName());
			columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
		} else {
			columns = DBUtils.getColumnDefinitions("default", tableName);
		}

		logger.debug("columns:" + columns);

		TableAggregateApp tableAggregateApp = tableAggregateAppService
				.getTableAggregateApp(RequestUtils.getLong(request, "id"));
		if (tableAggregateApp != null) {
			request.setAttribute("tableAggregateApp", tableAggregateApp);
		}

		if (columns != null && !columns.isEmpty()) {
			List<String> selected = new ArrayList<String>();
			List<ColumnDefinition> unselected = new ArrayList<ColumnDefinition>();

			if (tableAggregateApp != null && StringUtils.isNotEmpty(tableAggregateApp.getSourceCalculateColumns())) {
				List<String> cols = StringTools.split(tableAggregateApp.getSourceCalculateColumns());
				for (ColumnDefinition col : columns) {
					if (cols.contains(col.getColumnName()) || cols.contains(col.getColumnName().toLowerCase())
							|| cols.contains(col.getColumnName().toUpperCase())) {
						selected.add(col.getColumnName().toLowerCase());
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
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("tableAggregateApp.chooseColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/matrix/tableAggregateApp/chooseColumns");
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
					TableAggregateApp tableAggregateApp = tableAggregateAppService
							.getTableAggregateApp(Long.valueOf(x));
					if (tableAggregateApp != null
							&& (StringUtils.equals(tableAggregateApp.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {

					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			TableAggregateApp tableAggregateApp = tableAggregateAppService.getTableAggregateApp(Long.valueOf(id));
			if (tableAggregateApp != null
					&& (StringUtils.equals(tableAggregateApp.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {

				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/dropTable")
	public byte[] dropTable(HttpServletRequest request, ModelMap modelMap) {
		Long id = RequestUtils.getLong(request, "id");
		TableAggregateApp tableAggregateApp = tableAggregateAppService.getTableAggregateApp(id);
		if (tableAggregateApp != null && StringUtils.isNotEmpty(tableAggregateApp.getTargetTableName())) {
			TableFactory.clear();
			boolean error = false;
			List<Long> databaseIds = StringTools.splitToLong(tableAggregateApp.getTargetDatabaseIds());
			for (Long databaseId : databaseIds) {
				try {
					Database targetDatabase = databaseService.getDatabaseById(databaseId);
					if (targetDatabase != null) {
						if (StringUtils.startsWithIgnoreCase(tableAggregateApp.getTargetTableName(), "etl_")
								|| StringUtils.startsWithIgnoreCase(tableAggregateApp.getTargetTableName(), "sync_")
								|| StringUtils.startsWithIgnoreCase(tableAggregateApp.getTargetTableName(), "tmp")
								|| StringUtils.startsWithIgnoreCase(tableAggregateApp.getTargetTableName(), "useradd_")
								|| StringUtils.startsWithIgnoreCase(tableAggregateApp.getTargetTableName(),
										"tree_table_")) {
							if (DBUtils.tableExists(targetDatabase.getName(), tableAggregateApp.getTargetTableName())) {
								String ddlStatements = " drop table " + tableAggregateApp.getTargetTableName();
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

		TableAggregateApp tableAggregateApp = tableAggregateAppService
				.getTableAggregateApp(RequestUtils.getLong(request, "id"));
		if (tableAggregateApp != null) {
			request.setAttribute("tableAggregateApp", tableAggregateApp);
		}

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
		List<Database> activeDatabases = cfg.getActiveDatabases(loginContext);
		if (activeDatabases != null && !activeDatabases.isEmpty()) {
			if (tableAggregateApp != null && StringUtils.isNotEmpty(tableAggregateApp.getTargetDatabaseIds())) {
				List<Long> databaseIds = StringTools.splitToLong(tableAggregateApp.getTargetDatabaseIds().trim());
				for (Database db : activeDatabases) {
					if (databaseIds.contains(db.getId())) {
						db.setSelected("selected");
					} else {
						db.setSelected("");
					}
				}
			}
		}
		request.setAttribute("databases", activeDatabases);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableAggregateApp.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/tableAggregateApp/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		QueryHelper helper = new QueryHelper();
		SysParams.putInternalParams(params);
		long syncId = RequestUtils.getLong(request, "syncId");
		Database database = null;
		Database srcDatabase = null;
		Connection srcConn = null;
		try {
			TableAggregateApp tableAggregateApp = tableAggregateAppService.getTableAggregateApp(syncId);
			if (tableAggregateApp != null && StringUtils.equals(tableAggregateApp.getActive(), "Y")) {
				srcDatabase = databaseService.getDatabaseById(tableAggregateApp.getSrcDatabaseId());
				if (srcDatabase != null) {
					srcConn = DBConnectionFactory.getConnection(srcDatabase.getName());
					logger.debug("srcConn:" + srcConn);
				}

				List<TableAggregateItem> items = tableAggregateApp.getItems();
				for (TableAggregateItem item : items) {
					String sql = item.getSql();
					if (!DBUtils.isLegalQuerySql(sql)) {
						return ResponseUtils.responseJsonResult(false, item.getTitle() + "\nSQL已经不合法:" + sql);
					}
					try {
						List<ColumnDefinition> cols = helper.getColumnList(srcConn, sql, params);
						logger.debug(cols);
					} catch (Exception ex) {
						return ResponseUtils.responseJsonResult(false,
								item.getTitle() + "\nSql error:" + ex.getMessage());
					}
				}
				JdbcUtils.close(srcConn);

				List<Long> targetDBIds = StringTools.splitToLong(tableAggregateApp.getTargetDatabaseIds());
				SqlToAggregateTableBean bean = new SqlToAggregateTableBean();
				for (Long targetDBId : targetDBIds) {
					database = databaseService.getDatabaseById(targetDBId);
					if (database != null) {
						long start = System.currentTimeMillis();
						bean.execute(tableAggregateApp.getSrcDatabaseId(), targetDBId, syncId, params);
						TableAggregateHistory his = new TableAggregateHistory();
						his.setCreateBy(loginContext.getActorId());
						his.setDatabaseId(targetDBId);
						his.setDeploymentId(tableAggregateApp.getDeploymentId());
						his.setSyncId(tableAggregateApp.getId());
						his.setStatus(1);
						his.setTotalTime((int) (System.currentTimeMillis() - start));
						his.setDatabaseName(database.getTitle() + "[" + database.getHost() + "]");
						tableAggregateHistoryService.save(his);
					}
				}
				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			JdbcUtils.close(srcConn);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableAggregateAppQuery query = new TableAggregateAppQuery();
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
		int total = tableAggregateAppService.getTableAggregateAppCountByQueryCriteria(query);
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

			List<TableAggregateApp> list = tableAggregateAppService.getTableAggregateAppsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableAggregateApp tableAggregateApp : list) {
					JSONObject rowJSON = tableAggregateApp.toJsonObject();
					rowJSON.put("id", tableAggregateApp.getId());
					rowJSON.put("rowId", tableAggregateApp.getId());
					rowJSON.put("tableAggregateAppId", tableAggregateApp.getId());
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

		return new ModelAndView("/matrix/tableAggregateApp/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("tableAggregateApp.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/matrix/tableAggregateApp/query", modelMap);
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String targetTableName = request.getParameter("targetTableName");
		targetTableName = targetTableName.trim();
		if (!(StringUtils.startsWithIgnoreCase(targetTableName, "etl_")
				|| StringUtils.startsWithIgnoreCase(targetTableName, "sync_")
				|| StringUtils.startsWithIgnoreCase(targetTableName, "tmp")
				|| StringUtils.startsWithIgnoreCase(targetTableName, "useradd_")
				|| StringUtils.startsWithIgnoreCase(targetTableName, "tree_table_"))) {
			return ResponseUtils.responseJsonResult(false, "目标表必须是以etl_,sync_,useradd_,tmp开头");
		}
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableAggregateApp tableAggregateApp = new TableAggregateApp();
		try {
			Tools.populate(tableAggregateApp, params);
			tableAggregateApp.setNodeId(RequestUtils.getLong(request, "nodeId"));
			tableAggregateApp.setDeploymentId(request.getParameter("deploymentId"));
			tableAggregateApp.setTitle(request.getParameter("title"));
			tableAggregateApp.setSrcDatabaseId(RequestUtils.getLong(request, "srcDatabaseId"));
			tableAggregateApp.setSourceTableName(request.getParameter("sourceTableName"));
			tableAggregateApp.setSourceIdColumn(request.getParameter("sourceIdColumn"));
			tableAggregateApp.setSourceIndexIdColumn(request.getParameter("sourceIndexIdColumn"));
			tableAggregateApp.setSourceParentIdColumn(request.getParameter("sourceParentIdColumn"));
			tableAggregateApp.setSourceTreeIdColumn(request.getParameter("sourceTreeIdColumn"));
			tableAggregateApp.setSourceTextColumn(request.getParameter("sourceTextColumn"));
			tableAggregateApp.setSourceTableDateSplitColumn(request.getParameter("sourceTableDateSplitColumn"));
			tableAggregateApp.setSourceCalculateColumns(request.getParameter("sourceCalculateColumns"));
			tableAggregateApp.setSplitDateFormat(request.getParameter("splitDateFormat"));
			tableAggregateApp.setDimensionTableName(request.getParameter("dimensionTableName"));
			tableAggregateApp.setDimensionColumn(request.getParameter("dimensionColumn"));
			tableAggregateApp.setTargetTableName(targetTableName);
			tableAggregateApp.setSyncFlag(request.getParameter("syncFlag"));
			tableAggregateApp.setType(request.getParameter("type"));
			tableAggregateApp.setActive(request.getParameter("active"));
			tableAggregateApp.setAutoSyncFlag(request.getParameter("autoSyncFlag"));
			tableAggregateApp.setInterval(RequestUtils.getInt(request, "interval"));
			tableAggregateApp.setSortNo(RequestUtils.getInt(request, "sortNo"));
			tableAggregateApp.setCreateBy(actorId);
			tableAggregateApp.setUpdateBy(actorId);

			String[] targetDatabaseIds = request.getParameterValues("targetDatabaseIds");
			if (targetDatabaseIds != null && targetDatabaseIds.length > 0) {
				StringBuilder buff = new StringBuilder();
				for (String targetDatabaseId : targetDatabaseIds) {
					buff.append(targetDatabaseId).append(", ");
				}
				tableAggregateApp.setTargetDatabaseIds(buff.toString());
			}

			this.tableAggregateAppService.save(tableAggregateApp);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveAs")
	public byte[] saveAs(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		try {
			long syncId = RequestUtils.getLong(request, "syncId");
			if (syncId > 0) {
				long nid = tableAggregateAppService.saveAs(syncId, actorId);
				if (nid > 0) {
					return ResponseUtils.responseJsonResult(true);
				}
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.aggregate.service.tableAggregateAppService")
	public void setTableAggregateAppService(TableAggregateAppService tableAggregateAppService) {
		this.tableAggregateAppService = tableAggregateAppService;
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.aggregate.service.tableAggregateHistoryService")
	public void setTableAggregateHistoryService(TableAggregateHistoryService tableAggregateHistoryService) {
		this.tableAggregateHistoryService = tableAggregateHistoryService;
	}

}
