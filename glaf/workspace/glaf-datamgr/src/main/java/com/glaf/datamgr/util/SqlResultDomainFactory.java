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
public class SqlResultDomainFactory {

	public static final String TABLENAME = "SYS_SQL_RESULT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("databaseId", "DATABASEID_");
		columnMap.put("sqlDefId", "SQLDEFID_");
		columnMap.put("projectId", "PROJECTID_");
		columnMap.put("count", "COUNT_");
		columnMap.put("value", "VALUE_");
		columnMap.put("ext1", "EXT1_");
		columnMap.put("ext2", "EXT2_");
		columnMap.put("ext3", "EXT3_");
		columnMap.put("ext4", "EXT4_");
		columnMap.put("ext5", "EXT5_");
		columnMap.put("ext6", "EXT6_");
		columnMap.put("ext7", "EXT7_");
		columnMap.put("ext8", "EXT8_");
		columnMap.put("ext9", "EXT9_");
		columnMap.put("ext10", "EXT10_");
		columnMap.put("ext11", "EXT11_");
		columnMap.put("ext12", "EXT12_");
		columnMap.put("ext13", "EXT13_");
		columnMap.put("ext14", "EXT14_");
		columnMap.put("ext15", "EXT15_");
		columnMap.put("ext16", "EXT16_");
		columnMap.put("ext17", "EXT17_");
		columnMap.put("ext18", "EXT18_");
		columnMap.put("ext19", "EXT19_");
		columnMap.put("ext20", "EXT20_");
		columnMap.put("ext101", "EXT101_");
		columnMap.put("ext102", "EXT102_");
		columnMap.put("ext103", "EXT103_");
		columnMap.put("ext104", "EXT104_");
		columnMap.put("ext105", "EXT105_");
		columnMap.put("ext106", "EXT106_");
		columnMap.put("ext107", "EXT107_");
		columnMap.put("ext108", "EXT108_");
		columnMap.put("ext109", "EXT109_");
		columnMap.put("ext110", "EXT110_");
		columnMap.put("ext111", "EXT111_");
		columnMap.put("ext112", "EXT112_");
		columnMap.put("ext113", "EXT113_");
		columnMap.put("ext114", "EXT114_");
		columnMap.put("ext115", "EXT115_");
		columnMap.put("ext116", "EXT116_");
		columnMap.put("ext117", "EXT117_");
		columnMap.put("ext118", "EXT118_");
		columnMap.put("ext119", "EXT119_");
		columnMap.put("ext120", "EXT120_");
		columnMap.put("ext201", "EXT201_");
		columnMap.put("ext202", "EXT202_");
		columnMap.put("ext203", "EXT203_");
		columnMap.put("ext204", "EXT204_");
		columnMap.put("ext205", "EXT205_");
		columnMap.put("ext206", "EXT206_");
		columnMap.put("ext207", "EXT207_");
		columnMap.put("ext208", "EXT208_");
		columnMap.put("ext209", "EXT209_");
		columnMap.put("ext210", "EXT210_");
		columnMap.put("ext301", "EXT301_");
		columnMap.put("ext302", "EXT302_");
		columnMap.put("ext303", "EXT303_");
		columnMap.put("ext304", "EXT304_");
		columnMap.put("ext305", "EXT305_");
		columnMap.put("ext306", "EXT306_");
		columnMap.put("ext307", "EXT307_");
		columnMap.put("ext308", "EXT308_");
		columnMap.put("ext309", "EXT309_");
		columnMap.put("ext310", "EXT310_");
		columnMap.put("type", "TYPE_");
		columnMap.put("runYear", "RUNYEAR_");
		columnMap.put("runMonth", "RUNMONTH_");
		columnMap.put("runWeek", "RUNWEEK_");
		columnMap.put("runQuarter", "RUNQUARTER_");
		columnMap.put("runDay", "RUNDAY_");
		columnMap.put("jobNo", "JOBNO_");
		columnMap.put("operation", "OPERATION_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("databaseId", "Long");
		javaTypeMap.put("sqlDefId", "Long");
		javaTypeMap.put("projectId", "Long");
		javaTypeMap.put("count", "Integer");
		javaTypeMap.put("value", "Double");
		javaTypeMap.put("ext1", "String");
		javaTypeMap.put("ext2", "String");
		javaTypeMap.put("ext3", "String");
		javaTypeMap.put("ext4", "String");
		javaTypeMap.put("ext5", "String");
		javaTypeMap.put("ext6", "String");
		javaTypeMap.put("ext7", "String");
		javaTypeMap.put("ext8", "String");
		javaTypeMap.put("ext9", "String");
		javaTypeMap.put("ext10", "String");
		javaTypeMap.put("ext11", "String");
		javaTypeMap.put("ext12", "String");
		javaTypeMap.put("ext13", "String");
		javaTypeMap.put("ext14", "String");
		javaTypeMap.put("ext15", "String");
		javaTypeMap.put("ext16", "String");
		javaTypeMap.put("ext17", "String");
		javaTypeMap.put("ext18", "String");
		javaTypeMap.put("ext19", "String");
		javaTypeMap.put("ext20", "String");
		javaTypeMap.put("ext101", "Double");
		javaTypeMap.put("ext102", "Double");
		javaTypeMap.put("ext103", "Double");
		javaTypeMap.put("ext104", "Double");
		javaTypeMap.put("ext105", "Double");
		javaTypeMap.put("ext106", "Double");
		javaTypeMap.put("ext107", "Double");
		javaTypeMap.put("ext108", "Double");
		javaTypeMap.put("ext109", "Double");
		javaTypeMap.put("ext110", "Double");
		javaTypeMap.put("ext111", "Double");
		javaTypeMap.put("ext112", "Double");
		javaTypeMap.put("ext113", "Double");
		javaTypeMap.put("ext114", "Double");
		javaTypeMap.put("ext115", "Double");
		javaTypeMap.put("ext116", "Double");
		javaTypeMap.put("ext117", "Double");
		javaTypeMap.put("ext118", "Double");
		javaTypeMap.put("ext119", "Double");
		javaTypeMap.put("ext120", "Double");
		javaTypeMap.put("ext201", "Long");
		javaTypeMap.put("ext202", "Long");
		javaTypeMap.put("ext203", "Long");
		javaTypeMap.put("ext204", "Long");
		javaTypeMap.put("ext205", "Long");
		javaTypeMap.put("ext206", "Long");
		javaTypeMap.put("ext207", "Long");
		javaTypeMap.put("ext208", "Long");
		javaTypeMap.put("ext209", "Long");
		javaTypeMap.put("ext210", "Long");
		javaTypeMap.put("ext301", "Integer");
		javaTypeMap.put("ext302", "Integer");
		javaTypeMap.put("ext303", "Integer");
		javaTypeMap.put("ext304", "Integer");
		javaTypeMap.put("ext305", "Integer");
		javaTypeMap.put("ext306", "Integer");
		javaTypeMap.put("ext307", "Integer");
		javaTypeMap.put("ext308", "Integer");
		javaTypeMap.put("ext309", "Integer");
		javaTypeMap.put("ext310", "Integer");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("runYear", "Integer");
		javaTypeMap.put("runMonth", "Integer");
		javaTypeMap.put("runWeek", "Integer");
		javaTypeMap.put("runQuarter", "Integer");
		javaTypeMap.put("runDay", "Integer");
		javaTypeMap.put("jobNo", "String");
		javaTypeMap.put("operation", "String");
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
		tableDefinition.setName("SqlResult");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

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

		ColumnDefinition projectId = new ColumnDefinition();
		projectId.setName("projectId");
		projectId.setColumnName("PROJECTID_");
		projectId.setJavaType("Long");
		tableDefinition.addColumn(projectId);

		ColumnDefinition count = new ColumnDefinition();
		count.setName("count");
		count.setColumnName("COUNT_");
		count.setJavaType("Integer");
		tableDefinition.addColumn(count);

		ColumnDefinition value = new ColumnDefinition();
		value.setName("value");
		value.setColumnName("VALUE_");
		value.setJavaType("Double");
		tableDefinition.addColumn(value);

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

		ColumnDefinition operation = new ColumnDefinition();
		operation.setName("operation");
		operation.setColumnName("OPERATION_");
		operation.setJavaType("String");
		operation.setLength(50);
		tableDefinition.addColumn(operation);

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

	public static TableDefinition createTable(String tableName, List<ColumnDefinition> columns) {
		TableDefinition tableDefinition = getTableDefinition(tableName);
		if (columns != null && !columns.isEmpty()) {
			for (ColumnDefinition column : columns) {
				tableDefinition.addColumn(column);
			}
		}
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

	private SqlResultDomainFactory() {

	}

}
