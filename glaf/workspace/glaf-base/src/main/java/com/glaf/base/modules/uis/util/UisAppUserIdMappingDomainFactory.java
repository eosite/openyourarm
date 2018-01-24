package com.glaf.base.modules.uis.util;

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
public class UisAppUserIdMappingDomainFactory {

    public static final String TABLENAME = "UIS_APP_USERID_MAPPING";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("appUserId", "APP_USER_ID_");
        columnMap.put("userId", "USER_ID_");
        columnMap.put("appId", "APP_ID_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("appUserId", "String");
        javaTypeMap.put("userId", "String");
        javaTypeMap.put("appId", "String");
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
        tableDefinition.setName("UisAppUserIdMapping");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition appUserId = new ColumnDefinition();
        appUserId.setName("appUserId");
        appUserId.setColumnName("APP_USER_ID_");
        appUserId.setJavaType("String");
        appUserId.setLength(50);
        tableDefinition.addColumn(appUserId);

        ColumnDefinition userId = new ColumnDefinition();
        userId.setName("userId");
        userId.setColumnName("USER_ID_");
        userId.setJavaType("String");
        userId.setLength(50);
        tableDefinition.addColumn(userId);

        ColumnDefinition appId = new ColumnDefinition();
        appId.setName("appId");
        appId.setColumnName("APP_ID_");
        appId.setJavaType("String");
        appId.setLength(50);
        tableDefinition.addColumn(appId);

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

    private UisAppUserIdMappingDomainFactory() {

    }

}
