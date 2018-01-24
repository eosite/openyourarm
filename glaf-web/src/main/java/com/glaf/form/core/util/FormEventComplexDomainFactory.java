package com.glaf.form.core.util;

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
public class FormEventComplexDomainFactory {

	public static final String TABLENAME = "FORM_EVENT_COMPLEX";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("name", "NAME_");
		columnMap.put("remark", "REMARK_");
		columnMap.put("diagram", "DIAGRAM_");
		columnMap.put("rule", "RULE_");
		columnMap.put("pageId", "PAGEID_");
		columnMap.put("complexRule", "COMPLEX_RULE_");
		columnMap.put("createDate", "CREATEDATE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateDate", "UPDATEDATE_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("remark", "String");
		javaTypeMap.put("diagram", "String");
		javaTypeMap.put("rule", "String");
		javaTypeMap.put("pageId", "String");
		javaTypeMap.put("complexRule", "String");
		javaTypeMap.put("createDate", "Date");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("updateBy", "String");
		javaTypeMap.put("updateDate", "Date");
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
		tableDefinition.setName("FormEventComplex");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition remark = new ColumnDefinition();
		name.setName("remark");
		name.setColumnName("REMARK_");
		name.setJavaType("String");
		name.setLength(400);
		tableDefinition.addColumn(name);

		ColumnDefinition diagram = new ColumnDefinition();
		diagram.setName("diagram");
		diagram.setColumnName("DIAGRAM_");
		diagram.setJavaType("String");
		diagram.setLength(50);
		tableDefinition.addColumn(diagram);

		ColumnDefinition rule = new ColumnDefinition();
		rule.setName("rule");
		rule.setColumnName("RULE_");
		rule.setJavaType("String");
		rule.setLength(0);
		tableDefinition.addColumn(rule);

		ColumnDefinition pageId = new ColumnDefinition();
		pageId.setName("pageId");
		pageId.setColumnName("PAGEID_");
		pageId.setJavaType("String");
		pageId.setLength(50);
		tableDefinition.addColumn(pageId);

		ColumnDefinition complexRule = new ColumnDefinition();
		complexRule.setName("complexRule");
		complexRule.setColumnName("COMPLEX_RULE_");
		complexRule.setJavaType("String");
		complexRule.setLength(0);
		tableDefinition.addColumn(complexRule);

		ColumnDefinition createDate = new ColumnDefinition();
		createDate.setName("createDate");
		createDate.setColumnName("CREATEDATE_");
		createDate.setJavaType("Date");
		tableDefinition.addColumn(createDate);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY_");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

		ColumnDefinition updateDate = new ColumnDefinition();
		updateDate.setName("updateDate");
		updateDate.setColumnName("UPDATEDATE_");
		updateDate.setJavaType("Date");
		tableDefinition.addColumn(updateDate);

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

	private FormEventComplexDomainFactory() {

	}

}
