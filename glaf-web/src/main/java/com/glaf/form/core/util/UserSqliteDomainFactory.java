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
public class UserSqliteDomainFactory {

    public static final String TABLENAME = "USER_SQLITE_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("userId", "USERID_");
        columnMap.put("name", "NAME_");
        columnMap.put("desc", "DESC_");
        columnMap.put("sqliteCode", "SQLITECODE_");
        columnMap.put("ruleJson", "RULEJSON_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createDate", "CREATEDATE_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateDate", "UPDATEDATE_");
        columnMap.put("fileDate", "FILEDATE_");
        columnMap.put("downloadNum", "DOWNLOADNUM_");
        columnMap.put("status", "STATUS_");
        columnMap.put("errorMessage", "ERROR_MESSAGE_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("userId", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("desc", "String");
        javaTypeMap.put("sqliteCode", "String");
        javaTypeMap.put("ruleJson", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createDate", "Date");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateDate", "Date");
        javaTypeMap.put("fileDate", "Date");
        javaTypeMap.put("downloadNum", "Integer");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("errorMessage", "String");
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
        tableDefinition.setName("UserSqlite");

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

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition desc = new ColumnDefinition();
        desc.setName("desc");
        desc.setColumnName("DESC_");
        desc.setJavaType("String");
        desc.setLength(100);
        tableDefinition.addColumn(desc);

        ColumnDefinition sqliteCode = new ColumnDefinition();
        sqliteCode.setName("sqliteCode");
        sqliteCode.setColumnName("SQLITECODE_");
        sqliteCode.setJavaType("String");
        sqliteCode.setLength(50);
        tableDefinition.addColumn(sqliteCode);

        ColumnDefinition ruleJson = new ColumnDefinition();
        ruleJson.setName("ruleJson");
        ruleJson.setColumnName("RULEJSON_");
        ruleJson.setJavaType("String");
        ruleJson.setLength(0);
        tableDefinition.addColumn(ruleJson);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY_");
        createBy.setJavaType("String");
        createBy.setLength(50);
        tableDefinition.addColumn(createBy);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE_");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY_");
        updateBy.setJavaType("String");
        updateBy.setLength(50);
        tableDefinition.addColumn(updateBy);

        ColumnDefinition updateDate = new ColumnDefinition();
        updateDate.setName("updateDate");
        updateDate.setColumnName("UPDATEDATE_");
        updateDate.setJavaType("Date");
        tableDefinition.addColumn(updateDate);

        ColumnDefinition fileDate = new ColumnDefinition();
        fileDate.setName("fileDate");
        fileDate.setColumnName("FILEDATE_");
        fileDate.setJavaType("Date");
        tableDefinition.addColumn(fileDate);

        ColumnDefinition downloadNum = new ColumnDefinition();
        downloadNum.setName("downloadNum");
        downloadNum.setColumnName("DOWNLOADNUM_");
        downloadNum.setJavaType("Integer");
        tableDefinition.addColumn(downloadNum);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS_");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition errorMessage = new ColumnDefinition();
        errorMessage.setName("errorMessage");
        errorMessage.setColumnName("ERROR_MESSAGE_");
        errorMessage.setJavaType("String");
        errorMessage.setLength(0);
        tableDefinition.addColumn(errorMessage);

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

    private UserSqliteDomainFactory() {

    }

}
