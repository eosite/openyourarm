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
public class CellUTableTreeDomainFactory {

	public static final String TABLENAME = "CELL_UTABLETREE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("id", "ID");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("indexName", "INDEX_NAME");
		columnMap.put("Level", "NLEVEL");
		columnMap.put("nodeIco", "NODEICO");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("tableType", "TABLETYPE");
		columnMap.put("intDel", "INTDEL");
		columnMap.put("busiessId", "BUSIESS_ID");
		columnMap.put("content", "CONTENT");
		columnMap.put("num", "NUM");
		columnMap.put("menuIndex", "MENUINDEX");
		columnMap.put("domainIndex", "DOMAIN_INDEX");
		columnMap.put("winWidth", "WIN_WIDTH");
		columnMap.put("winHeight", "WIN_HEIGHT");

		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("id", "String");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("indexName", "String");
		javaTypeMap.put("Level", "Integer");
		javaTypeMap.put("nodeIco", "Integer");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("tableType", "Integer");
		javaTypeMap.put("intDel", "Integer");
		javaTypeMap.put("busiessId", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("menuIndex", "Integer");
		javaTypeMap.put("domainIndex", "Integer");
		javaTypeMap.put("winWidth", "Integer");
		javaTypeMap.put("winHeight", "Integer");
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
		tableDefinition.setName("CellUTableTree");

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

		ColumnDefinition nodeIco = new ColumnDefinition();
		nodeIco.setName("nodeIco");
		nodeIco.setColumnName("NODEICO");
		nodeIco.setJavaType("Integer");
		tableDefinition.addColumn(nodeIco);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition tableType = new ColumnDefinition();
		tableType.setName("tableType");
		tableType.setColumnName("TABLETYPE");
		tableType.setJavaType("Integer");
		tableDefinition.addColumn(tableType);

		ColumnDefinition intDel = new ColumnDefinition();
		intDel.setName("intDel");
		intDel.setColumnName("INTDEL");
		intDel.setJavaType("Integer");
		tableDefinition.addColumn(intDel);

		ColumnDefinition busiessId = new ColumnDefinition();
		busiessId.setName("busiessId");
		busiessId.setColumnName("BUSIESS_ID");
		busiessId.setJavaType("String");
		busiessId.setLength(50);
		tableDefinition.addColumn(busiessId);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(250);
		tableDefinition.addColumn(content);

		ColumnDefinition num = new ColumnDefinition();
		num.setName("num");
		num.setColumnName("NUM");
		num.setJavaType("String");
		num.setLength(50);
		tableDefinition.addColumn(num);

		ColumnDefinition menuIndex = new ColumnDefinition();
		menuIndex.setName("menuIndex");
		menuIndex.setColumnName("MENUINDEX");
		menuIndex.setJavaType("Integer");
		tableDefinition.addColumn(menuIndex);

		ColumnDefinition domainIndex = new ColumnDefinition();
		domainIndex.setName("domainIndex");
		domainIndex.setColumnName("DOMAIN_INDEX");
		domainIndex.setJavaType("Integer");
		tableDefinition.addColumn(domainIndex);

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

	private CellUTableTreeDomainFactory() {

	}

}
