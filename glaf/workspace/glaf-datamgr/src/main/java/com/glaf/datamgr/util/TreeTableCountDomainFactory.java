package com.glaf.datamgr.util;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class TreeTableCountDomainFactory {

	public static final String TABLENAME = "TREE_TABLE_COUNT";

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

	public static TableDefinition getTableDefinition() {
		return getTableDefinition(TABLENAME);
	}

	public static TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setName("TreeTableCount");

		ColumnDefinition syntheticId = new ColumnDefinition();
		syntheticId.setName("syntheticId");
		syntheticId.setColumnName("SYNTHETICID_");
		syntheticId.setJavaType("Long");
		tableDefinition.addColumn(syntheticId);

		ColumnDefinition databaseId = new ColumnDefinition();
		databaseId.setName("databaseId");
		databaseId.setColumnName("DATABASEID_");
		databaseId.setJavaType("Long");
		tableDefinition.addColumn(databaseId);

		ColumnDefinition discriminator = new ColumnDefinition();
		discriminator.setName("discriminator");
		discriminator.setColumnName("DISCRIMINATOR_");
		discriminator.setJavaType("String");
		discriminator.setLength(10);
		tableDefinition.addColumn(discriminator);

		ColumnDefinition mapping = new ColumnDefinition();
		mapping.setName("mapping");
		mapping.setColumnName("MAPPING_");
		mapping.setJavaType("String");
		mapping.setLength(50);
		tableDefinition.addColumn(mapping);

		ColumnDefinition section = new ColumnDefinition();
		section.setName("section");
		section.setColumnName("SECTION_");
		section.setJavaType("String");
		section.setLength(50);
		tableDefinition.addColumn(section);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition primaryKey = new ColumnDefinition();
		primaryKey.setName("primaryKey");
		primaryKey.setColumnName("PRIMARYKEY_");
		primaryKey.setJavaType("String");
		primaryKey.setLength(200);
		tableDefinition.addColumn(primaryKey);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEXID_");
		indexId.setJavaType("Long");
		tableDefinition.addColumn(indexId);

		ColumnDefinition origIndexId = new ColumnDefinition();
		origIndexId.setName("origIndexId");
		origIndexId.setColumnName("ORIG_INDEX_ID");
		origIndexId.setJavaType("Long");
		tableDefinition.addColumn(origIndexId);

		ColumnDefinition parentId = new ColumnDefinition();
		parentId.setName("parentId");
		parentId.setColumnName("PARENTID_");
		parentId.setJavaType("Long");
		tableDefinition.addColumn(parentId);

		ColumnDefinition origParentId = new ColumnDefinition();
		origParentId.setName("origParentId");
		origParentId.setColumnName("ORIG_PARENT_ID");
		origParentId.setJavaType("Long");
		tableDefinition.addColumn(origParentId);

		ColumnDefinition indexName = new ColumnDefinition();
		indexName.setName("indexName");
		indexName.setColumnName("INDEXNAME_");
		indexName.setJavaType("String");
		indexName.setLength(250);
		tableDefinition.addColumn(indexName);

		ColumnDefinition treeId = new ColumnDefinition();
		treeId.setName("treeId");
		treeId.setColumnName("TREEID_");
		treeId.setJavaType("String");
		treeId.setLength(500);
		tableDefinition.addColumn(treeId);

		ColumnDefinition origTreeId = new ColumnDefinition();
		origTreeId.setName("origTreeId");
		origTreeId.setColumnName("ORIG_TREE_ID");
		origTreeId.setJavaType("String");
		origTreeId.setLength(500);
		tableDefinition.addColumn(origTreeId);

		ColumnDefinition wbsIndex = new ColumnDefinition();
		wbsIndex.setName("wbsIndex");
		wbsIndex.setColumnName("WBSINDEX_");
		wbsIndex.setJavaType("Long");
		tableDefinition.addColumn(wbsIndex);

		ColumnDefinition runYear = new ColumnDefinition();
		runYear.setName("runYear");
		runYear.setColumnName("RUNYEAR_");
		runYear.setJavaType("Integer");
		tableDefinition.addColumn(runYear);

		ColumnDefinition runMonth = new ColumnDefinition();
		runMonth.setName("runMonth");
		runMonth.setColumnName("RUNMONTH_");
		runMonth.setJavaType("Integer");
		tableDefinition.addColumn(runMonth);

		ColumnDefinition runWeek = new ColumnDefinition();
		runWeek.setName("runWeek");
		runWeek.setColumnName("RUNWEEK_");
		runWeek.setJavaType("Integer");
		tableDefinition.addColumn(runWeek);

		ColumnDefinition runQuarter = new ColumnDefinition();
		runQuarter.setName("runQuarter");
		runQuarter.setColumnName("RUNQUARTER_");
		runQuarter.setJavaType("Integer");
		tableDefinition.addColumn(runQuarter);

		ColumnDefinition runDay = new ColumnDefinition();
		runDay.setName("runDay");
		runDay.setColumnName("RUNDAY_");
		runDay.setJavaType("Integer");
		tableDefinition.addColumn(runDay);

		ColumnDefinition jobNo = new ColumnDefinition();
		jobNo.setName("jobNo");
		jobNo.setColumnName("JOBNO_");
		jobNo.setJavaType("String");
		jobNo.setLength(50);
		tableDefinition.addColumn(jobNo);

		return tableDefinition;
	}

	private TreeTableCountDomainFactory() {

	}

}
