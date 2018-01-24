package com.glaf.isdp.util;

import java.util.*;
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
public class TreepInfoCountDomainFactory {

	public static final String TABLENAME = "TREEPINFO_COUNT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("databaseId", "DATABASEID_");
		columnMap.put("mapping", "MAPPING_");
		columnMap.put("title", "TITLE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("section", "SECTION_");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("indexName", "INDEX_NAME");
		columnMap.put("wbsIndex", "WBSINDEX");
		columnMap.put("doubleValue1", "DOUBLEVALUE1_");
		columnMap.put("doubleValue2", "DOUBLEVALUE2_");
		columnMap.put("doubleValue3", "DOUBLEVALUE3_");
		columnMap.put("doubleValue4", "DOUBLEVALUE4_");
		columnMap.put("doubleValue5", "DOUBLEVALUE5_");
		columnMap.put("doubleValue6", "DOUBLEVALUE6_");
		columnMap.put("doubleValue7", "DOUBLEVALUE7_");
		columnMap.put("doubleValue8", "DOUBLEVALUE8_");
		columnMap.put("doubleValue9", "DOUBLEVALUE9_");
		columnMap.put("doubleValue10", "DOUBLEVALUE10_");
		columnMap.put("intValue1", "INTVALUE1_");
		columnMap.put("intValue2", "INTVALUE2_");
		columnMap.put("intValue3", "INTVALUE3_");
		columnMap.put("intValue4", "INTVALUE4_");
		columnMap.put("intValue5", "INTVALUE5_");
		columnMap.put("intValue6", "INTVALUE6_");
		columnMap.put("intValue7", "INTVALUE7_");
		columnMap.put("intValue8", "INTVALUE8_");
		columnMap.put("intValue9", "INTVALUE9_");
		columnMap.put("intValue10", "INTVALUE10_");
		columnMap.put("longValue1", "LONGVALUE1_");
		columnMap.put("longValue2", "LONGVALUE2_");
		columnMap.put("longValue3", "LONGVALUE3_");
		columnMap.put("longValue4", "LONGVALUE4_");
		columnMap.put("longValue5", "LONGVALUE5_");
		columnMap.put("longValue6", "LONGVALUE6_");
		columnMap.put("longValue7", "LONGVALUE7_");
		columnMap.put("longValue8", "LONGVALUE8_");
		columnMap.put("longValue9", "LONGVALUE9_");
		columnMap.put("longValue10", "LONGVALUE10_");
		columnMap.put("stringValue1", "STRINGVALUE1_");
		columnMap.put("stringValue2", "STRINGVALUE2_");
		columnMap.put("stringValue3", "STRINGVALUE3_");
		columnMap.put("stringValue4", "STRINGVALUE4_");
		columnMap.put("stringValue5", "STRINGVALUE5_");
		columnMap.put("stringValue6", "STRINGVALUE6_");
		columnMap.put("stringValue7", "STRINGVALUE7_");
		columnMap.put("stringValue8", "STRINGVALUE8_");
		columnMap.put("stringValue9", "STRINGVALUE9_");
		columnMap.put("stringValue10", "STRINGVALUE10_");
		columnMap.put("runYear", "RUNYEAR_");
		columnMap.put("runMonth", "RUNMONTH_");
		columnMap.put("runWeek", "RUNWEEK_");
		columnMap.put("runQuarter", "RUNQUARTER_");
		columnMap.put("runDay", "RUNDAY_");
		columnMap.put("jobNo", "JOBNO_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("databaseId", "Long");
		javaTypeMap.put("mapping", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("section", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("indexName", "String");
		javaTypeMap.put("wbsIndex", "Integer");
		javaTypeMap.put("doubleValue1", "Double");
		javaTypeMap.put("doubleValue2", "Double");
		javaTypeMap.put("doubleValue3", "Double");
		javaTypeMap.put("doubleValue4", "Double");
		javaTypeMap.put("doubleValue5", "Double");
		javaTypeMap.put("doubleValue6", "Double");
		javaTypeMap.put("doubleValue7", "Double");
		javaTypeMap.put("doubleValue8", "Double");
		javaTypeMap.put("doubleValue9", "Double");
		javaTypeMap.put("doubleValue10", "Double");
		javaTypeMap.put("intValue1", "Integer");
		javaTypeMap.put("intValue2", "Integer");
		javaTypeMap.put("intValue3", "Integer");
		javaTypeMap.put("intValue4", "Integer");
		javaTypeMap.put("intValue5", "Integer");
		javaTypeMap.put("intValue6", "Integer");
		javaTypeMap.put("intValue7", "Integer");
		javaTypeMap.put("intValue8", "Integer");
		javaTypeMap.put("intValue9", "Integer");
		javaTypeMap.put("intValue10", "Integer");
		javaTypeMap.put("longValue1", "Long");
		javaTypeMap.put("longValue2", "Long");
		javaTypeMap.put("longValue3", "Long");
		javaTypeMap.put("longValue4", "Long");
		javaTypeMap.put("longValue5", "Long");
		javaTypeMap.put("longValue6", "Long");
		javaTypeMap.put("longValue7", "Long");
		javaTypeMap.put("longValue8", "Long");
		javaTypeMap.put("longValue9", "Long");
		javaTypeMap.put("longValue10", "Long");
		javaTypeMap.put("stringValue1", "String");
		javaTypeMap.put("stringValue2", "String");
		javaTypeMap.put("stringValue3", "String");
		javaTypeMap.put("stringValue4", "String");
		javaTypeMap.put("stringValue5", "String");
		javaTypeMap.put("stringValue6", "String");
		javaTypeMap.put("stringValue7", "String");
		javaTypeMap.put("stringValue8", "String");
		javaTypeMap.put("stringValue9", "String");
		javaTypeMap.put("stringValue10", "String");
		javaTypeMap.put("runYear", "Integer");
		javaTypeMap.put("runMonth", "Integer");
		javaTypeMap.put("runWeek", "Integer");
		javaTypeMap.put("runQuarter", "Integer");
		javaTypeMap.put("runDay", "Integer");
		javaTypeMap.put("jobNo", "String");
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
		tableDefinition.setName("TreepInfoCount");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition databaseId = new ColumnDefinition();
		databaseId.setName("databaseId");
		databaseId.setColumnName("DATABASEID_");
		databaseId.setJavaType("Long");
		tableDefinition.addColumn(databaseId);

		ColumnDefinition mapping = new ColumnDefinition();
		mapping.setName("mapping");
		mapping.setColumnName("MAPPING_");
		mapping.setJavaType("String");
		mapping.setLength(50);
		tableDefinition.addColumn(mapping);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(100);
		tableDefinition.addColumn(title);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition section = new ColumnDefinition();
		section.setName("section");
		section.setColumnName("SECTION_");
		section.setJavaType("String");
		section.setLength(50);
		tableDefinition.addColumn(section);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEX_ID");
		indexId.setJavaType("Integer");
		tableDefinition.addColumn(indexId);

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

		ColumnDefinition wbsIndex = new ColumnDefinition();
		wbsIndex.setName("wbsIndex");
		wbsIndex.setColumnName("WBSINDEX");
		wbsIndex.setJavaType("Integer");
		tableDefinition.addColumn(wbsIndex);

		ColumnDefinition doubleValue1 = new ColumnDefinition();
		doubleValue1.setName("doubleValue1");
		doubleValue1.setColumnName("DOUBLEVALUE1_");
		doubleValue1.setJavaType("Double");
		tableDefinition.addColumn(doubleValue1);

		ColumnDefinition doubleValue2 = new ColumnDefinition();
		doubleValue2.setName("doubleValue2");
		doubleValue2.setColumnName("DOUBLEVALUE2_");
		doubleValue2.setJavaType("Double");
		tableDefinition.addColumn(doubleValue2);

		ColumnDefinition doubleValue3 = new ColumnDefinition();
		doubleValue3.setName("doubleValue3");
		doubleValue3.setColumnName("DOUBLEVALUE3_");
		doubleValue3.setJavaType("Double");
		tableDefinition.addColumn(doubleValue3);

		ColumnDefinition doubleValue4 = new ColumnDefinition();
		doubleValue4.setName("doubleValue4");
		doubleValue4.setColumnName("DOUBLEVALUE4_");
		doubleValue4.setJavaType("Double");
		tableDefinition.addColumn(doubleValue4);

		ColumnDefinition doubleValue5 = new ColumnDefinition();
		doubleValue5.setName("doubleValue5");
		doubleValue5.setColumnName("DOUBLEVALUE5_");
		doubleValue5.setJavaType("Double");
		tableDefinition.addColumn(doubleValue5);

		ColumnDefinition doubleValue6 = new ColumnDefinition();
		doubleValue6.setName("doubleValue6");
		doubleValue6.setColumnName("DOUBLEVALUE6_");
		doubleValue6.setJavaType("Double");
		tableDefinition.addColumn(doubleValue6);

		ColumnDefinition doubleValue7 = new ColumnDefinition();
		doubleValue7.setName("doubleValue7");
		doubleValue7.setColumnName("DOUBLEVALUE7_");
		doubleValue7.setJavaType("Double");
		tableDefinition.addColumn(doubleValue7);

		ColumnDefinition doubleValue8 = new ColumnDefinition();
		doubleValue8.setName("doubleValue8");
		doubleValue8.setColumnName("DOUBLEVALUE8_");
		doubleValue8.setJavaType("Double");
		tableDefinition.addColumn(doubleValue8);

		ColumnDefinition doubleValue9 = new ColumnDefinition();
		doubleValue9.setName("doubleValue9");
		doubleValue9.setColumnName("DOUBLEVALUE9_");
		doubleValue9.setJavaType("Double");
		tableDefinition.addColumn(doubleValue9);

		ColumnDefinition doubleValue10 = new ColumnDefinition();
		doubleValue10.setName("doubleValue10");
		doubleValue10.setColumnName("DOUBLEVALUE10_");
		doubleValue10.setJavaType("Double");
		tableDefinition.addColumn(doubleValue10);

		ColumnDefinition intValue1 = new ColumnDefinition();
		intValue1.setName("intValue1");
		intValue1.setColumnName("INTVALUE1_");
		intValue1.setJavaType("Integer");
		tableDefinition.addColumn(intValue1);

		ColumnDefinition intValue2 = new ColumnDefinition();
		intValue2.setName("intValue2");
		intValue2.setColumnName("INTVALUE2_");
		intValue2.setJavaType("Integer");
		tableDefinition.addColumn(intValue2);

		ColumnDefinition intValue3 = new ColumnDefinition();
		intValue3.setName("intValue3");
		intValue3.setColumnName("INTVALUE3_");
		intValue3.setJavaType("Integer");
		tableDefinition.addColumn(intValue3);

		ColumnDefinition intValue4 = new ColumnDefinition();
		intValue4.setName("intValue4");
		intValue4.setColumnName("INTVALUE4_");
		intValue4.setJavaType("Integer");
		tableDefinition.addColumn(intValue4);

		ColumnDefinition intValue5 = new ColumnDefinition();
		intValue5.setName("intValue5");
		intValue5.setColumnName("INTVALUE5_");
		intValue5.setJavaType("Integer");
		tableDefinition.addColumn(intValue5);

		ColumnDefinition intValue6 = new ColumnDefinition();
		intValue6.setName("intValue6");
		intValue6.setColumnName("INTVALUE6_");
		intValue6.setJavaType("Integer");
		tableDefinition.addColumn(intValue6);

		ColumnDefinition intValue7 = new ColumnDefinition();
		intValue7.setName("intValue7");
		intValue7.setColumnName("INTVALUE7_");
		intValue7.setJavaType("Integer");
		tableDefinition.addColumn(intValue7);

		ColumnDefinition intValue8 = new ColumnDefinition();
		intValue8.setName("intValue8");
		intValue8.setColumnName("INTVALUE8_");
		intValue8.setJavaType("Integer");
		tableDefinition.addColumn(intValue8);

		ColumnDefinition intValue9 = new ColumnDefinition();
		intValue9.setName("intValue9");
		intValue9.setColumnName("INTVALUE9_");
		intValue9.setJavaType("Integer");
		tableDefinition.addColumn(intValue9);

		ColumnDefinition intValue10 = new ColumnDefinition();
		intValue10.setName("intValue10");
		intValue10.setColumnName("INTVALUE10_");
		intValue10.setJavaType("Integer");
		tableDefinition.addColumn(intValue10);

		ColumnDefinition longValue1 = new ColumnDefinition();
		longValue1.setName("longValue1");
		longValue1.setColumnName("LONGVALUE1_");
		longValue1.setJavaType("Long");
		tableDefinition.addColumn(longValue1);

		ColumnDefinition longValue2 = new ColumnDefinition();
		longValue2.setName("longValue2");
		longValue2.setColumnName("LONGVALUE2_");
		longValue2.setJavaType("Long");
		tableDefinition.addColumn(longValue2);

		ColumnDefinition longValue3 = new ColumnDefinition();
		longValue3.setName("longValue3");
		longValue3.setColumnName("LONGVALUE3_");
		longValue3.setJavaType("Long");
		tableDefinition.addColumn(longValue3);

		ColumnDefinition longValue4 = new ColumnDefinition();
		longValue4.setName("longValue4");
		longValue4.setColumnName("LONGVALUE4_");
		longValue4.setJavaType("Long");
		tableDefinition.addColumn(longValue4);

		ColumnDefinition longValue5 = new ColumnDefinition();
		longValue5.setName("longValue5");
		longValue5.setColumnName("LONGVALUE5_");
		longValue5.setJavaType("Long");
		tableDefinition.addColumn(longValue5);

		ColumnDefinition longValue6 = new ColumnDefinition();
		longValue6.setName("longValue6");
		longValue6.setColumnName("LONGVALUE6_");
		longValue6.setJavaType("Long");
		tableDefinition.addColumn(longValue6);

		ColumnDefinition longValue7 = new ColumnDefinition();
		longValue7.setName("longValue7");
		longValue7.setColumnName("LONGVALUE7_");
		longValue7.setJavaType("Long");
		tableDefinition.addColumn(longValue7);

		ColumnDefinition longValue8 = new ColumnDefinition();
		longValue8.setName("longValue8");
		longValue8.setColumnName("LONGVALUE8_");
		longValue8.setJavaType("Long");
		tableDefinition.addColumn(longValue8);

		ColumnDefinition longValue9 = new ColumnDefinition();
		longValue9.setName("longValue9");
		longValue9.setColumnName("LONGVALUE9_");
		longValue9.setJavaType("Long");
		tableDefinition.addColumn(longValue9);

		ColumnDefinition longValue10 = new ColumnDefinition();
		longValue10.setName("longValue10");
		longValue10.setColumnName("LONGVALUE10_");
		longValue10.setJavaType("Long");
		tableDefinition.addColumn(longValue10);

		ColumnDefinition stringValue1 = new ColumnDefinition();
		stringValue1.setName("stringValue1");
		stringValue1.setColumnName("STRINGVALUE1_");
		stringValue1.setJavaType("String");
		stringValue1.setLength(255);
		tableDefinition.addColumn(stringValue1);

		ColumnDefinition stringValue2 = new ColumnDefinition();
		stringValue2.setName("stringValue2");
		stringValue2.setColumnName("STRINGVALUE2_");
		stringValue2.setJavaType("String");
		stringValue2.setLength(255);
		tableDefinition.addColumn(stringValue2);

		ColumnDefinition stringValue3 = new ColumnDefinition();
		stringValue3.setName("stringValue3");
		stringValue3.setColumnName("STRINGVALUE3_");
		stringValue3.setJavaType("String");
		stringValue3.setLength(255);
		tableDefinition.addColumn(stringValue3);

		ColumnDefinition stringValue4 = new ColumnDefinition();
		stringValue4.setName("stringValue4");
		stringValue4.setColumnName("STRINGVALUE4_");
		stringValue4.setJavaType("String");
		stringValue4.setLength(255);
		tableDefinition.addColumn(stringValue4);

		ColumnDefinition stringValue5 = new ColumnDefinition();
		stringValue5.setName("stringValue5");
		stringValue5.setColumnName("STRINGVALUE5_");
		stringValue5.setJavaType("String");
		stringValue5.setLength(255);
		tableDefinition.addColumn(stringValue5);

		ColumnDefinition stringValue6 = new ColumnDefinition();
		stringValue6.setName("stringValue6");
		stringValue6.setColumnName("STRINGVALUE6_");
		stringValue6.setJavaType("String");
		stringValue6.setLength(255);
		tableDefinition.addColumn(stringValue6);

		ColumnDefinition stringValue7 = new ColumnDefinition();
		stringValue7.setName("stringValue7");
		stringValue7.setColumnName("STRINGVALUE7_");
		stringValue7.setJavaType("String");
		stringValue7.setLength(255);
		tableDefinition.addColumn(stringValue7);

		ColumnDefinition stringValue8 = new ColumnDefinition();
		stringValue8.setName("stringValue8");
		stringValue8.setColumnName("STRINGVALUE8_");
		stringValue8.setJavaType("String");
		stringValue8.setLength(255);
		tableDefinition.addColumn(stringValue8);

		ColumnDefinition stringValue9 = new ColumnDefinition();
		stringValue9.setName("stringValue9");
		stringValue9.setColumnName("STRINGVALUE9_");
		stringValue9.setJavaType("String");
		stringValue9.setLength(255);
		tableDefinition.addColumn(stringValue9);

		ColumnDefinition stringValue10 = new ColumnDefinition();
		stringValue10.setName("stringValue10");
		stringValue10.setColumnName("STRINGVALUE10_");
		stringValue10.setJavaType("String");
		stringValue10.setLength(255);
		tableDefinition.addColumn(stringValue10);

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

	public static void processDataRequest(DataRequest dataRequest) {
		if (dataRequest != null) {
			if (dataRequest.getFilter() != null) {
				if (dataRequest.getFilter().getField() != null) {
					dataRequest.getFilter().setColumn(columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter().setJavaType(javaTypeMap.get(dataRequest.getFilter().getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter().getFilters();
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
	}

	private TreepInfoCountDomainFactory() {

	}

}
