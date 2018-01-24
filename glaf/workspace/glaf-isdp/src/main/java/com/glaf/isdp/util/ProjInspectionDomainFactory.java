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
public class ProjInspectionDomainFactory {

	public static final String TABLENAME = "PROJ_INSPECTION";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("intFlag", "INTFLAG");
		columnMap.put("cellTmpFileTypeId", "CELL_TMPFILETYPE_ID");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("chkResult", "CHKRESULT");
		columnMap.put("pfileId", "PFILE_ID");
		columnMap.put("refillFlag", "REFILLFLAG");
		columnMap.put("groupId", "GROUPID");
		columnMap.put("oldId", "OLD_ID");
		columnMap.put("emailId", "EMAIL_ID");
		columnMap.put("recemailId", "RECEMAIL_ID");
		columnMap.put("mainId", "MAIN_ID");
		columnMap.put("tagNum", "TAGNUM");
		columnMap.put("ctime", "CTIME");
		columnMap.put("tname", "TNAME");
		columnMap.put("page", "PAGE");
		columnMap.put("duty", "DUTY");
		columnMap.put("thematic", "THEMATIC");
		columnMap.put("annotations", "ANNOTATIONS");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("intFlag", "Integer");
		javaTypeMap.put("cellTmpFileTypeId", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("chkResult", "Integer");
		javaTypeMap.put("pfileId", "String");
		javaTypeMap.put("refillFlag", "Integer");
		javaTypeMap.put("groupId", "Integer");
		javaTypeMap.put("oldId", "String");
		javaTypeMap.put("emailId", "String");
		javaTypeMap.put("recemailId", "String");
		javaTypeMap.put("mainId", "String");
		javaTypeMap.put("tagNum", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("tname", "String");
		javaTypeMap.put("page", "Integer");
		javaTypeMap.put("duty", "String");
		javaTypeMap.put("thematic", "String");
		javaTypeMap.put("annotations", "String");
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
		tableDefinition.setName("ProjInspection");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(100);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEX_ID");
		indexId.setJavaType("Integer");
		tableDefinition.addColumn(indexId);

		ColumnDefinition intFlag = new ColumnDefinition();
		intFlag.setName("intFlag");
		intFlag.setColumnName("INTFLAG");
		intFlag.setJavaType("Integer");
		tableDefinition.addColumn(intFlag);

		ColumnDefinition cellTmpFileTypeId = new ColumnDefinition();
		cellTmpFileTypeId.setName("cellTmpFileTypeId");
		cellTmpFileTypeId.setColumnName("CELL_TMPFILETYPE_ID");
		cellTmpFileTypeId.setJavaType("String");
		cellTmpFileTypeId.setLength(50);
		tableDefinition.addColumn(cellTmpFileTypeId);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

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

		ColumnDefinition emailId = new ColumnDefinition();
		emailId.setName("emailId");
		emailId.setColumnName("EMAIL_ID");
		emailId.setJavaType("String");
		emailId.setLength(50);
		tableDefinition.addColumn(emailId);

		ColumnDefinition recemailId = new ColumnDefinition();
		recemailId.setName("recemailId");
		recemailId.setColumnName("RECEMAIL_ID");
		recemailId.setJavaType("String");
		recemailId.setLength(50);
		tableDefinition.addColumn(recemailId);

		ColumnDefinition mainId = new ColumnDefinition();
		mainId.setName("mainId");
		mainId.setColumnName("MAIN_ID");
		mainId.setJavaType("String");
		mainId.setLength(50);
		tableDefinition.addColumn(mainId);

		ColumnDefinition tagNum = new ColumnDefinition();
		tagNum.setName("tagNum");
		tagNum.setColumnName("TAGNUM");
		tagNum.setJavaType("String");
		tagNum.setLength(50);
		tableDefinition.addColumn(tagNum);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition tname = new ColumnDefinition();
		tname.setName("tname");
		tname.setColumnName("TNAME");
		tname.setJavaType("String");
		tname.setLength(250);
		tableDefinition.addColumn(tname);

		ColumnDefinition page = new ColumnDefinition();
		page.setName("page");
		page.setColumnName("PAGE");
		page.setJavaType("Integer");
		tableDefinition.addColumn(page);

		ColumnDefinition duty = new ColumnDefinition();
		duty.setName("duty");
		duty.setColumnName("DUTY");
		duty.setJavaType("String");
		duty.setLength(100);
		tableDefinition.addColumn(duty);

		ColumnDefinition thematic = new ColumnDefinition();
		thematic.setName("thematic");
		thematic.setColumnName("THEMATIC");
		thematic.setJavaType("String");
		thematic.setLength(100);
		tableDefinition.addColumn(thematic);

		ColumnDefinition annotations = new ColumnDefinition();
		annotations.setName("annotations");
		annotations.setColumnName("ANNOTATIONS");
		annotations.setJavaType("String");
		annotations.setLength(100);
		tableDefinition.addColumn(annotations);

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

	private ProjInspectionDomainFactory() {

	}

}
