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
public class TableSyntheticDomainFactory {

	public static final String TABLENAME = "SYS_TABLE_SYNTHETIC";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("name", "NAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("syncStatus", "SYNCSTATUS_");
		columnMap.put("syncTime", "SYNCTIME_");
		columnMap.put("sourceDatabaseIds", "SOURCEDATABASEIDS_");
		columnMap.put("sourceTableName", "SOURCETABLENAME_");
		columnMap.put("primaryKey", "PRIMARYKEY_");
		columnMap.put("syncColumns", "SYNCCOLUMNS_");
		columnMap.put("sqlCriteria", "SQLCRITERIA_");
		columnMap.put("targetTableName", "TARGETTABLENAME_");
		columnMap.put("targetDatabaseId", "TARGETDATABASEID_");
		columnMap.put("scheduleFlag", "SCHEDULEFLAG_");
		columnMap.put("createTableFlag", "CREATETABLEFLAG_");
		columnMap.put("deleteFetch", "DELETEFETCH_");
		columnMap.put("insertOnly", "INSERTONLY_");
		columnMap.put("jobNo", "JOBNO_");
		columnMap.put("sortNo", "SORTNO_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("syncStatus", "Integer");
		javaTypeMap.put("syncTime", "Date");
		javaTypeMap.put("sourceDatabaseIds", "String");
		javaTypeMap.put("sourceTableName", "String");
		javaTypeMap.put("primaryKey", "String");
		javaTypeMap.put("syncColumns", "String");
		javaTypeMap.put("sqlCriteria", "String");
		javaTypeMap.put("targetTableName", "String");
		javaTypeMap.put("targetDatabaseId", "Long");
		javaTypeMap.put("scheduleFlag", "String");
		javaTypeMap.put("createTableFlag", "String");
		javaTypeMap.put("deleteFetch", "String");
		javaTypeMap.put("insertOnly", "String");
		javaTypeMap.put("jobNo", "String");
		javaTypeMap.put("sortNo", "Integer");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("updateBy", "String");
		javaTypeMap.put("updateTime", "Date");
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
		tableDefinition.setName("TableSynthetic");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(50);
		tableDefinition.addColumn(name);

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

		ColumnDefinition syncStatus = new ColumnDefinition();
		syncStatus.setName("syncStatus");
		syncStatus.setColumnName("SYNCSTATUS_");
		syncStatus.setJavaType("Integer");
		tableDefinition.addColumn(syncStatus);

		ColumnDefinition syncTime = new ColumnDefinition();
		syncTime.setName("syncTime");
		syncTime.setColumnName("SYNCTIME_");
		syncTime.setJavaType("Date");
		tableDefinition.addColumn(syncTime);

		ColumnDefinition sourceDatabaseIds = new ColumnDefinition();
		sourceDatabaseIds.setName("sourceDatabaseIds");
		sourceDatabaseIds.setColumnName("SOURCEDATABASEIDS_");
		sourceDatabaseIds.setJavaType("String");
		sourceDatabaseIds.setLength(2000);
		tableDefinition.addColumn(sourceDatabaseIds);

		ColumnDefinition sourceTableName = new ColumnDefinition();
		sourceTableName.setName("sourceTableName");
		sourceTableName.setColumnName("SOURCETABLENAME_");
		sourceTableName.setJavaType("String");
		sourceTableName.setLength(50);
		tableDefinition.addColumn(sourceTableName);

		ColumnDefinition primaryKey = new ColumnDefinition();
		primaryKey.setName("primaryKey");
		primaryKey.setColumnName("PRIMARYKEY_");
		primaryKey.setJavaType("String");
		primaryKey.setLength(50);
		tableDefinition.addColumn(primaryKey);

		ColumnDefinition syncColumns = new ColumnDefinition();
		syncColumns.setName("syncColumns");
		syncColumns.setColumnName("SYNCCOLUMNS_");
		syncColumns.setJavaType("String");
		syncColumns.setLength(2000);
		tableDefinition.addColumn(syncColumns);

		ColumnDefinition sqlCriteria = new ColumnDefinition();
		sqlCriteria.setName("sqlCriteria");
		sqlCriteria.setColumnName("SQLCRITERIA_");
		sqlCriteria.setJavaType("String");
		sqlCriteria.setLength(4000);
		tableDefinition.addColumn(sqlCriteria);

		ColumnDefinition targetTableName = new ColumnDefinition();
		targetTableName.setName("targetTableName");
		targetTableName.setColumnName("TARGETTABLENAME_");
		targetTableName.setJavaType("String");
		targetTableName.setLength(50);
		tableDefinition.addColumn(targetTableName);

		ColumnDefinition targetDatabaseId = new ColumnDefinition();
		targetDatabaseId.setName("targetDatabaseId");
		targetDatabaseId.setColumnName("TARGETDATABASEID_");
		targetDatabaseId.setJavaType("Long");
		tableDefinition.addColumn(targetDatabaseId);

		ColumnDefinition scheduleFlag = new ColumnDefinition();
		scheduleFlag.setName("scheduleFlag");
		scheduleFlag.setColumnName("SCHEDULEFLAG_");
		scheduleFlag.setJavaType("String");
		scheduleFlag.setLength(1);
		tableDefinition.addColumn(scheduleFlag);

		ColumnDefinition createTableFlag = new ColumnDefinition();
		createTableFlag.setName("createTableFlag");
		createTableFlag.setColumnName("CREATETABLEFLAG_");
		createTableFlag.setJavaType("String");
		createTableFlag.setLength(1);
		tableDefinition.addColumn(createTableFlag);

		ColumnDefinition deleteFetch = new ColumnDefinition();
		deleteFetch.setName("deleteFetch");
		deleteFetch.setColumnName("DELETEFETCH_");
		deleteFetch.setJavaType("String");
		deleteFetch.setLength(1);
		tableDefinition.addColumn(deleteFetch);

		ColumnDefinition insertOnly = new ColumnDefinition();
		insertOnly.setName("insertOnly");
		insertOnly.setColumnName("INSERTONLY_");
		insertOnly.setJavaType("String");
		insertOnly.setLength(1);
		tableDefinition.addColumn(insertOnly);

		ColumnDefinition jobNo = new ColumnDefinition();
		jobNo.setName("jobNo");
		jobNo.setColumnName("JOBNO_");
		jobNo.setJavaType("String");
		jobNo.setLength(50);
		tableDefinition.addColumn(jobNo);

		ColumnDefinition sortNo = new ColumnDefinition();
		sortNo.setName("sortNo");
		sortNo.setColumnName("SORTNO_");
		sortNo.setJavaType("Integer");
		tableDefinition.addColumn(sortNo);

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

	private TableSyntheticDomainFactory() {

	}

}
