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
public class FlowActivDDomainFactory {

	public static final String TABLENAME = "FLOW_ACTIV_D";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("processId", "PROCESS_ID");
		columnMap.put("typeOfAct", "TYPEOFACT");
		columnMap.put("name", "NAME");
		columnMap.put("content", "CONTENT");
		columnMap.put("strFuntion", "STRFUNTION");
		columnMap.put("netRoleId", "NETROLEID");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("timeLimit", "TIMELIMIT");
		columnMap.put("isSave", "ISSAVE");
		columnMap.put("isDel", "ISDEL");
		columnMap.put("intSelectUser", "INTSELECTUSER");
		columnMap.put("intUseDomain", "INTUSEDOMAIN");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("processId", "String");
		javaTypeMap.put("typeOfAct", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("strFuntion", "String");
		javaTypeMap.put("netRoleId", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("timeLimit", "Double");
		javaTypeMap.put("isSave", "Integer");
		javaTypeMap.put("isDel", "Integer");
		javaTypeMap.put("intSelectUser", "Integer");
		javaTypeMap.put("intUseDomain", "Integer");
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
		tableDefinition.setName("FlowActivD");

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

		ColumnDefinition typeOfAct = new ColumnDefinition();
		typeOfAct.setName("typeOfAct");
		typeOfAct.setColumnName("TYPEOFACT");
		typeOfAct.setJavaType("String");
		typeOfAct.setLength(20);
		tableDefinition.addColumn(typeOfAct);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME");
		name.setJavaType("String");
		name.setLength(100);
		tableDefinition.addColumn(name);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(100);
		tableDefinition.addColumn(content);

		ColumnDefinition strFuntion = new ColumnDefinition();
		strFuntion.setName("strFuntion");
		strFuntion.setColumnName("STRFUNTION");
		strFuntion.setJavaType("String");
		strFuntion.setLength(1000);
		tableDefinition.addColumn(strFuntion);

		ColumnDefinition netRoleId = new ColumnDefinition();
		netRoleId.setName("netRoleId");
		netRoleId.setColumnName("NETROLEID");
		netRoleId.setJavaType("String");
		netRoleId.setLength(50);
		tableDefinition.addColumn(netRoleId);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition timeLimit = new ColumnDefinition();
		timeLimit.setName("timeLimit");
		timeLimit.setColumnName("TIMELIMIT");
		timeLimit.setJavaType("Double");
		tableDefinition.addColumn(timeLimit);

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

		ColumnDefinition intSelectUser = new ColumnDefinition();
		intSelectUser.setName("intSelectUser");
		intSelectUser.setColumnName("INTSELECTUSER");
		intSelectUser.setJavaType("Integer");
		tableDefinition.addColumn(intSelectUser);

		ColumnDefinition intUseDomain = new ColumnDefinition();
		intUseDomain.setName("intUseDomain");
		intUseDomain.setColumnName("INTUSEDOMAIN");
		intUseDomain.setJavaType("Integer");
		tableDefinition.addColumn(intUseDomain);

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

	private FlowActivDDomainFactory() {

	}

}
