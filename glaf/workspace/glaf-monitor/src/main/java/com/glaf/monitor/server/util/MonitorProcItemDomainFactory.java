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
public class MonitorProcItemDomainFactory {

    public static final String TABLENAME = "MONITOR_PROC_ITEM";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("procId", "PROC_ID_");
        columnMap.put("code", "CODE_");
        columnMap.put("type", "TYPE_");
        columnMap.put("name", "NAME_");
        columnMap.put("unit", "UNIT_");
        columnMap.put("alarmVal", "ALARM_VAL_");
        columnMap.put("refType", "REF_TYPE_");
        columnMap.put("warningType", "WARNING_TYPE_");
        columnMap.put("monitorServiceAddress", "MONITOR_SERVICE_ADDRESS_");
        columnMap.put("value", "VALUE_");
        columnMap.put("createby", "CREATEBY_");
        columnMap.put("createtime", "CREATETIME_");
        columnMap.put("updateby", "UPDATEBY_");
        columnMap.put("updatetime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("procId", "String");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("type", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("unit", "String");
        javaTypeMap.put("alarmVal", "Integer");
        javaTypeMap.put("refType", "String");
        javaTypeMap.put("warningType", "String");
        javaTypeMap.put("monitorServiceAddress", "String");
        javaTypeMap.put("value", "String");
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
        tableDefinition.setName("MonitorProcItem");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition procId = new ColumnDefinition();
        procId.setName("procId");
        procId.setColumnName("PROC_ID_");
        procId.setJavaType("String");
        procId.setLength(50);
        tableDefinition.addColumn(procId);

        ColumnDefinition code = new ColumnDefinition();
        code.setName("code");
        code.setColumnName("CODE_");
        code.setJavaType("String");
        code.setLength(30);
        tableDefinition.addColumn(code);
        
        ColumnDefinition type = new ColumnDefinition();
        type.setName("type");
        type.setColumnName("TYPE_");
        type.setJavaType("String");
        type.setLength(20);
        tableDefinition.addColumn(type);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(30);
        tableDefinition.addColumn(name);

        ColumnDefinition unit = new ColumnDefinition();
        unit.setName("unit");
        unit.setColumnName("UNIT_");
        unit.setJavaType("String");
        unit.setLength(20);
        tableDefinition.addColumn(unit);

        ColumnDefinition alarmVal = new ColumnDefinition();
        alarmVal.setName("alarmVal");
        alarmVal.setColumnName("ALARM_VAL_");
        alarmVal.setJavaType("Integer");
        tableDefinition.addColumn(alarmVal);

        ColumnDefinition refType = new ColumnDefinition();
        refType.setName("refType");
        refType.setColumnName("REF_TYPE_");
        refType.setJavaType("String");
        refType.setLength(20);
        tableDefinition.addColumn(refType);

        ColumnDefinition warningType = new ColumnDefinition();
        warningType.setName("warningType");
        warningType.setColumnName("WARNING_TYPE_");
        warningType.setJavaType("String");
        warningType.setLength(20);
        tableDefinition.addColumn(warningType);

        ColumnDefinition monitorServiceAddress = new ColumnDefinition();
        monitorServiceAddress.setName("monitorServiceAddress");
        monitorServiceAddress.setColumnName("MONITOR_SERVICE_ADDRESS_");
        monitorServiceAddress.setJavaType("String");
        monitorServiceAddress.setLength(150);
        tableDefinition.addColumn(monitorServiceAddress);
        
        ColumnDefinition value = new ColumnDefinition();
        value.setName("value");
        value.setColumnName("VALUE_");
        value.setJavaType("String");
        value.setLength(150);
        tableDefinition.addColumn(value);

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

    private MonitorProcItemDomainFactory() {

    }

}
