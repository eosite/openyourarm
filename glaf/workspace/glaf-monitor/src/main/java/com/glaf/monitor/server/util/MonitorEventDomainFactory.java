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
public class MonitorEventDomainFactory {

    public static final String TABLENAME = "MONITOR_EVENT_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("eventId", "EVENT_ID_");
        columnMap.put("objectId", "OBJECT_ID_");
        columnMap.put("objectType", "OBJECT_TYPE_");
        columnMap.put("eventType", "EVENT_TYPE_");
        columnMap.put("eventMonitorItem", "EVENT_MONITOR_ITEM_");
        columnMap.put("happenTime", "HAPPEN_TIME_");
        columnMap.put("snapshot", "SNAPSHOT_");
        columnMap.put("recoveryTime", "RECOVERY_TIME_");
        columnMap.put("status", "STATUS");
        columnMap.put("createby", "CREATEBY_");
        columnMap.put("createtime", "CREATETIME_");
        columnMap.put("updateby", "UPDATEBY_");
        columnMap.put("updatetime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("eventId", "String");
        javaTypeMap.put("objectId", "String");
        javaTypeMap.put("objectType", "String");
        javaTypeMap.put("eventType", "String");
        javaTypeMap.put("eventMonitorItem", "String");
        javaTypeMap.put("happenTime", "Date");
        javaTypeMap.put("snapshot", "String");
        javaTypeMap.put("recoveryTime", "Date");
        javaTypeMap.put("status", "Integer");
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
        tableDefinition.setName("MonitorEvent");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("eventId");
        idColumn.setColumnName("EVENT_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


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

        ColumnDefinition eventType = new ColumnDefinition();
        eventType.setName("eventType");
        eventType.setColumnName("EVENT_TYPE_");
        eventType.setJavaType("String");
        eventType.setLength(20);
        tableDefinition.addColumn(eventType);

        ColumnDefinition eventMonitorItem = new ColumnDefinition();
        eventMonitorItem.setName("eventMonitorItem");
        eventMonitorItem.setColumnName("EVENT_MONITOR_ITEM_");
        eventMonitorItem.setJavaType("String");
        eventMonitorItem.setLength(50);
        tableDefinition.addColumn(eventMonitorItem);

        ColumnDefinition happenTime = new ColumnDefinition();
        happenTime.setName("happenTime");
        happenTime.setColumnName("HAPPEN_TIME_");
        happenTime.setJavaType("Date");
        tableDefinition.addColumn(happenTime);

        ColumnDefinition snapshot = new ColumnDefinition();
        snapshot.setName("snapshot");
        snapshot.setColumnName("SNAPSHOT_");
        snapshot.setJavaType("String");
        snapshot.setLength(0);
        tableDefinition.addColumn(snapshot);

        ColumnDefinition recoveryTime = new ColumnDefinition();
        recoveryTime.setName("recoveryTime");
        recoveryTime.setColumnName("RECOVERY_TIME_");
        recoveryTime.setJavaType("Date");
        tableDefinition.addColumn(recoveryTime);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

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

    private MonitorEventDomainFactory() {

    }

}
