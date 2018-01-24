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
public class ConvertElemPropTmplDomainFactory {

	public static final String TABLENAME = "CVT_ELEMPROP_TMPL";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

 

	static {
		columnMap.put("elemPropId", "ELEMPROP_ID_");
		columnMap.put("cvtId", "CVT_ID_");
		columnMap.put("rowIndex", "ROW_INDEX_");
		columnMap.put("colIndex", "COL_INDEX_");
		columnMap.put("cellPropVal", "CELL_PROP_VAL_");
		columnMap.put("cellProp", "CELL_PROP_");
		columnMap.put("createDatetime", "CREATE_DATETIME_");
		columnMap.put("modifyDatetime", "MODIFY_DATETIME_");

		javaTypeMap.put("elemPropId", "Long");
		javaTypeMap.put("cvtId", "Long");
		javaTypeMap.put("rowIndex", "Integer");
		javaTypeMap.put("colIndex", "Integer");
		javaTypeMap.put("cellPropVal", "String");
		javaTypeMap.put("cellProp", "String");
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
		tableDefinition.setName("ConvertElemPropTmpl");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("elemPropId");
		idColumn.setColumnName("ELEMPROP_ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition cvtId = new ColumnDefinition();
		cvtId.setName("cvtId");
		cvtId.setColumnName("CVT_ID_");
		cvtId.setJavaType("Long");
		tableDefinition.addColumn(cvtId);

		ColumnDefinition rowIndex = new ColumnDefinition();
		rowIndex.setName("rowIndex");
		rowIndex.setColumnName("ROW_INDEX_");
		rowIndex.setJavaType("Integer");
		tableDefinition.addColumn(rowIndex);

		ColumnDefinition colIndex = new ColumnDefinition();
		colIndex.setName("colIndex");
		colIndex.setColumnName("COL_INDEX_");
		colIndex.setJavaType("Integer");
		tableDefinition.addColumn(colIndex);

		ColumnDefinition cellPropVal = new ColumnDefinition();
		cellPropVal.setName("cellPropVal");
		cellPropVal.setColumnName("CELL_PROP_VAL_");
		cellPropVal.setJavaType("String");
		cellPropVal.setLength(150);
		tableDefinition.addColumn(cellPropVal);

		ColumnDefinition cellProp = new ColumnDefinition();
		cellProp.setName("cellProp");
		cellProp.setColumnName("CELL_PROP_");
		cellProp.setJavaType("String");
		cellProp.setLength(30);
		tableDefinition.addColumn(cellProp);

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

	private ConvertElemPropTmplDomainFactory() {

	}

}
