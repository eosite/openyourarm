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
public class TreeDotDomainFactory {

	public static final String TABLENAME = "TREEDOT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("id", "ID");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("indexIame", "INDEX_NAME");
		columnMap.put("Level", "NLEVEL");
		columnMap.put("num", "NUM");
		columnMap.put("content", "CONTENT");
		columnMap.put("sindexName", "SINDEX_NAME");
		columnMap.put("nodeIco", "NODEICO");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("menuId", "MENUID");
		columnMap.put("isEnd", "ISEND");
		columnMap.put("sysMenuId", "SYSMENUID");
		columnMap.put("type", "TYPE");
		columnMap.put("fileNumId", "FILENUMID");
		columnMap.put("fileNumId2", "FILENUMID2");
		columnMap.put("projIndex", "PROJ_INDEX");
		columnMap.put("domainIsndex", "DOMAIN_INDEX");

		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("id", "String");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("indexIame", "String");
		javaTypeMap.put("Level", "Integer");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("sindexName", "String");
		javaTypeMap.put("nodeIco", "Integer");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("menuId", "Integer");
		javaTypeMap.put("isEnd", "Integer");
		javaTypeMap.put("sysMenuId", "String");
		javaTypeMap.put("type", "Integer");
		javaTypeMap.put("fileNumId", "String");
		javaTypeMap.put("fileNumId2", "String");
		javaTypeMap.put("projIndex", "Integer");
		javaTypeMap.put("domainIsndex", "Integer");
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
		tableDefinition.setName("TreeDot");

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

		ColumnDefinition indexIame = new ColumnDefinition();
		indexIame.setName("indexIame");
		indexIame.setColumnName("INDEX_NAME");
		indexIame.setJavaType("String");
		indexIame.setLength(255);
		tableDefinition.addColumn(indexIame);

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

		ColumnDefinition sindexName = new ColumnDefinition();
		sindexName.setName("sindexName");
		sindexName.setColumnName("SINDEX_NAME");
		sindexName.setJavaType("String");
		sindexName.setLength(255);
		tableDefinition.addColumn(sindexName);

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

		ColumnDefinition menuId = new ColumnDefinition();
		menuId.setName("menuId");
		menuId.setColumnName("MENUID");
		menuId.setJavaType("Integer");
		tableDefinition.addColumn(menuId);

		ColumnDefinition isEnd = new ColumnDefinition();
		isEnd.setName("isEnd");
		isEnd.setColumnName("ISEND");
		isEnd.setJavaType("Integer");
		tableDefinition.addColumn(isEnd);

		ColumnDefinition sysMenuId = new ColumnDefinition();
		sysMenuId.setName("sysMenuId");
		sysMenuId.setColumnName("SYSMENUID");
		sysMenuId.setJavaType("String");
		sysMenuId.setLength(500);
		tableDefinition.addColumn(sysMenuId);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE");
		type.setJavaType("Integer");
		tableDefinition.addColumn(type);

		ColumnDefinition fileNumId = new ColumnDefinition();
		fileNumId.setName("fileNumId");
		fileNumId.setColumnName("FILENUMID");
		fileNumId.setJavaType("String");
		fileNumId.setLength(50);
		tableDefinition.addColumn(fileNumId);

		ColumnDefinition fileNumId2 = new ColumnDefinition();
		fileNumId2.setName("fileNumId2");
		fileNumId2.setColumnName("FILENUMID2");
		fileNumId2.setJavaType("String");
		fileNumId2.setLength(50);
		tableDefinition.addColumn(fileNumId2);

		ColumnDefinition projIndex = new ColumnDefinition();
		projIndex.setName("projIndex");
		projIndex.setColumnName("PROJ_INDEX");
		projIndex.setJavaType("Integer");
		tableDefinition.addColumn(projIndex);

		ColumnDefinition domainIsndex = new ColumnDefinition();
		domainIsndex.setName("domainIsndex");
		domainIsndex.setColumnName("DOMAIN_INDEX");
		domainIsndex.setJavaType("Integer");
		tableDefinition.addColumn(domainIsndex);

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

	private TreeDotDomainFactory() {

	}

}
