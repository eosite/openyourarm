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
public class CellDataFieldDomainFactory {

	public static final String TABLENAME = "CELL_DATA_FIELD";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("tableId", "TABLEID");
		columnMap.put("fieldName", "FIELDNAME");
		columnMap.put("userId", "USERID");
		columnMap.put("maxUser", "MAXUSER");
		columnMap.put("maxSys", "MAXSYS");
		columnMap.put("ctime", "CTIME");
		columnMap.put("sysNum", "SYSNUM");
		columnMap.put("tableName", "TABLENAME");
		columnMap.put("dname", "DNAME");
		columnMap.put("userIndex", "USERINDEX");
		columnMap.put("treeTableNameB", "TREETABLENAME_B");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("tableId", "String");
		javaTypeMap.put("fieldName", "String");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("maxUser", "Integer");
		javaTypeMap.put("maxSys", "Integer");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("sysNum", "String");
		javaTypeMap.put("tableName", "String");
		javaTypeMap.put("dname", "String");
		javaTypeMap.put("userIndex", "String");
		javaTypeMap.put("treeTableNameB", "String");
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
		tableDefinition.setName("CellDataField");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition tableId = new ColumnDefinition();
		tableId.setName("tableId");
		tableId.setColumnName("TABLEID");
		tableId.setJavaType("String");
		tableId.setLength(50);
		tableDefinition.addColumn(tableId);

		ColumnDefinition fieldName = new ColumnDefinition();
		fieldName.setName("fieldName");
		fieldName.setColumnName("FIELDNAME");
		fieldName.setJavaType("String");
		fieldName.setLength(50);
		tableDefinition.addColumn(fieldName);

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID");
		userId.setJavaType("String");
		userId.setLength(50);
		tableDefinition.addColumn(userId);

		ColumnDefinition maxUser = new ColumnDefinition();
		maxUser.setName("maxUser");
		maxUser.setColumnName("MAXUSER");
		maxUser.setJavaType("Integer");
		tableDefinition.addColumn(maxUser);

		ColumnDefinition maxSys = new ColumnDefinition();
		maxSys.setName("maxSys");
		maxSys.setColumnName("MAXSYS");
		maxSys.setJavaType("Integer");
		tableDefinition.addColumn(maxSys);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition sysNum = new ColumnDefinition();
		sysNum.setName("sysNum");
		sysNum.setColumnName("SYSNUM");
		sysNum.setJavaType("String");
		sysNum.setLength(100);
		tableDefinition.addColumn(sysNum);

		ColumnDefinition tableName1 = new ColumnDefinition();
		tableName1.setName("tableName");
		tableName1.setColumnName("TABLENAME");
		tableName1.setJavaType("String");
		tableName1.setLength(50);
		tableDefinition.addColumn(tableName1);

		ColumnDefinition dname = new ColumnDefinition();
		dname.setName("dname");
		dname.setColumnName("DNAME");
		dname.setJavaType("String");
		dname.setLength(50);
		tableDefinition.addColumn(dname);

		ColumnDefinition userIndex = new ColumnDefinition();
		userIndex.setName("userIndex");
		userIndex.setColumnName("USERINDEX");
		userIndex.setJavaType("String");
		userIndex.setLength(50);
		tableDefinition.addColumn(userIndex);

		ColumnDefinition treeTableNameB = new ColumnDefinition();
		treeTableNameB.setName("treeTableNameB");
		treeTableNameB.setColumnName("TREETABLENAME_B");
		treeTableNameB.setJavaType("String");
		treeTableNameB.setLength(50);
		tableDefinition.addColumn(treeTableNameB);

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

	private CellDataFieldDomainFactory() {

	}

}
