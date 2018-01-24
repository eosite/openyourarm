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
public class OperationDomainFactory {

	public static final String TABLENAME = "SYS_OPERATION";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("name", "NAME_");
		columnMap.put("code", "CODE_");
		columnMap.put("description", "DESCRIPTION_");
		columnMap.put("method", "METHOD_");
		columnMap.put("url", "URL_");
		columnMap.put("tablename", "TABLENAME_");
		columnMap.put("idField", "IDFIELD_");
		columnMap.put("idColumn", "IDCOLUMN_");
		columnMap.put("idJavaType", "IDJAVATYPE_");
		columnMap.put("sqlDefId", "SQLDEFID_");
		columnMap.put("sort", "SORT_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("code", "String");
		javaTypeMap.put("description", "String");
		javaTypeMap.put("method", "String");
		javaTypeMap.put("url", "String");
		javaTypeMap.put("tablename", "String");
		javaTypeMap.put("idField", "String");
		javaTypeMap.put("idColumn", "String");
		javaTypeMap.put("idJavaType", "String");
		javaTypeMap.put("sqlDefId", "Long");
		javaTypeMap.put("sort", "Integer");
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
		tableDefinition.setName("Operation");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(200);
		tableDefinition.addColumn(name);

		ColumnDefinition code = new ColumnDefinition();
		code.setName("code");
		code.setColumnName("CODE_");
		code.setJavaType("String");
		code.setLength(200);
		tableDefinition.addColumn(code);

		ColumnDefinition description = new ColumnDefinition();
		description.setName("description");
		description.setColumnName("DESCRIPTION_");
		description.setJavaType("String");
		description.setLength(2000);
		tableDefinition.addColumn(description);

		ColumnDefinition method = new ColumnDefinition();
		method.setName("method");
		method.setColumnName("METHOD_");
		method.setJavaType("String");
		method.setLength(50);
		tableDefinition.addColumn(method);

		ColumnDefinition url = new ColumnDefinition();
		url.setName("url");
		url.setColumnName("URL_");
		url.setJavaType("String");
		url.setLength(500);
		tableDefinition.addColumn(url);

		ColumnDefinition tablename = new ColumnDefinition();
		tablename.setName("tablename");
		tablename.setColumnName("TABLENAME_");
		tablename.setJavaType("String");
		tablename.setLength(50);
		tableDefinition.addColumn(tablename);

		ColumnDefinition idField = new ColumnDefinition();
		idField.setName("idField");
		idField.setColumnName("IDFIELD_");
		idField.setJavaType("String");
		idField.setLength(50);
		tableDefinition.addColumn(idField);

		ColumnDefinition pkColumn = new ColumnDefinition();
		pkColumn.setName("idColumn");
		pkColumn.setColumnName("IDCOLUMN_");
		pkColumn.setJavaType("String");
		pkColumn.setLength(50);
		tableDefinition.addColumn(pkColumn);

		ColumnDefinition idJavaType = new ColumnDefinition();
		idJavaType.setName("idJavaType");
		idJavaType.setColumnName("IDJAVATYPE_");
		idJavaType.setJavaType("String");
		idJavaType.setLength(50);
		tableDefinition.addColumn(idJavaType);

		ColumnDefinition sqlDefId = new ColumnDefinition();
		sqlDefId.setName("sqlDefId");
		sqlDefId.setColumnName("SQLDEFID_");
		sqlDefId.setJavaType("Long");
		tableDefinition.addColumn(sqlDefId);

		ColumnDefinition sort = new ColumnDefinition();
		sort.setName("sort");
		sort.setColumnName("SORT_");
		sort.setJavaType("Integer");
		tableDefinition.addColumn(sort);

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
					dataRequest.getFilter().setColumn(
							columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter()
							.setJavaType(
									javaTypeMap.get(dataRequest.getFilter()
											.getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter()
						.getFilters();
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

	private OperationDomainFactory() {

	}

}
