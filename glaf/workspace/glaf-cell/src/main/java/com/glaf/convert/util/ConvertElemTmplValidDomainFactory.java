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
public class ConvertElemTmplValidDomainFactory {

	public static final String TABLENAME = "CVT_ELEM_TMPL_VALID";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("validRuleId", "VALID_RULE_ID_");
		columnMap.put("cvtElemId", "CVT_ELEM_ID_");
		columnMap.put("validType", "VALID_TYPE_");
		columnMap.put("expression", "EXPRESSION_");
		columnMap.put("dType", "DTYPE_");
		columnMap.put("len", "LEN_");
		columnMap.put("rangeUpper", "RANGE_UPPER_");
		columnMap.put("rangeLower", "RANGE_LOWER_");
		columnMap.put("useCondition", "USECONDITION_");
		columnMap.put("seq", "SEQ_");
		columnMap.put("parentRuleId", "PARENT_RULE_ID_");
		columnMap.put("treeId", "TREEID_");
		columnMap.put("createDatetime", "CREATE_DATETIME_");
		columnMap.put("modifyDatetime", "MODIFY_DATETIME_");

		javaTypeMap.put("validRuleId", "Long");
		javaTypeMap.put("cvtElemId", "Long");
		javaTypeMap.put("validType", "String");
		javaTypeMap.put("expression", "String");
		javaTypeMap.put("dType", "String");
		javaTypeMap.put("len", "Integer");
		javaTypeMap.put("rangeUpper", "String");
		javaTypeMap.put("rangeLower", "String");
		javaTypeMap.put("useCondition", "String");
		javaTypeMap.put("seq", "Integer");
		javaTypeMap.put("parentRuleId", "Long");
		javaTypeMap.put("treeId", "String");
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
		tableDefinition.setName("ConvertElemTmplValid");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("validRuleId");
		idColumn.setColumnName("VALID_RULE_ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition cvtElemId = new ColumnDefinition();
		cvtElemId.setName("cvtElemId");
		cvtElemId.setColumnName("CVT_ELEM_ID_");
		cvtElemId.setJavaType("Long");
		tableDefinition.addColumn(cvtElemId);

		ColumnDefinition validType = new ColumnDefinition();
		validType.setName("validType");
		validType.setColumnName("VALID_TYPE_");
		validType.setJavaType("String");
		validType.setLength(10);
		tableDefinition.addColumn(validType);

		ColumnDefinition expression = new ColumnDefinition();
		expression.setName("expression");
		expression.setColumnName("EXPRESSION_");
		expression.setJavaType("String");
		expression.setLength(150);
		tableDefinition.addColumn(expression);

		ColumnDefinition dType = new ColumnDefinition();
		dType.setName("dType");
		dType.setColumnName("DTYPE_");
		dType.setJavaType("String");
		dType.setLength(0);
		tableDefinition.addColumn(dType);

		ColumnDefinition len = new ColumnDefinition();
		len.setName("len");
		len.setColumnName("LEN_");
		len.setJavaType("Integer");
		tableDefinition.addColumn(len);

		ColumnDefinition rangeUpper = new ColumnDefinition();
		rangeUpper.setName("rangeUpper");
		rangeUpper.setColumnName("RANGE_UPPER_");
		rangeUpper.setJavaType("String");
		rangeUpper.setLength(30);
		tableDefinition.addColumn(rangeUpper);

		ColumnDefinition rangeLower = new ColumnDefinition();
		rangeLower.setName("rangeLower");
		rangeLower.setColumnName("RANGE_LOWER_");
		rangeLower.setJavaType("String");
		rangeLower.setLength(30);
		tableDefinition.addColumn(rangeLower);

		ColumnDefinition useCondition = new ColumnDefinition();
		useCondition.setName("useCondition");
		useCondition.setColumnName("USECONDITION_");
		useCondition.setJavaType("String");
		useCondition.setLength(100);
		tableDefinition.addColumn(useCondition);

		ColumnDefinition seq = new ColumnDefinition();
		seq.setName("seq");
		seq.setColumnName("SEQ_");
		seq.setJavaType("Integer");
		tableDefinition.addColumn(seq);

		ColumnDefinition parentRuleId = new ColumnDefinition();
		parentRuleId.setName("parentRuleId");
		parentRuleId.setColumnName("PARENT_RULE_ID_");
		parentRuleId.setJavaType("Long");
		tableDefinition.addColumn(parentRuleId);

		ColumnDefinition treeId = new ColumnDefinition();
		treeId.setName("treeId");
		treeId.setColumnName("TREEID_");
		treeId.setJavaType("String");
		treeId.setLength(100);
		tableDefinition.addColumn(treeId);

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

	private ConvertElemTmplValidDomainFactory() {

	}

}
