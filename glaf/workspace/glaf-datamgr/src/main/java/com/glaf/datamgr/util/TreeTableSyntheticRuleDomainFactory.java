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
public class TreeTableSyntheticRuleDomainFactory {

	public static final String TABLENAME = "SYS_TREETABLE_SYNTHETIC_RULE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("syntheticId", "SYNTHETICID_");
		columnMap.put("name", "NAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("columnName", "COLUMNNAME_");
		columnMap.put("columnLabel", "COLUMNLABEL_");
		columnMap.put("columnTitle", "COLUMNTITLE_");
		columnMap.put("columnSize", "COLUMNSIZE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("mappingToSourceIdColumn", "MAPPINGTOSOURCEIDCOLUMN_");
		columnMap.put("mappingToTargetColumn", "MAPPINGTOTARGETCOLUMN_");
		columnMap.put("mappingToTargetAlias", "MAPPINGTOTARGETALIAS_");
		columnMap.put("datasetId", "DATASETID_");
		columnMap.put("sqlDefId", "SQLDEFID_");
		columnMap.put("locked", "LOCKED_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("syntheticId", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("columnName", "String");
		javaTypeMap.put("columnLabel", "String");
		javaTypeMap.put("columnTitle", "String");
		javaTypeMap.put("columnSize", "Integer");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("mappingToSourceIdColumn", "String");
		javaTypeMap.put("mappingToTargetColumn", "String");
		javaTypeMap.put("mappingToTargetAlias", "String");
		javaTypeMap.put("datasetId", "String");
		javaTypeMap.put("sqlDefId", "Long");
		javaTypeMap.put("locked", "Integer");
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
		tableDefinition.setName("TreeTableSyntheticRule");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition syntheticId = new ColumnDefinition();
		syntheticId.setName("syntheticId");
		syntheticId.setColumnName("SYNTHETICID_");
		syntheticId.setJavaType("Long");
		tableDefinition.addColumn(syntheticId);

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

		ColumnDefinition columnName = new ColumnDefinition();
		columnName.setName("columnName");
		columnName.setColumnName("COLUMNNAME_");
		columnName.setJavaType("String");
		columnName.setLength(50);
		tableDefinition.addColumn(columnName);

		ColumnDefinition columnLabel = new ColumnDefinition();
		columnLabel.setName("columnLabel");
		columnLabel.setColumnName("COLUMNLABEL_");
		columnLabel.setJavaType("String");
		columnLabel.setLength(100);
		tableDefinition.addColumn(columnLabel);

		ColumnDefinition columnTitle = new ColumnDefinition();
		columnTitle.setName("columnTitle");
		columnTitle.setColumnName("COLUMNTITLE_");
		columnTitle.setJavaType("String");
		columnTitle.setLength(200);
		tableDefinition.addColumn(columnTitle);

		ColumnDefinition columnSize = new ColumnDefinition();
		columnSize.setName("columnSize");
		columnSize.setColumnName("COLUMNSIZE_");
		columnSize.setJavaType("Integer");
		tableDefinition.addColumn(columnSize);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition mappingToSourceIdColumn = new ColumnDefinition();
		mappingToSourceIdColumn.setName("mappingToSourceIdColumn");
		mappingToSourceIdColumn.setColumnName("MAPPINGTOSOURCEIDCOLUMN_");
		mappingToSourceIdColumn.setJavaType("String");
		mappingToSourceIdColumn.setLength(500);
		tableDefinition.addColumn(mappingToSourceIdColumn);

		ColumnDefinition mappingToTargetColumn = new ColumnDefinition();
		mappingToTargetColumn.setName("mappingToTargetColumn");
		mappingToTargetColumn.setColumnName("MAPPINGTOTARGETCOLUMN_");
		mappingToTargetColumn.setJavaType("String");
		mappingToTargetColumn.setLength(500);
		tableDefinition.addColumn(mappingToTargetColumn);

		ColumnDefinition mappingToTargetAlias = new ColumnDefinition();
		mappingToTargetAlias.setName("mappingToTargetAlias");
		mappingToTargetAlias.setColumnName("MAPPINGTOTARGETALIAS_");
		mappingToTargetAlias.setJavaType("String");
		mappingToTargetAlias.setLength(500);
		tableDefinition.addColumn(mappingToTargetAlias);

		ColumnDefinition datasetId = new ColumnDefinition();
		datasetId.setName("datasetId");
		datasetId.setColumnName("DATASETID_");
		datasetId.setJavaType("String");
		datasetId.setLength(50);
		tableDefinition.addColumn(datasetId);

		ColumnDefinition sqlDefId = new ColumnDefinition();
		sqlDefId.setName("sqlDefId");
		sqlDefId.setColumnName("SQLDEFID_");
		sqlDefId.setJavaType("Long");
		tableDefinition.addColumn(sqlDefId);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

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

	private TreeTableSyntheticRuleDomainFactory() {

	}

}
