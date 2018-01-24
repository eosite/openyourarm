package com.glaf.isdp.util;

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
public class FlowForwardDomainFactory {

	public static final String TABLENAME = "FLOW_FORWARD";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("processId", "PROCESS_ID");
		columnMap.put("activDId", "ACTIV_D_ID");
		columnMap.put("activDNext", "ACTIV_D_NEXT");
		columnMap.put("sendType", "SENDTYPE");
		columnMap.put("sendTimes", "SENDTIMES");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("processId", "String");
		javaTypeMap.put("activDId", "String");
		javaTypeMap.put("activDNext", "String");
		javaTypeMap.put("sendType", "Integer");
		javaTypeMap.put("sendTimes", "Integer");
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
		tableDefinition.setName("FlowForward");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition processId = new ColumnDefinition();
		processId.setName("processId");
		processId.setColumnName("PROCESS_ID");
		processId.setJavaType("String");
		processId.setLength(50);
		tableDefinition.addColumn(processId);

		ColumnDefinition activDId = new ColumnDefinition();
		activDId.setName("activDId");
		activDId.setColumnName("ACTIV_D_ID");
		activDId.setJavaType("String");
		activDId.setLength(50);
		tableDefinition.addColumn(activDId);

		ColumnDefinition activDNext = new ColumnDefinition();
		activDNext.setName("activDNext");
		activDNext.setColumnName("ACTIV_D_NEXT");
		activDNext.setJavaType("String");
		activDNext.setLength(50);
		tableDefinition.addColumn(activDNext);

		ColumnDefinition sendType = new ColumnDefinition();
		sendType.setName("sendType");
		sendType.setColumnName("SENDTYPE");
		sendType.setJavaType("Integer");
		tableDefinition.addColumn(sendType);

		ColumnDefinition sendTimes = new ColumnDefinition();
		sendTimes.setName("sendTimes");
		sendTimes.setColumnName("SENDTIMES");
		sendTimes.setJavaType("Integer");
		tableDefinition.addColumn(sendTimes);

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
		if (dataRequest.getFilter() != null) {
			if (dataRequest.getFilter().getField() != null) {
				dataRequest.getFilter().setColumn(
						columnMap.get(dataRequest.getFilter().getField()));
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
			}

			List<FilterDescriptor> filters = dataRequest.getFilter()
					.getFilters();
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

	private FlowForwardDomainFactory() {

	}

}
