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
public class FormPageDomainFactory {

	public static final String TABLENAME = "FORM_PAGE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("parentId", "PARENTID_");
		columnMap.put("deploymentId", "DEPLOYMENTID_");
		columnMap.put("name", "NAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("formHtml", "FORMHTML_");
		columnMap.put("formConfig", "FORMCONFIG_");
		columnMap.put("outputHtml", "OUTPUTHTML_");
		columnMap.put("formType", "FORMTYPE_");
		columnMap.put("sortNo", "SORTNO_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("deleteFlag", "DELETEFLAG_");
		columnMap.put("cacheFlag", "CACHEFLAG_");
		columnMap.put("publicFlag", "PUBLICFLAG_");
		columnMap.put("userStyleFlag", "USERSTYLEFLAG_");
		columnMap.put("businessTable", "BUSINESSTABLE_");
		columnMap.put("primaryKeyColumn", "PRIMARYKEYCOLUMN_");
		columnMap.put("processName", "PROCESSNAME_");
		columnMap.put("taskFlag", "TASKFLAG_");
		columnMap.put("version", "VERSION_");
		columnMap.put("createDate", "CREATEDATE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("updateDate", "UPDATEDATE_");
		columnMap.put("updateBy", "UPDATEBY_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("parentId", "String");
		javaTypeMap.put("deploymentId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("formHtml", "String");
		javaTypeMap.put("formConfig", "String");
		javaTypeMap.put("outputHtml", "String");
		javaTypeMap.put("formType", "String");
		javaTypeMap.put("sortNo", "Integer");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("deleteFlag", "Integer");
		javaTypeMap.put("cacheFlag", "String");
		javaTypeMap.put("publicFlag", "String");
		javaTypeMap.put("userStyleFlag", "String");
		javaTypeMap.put("businessTable", "String");
		javaTypeMap.put("primaryKeyColumn", "String");
		javaTypeMap.put("processName", "String");
		javaTypeMap.put("taskFlag", "String");
		javaTypeMap.put("version", "Integer");
		javaTypeMap.put("createDate", "Date");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("updateDate", "Date");
		javaTypeMap.put("updateBy", "String");
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
		tableDefinition.setName("FormPage");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition parentId = new ColumnDefinition();
		parentId.setName("parentId");
		parentId.setColumnName("PARENTID_");
		parentId.setJavaType("String");
		parentId.setLength(50);
		tableDefinition.addColumn(parentId);

		ColumnDefinition deploymentId = new ColumnDefinition();
		deploymentId.setName("deploymentId");
		deploymentId.setColumnName("DEPLOYMENTID_");
		deploymentId.setJavaType("String");
		deploymentId.setLength(100);
		tableDefinition.addColumn(deploymentId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(100);
		tableDefinition.addColumn(name);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(100);
		tableDefinition.addColumn(title);

		ColumnDefinition formHtml = new ColumnDefinition();
		formHtml.setName("formHtml");
		formHtml.setColumnName("FORMHTML_");
		formHtml.setJavaType("Clob");
		tableDefinition.addColumn(formHtml);

		ColumnDefinition formConfig = new ColumnDefinition();
		formConfig.setName("formConfig");
		formConfig.setColumnName("FORMCONFIG_");
		formConfig.setJavaType("Clob");
		tableDefinition.addColumn(formConfig);

		ColumnDefinition outputHtml = new ColumnDefinition();
		outputHtml.setName("outputHtml");
		outputHtml.setColumnName("OUTPUTHTML_");
		outputHtml.setJavaType("Clob");
		tableDefinition.addColumn(outputHtml);

		ColumnDefinition formType = new ColumnDefinition();
		formType.setName("formType");
		formType.setColumnName("FORMTYPE_");
		formType.setJavaType("String");
		formType.setLength(50);
		tableDefinition.addColumn(formType);

		ColumnDefinition sortNo = new ColumnDefinition();
		sortNo.setName("sortNo");
		sortNo.setColumnName("SORTNO_");
		sortNo.setJavaType("Integer");
		tableDefinition.addColumn(sortNo);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

		ColumnDefinition deleteFlag = new ColumnDefinition();
		deleteFlag.setName("deleteFlag");
		deleteFlag.setColumnName("DELETEFLAG_");
		deleteFlag.setJavaType("Integer");
		tableDefinition.addColumn(deleteFlag);

		ColumnDefinition cacheFlag = new ColumnDefinition();
		cacheFlag.setName("cacheFlag");
		cacheFlag.setColumnName("CACHEFLAG_");
		cacheFlag.setJavaType("String");
		cacheFlag.setLength(1);
		tableDefinition.addColumn(cacheFlag);

		ColumnDefinition publicFlag = new ColumnDefinition();
		publicFlag.setName("publicFlag");
		publicFlag.setColumnName("PUBLICFLAG_");
		publicFlag.setJavaType("String");
		publicFlag.setLength(1);
		tableDefinition.addColumn(publicFlag);

		ColumnDefinition userStyleFlag = new ColumnDefinition();
		userStyleFlag.setName("userStyleFlag");
		userStyleFlag.setColumnName("USERSTYLEFLAG_");
		userStyleFlag.setJavaType("String");
		userStyleFlag.setLength(1);
		tableDefinition.addColumn(userStyleFlag);

		ColumnDefinition businessTable = new ColumnDefinition();
		businessTable.setName("businessTable");
		businessTable.setColumnName("BUSINESSTABLE_");
		businessTable.setJavaType("String");
		businessTable.setLength(50);
		tableDefinition.addColumn(businessTable);

		ColumnDefinition primaryKeyColumn = new ColumnDefinition();
		primaryKeyColumn.setName("primaryKeyColumn");
		primaryKeyColumn.setColumnName("PRIMARYKEYCOLUMN_");
		primaryKeyColumn.setJavaType("String");
		primaryKeyColumn.setLength(50);
		tableDefinition.addColumn(primaryKeyColumn);

		ColumnDefinition processName = new ColumnDefinition();
		processName.setName("processName");
		processName.setColumnName("PROCESSNAME_");
		processName.setJavaType("String");
		processName.setLength(200);
		tableDefinition.addColumn(processName);

		ColumnDefinition taskFlag = new ColumnDefinition();
		taskFlag.setName("taskFlag");
		taskFlag.setColumnName("TASKFLAG_");
		taskFlag.setJavaType("String");
		taskFlag.setLength(20);
		tableDefinition.addColumn(taskFlag);

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

		ColumnDefinition updateDate = new ColumnDefinition();
		updateDate.setName("updateDate");
		updateDate.setColumnName("UPDATEDATE_");
		updateDate.setJavaType("Date");
		tableDefinition.addColumn(updateDate);

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY_");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

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

	private FormPageDomainFactory() {

	}

}
