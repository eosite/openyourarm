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
public class ProjCellAndFilesDomainFactory {

	public static final String TABLENAME = "PROJ_CELLANDFILES";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("intType", "INTTYPE");
		columnMap.put("name", "NAME");
		columnMap.put("defId", "DEFID");
		columnMap.put("useId", "USEID");
		columnMap.put("intPage0", "INTPAGE0");
		columnMap.put("intPage1", "INTPAGE1");
		columnMap.put("intPage2", "INTPAGE2");
		columnMap.put("intFinish", "INTFINISH");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("intType", "Integer");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("defId", "String");
		javaTypeMap.put("useId", "String");
		javaTypeMap.put("intPage0", "Integer");
		javaTypeMap.put("intPage1", "Integer");
		javaTypeMap.put("intPage2", "Integer");
		javaTypeMap.put("intFinish", "Integer");
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
		tableDefinition.setName("ProjCellAndFiles");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEX_ID");
		indexId.setJavaType("Integer");
		tableDefinition.addColumn(indexId);

		ColumnDefinition intType = new ColumnDefinition();
		intType.setName("intType");
		intType.setColumnName("INTTYPE");
		intType.setJavaType("Integer");
		tableDefinition.addColumn(intType);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME");
		name.setJavaType("String");
		name.setLength(200);
		tableDefinition.addColumn(name);

		ColumnDefinition defId = new ColumnDefinition();
		defId.setName("defId");
		defId.setColumnName("DEFID");
		defId.setJavaType("String");
		defId.setLength(50);
		tableDefinition.addColumn(defId);

		ColumnDefinition useId = new ColumnDefinition();
		useId.setName("useId");
		useId.setColumnName("USEID");
		useId.setJavaType("String");
		useId.setLength(50);
		tableDefinition.addColumn(useId);

		ColumnDefinition intPage0 = new ColumnDefinition();
		intPage0.setName("intPage0");
		intPage0.setColumnName("INTPAGE0");
		intPage0.setJavaType("Integer");
		tableDefinition.addColumn(intPage0);

		ColumnDefinition intPage1 = new ColumnDefinition();
		intPage1.setName("intPage1");
		intPage1.setColumnName("INTPAGE1");
		intPage1.setJavaType("Integer");
		tableDefinition.addColumn(intPage1);

		ColumnDefinition intPage2 = new ColumnDefinition();
		intPage2.setName("intPage2");
		intPage2.setColumnName("INTPAGE2");
		intPage2.setJavaType("Integer");
		tableDefinition.addColumn(intPage2);

		ColumnDefinition intFinish = new ColumnDefinition();
		intFinish.setName("intFinish");
		intFinish.setColumnName("INTFINISH");
		intFinish.setJavaType("Integer");
		tableDefinition.addColumn(intFinish);

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

	private ProjCellAndFilesDomainFactory() {

	}

}
