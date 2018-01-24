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
public class CellTreeDotDomainFactory {

	public static final String TABLENAME = "CELL_TREEDOT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("id", "ID");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("indexName", "INDEX_NAME");
		columnMap.put("Level", "NLEVEL");
		columnMap.put("num", "NUM");
		columnMap.put("content", "CONTENT");
		columnMap.put("nodeIco", "NODEICO");
		columnMap.put("sindexName", "SINDEX_NAME");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("viewType", "VIEWTYPE");
		columnMap.put("isMode", "ISMODE");
		columnMap.put("modeTableId", "MODETABLE_ID");
		columnMap.put("isSystem", "ISSYSTEM");
		columnMap.put("customData", "CUSTOMDATA");
		columnMap.put("intSystemSelect", "INTSYSTEMSELECT");
		columnMap.put("intUsed", "INTUSED");
		columnMap.put("intDel", "INTDEL");
		columnMap.put("typeTableName", "TYPE_TABLENAME");
		columnMap.put("intOperation", "INTOPERATION");
		columnMap.put("picFile", "PICFILE");
		columnMap.put("fileContent", "FILE_CONTENT");
		columnMap.put("intMuiFrm", "INTMUIFRM");
		columnMap.put("intNoShow", "INTNOSHOW");
		columnMap.put("typeBaseTable", "TYPE_BASETABLE");
		columnMap.put("typeIndex", "TYPE_INDEX");
		columnMap.put("gid", "GID");
		columnMap.put("fileName", "FILE_NAME");
		columnMap.put("linkFileContent", "LINK_FILE_CONTENT");
		columnMap.put("linkFileName", "LINK_FILE_NAME");

		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("id", "String");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("indexName", "String");
		javaTypeMap.put("Level", "Integer");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("nodeIco", "Integer");
		javaTypeMap.put("sindexName", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("viewType", "Integer");
		javaTypeMap.put("isMode", "String");
		javaTypeMap.put("modeTableId", "String");
		javaTypeMap.put("isSystem", "Integer");
		javaTypeMap.put("customData", "String");
		javaTypeMap.put("intSystemSelect", "Integer");
		javaTypeMap.put("intUsed", "Integer");
		javaTypeMap.put("intDel", "Integer");
		javaTypeMap.put("typeTableName", "String");
		javaTypeMap.put("intOperation", "Integer");
		javaTypeMap.put("picFile", "String");
		javaTypeMap.put("fileContent", "String");
		javaTypeMap.put("intMuiFrm", "Integer");
		javaTypeMap.put("intNoShow", "Integer");
		javaTypeMap.put("typeBaseTable", "String");
		javaTypeMap.put("typeIndex", "Integer");
		javaTypeMap.put("gid", "String");
		javaTypeMap.put("fileName", "String");
		javaTypeMap.put("linkFileContent", "String");
		javaTypeMap.put("linkFileName", "String");
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
		tableDefinition.setName("CellTreeDot");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("indexId");
		idColumn.setColumnName("INDEX_ID");
		idColumn.setJavaType("Integer");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition id = new ColumnDefinition();
		id.setName("id");
		id.setColumnName("ID");
		id.setJavaType("String");
		id.setLength(100);
		tableDefinition.addColumn(id);

		ColumnDefinition parentId = new ColumnDefinition();
		parentId.setName("parentId");
		parentId.setColumnName("PARENT_ID");
		parentId.setJavaType("Integer");
		tableDefinition.addColumn(parentId);

		ColumnDefinition indexName = new ColumnDefinition();
		indexName.setName("indexName");
		indexName.setColumnName("INDEX_NAME");
		indexName.setJavaType("String");
		indexName.setLength(255);
		tableDefinition.addColumn(indexName);

		ColumnDefinition Level = new ColumnDefinition();
		Level.setName("Level");
		Level.setColumnName("NLEVEL");
		Level.setJavaType("Integer");
		tableDefinition.addColumn(Level);

		ColumnDefinition num = new ColumnDefinition();
		num.setName("num");
		num.setColumnName("NUM");
		num.setJavaType("String");
		num.setLength(50);
		tableDefinition.addColumn(num);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(255);
		tableDefinition.addColumn(content);

		ColumnDefinition nodeIco = new ColumnDefinition();
		nodeIco.setName("nodeIco");
		nodeIco.setColumnName("NODEICO");
		nodeIco.setJavaType("Integer");
		tableDefinition.addColumn(nodeIco);

		ColumnDefinition sindexName = new ColumnDefinition();
		sindexName.setName("sindexName");
		sindexName.setColumnName("SINDEX_NAME");
		sindexName.setJavaType("String");
		sindexName.setLength(255);
		tableDefinition.addColumn(sindexName);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition viewType = new ColumnDefinition();
		viewType.setName("viewType");
		viewType.setColumnName("VIEWTYPE");
		viewType.setJavaType("Integer");
		tableDefinition.addColumn(viewType);

		ColumnDefinition isMode = new ColumnDefinition();
		isMode.setName("isMode");
		isMode.setColumnName("ISMODE");
		isMode.setJavaType("String");
		isMode.setLength(1);
		tableDefinition.addColumn(isMode);

		ColumnDefinition modeTableId = new ColumnDefinition();
		modeTableId.setName("modeTableId");
		modeTableId.setColumnName("MODETABLE_ID");
		modeTableId.setJavaType("String");
		modeTableId.setLength(50);
		tableDefinition.addColumn(modeTableId);

		ColumnDefinition isSystem = new ColumnDefinition();
		isSystem.setName("isSystem");
		isSystem.setColumnName("ISSYSTEM");
		isSystem.setJavaType("Integer");
		tableDefinition.addColumn(isSystem);

		ColumnDefinition customData = new ColumnDefinition();
		customData.setName("customData");
		customData.setColumnName("CUSTOMDATA");
		customData.setJavaType("String");
		customData.setLength(50);
		tableDefinition.addColumn(customData);

		ColumnDefinition intSystemSelect = new ColumnDefinition();
		intSystemSelect.setName("intSystemSelect");
		intSystemSelect.setColumnName("INTSYSTEMSELECT");
		intSystemSelect.setJavaType("Integer");
		tableDefinition.addColumn(intSystemSelect);

		ColumnDefinition intUsed = new ColumnDefinition();
		intUsed.setName("intUsed");
		intUsed.setColumnName("INTUSED");
		intUsed.setJavaType("Integer");
		tableDefinition.addColumn(intUsed);

		ColumnDefinition intDel = new ColumnDefinition();
		intDel.setName("intDel");
		intDel.setColumnName("INTDEL");
		intDel.setJavaType("Integer");
		tableDefinition.addColumn(intDel);

		ColumnDefinition typeTableName = new ColumnDefinition();
		typeTableName.setName("typeTableName");
		typeTableName.setColumnName("TYPE_TABLENAME");
		typeTableName.setJavaType("String");
		typeTableName.setLength(50);
		tableDefinition.addColumn(typeTableName);

		ColumnDefinition intOperation = new ColumnDefinition();
		intOperation.setName("intOperation");
		intOperation.setColumnName("INTOPERATION");
		intOperation.setJavaType("Integer");
		tableDefinition.addColumn(intOperation);

		ColumnDefinition picFile = new ColumnDefinition();
		picFile.setName("picFile");
		picFile.setColumnName("PICFILE");
		picFile.setJavaType("String");
		picFile.setLength(250);
		tableDefinition.addColumn(picFile);

		ColumnDefinition fileContent = new ColumnDefinition();
		fileContent.setName("fileContent");
		fileContent.setColumnName("FILE_CONTENT");
		fileContent.setJavaType("String");
		fileContent.setLength(0);
		tableDefinition.addColumn(fileContent);

		ColumnDefinition intMuiFrm = new ColumnDefinition();
		intMuiFrm.setName("intMuiFrm");
		intMuiFrm.setColumnName("INTMUIFRM");
		intMuiFrm.setJavaType("Integer");
		tableDefinition.addColumn(intMuiFrm);

		ColumnDefinition intNoShow = new ColumnDefinition();
		intNoShow.setName("intNoShow");
		intNoShow.setColumnName("INTNOSHOW");
		intNoShow.setJavaType("Integer");
		tableDefinition.addColumn(intNoShow);

		ColumnDefinition typeBaseTable = new ColumnDefinition();
		typeBaseTable.setName("typeBaseTable");
		typeBaseTable.setColumnName("TYPE_BASETABLE");
		typeBaseTable.setJavaType("String");
		typeBaseTable.setLength(50);
		tableDefinition.addColumn(typeBaseTable);

		ColumnDefinition typeIndex = new ColumnDefinition();
		typeIndex.setName("typeIndex");
		typeIndex.setColumnName("TYPE_INDEX");
		typeIndex.setJavaType("Integer");
		tableDefinition.addColumn(typeIndex);

		ColumnDefinition gid = new ColumnDefinition();
		gid.setName("gid");
		gid.setColumnName("GID");
		gid.setJavaType("String");
		gid.setLength(50);
		tableDefinition.addColumn(gid);

		ColumnDefinition fileName = new ColumnDefinition();
		fileName.setName("fileName");
		fileName.setColumnName("FILE_NAME");
		fileName.setJavaType("String");
		fileName.setLength(255);
		tableDefinition.addColumn(fileName);

		ColumnDefinition linkFileContent = new ColumnDefinition();
		linkFileContent.setName("linkFileContent");
		linkFileContent.setColumnName("LINK_FILE_CONTENT");
		linkFileContent.setJavaType("String");
		linkFileContent.setLength(0);
		tableDefinition.addColumn(linkFileContent);

		ColumnDefinition linkFileName = new ColumnDefinition();
		linkFileName.setName("linkFileName");
		linkFileName.setColumnName("LINK_FILE_NAME");
		linkFileName.setJavaType("String");
		linkFileName.setLength(255);
		tableDefinition.addColumn(linkFileName);

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

	private CellTreeDotDomainFactory() {

	}

}
