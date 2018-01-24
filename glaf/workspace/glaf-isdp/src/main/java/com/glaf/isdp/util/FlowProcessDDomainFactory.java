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
public class FlowProcessDDomainFactory {

	public static final String TABLENAME = "FLOW_PROCESS_D";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("mainId", "MAIN_ID");
		columnMap.put("fileId", "FILEID");
		columnMap.put("name", "NAME");
		columnMap.put("content", "CONTENT");
		columnMap.put("actor", "ACTOR");
		columnMap.put("ctime", "CTIME");
		columnMap.put("version", "VERSION");
		columnMap.put("tcadFile", "TCADFILE");
		columnMap.put("isSave", "ISSAVE");
		columnMap.put("intFlag", "INTFLAG");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("mainId", "String");
		javaTypeMap.put("fileId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("actor", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("version", "String");
		javaTypeMap.put("tcadFile", "String");
		javaTypeMap.put("isSave", "Integer");
		javaTypeMap.put("intFlag", "Integer");
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
		tableDefinition.setName("FlowProcessD");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition mainId = new ColumnDefinition();
		mainId.setName("mainId");
		mainId.setColumnName("MAIN_ID");
		mainId.setJavaType("String");
		mainId.setLength(50);
		tableDefinition.addColumn(mainId);

		ColumnDefinition fileId = new ColumnDefinition();
		fileId.setName("fileId");
		fileId.setColumnName("FILEID");
		fileId.setJavaType("String");
		fileId.setLength(50);
		tableDefinition.addColumn(fileId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME");
		name.setJavaType("String");
		name.setLength(200);
		tableDefinition.addColumn(name);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(100);
		tableDefinition.addColumn(content);

		ColumnDefinition actor = new ColumnDefinition();
		actor.setName("actor");
		actor.setColumnName("ACTOR");
		actor.setJavaType("String");
		actor.setLength(50);
		tableDefinition.addColumn(actor);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition version = new ColumnDefinition();
		version.setName("version");
		version.setColumnName("VERSION");
		version.setJavaType("String");
		version.setLength(50);
		tableDefinition.addColumn(version);

		ColumnDefinition tcadFile = new ColumnDefinition();
		tcadFile.setName("tcadFile");
		tcadFile.setColumnName("TCADFILE");
		tcadFile.setJavaType("String");
		tcadFile.setLength(0);
		tableDefinition.addColumn(tcadFile);

		ColumnDefinition isSave = new ColumnDefinition();
		isSave.setName("isSave");
		isSave.setColumnName("ISSAVE");
		isSave.setJavaType("Integer");
		tableDefinition.addColumn(isSave);

		ColumnDefinition intFlag = new ColumnDefinition();
		intFlag.setName("intFlag");
		intFlag.setColumnName("INTFLAG");
		intFlag.setJavaType("Integer");
		tableDefinition.addColumn(intFlag);

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

	private FlowProcessDDomainFactory() {

	}

}
