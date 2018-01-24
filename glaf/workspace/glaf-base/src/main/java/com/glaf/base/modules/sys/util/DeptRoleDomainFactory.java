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
public class DeptRoleDomainFactory {

	public static final String TABLENAME = "SYS_DEPT_ROLE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("deptId", "DEPTID");
		columnMap.put("deptFlag", "DEPTFLAG");
		columnMap.put("menuFlag", "MENUFLAG");
		columnMap.put("roleId", "ROLEID");
		columnMap.put("isPropagationAllowed", "ISPROPAGATIONALLOWED_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("deptId", "Long");
		javaTypeMap.put("deptFlag", "Integer");
		javaTypeMap.put("menuFlag", "Integer");
		javaTypeMap.put("roleId", "Long");
		javaTypeMap.put("isPropagationAllowed", "String");
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
		tableDefinition.setName("DeptRole");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition deptId = new ColumnDefinition();
		deptId.setName("deptId");
		deptId.setColumnName("DEPTID");
		deptId.setJavaType("Long");
		tableDefinition.addColumn(deptId);

		ColumnDefinition deptFlag = new ColumnDefinition();
		deptFlag.setName("deptFlag");
		deptFlag.setColumnName("DEPTFLAG");
		deptFlag.setJavaType("Integer");
		tableDefinition.addColumn(deptFlag);

		ColumnDefinition menuFlag = new ColumnDefinition();
		menuFlag.setName("menuFlag");
		menuFlag.setColumnName("MENUFLAG");
		menuFlag.setJavaType("Integer");
		tableDefinition.addColumn(menuFlag);

		ColumnDefinition roleId = new ColumnDefinition();
		roleId.setName("roleId");
		roleId.setColumnName("ROLEID");
		roleId.setJavaType("Long");
		tableDefinition.addColumn(roleId);

		ColumnDefinition isPropagationAllowed = new ColumnDefinition();
		isPropagationAllowed.setName("isPropagationAllowed");
		isPropagationAllowed.setColumnName("ISPROPAGATIONALLOWED_");
		isPropagationAllowed.setJavaType("String");
		isPropagationAllowed.setLength(1);
		tableDefinition.addColumn(isPropagationAllowed);

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

	private DeptRoleDomainFactory() {

	}

}
