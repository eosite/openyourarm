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
public class CellMyTaskMainDomainFactory {

	public static final String TABLENAME = "CELL_MYTASKMAIN";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("ctime", "CTIME");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("taskId", "TASK_ID");
		columnMap.put("name", "NAME");
		columnMap.put("projName", "PROJNAME");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("typeIndexId", "TYPE_INDEX_ID");
		columnMap.put("flagInt", "FLAGINT");
		columnMap.put("myCellTsksId", "MYCELL_TSKS_ID");
		columnMap.put("fromTasksId", "FROMTASKSID");
		columnMap.put("toTaskId", "TOTASKID");
		columnMap.put("intFinish", "INTFINISH");
		columnMap.put("fileContent", "FILE_CONTENT");
		columnMap.put("typeTableName", "TYPE_TABLENAME");
		columnMap.put("typeId", "TYPE_ID");
		columnMap.put("userId", "USERID");
		columnMap.put("netRoleId", "NETROLEID");
		columnMap.put("intIsFlow", "INTISFLOW");
		columnMap.put("intStop", "INTSTOP");
		columnMap.put("fileTypeIndex", "FILETYPE_INDEX");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("taskId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("projName", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("typeIndexId", "Integer");
		javaTypeMap.put("flagInt", "Integer");
		javaTypeMap.put("myCellTsksId", "String");
		javaTypeMap.put("fromTasksId", "String");
		javaTypeMap.put("toTaskId", "String");
		javaTypeMap.put("intFinish", "Integer");
		javaTypeMap.put("fileContent", "String");
		javaTypeMap.put("typeTableName", "String");
		javaTypeMap.put("typeId", "String");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("netRoleId", "String");
		javaTypeMap.put("intIsFlow", "Integer");
		javaTypeMap.put("intStop", "Integer");
		javaTypeMap.put("fileTypeIndex", "Integer");
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
		tableDefinition.setName("CellMyTaskMain");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEX_ID");
		indexId.setJavaType("Integer");
		tableDefinition.addColumn(indexId);

		ColumnDefinition taskId = new ColumnDefinition();
		taskId.setName("taskId");
		taskId.setColumnName("TASK_ID");
		taskId.setJavaType("String");
		taskId.setLength(50);
		tableDefinition.addColumn(taskId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME");
		name.setJavaType("String");
		name.setLength(250);
		tableDefinition.addColumn(name);

		ColumnDefinition projName = new ColumnDefinition();
		projName.setName("projName");
		projName.setColumnName("PROJNAME");
		projName.setJavaType("String");
		projName.setLength(250);
		tableDefinition.addColumn(projName);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition typeIndexId = new ColumnDefinition();
		typeIndexId.setName("typeIndexId");
		typeIndexId.setColumnName("TYPE_INDEX_ID");
		typeIndexId.setJavaType("Integer");
		tableDefinition.addColumn(typeIndexId);

		ColumnDefinition flagInt = new ColumnDefinition();
		flagInt.setName("flagInt");
		flagInt.setColumnName("FLAGINT");
		flagInt.setJavaType("Integer");
		tableDefinition.addColumn(flagInt);

		ColumnDefinition myCellTsksId = new ColumnDefinition();
		myCellTsksId.setName("myCellTsksId");
		myCellTsksId.setColumnName("MYCELL_TSKS_ID");
		myCellTsksId.setJavaType("String");
		myCellTsksId.setLength(50);
		tableDefinition.addColumn(myCellTsksId);

		ColumnDefinition fromTasksId = new ColumnDefinition();
		fromTasksId.setName("fromTasksId");
		fromTasksId.setColumnName("FROMTASKSID");
		fromTasksId.setJavaType("String");
		fromTasksId.setLength(50);
		tableDefinition.addColumn(fromTasksId);

		ColumnDefinition toTaskId = new ColumnDefinition();
		toTaskId.setName("toTaskId");
		toTaskId.setColumnName("TOTASKID");
		toTaskId.setJavaType("String");
		toTaskId.setLength(50);
		tableDefinition.addColumn(toTaskId);

		ColumnDefinition intFinish = new ColumnDefinition();
		intFinish.setName("intFinish");
		intFinish.setColumnName("INTFINISH");
		intFinish.setJavaType("Integer");
		tableDefinition.addColumn(intFinish);

		ColumnDefinition fileContent = new ColumnDefinition();
		fileContent.setName("fileContent");
		fileContent.setColumnName("FILE_CONTENT");
		fileContent.setJavaType("String");
		fileContent.setLength(0);
		tableDefinition.addColumn(fileContent);

		ColumnDefinition typeTableName = new ColumnDefinition();
		typeTableName.setName("typeTableName");
		typeTableName.setColumnName("TYPE_TABLENAME");
		typeTableName.setJavaType("String");
		typeTableName.setLength(50);
		tableDefinition.addColumn(typeTableName);

		ColumnDefinition typeId = new ColumnDefinition();
		typeId.setName("typeId");
		typeId.setColumnName("TYPE_ID");
		typeId.setJavaType("String");
		typeId.setLength(50);
		tableDefinition.addColumn(typeId);

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID");
		userId.setJavaType("String");
		userId.setLength(20);
		tableDefinition.addColumn(userId);

		ColumnDefinition netRoleId = new ColumnDefinition();
		netRoleId.setName("netRoleId");
		netRoleId.setColumnName("NETROLEID");
		netRoleId.setJavaType("String");
		netRoleId.setLength(50);
		tableDefinition.addColumn(netRoleId);

		ColumnDefinition intIsFlow = new ColumnDefinition();
		intIsFlow.setName("intIsFlow");
		intIsFlow.setColumnName("INTISFLOW");
		intIsFlow.setJavaType("Integer");
		tableDefinition.addColumn(intIsFlow);

		ColumnDefinition intStop = new ColumnDefinition();
		intStop.setName("intStop");
		intStop.setColumnName("INTSTOP");
		intStop.setJavaType("Integer");
		tableDefinition.addColumn(intStop);

		ColumnDefinition fileTypeIndex = new ColumnDefinition();
		fileTypeIndex.setName("fileTypeIndex");
		fileTypeIndex.setColumnName("FILETYPE_INDEX");
		fileTypeIndex.setJavaType("Integer");
		tableDefinition.addColumn(fileTypeIndex);

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

	private CellMyTaskMainDomainFactory() {

	}

}
