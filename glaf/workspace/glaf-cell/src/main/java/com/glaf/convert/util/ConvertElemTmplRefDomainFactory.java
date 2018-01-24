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
public class ConvertElemTmplRefDomainFactory {

	public static final String TABLENAME = "CVT_ELEM_TMPL_REF";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("refRuleId", "REF_RULE_ID_");
		columnMap.put("cvtElemId", "CVT_ELEM_ID_");
		columnMap.put("refType", "REF_TYPE_");
		columnMap.put("refContent", "REF_CONTENT_");
		columnMap.put("refCondition", "REF_CONDITON_");
		columnMap.put("refFieldId", "REF_FIELD_ID_");
		columnMap.put("useCondition", "USECONDITION_");
		columnMap.put("transtionFlag", "TRANSTION_FLAG_");
		columnMap.put("createDatetime", "CREATE_DATETIME_");
		columnMap.put("modifyDatetime", "MODIFY_DATETIME_");

		javaTypeMap.put("refRuleId", "Long");
		javaTypeMap.put("cvtElemId", "Long");
		javaTypeMap.put("refType", "String");
		javaTypeMap.put("refContent", "String");
		javaTypeMap.put("refCondition", "String");
		javaTypeMap.put("refFieldId", "String");
		javaTypeMap.put("useCondition", "String");
		javaTypeMap.put("transtionFlag", "String");
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
		tableDefinition.setName("ConvertElemTmplRef");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("refRuleId");
		idColumn.setColumnName("REF_RULE_ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition cvtElemId = new ColumnDefinition();
		cvtElemId.setName("cvtElemId");
		cvtElemId.setColumnName("CVT_ELEM_ID_");
		cvtElemId.setJavaType("Long");
		tableDefinition.addColumn(cvtElemId);

		ColumnDefinition refType = new ColumnDefinition();
		refType.setName("refType");
		refType.setColumnName("REF_TYPE_");
		refType.setJavaType("String");
		refType.setLength(10);
		tableDefinition.addColumn(refType);

		ColumnDefinition refContent = new ColumnDefinition();
		refContent.setName("refContent");
		refContent.setColumnName("REF_CONTENT_");
		refContent.setJavaType("String");
		refContent.setLength(0);
		tableDefinition.addColumn(refContent);

		ColumnDefinition refCondition = new ColumnDefinition();
		refCondition.setName("refCondition");
		refCondition.setColumnName("REF_CONDITON_");
		refCondition.setJavaType("String");
		refCondition.setLength(150);
		tableDefinition.addColumn(refCondition);

		ColumnDefinition refFieldId = new ColumnDefinition();
		refFieldId.setName("refFieldId");
		refFieldId.setColumnName("REF_FIELD_ID_");
		refFieldId.setJavaType("String");
		refFieldId.setLength(30);
		tableDefinition.addColumn(refFieldId);

		ColumnDefinition useCondition = new ColumnDefinition();
		useCondition.setName("useCondition");
		useCondition.setColumnName("USECONDITION_");
		useCondition.setJavaType("String");
		useCondition.setLength(150);
		tableDefinition.addColumn(useCondition);

		ColumnDefinition transtionFlag = new ColumnDefinition();
		transtionFlag.setName("transtionFlag");
		transtionFlag.setColumnName("TRANSTION_FLAG_");
		transtionFlag.setJavaType("String");
		transtionFlag.setLength(1);
		tableDefinition.addColumn(transtionFlag);

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

	private ConvertElemTmplRefDomainFactory() {

	}

}
