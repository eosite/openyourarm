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
public class CellDataTableDomainFactory {

	public static final String TABLENAME = "CELL_DATA_TABLE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("tableName", "TABLENAME");
		columnMap.put("name", "NAME");
		columnMap.put("addType", "ADDTYPE");
		columnMap.put("maxUser", "MAXUSER");
		columnMap.put("maxSys", "MAXSYS");
		columnMap.put("userId", "USERID");
		columnMap.put("ctime", "CTIME");
		columnMap.put("content", "CONTENT");
		columnMap.put("sysNum", "SYSNUM");
		columnMap.put("isSubTable", "ISSUBTABLE");
		columnMap.put("topId", "TOPID");
		columnMap.put("fileDotFileId", "FILEDOT_FILEID");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("winWidth", "WIN_WIDTH");
		columnMap.put("winHeight", "WIN_HEIGHT");
		columnMap.put("intQuote", "INTQUOTE");
		columnMap.put("intLineEdit", "INTLINEEDIT");
		columnMap.put("printFileId", "PRINTFILEID");
		columnMap.put("intUseSTreeWBS", "INTUSESTREEWBS");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("tableName", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("addType", "Integer");
		javaTypeMap.put("maxUser", "Integer");
		javaTypeMap.put("maxSys", "Integer");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("sysNum", "String");
		javaTypeMap.put("isSubTable", "String");
		javaTypeMap.put("topId", "String");
		javaTypeMap.put("fileDotFileId", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("winWidth", "Integer");
		javaTypeMap.put("winHeight", "Integer");
		javaTypeMap.put("intQuote", "Integer");
		javaTypeMap.put("intLineEdit", "Integer");
		javaTypeMap.put("printFileId", "String");
		javaTypeMap.put("intUseSTreeWBS", "Integer");
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
		tableDefinition.setName("CellDataTable");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition tableName1 = new ColumnDefinition();
		tableName1.setName("tableName");
		tableName1.setColumnName("TABLENAME");
		tableName1.setJavaType("String");
		tableName1.setLength(50);
		tableDefinition.addColumn(tableName1);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME");
		name.setJavaType("String");
		name.setLength(255);
		tableDefinition.addColumn(name);

		ColumnDefinition addType = new ColumnDefinition();
		addType.setName("addType");
		addType.setColumnName("ADDTYPE");
		addType.setJavaType("Integer");
		tableDefinition.addColumn(addType);

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

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID");
		userId.setJavaType("String");
		userId.setLength(50);
		tableDefinition.addColumn(userId);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(250);
		tableDefinition.addColumn(content);

		ColumnDefinition sysNum = new ColumnDefinition();
		sysNum.setName("sysNum");
		sysNum.setColumnName("SYSNUM");
		sysNum.setJavaType("String");
		sysNum.setLength(100);
		tableDefinition.addColumn(sysNum);

		ColumnDefinition isSubTable = new ColumnDefinition();
		isSubTable.setName("isSubTable");
		isSubTable.setColumnName("ISSUBTABLE");
		isSubTable.setJavaType("String");
		isSubTable.setLength(1);
		tableDefinition.addColumn(isSubTable);

		ColumnDefinition topId = new ColumnDefinition();
		topId.setName("topId");
		topId.setColumnName("TOPID");
		topId.setJavaType("String");
		topId.setLength(50);
		tableDefinition.addColumn(topId);

		ColumnDefinition fileDotFileId = new ColumnDefinition();
		fileDotFileId.setName("fileDotFileId");
		fileDotFileId.setColumnName("FILEDOT_FILEID");
		fileDotFileId.setJavaType("String");
		fileDotFileId.setLength(50);
		tableDefinition.addColumn(fileDotFileId);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEX_ID");
		indexId.setJavaType("Integer");
		tableDefinition.addColumn(indexId);

		ColumnDefinition winWidth = new ColumnDefinition();
		winWidth.setName("winWidth");
		winWidth.setColumnName("WIN_WIDTH");
		winWidth.setJavaType("Integer");
		tableDefinition.addColumn(winWidth);

		ColumnDefinition winHeight = new ColumnDefinition();
		winHeight.setName("winHeight");
		winHeight.setColumnName("WIN_HEIGHT");
		winHeight.setJavaType("Integer");
		tableDefinition.addColumn(winHeight);

		ColumnDefinition intQuote = new ColumnDefinition();
		intQuote.setName("intQuote");
		intQuote.setColumnName("INTQUOTE");
		intQuote.setJavaType("Integer");
		tableDefinition.addColumn(intQuote);

		ColumnDefinition intLineEdit = new ColumnDefinition();
		intLineEdit.setName("intLineEdit");
		intLineEdit.setColumnName("INTLINEEDIT");
		intLineEdit.setJavaType("Integer");
		tableDefinition.addColumn(intLineEdit);

		ColumnDefinition printFileId = new ColumnDefinition();
		printFileId.setName("printFileId");
		printFileId.setColumnName("PRINTFILEID");
		printFileId.setJavaType("String");
		printFileId.setLength(50);
		tableDefinition.addColumn(printFileId);

		ColumnDefinition intUseSTreeWBS = new ColumnDefinition();
		intUseSTreeWBS.setName("intUseSTreeWBS");
		intUseSTreeWBS.setColumnName("INTUSESTREEWBS");
		intUseSTreeWBS.setJavaType("Integer");
		tableDefinition.addColumn(intUseSTreeWBS);

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

	private CellDataTableDomainFactory() {

	}

}
