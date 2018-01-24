package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.bean.TableMetaBean;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/tableInterface")
@RequestMapping("/sys/tableInterface")
public class TableInterfaceController {
	protected static final Log logger = LogFactory.getLog(TableInterfaceController.class);

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	public TableInterfaceController() {

	}

	@RequestMapping("/addTable")
	public ModelAndView addTable(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		List<ColumnDefinition> columns = null;
		List<Map<String, Object>> fieldList = null;
		DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
		List<Database> activeDatabases = cfg.getDatabases(loginContext);
		request.setAttribute("databases", activeDatabases);
		QueryHelper helper = new QueryHelper();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		long databaseId = RequestUtils.getLong(request, "databaseId", 0);
		String tableName = request.getParameter("tableName");
		if (StringUtils.isNotEmpty(tableName)) {
			String sql = " select fname, dname from interface where frmtype = '" + tableName + "' ";
			String sql2 = " select * from cell_data_table where id = '" + tableName + "' ";
			if (databaseId > 0) {
				Database database = databaseService.getDatabaseById(databaseId);
				try {
					columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
				} catch (Exception ex) {
					columns = helper.getColumnDefinitions(database.getName(), " select * from " + tableName + "   ",
							paramMap);
				}
				fieldList = helper.getResultList(database.getName(), sql, paramMap);
				Map<String, Object> dataMap = helper.selectOne(database.getName(), sql2, paramMap);
				if (dataMap != null && dataMap.get("name") != null) {
					request.setAttribute("tableNameCn", dataMap.get("name"));
				}
			} else {
				try {
					columns = DBUtils.getColumnDefinitions(tableName);
				} catch (Exception ex) {
					columns = helper.getColumnDefinitions(" select * from " + tableName + "   ", paramMap);
				}
				fieldList = helper.getResultList(sql, paramMap);
				Map<String, Object> dataMap = helper.selectOne(sql2, paramMap);
				if (dataMap != null && dataMap.get("name") != null) {
					request.setAttribute("tableNameCn", dataMap.get("name"));
				}
			}
		}

		if (columns != null && !columns.isEmpty() && fieldList != null && !fieldList.isEmpty()) {
			Map<String, String> columnMap = new HashMap<String, String>();
			for (Map<String, Object> dataMap : fieldList) {
				if (dataMap.get("dname") != null && dataMap.get("fname") != null) {
					columnMap.put(ParamUtils.getString(dataMap, "dname").toUpperCase(),
							ParamUtils.getString(dataMap, "fname"));
				}
			}
			for (ColumnDefinition column : columns) {
				if (columnMap.get(column.getColumnName()) != null) {
					column.setTitle(columnMap.get(column.getColumnName()));
				} else {
					column.setTitle(column.getColumnName().toLowerCase());
				}
			}
		}

		request.setAttribute("columns", columns);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableInterface.addTable");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableInterface/addTable", modelMap);
	}

	@ResponseBody
	@RequestMapping("/columnsJson")
	public byte[] columnsJson(HttpServletRequest request) throws IOException {
		JSONArray result = new JSONArray();
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		List<ColumnDefinition> columns = null;
		List<Map<String, Object>> fieldList = null;
		DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
		List<Database> activeDatabases = cfg.getDatabases(loginContext);
		request.setAttribute("databases", activeDatabases);
		QueryHelper helper = new QueryHelper();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		long databaseId = RequestUtils.getLong(request, "databaseId", 0);
		String tableName = request.getParameter("tableName");
		if (StringUtils.isNotEmpty(tableName)) {
			String sql = " select fname, dname, ismustfill from interface where frmtype = '" + tableName + "' ";
			String sql2 = " select * from cell_data_table where id = '" + tableName + "' ";
			if (databaseId > 0) {
				Database database = databaseService.getDatabaseById(databaseId);
				try {
					columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
				} catch (Exception ex) {
					columns = helper.getColumnDefinitions(database.getName(), " select * from " + tableName + "   ",
							paramMap);
				}
				fieldList = helper.getResultList(database.getName(), sql, paramMap);
				Map<String, Object> dataMap = helper.selectOne(database.getName(), sql2, paramMap);
				if (dataMap != null && dataMap.get("name") != null) {
					request.setAttribute("tableNameCn", dataMap.get("name"));
				}
			} else {
				try {
					columns = DBUtils.getColumnDefinitions(tableName);
				} catch (Exception ex) {
					columns = helper.getColumnDefinitions(" select * from " + tableName + "   ", paramMap);
				}
				fieldList = helper.getResultList(sql, paramMap);
				Map<String, Object> dataMap = helper.selectOne(sql2, paramMap);
				if (dataMap != null && dataMap.get("name") != null) {
					request.setAttribute("tableNameCn", dataMap.get("name"));
				}
			}
		}

		if (columns != null && !columns.isEmpty()) {
			Map<String, String> columnMap = new HashMap<String, String>();
			Map<String, String> ismustfillMap = new HashMap<String, String>();
			if (fieldList != null && !fieldList.isEmpty()) {
				for (Map<String, Object> dataMap : fieldList) {
					if (dataMap.get("dname") != null && dataMap.get("fname") != null) {
						columnMap.put(ParamUtils.getString(dataMap, "dname").toUpperCase(),
								ParamUtils.getString(dataMap, "fname"));
					}
					if (dataMap.get("dname") != null && dataMap.get("ismustfill") != null) {
						ismustfillMap.put(ParamUtils.getString(dataMap, "dname").toUpperCase(),
								ParamUtils.getString(dataMap, "ismustfill"));
					}
				}
			}
			for (ColumnDefinition column : columns) {
				if (columnMap.get(column.getColumnName()) != null) {
					column.setTitle(columnMap.get(column.getColumnName()));
				} else {
					column.setTitle(column.getColumnName().toLowerCase());
				}

				if (ismustfillMap.get(column.getColumnName()) != null) {
					if ("1".equals(ismustfillMap.get(column.getColumnName()))) {
						column.setRequired(true);
					}
				} else {
					column.setRequired(false);
				}
			}

			for (ColumnDefinition column : columns) {
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "id")) {
					continue;
				}
				JSONObject json = new JSONObject();
				json.put("columnName", column.getColumnName());
				json.put("title", column.getTitle());
				json.put("length", column.getLength());
				json.put("type", column.getJavaType());
				if (column.isRequired()) {
					json.put("ismustfill", "1");
				} else {
					json.put("ismustfill", "0");
				}
				switch (column.getJavaType()) {
				case "Integer":
					json.put("typeName", "整数型");
					break;
				case "Long":
					json.put("typeName", "长整数型");
					break;
				case "Double":
					json.put("typeName", "数值型");
					break;
				case "Date":
					json.put("typeName", "日期型");
					break;
				case "String":
					json.put("typeName", "字符串型");
					break;
				case "Text":
					json.put("typeName", "长文本型");
					break;
				}
				result.add(json);
			}
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		List<ColumnDefinition> columns = null;
		List<Map<String, Object>> fieldList = null;
		DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
		List<Database> activeDatabases = cfg.getDatabases(loginContext);
		request.setAttribute("databases", activeDatabases);
		QueryHelper helper = new QueryHelper();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		long databaseId = RequestUtils.getLong(request, "databaseId", 0);
		String tableName = request.getParameter("tableName");
		if (StringUtils.isNotEmpty(tableName)) {
			String sql = " select fname, dname from interface where frmtype = '" + tableName + "' ";
			String sql2 = " select * from cell_data_table where id = '" + tableName + "' ";
			if (databaseId > 0) {
				Database database = databaseService.getDatabaseById(databaseId);
				try {
					columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
				} catch (Exception ex) {
					columns = helper.getColumnDefinitions(database.getName(), " select * from " + tableName + "   ",
							paramMap);
				}
				fieldList = helper.getResultList(database.getName(), sql, paramMap);
				Map<String, Object> dataMap = helper.selectOne(database.getName(), sql2, paramMap);
				if (dataMap != null && dataMap.get("name") != null) {
					request.setAttribute("tableNameCn", dataMap.get("name"));
				}
			} else {
				try {
					columns = DBUtils.getColumnDefinitions(tableName);
				} catch (Exception ex) {
					columns = helper.getColumnDefinitions(" select * from " + tableName + "   ", paramMap);
				}
				fieldList = helper.getResultList(sql, paramMap);
				Map<String, Object> dataMap = helper.selectOne(sql2, paramMap);
				if (dataMap != null && dataMap.get("name") != null) {
					request.setAttribute("tableNameCn", dataMap.get("name"));
				}
			}
		}

		if (columns != null && !columns.isEmpty() && fieldList != null && !fieldList.isEmpty()) {
			Map<String, String> columnMap = new HashMap<String, String>();
			for (Map<String, Object> dataMap : fieldList) {
				if (dataMap.get("dname") != null && dataMap.get("fname") != null) {
					columnMap.put(ParamUtils.getString(dataMap, "dname").toUpperCase(),
							ParamUtils.getString(dataMap, "fname"));
				}
			}
			for (ColumnDefinition column : columns) {
				if (columnMap.get(column.getColumnName()) != null) {
					column.setTitle(columnMap.get(column.getColumnName()));
				} else {
					column.setTitle(column.getColumnName().toLowerCase());
				}
			}
		}

		request.setAttribute("columns", columns);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("tableInterface.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/tableInterface/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request, ModelMap modelMap) {
		String actorId = RequestUtils.getActorId(request);
		RequestUtils.setRequestParameterToAttribute(request);
		List<ColumnDefinition> columns = null;
		QueryHelper helper = new QueryHelper();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		long databaseId = RequestUtils.getLong(request, "databaseId", 0);
		String tableName = request.getParameter("tableName");
		String tableNameCn = request.getParameter("tableNameCn");
		if (StringUtils.isNotEmpty(tableName)) {
			String systemName = null;
			if (databaseId > 0) {
				Database database = databaseService.getDatabaseById(databaseId);
				systemName = database.getName();
				try {
					columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
				} catch (Exception ex) {
					columns = helper.getColumnDefinitions(database.getName(), " select * from " + tableName + "  ",
							paramMap);
				}
			} else {
				try {
					columns = DBUtils.getColumnDefinitions(tableName);
				} catch (Exception ex) {
					columns = helper.getColumnDefinitions(" select * from " + tableName + "  ", paramMap);
				}
			}

			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition column : columns) {
					String title = request.getParameter(column.getColumnName().toUpperCase() + "_title");
					column.setTitle(title);
				}
			}

			if (StringUtils.isEmpty(tableNameCn)) {
				tableNameCn = tableName;
			}

			TableMetaBean tableMetaBean = new TableMetaBean();
			tableMetaBean.saveTableMeta(actorId, systemName, tableName, tableNameCn, columns);

			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveAdd")
	public byte[] saveAdd(HttpServletRequest request, ModelMap modelMap) {
		logger.debug(RequestUtils.getParameterMap(request));
		String createTable = request.getParameter("createTable");
		String tableName = request.getParameter("tableName");
		String tableNameCn = request.getParameter("tableNameCn");
		String jsonArray = request.getParameter("jsonArray");
		if (StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(jsonArray) && DBUtils.isTableName(tableName)) {
			if (StringUtils.isEmpty(tableNameCn)) {
				tableNameCn = tableName;
			}
			JSONArray columnsArray = JSON.parseArray(jsonArray);
			long databaseId = RequestUtils.getLong(request, "databaseId", 0);
			String systemName = null;
			if (databaseId > 0) {
				DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
				Database database = cfg.getDatabase(databaseId);
				systemName = database.getName();
			}

			if (columnsArray != null && !columnsArray.isEmpty()) {
				int len = columnsArray.size();
				long ts = System.currentTimeMillis();
				List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
				for (int i = 0; i < len; i++) {
					JSONObject json = columnsArray.getJSONObject(i);
					String columnName = json.getString("columnName");
					if (!DBUtils.isTableColumn(columnName)) {
						columnName = "field_" + ts++;
					}
					ColumnDefinition column = new ColumnDefinition();
					column.setColumnName(columnName);
					column.setJavaType(json.getString("type"));
					column.setTitle(json.getString("title"));
					if ("1".equals(json.getString("ismustfill"))) {
						column.setRequired(true);
					}
					if ("String".equals(column.getJavaType())) {
						int length = json.getInteger("length");
						if (length > 0 && length <= 4000) {
							column.setLength(length);
						} else {
							column.setLength(200);
						}
					}
					columns.add(column);
				}

				ColumnDefinition idColumn = new ColumnDefinition();
				idColumn.setColumnName("id");
				idColumn.setJavaType("Long");
				idColumn.setTitle("主键");

				if (StringUtils.isNotEmpty(createTable) && StringUtils.equals(createTable, "true")) {
					TableDefinition tableDefinition = new TableDefinition();
					tableDefinition.setTableName(tableName);
					tableDefinition.setColumns(columns);
					tableDefinition.setIdColumn(idColumn);

					if (systemName != null) {
						if (DBUtils.tableExists(systemName, tableName)) {
							DBUtils.alterTable(systemName, tableDefinition);
						} else {
							DBUtils.createTable(systemName, tableDefinition);
						}
					} else {
						if (DBUtils.tableExists(tableName)) {
							DBUtils.alterTable(tableDefinition);
						} else {
							DBUtils.createTable(tableDefinition);
						}
					}
				}
				columns.add(idColumn);
				String createBy = RequestUtils.getActorId(request);
				TableMetaBean tableMetaBean = new TableMetaBean();
				tableMetaBean.saveTableMeta(createBy, systemName, tableName, tableNameCn, columns);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@ResponseBody
	@RequestMapping("/typeJson")
	public byte[] typeJson(HttpServletRequest request) throws IOException {
		JSONArray result = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("type", "Integer");
		json.put("typeName", "整数型");
		result.add(json);

		json = new JSONObject();
		json.put("type", "Long");
		json.put("typeName", "长整数型");
		result.add(json);

		json = new JSONObject();
		json.put("type", "Double");
		json.put("typeName", "数值型");
		result.add(json);

		json = new JSONObject();
		json.put("type", "Date");
		json.put("typeName", "日期型");
		result.add(json);

		json = new JSONObject();
		json.put("type", "Text");
		json.put("typeName", "长文本型");
		result.add(json);

		json = new JSONObject();
		json.put("type", "String");
		json.put("typeName", "字符串型");
		result.add(json);

		return result.toJSONString().getBytes();
	}

}
