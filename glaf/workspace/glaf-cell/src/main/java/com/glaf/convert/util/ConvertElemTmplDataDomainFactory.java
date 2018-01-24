package com.glaf.convert.util;

import java.util.List;
import java.util.Map;
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
public class ConvertElemTmplDataDomainFactory {

	public static final String TABLENAME = "CVT_ELEM_TMPL_DATA";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("dataRuleId", "DATA_RULE_ID_");
		columnMap.put("cvtElemId", "CVT_ELEM_ID_");
		columnMap.put("tableName", "TABLE_NAME_");
		columnMap.put("fieldName", "FIELD_NAME_");
		columnMap.put("dataTableId", "DATA_TABLE_ID_");
		columnMap.put("subTable", "SUB_TABLE_");
		columnMap.put("createDatetime", "CREATE_DATETIME_");
		columnMap.put("modifyDatetime", "MODIFY_DATETIME_");

		javaTypeMap.put("dataRuleId", "Long");
		javaTypeMap.put("cvtElemId", "Long");
		javaTypeMap.put("tableName", "String");
		javaTypeMap.put("fieldName", "String");
		javaTypeMap.put("dataTableId", "String");
		javaTypeMap.put("subTable", "String");
		javaTypeMap.put("createDatetime", "Date");
		javaTypeMap.put("modifyDatetime", "Date");
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
		tableDefinition.setName("ConvertElemTmplData");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("dataRuleId");
		idColumn.setColumnName("DATA_RULE_ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition cvtElemId = new ColumnDefinition();
		cvtElemId.setName("cvtElemId");
		cvtElemId.setColumnName("CVT_ELEM_ID_");
		cvtElemId.setJavaType("Long");
		tableDefinition.addColumn(cvtElemId);

		ColumnDefinition tableName1 = new ColumnDefinition();
		tableName1.setName("tableName");
		tableName1.setColumnName("TABLE_NAME_");
		tableName1.setJavaType("String");
		tableName1.setLength(30);
		tableDefinition.addColumn(tableName1);

		ColumnDefinition fieldName = new ColumnDefinition();
		fieldName.setName("fieldName");
		fieldName.setColumnName("FIELD_NAME_");
		fieldName.setJavaType("String");
		fieldName.setLength(30);
		tableDefinition.addColumn(fieldName);

		ColumnDefinition dataTableId = new ColumnDefinition();
		dataTableId.setName("dataTableId");
		dataTableId.setColumnName("DATA_TABLE_ID_");
		dataTableId.setJavaType("String");
		dataTableId.setLength(50);
		tableDefinition.addColumn(dataTableId);

		ColumnDefinition subTable = new ColumnDefinition();
		subTable.setName("subTable");
		subTable.setColumnName("SUB_TABLE_");
		subTable.setJavaType("String");
		subTable.setLength(1);
		tableDefinition.addColumn(subTable);

		ColumnDefinition createDatetime = new ColumnDefinition();
		createDatetime.setName("createDatetime");
		createDatetime.setColumnName("CREATE_DATETIME_");
		createDatetime.setJavaType("Date");
		tableDefinition.addColumn(createDatetime);

		ColumnDefinition modifyDatetime = new ColumnDefinition();
		modifyDatetime.setName("modifyDatetime");
		modifyDatetime.setColumnName("MODIFY_DATETIME_");
		modifyDatetime.setJavaType("Date");
		tableDefinition.addColumn(modifyDatetime);

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

	private ConvertElemTmplDataDomainFactory() {

	}

}
