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
public class MonitorTerminalBusCategoryDomainFactory {

    public static final String TABLENAME = "MONITOR_TERMINAL_BUS_CATEGORY";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("terminalId", "TERMINAL_ID_");
        columnMap.put("categoryId", "CATEGORY_ID_");
        columnMap.put("createby", "CREATEBY_");
        columnMap.put("createtime", "CREATETIME_");

	javaTypeMap.put("terminalId", "String");
        javaTypeMap.put("categoryId", "Integer");
        javaTypeMap.put("createby", "String");
        javaTypeMap.put("createtime", "Date");
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
        tableDefinition.setName("MonitorTerminalBusCategory");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("terminalId");
        idColumn.setColumnName("TERMINAL_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition categoryId = new ColumnDefinition();
        categoryId.setName("categoryId");
        categoryId.setColumnName("CATEGORY_ID_");
        categoryId.setJavaType("Integer");
        tableDefinition.addColumn(categoryId);

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

    private MonitorTerminalBusCategoryDomainFactory() {

    }

}
