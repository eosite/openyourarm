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
public class CellFillFormDomainFactory {

	public static final String TABLENAME = "CELL_FILLFORM";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("topId", "TOPID");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("taskId", "TASK_ID");
		columnMap.put("pfileFlag", "PFILEFLAG");
		columnMap.put("fileDotFileId", "FILEDOT_FILEID");
		columnMap.put("name", "NAME");
		columnMap.put("chkNum", "CHKNUM");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("chkTotal", "CHKTOTAL");
		columnMap.put("chkResult", "CHKRESULT");
		columnMap.put("pfileId", "PFILE_ID");
		columnMap.put("tempSave", "TEMPSAVE");
		columnMap.put("userId", "USERID");
		columnMap.put("refillFlag", "REFILLFLAG");
		columnMap.put("groupId", "GROUPID");
		columnMap.put("oldId", "OLD_ID");
		columnMap.put("roleId", "ROLE_ID");
		columnMap.put("isFinish", "ISFINISH");
		columnMap.put("typeTableName", "TYPE_TABLENAME");
		columnMap.put("typeId", "TYPE_ID");
		columnMap.put("typeIndexId", "TYPE_INDEX_ID");
		columnMap.put("mainId", "MAIN_ID");
		columnMap.put("intLastPage", "INTLASTPAGE");
		columnMap.put("intSheets", "INTSHEETS");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("topId", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("taskId", "String");
		javaTypeMap.put("pfileFlag", "Integer");
		javaTypeMap.put("fileDotFileId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("chkNum", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("chkTotal", "Integer");
		javaTypeMap.put("chkResult", "Integer");
		javaTypeMap.put("pfileId", "String");
		javaTypeMap.put("tempSave", "Integer");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("refillFlag", "Integer");
		javaTypeMap.put("groupId", "Integer");
		javaTypeMap.put("oldId", "String");
		javaTypeMap.put("roleId", "Integer");
		javaTypeMap.put("isFinish", "Integer");
		javaTypeMap.put("typeTableName", "String");
		javaTypeMap.put("typeId", "String");
		javaTypeMap.put("typeIndexId", "Integer");
		javaTypeMap.put("mainId", "String");
		javaTypeMap.put("intLastPage", "Integer");
		javaTypeMap.put("intSheets", "Integer");
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
		tableDefinition.setName("CellFillForm");

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

		ColumnDefinition pfileFlag = new ColumnDefinition();
		pfileFlag.setName("pfileFlag");
		pfileFlag.setColumnName("PFILEFLAG");
		pfileFlag.setJavaType("Integer");
		tableDefinition.addColumn(pfileFlag);

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
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition chkNum = new ColumnDefinition();
		chkNum.setName("chkNum");
		chkNum.setColumnName("CHKNUM");
		chkNum.setJavaType("String");
		chkNum.setLength(50);
		tableDefinition.addColumn(chkNum);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition chkTotal = new ColumnDefinition();
		chkTotal.setName("chkTotal");
		chkTotal.setColumnName("CHKTOTAL");
		chkTotal.setJavaType("Integer");
		tableDefinition.addColumn(chkTotal);

		ColumnDefinition chkResult = new ColumnDefinition();
		chkResult.setName("chkResult");
		chkResult.setColumnName("CHKRESULT");
		chkResult.setJavaType("Integer");
		tableDefinition.addColumn(chkResult);

		ColumnDefinition pfileId = new ColumnDefinition();
		pfileId.setName("pfileId");
		pfileId.setColumnName("PFILE_ID");
		pfileId.setJavaType("String");
		pfileId.setLength(50);
		tableDefinition.addColumn(pfileId);

		ColumnDefinition tempSave = new ColumnDefinition();
		tempSave.setName("tempSave");
		tempSave.setColumnName("TEMPSAVE");
		tempSave.setJavaType("Integer");
		tableDefinition.addColumn(tempSave);

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID");
		userId.setJavaType("String");
		userId.setLength(20);
		tableDefinition.addColumn(userId);

		ColumnDefinition refillFlag = new ColumnDefinition();
		refillFlag.setName("refillFlag");
		refillFlag.setColumnName("REFILLFLAG");
		refillFlag.setJavaType("Integer");
		tableDefinition.addColumn(refillFlag);

		ColumnDefinition groupId = new ColumnDefinition();
		groupId.setName("groupId");
		groupId.setColumnName("GROUPID");
		groupId.setJavaType("Integer");
		tableDefinition.addColumn(groupId);

		ColumnDefinition oldId = new ColumnDefinition();
		oldId.setName("oldId");
		oldId.setColumnName("OLD_ID");
		oldId.setJavaType("String");
		oldId.setLength(50);
		tableDefinition.addColumn(oldId);

		ColumnDefinition roleId = new ColumnDefinition();
		roleId.setName("roleId");
		roleId.setColumnName("ROLE_ID");
		roleId.setJavaType("Integer");
		tableDefinition.addColumn(roleId);

		ColumnDefinition isFinish = new ColumnDefinition();
		isFinish.setName("isFinish");
		isFinish.setColumnName("ISFINISH");
		isFinish.setJavaType("Integer");
		tableDefinition.addColumn(isFinish);

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

		ColumnDefinition typeIndexId = new ColumnDefinition();
		typeIndexId.setName("typeIndexId");
		typeIndexId.setColumnName("TYPE_INDEX_ID");
		typeIndexId.setJavaType("Integer");
		tableDefinition.addColumn(typeIndexId);

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

		ColumnDefinition intSheets = new ColumnDefinition();
		intSheets.setName("intSheets");
		intSheets.setColumnName("INTSHEETS");
		intSheets.setJavaType("Integer");
		tableDefinition.addColumn(intSheets);

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

	private CellFillFormDomainFactory() {

	}

}
