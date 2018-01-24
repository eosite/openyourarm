package com.glaf.base.modules.sys.util;

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
public class SysAgentDomainFactory {

	public static final String TABLENAME = "SYS_AGENT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("assignFrom", "ASSIGNFROM_");
		columnMap.put("assignFromName", "ASSIGNFROMNAME_");
		columnMap.put("assignTo", "ASSIGNTO_");
		columnMap.put("assignToName", "ASSIGNTONAME_");
		columnMap.put("processName", "PROCESSNAME_");
		columnMap.put("taskName", "TASKNAME_");
		columnMap.put("serviceKey", "SERVICEKEY_");
		columnMap.put("objectId", "OBJECTID_");
		columnMap.put("objectValue", "OBJECTVALUE_");
		columnMap.put("startDate", "STARTDATE_");
		columnMap.put("endDate", "ENDDATE_");
		columnMap.put("agentType", "AGENTTYPE_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createDate", "CREATEDATE_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("assignFrom", "String");
		javaTypeMap.put("assignFromName", "String");
		javaTypeMap.put("assignTo", "String");
		javaTypeMap.put("assignToName", "String");
		javaTypeMap.put("processName", "String");
		javaTypeMap.put("taskName", "String");
		javaTypeMap.put("serviceKey", "String");
		javaTypeMap.put("objectId", "String");
		javaTypeMap.put("objectValue", "String");
		javaTypeMap.put("startDate", "Date");
		javaTypeMap.put("endDate", "Date");
		javaTypeMap.put("agentType", "Integer");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createDate", "Date");
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
		tableDefinition.setName("SysAgent");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition assignFrom = new ColumnDefinition();
		assignFrom.setName("assignFrom");
		assignFrom.setColumnName("ASSIGNFROM_");
		assignFrom.setJavaType("String");
		assignFrom.setLength(50);
		tableDefinition.addColumn(assignFrom);

		ColumnDefinition assignFromName = new ColumnDefinition();
		assignFromName.setName("assignFromName");
		assignFromName.setColumnName("ASSIGNFROMNAME_");
		assignFromName.setJavaType("String");
		assignFromName.setLength(50);
		tableDefinition.addColumn(assignFromName);

		ColumnDefinition assignTo = new ColumnDefinition();
		assignTo.setName("assignTo");
		assignTo.setColumnName("ASSIGNTO_");
		assignTo.setJavaType("String");
		assignTo.setLength(50);
		tableDefinition.addColumn(assignTo);

		ColumnDefinition assignToName = new ColumnDefinition();
		assignToName.setName("assignToName");
		assignToName.setColumnName("ASSIGNTONAME_");
		assignToName.setJavaType("String");
		assignToName.setLength(50);
		tableDefinition.addColumn(assignToName);

		ColumnDefinition processName = new ColumnDefinition();
		processName.setName("processName");
		processName.setColumnName("PROCESSNAME_");
		processName.setJavaType("String");
		processName.setLength(50);
		tableDefinition.addColumn(processName);

		ColumnDefinition taskName = new ColumnDefinition();
		taskName.setName("taskName");
		taskName.setColumnName("TASKNAME_");
		taskName.setJavaType("String");
		taskName.setLength(50);
		tableDefinition.addColumn(taskName);

		ColumnDefinition serviceKey = new ColumnDefinition();
		serviceKey.setName("serviceKey");
		serviceKey.setColumnName("SERVICEKEY_");
		serviceKey.setJavaType("String");
		serviceKey.setLength(50);
		tableDefinition.addColumn(serviceKey);

		ColumnDefinition objectId = new ColumnDefinition();
		objectId.setName("objectId");
		objectId.setColumnName("OBJECTID_");
		objectId.setJavaType("String");
		objectId.setLength(50);
		tableDefinition.addColumn(objectId);

		ColumnDefinition objectValue = new ColumnDefinition();
		objectValue.setName("objectValue");
		objectValue.setColumnName("OBJECTVALUE_");
		objectValue.setJavaType("String");
		objectValue.setLength(50);
		tableDefinition.addColumn(objectValue);

		ColumnDefinition startDate = new ColumnDefinition();
		startDate.setName("startDate");
		startDate.setColumnName("STARTDATE_");
		startDate.setJavaType("Date");
		tableDefinition.addColumn(startDate);

		ColumnDefinition endDate = new ColumnDefinition();
		endDate.setName("endDate");
		endDate.setColumnName("ENDDATE_");
		endDate.setJavaType("Date");
		tableDefinition.addColumn(endDate);

		ColumnDefinition agentType = new ColumnDefinition();
		agentType.setName("agentType");
		agentType.setColumnName("AGENTTYPE_");
		agentType.setJavaType("Integer");
		tableDefinition.addColumn(agentType);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition createDate = new ColumnDefinition();
		createDate.setName("createDate");
		createDate.setColumnName("CREATEDATE_");
		createDate.setJavaType("Date");
		tableDefinition.addColumn(createDate);

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

	private SysAgentDomainFactory() {

	}

}
