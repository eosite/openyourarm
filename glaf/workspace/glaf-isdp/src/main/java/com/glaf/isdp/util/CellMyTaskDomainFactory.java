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
public class CellMyTaskDomainFactory {

	public static final String TABLENAME = "CELL_MYTASK";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("topId", "TOPID");
		columnMap.put("fillFormId", "FILLFORM_ID");
		columnMap.put("ctime", "CTIME");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("taskId", "TASK_ID");
		columnMap.put("fileDotFileId", "FILEDOT_FILEID");
		columnMap.put("name", "NAME");
		columnMap.put("projName", "PROJNAME");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("typeIndexId", "TYPE_INDEX_ID");
		columnMap.put("page", "PAGE");
		columnMap.put("finishInt", "FINISHINT");
		columnMap.put("formTypeInt", "FORMTYPEINT");
		columnMap.put("flagInt", "FLAGINT");
		columnMap.put("intInFlow", "INTINFLOW");
		columnMap.put("mainId", "MAIN_ID");
		columnMap.put("intLastPage", "INTLASTPAGE");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("topId", "String");
		javaTypeMap.put("fillFormId", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("taskId", "String");
		javaTypeMap.put("fileDotFileId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("projName", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("typeIndexId", "Integer");
		javaTypeMap.put("page", "Integer");
		javaTypeMap.put("finishInt", "Integer");
		javaTypeMap.put("formTypeInt", "Integer");
		javaTypeMap.put("flagInt", "Integer");
		javaTypeMap.put("intInFlow", "Integer");
		javaTypeMap.put("mainId", "String");
		javaTypeMap.put("intLastPage", "Integer");
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
		tableDefinition.setName("CellMyTask");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition topId = new ColumnDefinition();
		topId.setName("topId");
		topId.setColumnName("TOPID");
		topId.setJavaType("String");
		topId.setLength(50);
		tableDefinition.addColumn(topId);

		ColumnDefinition fillFormId = new ColumnDefinition();
		fillFormId.setName("fillFormId");
		fillFormId.setColumnName("FILLFORM_ID");
		fillFormId.setJavaType("String");
		fillFormId.setLength(50);
		tableDefinition.addColumn(fillFormId);

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

		ColumnDefinition fileDotFileId = new ColumnDefinition();
		fileDotFileId.setName("fileDotFileId");
		fileDotFileId.setColumnName("FILEDOT_FILEID");
		fileDotFileId.setJavaType("String");
		fileDotFileId.setLength(50);
		tableDefinition.addColumn(fileDotFileId);

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

		ColumnDefinition page = new ColumnDefinition();
		page.setName("page");
		page.setColumnName("PAGE");
		page.setJavaType("Integer");
		tableDefinition.addColumn(page);

		ColumnDefinition finishInt = new ColumnDefinition();
		finishInt.setName("finishInt");
		finishInt.setColumnName("FINISHINT");
		finishInt.setJavaType("Integer");
		tableDefinition.addColumn(finishInt);

		ColumnDefinition formTypeInt = new ColumnDefinition();
		formTypeInt.setName("formTypeInt");
		formTypeInt.setColumnName("FORMTYPEINT");
		formTypeInt.setJavaType("Integer");
		tableDefinition.addColumn(formTypeInt);

		ColumnDefinition flagInt = new ColumnDefinition();
		flagInt.setName("flagInt");
		flagInt.setColumnName("FLAGINT");
		flagInt.setJavaType("Integer");
		tableDefinition.addColumn(flagInt);

		ColumnDefinition intInFlow = new ColumnDefinition();
		intInFlow.setName("intInFlow");
		intInFlow.setColumnName("INTINFLOW");
		intInFlow.setJavaType("Integer");
		tableDefinition.addColumn(intInFlow);

		ColumnDefinition mainId = new ColumnDefinition();
		mainId.setName("mainId");
		mainId.setColumnName("MAIN_ID");
		mainId.setJavaType("String");
		mainId.setLength(50);
		tableDefinition.addColumn(mainId);

		ColumnDefinition intLastPage = new ColumnDefinition();
		intLastPage.setName("intLastPage");
		intLastPage.setColumnName("INTLASTPAGE");
		intLastPage.setJavaType("Integer");
		tableDefinition.addColumn(intLastPage);

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

	private CellMyTaskDomainFactory() {

	}

}
