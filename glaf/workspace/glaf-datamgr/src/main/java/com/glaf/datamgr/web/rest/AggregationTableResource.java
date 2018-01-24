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

package com.glaf.datamgr.web.rest;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.query.TablePageQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.bean.AggregationTableExecution;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.util.Constants;

@Controller("/rs/datamgr/table")
@Path("/rs/datamgr/table")
public class AggregationTableResource {

	protected static final Log logger = LogFactory.getLog(AggregationTableResource.class);

	protected DataSetService dataSetService;

	protected IDatabaseService databaseService;

	protected ITableDefinitionService tableDefinitionService;

	protected ITablePageService tablePageService;

	@GET
	@POST
	@Path("/columns")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] columns(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tableName = ParamUtils.getString(params, "tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		TableDefinition table = tableDefinitionService.getTableDefinition(tableName);
		ArrayNode responseJSON = new ObjectMapper().createArrayNode();
		if (table != null) {
			for (ColumnDefinition column : table.getColumns()) {
				ObjectNode columnJSON = new ObjectMapper().createObjectNode();
				columnJSON.put("columnName", column.getColumnName());
				if (column.getTitle() != null) {
					columnJSON.put("title", column.getTitle());
				}
				if (column.getValueExpression() != null) {
					columnJSON.put("valueExpression", column.getValueExpression());
				}
				if (column.getFormula() != null) {
					columnJSON.put("formula", column.getFormula());
				}
				if (column.getJavaType() != null) {
					columnJSON.put("javaType", column.getJavaType());
				}
				if (column.getTranslator() != null) {
					columnJSON.put("translator", column.getTranslator());
				}
				if (column.getName() != null) {
					columnJSON.put("name", column.getName());
				}
				if (column.getRegex() != null) {
					columnJSON.put("regex", column.getRegex());
				}
				columnJSON.put("length", column.getLength());
				columnJSON.put("ordinal", column.getOrdinal());
				columnJSON.put("precision", column.getPrecision());
				columnJSON.put("scale", column.getScale());
				responseJSON.add(columnJSON);
			}
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] delete(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = tableName.toLowerCase();
			if (StringUtils.startsWith(tableName, "mx_") || StringUtils.startsWith(tableName, "sys_")
					|| StringUtils.startsWith(tableName, "jbpm_") || StringUtils.startsWith(tableName, "act_")) {
				return ResponseUtils.responseJsonResult(false);
			}
			tableDefinitionService.deleteTable(tableName);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@POST
	@Path("/deleteColumn")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteColumn(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		String columnId = request.getParameter("columnId");
		if (StringUtils.isNotEmpty(columnId)) {
			columnId = RequestUtils.decodeString(columnId);
			columnId = columnId.toLowerCase();
			if (StringUtils.equalsIgnoreCase("ID", columnId)
					|| StringUtils.equalsIgnoreCase("AGGREGATIONKEY", columnId)) {
				return ResponseUtils.responseJsonResult(false);
			}
			tableDefinitionService.deleteColumn(columnId);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@POST
	@Path("/deleteTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteTable(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = tableName.toLowerCase();
			if (StringUtils.startsWith(tableName, "mx_") || StringUtils.startsWith(tableName, "sys_")
					|| StringUtils.startsWith(tableName, "jbpm_") || StringUtils.startsWith(tableName, "act_")) {
				return ResponseUtils.responseJsonResult(false);
			}
			tableDefinitionService.deleteTable(tableName);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@GET
	@POST
	@Path("/headers")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] headers(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tableName = ParamUtils.getString(params, "tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		QueryHelper helper = new QueryHelper();
		List<ColumnDefinition> columns = null;
		try {
			String sql = "select * from " + tableName + " where 1=0 ";
			columns = helper.getColumnDefinitions(sql, params);
		} catch (Exception ex) {
			logger.error(ex);
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
		if (columns != null && !columns.isEmpty()) {
			ObjectNode rowJSON = new ObjectMapper().createObjectNode();
			for (ColumnDefinition column : columns) {
				if (column.getColumnName() != null) {
					rowJSON.put("columnName", column.getColumnName());
				}
				if (column.getTitle() != null) {
					rowJSON.put("title", column.getTitle());
				}
				if (column.getName() != null) {
					rowJSON.put("name", column.getName());
				}
				if (column.getJavaType() != null) {
					rowJSON.put("javaType", column.getJavaType());
				}
			}
			rowsJSON.add(rowJSON);
		}

		responseJSON.set("rows", rowsJSON);

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableDefinitionQuery query = new TableDefinitionQuery();
		Tools.populate(query, params);
		query.setType(Constants.ETL_TABLE_TYPE);
		List<TableDefinition> tables = tableDefinitionService.list(query);
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		ArrayNode tablesJSON = new ObjectMapper().createArrayNode();
		responseJSON.set("tables", tablesJSON);

		for (TableDefinition table : tables) {
			ObjectNode tableJSON = new ObjectMapper().createObjectNode();
			tableJSON.put("tableName", table.getTableName());
			tableJSON.put("tableName_enc", RequestUtils.encodeString(table.getTableName()));
			tableJSON.put("title", table.getTitle());
			if (table.getDescription() != null) {
				tableJSON.put("description", table.getDescription());
			}
			tableJSON.put("locked", table.getLocked());
			tableJSON.put("revision", table.getRevision());
			if (table.getCreateBy() != null) {
				tableJSON.put("createBy", table.getCreateBy());
			}
			tableJSON.put("createTime", DateUtils.getDateTime(table.getCreateTime()));
			tablesJSON.add(tableJSON);

			table = tableDefinitionService.getTableDefinition(table.getTableName());

			ArrayNode columnsJSON = new ObjectMapper().createArrayNode();

			for (ColumnDefinition column : table.getColumns()) {
				ObjectNode columnJSON = new ObjectMapper().createObjectNode();
				columnJSON.put("columnName", column.getColumnName());
				if (column.getTitle() != null) {
					columnJSON.put("title", column.getTitle());
				}
				if (column.getValueExpression() != null) {
					columnJSON.put("valueExpression", column.getValueExpression());
				}
				if (column.getFormula() != null) {
					columnJSON.put("formula", column.getFormula());
				}
				if (column.getJavaType() != null) {
					columnJSON.put("javaType", column.getJavaType());
				}
				if (column.getTranslator() != null) {
					columnJSON.put("translator", column.getTranslator());
				}
				if (column.getName() != null) {
					columnJSON.put("name", column.getName());
				}
				if (column.getRegex() != null) {
					columnJSON.put("regex", column.getRegex());
				}
				columnJSON.put("length", column.getLength());
				columnJSON.put("ordinal", column.getOrdinal());
				columnJSON.put("precision", column.getPrecision());
				columnJSON.put("scale", column.getScale());
				columnsJSON.add(columnJSON);
			}

			tableJSON.set("columns", columnsJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/saveColumn")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveColumn(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}

		if (StringUtils.isNotEmpty(tableName)) {
			List<String> columnNames = new ArrayList<String>();
			TableDefinition tableDefinition = tableDefinitionService.getTableDefinition(tableName);
			for (ColumnDefinition column : tableDefinition.getColumns()) {
				String col = column.getColumnName();
				columnNames.add(col.toUpperCase());
			}
			ColumnDefinition columnDefinition = new ColumnDefinition();
			Tools.populate(columnDefinition, params);
			tableDefinitionService.saveColumn(tableName, columnDefinition);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@POST
	@Path("/saveTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveTable(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		String actionType = request.getParameter("actionType");
		TableDefinition tableDefinition = tableDefinitionService.getTableDefinition(tableName);
		if (tableDefinition == null) {
			tableDefinition = new TableDefinition();
			tableDefinition.setTableName(tableName);
			tableDefinition.setCreateBy(loginContext.getActorId());

			ColumnDefinition idColumn = new ColumnDefinition();
			idColumn.setName("id");
			idColumn.setColumnName("ID_");
			idColumn.setJavaType("Long");
			idColumn.setSystemFlag("Y");
			idColumn.setTitle("主键");
			tableDefinition.setIdColumn(idColumn);

			ColumnDefinition databaseId = new ColumnDefinition();
			databaseId.setName("databaseId");
			databaseId.setColumnName("DATABASEID_");
			databaseId.setJavaType("Long");
			databaseId.setSystemFlag("Y");
			databaseId.setTitle("数据库编号");
			tableDefinition.addColumn(databaseId);

			ColumnDefinition datasetId = new ColumnDefinition();
			datasetId.setName("datasetId");
			datasetId.setColumnName("DATASETID_");
			datasetId.setJavaType("String");
			datasetId.setLength(50);
			datasetId.setSystemFlag("Y");
			datasetId.setTitle("数据集编号");
			tableDefinition.addColumn(datasetId);

			ColumnDefinition projectId = new ColumnDefinition();
			projectId.setName("projectId");
			projectId.setColumnName("PROJECTID_");
			projectId.setJavaType("Long");
			projectId.setSystemFlag("Y");
			projectId.setTitle("项目编号");
			tableDefinition.addColumn(projectId);

			ColumnDefinition count = new ColumnDefinition();
			count.setName("count");
			count.setColumnName("COUNT_");
			count.setJavaType("Integer");
			count.setSystemFlag("Y");
			count.setTitle("汇总个数");
			tableDefinition.addColumn(count);

			ColumnDefinition value = new ColumnDefinition();
			value.setName("value");
			value.setColumnName("VALUE_");
			value.setJavaType("Double");
			value.setSystemFlag("Y");
			value.setTitle("数值");
			tableDefinition.addColumn(value);

			ColumnDefinition type = new ColumnDefinition();
			type.setName("type");
			type.setColumnName("TYPE_");
			type.setJavaType("String");
			type.setLength(50);
			type.setSystemFlag("Y");
			type.setTitle("类型");
			tableDefinition.addColumn(type);

			ColumnDefinition runYear = new ColumnDefinition();
			runYear.setName("runYear");
			runYear.setColumnName("RUNYEAR_");
			runYear.setJavaType("Integer");
			runYear.setSystemFlag("Y");
			runYear.setTitle("运行年份");
			tableDefinition.addColumn(runYear);

			ColumnDefinition runMonth = new ColumnDefinition();
			runMonth.setName("runMonth");
			runMonth.setColumnName("RUNMONTH_");
			runMonth.setJavaType("Integer");
			runMonth.setSystemFlag("Y");
			runMonth.setTitle("运行月份");
			tableDefinition.addColumn(runMonth);

			ColumnDefinition runWeek = new ColumnDefinition();
			runWeek.setName("runWeek");
			runWeek.setColumnName("RUNWEEK_");
			runWeek.setJavaType("Integer");
			runWeek.setSystemFlag("Y");
			runWeek.setTitle("周");
			tableDefinition.addColumn(runWeek);

			ColumnDefinition runQuarter = new ColumnDefinition();
			runQuarter.setName("runQuarter");
			runQuarter.setColumnName("RUNQUARTER_");
			runQuarter.setJavaType("Integer");
			runQuarter.setSystemFlag("Y");
			runQuarter.setTitle("季度");
			tableDefinition.addColumn(runQuarter);

			ColumnDefinition runDay = new ColumnDefinition();
			runDay.setName("runDay");
			runDay.setColumnName("RUNDAY_");
			runDay.setJavaType("Integer");
			runDay.setSystemFlag("Y");
			runDay.setTitle("运行日期");
			tableDefinition.addColumn(runDay);

			ColumnDefinition jobNo = new ColumnDefinition();
			jobNo.setName("jobNo");
			jobNo.setColumnName("JOBNO_");
			jobNo.setJavaType("String");
			jobNo.setLength(50);
			jobNo.setSystemFlag("Y");
			jobNo.setTitle("批次号");
			tableDefinition.addColumn(jobNo);

			ColumnDefinition operation = new ColumnDefinition();
			operation.setName("operation");
			operation.setColumnName("OPERATION_");
			operation.setJavaType("String");
			operation.setLength(50);
			operation.setSystemFlag("Y");
			operation.setTitle("操作");
			tableDefinition.addColumn(operation);

			ColumnDefinition createBy = new ColumnDefinition();
			createBy.setName("createBy");
			createBy.setColumnName("CREATEBY_");
			createBy.setJavaType("String");
			createBy.setLength(50);
			createBy.setSystemFlag("Y");
			createBy.setTitle("创建人");
			tableDefinition.addColumn(createBy);

			ColumnDefinition createTime = new ColumnDefinition();
			createTime.setName("createTime");
			createTime.setColumnName("CREATETIME_");
			createTime.setJavaType("Date");
			createTime.setSystemFlag("Y");
			createTime.setTitle("创建日期");
			tableDefinition.addColumn(createTime);

			ColumnDefinition aggregationkey = new ColumnDefinition();
			aggregationkey.setName("aggregationKey");
			aggregationkey.setColumnName("AGGREGATIONKEY_");
			aggregationkey.setJavaType("String");
			aggregationkey.setLength(500);
			aggregationkey.setSystemFlag("Y");
			aggregationkey.setTitle("聚合键");
			tableDefinition.addColumn(aggregationkey);
		}
		Tools.populate(tableDefinition, params);
		tableDefinition.setTitle(request.getParameter("title"));
		tableDefinition.setDescription(request.getParameter("description"));
		tableDefinition.setPrimaryKey(request.getParameter("primaryKey"));
		for (ColumnDefinition column : tableDefinition.getColumns()) {
			String columnName = column.getColumnName();
			String param = columnName + "_length";
			if (StringUtils.isNotEmpty(RequestUtils.getParameter(request, param))) {
				column.setLength(RequestUtils.getInt(request, param));
			}
			param = columnName + "_name";
			if (StringUtils.isNotEmpty(RequestUtils.getParameter(request, param))) {
				column.setName(RequestUtils.getParameter(request, param));
			}
			param = columnName + "_title";
			if (StringUtils.isNotEmpty(RequestUtils.getParameter(request, param))) {
				column.setTitle(RequestUtils.getParameter(request, param));
			}
		}

		logger.debug("save table...");
		tableDefinition.setType(Constants.ETL_TABLE_TYPE);
		tableDefinitionService.save(tableDefinition);

		if ("alterTable".equals(actionType)) {
			logger.debug("alterTable...");
			tableDefinition = tableDefinitionService.getTableDefinition(tableName);
			if (tableDefinition != null) {
				List<ColumnDefinition> columns = tableDefinition.getColumns();
				Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
				if (columns != null && !columns.isEmpty()) {
					for (ColumnDefinition column : columns) {
						columnMap.put(column.getColumnName().toLowerCase(), column);
					}
				}
				List<ColumnDefinition> addColumns = new ArrayList<ColumnDefinition>();
				Collection<String> datasetList = new HashSet<String>();
				String datasetIds = tableDefinition.getDatasetIds();
				if (StringUtils.isNotEmpty(datasetIds)) {
					Collection<String> list = StringTools.split(datasetIds);
					if (list != null && !list.isEmpty()) {
						for (String tmp : list) {
							datasetList.add(tmp);
						}
					}
				}
				datasetIds = tableDefinition.getAggregationDatasetIds();
				if (StringUtils.isNotEmpty(datasetIds)) {
					Collection<String> list = StringTools.split(datasetIds);
					if (list != null && !list.isEmpty()) {
						for (String tmp : list) {
							datasetList.add(tmp);
						}
					}
				}
				if (!datasetList.isEmpty()) {
					for (String tmp : datasetList) {
						DataSet dataset = dataSetService.getDataSet(tmp);
						List<SelectSegment> selectSegments = dataset.getSelectSegments();
						if (selectSegments != null && !selectSegments.isEmpty()) {
							for (SelectSegment seg : selectSegments) {
								List<ColumnDefinition> cols = TableFactory.getColumnDefinitions(seg.getTableName());
								if (StringUtils.equals(seg.getOutput(), "true")
										&& !columnMap.containsKey(seg.getColumnName().toLowerCase())) {
									for (ColumnDefinition col : cols) {
										if (StringUtils.equalsIgnoreCase(seg.getColumnName(), col.getColumnName())) {
											col.setName(seg.getColumnLabel());
											col.setTitle(seg.getTitle());
											col.setColumnName(seg.getColumnName());
											col.setSystemFlag("Y");
											col.setTableName(tableName);
											addColumns.add(col);
											break;
										}
									}
								}
							}
						}
					}
				}
				if (addColumns.size() > 0) {
					tableDefinitionService.insertColumns(tableName, addColumns);
				}

				tableDefinition = tableDefinitionService.getTableDefinition(tableName);
				if (DBUtils.tableExists(tableName)) {
					DBUtils.alterTable(tableDefinition);
				} else {
					DBUtils.createTable(tableDefinition);
				}
			}
		}
		return ResponseUtils.responseJsonResult(true);
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
	public void setTableDefinitionService(ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@GET
	@POST
	@Path("/tablePage")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] tablePage(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);

		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		if (!DBUtils.isAllowedTable(tableName)) {
			try {
				return responseJSON.toString().getBytes("UTF-8");
			} catch (IOException e) {
				return responseJSON.toString().getBytes();
			}
		}

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
		if ("easyui".equals(gridType)) {
			int pageNo = ParamUtils.getInt(params, "page");
			limit = ParamUtils.getInt(params, "rows");
			start = (pageNo - 1) * limit;
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "order");
		} else if ("extjs".equals(gridType)) {
			start = ParamUtils.getInt(params, "start");
			limit = ParamUtils.getInt(params, "limit");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		} else if ("yui".equals(gridType)) {
			start = ParamUtils.getInt(params, "startIndex");
			limit = ParamUtils.getInt(params, "results");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		}

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0 || limit > 10000) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		TablePageQuery query = new TablePageQuery();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.tableName(tableName);
		if (orderName != null) {
			if (StringUtils.equals(order, "asc")) {
				query.orderAsc(orderName);
			} else {
				query.orderDesc(orderName);
			}
		}

		int total = -1;
		List<Map<String, Object>> rows = null;

		try {
			total = tablePageService.getTableCount(query);
			if (total > 0) {
				rows = tablePageService.getTableData(query);
			}
		} catch (Exception ex) {
			logger.error(ex);
		}

		ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
		if (rows != null && !rows.isEmpty()) {
			responseJSON.put("total", total);
			for (Map<String, Object> dataMap : rows) {
				ObjectNode rowJSON = new ObjectMapper().createObjectNode();
				if (dataMap != null && dataMap.size() > 0) {
					Set<Entry<String, Object>> entrySet = dataMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Date) {
								Date date = (Date) value;
								rowJSON.put(name, DateUtils.getDateTime(date));
							} else {
								rowJSON.put(name, value.toString());
							}
						} else {
							rowJSON.put(name, "");
						}
					}
				}
				rowsJSON.add(rowJSON);
			}
		}

		if ("yui".equals(gridType)) {
			responseJSON.set("records", rowsJSON);
		} else {
			responseJSON.set("rows", rowsJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@GET
	@POST
	@Path("/transform")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] transform(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = tableName.toLowerCase();
			if (StringUtils.startsWith(tableName, "mx_") || StringUtils.startsWith(tableName, "sys_")
					|| StringUtils.startsWith(tableName, "jbpm_") || StringUtils.startsWith(tableName, "act_")) {
				return ResponseUtils.responseJsonResult(false);
			}
			AggregationTableExecution execution = new AggregationTableExecution();
			execution.execute(RequestUtils.getActorId(request), tableName);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableDefinitionQuery query = new TableDefinitionQuery();
		Tools.populate(query, params);
		String tableName = request.getParameter("tableName");
		String tableName_enc = request.getParameter("tableName_enc");
		if (StringUtils.isNotEmpty(tableName_enc)) {
			tableName = RequestUtils.decodeString(tableName_enc);
		}
		TableDefinition tableDefinition = tableDefinitionService.getTableDefinition(tableName);
		ObjectNode responseJSON = tableDefinition.toObjectNode();
		ArrayNode columnsJSON = new ObjectMapper().createArrayNode();
		responseJSON.set("columns", columnsJSON);

		for (ColumnDefinition column : tableDefinition.getColumns()) {
			ObjectNode columnJSON = column.toObjectNode();
			columnsJSON.add(columnJSON);
		}

		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

}