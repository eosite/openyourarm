package com.glaf.form.core.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class PageProcessInstanceDomainFactory {

	public static final String TABLENAME = "PAGE_PROCESSINSTANCE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("processInstanceId", "PROCESSINSTANCEID_");
		columnMap.put("pageId", "PAGEID_");
		columnMap.put("processName", "PROCESSNAME_");
		columnMap.put("title", "TITLE_");
		columnMap.put("businessTable", "BUSINESSTABLE_");
		columnMap.put("businessKey", "BUSINESSKEY_");
		columnMap.put("status", "STATUS_");
		columnMap.put("wfStatus", "WFSTATUS_");
		columnMap.put("sortNo", "SORTNO_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("deleteFlag", "DELETEFLAG_");
		columnMap.put("version", "VERSION_");
		columnMap.put("startTime", "STARTTIME_");
		columnMap.put("endTime", "ENDTIME_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("createBy", "CREATEBY_");

		javaTypeMap.put("processInstanceId", "String");
		javaTypeMap.put("pageId", "String");
		javaTypeMap.put("processName", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("businessTable", "String");
		javaTypeMap.put("businessKey", "String");
		javaTypeMap.put("status", "Integer");
		javaTypeMap.put("wfStatus", "Integer");
		javaTypeMap.put("sortNo", "Integer");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("deleteFlag", "Integer");
		javaTypeMap.put("version", "Integer");
		javaTypeMap.put("startTime", "Date");
		javaTypeMap.put("endTime", "Date");
		javaTypeMap.put("createTime", "Date");
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
		tableDefinition.setName("PageProcessInstance");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("processInstanceId");
		idColumn.setColumnName("PROCESSINSTANCEID_");
		idColumn.setJavaType("String");
		idColumn.setLength(200);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition pageId = new ColumnDefinition();
		pageId.setName("pageId");
		pageId.setColumnName("PAGEID_");
		pageId.setJavaType("String");
		pageId.setLength(50);
		tableDefinition.addColumn(pageId);

		ColumnDefinition processName = new ColumnDefinition();
		processName.setName("processName");
		processName.setColumnName("PROCESSNAME_");
		processName.setJavaType("String");
		processName.setLength(100);
		tableDefinition.addColumn(processName);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(200);
		tableDefinition.addColumn(title);

		ColumnDefinition businessTable = new ColumnDefinition();
		businessTable.setName("businessTable");
		businessTable.setColumnName("BUSINESSTABLE_");
		businessTable.setJavaType("String");
		businessTable.setLength(50);
		tableDefinition.addColumn(businessTable);

		ColumnDefinition businessKey = new ColumnDefinition();
		businessKey.setName("businessKey");
		businessKey.setColumnName("BUSINESSKEY_");
		businessKey.setJavaType("String");
		businessKey.setLength(500);
		tableDefinition.addColumn(businessKey);

		ColumnDefinition status = new ColumnDefinition();
		status.setName("status");
		status.setColumnName("STATUS_");
		status.setJavaType("Integer");
		tableDefinition.addColumn(status);

		ColumnDefinition wfStatus = new ColumnDefinition();
		wfStatus.setName("wfStatus");
		wfStatus.setColumnName("WFSTATUS_");
		wfStatus.setJavaType("Integer");
		tableDefinition.addColumn(wfStatus);

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

		ColumnDefinition version = new ColumnDefinition();
		version.setName("version");
		version.setColumnName("VERSION_");
		version.setJavaType("Integer");
		tableDefinition.addColumn(version);

		ColumnDefinition startTime = new ColumnDefinition();
		startTime.setName("startTime");
		startTime.setColumnName("STARTTIME_");
		startTime.setJavaType("Date");
		tableDefinition.addColumn(startTime);

		ColumnDefinition endTime = new ColumnDefinition();
		endTime.setName("endTime");
		endTime.setColumnName("ENDTIME_");
		endTime.setJavaType("Date");
		tableDefinition.addColumn(endTime);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

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

	private PageProcessInstanceDomainFactory() {

	}

}
