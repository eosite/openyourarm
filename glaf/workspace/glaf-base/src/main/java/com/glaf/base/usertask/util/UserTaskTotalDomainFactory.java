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
public class UserTaskTotalDomainFactory {

	public static final String TABLENAME = "USER_TASK_TOTAL";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("userId", "USERID_");
		columnMap.put("name", "NAME_");
		columnMap.put("databaseId", "DATABASEID_");
		columnMap.put("total", "TOTAL_");
		columnMap.put("finished", "FINISHED_");
		columnMap.put("pending", "PENDING_");
		columnMap.put("quantity1", "QUANTITY1_");
		columnMap.put("quantity2", "QUANTITY2_");
		columnMap.put("quantity3", "QUANTITY3_");
		columnMap.put("quantity4", "QUANTITY4_");
		columnMap.put("quantity5", "QUANTITY5_");
		columnMap.put("quantity6", "QUANTITY6_");
		columnMap.put("type", "TYPE_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("createBy", "CREATEBY_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("databaseId", "Long");
		javaTypeMap.put("total", "Integer");
		javaTypeMap.put("finished", "Integer");
		javaTypeMap.put("pending", "Integer");
		javaTypeMap.put("quantity1", "Integer");
		javaTypeMap.put("quantity2", "Integer");
		javaTypeMap.put("quantity3", "Integer");
		javaTypeMap.put("quantity4", "Integer");
		javaTypeMap.put("quantity5", "Integer");
		javaTypeMap.put("quantity6", "Integer");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("createBy", "String");
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
		tableDefinition.setName("UserTaskTotal");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID_");
		userId.setJavaType("String");
		userId.setLength(50);
		tableDefinition.addColumn(userId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(200);
		tableDefinition.addColumn(name);

		ColumnDefinition databaseId = new ColumnDefinition();
		databaseId.setName("databaseId");
		databaseId.setColumnName("DATABASEID_");
		databaseId.setJavaType("Long");
		tableDefinition.addColumn(databaseId);

		ColumnDefinition total = new ColumnDefinition();
		total.setName("total");
		total.setColumnName("TOTAL_");
		total.setJavaType("Integer");
		tableDefinition.addColumn(total);

		ColumnDefinition finished = new ColumnDefinition();
		finished.setName("finished");
		finished.setColumnName("FINISHED_");
		finished.setJavaType("Integer");
		tableDefinition.addColumn(finished);

		ColumnDefinition pending = new ColumnDefinition();
		pending.setName("pending");
		pending.setColumnName("PENDING_");
		pending.setJavaType("Integer");
		tableDefinition.addColumn(pending);

		ColumnDefinition quantity1 = new ColumnDefinition();
		quantity1.setName("quantity1");
		quantity1.setColumnName("QUANTITY1_");
		quantity1.setJavaType("Integer");
		tableDefinition.addColumn(quantity1);

		ColumnDefinition quantity2 = new ColumnDefinition();
		quantity2.setName("quantity2");
		quantity2.setColumnName("QUANTITY2_");
		quantity2.setJavaType("Integer");
		tableDefinition.addColumn(quantity2);

		ColumnDefinition quantity3 = new ColumnDefinition();
		quantity3.setName("quantity3");
		quantity3.setColumnName("QUANTITY3_");
		quantity3.setJavaType("Integer");
		tableDefinition.addColumn(quantity3);

		ColumnDefinition quantity4 = new ColumnDefinition();
		quantity4.setName("quantity4");
		quantity4.setColumnName("QUANTITY4_");
		quantity4.setJavaType("Integer");
		tableDefinition.addColumn(quantity4);

		ColumnDefinition quantity5 = new ColumnDefinition();
		quantity5.setName("quantity5");
		quantity5.setColumnName("QUANTITY5_");
		quantity5.setJavaType("Integer");
		tableDefinition.addColumn(quantity5);

		ColumnDefinition quantity6 = new ColumnDefinition();
		quantity6.setName("quantity6");
		quantity6.setColumnName("QUANTITY6_");
		quantity6.setJavaType("Integer");
		tableDefinition.addColumn(quantity6);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

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

	private UserTaskTotalDomainFactory() {

	}

}
