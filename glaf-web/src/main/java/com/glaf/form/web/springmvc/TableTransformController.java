package com.glaf.form.web.springmvc;

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

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.*;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;
import com.glaf.datamgr.task.TableTransformTask;
import com.glaf.datamgr.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/tableTransform")
@RequestMapping("/form/tableTransform")
public class TableTransformController {
	protected static final Log logger = LogFactory.getLog(TableTransformController.class);

	protected IDatabaseService databaseService;

	protected TableTransformService tableTransformService;

	public TableTransformController() {

	}

	@RequestMapping("/chooseColumns")
	public ModelAndView chooseColumns(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		List<Long> databaseIds = StringTools.splitToLong(request.getParameter("databaseIds"));
		if (databaseIds != null && !databaseIds.isEmpty()) {
			long databaseId = databaseIds.get(0);
			String tableName = request.getParameter("tableName");
			Database database = databaseService.getDatabaseById(databaseId);
			List<ColumnDefinition> columns = null;
			if (database != null) {
				columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
			} else {
				columns = DBUtils.getColumnDefinitions(tableName);
			}

			TableTransform tableTransform = tableTransformService
					.getTableTransform(RequestUtils.getString(request, "tableName"));
			if (tableTransform != null) {
				request.setAttribute("tableTransform", tableTransform);
			}

			if (columns != null && !columns.isEmpty()) {
				List<String> selected = new ArrayList<String>();
				List<ColumnDefinition> unselected = new ArrayList<ColumnDefinition>();
				if (tableTransform != null && StringUtils.isNotEmpty(tableTransform.getTransformColumns())) {
					List<String> cols = StringTools.split(tableTransform.getTransformColumns());
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
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableTransform.chooseColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableTransform/chooseColumns", modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String tableName = RequestUtils.getString(request, "tableName");
		String tableNames = request.getParameter("tableNames");
		if (StringUtils.isNotEmpty(tableNames)) {
			StringTokenizer token = new StringTokenizer(tableNames, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					TableTransform tableTransform = tableTransformService.getTableTransform(String.valueOf(x));
					if (tableTransform != null
							&& (StringUtils.equals(tableTransform.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						tableTransformService.deleteById(tableTransform.getTableName());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (tableName != null) {
			TableTransform tableTransform = tableTransformService.getTableTransform(String.valueOf(tableName));
			if (tableTransform != null && (StringUtils.equals(tableTransform.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				tableTransformService.deleteById(tableTransform.getTableName());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		TableTransform tableTransform = tableTransformService.getTableTransform(request.getParameter("tableName"));
		if (tableTransform != null) {
			request.setAttribute("tableTransform", tableTransform);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			if (tableTransform != null && StringUtils.isNotEmpty(tableTransform.getDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(tableTransform.getDatabaseIds());
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

		String x_view = ViewProperties.getString("tableTransform.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableTransform/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/emptyColumns")
	public byte[] emptyColumns(HttpServletRequest request) {
		TableTransform tableTransform = tableTransformService.getTableTransform(request.getParameter("tableName"));
		int errorCount = 0;
		String sql = null;
		Connection conn = null;
		Database database = null;
		StringBuilder buffer = new StringBuilder();
		if (tableTransform != null && StringUtils.isNotEmpty(tableTransform.getDatabaseIds())) {
			StringTokenizer token = new StringTokenizer(tableTransform.getDatabaseIds(), ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
					try {
						database = databaseService.getDatabaseById(Long.parseLong(x));
						if (database != null) {
							conn = DBConnectionFactory.getConnection(database.getName());
							conn.setAutoCommit(false);
							List<ColumnTransform> columns = tableTransform.getColumns();
							if (columns != null && !columns.isEmpty()) {
								for (ColumnTransform col : columns) {
									/**
									 * 只能重置目标字段的值
									 */
									if (StringUtils.containsAny(col.getTargetColumnName(), "_COL_")) {
										sql = " update " + tableTransform.getTableName() + " set "
												+ col.getTargetColumnName() + " = null ";
										logger.debug(sql);
										DBUtils.executeSchemaResource(conn, sql);
									}
								}
							}
							conn.commit();
							JdbcUtils.close(conn);
						}
					} catch (Exception ex) {
						errorCount++;
						buffer.append(ex.getMessage()).append("\n\r");
					} finally {
						JdbcUtils.close(conn);
					}
				}
			}
			if (buffer.length() == 0) {
				buffer.append("执行成功。");
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableTransform tableTransform = tableTransformService.getTableTransform(request.getParameter("tableName"));
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		if (tableTransform != null && StringUtils.isNotEmpty(tableTransform.getDatabaseIds())) {
			tableTransform.setTransformTime(new Date());
			StringTokenizer token = new StringTokenizer(tableTransform.getDatabaseIds(), ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
					jobNo = "table_transform_" + tableTransform.getTableName() + "_" + x + "_" + runDay;
					try {
						database = databaseService.getDatabaseById(Long.parseLong(x));
						if (database != null) {
							TableTransformTask task = new TableTransformTask(database.getId(),
									tableTransform.getTableName(), jobNo, params);
							if (task.execute()) {
								buffer.append(database.getTitle()).append("[").append(database.getMapping())
										.append("]执行成功。\n");
							} else {
								errorCount++;
								buffer.append(database.getTitle()).append("[").append(database.getMapping())
										.append("]执行失败。\n");
								StringBuffer sb = ExceptionUtils
										.getMsgBuffer("table_transform_" + tableTransform.getTableName());
								buffer.append(sb.toString());
							}
						}
					} catch (Exception ex) {
						errorCount++;
					} finally {
						ExceptionUtils.clear("table_transform_" + tableTransform.getTableName());
					}
				}
			}
			if (errorCount == 0) {
				tableTransform.setTransformStatus(1);
			} else {
				tableTransform.setTransformStatus(-1);
			}
			tableTransformService.updateTableTransformStatus(tableTransform);
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableTransformQuery query = new TableTransformQuery();
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
		int total = tableTransformService.getTableTransformCountByQueryCriteria(query);
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

			List<TableTransform> list = tableTransformService.getTableTransformsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableTransform tableTransform : list) {
					JSONObject rowJSON = tableTransform.toJsonObject();
					rowJSON.put("id", tableTransform.getTableName());
					rowJSON.put("rowId", tableTransform.getTableName());
					rowJSON.put("tableTransformId", tableTransform.getTableName());
					if (tableTransform.getTransformStatus() == 1) {
						rowJSON.put("transformStatusText", "成功");
					} else if (tableTransform.getTransformStatus() == -1) {
						rowJSON.put("transformStatusText", "失败");
					}
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

		return new ModelAndView("/datamgr/tableTransform/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("tableTransform.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/tableTransform/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableTransformQuery query = new TableTransformQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		TableTransformDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = tableTransformService.getTableTransformCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<TableTransform> list = tableTransformService.getTableTransformsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableTransform tableTransform : list) {
					JSONObject rowJSON = tableTransform.toJsonObject();
					rowJSON.put("id", tableTransform.getTableName());
					rowJSON.put("tableTransformId", tableTransform.getTableName());
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

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TableTransform tableTransform = new TableTransform();
		Tools.populate(tableTransform, params);
		tableTransform.setTableName(request.getParameter("tableName"));
		tableTransform.setTitle(request.getParameter("title"));
		tableTransform.setDatabaseIds(request.getParameter("databaseIds"));
		tableTransform.setPrimaryKey(request.getParameter("primaryKey"));
		tableTransform.setTargetTableName(request.getParameter("targetTableName"));
		tableTransform.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
		tableTransform.setTransformFlag(request.getParameter("transformFlag"));
		tableTransform.setTransformColumns(request.getParameter("transformColumns"));
		tableTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
		tableTransform.setSort(RequestUtils.getInt(request, "sort"));
		tableTransform.setCurrentUserFlag(request.getParameter("currentUserFlag"));
		tableTransform.setDeleteFlag(0);
		tableTransform.setLocked(RequestUtils.getInt(request, "locked"));
		tableTransform.setCreateBy(actorId);
		tableTransform.setUpdateBy(actorId);

		tableTransformService.save(tableTransform);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody TableTransform saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		TableTransform tableTransform = new TableTransform();
		try {
			Tools.populate(tableTransform, model);
			tableTransform.setTitle(ParamUtils.getString(model, "title"));
			tableTransform.setTableName(ParamUtils.getString(model, "tableName"));
			tableTransform.setDatabaseIds(ParamUtils.getString(model, "databaseIds"));
			tableTransform.setPrimaryKey(ParamUtils.getString(model, "primaryKey"));
			tableTransform.setTargetTableName(request.getParameter("targetTableName"));
			tableTransform.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
			tableTransform.setTransformFlag(request.getParameter("transformFlag"));
			tableTransform.setTransformColumns(ParamUtils.getString(model, "transformColumns"));
			tableTransform.setSqlCriteria(ParamUtils.getString(model, "sqlCriteria"));
			tableTransform.setSort(ParamUtils.getInt(model, "sort"));
			tableTransform.setCurrentUserFlag(request.getParameter("currentUserFlag"));
			tableTransform.setDeleteFlag(0);
			tableTransform.setLocked(ParamUtils.getInt(model, "locked"));
			tableTransform.setCreateBy(actorId);
			tableTransform.setUpdateBy(actorId);

			this.tableTransformService.save(tableTransform);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return tableTransform;
	}

	@ResponseBody
	@RequestMapping("/saveTableTransform")
	public byte[] saveTableTransform(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableTransform tableTransform = new TableTransform();
		try {
			Tools.populate(tableTransform, params);
			tableTransform.setTableName(request.getParameter("tableName"));
			tableTransform.setTitle(request.getParameter("title"));
			tableTransform.setDatabaseIds(request.getParameter("databaseIds"));
			tableTransform.setPrimaryKey(request.getParameter("primaryKey"));
			tableTransform.setTargetTableName(request.getParameter("targetTableName"));
			tableTransform.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
			tableTransform.setTransformFlag(request.getParameter("transformFlag"));
			tableTransform.setTransformColumns(request.getParameter("transformColumns"));
			tableTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
			tableTransform.setSort(RequestUtils.getInt(request, "sort"));
			tableTransform.setCurrentUserFlag(request.getParameter("currentUserFlag"));
			tableTransform.setDeleteFlag(0);
			tableTransform.setLocked(RequestUtils.getInt(request, "locked"));
			tableTransform.setCreateBy(actorId);
			tableTransform.setUpdateBy(actorId);
			this.tableTransformService.save(tableTransform);

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
	public void setTableTransformService(TableTransformService tableTransformService) {
		this.tableTransformService = tableTransformService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		TableTransform tableTransform = tableTransformService.getTableTransform(request.getParameter("tableName"));

		Tools.populate(tableTransform, params);

		tableTransform.setTitle(request.getParameter("title"));
		tableTransform.setDatabaseIds(request.getParameter("databaseIds"));
		tableTransform.setPrimaryKey(request.getParameter("primaryKey"));
		tableTransform.setTargetTableName(request.getParameter("targetTableName"));
		tableTransform.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
		tableTransform.setTransformFlag(request.getParameter("transformFlag"));
		tableTransform.setTransformColumns(request.getParameter("transformColumns"));
		tableTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
		tableTransform.setSort(RequestUtils.getInt(request, "sort"));
		tableTransform.setCurrentUserFlag(request.getParameter("currentUserFlag"));
		tableTransform.setDeleteFlag(0);
		tableTransform.setLocked(RequestUtils.getInt(request, "locked"));
		tableTransform.setUpdateBy(actorId);

		tableTransformService.save(tableTransform);

		return this.list(request, modelMap);
	}

}
