package com.glaf.monitor.server.util;

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
public class MonitorLogDomainFactory {

    public static final String TABLENAME = "MONITOR_LOG_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("logTime", "LOG_TIME_");
        columnMap.put("logPath", "LOG_PATH_");
        columnMap.put("objectId", "OBJECT_ID_");
        columnMap.put("objectType", "OBJECT_TYPE_");
        columnMap.put("createby", "CREATEBY_");
        columnMap.put("createtime", "CREATETIME_");
        columnMap.put("updateby", "UPDATEBY_");
        columnMap.put("updatetime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("logTime", "Date");
        javaTypeMap.put("logPath", "String");
        javaTypeMap.put("objectId", "String");
        javaTypeMap.put("objectType", "String");
        javaTypeMap.put("createby", "String");
        javaTypeMap.put("createtime", "Date");
        javaTypeMap.put("updateby", "String");
        javaTypeMap.put("updatetime", "Date");
        javaTypeMap.put("deleteFlag", "Integer");
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
        tableDefinition.setName("MonitorLog");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition logTime = new ColumnDefinition();
        logTime.setName("logTime");
        logTime.setColumnName("LOG_TIME_");
        logTime.setJavaType("Date");
        tableDefinition.addColumn(logTime);

        ColumnDefinition logPath = new ColumnDefinition();
        logPath.setName("logPath");
        logPath.setColumnName("LOG_PATH_");
        logPath.setJavaType("String");
        logPath.setLength(200);
        tableDefinition.addColumn(logPath);

        ColumnDefinition objectId = new ColumnDefinition();
        objectId.setName("objectId");
        objectId.setColumnName("OBJECT_ID_");
        objectId.setJavaType("String");
        objectId.setLength(50);
        tableDefinition.addColumn(objectId);

        ColumnDefinition objectType = new ColumnDefinition();
        objectType.setName("objectType");
        objectType.setColumnName("OBJECT_TYPE_");
        objectType.setJavaType("String");
        objectType.setLength(20);
        tableDefinition.addColumn(objectType);

        ColumnDefinition createby = new ColumnDefinition();
        createby.setName("createby");
        createby.setColumnName("CREATEBY_");
        createby.setJavaType("String");
        createby.setLength(30);
        tableDefinition.addColumn(createby);

        ColumnDefinition createtime = new ColumnDefinition();
        createtime.setName("createtime");
        createtime.setColumnName("CREATETIME_");
        createtime.setJavaType("Date");
        tableDefinition.addColumn(createtime);

        ColumnDefinition updateby = new ColumnDefinition();
        updateby.setName("updateby");
        updateby.setColumnName("UPDATEBY_");
        updateby.setJavaType("String");
        updateby.setLength(30);
        tableDefinition.addColumn(updateby);

        ColumnDefinition updatetime = new ColumnDefinition();
        updatetime.setName("updatetime");
        updatetime.setColumnName("UPDATETIME_");
        updatetime.setJavaType("Date");
        tableDefinition.addColumn(updatetime);

        ColumnDefinition deleteFlag = new ColumnDefinition();
        deleteFlag.setName("deleteFlag");
        deleteFlag.setColumnName("DELETE_FLAG_");
        deleteFlag.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag);

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

    private MonitorLogDomainFactory() {

    }

}
