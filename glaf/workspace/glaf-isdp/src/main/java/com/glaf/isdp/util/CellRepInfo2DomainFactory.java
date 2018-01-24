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
public class CellRepInfo2DomainFactory {

	public static final String TABLENAME = "CELL_REPINFO2";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("frmType", "FRMTYPE");
		columnMap.put("type", "TYPE");
		columnMap.put("content", "CONTENT");
		columnMap.put("formula", "FORMULA");
		columnMap.put("ostTableName", "OST_TABLENAME");
		columnMap.put("ostRow", "OST_ROW");
		columnMap.put("ostCol", "OST_COL");
		columnMap.put("ostRowEnd", "OST_ROWEND");
		columnMap.put("ostColEnd", "OST_COLEND");
		columnMap.put("ostCellId", "OST_CELLID");
		columnMap.put("fileDotFileId", "FILEDOT_FILEID");
		columnMap.put("ostColor", "OST_COLOR");
		columnMap.put("ostWay", "OST_WAY");
		columnMap.put("roleId", "ROLE_ID");
		columnMap.put("tableName", "TABLENAME");
		columnMap.put("fname", "FNAME");
		columnMap.put("dname", "DNAME");
		columnMap.put("isSubTable", "ISSUBTABLE");
		columnMap.put("tableName2", "TABLENAME2");
		columnMap.put("intAutoinValue", "INTAUTOINVALUE");
		columnMap.put("intSelfClick", "INTSELFCLICK");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("frmType", "String");
		javaTypeMap.put("type", "Integer");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("formula", "String");
		javaTypeMap.put("ostTableName", "String");
		javaTypeMap.put("ostRow", "Integer");
		javaTypeMap.put("ostCol", "Integer");
		javaTypeMap.put("ostRowEnd", "Integer");
		javaTypeMap.put("ostColEnd", "Integer");
		javaTypeMap.put("ostCellId", "String");
		javaTypeMap.put("fileDotFileId", "String");
		javaTypeMap.put("ostColor", "Integer");
		javaTypeMap.put("ostWay", "Integer");
		javaTypeMap.put("roleId", "Integer");
		javaTypeMap.put("tableName", "String");
		javaTypeMap.put("fname", "String");
		javaTypeMap.put("dname", "String");
		javaTypeMap.put("isSubTable", "String");
		javaTypeMap.put("tableName2", "String");
		javaTypeMap.put("intAutoinValue", "Integer");
		javaTypeMap.put("intSelfClick", "Integer");
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
		tableDefinition.setName("CellRepInfo2");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition frmType = new ColumnDefinition();
		frmType.setName("frmType");
		frmType.setColumnName("FRMTYPE");
		frmType.setJavaType("String");
		frmType.setLength(50);
		tableDefinition.addColumn(frmType);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE");
		type.setJavaType("Integer");
		tableDefinition.addColumn(type);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(0);
		tableDefinition.addColumn(content);

		ColumnDefinition formula = new ColumnDefinition();
		formula.setName("formula");
		formula.setColumnName("FORMULA");
		formula.setJavaType("String");
		formula.setLength(0);
		tableDefinition.addColumn(formula);

		ColumnDefinition ostTableName = new ColumnDefinition();
		ostTableName.setName("ostTableName");
		ostTableName.setColumnName("OST_TABLENAME");
		ostTableName.setJavaType("String");
		ostTableName.setLength(50);
		tableDefinition.addColumn(ostTableName);

		ColumnDefinition ostRow = new ColumnDefinition();
		ostRow.setName("ostRow");
		ostRow.setColumnName("OST_ROW");
		ostRow.setJavaType("Integer");
		tableDefinition.addColumn(ostRow);

		ColumnDefinition ostCol = new ColumnDefinition();
		ostCol.setName("ostCol");
		ostCol.setColumnName("OST_COL");
		ostCol.setJavaType("Integer");
		tableDefinition.addColumn(ostCol);

