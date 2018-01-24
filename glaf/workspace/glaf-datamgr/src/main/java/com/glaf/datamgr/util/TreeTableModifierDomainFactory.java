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
public class TreeTableModifierDomainFactory {

	public static final String TABLENAME = "SYS_TREETABLE_MODIFIER";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("tableName", "TABLENAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("databaseIds", "DATABASEIDS_");
		columnMap.put("primaryKey", "PRIMARYKEY_");
		columnMap.put("idColumn", "IDCOLUMN_");
		columnMap.put("parentIdColumn", "PARENTIDCOLUMN_");
		columnMap.put("treeIdColumn", "TREEIDCOLUMN_");
		columnMap.put("levelColumn", "LEVELCOLUMN_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("tableName", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("databaseIds", "String");
		javaTypeMap.put("primaryKey", "String");
		javaTypeMap.put("idColumn", "String");
		javaTypeMap.put("parentIdColumn", "String");
		javaTypeMap.put("treeIdColumn", "String");
		javaTypeMap.put("levelColumn", "String");
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
		tableDefinition.setName("TreeTableModifier");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition tableNamex = new ColumnDefinition();
		tableNamex.setName("tableName");
		tableNamex.setColumnName("TABLENAME_");
		tableNamex.setJavaType("String");
		tableNamex.setLength(100);
		tableDefinition.addColumn(tableNamex);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(200);
		tableDefinition.addColumn(title);

		ColumnDefinition databaseIds = new ColumnDefinition();
		databaseIds.setName("databaseIds");
		databaseIds.setColumnName("DATABASEIDS_");
		databaseIds.setJavaType("String");
		databaseIds.setLength(2000);
		tableDefinition.addColumn(databaseIds);

		ColumnDefinition primaryKey = new ColumnDefinition();
		primaryKey.setName("primaryKey");
		primaryKey.setColumnName("PRIMARYKEY_");
		primaryKey.setJavaType("String");
		primaryKey.setLength(50);
		tableDefinition.addColumn(primaryKey);

		ColumnDefinition idColumnx = new ColumnDefinition();
		idColumnx.setName("idColumn");
		idColumnx.setColumnName("IDCOLUMN_");
		idColumnx.setJavaType("String");
		idColumnx.setLength(50);
		tableDefinition.addColumn(idColumnx);

		ColumnDefinition parentIdColumn = new ColumnDefinition();
		parentIdColumn.setName("parentIdColumn");
		parentIdColumn.setColumnName("PARENTIDCOLUMN_");
		parentIdColumn.setJavaType("String");
		parentIdColumn.setLength(50);
		tableDefinition.addColumn(parentIdColumn);

		ColumnDefinition treeIdColumn = new ColumnDefinition();
		treeIdColumn.setName("treeIdColumn");
		treeIdColumn.setColumnName("TREEIDCOLUMN_");
		treeIdColumn.setJavaType("String");
		treeIdColumn.setLength(50);
		tableDefinition.addColumn(treeIdColumn);

		ColumnDefinition levelColumn = new ColumnDefinition();
		levelColumn.setName("levelColumn");
		levelColumn.setColumnName("LEVELCOLUMN_");
		levelColumn.setJavaType("String");
		levelColumn.setLength(50);
		tableDefinition.addColumn(levelColumn);

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

	private TreeTableModifierDomainFactory() {

	}

}
