package com.glaf.form.core.util;

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
public class UserSqliteUploadDomainFactory {

    public static final String TABLENAME = "USER_SQLITE_UPLOAD_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("userId", "USERID_");
        columnMap.put("fileName", "FILENAME_");
        columnMap.put("filePath", "FILEPATH_");
        columnMap.put("logfilePath", "LOGFILEPATH_");
        columnMap.put("status", "STATUS_");
        columnMap.put("uploadDate", "UPLOADDATE_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("userId", "String");
        javaTypeMap.put("fileName", "String");
        javaTypeMap.put("filePath", "String");
        javaTypeMap.put("logfilePath", "String");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("uploadDate", "Date");
    }


    public static Map<String, String> getColumnMap() {
	return columnMap;
    }

    public static Map<String, String> getJavaTypeMap() {
	return javaTypeMap;
    }

    public static TableDefinition getTableDefinition(){
        return getTableDefinition(TABLENAME);
    }

    public static TableDefinition getTableDefinition(String tableName) {
        tableName = tableName.toUpperCase();
        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setTableName(tableName);
        tableDefinition.setName("UserSqliteUpload");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition userId = new ColumnDefinition();
        userId.setName("userId");
        userId.setColumnName("USERID_");
        userId.setJavaType("String");
        userId.setLength(50);
        tableDefinition.addColumn(userId);

        ColumnDefinition fileName = new ColumnDefinition();
        fileName.setName("fileName");
        fileName.setColumnName("FILENAME_");
        fileName.setJavaType("String");
        fileName.setLength(50);
        tableDefinition.addColumn(fileName);

        ColumnDefinition filePath = new ColumnDefinition();
        filePath.setName("filePath");
        filePath.setColumnName("FILEPATH_");
        filePath.setJavaType("String");
        filePath.setLength(50);
        tableDefinition.addColumn(filePath);

        ColumnDefinition logfilePath = new ColumnDefinition();
        logfilePath.setName("logfilePath");
        logfilePath.setColumnName("LOGFILEPATH_");
        logfilePath.setJavaType("String");
        logfilePath.setLength(50);
        tableDefinition.addColumn(logfilePath);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS_");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition uploadDate = new ColumnDefinition();
        uploadDate.setName("uploadDate");
        uploadDate.setColumnName("UPLOADDATE_");
        uploadDate.setJavaType("Date");
        tableDefinition.addColumn(uploadDate);

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
    }

    private UserSqliteUploadDomainFactory() {

    }

}
