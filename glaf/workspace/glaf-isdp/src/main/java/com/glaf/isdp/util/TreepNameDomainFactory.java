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
public class TreepNameDomainFactory {

	public static final String TABLENAME = "TREEPNAME";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("id", "ID");
		columnMap.put("num", "NUM");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("indexName", "INDEX_NAME");
		columnMap.put("Level", "NLEVEL");
		columnMap.put("showId", "SHOWID");
		columnMap.put("ruleId", "RULEID");
		columnMap.put("nodeIco", "NODEICO");
		columnMap.put("fruleId", "FRULEID");
		columnMap.put("wcompany", "WCOMPANY");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("sysId", "SYS_ID");
		columnMap.put("domainIndex", "DOMAIN_INDEX");

		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("id", "String");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("indexName", "String");
		javaTypeMap.put("Level", "Integer");
		javaTypeMap.put("showId", "Integer");
		javaTypeMap.put("ruleId", "String");
		javaTypeMap.put("nodeIco", "Integer");
		javaTypeMap.put("fruleId", "String");
		javaTypeMap.put("wcompany", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("sysId", "String");
		javaTypeMap.put("domainIndex", "Integer");
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
		tableDefinition.setName("TreepName");

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

		ColumnDefinition num = new ColumnDefinition();
		num.setName("num");
		num.setColumnName("NUM");
		num.setJavaType("String");
		num.setLength(50);
		tableDefinition.addColumn(num);

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

		ColumnDefinition showId = new ColumnDefinition();
		showId.setName("showId");
		showId.setColumnName("SHOWID");
		showId.setJavaType("Integer");
		tableDefinition.addColumn(showId);

		ColumnDefinition ruleId = new ColumnDefinition();
		ruleId.setName("ruleId");
		ruleId.setColumnName("RULEID");
		ruleId.setJavaType("String");
		ruleId.setLength(50);
		tableDefinition.addColumn(ruleId);

		ColumnDefinition nodeIco = new ColumnDefinition();
		nodeIco.setName("nodeIco");
		nodeIco.setColumnName("NODEICO");
		nodeIco.setJavaType("Integer");
		tableDefinition.addColumn(nodeIco);

		ColumnDefinition fruleId = new ColumnDefinition();
		fruleId.setName("fruleId");
		fruleId.setColumnName("FRULEID");
		fruleId.setJavaType("String");
		fruleId.setLength(50);
		tableDefinition.addColumn(fruleId);

		ColumnDefinition wcompany = new ColumnDefinition();
		wcompany.setName("wcompany");
		wcompany.setColumnName("WCOMPANY");
		wcompany.setJavaType("String");
		wcompany.setLength(250);
		tableDefinition.addColumn(wcompany);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition sysId = new ColumnDefinition();
		sysId.setName("sysId");
		sysId.setColumnName("SYS_ID");
		sysId.setJavaType("String");
		sysId.setLength(50);
		tableDefinition.addColumn(sysId);

		ColumnDefinition domainIndex = new ColumnDefinition();
		domainIndex.setName("domainIndex");
		domainIndex.setColumnName("DOMAIN_INDEX");
		domainIndex.setJavaType("Integer");
		tableDefinition.addColumn(domainIndex);

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

	private TreepNameDomainFactory() {

	}

}
