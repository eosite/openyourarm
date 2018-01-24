package com.glaf.datamgr.util;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Resources;
import com.glaf.core.xml.XmlMappingReader;

/**
 * 
 * 实体数据工厂类
 *
 */
public class FileHistoryDomainFactory {

	public static final String TABLENAME = "FILE_HISTORY";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static volatile TableModel tableModel;

	static {
		columnMap.put("fileId", "FILEID_");
		columnMap.put("fileName", "FILENAME_");
		columnMap.put("fileSize", "FILESIZE_");
		columnMap.put("fileContent", "FILECONTENT_");
		columnMap.put("lastModified", "LASTMODIFIED_");
		columnMap.put("md5", "MD5_");
		columnMap.put("referId", "REFERID_");
		columnMap.put("path", "PATH_");
		columnMap.put("pkgStatus", "PKGSTATUS_");
		columnMap.put("pkgUpdateTime", "PKGUPDATETIME_");
		columnMap.put("version", "VERSION_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("fileId", "String");
		javaTypeMap.put("fileName", "String");
		javaTypeMap.put("fileSize", "Integer");
		javaTypeMap.put("fileContent", "Blob");
		javaTypeMap.put("lastModified", "Long");
		javaTypeMap.put("md5", "String");
		javaTypeMap.put("referId", "String");
		javaTypeMap.put("path", "String");
		javaTypeMap.put("pkgStatus", "String");
		javaTypeMap.put("pkgUpdateTime", "Date");
		javaTypeMap.put("version", "Integer");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
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
		tableDefinition.setName("FileHistory");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("fileId");
		idColumn.setColumnName("FILEID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition fileName = new ColumnDefinition();
		fileName.setName("fileName");
		fileName.setColumnName("FILENAME_");
		fileName.setJavaType("String");
		fileName.setLength(200);
		tableDefinition.addColumn(fileName);

		ColumnDefinition fileSize = new ColumnDefinition();
		fileSize.setName("fileSize");
		fileSize.setColumnName("FILESIZE_");
		fileSize.setJavaType("Integer");
		tableDefinition.addColumn(fileSize);

		ColumnDefinition fileContent = new ColumnDefinition();
		fileContent.setName("fileContent");
		fileContent.setColumnName("FILECONTENT_");
		fileContent.setJavaType("Blob");
		tableDefinition.addColumn(fileContent);

		ColumnDefinition lastModified = new ColumnDefinition();
		lastModified.setName("lastModified");
		lastModified.setColumnName("LASTMODIFIED_");
		lastModified.setJavaType("Long");
		tableDefinition.addColumn(lastModified);

		ColumnDefinition md5 = new ColumnDefinition();
		md5.setName("md5");
		md5.setColumnName("MD5_");
		md5.setJavaType("String");
		md5.setLength(200);
		tableDefinition.addColumn(md5);

		ColumnDefinition referId = new ColumnDefinition();
		referId.setName("referId");
		referId.setColumnName("REFERID_");
		referId.setJavaType("String");
		referId.setLength(50);
		tableDefinition.addColumn(referId);

		ColumnDefinition path = new ColumnDefinition();
		path.setName("path");
		path.setColumnName("PATH_");
		path.setJavaType("String");
		path.setLength(500);
		tableDefinition.addColumn(path);

		ColumnDefinition pkgStatus = new ColumnDefinition();
		pkgStatus.setName("pkgStatus");
		pkgStatus.setColumnName("PKGSTATUS_");
		pkgStatus.setJavaType("String");
		pkgStatus.setLength(20);
		tableDefinition.addColumn(pkgStatus);

		ColumnDefinition pkgUpdateTime = new ColumnDefinition();
		pkgUpdateTime.setName("pkgUpdateTime");
		pkgUpdateTime.setColumnName("PKGUPDATETIME_");
		pkgUpdateTime.setJavaType("Date");
		tableDefinition.addColumn(pkgUpdateTime);

		ColumnDefinition version = new ColumnDefinition();
		version.setName("version");
		version.setColumnName("VERSION_");
		version.setJavaType("Integer");
		tableDefinition.addColumn(version);

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

	public static TableModel readTableModel() {
		if (tableModel == null) {
			XmlMappingReader reader = new XmlMappingReader();
			InputStream inputStream = null;
			try {
				inputStream = Resources.getResourceAsStream("com/glaf/datamgr/domain/FileHistory.mapping.xml");
				tableModel = reader.read(inputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		TableModel bean = new TableModel();
		try {
			BeanUtils.copyProperties(bean, tableModel);
		} catch (Exception ex) {
			org.springframework.beans.BeanUtils.copyProperties(tableModel, bean);
		}
		return bean;
	}

	private FileHistoryDomainFactory() {

	}

}
