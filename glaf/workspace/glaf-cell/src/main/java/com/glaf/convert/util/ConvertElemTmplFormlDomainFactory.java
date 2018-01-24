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
public class ConvertElemTmplFormlDomainFactory {

	public static final String TABLENAME = "CVT_ELEM_TMPL_FORML";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("formlRuleId", "FORML_RULE_ID_");
		columnMap.put("cvtElemId", "CVT_ELEM_ID_");
		columnMap.put("formlName", "FORML_NAME_");
		columnMap.put("formlContent", "FORML_CONTENT_");
		columnMap.put("useConditon", "USECONDITION_");
		columnMap.put("createDatetime", "CREATE_DATETIME_");
		columnMap.put("modifyDatetime", "MODIFY_DATETIME_");

		javaTypeMap.put("formlRuleId", "Long");
		javaTypeMap.put("cvtElemId", "Long");
		javaTypeMap.put("formlName", "String");
		javaTypeMap.put("formlContent", "String");
		javaTypeMap.put("useConditon", "String");
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
		tableDefinition.setName("ConvertElemTmplForml");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("formlRuleId");
		idColumn.setColumnName("FORML_RULE_ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition cvtElemId = new ColumnDefinition();
		cvtElemId.setName("cvtElemId");
		cvtElemId.setColumnName("CVT_ELEM_ID_");
		cvtElemId.setJavaType("Long");
		tableDefinition.addColumn(cvtElemId);

		ColumnDefinition formlName = new ColumnDefinition();
		formlName.setName("formlName");
		formlName.setColumnName("FORML_NAME_");
		formlName.setJavaType("String");
		formlName.setLength(0);
		tableDefinition.addColumn(formlName);

		ColumnDefinition formlContent = new ColumnDefinition();
		formlContent.setName("formlContent");
		formlContent.setColumnName("FORML_CONTENT_");
		formlContent.setJavaType("String");
		formlContent.setLength(0);
		tableDefinition.addColumn(formlContent);

		ColumnDefinition useConditon = new ColumnDefinition();
		useConditon.setName("useConditon");
		useConditon.setColumnName("USECONDITION_");
		useConditon.setJavaType("String");
		useConditon.setLength(100);
		tableDefinition.addColumn(useConditon);

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

	private ConvertElemTmplFormlDomainFactory() {

	}

}
