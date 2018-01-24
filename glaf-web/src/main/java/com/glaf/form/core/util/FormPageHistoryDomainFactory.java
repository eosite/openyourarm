package com.glaf.form.core.util;

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
public class FormPageHistoryDomainFactory {

	public static final String TABLENAME = "FORM_PAGE_HISTORY";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("pageId", "PAGEID_");
		columnMap.put("deploymentId", "DEPLOYMENTID_");
		columnMap.put("formHtml", "FORMHTML_");
		columnMap.put("formConfig", "FORMCONFIG_");
		columnMap.put("outputHtml", "OUTPUTHTML_");
		columnMap.put("formType", "FORMTYPE_");
		columnMap.put("version", "VERSION_");
		columnMap.put("createDate", "CREATEDATE_");
		columnMap.put("createBy", "CREATEBY_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("pageId", "String");
		javaTypeMap.put("deploymentId", "String");
		javaTypeMap.put("formHtml", "String");
		javaTypeMap.put("formConfig", "String");
		javaTypeMap.put("outputHtml", "String");
		javaTypeMap.put("formType", "String");
		javaTypeMap.put("version", "Integer");
		javaTypeMap.put("createDate", "Date");
		javaTypeMap.put("createBy", "String");
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
		tableDefinition.setName("FormPageHistory");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition pageId = new ColumnDefinition();
		pageId.setName("pageId");
		pageId.setColumnName("PAGEID_");
		pageId.setJavaType("String");
		pageId.setLength(50);
		tableDefinition.addColumn(pageId);

		ColumnDefinition deploymentId = new ColumnDefinition();
		deploymentId.setName("deploymentId");
		deploymentId.setColumnName("DEPLOYMENTID_");
		deploymentId.setJavaType("String");
		deploymentId.setLength(100);
		tableDefinition.addColumn(deploymentId);

		ColumnDefinition formHtml = new ColumnDefinition();
		formHtml.setName("formHtml");
		formHtml.setColumnName("FORMHTML_");
		formHtml.setJavaType("String");
		formHtml.setLength(2000);
		tableDefinition.addColumn(formHtml);

		ColumnDefinition formConfig = new ColumnDefinition();
		formConfig.setName("formConfig");
		formConfig.setColumnName("FORMCONFIG_");
		formConfig.setJavaType("String");
		formConfig.setLength(2000);
		tableDefinition.addColumn(formConfig);

		ColumnDefinition outputHtml = new ColumnDefinition();
		outputHtml.setName("outputHtml");
		outputHtml.setColumnName("OUTPUTHTML_");
		outputHtml.setJavaType("String");
		outputHtml.setLength(2000);
		tableDefinition.addColumn(outputHtml);

		ColumnDefinition formType = new ColumnDefinition();
		formType.setName("formType");
		formType.setColumnName("FORMTYPE_");
		formType.setJavaType("String");
		formType.setLength(50);
		tableDefinition.addColumn(formType);

		ColumnDefinition version = new ColumnDefinition();
		version.setName("version");
		version.setColumnName("VERSION_");
		version.setJavaType("Integer");
		tableDefinition.addColumn(version);

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

	private FormPageHistoryDomainFactory() {

	}

}
