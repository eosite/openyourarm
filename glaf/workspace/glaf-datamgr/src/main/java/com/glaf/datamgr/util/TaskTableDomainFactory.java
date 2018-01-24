package com.glaf.datamgr.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class TaskTableDomainFactory {

	public static final String TABLENAME = "SYS_TASK_TABLE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("title", "TITLE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("tableName", "TABLENAME_");
		columnMap.put("idColumn", "IDCOLUMN_");
		columnMap.put("nameColumn", "NAMECOLUMN_");
		columnMap.put("nameExpression", "NAMEEXPRESSION_");
		columnMap.put("valueColumn", "VALUECOLUMN_");
		columnMap.put("valueExpression", "VALUEEXPRESSION_");
		columnMap.put("typeColumn", "TYPECOLUMN_");
		columnMap.put("startDateColumn", "STARTDATECOLUMN_");
		columnMap.put("endDateColumn", "ENDDATECOLUMN_");
		columnMap.put("databaseIds", "DATABASEIDS_");
		columnMap.put("syncColumns", "SYNCCOLUMNS_");
		columnMap.put("startTime", "STARTTIME_");
		columnMap.put("endTime", "ENDTIME_");
		columnMap.put("frequency", "FREQUENCY_");
		columnMap.put("executeDay", "EXECUTEDAY_");
		columnMap.put("sortNo", "SORTNO_");
		columnMap.put("deleteFlag", "DELETEFLAG_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("tableName", "String");
		javaTypeMap.put("idColumn", "String");
		javaTypeMap.put("nameColumn", "String");
		javaTypeMap.put("nameExpression", "String");
		javaTypeMap.put("valueColumn", "String");
		javaTypeMap.put("valueExpression", "String");
		javaTypeMap.put("typeColumn", "String");
		javaTypeMap.put("startDateColumn", "String");
		javaTypeMap.put("endDateColumn", "String");
		javaTypeMap.put("databaseIds", "String");
		javaTypeMap.put("syncColumns", "String");
		javaTypeMap.put("startTime", "Date");
		javaTypeMap.put("endTime", "Date");
		javaTypeMap.put("frequency", "Integer");
		javaTypeMap.put("executeDay", "Integer");
		javaTypeMap.put("sortNo", "Integer");
		javaTypeMap.put("deleteFlag", "Integer");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("updateBy", "String");
		javaTypeMap.put("updateTime", "Date");
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
		tableDefinition.setName("TaskTable");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(200);
		tableDefinition.addColumn(title);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(20);
		tableDefinition.addColumn(type);

		ColumnDefinition tableName_ = new ColumnDefinition();
		tableName_.setName("tableName");
		tableName_.setColumnName("TABLENAME_");
		tableName_.setJavaType("String");
		tableName_.setLength(50);
		tableDefinition.addColumn(tableName_);

		ColumnDefinition idColumn_ = new ColumnDefinition();
		idColumn_.setName("idColumn");
		idColumn_.setColumnName("IDCOLUMN_");
		idColumn_.setJavaType("String");
		idColumn_.setLength(50);
		tableDefinition.addColumn(idColumn_);

		ColumnDefinition nameColumn = new ColumnDefinition();
		nameColumn.setName("nameColumn");
		nameColumn.setColumnName("NAMECOLUMN_");
		nameColumn.setJavaType("String");
		nameColumn.setLength(250);
		tableDefinition.addColumn(nameColumn);

		ColumnDefinition nameExpression = new ColumnDefinition();
		nameExpression.setName("nameExpression");
		nameExpression.setColumnName("NAMEEXPRESSION_");
		nameExpression.setJavaType("String");
		nameExpression.setLength(250);
		tableDefinition.addColumn(nameExpression);

		ColumnDefinition valueColumn = new ColumnDefinition();
		valueColumn.setName("valueColumn");
		valueColumn.setColumnName("VALUECOLUMN_");
		valueColumn.setJavaType("String");
		valueColumn.setLength(250);
		tableDefinition.addColumn(valueColumn);

		ColumnDefinition valueExpression = new ColumnDefinition();
		valueExpression.setName("valueExpression");
		valueExpression.setColumnName("VALUEEXPRESSION_");
		valueExpression.setJavaType("String");
		valueExpression.setLength(250);
		tableDefinition.addColumn(valueExpression);

		ColumnDefinition typeColumn = new ColumnDefinition();
		typeColumn.setName("typeColumn");
		typeColumn.setColumnName("TYPECOLUMN_");
		typeColumn.setJavaType("String");
		typeColumn.setLength(250);
		tableDefinition.addColumn(typeColumn);

		ColumnDefinition startDateColumn = new ColumnDefinition();
		startDateColumn.setName("startDateColumn");
		startDateColumn.setColumnName("STARTDATECOLUMN_");
		startDateColumn.setJavaType("String");
		startDateColumn.setLength(50);
		tableDefinition.addColumn(startDateColumn);

		ColumnDefinition endDateColumn = new ColumnDefinition();
		endDateColumn.setName("endDateColumn");
		endDateColumn.setColumnName("ENDDATECOLUMN_");
		endDateColumn.setJavaType("String");
		endDateColumn.setLength(50);
		tableDefinition.addColumn(endDateColumn);

		ColumnDefinition databaseIds = new ColumnDefinition();
		databaseIds.setName("databaseIds");
		databaseIds.setColumnName("DATABASEIDS_");
		databaseIds.setJavaType("String");
		databaseIds.setLength(2000);
		tableDefinition.addColumn(databaseIds);

		ColumnDefinition syncColumns = new ColumnDefinition();
		syncColumns.setName("syncColumns");
		syncColumns.setColumnName("SYNCCOLUMNS_");
		syncColumns.setJavaType("String");
		syncColumns.setLength(4000);
		tableDefinition.addColumn(syncColumns);

		ColumnDefinition startTime = new ColumnDefinition();
		startTime.setName("startTime");
		startTime.setColumnName("STARTTIME_");
		startTime.setJavaType("Date");
		tableDefinition.addColumn(startTime);

		ColumnDefinition endTime = new ColumnDefinition();
		endTime.setName("endTime");
		endTime.setColumnName("ENDTIME_");
		endTime.setJavaType("Date");
		tableDefinition.addColumn(endTime);

		ColumnDefinition frequency = new ColumnDefinition();
		frequency.setName("frequency");
		frequency.setColumnName("FREQUENCY_");
		frequency.setJavaType("Integer");
		tableDefinition.addColumn(frequency);

		ColumnDefinition executeDay = new ColumnDefinition();
		executeDay.setName("executeDay");
		executeDay.setColumnName("EXECUTEDAY_");
		executeDay.setJavaType("Integer");
		tableDefinition.addColumn(executeDay);

		ColumnDefinition sortNo = new ColumnDefinition();
		sortNo.setName("sortNo");
		sortNo.setColumnName("SORTNO_");
		sortNo.setJavaType("Integer");
		tableDefinition.addColumn(sortNo);

		ColumnDefinition deleteFlag = new ColumnDefinition();
		deleteFlag.setName("deleteFlag");
		deleteFlag.setColumnName("DELETEFLAG_");
		deleteFlag.setJavaType("Integer");
		tableDefinition.addColumn(deleteFlag);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

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

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY_");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

		ColumnDefinition updateTime = new ColumnDefinition();
		updateTime.setName("updateTime");
		updateTime.setColumnName("UPDATETIME_");
		updateTime.setJavaType("Date");
		tableDefinition.addColumn(updateTime);

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

	private TaskTableDomainFactory() {

	}

}
