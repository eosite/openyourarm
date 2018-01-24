package com.glaf.datamgr.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class FieldSetDomainFactory {

	public static final String TABLENAME = "SYS_FIELD_SET";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("datasetId", "DATASETID_");
		columnMap.put("name", "NAME_");
		columnMap.put("code", "CODE_");
		columnMap.put("fieldTable", "FIELDTABLE_");
		columnMap.put("tableNameCN", "TABLENAMECN_");
		columnMap.put("columnName", "COLUMNNAME_");
		columnMap.put("columnWidth", "COLUMNWIDTH_");
		columnMap.put("text", "TEXT_");
		columnMap.put("description", "DESCRIPTION_");
		columnMap.put("fieldId", "FIELDID_");
		columnMap.put("fieldLength", "FIELDLENGTH_");
		columnMap.put("fieldType", "FIELDTYPE_");
		columnMap.put("isShowList", "ISSHOWLIST_");
		columnMap.put("isShowTooltip", "ISSHOWTOOLTIP_");
		columnMap.put("isEditor", "ISEDITOR_");
		columnMap.put("editor", "EDITOR_");
		columnMap.put("state", "STATE_");
		columnMap.put("checked", "CHECKED_");
		columnMap.put("alignment", "ALIGNMENT_");
		columnMap.put("domId", "DOMID_");
		columnMap.put("target", "TARGET_");
		columnMap.put("url", "URL_");
		columnMap.put("type", "TYPE_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("deleteFlag", "DELETEFLAG_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");
		columnMap.put("updateBy", "UPDATEBY_");
		columnMap.put("updateTime", "UPDATETIME_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("datasetId", "Long");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("code", "String");
		javaTypeMap.put("fieldTable", "String");
		javaTypeMap.put("tableNameCN", "String");
		javaTypeMap.put("columnName", "String");
		javaTypeMap.put("columnWidth", "String");
		javaTypeMap.put("text", "String");
		javaTypeMap.put("description", "String");
		javaTypeMap.put("fieldId", "String");
		javaTypeMap.put("fieldLength", "Integer");
		javaTypeMap.put("fieldType", "String");
		javaTypeMap.put("isShowList", "String");
		javaTypeMap.put("isShowTooltip", "String");
		javaTypeMap.put("isEditor", "String");
		javaTypeMap.put("editor", "String");
		javaTypeMap.put("state", "String");
		javaTypeMap.put("checked", "String");
		javaTypeMap.put("alignment", "String");
		javaTypeMap.put("domId", "String");
		javaTypeMap.put("target", "String");
		javaTypeMap.put("url", "String");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("deleteFlag", "Integer");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("updateBy", "String");
		javaTypeMap.put("updateTime", "Date");
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
		tableDefinition.setName("FieldSet");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition datasetId = new ColumnDefinition();
		datasetId.setName("datasetId");
		datasetId.setColumnName("DATASETID_");
		datasetId.setJavaType("Long");
		tableDefinition.addColumn(datasetId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME_");
		name.setJavaType("String");
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition code = new ColumnDefinition();
		code.setName("code");
		code.setColumnName("CODE_");
		code.setJavaType("String");
		code.setLength(50);
		tableDefinition.addColumn(code);

		ColumnDefinition fieldTable = new ColumnDefinition();
		fieldTable.setName("fieldTable");
		fieldTable.setColumnName("FIELDTABLE_");
		fieldTable.setJavaType("String");
		fieldTable.setLength(50);
		tableDefinition.addColumn(fieldTable);

		ColumnDefinition tableNameCN = new ColumnDefinition();
		tableNameCN.setName("tableNameCN");
		tableNameCN.setColumnName("TABLENAMECN_");
		tableNameCN.setJavaType("String");
		tableNameCN.setLength(100);
		tableDefinition.addColumn(tableNameCN);

		ColumnDefinition columnName = new ColumnDefinition();
		columnName.setName("columnName");
		columnName.setColumnName("COLUMNNAME_");
		columnName.setJavaType("String");
		columnName.setLength(50);
		tableDefinition.addColumn(columnName);

		ColumnDefinition columnWidth = new ColumnDefinition();
		columnWidth.setName("columnWidth");
		columnWidth.setColumnName("COLUMNWIDTH_");
		columnWidth.setJavaType("String");
		columnWidth.setLength(50);
		tableDefinition.addColumn(columnWidth);

		ColumnDefinition text = new ColumnDefinition();
		text.setName("text");
		text.setColumnName("TEXT_");
		text.setJavaType("String");
		text.setLength(200);
		tableDefinition.addColumn(text);

		ColumnDefinition description = new ColumnDefinition();
		description.setName("description");
		description.setColumnName("DESCRIPTION_");
		description.setJavaType("String");
		description.setLength(500);
		tableDefinition.addColumn(description);

		ColumnDefinition fieldId = new ColumnDefinition();
		fieldId.setName("fieldId");
		fieldId.setColumnName("FIELDID_");
		fieldId.setJavaType("String");
		fieldId.setLength(50);
		tableDefinition.addColumn(fieldId);

		ColumnDefinition fieldLength = new ColumnDefinition();
		fieldLength.setName("fieldLength");
		fieldLength.setColumnName("FIELDLENGTH_");
		fieldLength.setJavaType("Integer");
		tableDefinition.addColumn(fieldLength);

		ColumnDefinition fieldType = new ColumnDefinition();
		fieldType.setName("fieldType");
		fieldType.setColumnName("FIELDTYPE_");
		fieldType.setJavaType("String");
		fieldType.setLength(20);
		tableDefinition.addColumn(fieldType);

		ColumnDefinition isShowList = new ColumnDefinition();
		isShowList.setName("isShowList");
		isShowList.setColumnName("ISSHOWLIST_");
		isShowList.setJavaType("String");
		isShowList.setLength(10);
		tableDefinition.addColumn(isShowList);

		ColumnDefinition isShowTooltip = new ColumnDefinition();
		isShowTooltip.setName("isShowTooltip");
		isShowTooltip.setColumnName("ISSHOWTOOLTIP_");
		isShowTooltip.setJavaType("String");
		isShowTooltip.setLength(10);
		tableDefinition.addColumn(isShowTooltip);

		ColumnDefinition isEditor = new ColumnDefinition();
		isEditor.setName("isEditor");
		isEditor.setColumnName("ISEDITOR_");
		isEditor.setJavaType("String");
		isEditor.setLength(10);
		tableDefinition.addColumn(isEditor);

		ColumnDefinition editor = new ColumnDefinition();
		editor.setName("editor");
		editor.setColumnName("EDITOR_");
		editor.setJavaType("String");
		editor.setLength(50);
		tableDefinition.addColumn(editor);

		ColumnDefinition state = new ColumnDefinition();
		state.setName("state");
		state.setColumnName("STATE_");
		state.setJavaType("String");
		state.setLength(50);
		tableDefinition.addColumn(state);

		ColumnDefinition checked = new ColumnDefinition();
		checked.setName("checked");
		checked.setColumnName("CHECKED_");
		checked.setJavaType("String");
		checked.setLength(10);
		tableDefinition.addColumn(checked);

		ColumnDefinition alignment = new ColumnDefinition();
		alignment.setName("alignment");
		alignment.setColumnName("ALIGNMENT_");
		alignment.setJavaType("String");
		alignment.setLength(10);
		tableDefinition.addColumn(alignment);

		ColumnDefinition domId = new ColumnDefinition();
		domId.setName("domId");
		domId.setColumnName("DOMID_");
		domId.setJavaType("String");
		domId.setLength(50);
		tableDefinition.addColumn(domId);

		ColumnDefinition target = new ColumnDefinition();
		target.setName("target");
		target.setColumnName("TARGET_");
		target.setJavaType("String");
		target.setLength(50);
		tableDefinition.addColumn(target);

		ColumnDefinition url = new ColumnDefinition();
		url.setName("url");
		url.setColumnName("URL_");
		url.setJavaType("String");
		url.setLength(500);
		tableDefinition.addColumn(url);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

		ColumnDefinition deleteFlag = new ColumnDefinition();
		deleteFlag.setName("deleteFlag");
		deleteFlag.setColumnName("DELETEFLAG_");
		deleteFlag.setJavaType("Integer");
		tableDefinition.addColumn(deleteFlag);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY_");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

		ColumnDefinition updateTime = new ColumnDefinition();
		updateTime.setName("updateTime");
		updateTime.setColumnName("UPDATETIME_");
		updateTime.setJavaType("Date");
		tableDefinition.addColumn(updateTime);

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

	private FieldSetDomainFactory() {

	}

}
