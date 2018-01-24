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
public class FlowForwardDDomainFactory {

	public static final String TABLENAME = "FLOW_FORWARD_D";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("processId", "PROCESS_ID");
		columnMap.put("activId", "ACTIV_ID");
		columnMap.put("activPre", "ACTIV_PRE");
		columnMap.put("activNext", "ACTIV_NEXT");
		columnMap.put("isSave", "ISSAVE");
		columnMap.put("isDel", "ISDEL");
		columnMap.put("listNo", "LISTNO");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("processId", "String");
		javaTypeMap.put("activId", "String");
		javaTypeMap.put("activPre", "String");
		javaTypeMap.put("activNext", "String");
		javaTypeMap.put("isSave", "Integer");
		javaTypeMap.put("isDel", "Integer");
		javaTypeMap.put("listNo", "Integer");
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
		tableDefinition.setName("FlowForwardD");

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

		ColumnDefinition activId = new ColumnDefinition();
		activId.setName("activId");
		activId.setColumnName("ACTIV_ID");
		activId.setJavaType("String");
		activId.setLength(50);
		tableDefinition.addColumn(activId);

		ColumnDefinition activPre = new ColumnDefinition();
		activPre.setName("activPre");
		activPre.setColumnName("ACTIV_PRE");
		activPre.setJavaType("String");
		activPre.setLength(50);
		tableDefinition.addColumn(activPre);

		ColumnDefinition activNext = new ColumnDefinition();
		activNext.setName("activNext");
		activNext.setColumnName("ACTIV_NEXT");
		activNext.setJavaType("String");
		activNext.setLength(50);
		tableDefinition.addColumn(activNext);

		ColumnDefinition isSave = new ColumnDefinition();
		isSave.setName("isSave");
		isSave.setColumnName("ISSAVE");
		isSave.setJavaType("Integer");
		tableDefinition.addColumn(isSave);

		ColumnDefinition isDel = new ColumnDefinition();
		isDel.setName("isDel");
		isDel.setColumnName("ISDEL");
		isDel.setJavaType("Integer");
		tableDefinition.addColumn(isDel);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

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

	private FlowForwardDDomainFactory() {

	}

}
