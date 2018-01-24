package com.glaf.convert.util;

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
public class ConvertPageTmplDomainFactory {

	public static final String TABLENAME = "CVT_PAGE_TMPL";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("cvtId", "CVT_ID_");
		columnMap.put("fileDotFieldId", "FILEDOT_FIELD_ID_");
		columnMap.put("cvtType", "CVT_TYPE_");
		columnMap.put("cvtSrcContent", "CVT_SRC_CONTENT_");
		columnMap.put("cvtSrcFileName", "CVT_SRC_FILENAME_");
		columnMap.put("cvtSrcName", "CVT_SRC_NAME_");
		columnMap.put("cvtXmlContent", "CVT_XML_CONTENT_");
		columnMap.put("cvtDesContent", "CVT_DES_CONTENT_");
		columnMap.put("cvtDesExt", "CVT_DES_EXT_");
		columnMap.put("status", "STATUS_");
		columnMap.put("effectiveFlag", "EFFECTIVE_FLAG_");
		columnMap.put("cvtStatus", "CVT_STATUS_");
		columnMap.put("createDatetime", "CREAT_DATETIME_");
		columnMap.put("modifyDatetime", "MODIFY_DATETIME_");

		javaTypeMap.put("cvtId", "Long");
		javaTypeMap.put("fileDotFieldId", "String");
		javaTypeMap.put("cvtType", "String");
		javaTypeMap.put("cvtSrcContent", "String");
		javaTypeMap.put("cvtSrcFileName", "String");
		javaTypeMap.put("cvtSrcName", "String");
		javaTypeMap.put("cvtXmlContent", "String");
		javaTypeMap.put("cvtDesContent", "String");
		javaTypeMap.put("cvtDesExt", "String");
		javaTypeMap.put("status", "Integer");
		javaTypeMap.put("effectiveFlag", "Integer");
		javaTypeMap.put("cvtStatus", "Integer");
		javaTypeMap.put("createDatetime", "Date");
		javaTypeMap.put("modifyDatetime", "Date");
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
		tableDefinition.setName("ConvertPageTmpl");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("cvtId");
		idColumn.setColumnName("CVT_ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition fileDotFieldId = new ColumnDefinition();
		fileDotFieldId.setName("fileDotFieldId");
		fileDotFieldId.setColumnName("FILEDOT_FIELD_ID_");
		fileDotFieldId.setJavaType("String");
		fileDotFieldId.setLength(50);
		tableDefinition.addColumn(fileDotFieldId);

		ColumnDefinition cvtType = new ColumnDefinition();
		cvtType.setName("cvtType");
		cvtType.setColumnName("CVT_TYPE_");
		cvtType.setJavaType("String");
		cvtType.setLength(10);
		tableDefinition.addColumn(cvtType);

		ColumnDefinition cvtSrcContent = new ColumnDefinition();
		cvtSrcContent.setName("cvtSrcContent");
		cvtSrcContent.setColumnName("CVT_SRC_CONTENT_");
		cvtSrcContent.setJavaType("String");
		cvtSrcContent.setLength(0);
		tableDefinition.addColumn(cvtSrcContent);

		ColumnDefinition cvtSrcFileName = new ColumnDefinition();
		cvtSrcFileName.setName("cvtSrcFileName");
		cvtSrcFileName.setColumnName("CVT_SRC_FILENAME_");
		cvtSrcFileName.setJavaType("String");
		cvtSrcFileName.setLength(50);
		tableDefinition.addColumn(cvtSrcFileName);

		ColumnDefinition cvtSrcName = new ColumnDefinition();
		cvtSrcName.setName("cvtSrcName");
		cvtSrcName.setColumnName("CVT_SRC_NAME_");
		cvtSrcName.setJavaType("String");
		cvtSrcName.setLength(50);
		tableDefinition.addColumn(cvtSrcName);

		ColumnDefinition cvtXmlContent = new ColumnDefinition();
		cvtXmlContent.setName("cvtXmlContent");
		cvtXmlContent.setColumnName("CVT_XML_CONTENT_");
		cvtXmlContent.setJavaType("String");
		cvtXmlContent.setLength(0);
		tableDefinition.addColumn(cvtXmlContent);

		ColumnDefinition cvtDesContent = new ColumnDefinition();
		cvtDesContent.setName("cvtDesContent");
		cvtDesContent.setColumnName("CVT_DES_CONTENT_");
		cvtDesContent.setJavaType("String");
		cvtDesContent.setLength(0);
		tableDefinition.addColumn(cvtDesContent);

		ColumnDefinition cvtDesExt = new ColumnDefinition();
		cvtDesExt.setName("cvtDesExt");
		cvtDesExt.setColumnName("CVT_DES_EXT_");
		cvtDesExt.setJavaType("String");
		cvtDesExt.setLength(10);
		tableDefinition.addColumn(cvtDesExt);

		ColumnDefinition status = new ColumnDefinition();
		status.setName("status");
		status.setColumnName("STATUS_");
		status.setJavaType("Integer");
		tableDefinition.addColumn(status);

		ColumnDefinition effectiveFlag = new ColumnDefinition();
		effectiveFlag.setName("effectiveFlag");
		effectiveFlag.setColumnName("EFFECTIVE_FLAG_");
		effectiveFlag.setJavaType("Integer");
		tableDefinition.addColumn(effectiveFlag);

		ColumnDefinition cvtStatus = new ColumnDefinition();
		cvtStatus.setName("cvtStatus");
		cvtStatus.setColumnName("CVT_STATUS_");
		cvtStatus.setJavaType("Integer");
		tableDefinition.addColumn(cvtStatus);

		ColumnDefinition createDatetime = new ColumnDefinition();
		createDatetime.setName("createDatetime");
		createDatetime.setColumnName("CREAT_DATETIME_");
		createDatetime.setJavaType("Date");
		tableDefinition.addColumn(createDatetime);

		ColumnDefinition modifyDatetime = new ColumnDefinition();
		modifyDatetime.setName("modifyDatetime");
		modifyDatetime.setColumnName("MODIFY_DATETIME_");
		modifyDatetime.setJavaType("Date");
		tableDefinition.addColumn(modifyDatetime);

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

	private ConvertPageTmplDomainFactory() {

	}

}
