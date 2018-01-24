package com.glaf.base.modules.sys.util;

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
public class FiledotUseDomainFactory {

	public static final String TABLENAME = "DOTUSE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("fileID", "FILEID");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("projid", "PROJID");
		columnMap.put("pid", "PID");
		columnMap.put("dotid", "DOTID");
		columnMap.put("num", "NUM");
		columnMap.put("name", "NAME");
		columnMap.put("cman", "CMAN");
		columnMap.put("createTime", "CTIME");
		columnMap.put("fileName", "FILE_NAME");
		columnMap.put("fileContent", "FILE_CONTENT");
		columnMap.put("filesize", "FILESIZE");
		columnMap.put("vision", "VISION");
		columnMap.put("savetime", "SAVETIME");
		columnMap.put("remark", "REMARK");
		columnMap.put("dwid", "DWID");
		columnMap.put("fbid", "FBID");
		columnMap.put("fxid", "FXID");
		columnMap.put("jid", "JID");
		columnMap.put("flid", "FLID");
		columnMap.put("topnode", "TOPNODE");
		columnMap.put("topid", "TOPID");
		columnMap.put("fname", "FNAME");
		columnMap.put("ischeck", "ISCHECK");
		columnMap.put("ISINK", "ISINK");
		columnMap.put("oldid", "OLD_ID");
		columnMap.put("taskid", "TASK_ID");
		columnMap.put("type", "TYPE");

