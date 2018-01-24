package com.glaf.etl.util;

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
public class EtlTransferTaskInstDomainFactory {

    public static final String TABLENAME = "ETL_TRANSFER_TASK_INST_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id_", "ID_");
        columnMap.put("taskId_", "TASK_ID_");
        columnMap.put("startTime_", "START_TIME_");
        columnMap.put("endTime_", "END_TIME_");
        columnMap.put("succeed_", "SUCCEED_");
        columnMap.put("srcSuccessRows_", "SRC_SUCCESS_ROWS_");
        columnMap.put("srcFailedRows_", "SRC_FAILED_ROWS_");
        columnMap.put("targetSuccessRows_", "TARGET_SUCCESS_ROWS_");
        columnMap.put("targetFailedRows_", "TARGET_FAILED_ROWS_");

	javaTypeMap.put("id_", "String");
        javaTypeMap.put("taskId_", "String");
        javaTypeMap.put("startTime_", "Date");
        javaTypeMap.put("endTime_", "Date");
        javaTypeMap.put("succeed_", "Integer");
        javaTypeMap.put("srcSuccessRows_", "Long");
        javaTypeMap.put("srcFailedRows_", "Long");
        javaTypeMap.put("targetSuccessRows_", "Long");
        javaTypeMap.put("targetFailedRows_", "Long");
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
        tableDefinition.setName("EtlTransferTaskInst");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id_");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(64);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition taskId_ = new ColumnDefinition();
        taskId_.setName("taskId_");
        taskId_.setColumnName("TASK_ID_");
        taskId_.setJavaType("String");
        taskId_.setLength(64);
        tableDefinition.addColumn(taskId_);

        ColumnDefinition startTime_ = new ColumnDefinition();
        startTime_.setName("startTime_");
        startTime_.setColumnName("START_TIME_");
        startTime_.setJavaType("Date");
        tableDefinition.addColumn(startTime_);

        ColumnDefinition endTime_ = new ColumnDefinition();
        endTime_.setName("endTime_");
        endTime_.setColumnName("END_TIME_");
        endTime_.setJavaType("Date");
        tableDefinition.addColumn(endTime_);

        ColumnDefinition succeed_ = new ColumnDefinition();
        succeed_.setName("succeed_");
        succeed_.setColumnName("SUCCEED_");
        succeed_.setJavaType("Integer");
        tableDefinition.addColumn(succeed_);

        ColumnDefinition srcSuccessRows_ = new ColumnDefinition();
        srcSuccessRows_.setName("srcSuccessRows_");
        srcSuccessRows_.setColumnName("SRC_SUCCESS_ROWS_");
        srcSuccessRows_.setJavaType("Long");
        tableDefinition.addColumn(srcSuccessRows_);

        ColumnDefinition srcFailedRows_ = new ColumnDefinition();
        srcFailedRows_.setName("srcFailedRows_");
        srcFailedRows_.setColumnName("SRC_FAILED_ROWS_");
        srcFailedRows_.setJavaType("Long");
        tableDefinition.addColumn(srcFailedRows_);

        ColumnDefinition targetSuccessRows_ = new ColumnDefinition();
        targetSuccessRows_.setName("targetSuccessRows_");
        targetSuccessRows_.setColumnName("TARGET_SUCCESS_ROWS_");
        targetSuccessRows_.setJavaType("Long");
        tableDefinition.addColumn(targetSuccessRows_);

        ColumnDefinition targetFailedRows_ = new ColumnDefinition();
        targetFailedRows_.setName("targetFailedRows_");
        targetFailedRows_.setColumnName("TARGET_FAILED_ROWS_");
        targetFailedRows_.setJavaType("Long");
        tableDefinition.addColumn(targetFailedRows_);

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

    private EtlTransferTaskInstDomainFactory() {

    }

}
