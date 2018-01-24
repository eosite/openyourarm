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
public class ConvertPageParamDomainFactory {

	public static final String TABLENAME = "CVT_PAGE_PARAM";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("cvtParamId", "CVT_PARAM_ID_");
		columnMap.put("cvtId", "CVT_ID_");
		columnMap.put("paramName", "PARAM_NAME_");
		columnMap.put("paramCode", "PARAM_CODE_");
		columnMap.put("paramType", "PARAM_TYPE_");
		columnMap.put("createDatetime", "CREATE_DATETIME_");
		columnMap.put("modifyDatetime", "MODIFY_DATETIME_");

		javaTypeMap.put("cvtParamId", "Long");
		javaTypeMap.put("cvtId", "Long");
		javaTypeMap.put("paramName", "String");
		javaTypeMap.put("paramCode", "String");
		javaTypeMap.put("paramType", "String");
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
		tableDefinition.setName("ConvertPageParam");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("cvtParamId");
		idColumn.setColumnName("CVT_PARAM_ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition cvtId = new ColumnDefinition();
		cvtId.setName("cvtId");
		cvtId.setColumnName("CVT_ID_");
		cvtId.setJavaType("Long");
		tableDefinition.addColumn(cvtId);

		ColumnDefinition paramName = new ColumnDefinition();
		paramName.setName("paramName");
		paramName.setColumnName("PARAM_NAME_");
		paramName.setJavaType("String");
		paramName.setLength(50);
		tableDefinition.addColumn(paramName);

		ColumnDefinition paramCode = new ColumnDefinition();
		paramCode.setName("paramCode");
		paramCode.setColumnName("PARAM_CODE_");
		paramCode.setJavaType("String");
		paramCode.setLength(30);
		tableDefinition.addColumn(paramCode);

		ColumnDefinition paramType = new ColumnDefinition();
		paramType.setName("paramType");
		paramType.setColumnName("PARAM_TYPE_");
		paramType.setJavaType("String");
		paramType.setLength(10);
		tableDefinition.addColumn(paramType);

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

	private ConvertPageParamDomainFactory() {

	}

}
