package com.glaf.matrix.combination.web.springmvc;

import java.io.IOException;
import java.sql.Connection;
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
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.*;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.*;
import com.glaf.matrix.combination.bean.SqlToTableBean;
import com.glaf.matrix.combination.domain.*;
import com.glaf.matrix.combination.query.*;
import com.glaf.matrix.combination.service.*;
import com.glaf.matrix.util.SysParams;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/sqlToTableApp")
@RequestMapping("/sys/sqlToTableApp")
public class SqlToTableAppController {
	protected static final Log logger = LogFactory.getLog(SqlToTableAppController.class);

	protected IDatabaseService databaseService;

	protected SqlToTableAppService sqlToTableAppService;

	protected SqlToTableHistoryService sqlToTableHistoryService;

	public SqlToTableAppController() {

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
					SqlToTableApp sqlToTableApp = sqlToTableAppService.getSqlToTableApp(Long.valueOf(x));
					if (sqlToTableApp != null
							&& (StringUtils.equals(sqlToTableApp.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {

					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SqlToTableApp sqlToTableApp = sqlToTableAppService.getSqlToTableApp(Long.valueOf(id));
			if (sqlToTableApp != null && (StringUtils.equals(sqlToTableApp.getCreateBy(), loginContext.getActorId())
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
		SqlToTableApp sqlToTableApp = sqlToTableAppService.getSqlToTableApp(id);
		if (sqlToTableApp != null && StringUtils.isNotEmpty(sqlToTableApp.getTargetTableName())) {
			TableFactory.clear();
			boolean error = false;
			try {
				Database targetDatabase = databaseService.getDatabaseById(sqlToTableApp.getTargetDatabaseId());
				if (targetDatabase != null) {
					if (StringUtils.startsWithIgnoreCase(sqlToTableApp.getTargetTableName(), "etl_")
							|| StringUtils.startsWithIgnoreCase(sqlToTableApp.getTargetTableName(), "sync_")
							|| StringUtils.startsWithIgnoreCase(sqlToTableApp.getTargetTableName(), "tmp")
							|| StringUtils.startsWithIgnoreCase(sqlToTableApp.getTargetTableName(), "useradd_")
							|| StringUtils.startsWithIgnoreCase(sqlToTableApp.getTargetTableName(), "tree_table_")) {
						if (DBUtils.tableExists(targetDatabase.getName(), sqlToTableApp.getTargetTableName())) {
							String ddlStatements = " drop table " + sqlToTableApp.getTargetTableName();
							logger.warn(ddlStatements);
							DBUtils.executeSchemaResource(targetDatabase.getName(), ddlStatements);
						}
					}
				}
			} catch (Exception ex) {
				error = true;
				logger.error(ex);
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

		SqlToTableApp sqlToTableApp = sqlToTableAppService.getSqlToTableApp(RequestUtils.getLong(request, "id"));
		if (sqlToTableApp != null) {
			request.setAttribute("sqlToTableApp", sqlToTableApp);
		}

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
		List<Database> activeDatabases = cfg.getActiveDatabases(loginContext);
		if (activeDatabases != null && !activeDatabases.isEmpty()) {
			if (sqlToTableApp != null && StringUtils.isNotEmpty(sqlToTableApp.getSrcDatabaseIds())) {
				List<Long> databaseIds = StringTools.splitToLong(sqlToTableApp.getSrcDatabaseIds().trim());
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

		String x_view = ViewProperties.getString("sqlToTableApp.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/sqlToTableApp/edit", modelMap);
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
		Connection srcConn = null;
		try {
			SqlToTableApp sqlToTableApp = sqlToTableAppService.getSqlToTableApp(syncId);
			if (sqlToTableApp != null && StringUtils.equals(sqlToTableApp.getActive(), "Y")) {
				List<Long> srcDBIds = StringTools.splitToLong(sqlToTableApp.getSrcDatabaseIds());
				for (Long dbId : srcDBIds) {
					database = databaseService.getDatabaseById(dbId);
					if (database != null) {
						srcConn = DBConnectionFactory.getConnection(database.getName());
						logger.debug("srcConn:" + srcConn);
						List<SqlToTableItem> items = sqlToTableApp.getItems();
						for (SqlToTableItem item : items) {
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
					}
				}

				SqlToTableBean bean = new SqlToTableBean();
				long start = System.currentTimeMillis();
				bean.execute(syncId, params);

				SqlToTableHistory his = new SqlToTableHistory();
				his.setCreateBy(loginContext.getActorId());
				his.setDatabaseId(database.getId());
				his.setDeploymentId(sqlToTableApp.getDeploymentId());
				his.setSyncId(sqlToTableApp.getId());
				his.setStatus(1);
				his.setTotalTime((int) (System.currentTimeMillis() - start));
				his.setDatabaseName(database.getTitle() + "[" + database.getHost() + "]");
				sqlToTableHistoryService.save(his);

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

	@ResponseBody
	@RequestMapping("/executeAll")
	public byte[] executeAll(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysParams.putInternalParams(params);
		String syncIds = request.getParameter("syncIds");
		List<Long> syncIdList = StringTools.splitToLong(syncIds, ",");
		Connection srcConn = null;
		int errorCount = 0;
		SqlToTableBean bean = new SqlToTableBean();
		for (Long syncId : syncIdList) {
			try {
				SqlToTableApp sqlToTableApp = sqlToTableAppService.getSqlToTableApp(syncId);
				if (sqlToTableApp != null && StringUtils.equals(sqlToTableApp.getActive(), "Y")) {
					long start = System.currentTimeMillis();
					bean.execute(syncId, params);
					SqlToTableHistory his = new SqlToTableHistory();
					his.setCreateBy(loginContext.getActorId());
					his.setDeploymentId(sqlToTableApp.getDeploymentId());
					his.setSyncId(sqlToTableApp.getId());
					his.setStatus(1);
					his.setTotalTime((int) (System.currentTimeMillis() - start));
					sqlToTableHistoryService.save(his);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
				errorCount++;
			} finally {
				JdbcUtils.close(srcConn);
			}
		}
		if (errorCount == 0) {
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlToTableAppQuery query = new SqlToTableAppQuery();
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
		int total = sqlToTableAppService.getSqlToTableAppCountByQueryCriteria(query);
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

			List<SqlToTableApp> list = sqlToTableAppService.getSqlToTableAppsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SqlToTableApp sqlToTableApp : list) {
					JSONObject rowJSON = sqlToTableApp.toJsonObject();
					rowJSON.put("id", sqlToTableApp.getId());
					rowJSON.put("rowId", sqlToTableApp.getId());
					rowJSON.put("sqlToTableAppId", sqlToTableApp.getId());
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

		return new ModelAndView("/matrix/sqlToTableApp/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sqlToTableApp.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/matrix/sqlToTableApp/query", modelMap);
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
			return ResponseUtils.responseJsonResult(false, "目标表必须是以etl_,sync_,useradd_,tree_table_,tmp开头");
		}
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SqlToTableApp sqlToTableApp = new SqlToTableApp();
		try {
			Tools.populate(sqlToTableApp, params);
			sqlToTableApp.setTitle(request.getParameter("title"));
			sqlToTableApp.setNodeId(RequestUtils.getLong(request, "nodeId"));
			sqlToTableApp.setDeploymentId(request.getParameter("deploymentId"));
			sqlToTableApp.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			sqlToTableApp.setTargetTableName(targetTableName);
			sqlToTableApp.setSyncFlag(request.getParameter("syncFlag"));
			sqlToTableApp.setType(request.getParameter("type"));
			sqlToTableApp.setActive(request.getParameter("active"));
			sqlToTableApp.setAutoSyncFlag(request.getParameter("autoSyncFlag"));
			sqlToTableApp.setExternalColumnsFlag(request.getParameter("externalColumnsFlag"));
			sqlToTableApp.setUniqueFlag(request.getParameter("uniqueFlag"));
			sqlToTableApp.setInterval(RequestUtils.getInt(request, "interval"));
			sqlToTableApp.setSortNo(RequestUtils.getInt(request, "sortNo"));
			sqlToTableApp.setCreateBy(actorId);
			sqlToTableApp.setUpdateBy(actorId);

			String[] srcDatabaseIds = request.getParameterValues("srcDatabaseIds");
			if (srcDatabaseIds != null && srcDatabaseIds.length > 0) {
				StringBuilder buff = new StringBuilder();
				for (String srcDatabaseId : srcDatabaseIds) {
					buff.append(srcDatabaseId).append(", ");
				}
				sqlToTableApp.setSrcDatabaseIds(buff.toString());
			}

			this.sqlToTableAppService.save(sqlToTableApp);

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
				long nid = sqlToTableAppService.saveAs(syncId, actorId);
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

	@javax.annotation.Resource(name = "com.glaf.matrix.combination.service.sqlToTableAppService")
	public void setSqlToTableAppService(SqlToTableAppService sqlToTableAppService) {
		this.sqlToTableAppService = sqlToTableAppService;
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.combination.service.sqlToTableHistoryService")
	public void setSqlToTableHistoryService(SqlToTableHistoryService sqlToTableHistoryService) {
		this.sqlToTableHistoryService = sqlToTableHistoryService;
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

}
