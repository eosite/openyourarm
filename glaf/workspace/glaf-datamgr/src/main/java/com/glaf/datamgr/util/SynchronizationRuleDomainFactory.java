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
public class SynchronizationRuleDomainFactory {

	public static final String TABLENAME = "SysSynchronization";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "INTERFACEID");
		columnMap.put("actionName", "ACTIONNAME");
		columnMap.put("formulas", "FORMULAS");
		columnMap.put("targetTable", "TARGETTABLE");
		columnMap.put("souceTable", "SOUCETABLE");
		columnMap.put("wbsTable", "WBSTABLE");
		columnMap.put("targetTableModel", "TARGETTABLEMODEL");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("actionName", "String");
		javaTypeMap.put("formulas", "String");
		javaTypeMap.put("targetTable", "String");
		javaTypeMap.put("souceTable", "String");
		javaTypeMap.put("wbsTable", "String");
		javaTypeMap.put("targetTableModel", "String");
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
		tableDefinition.setName("SynchronizationRule");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("INTERFACEID");
		idColumn.setJavaType("String");
		idColumn.setLength(500);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition actionName = new ColumnDefinition();
		actionName.setName("actionName");
		actionName.setColumnName("ACTIONNAME");
		actionName.setJavaType("Clob");
		actionName.setLength(4000);
		tableDefinition.addColumn(actionName);

		ColumnDefinition formulas = new ColumnDefinition();
		formulas.setName("formulas");
		formulas.setColumnName("FORMULAS");
		formulas.setJavaType("Clob");
		formulas.setLength(4000);
		tableDefinition.addColumn(formulas);

		ColumnDefinition targetTable = new ColumnDefinition();
		targetTable.setName("targetTable");
		targetTable.setColumnName("TARGETTABLE");
		targetTable.setJavaType("Clob");
		targetTable.setLength(4000);
		tableDefinition.addColumn(targetTable);

		ColumnDefinition souceTable = new ColumnDefinition();
		souceTable.setName("souceTable");
		souceTable.setColumnName("SOUCETABLE");
		souceTable.setJavaType("Clob");
		souceTable.setLength(4000);
		tableDefinition.addColumn(souceTable);

		ColumnDefinition wbsTable = new ColumnDefinition();
		wbsTable.setName("wbsTable");
		wbsTable.setColumnName("WBSTABLE");
		wbsTable.setJavaType("Clob");
		wbsTable.setLength(4000);
		tableDefinition.addColumn(wbsTable);

		ColumnDefinition targetTableModel = new ColumnDefinition();
		targetTableModel.setName("targetTableModel");
		targetTableModel.setColumnName("TARGETTABLEMODEL");
		targetTableModel.setJavaType("Clob");
		targetTableModel.setLength(4000);
		tableDefinition.addColumn(targetTableModel);

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

	private SynchronizationRuleDomainFactory() {

	}

}