		javaTypeMap.put("fileID", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("projid", "Integer");
		javaTypeMap.put("pid", "Integer");
		javaTypeMap.put("dotid", "String");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("cman", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("fileName", "String");
		javaTypeMap.put("fileContent", "Blob");
		javaTypeMap.put("filesize", "Integer");
		javaTypeMap.put("vision", "String");
		javaTypeMap.put("savetime", "String");
		javaTypeMap.put("remark", "String");
		javaTypeMap.put("dwid", "Integer");
		javaTypeMap.put("fbid", "Integer");
		javaTypeMap.put("fxid", "Integer");
		javaTypeMap.put("jid", "String");
		javaTypeMap.put("flid", "String");
		javaTypeMap.put("topnode", "String");
		javaTypeMap.put("topid", "String");
		javaTypeMap.put("fname", "String");
		javaTypeMap.put("ischeck", "Integer");
		javaTypeMap.put("ISINK", "String");
		javaTypeMap.put("oldid", "String");
		javaTypeMap.put("taskid", "String");
		javaTypeMap.put("type", "Integer");
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
		tableDefinition.setName("FiledotUse");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("fileID");
		idColumn.setColumnName("FILEID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEX_ID");
		indexId.setJavaType("Integer");
		tableDefinition.addColumn(indexId);

		ColumnDefinition projid = new ColumnDefinition();
		projid.setName("projid");
		projid.setColumnName("PROJID");
		projid.setJavaType("Integer");
		tableDefinition.addColumn(projid);

		ColumnDefinition pid = new ColumnDefinition();
		pid.setName("pid");
		pid.setColumnName("PID");
		pid.setJavaType("Integer");
		tableDefinition.addColumn(pid);

		ColumnDefinition dotid = new ColumnDefinition();
		dotid.setName("dotid");
		dotid.setColumnName("DOTID");
		dotid.setJavaType("String");
		dotid.setLength(50);
		tableDefinition.addColumn(dotid);

		ColumnDefinition num = new ColumnDefinition();
		num.setName("num");
		num.setColumnName("NUM");
		num.setJavaType("String");
		num.setLength(10);
		tableDefinition.addColumn(num);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME");
		name.setJavaType("String");
		name.setLength(255);
		tableDefinition.addColumn(name);

		ColumnDefinition cman = new ColumnDefinition();
		cman.setName("cman");
		cman.setColumnName("CMAN");
		cman.setJavaType("String");
		cman.setLength(20);
		tableDefinition.addColumn(cman);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CTIME");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		ColumnDefinition fileName = new ColumnDefinition();
		fileName.setName("fileName");
		fileName.setColumnName("FILE_NAME");
		fileName.setJavaType("String");
		fileName.setLength(255);
		tableDefinition.addColumn(fileName);

		ColumnDefinition fileContent = new ColumnDefinition();
		fileContent.setName("fileContent");
		fileContent.setColumnName("FILE_CONTENT");
		fileContent.setJavaType("Blob");
		tableDefinition.addColumn(fileContent);

		ColumnDefinition filesize = new ColumnDefinition();
		filesize.setName("filesize");
		filesize.setColumnName("FILESIZE");
		filesize.setJavaType("Integer");
		tableDefinition.addColumn(filesize);

		ColumnDefinition vision = new ColumnDefinition();
		vision.setName("vision");
		vision.setColumnName("VISION");
		vision.setJavaType("String");
		vision.setLength(100);
		tableDefinition.addColumn(vision);

		ColumnDefinition savetime = new ColumnDefinition();
		savetime.setName("savetime");
		savetime.setColumnName("SAVETIME");
		savetime.setJavaType("String");
		savetime.setLength(100);
		tableDefinition.addColumn(savetime);

		ColumnDefinition remark = new ColumnDefinition();
		remark.setName("remark");
		remark.setColumnName("REMARK");
		remark.setJavaType("String");
		remark.setLength(200);
		tableDefinition.addColumn(remark);

		ColumnDefinition dwid = new ColumnDefinition();
		dwid.setName("dwid");
		dwid.setColumnName("DWID");
		dwid.setJavaType("Integer");
		tableDefinition.addColumn(dwid);

		ColumnDefinition fbid = new ColumnDefinition();
		fbid.setName("fbid");
		fbid.setColumnName("FBID");
		fbid.setJavaType("Integer");
		tableDefinition.addColumn(fbid);

		ColumnDefinition fxid = new ColumnDefinition();
		fxid.setName("fxid");
		fxid.setColumnName("FXID");
		fxid.setJavaType("Integer");
		tableDefinition.addColumn(fxid);

		ColumnDefinition jid = new ColumnDefinition();
		jid.setName("jid");
		jid.setColumnName("JID");
		jid.setJavaType("String");
		jid.setLength(50);
		tableDefinition.addColumn(jid);

		ColumnDefinition flid = new ColumnDefinition();
		flid.setName("flid");
		flid.setColumnName("FLID");
		flid.setJavaType("String");
		flid.setLength(50);
		tableDefinition.addColumn(flid);

		ColumnDefinition topnode = new ColumnDefinition();
		topnode.setName("topnode");
		topnode.setColumnName("TOPNODE");
		topnode.setJavaType("String");
		topnode.setLength(250);
		tableDefinition.addColumn(topnode);

		ColumnDefinition topid = new ColumnDefinition();
		topid.setName("topid");
		topid.setColumnName("TOPID");
		topid.setJavaType("String");
		topid.setLength(50);
		tableDefinition.addColumn(topid);



		ColumnDefinition fname = new ColumnDefinition();
		fname.setName("fname");
		fname.setColumnName("FNAME");
		fname.setJavaType("String");
		fname.setLength(250);
		tableDefinition.addColumn(fname);

		ColumnDefinition ischeck = new ColumnDefinition();
		ischeck.setName("ischeck");
		ischeck.setColumnName("ISCHECK");
		ischeck.setJavaType("Integer");
		tableDefinition.addColumn(ischeck);

		ColumnDefinition ISINK = new ColumnDefinition();
		ISINK.setName("ISINK");
		ISINK.setColumnName("ISINK");
		ISINK.setJavaType("String");
		ISINK.setLength(1);
		tableDefinition.addColumn(ISINK);

		ColumnDefinition oldid = new ColumnDefinition();
		oldid.setName("oldid");
		oldid.setColumnName("OLD_ID");
		oldid.setJavaType("String");
		oldid.setLength(50);
		tableDefinition.addColumn(oldid);

		ColumnDefinition taskid = new ColumnDefinition();
		taskid.setName("taskid");
		taskid.setColumnName("TASK_ID");
		taskid.setJavaType("String");
		taskid.setLength(50);
		tableDefinition.addColumn(taskid);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("type");
		type.setJavaType("Integer");
		tableDefinition.addColumn(type);

 

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
					dataRequest.getFilter().setColumn(
							columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter()
							.setJavaType(
									javaTypeMap.get(dataRequest.getFilter()
											.getField()));
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
	}

	private FiledotUseDomainFactory() {

	}

}
