package com.glaf.datamgr.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class SqlTransportLogDomainFactory {

	public static final String TABLENAME = "SQL_TRANS_LOG";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("projectId", "PROJECTID_");
		columnMap.put("databaseId", "DATABASEID_");
		columnMap.put("sqlDefId", "SQLDEFID_");
		columnMap.put("status", "STATUS_");
		columnMap.put("type", "TYPE_");
		columnMap.put("runYear", "RUNYEAR_");
		columnMap.put("runMonth", "RUNMONTH_");
		columnMap.put("runWeek", "RUNWEEK_");
		columnMap.put("runQuarter", "RUNQUARTER_");
		columnMap.put("runDay", "RUNDAY_");
		columnMap.put("jobNo", "JOBNO_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("projectId", "Long");
		javaTypeMap.put("databaseId", "Long");
		javaTypeMap.put("sqlDefId", "Long");
		javaTypeMap.put("status", "Integer");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("runYear", "Integer");
		javaTypeMap.put("runMonth", "Integer");
		javaTypeMap.put("runWeek", "Integer");
		javaTypeMap.put("runQuarter", "Integer");
		javaTypeMap.put("runDay", "Integer");
		javaTypeMap.put("jobNo", "String");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
	}

	public static Map<String, String> getColumnMap() {
		return columnMap;
	}

	public static Map<String, String> getJavaTypeMap() {
		return javaTypeMap;
	}

	public static TableDefinition getTableDefinition() {
		return getTableDefinition(TABLENAME);
	}

	public static TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setName("SqlTransportLog");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition projectId = new ColumnDefinition();
		projectId.setName("projectId");
		projectId.setColumnName("PROJECTID_");
		projectId.setJavaType("Long");
		tableDefinition.addColumn(projectId);

		ColumnDefinition databaseId = new ColumnDefinition();
		databaseId.setName("databaseId");
		databaseId.setColumnName("DATABASEID_");
		databaseId.setJavaType("Long");
		tableDefinition.addColumn(databaseId);

		ColumnDefinition sqlDefId = new ColumnDefinition();
		sqlDefId.setName("sqlDefId");
		sqlDefId.setColumnName("SQLDEFID_");
		sqlDefId.setJavaType("Long");
		tableDefinition.addColumn(sqlDefId);

		ColumnDefinition status = new ColumnDefinition();
		status.setName("status");
		status.setColumnName("STATUS_");
		status.setJavaType("Integer");
		tableDefinition.addColumn(status);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition runYear = new ColumnDefinition();
		runYear.setName("runYear");
		runYear.setColumnName("RUNYEAR_");
		runYear.setJavaType("Integer");
		tableDefinition.addColumn(runYear);

		ColumnDefinition runMonth = new ColumnDefinition();
		runMonth.setName("runMonth");
		runMonth.setColumnName("RUNMONTH_");
		runMonth.setJavaType("Integer");
		tableDefinition.addColumn(runMonth);

		ColumnDefinition runWeek = new ColumnDefinition();
		runWeek.setName("runWeek");
		runWeek.setColumnName("RUNWEEK_");
		runWeek.setJavaType("Integer");
		tableDefinition.addColumn(runWeek);

		ColumnDefinition runQuarter = new ColumnDefinition();
		runQuarter.setName("runQuarter");
		runQuarter.setColumnName("RUNQUARTER_");
		runQuarter.setJavaType("Integer");
		tableDefinition.addColumn(runQuarter);

		ColumnDefinition runDay = new ColumnDefinition();
		runDay.setName("runDay");
		runDay.setColumnName("RUNDAY_");
		runDay.setJavaType("Integer");
		tableDefinition.addColumn(runDay);

		ColumnDefinition jobNo = new ColumnDefinition();
		jobNo.setName("jobNo");
		jobNo.setColumnName("JOBNO_");
		jobNo.setJavaType("String");
		jobNo.setLength(50);
		tableDefinition.addColumn(jobNo);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		return tableDefinition;
	}

	public static TableDefinition createTable() {
		TableDefinition tableDefinition = getTableDefinition(TABLENAME);
		if (!DBUtils.tableExists(TABLENAME)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static TableDefinition createTable(String tableName) {
		TableDefinition tableDefinition = getTableDefinition(tableName);
		if (!DBUtils.tableExists(tableName)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static void processDataRequest(DataRequest dataRequest) {
		if (dataRequest != null) {
			if (dataRequest.getFilter() != null) {
				if (dataRequest.getFilter().getField() != null) {
					dataRequest.getFilter().setColumn(columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter().setJavaType(javaTypeMap.get(dataRequest.getFilter().getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter().getFilters();
				for (FilterDescriptor filter : filters) {
					filter.setParent(dataRequest.getFilter());
					if (filter.getField() != null) {
						filter.setColumn(columnMap.get(filter.getField()));
						filter.setJavaType(javaTypeMap.get(filter.getField()));
					}

					List<FilterDescriptor> subFilters = filter.getFilters();
					for (FilterDescriptor f : subFilters) {
						f.setColumn(columnMap.get(f.getField()));
						f.setJavaType(javaTypeMap.get(f.getField()));
						f.setParent(filter);
					}
				}
			}
		}
	}

	private SqlTransportLogDomainFactory() {

	}

}
