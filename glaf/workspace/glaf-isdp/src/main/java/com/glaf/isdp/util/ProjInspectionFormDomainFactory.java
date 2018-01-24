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
public class ProjInspectionFormDomainFactory {

	public static final String TABLENAME = "PROJ_INSPECTION_FORM";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("projInspectionId", "PROJ_INSPECTION_ID");
		columnMap.put("cellFormId", "CELL_FORM_ID");
		columnMap.put("dotUseId", "DOTUSE_ID");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("intIsCheck", "INTISCHECK");
		columnMap.put("intMust", "INTMUST");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("projInspectionId", "String");
		javaTypeMap.put("cellFormId", "String");
		javaTypeMap.put("dotUseId", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("intIsCheck", "Integer");
		javaTypeMap.put("intMust", "Integer");
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
		tableDefinition.setName("ProjInspectionForm");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(100);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition projInspectionId = new ColumnDefinition();
		projInspectionId.setName("projInspectionId");
		projInspectionId.setColumnName("PROJ_INSPECTION_ID");
		projInspectionId.setJavaType("String");
		projInspectionId.setLength(50);
		tableDefinition.addColumn(projInspectionId);

		ColumnDefinition cellFormId = new ColumnDefinition();
		cellFormId.setName("cellFormId");
		cellFormId.setColumnName("CELL_FORM_ID");
		cellFormId.setJavaType("String");
		cellFormId.setLength(50);
		tableDefinition.addColumn(cellFormId);

		ColumnDefinition dotUseId = new ColumnDefinition();
		dotUseId.setName("dotUseId");
		dotUseId.setColumnName("DOTUSE_ID");
		dotUseId.setJavaType("String");
		dotUseId.setLength(50);
		tableDefinition.addColumn(dotUseId);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition intIsCheck = new ColumnDefinition();
		intIsCheck.setName("intIsCheck");
		intIsCheck.setColumnName("INTISCHECK");
		intIsCheck.setJavaType("Integer");
		tableDefinition.addColumn(intIsCheck);

		ColumnDefinition intMust = new ColumnDefinition();
		intMust.setName("intMust");
		intMust.setColumnName("INTMUST");
		intMust.setJavaType("Integer");
		tableDefinition.addColumn(intMust);

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

	private ProjInspectionFormDomainFactory() {

	}

}
