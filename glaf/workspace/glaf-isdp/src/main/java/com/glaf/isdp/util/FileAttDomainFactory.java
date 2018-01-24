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
public class FileAttDomainFactory {

	public static final String TABLENAME = "FILEATT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("fileID", "FILEID");
		columnMap.put("topId", "TOPID");
		columnMap.put("num", "NUM");
		columnMap.put("actor", "ACTOR");
		columnMap.put("fname", "FNAME");
		columnMap.put("fileName", "FILE_NAME");
		columnMap.put("fileContent", "FILE_CONTENT");
		columnMap.put("vision", "VISION");
		columnMap.put("ctime", "CTIME");
		columnMap.put("fileSize", "FILESIZE");
		columnMap.put("visID", "VISID");
		columnMap.put("attID", "ATTID");
		columnMap.put("isTextContent", "ISTEXTCONTENT");
		columnMap.put("textContent", "TEXTCONTENT");

		javaTypeMap.put("fileID", "String");
		javaTypeMap.put("topId", "String");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("actor", "String");
		javaTypeMap.put("fname", "String");
		javaTypeMap.put("fileName", "String");
		javaTypeMap.put("fileContent", "String");
		javaTypeMap.put("vision", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("fileSize", "Integer");
		javaTypeMap.put("visID", "String");
		javaTypeMap.put("attID", "String");
		javaTypeMap.put("isTextContent", "Integer");
		javaTypeMap.put("textContent", "String");
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
		tableDefinition.setName("FileAtt");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("fileID");
		idColumn.setColumnName("FILEID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition topId = new ColumnDefinition();
		topId.setName("topId");
		topId.setColumnName("TOPID");
		topId.setJavaType("String");
		topId.setLength(50);
		tableDefinition.addColumn(topId);

		ColumnDefinition num = new ColumnDefinition();
		num.setName("num");
		num.setColumnName("NUM");
		num.setJavaType("String");
		num.setLength(50);
		tableDefinition.addColumn(num);

		ColumnDefinition actor = new ColumnDefinition();
		actor.setName("actor");
		actor.setColumnName("ACTOR");
		actor.setJavaType("String");
		actor.setLength(20);
		tableDefinition.addColumn(actor);

		ColumnDefinition fname = new ColumnDefinition();
		fname.setName("fname");
		fname.setColumnName("FNAME");
		fname.setJavaType("String");
		fname.setLength(255);
		tableDefinition.addColumn(fname);

		ColumnDefinition fileName = new ColumnDefinition();
		fileName.setName("fileName");
		fileName.setColumnName("FILE_NAME");
		fileName.setJavaType("String");
		fileName.setLength(255);
		tableDefinition.addColumn(fileName);

		ColumnDefinition fileContent = new ColumnDefinition();
		fileContent.setName("fileContent");
		fileContent.setColumnName("FILE_CONTENT");
		fileContent.setJavaType("String");
		fileContent.setLength(0);
		tableDefinition.addColumn(fileContent);

		ColumnDefinition vision = new ColumnDefinition();
		vision.setName("vision");
		vision.setColumnName("VISION");
		vision.setJavaType("String");
		vision.setLength(50);
		tableDefinition.addColumn(vision);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition fileSize = new ColumnDefinition();
		fileSize.setName("fileSize");
		fileSize.setColumnName("FILESIZE");
		fileSize.setJavaType("Integer");
		tableDefinition.addColumn(fileSize);

		ColumnDefinition visID = new ColumnDefinition();
		visID.setName("visID");
		visID.setColumnName("VISID");
		visID.setJavaType("String");
		visID.setLength(50);
		tableDefinition.addColumn(visID);

		ColumnDefinition attID = new ColumnDefinition();
		attID.setName("attID");
		attID.setColumnName("ATTID");
		attID.setJavaType("String");
		attID.setLength(50);
		tableDefinition.addColumn(attID);

		ColumnDefinition isTextContent = new ColumnDefinition();
		isTextContent.setName("isTextContent");
		isTextContent.setColumnName("ISTEXTCONTENT");
		isTextContent.setJavaType("Integer");
		tableDefinition.addColumn(isTextContent);

		ColumnDefinition textContent = new ColumnDefinition();
		textContent.setName("textContent");
		textContent.setColumnName("TEXTCONTENT");
		textContent.setJavaType("String");
		textContent.setLength(0);
		tableDefinition.addColumn(textContent);

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

	private FileAttDomainFactory() {

	}

}
