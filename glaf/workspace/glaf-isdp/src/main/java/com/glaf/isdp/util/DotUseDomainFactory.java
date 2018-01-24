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
public class DotUseDomainFactory {

	public static final String TABLENAME = "DOTUSE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("fileID", "FILEID");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("projId", "PROJID");
		columnMap.put("pid", "PID");
		columnMap.put("dotId", "DOTID");
		columnMap.put("num", "NUM");
		columnMap.put("name", "NAME");
		columnMap.put("cman", "CMAN");
		columnMap.put("ctime", "CTIME");
		columnMap.put("fileName", "FILE_NAME");
		columnMap.put("fileContent", "FILE_CONTENT");
		columnMap.put("fileSize", "FILESIZE");
		columnMap.put("vision", "VISION");
		columnMap.put("saveTime", "SAVETIME");
		columnMap.put("remark", "REMARK");
		columnMap.put("dwid", "DWID");
		columnMap.put("fbid", "FBID");
		columnMap.put("fxid", "FXID");
		columnMap.put("jid", "JID");
		columnMap.put("flid", "FLID");
		columnMap.put("topNode", "TOPNODE");
		columnMap.put("topId", "TOPID");
		columnMap.put("type", "TYPE");
		columnMap.put("fname", "FNAME");
		columnMap.put("isInk", "ISINK");
		columnMap.put("oldId", "OLD_ID");
		columnMap.put("taskId", "TASK_ID");
		columnMap.put("isCheck", "ISCHECK");

		javaTypeMap.put("fileID", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("projId", "Integer");
		javaTypeMap.put("pid", "Integer");
		javaTypeMap.put("dotId", "String");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("cman", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("fileName", "String");
		javaTypeMap.put("fileContent", "String");
		javaTypeMap.put("fileSize", "Integer");
		javaTypeMap.put("vision", "String");
		javaTypeMap.put("saveTime", "String");
		javaTypeMap.put("remark", "String");
		javaTypeMap.put("dwid", "Integer");
		javaTypeMap.put("fbid", "Integer");
		javaTypeMap.put("fxid", "Integer");
		javaTypeMap.put("jid", "String");
		javaTypeMap.put("flid", "String");
		javaTypeMap.put("topNode", "String");
		javaTypeMap.put("topId", "String");
		javaTypeMap.put("type", "Integer");
		javaTypeMap.put("fname", "String");
		javaTypeMap.put("isInk", "String");
		javaTypeMap.put("oldId", "String");
		javaTypeMap.put("taskId", "String");
		javaTypeMap.put("isCheck", "Integer");
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
		tableDefinition.setName("DotUse");

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

		ColumnDefinition projId = new ColumnDefinition();
		projId.setName("projId");
		projId.setColumnName("PROJID");
		projId.setJavaType("Integer");
		tableDefinition.addColumn(projId);

		ColumnDefinition pid = new ColumnDefinition();
		pid.setName("pid");
		pid.setColumnName("PID");
		pid.setJavaType("Integer");
		tableDefinition.addColumn(pid);

		ColumnDefinition dotId = new ColumnDefinition();
		dotId.setName("dotId");
		dotId.setColumnName("DOTID");
		dotId.setJavaType("String");
		dotId.setLength(50);
		tableDefinition.addColumn(dotId);

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
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition cman = new ColumnDefinition();
		cman.setName("cman");
		cman.setColumnName("CMAN");
		cman.setJavaType("String");
		cman.setLength(20);
		tableDefinition.addColumn(cman);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

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

		ColumnDefinition fileSize = new ColumnDefinition();
		fileSize.setName("fileSize");
		fileSize.setColumnName("FILESIZE");
		fileSize.setJavaType("Integer");
		tableDefinition.addColumn(fileSize);

		ColumnDefinition vision = new ColumnDefinition();
		vision.setName("vision");
		vision.setColumnName("VISION");
		vision.setJavaType("String");
		vision.setLength(100);
		tableDefinition.addColumn(vision);

		ColumnDefinition saveTime = new ColumnDefinition();
		saveTime.setName("saveTime");
		saveTime.setColumnName("SAVETIME");
		saveTime.setJavaType("String");
		saveTime.setLength(100);
		tableDefinition.addColumn(saveTime);

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

		ColumnDefinition topNode = new ColumnDefinition();
		topNode.setName("topNode");
		topNode.setColumnName("TOPNODE");
		topNode.setJavaType("String");
		topNode.setLength(250);
		tableDefinition.addColumn(topNode);

		ColumnDefinition topId = new ColumnDefinition();
		topId.setName("topId");
		topId.setColumnName("TOPID");
		topId.setJavaType("String");
		topId.setLength(50);
		tableDefinition.addColumn(topId);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE");
		type.setJavaType("Integer");
		tableDefinition.addColumn(type);

		ColumnDefinition fname = new ColumnDefinition();
		fname.setName("fname");
		fname.setColumnName("FNAME");
		fname.setJavaType("String");
		fname.setLength(250);
		tableDefinition.addColumn(fname);

		ColumnDefinition isInk = new ColumnDefinition();
		isInk.setName("isInk");
		isInk.setColumnName("ISINK");
		isInk.setJavaType("String");
		isInk.setLength(1);
		tableDefinition.addColumn(isInk);

		ColumnDefinition oldId = new ColumnDefinition();
		oldId.setName("oldId");
		oldId.setColumnName("OLD_ID");
		oldId.setJavaType("String");
		oldId.setLength(50);
		tableDefinition.addColumn(oldId);

		ColumnDefinition taskId = new ColumnDefinition();
		taskId.setName("taskId");
		taskId.setColumnName("TASK_ID");
		taskId.setJavaType("String");
		taskId.setLength(50);
		tableDefinition.addColumn(taskId);

		ColumnDefinition isCheck = new ColumnDefinition();
		isCheck.setName("isCheck");
		isCheck.setColumnName("ISCHECK");
		isCheck.setJavaType("Integer");
		tableDefinition.addColumn(isCheck);

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

	private DotUseDomainFactory() {

	}

}
