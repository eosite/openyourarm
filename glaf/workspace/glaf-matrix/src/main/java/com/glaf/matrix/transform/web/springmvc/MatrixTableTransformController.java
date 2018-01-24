package com.glaf.matrix.transform.web.springmvc;

import java.io.IOException;
import java.sql.Connection;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.util.ExceptionUtils;
import com.glaf.matrix.transform.domain.MatrixColumnTransform;
import com.glaf.matrix.transform.domain.MatrixTableTransform;
import com.glaf.matrix.transform.query.MatrixTableTransformQuery;
import com.glaf.matrix.transform.service.MatrixTableTransformService;
import com.glaf.matrix.transform.task.MatrixTableTransformTask;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/matrixTableTransform")
@RequestMapping("/sys/matrixTableTransform")
public class MatrixTableTransformController {
	protected static final Log logger = LogFactory.getLog(MatrixTableTransformController.class);

	protected IDatabaseService databaseService;

	protected MatrixTableTransformService matrixTableTransformService;

	public MatrixTableTransformController() {

	}

	@RequestMapping("/chooseColumns")
	public ModelAndView chooseColumns(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		List<Long> databaseIds = StringTools.splitToLong(request.getParameter("databaseIds"));
		if (databaseIds != null && !databaseIds.isEmpty()) {
			long databaseId = databaseIds.get(0);
			String tableName = request.getParameter("tableName");
			logger.debug("tableName:" + tableName);
			Database database = databaseService.getDatabaseById(databaseId);
			List<ColumnDefinition> columns = null;
			if (database != null) {
				logger.debug("database:" + database.getName());
				columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
			} else {
				columns = DBUtils.getColumnDefinitions("default", tableName);
			}

			logger.debug("columns:" + columns);

			MatrixTableTransform matrixTableTransform = matrixTableTransformService
					.getMatrixTableTransform(RequestUtils.getString(request, "tableName"));
			if (matrixTableTransform != null) {
				request.setAttribute("matrixTableTransform", matrixTableTransform);
			}

			if (columns != null && !columns.isEmpty()) {
				List<String> selected = new ArrayList<String>();
				List<ColumnDefinition> unselected = new ArrayList<ColumnDefinition>();
				if (matrixTableTransform != null
						&& StringUtils.isNotEmpty(matrixTableTransform.getTransformColumns())) {
					List<String> cols = StringTools.split(matrixTableTransform.getTransformColumns());
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

		String x_view = ViewProperties.getString("matrixTableTransform.chooseColumns");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/tableTransform/chooseColumns", modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String transformId = RequestUtils.getString(request, "transformId");
		String transformIds = request.getParameter("transformIds");
		if (StringUtils.isNotEmpty(transformIds)) {
			StringTokenizer token = new StringTokenizer(transformIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					MatrixTableTransform matrixTableTransform = matrixTableTransformService
							.getMatrixTableTransform(String.valueOf(x));
					if (matrixTableTransform != null
							&& (StringUtils.equals(matrixTableTransform.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						matrixTableTransformService.deleteById(matrixTableTransform.getTableName());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (transformId != null) {
			MatrixTableTransform matrixTableTransform = matrixTableTransformService
					.getMatrixTableTransform(String.valueOf(transformId));
			if (matrixTableTransform != null
					&& (StringUtils.equals(matrixTableTransform.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				matrixTableTransformService.deleteById(matrixTableTransform.getTableName());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		MatrixTableTransform matrixTableTransform = matrixTableTransformService
				.getMatrixTableTransform(request.getParameter("transformId"));
		if (matrixTableTransform != null) {
			request.setAttribute("matrixTableTransform", matrixTableTransform);
		}

		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		List<Database> databases = config.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			if (matrixTableTransform != null && StringUtils.isNotEmpty(matrixTableTransform.getDatabaseIds())) {
				List<Long> ids = StringTools.splitToLong(matrixTableTransform.getDatabaseIds());
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

		String x_view = ViewProperties.getString("matrixTableTransform.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/tableTransform/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/emptyColumns")
	public byte[] emptyColumns(HttpServletRequest request) {
		MatrixTableTransform matrixTableTransform = matrixTableTransformService
				.getMatrixTableTransform(request.getParameter("tableName"));
		int errorCount = 0;
		String sql = null;
		Connection conn = null;
		Database database = null;
		StringBuilder buffer = new StringBuilder();
		if (matrixTableTransform != null && StringUtils.isNotEmpty(matrixTableTransform.getDatabaseIds())) {
			StringTokenizer token = new StringTokenizer(matrixTableTransform.getDatabaseIds(), ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
					try {
						database = databaseService.getDatabaseById(Long.parseLong(x));
						if (database != null) {
							conn = DBConnectionFactory.getConnection(database.getName());
							conn.setAutoCommit(false);
							List<MatrixColumnTransform> columns = matrixTableTransform.getColumns();
							if (columns != null && !columns.isEmpty()) {
								for (MatrixColumnTransform col : columns) {
									/**
									 * 只能重置目标字段的值
									 */
									if (StringUtils.containsAny(col.getTargetColumnName(), "_COL_")) {
										sql = " update " + matrixTableTransform.getTableName() + " set "
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
		MatrixTableTransform matrixTableTransform = matrixTableTransformService
				.getMatrixTableTransform(request.getParameter("transformId"));
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		if (matrixTableTransform != null && StringUtils.isNotEmpty(matrixTableTransform.getDatabaseIds())) {
			matrixTableTransform.setTransformTime(new Date());
			StringTokenizer token = new StringTokenizer(matrixTableTransform.getDatabaseIds(), ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
					jobNo = "matrix_table_transform_" + matrixTableTransform.getTransformId() + "_" + x + "_" + runDay;
					try {
						database = databaseService.getDatabaseById(Long.parseLong(x));
						if (database != null) {
							MatrixTableTransformTask task = new MatrixTableTransformTask(database.getId(),
									matrixTableTransform.getTransformId(), jobNo, params);
							if (task.execute()) {
								buffer.append(database.getTitle()).append("[").append(database.getMapping())
										.append("]执行成功。\n");
							} else {
								errorCount++;
								buffer.append(database.getTitle()).append("[").append(database.getMapping())
										.append("]执行失败。\n");
								StringBuffer sb = ExceptionUtils.getMsgBuffer(
										"matrix_table_transform_" + matrixTableTransform.getTransformId());
								buffer.append(sb.toString());
							}
						}
					} catch (Exception ex) {
						errorCount++;
					} finally {
						ExceptionUtils.clear("matrix_table_transform_" + matrixTableTransform.getTransformId());
					}
				}
			}
			if (errorCount == 0) {
				matrixTableTransform.setTransformStatus(1);
			} else {
				matrixTableTransform.setTransformStatus(-1);
			}
			matrixTableTransformService.updateMatrixTableTransformStatus(matrixTableTransform);
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@ResponseBody
	@RequestMapping("/executeAll")
	public byte[] executeAll(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String rowIds = request.getParameter("syncIds");
		List<String> transformIds = StringTools.split(rowIds);
		int errorCount = 0;
		int errorTotal = 0;
		String jobNo = null;
		Database database = null;
		MatrixTableTransformTask task = null;
		MatrixTableTransform matrixTableTransform = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		for (String transformId : transformIds) {
			errorCount = 0;
			matrixTableTransform = matrixTableTransformService.getMatrixTableTransform(transformId);
			if (matrixTableTransform != null && StringUtils.isNotEmpty(matrixTableTransform.getDatabaseIds())) {
				matrixTableTransform.setTransformTime(new Date());
				StringTokenizer token = new StringTokenizer(matrixTableTransform.getDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						jobNo = "matrix_table_transform_" + matrixTableTransform.getTransformId() + "_" + x + "_"
								+ runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								task = new MatrixTableTransformTask(database.getId(),
										matrixTableTransform.getTransformId(), jobNo, params);
								if (task.execute()) {
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行成功。\n");
								} else {
									errorCount++;
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行失败。\n");
									StringBuffer sb = ExceptionUtils.getMsgBuffer(
											"matrix_table_transform_" + matrixTableTransform.getTransformId());
									buffer.append(sb.toString());
								}
							}
						} catch (Exception ex) {
							errorCount++;
							errorTotal++;
						} finally {
							ExceptionUtils.clear("matrix_table_transform_" + matrixTableTransform.getTransformId());
						}
					}
				}
				if (errorCount == 0) {
					matrixTableTransform.setTransformStatus(1);
				} else {
					matrixTableTransform.setTransformStatus(-1);
				}
				matrixTableTransformService.updateMatrixTableTransformStatus(matrixTableTransform);
			}
		}

		return ResponseUtils.responseJsonResult(errorTotal == 0 ? true : false, buffer.toString());
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MatrixTableTransformQuery query = new MatrixTableTransformQuery();
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
		int total = matrixTableTransformService.getMatrixTableTransformCountByQueryCriteria(query);
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

			List<MatrixTableTransform> list = matrixTableTransformService.getMatrixTableTransformsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MatrixTableTransform matrixTableTransform : list) {
					JSONObject rowJSON = matrixTableTransform.toJsonObject();
					rowJSON.put("id", matrixTableTransform.getTransformId());
					rowJSON.put("rowId", matrixTableTransform.getTransformId());
					rowJSON.put("transformId", matrixTableTransform.getTransformId());
					if (matrixTableTransform.getTransformStatus() == 1) {
						rowJSON.put("transformStatusText", "成功");
					} else if (matrixTableTransform.getTransformStatus() == -1) {
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

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/matrix/tableTransform/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("matrixTableTransform.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/matrix/tableTransform/query", modelMap);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		MatrixTableTransform matrixTableTransform = new MatrixTableTransform();
		Tools.populate(matrixTableTransform, params);
		matrixTableTransform.setTransformId(request.getParameter("transformId"));
		matrixTableTransform.setTableName(request.getParameter("tableName"));
		matrixTableTransform.setTitle(request.getParameter("title"));
		matrixTableTransform.setDatabaseIds(request.getParameter("databaseIds"));
		matrixTableTransform.setPrimaryKey(request.getParameter("primaryKey"));
		matrixTableTransform.setTargetTableName(request.getParameter("targetTableName"));
		matrixTableTransform
				.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
		matrixTableTransform.setTransformFlag(request.getParameter("transformFlag"));
		matrixTableTransform.setTransformColumns(request.getParameter("transformColumns"));
		matrixTableTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
		matrixTableTransform.setSort(RequestUtils.getInt(request, "sort"));
		matrixTableTransform.setCurrentUserFlag(request.getParameter("currentUserFlag"));
		matrixTableTransform.setDeleteFlag(0);
		matrixTableTransform.setLocked(RequestUtils.getInt(request, "locked"));
		matrixTableTransform.setCreateBy(actorId);
		matrixTableTransform.setUpdateBy(actorId);

		matrixTableTransformService.save(matrixTableTransform);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveMatrixTableTransform")
	public byte[] saveMatrixTableTransform(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MatrixTableTransform matrixTableTransform = new MatrixTableTransform();
		try {
			Tools.populate(matrixTableTransform, params);
			matrixTableTransform.setTransformId(request.getParameter("transformId"));
			matrixTableTransform.setTableName(request.getParameter("tableName"));
			matrixTableTransform.setTitle(request.getParameter("title"));
			matrixTableTransform.setDatabaseIds(request.getParameter("databaseIds"));
			matrixTableTransform.setPrimaryKey(request.getParameter("primaryKey"));
			matrixTableTransform.setTargetTableName(request.getParameter("targetTableName"));
			matrixTableTransform
					.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
			matrixTableTransform.setTransformFlag(request.getParameter("transformFlag"));
			matrixTableTransform.setTransformColumns(request.getParameter("transformColumns"));
			matrixTableTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
			matrixTableTransform.setSort(RequestUtils.getInt(request, "sort"));
			matrixTableTransform.setCurrentUserFlag(request.getParameter("currentUserFlag"));
			matrixTableTransform.setDeleteFlag(0);
			matrixTableTransform.setLocked(RequestUtils.getInt(request, "locked"));
			matrixTableTransform.setCreateBy(actorId);
			matrixTableTransform.setUpdateBy(actorId);
			this.matrixTableTransformService.save(matrixTableTransform);

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
	public void setMatrixTableTransformService(MatrixTableTransformService matrixTableTransformService) {
		this.matrixTableTransformService = matrixTableTransformService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		MatrixTableTransform matrixTableTransform = matrixTableTransformService
				.getMatrixTableTransform(request.getParameter("transformId"));

		Tools.populate(matrixTableTransform, params);

		matrixTableTransform.setTitle(request.getParameter("title"));
		matrixTableTransform.setDatabaseIds(request.getParameter("databaseIds"));
		matrixTableTransform.setPrimaryKey(request.getParameter("primaryKey"));
		matrixTableTransform.setTargetTableName(request.getParameter("targetTableName"));
		matrixTableTransform
				.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
		matrixTableTransform.setTransformFlag(request.getParameter("transformFlag"));
		matrixTableTransform.setTransformColumns(request.getParameter("transformColumns"));
		matrixTableTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
		matrixTableTransform.setSort(RequestUtils.getInt(request, "sort"));
		matrixTableTransform.setCurrentUserFlag(request.getParameter("currentUserFlag"));
		matrixTableTransform.setDeleteFlag(0);
		matrixTableTransform.setLocked(RequestUtils.getInt(request, "locked"));
		matrixTableTransform.setUpdateBy(actorId);

		matrixTableTransformService.save(matrixTableTransform);

		return this.list(request, modelMap);
	}

}
