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
public class TreeProjDomainFactory {

	public static final String TABLENAME = "TREEPROJ";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("projType", "PROJTYPE");
		columnMap.put("id", "ID");
		columnMap.put("topId", "TOPID");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("indexName", "INDEX_NAME");
		columnMap.put("Level", "NLEVEL");
		columnMap.put("num", "NUM");
		columnMap.put("content", "CONTENT");
		columnMap.put("useId", "USEID");
		columnMap.put("sindexName", "SINDEX_NAME");
		columnMap.put("content2", "CONTENT2");
		columnMap.put("topNode", "TOPNODE");
		columnMap.put("nodeIco", "NODEICO");
		columnMap.put("unitNum", "UNITNUM");
		columnMap.put("showId", "SHOWID");
		columnMap.put("scaleQ", "SCALE_Q");
		columnMap.put("isPegWork", "ISPEGWORK");
		columnMap.put("treeProjUser2", "TREEPROJ_USER2");

		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("projType", "String");
		javaTypeMap.put("id", "String");
		javaTypeMap.put("topId", "Integer");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("indexName", "String");
		javaTypeMap.put("Level", "Integer");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("useId", "String");
		javaTypeMap.put("sindexName", "String");
		javaTypeMap.put("content2", "String");
		javaTypeMap.put("topNode", "String");
		javaTypeMap.put("nodeIco", "Integer");
		javaTypeMap.put("unitNum", "String");
		javaTypeMap.put("showId", "Integer");
		javaTypeMap.put("scaleQ", "Double");
		javaTypeMap.put("isPegWork", "String");
		javaTypeMap.put("treeProjUser2", "String");
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
		tableDefinition.setName("TreeProj");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("indexId");
		idColumn.setColumnName("INDEX_ID");
		idColumn.setJavaType("Integer");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition projType = new ColumnDefinition();
		projType.setName("projType");
		projType.setColumnName("PROJTYPE");
		projType.setJavaType("String");
		projType.setLength(1);
		tableDefinition.addColumn(projType);

		ColumnDefinition id = new ColumnDefinition();
		id.setName("id");
		id.setColumnName("ID");
		id.setJavaType("String");
		id.setLength(100);
		tableDefinition.addColumn(id);

		ColumnDefinition topId = new ColumnDefinition();
		topId.setName("topId");
		topId.setColumnName("TOPID");
		topId.setJavaType("Integer");
		tableDefinition.addColumn(topId);

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
		num.setLength(255);
		tableDefinition.addColumn(num);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(255);
		tableDefinition.addColumn(content);

		ColumnDefinition useId = new ColumnDefinition();
		useId.setName("useId");
		useId.setColumnName("USEID");
		useId.setJavaType("String");
		useId.setLength(50);
		tableDefinition.addColumn(useId);

		ColumnDefinition sindexName = new ColumnDefinition();
		sindexName.setName("sindexName");
		sindexName.setColumnName("SINDEX_NAME");
		sindexName.setJavaType("String");
		sindexName.setLength(255);
		tableDefinition.addColumn(sindexName);

		ColumnDefinition content2 = new ColumnDefinition();
		content2.setName("content2");
		content2.setColumnName("CONTENT2");
		content2.setJavaType("String");
		content2.setLength(255);
		tableDefinition.addColumn(content2);

		ColumnDefinition topNode = new ColumnDefinition();
		topNode.setName("topNode");
		topNode.setColumnName("TOPNODE");
		topNode.setJavaType("String");
		topNode.setLength(255);
		tableDefinition.addColumn(topNode);

		ColumnDefinition nodeIco = new ColumnDefinition();
		nodeIco.setName("nodeIco");
		nodeIco.setColumnName("NODEICO");
		nodeIco.setJavaType("Integer");
		tableDefinition.addColumn(nodeIco);

		ColumnDefinition unitNum = new ColumnDefinition();
		unitNum.setName("unitNum");
		unitNum.setColumnName("UNITNUM");
		unitNum.setJavaType("String");
		unitNum.setLength(50);
		tableDefinition.addColumn(unitNum);

		ColumnDefinition showId = new ColumnDefinition();
		showId.setName("showId");
		showId.setColumnName("SHOWID");
		showId.setJavaType("Integer");
		tableDefinition.addColumn(showId);

		ColumnDefinition scaleQ = new ColumnDefinition();
		scaleQ.setName("scaleQ");
		scaleQ.setColumnName("SCALE_Q");
		scaleQ.setJavaType("Double");
		tableDefinition.addColumn(scaleQ);

		ColumnDefinition isPegWork = new ColumnDefinition();
		isPegWork.setName("isPegWork");
		isPegWork.setColumnName("ISPEGWORK");
		isPegWork.setJavaType("String");
		isPegWork.setLength(1);
		tableDefinition.addColumn(isPegWork);

		ColumnDefinition treeProjUser2 = new ColumnDefinition();
		treeProjUser2.setName("treeProjUser2");
		treeProjUser2.setColumnName("TREEPROJ_USER2");
		treeProjUser2.setJavaType("String");
		treeProjUser2.setLength(1000);
		tableDefinition.addColumn(treeProjUser2);

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

	private TreeProjDomainFactory() {

	}

}
