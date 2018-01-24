package com.glaf.base.usertask.util;

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
public class UserTaskDomainFactory {

	public static final String TABLENAME = "USER_TASK";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("id", "ID");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("actorId", "ACTORID");
		columnMap.put("name", "NAME");
		columnMap.put("indexName", "INDEX_NAME");
		columnMap.put("activityId", "ACTIVITYID");
		columnMap.put("startTime", "STARTTIME");
		columnMap.put("endTime", "ENDTIME");
		columnMap.put("state", "STATE");
		columnMap.put("timelimit", "TIMELIMIT");
		columnMap.put("lastModified", "LASTMODIFIED");
		columnMap.put("listno", "LISTNO");
		columnMap.put("level", "NLEVEL");

		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("id", "String");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("actorId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("indexName", "String");
		javaTypeMap.put("activityId", "String");
		javaTypeMap.put("startTime", "Date");
		javaTypeMap.put("endTime", "Date");
		javaTypeMap.put("state", "Integer");
		javaTypeMap.put("timelimit", "Integer");
		javaTypeMap.put("lastModified", "Long");
		javaTypeMap.put("listno", "Integer");
		javaTypeMap.put("level", "Integer");
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
		tableDefinition.setName("UserTask");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("indexId");
		idColumn.setColumnName("INDEX_ID");
		idColumn.setJavaType("Integer");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition id = new ColumnDefinition();
		id.setName("id");
		id.setColumnName("ID");
		id.setJavaType("String");
		id.setLength(200);
		tableDefinition.addColumn(id);

		ColumnDefinition parentId = new ColumnDefinition();
		parentId.setName("parentId");
		parentId.setColumnName("PARENT_ID");
		parentId.setJavaType("Integer");
		tableDefinition.addColumn(parentId);

		ColumnDefinition actorId = new ColumnDefinition();
		actorId.setName("actorId");
		actorId.setColumnName("ACTORID");
		actorId.setJavaType("String");
		actorId.setLength(50);
		tableDefinition.addColumn(actorId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME");
		name.setJavaType("String");
		name.setLength(200);
		tableDefinition.addColumn(name);

		ColumnDefinition indexName = new ColumnDefinition();
		indexName.setName("indexName");
		indexName.setColumnName("INDEX_NAME");
		indexName.setJavaType("String");
		indexName.setLength(200);
		tableDefinition.addColumn(indexName);

		ColumnDefinition activityId = new ColumnDefinition();
		activityId.setName("activityId");
		activityId.setColumnName("ACTIVITYID");
		activityId.setJavaType("String");
		activityId.setLength(50);
		tableDefinition.addColumn(activityId);

		ColumnDefinition startTime = new ColumnDefinition();
		startTime.setName("startTime");
		startTime.setColumnName("STARTTIME");
		startTime.setJavaType("Date");
		tableDefinition.addColumn(startTime);

		ColumnDefinition endTime = new ColumnDefinition();
		endTime.setName("endTime");
		endTime.setColumnName("ENDTIME");
		endTime.setJavaType("Date");
		tableDefinition.addColumn(endTime);

		ColumnDefinition state = new ColumnDefinition();
		state.setName("state");
		state.setColumnName("STATE");
		state.setJavaType("Integer");
		tableDefinition.addColumn(state);

		ColumnDefinition timelimit = new ColumnDefinition();
		timelimit.setName("timelimit");
		timelimit.setColumnName("TIMELIMIT");
		timelimit.setJavaType("Integer");
		tableDefinition.addColumn(timelimit);

		ColumnDefinition lastModified = new ColumnDefinition();
		lastModified.setName("lastModified");
		lastModified.setColumnName("LASTMODIFIED");
		lastModified.setJavaType("Long");
		tableDefinition.addColumn(lastModified);

		ColumnDefinition listno = new ColumnDefinition();
		listno.setName("listno");
		listno.setColumnName("LISTNO");
		listno.setJavaType("Integer");
		tableDefinition.addColumn(listno);

		ColumnDefinition level = new ColumnDefinition();
		level.setName("level");
		level.setColumnName("NLEVEL");
		level.setJavaType("Integer");
		tableDefinition.addColumn(level);

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

	private UserTaskDomainFactory() {

	}

}