		ColumnDefinition ostRowEnd = new ColumnDefinition();
		ostRowEnd.setName("ostRowEnd");
		ostRowEnd.setColumnName("OST_ROWEND");
		ostRowEnd.setJavaType("Integer");
		tableDefinition.addColumn(ostRowEnd);

		ColumnDefinition ostColEnd = new ColumnDefinition();
		ostColEnd.setName("ostColEnd");
		ostColEnd.setColumnName("OST_COLEND");
		ostColEnd.setJavaType("Integer");
		tableDefinition.addColumn(ostColEnd);

		ColumnDefinition ostCellId = new ColumnDefinition();
		ostCellId.setName("ostCellId");
		ostCellId.setColumnName("OST_CELLID");
		ostCellId.setJavaType("String");
		ostCellId.setLength(20);
		tableDefinition.addColumn(ostCellId);

		ColumnDefinition fileDotFileId = new ColumnDefinition();
		fileDotFileId.setName("fileDotFileId");
		fileDotFileId.setColumnName("FILEDOT_FILEID");
		fileDotFileId.setJavaType("String");
		fileDotFileId.setLength(50);
		tableDefinition.addColumn(fileDotFileId);

		ColumnDefinition ostColor = new ColumnDefinition();
		ostColor.setName("ostColor");
		ostColor.setColumnName("OST_COLOR");
		ostColor.setJavaType("Integer");
		tableDefinition.addColumn(ostColor);

		ColumnDefinition ostWay = new ColumnDefinition();
		ostWay.setName("ostWay");
		ostWay.setColumnName("OST_WAY");
		ostWay.setJavaType("Integer");
		tableDefinition.addColumn(ostWay);

		ColumnDefinition roleId = new ColumnDefinition();
		roleId.setName("roleId");
		roleId.setColumnName("ROLE_ID");
		roleId.setJavaType("Integer");
		tableDefinition.addColumn(roleId);

		ColumnDefinition tableName1 = new ColumnDefinition();
		tableName1.setName("tableName");
		tableName1.setColumnName("TABLENAME");
		tableName1.setJavaType("String");
		tableName1.setLength(50);
		tableDefinition.addColumn(tableName1);

		ColumnDefinition fname = new ColumnDefinition();
		fname.setName("fname");
		fname.setColumnName("FNAME");
		fname.setJavaType("String");
		fname.setLength(255);
		tableDefinition.addColumn(fname);

		ColumnDefinition dname = new ColumnDefinition();
		dname.setName("dname");
		dname.setColumnName("DNAME");
		dname.setJavaType("String");
		dname.setLength(50);
		tableDefinition.addColumn(dname);

		ColumnDefinition isSubTable = new ColumnDefinition();
		isSubTable.setName("isSubTable");
		isSubTable.setColumnName("ISSUBTABLE");
		isSubTable.setJavaType("String");
		isSubTable.setLength(1);
		tableDefinition.addColumn(isSubTable);

		ColumnDefinition tableName2 = new ColumnDefinition();
		tableName2.setName("tableName2");
		tableName2.setColumnName("TABLENAME2");
		tableName2.setJavaType("String");
		tableName2.setLength(50);
		tableDefinition.addColumn(tableName2);

		ColumnDefinition intAutoinValue = new ColumnDefinition();
		intAutoinValue.setName("intAutoinValue");
		intAutoinValue.setColumnName("INTAUTOINVALUE");
		intAutoinValue.setJavaType("Integer");
		tableDefinition.addColumn(intAutoinValue);

		ColumnDefinition intSelfClick = new ColumnDefinition();
		intSelfClick.setName("intSelfClick");
		intSelfClick.setColumnName("INTSELFCLICK");
		intSelfClick.setJavaType("Integer");
		tableDefinition.addColumn(intSelfClick);

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

	private CellRepInfo2DomainFactory() {

	}

}
