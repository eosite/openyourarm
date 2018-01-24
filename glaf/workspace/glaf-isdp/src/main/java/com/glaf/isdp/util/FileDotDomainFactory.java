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
public class FileDotDomainFactory {

	public static final String TABLENAME = "FILEDOT";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("fileID", "FILEID");
		columnMap.put("listId", "LISTID");
		columnMap.put("indexId", "INDEXID");
		columnMap.put("fbelong", "FBELONG");
		columnMap.put("fnum", "FNUM");
		columnMap.put("pbelong", "PBELONG");
		columnMap.put("num", "NUM");
		columnMap.put("fname", "FNAME");
		columnMap.put("dotName", "DOTNAME");
		columnMap.put("ctime", "CTIME");
		columnMap.put("dtime", "DTIME");
		columnMap.put("fileName", "FILE_NAME");
		columnMap.put("fileContent", "FILE_CONTENT");
		columnMap.put("fileSize", "FILESIZE");
		columnMap.put("dwid", "DWID");
		columnMap.put("fbid", "FBID");
		columnMap.put("fxid", "FXID");
		columnMap.put("jid", "JID");
		columnMap.put("flid", "FLID");
		columnMap.put("topNode", "TOPNODE");
		columnMap.put("cman", "CMAN");
		columnMap.put("content", "CONTENT");
		columnMap.put("listFlag", "LISTFLAG");
		columnMap.put("toFile", "TOFILE");
		columnMap.put("isInk", "ISINK");
		columnMap.put("dotType", "DOTTYPE");
		columnMap.put("ctimeDName", "CTIMEDNAME");
		columnMap.put("type", "TYPE");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("utreeIndex", "UTREE_INDEX");
		columnMap.put("isQuan", "ISQUAN");
		columnMap.put("intSysForm", "INTSYSFORM");

		javaTypeMap.put("fileID", "String");
		javaTypeMap.put("listId", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("fbelong", "String");
		javaTypeMap.put("fnum", "String");
		javaTypeMap.put("pbelong", "String");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("fname", "String");
		javaTypeMap.put("dotName", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("dtime", "Date");
		javaTypeMap.put("fileName", "String");
		javaTypeMap.put("fileContent", "String");
		javaTypeMap.put("fileSize", "Integer");
		javaTypeMap.put("dwid", "Integer");
		javaTypeMap.put("fbid", "Integer");
		javaTypeMap.put("fxid", "Integer");
		javaTypeMap.put("jid", "String");
		javaTypeMap.put("flid", "String");
		javaTypeMap.put("topNode", "String");
		javaTypeMap.put("cman", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("listFlag", "String");
		javaTypeMap.put("toFile", "Integer");
		javaTypeMap.put("isInk", "String");
		javaTypeMap.put("dotType", "Integer");
		javaTypeMap.put("ctimeDName", "String");
		javaTypeMap.put("type", "Integer");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("utreeIndex", "Integer");
		javaTypeMap.put("isQuan", "String");
		javaTypeMap.put("intSysForm", "Integer");
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
		tableDefinition.setName("FileDot");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("fileID");
		idColumn.setColumnName("FILEID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition listId = new ColumnDefinition();
		listId.setName("listId");
		listId.setColumnName("LISTID");
		listId.setJavaType("String");
		listId.setLength(50);
		tableDefinition.addColumn(listId);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEXID");
		indexId.setJavaType("Integer");
		tableDefinition.addColumn(indexId);

		ColumnDefinition fbelong = new ColumnDefinition();
		fbelong.setName("fbelong");
		fbelong.setColumnName("FBELONG");
		fbelong.setJavaType("String");
		fbelong.setLength(250);
		tableDefinition.addColumn(fbelong);

		ColumnDefinition fnum = new ColumnDefinition();
		fnum.setName("fnum");
		fnum.setColumnName("FNUM");
		fnum.setJavaType("String");
		fnum.setLength(50);
		tableDefinition.addColumn(fnum);

		ColumnDefinition pbelong = new ColumnDefinition();
		pbelong.setName("pbelong");
		pbelong.setColumnName("PBELONG");
		pbelong.setJavaType("String");
		pbelong.setLength(200);
		tableDefinition.addColumn(pbelong);

		ColumnDefinition num = new ColumnDefinition();
		num.setName("num");
		num.setColumnName("NUM");
		num.setJavaType("String");
		num.setLength(50);
		tableDefinition.addColumn(num);

		ColumnDefinition fname = new ColumnDefinition();
		fname.setName("fname");
		fname.setColumnName("FNAME");
		fname.setJavaType("String");
		fname.setLength(255);
		tableDefinition.addColumn(fname);

		ColumnDefinition dotName = new ColumnDefinition();
		dotName.setName("dotName");
		dotName.setColumnName("DOTNAME");
		dotName.setJavaType("String");
		dotName.setLength(255);
		tableDefinition.addColumn(dotName);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition dtime = new ColumnDefinition();
		dtime.setName("dtime");
		dtime.setColumnName("DTIME");
		dtime.setJavaType("Date");
		tableDefinition.addColumn(dtime);

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

		ColumnDefinition cman = new ColumnDefinition();
		cman.setName("cman");
		cman.setColumnName("CMAN");
		cman.setJavaType("String");
		cman.setLength(50);
		tableDefinition.addColumn(cman);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(250);
		tableDefinition.addColumn(content);

		ColumnDefinition listFlag = new ColumnDefinition();
		listFlag.setName("listFlag");
		listFlag.setColumnName("LISTFLAG");
		listFlag.setJavaType("String");
		listFlag.setLength(1);
		tableDefinition.addColumn(listFlag);

		ColumnDefinition toFile = new ColumnDefinition();
		toFile.setName("toFile");
		toFile.setColumnName("TOFILE");
		toFile.setJavaType("Integer");
		tableDefinition.addColumn(toFile);

		ColumnDefinition isInk = new ColumnDefinition();
		isInk.setName("isInk");
		isInk.setColumnName("ISINK");
		isInk.setJavaType("String");
		isInk.setLength(1);
		tableDefinition.addColumn(isInk);

		ColumnDefinition dotType = new ColumnDefinition();
		dotType.setName("dotType");
		dotType.setColumnName("DOTTYPE");
		dotType.setJavaType("Integer");
		tableDefinition.addColumn(dotType);

		ColumnDefinition ctimeDName = new ColumnDefinition();
		ctimeDName.setName("ctimeDName");
		ctimeDName.setColumnName("CTIMEDNAME");
		ctimeDName.setJavaType("String");
		ctimeDName.setLength(50);
		tableDefinition.addColumn(ctimeDName);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE");
		type.setJavaType("Integer");
		tableDefinition.addColumn(type);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition utreeIndex = new ColumnDefinition();
		utreeIndex.setName("utreeIndex");
		utreeIndex.setColumnName("UTREE_INDEX");
		utreeIndex.setJavaType("Integer");
		tableDefinition.addColumn(utreeIndex);

		ColumnDefinition isQuan = new ColumnDefinition();
		isQuan.setName("isQuan");
		isQuan.setColumnName("ISQUAN");
		isQuan.setJavaType("String");
		isQuan.setLength(1);
		tableDefinition.addColumn(isQuan);

		ColumnDefinition intSysForm = new ColumnDefinition();
		intSysForm.setName("intSysForm");
		intSysForm.setColumnName("INTSYSFORM");
		intSysForm.setJavaType("Integer");
		tableDefinition.addColumn(intSysForm);

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

	private FileDotDomainFactory() {

	}

}
