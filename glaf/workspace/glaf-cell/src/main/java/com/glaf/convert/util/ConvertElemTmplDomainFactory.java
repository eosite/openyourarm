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
public class ConvertElemTmplDomainFactory {

	public static final String TABLENAME = "CVT_ELEM_TMPL";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("cvtElemId", "CVT_ELEM_ID_");
		columnMap.put("cvtId", "CVT_ID_");
		columnMap.put("elemType", "ELEM_TYPE_");
		columnMap.put("elemName", "ELEM_NAME_");
		columnMap.put("elemId", "ELEM_ID_");
		columnMap.put("dType", "DTYPE_");
		columnMap.put("len", "LEN_");
		columnMap.put("digit", "DIGIT_");
		columnMap.put("defaultVal", "DEFAULT_VAL_");
		columnMap.put("rowIndex", "ROW_INDEX_");
		columnMap.put("colIndex", "COL_INDEX_");
		columnMap.put("readOnly", "READONLY_");
		columnMap.put("print", "PRINT_");
		columnMap.put("display", "DISPLAY_");
		columnMap.put("isMustFill", "ISMUSTFILL_");
		columnMap.put("isDataOnly", "ISDATAONLY_");
		columnMap.put("direction", "DIRECTION_");
		columnMap.put("vararea", "VAR_AREA_");
		columnMap.put("endRowIndex", "END_ROW_INDEX_");
		columnMap.put("endColIndex", "END_COL_INDEX_");
		columnMap.put("createDatetime", "CREATE_DATETIME_");
		columnMap.put("modifyDatetime", "MODIFY_DATETIME_");
		columnMap.put("repinfoListId", "REPINFO_LISTID_");

		javaTypeMap.put("cvtElemId", "Long");
		javaTypeMap.put("cvtId", "Long");
		javaTypeMap.put("elemType", "String");
		javaTypeMap.put("elemName", "String");
		javaTypeMap.put("elemId", "String");
		javaTypeMap.put("dType", "String");
		javaTypeMap.put("len", "Integer");
		javaTypeMap.put("digit", "Integer");
		javaTypeMap.put("defaultVal", "String");
		javaTypeMap.put("rowIndex", "Integer");
		javaTypeMap.put("colIndex", "Integer");
		javaTypeMap.put("readOnly", "String");
		javaTypeMap.put("print", "String");
		javaTypeMap.put("display", "String");
		javaTypeMap.put("isMustFill", "String");
		javaTypeMap.put("isDataOnly", "String");
		javaTypeMap.put("direction", "String");
		javaTypeMap.put("vararea", "String");
		javaTypeMap.put("endRowIndex", "Integer");
		javaTypeMap.put("endColIndex", "Integer");
		javaTypeMap.put("createDatetime", "Date");
		javaTypeMap.put("modifyDatetime", "Date");
		javaTypeMap.put("repinfoListId", "String");
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
		tableDefinition.setName("ConvertElemTmpl");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("cvtElemId");
		idColumn.setColumnName("CVT_ELEM_ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition cvtId = new ColumnDefinition();
		cvtId.setName("cvtId");
		cvtId.setColumnName("CVT_ID_");
		cvtId.setJavaType("Long");
		tableDefinition.addColumn(cvtId);

		ColumnDefinition elemType = new ColumnDefinition();
		elemType.setName("elemType");
		elemType.setColumnName("ELEM_TYPE_");
		elemType.setJavaType("String");
		elemType.setLength(20);
		tableDefinition.addColumn(elemType);

		ColumnDefinition elemName = new ColumnDefinition();
		elemName.setName("elemName");
		elemName.setColumnName("ELEM_NAME_");
		elemName.setJavaType("String");
		elemName.setLength(50);
		tableDefinition.addColumn(elemName);

		ColumnDefinition elemId = new ColumnDefinition();
		elemId.setName("elemId");
		elemId.setColumnName("ELEM_ID_");
		elemId.setJavaType("String");
		elemId.setLength(30);
		tableDefinition.addColumn(elemId);

		ColumnDefinition dType = new ColumnDefinition();
		dType.setName("dType");
		dType.setColumnName("DTYPE_");
		dType.setJavaType("String");
		dType.setLength(10);
		tableDefinition.addColumn(dType);

		ColumnDefinition len = new ColumnDefinition();
		len.setName("len");
		len.setColumnName("LEN_");
		len.setJavaType("Integer");
		tableDefinition.addColumn(len);

		ColumnDefinition digit = new ColumnDefinition();
		digit.setName("digit");
		digit.setColumnName("DIGIT_");
		digit.setJavaType("Integer");
		tableDefinition.addColumn(digit);

		ColumnDefinition defaultVal = new ColumnDefinition();
		defaultVal.setName("defaultVal");
		defaultVal.setColumnName("DEFAULT_VAL_");
		defaultVal.setJavaType("String");
		defaultVal.setLength(50);
		tableDefinition.addColumn(defaultVal);

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

		ColumnDefinition readOnly = new ColumnDefinition();
		readOnly.setName("readOnly");
		readOnly.setColumnName("READONLY_");
		readOnly.setJavaType("String");
		readOnly.setLength(1);
		tableDefinition.addColumn(readOnly);

		ColumnDefinition print = new ColumnDefinition();
		print.setName("print");
		print.setColumnName("PRINT_");
		print.setJavaType("String");
		print.setLength(1);
		tableDefinition.addColumn(print);

		ColumnDefinition display = new ColumnDefinition();
		display.setName("display");
		display.setColumnName("DISPLAY_");
		display.setJavaType("String");
		display.setLength(1);
		tableDefinition.addColumn(display);

		ColumnDefinition isMustFill = new ColumnDefinition();
		isMustFill.setName("isMustFill");
		isMustFill.setColumnName("ISMUSTFILL_");
		isMustFill.setJavaType("String");
		isMustFill.setLength(1);
		tableDefinition.addColumn(isMustFill);

		ColumnDefinition isDataOnly = new ColumnDefinition();
		isDataOnly.setName("isDataOnly");
		isDataOnly.setColumnName("ISDATAONLY_");
		isDataOnly.setJavaType("String");
		isDataOnly.setLength(1);
		tableDefinition.addColumn(isDataOnly);

		ColumnDefinition direction = new ColumnDefinition();
		direction.setName("direction");
		direction.setColumnName("DIRECTION_");
		direction.setJavaType("String");
		direction.setLength(1);
		tableDefinition.addColumn(direction);

		ColumnDefinition vararea = new ColumnDefinition();
		vararea.setName("vararea");
		vararea.setColumnName("VAR_AREA_");
		vararea.setJavaType("String");
		vararea.setLength(1);
		tableDefinition.addColumn(vararea);

		ColumnDefinition endRowIndex = new ColumnDefinition();
		endRowIndex.setName("endRowIndex");
		endRowIndex.setColumnName("END_ROW_INDEX_");
		endRowIndex.setJavaType("Integer");
		tableDefinition.addColumn(endRowIndex);

		ColumnDefinition endColIndex = new ColumnDefinition();
		endColIndex.setName("endColIndex");
		endColIndex.setColumnName("END_COL_INDEX_");
		endColIndex.setJavaType("Integer");
		tableDefinition.addColumn(endColIndex);

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

		ColumnDefinition repinfoListId = new ColumnDefinition();
		repinfoListId.setName("repinfoListId");
		repinfoListId.setColumnName("REPINFO_LISTID_");
		repinfoListId.setJavaType("String");
		repinfoListId.setLength(50);
		tableDefinition.addColumn(repinfoListId);

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

	private ConvertElemTmplDomainFactory() {

	}

}
