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
public class MonitorProcDomainFactory {

    public static final String TABLENAME = "MONITOR_PROC";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("terminalId", "TERMINAL_ID_");
        columnMap.put("level", "LEVEL_");
        columnMap.put("processName", "PROCESS_NAME_");
        columnMap.put("name", "NAME_");
        columnMap.put("prod", "PROD_");
        columnMap.put("ver", "VER_");
        columnMap.put("type", "TYPE_");
        columnMap.put("desc", "DESC_");
        columnMap.put("port", "PORT_");
        columnMap.put("monitorServiceAddress", "MONITOR_SERVICE_ADDRESS_");
        columnMap.put("startAddress", "START_ADDRESS_");
        columnMap.put("stopAddress", "STOP_ADDRESS_");
        columnMap.put("terminateAddress", "TERMINATE_ADDRESS_");
        columnMap.put("status", "STATUS_");
        columnMap.put("parentProcId", "PARENT_PROC_ID_");
        columnMap.put("otherItems", "OTHER_ITEMS_");
        columnMap.put("createby", "CREATEBY_");
        columnMap.put("createtime", "CREATETIME_");
        columnMap.put("updateby", "UPDATEBY_");
        columnMap.put("updatetime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");
        
        columnMap.put("startCommand", "START_COMMAND_");
        columnMap.put("stopCommand", "STOP_COMMAND_");
        columnMap.put("terminateCommand", "TERMINATE_COMMAND_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("terminalId", "String");
        javaTypeMap.put("level", "String");
        javaTypeMap.put("processName", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("prod", "String");
        javaTypeMap.put("ver", "String");
        javaTypeMap.put("type", "String");
        javaTypeMap.put("desc", "String");
        javaTypeMap.put("port", "Integer");
        javaTypeMap.put("monitorServiceAddress", "String");
        javaTypeMap.put("startAddress", "String");
        javaTypeMap.put("stopAddress", "String");
        javaTypeMap.put("terminateAddress", "String");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("parentProcId", "String");
        javaTypeMap.put("otherItems", "String");
        javaTypeMap.put("createby", "String");
        javaTypeMap.put("createtime", "Date");
        javaTypeMap.put("updateby", "String");
        javaTypeMap.put("updatetime", "Date");
        javaTypeMap.put("deleteFlag", "Integer");
        
        javaTypeMap.put("startCommand", "String");
        javaTypeMap.put("stopCommand", "String");
        javaTypeMap.put("terminateCommand", "String");
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
        tableDefinition.setName("MonitorProc");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition terminalId = new ColumnDefinition();
        terminalId.setName("terminalId");
        terminalId.setColumnName("TERMINAL_ID_");
        terminalId.setJavaType("String");
        terminalId.setLength(50);
        tableDefinition.addColumn(terminalId);

        ColumnDefinition level = new ColumnDefinition();
        level.setName("level");
        level.setColumnName("LEVEL_");
        level.setJavaType("String");
        level.setLength(10);
        tableDefinition.addColumn(level);

        ColumnDefinition processName = new ColumnDefinition();
        processName.setName("processName");
        processName.setColumnName("PROCESS_NAME_");
        processName.setJavaType("String");
        processName.setLength(100);
        tableDefinition.addColumn(processName);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition prod = new ColumnDefinition();
        prod.setName("prod");
        prod.setColumnName("PROD_");
        prod.setJavaType("String");
        prod.setLength(100);
        tableDefinition.addColumn(prod);

        ColumnDefinition ver = new ColumnDefinition();
        ver.setName("ver");
        ver.setColumnName("VER_");
        ver.setJavaType("String");
        ver.setLength(20);
        tableDefinition.addColumn(ver);

        ColumnDefinition type = new ColumnDefinition();
        type.setName("type");
        type.setColumnName("TYPE_");
        type.setJavaType("String");
        type.setLength(30);
        tableDefinition.addColumn(type);

        ColumnDefinition desc = new ColumnDefinition();
        desc.setName("desc");
        desc.setColumnName("DESC_");
        desc.setJavaType("String");
        desc.setLength(150);
        tableDefinition.addColumn(desc);

        ColumnDefinition port = new ColumnDefinition();
        port.setName("port");
        port.setColumnName("PORT_");
        port.setJavaType("Integer");
        tableDefinition.addColumn(port);

        ColumnDefinition monitorServiceAddress = new ColumnDefinition();
        monitorServiceAddress.setName("monitorServiceAddress");
        monitorServiceAddress.setColumnName("MONITOR_SERVICE_ADDRESS_");
        monitorServiceAddress.setJavaType("String");
        monitorServiceAddress.setLength(150);
        tableDefinition.addColumn(monitorServiceAddress);

        ColumnDefinition startAddress = new ColumnDefinition();
        startAddress.setName("startAddress");
        startAddress.setColumnName("START_ADDRESS_");
        startAddress.setJavaType("String");
        startAddress.setLength(150);
        tableDefinition.addColumn(startAddress);

        ColumnDefinition stopAddress = new ColumnDefinition();
        stopAddress.setName("stopAddress");
        stopAddress.setColumnName("STOP_ADDRESS_");
        stopAddress.setJavaType("String");
        stopAddress.setLength(150);
        tableDefinition.addColumn(stopAddress);

        ColumnDefinition terminateAddress = new ColumnDefinition();
        terminateAddress.setName("terminateAddress");
        terminateAddress.setColumnName("TERMINATE_ADDRESS_");
        terminateAddress.setJavaType("String");
        terminateAddress.setLength(150);
        tableDefinition.addColumn(terminateAddress);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS_");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition parentProcId = new ColumnDefinition();
        parentProcId.setName("parentProcId");
        parentProcId.setColumnName("PARENT_PROC_ID_");
        parentProcId.setJavaType("String");
        parentProcId.setLength(50);
        tableDefinition.addColumn(parentProcId);

        ColumnDefinition otherItems = new ColumnDefinition();
        otherItems.setName("otherItems");
        otherItems.setColumnName("OTHER_ITEMS_");
        otherItems.setJavaType("String");
        otherItems.setLength(0);
        tableDefinition.addColumn(otherItems);

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
        
        ColumnDefinition startCommand = new ColumnDefinition();
        startCommand.setName("startCommand");
        startCommand.setColumnName("START_COMMAND_");
        startCommand.setJavaType("String");
        startCommand.setLength(150);
        tableDefinition.addColumn(startCommand);

        ColumnDefinition stopCommand = new ColumnDefinition();
        stopCommand.setName("stopCommand");
        stopCommand.setColumnName("STOP_COMMAND_");
        stopCommand.setJavaType("String");
        stopCommand.setLength(150);
        tableDefinition.addColumn(stopCommand);

        ColumnDefinition terminateCommand = new ColumnDefinition();
        terminateCommand.setName("terminateCommand");
        terminateCommand.setColumnName("TERMINATE_COMMAND_");
        terminateCommand.setJavaType("String");
        terminateCommand.setLength(150);
        tableDefinition.addColumn(terminateCommand);

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

    private MonitorProcDomainFactory() {

    }

}
